package com.m57.hermescontrol.ui.chat

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Batch-9 F5: revert is ChatGPT-visual — the tapped row and everything after
 * it vanishes locally, and the recovered prompt lands in the composer with
 * the keyboard auto-open flag set. Pure state-planning test (see
 * [ChatUiState.revertedTo]); no ViewModel harness needed.
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
}
