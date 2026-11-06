package com.m57.hermescontrol.ui.chat

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Batch-9 F5: revert is ChatGPT-visual — the tapped row and everything after
 * it vanishes locally, and the recovered prompt lands in the composer with
 * the keyboard auto-open flag set. Batch-10 G2: the truncation is DURABLE —
 * the removed tail is staged (StagedRevert), every ingestion site filters
 * the staged ids, cancel restores the tail, and a send while staged keeps
 * the staged state. Pure state-planning test (see [ChatUiState.revertedTo],
 * [ChatUiState.cancelledStagedRevert], excludingStagedRevert); no ViewModel
 * harness needed.
 */
class ChatRevertStateTest {
    private fun state(vararg messages: ChatMessage) = ChatUiState(messages = messages.toList())

    @Test
    fun revertOnAssistantRow_truncatesThroughTappedRowAndStagesPrompt() {
        val user = ChatMessage(id = "u1", role = MessageRole.USER, content = "original prompt")
        val assistant = ChatMessage(id = "a1", role = MessageRole.ASSISTANT, content = "reply")
        val laterUser = ChatMessage(id = "u2", role = MessageRole.USER, content = "second prompt")
        val laterAssistant = ChatMessage(id = "a2", role = MessageRole.ASSISTANT, content = "second reply")

        val next = state(user, assistant, laterUser, laterAssistant).revertedTo("a1")

        checkNotNull(next)
        assertEquals(listOf(user), next.messages)
        assertEquals("original prompt", next.pendingPrefillText)
        assertTrue(next.pendingPrefillFocus)
    }

    @Test
    fun revertOnUserRow_removesTappedAndLaterRows() {
        val user = ChatMessage(id = "u1", role = MessageRole.USER, content = "original prompt")
        val assistant = ChatMessage(id = "a1", role = MessageRole.ASSISTANT, content = "reply")
        val laterUser = ChatMessage(id = "u2", role = MessageRole.USER, content = "second prompt")
        val laterAssistant = ChatMessage(id = "a2", role = MessageRole.ASSISTANT, content = "second reply")

        val next = state(user, assistant, laterUser, laterAssistant).revertedTo("u2")

        checkNotNull(next)
        assertEquals(listOf(user, assistant), next.messages)
        assertEquals("second prompt", next.pendingPrefillText)
        assertTrue(next.pendingPrefillFocus)
    }

    @Test
    fun revertWithoutRecoverablePrompt_isNoPrompt() {
        val orphan = ChatMessage(id = "a0", role = MessageRole.ASSISTANT, content = "orphan reply")

        assertNull(state(orphan).revertedTo("a0"))
        assertNull(state(orphan).revertedTo("missing-id"))
    }

    @Test
    fun revertStagesRemovedTailForCancel() {
        val user = ChatMessage(id = "u1", role = MessageRole.USER, content = "original prompt")
        val assistant = ChatMessage(id = "a1", role = MessageRole.ASSISTANT, content = "reply")
        val laterUser = ChatMessage(id = "u2", role = MessageRole.USER, content = "second prompt")
        val laterAssistant = ChatMessage(id = "a2", role = MessageRole.ASSISTANT, content = "second reply")

        val next = state(user, assistant, laterUser, laterAssistant).revertedTo("a1")

        checkNotNull(next)
        val staged = checkNotNull(next.stagedRevert)
        assertEquals(listOf(assistant, laterUser, laterAssistant), staged.removedMessages)
        assertEquals(setOf("a1", "u2", "a2"), staged.removedIds)
        assertEquals("original prompt", staged.prefillText)
    }

    @Test
    fun ingestionGuardDropsRemovedIdsWhileStaged() {
        val user = ChatMessage(id = "u1", role = MessageRole.USER, content = "original prompt")
        val assistant = ChatMessage(id = "a1", role = MessageRole.ASSISTANT, content = "reply")
        val laterUser = ChatMessage(id = "u2", role = MessageRole.USER, content = "second prompt")
        val laterAssistant = ChatMessage(id = "a2", role = MessageRole.ASSISTANT, content = "second reply")
        val staged = state(user, assistant, laterUser, laterAssistant).revertedTo("a1")

        checkNotNull(staged)
        // A sync merge re-offering the whole server transcript (including
        // the removed tail) keeps only the pre-revert rows.
        assertEquals(
            listOf(user),
            listOf(user, assistant, laterUser, laterAssistant).excludingStagedRevert(staged),
        )
        // Nothing staged: the guard is a pass-through.
        val unstaged = state(user, assistant)
        assertEquals(listOf(user, assistant), listOf(user, assistant).excludingStagedRevert(unstaged))
    }

    @Test
    fun cancelRestoresRemovedTailExactly() {
        val user = ChatMessage(id = "u1", role = MessageRole.USER, content = "original prompt")
        val assistant = ChatMessage(id = "a1", role = MessageRole.ASSISTANT, content = "reply")
        val laterUser = ChatMessage(id = "u2", role = MessageRole.USER, content = "second prompt")
        val laterAssistant = ChatMessage(id = "a2", role = MessageRole.ASSISTANT, content = "second reply")
        val original = state(user, assistant, laterUser, laterAssistant)

        val cancelled = original.revertedTo("a1")!!.cancelledStagedRevert()

        assertEquals(original.messages, cancelled.messages)
        assertNull(cancelled.stagedRevert)
        assertNull(cancelled.pendingPrefillText)
        assertFalse(cancelled.pendingPrefillFocus)
    }

    @Test
    fun sendWhileStagedKeepsStagedStateAndFiltersTail() {
        val user = ChatMessage(id = "u1", role = MessageRole.USER, content = "original prompt")
        val assistant = ChatMessage(id = "a1", role = MessageRole.ASSISTANT, content = "reply")
        val staged = state(user, assistant).revertedTo("a1")

        checkNotNull(staged)
        val newTurn = ChatMessage(id = "u9", role = MessageRole.USER, content = "edited prompt")
        // The send site's reducer shape: the new turn appends after the
        // truncated point, the staged ids stay filtered, and the staged
        // state itself survives the send untouched (ChatGPT parity).
        val afterSend =
            staged.copy(
                messages = (staged.messages + newTurn).excludingStagedRevert(staged),
                isSending = true,
            )

        assertEquals(listOf(user, newTurn), afterSend.messages)
        assertEquals(staged.stagedRevert, afterSend.stagedRevert)
    }

    @Test
    fun cancelAfterSendRestoresTailBeforeTheNewTurn() {
        val user = ChatMessage(id = "u1", role = MessageRole.USER, content = "original prompt")
        val assistant = ChatMessage(id = "a1", role = MessageRole.ASSISTANT, content = "reply")
        val staged = state(user, assistant).revertedTo("a1")

        checkNotNull(staged)
        val newTurn = ChatMessage(id = "u9", role = MessageRole.USER, content = "edited prompt")
        val afterSend = staged.copy(messages = staged.messages + newTurn)

        val cancelled = afterSend.cancelledStagedRevert()

        // Chronology: the restored tail sits where it was (before the
        // post-revert turn), NOT appended after it.
        assertEquals(listOf(user, assistant, newTurn), cancelled.messages)
        assertNull(cancelled.stagedRevert)
    }
}
