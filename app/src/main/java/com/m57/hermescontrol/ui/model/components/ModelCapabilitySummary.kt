package com.m57.hermescontrol.ui.model.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.m57.hermescontrol.data.model.ModelCapabilities
import com.m57.hermescontrol.data.model.reasoningSupport
import com.m57.hermescontrol.theme.LocalHermesStatusColors

@Composable
fun ModelCapabilitySummary(capabilities: ModelCapabilities?) {
    if (capabilities == null) return
    // Batch-9 F7 audit: each label carries its polarity — positive
    // capabilities tint success (matching CapabilityToggle's on-state),
    // negatives and neutral specs stay onSurfaceVariant.
    val labels =
        buildList {
            capabilities.supports_tools?.let { add(if (it) "Tools" else "No tools" to it) }
            capabilities.supports_vision?.let { add(if (it) "Vision" else "No vision" to it) }
            capabilities.reasoningSupport?.let { add(if (it) "Reasoning" else "no reasoning" to it) }
            if (capabilities.reasoningSupport == true && capabilities.can_disable_reasoning == false) {
                add("reasoning always on" to true)
            }
            capabilities.context_window?.let { add("${it / 1000}K ctx" to false) }
            capabilities.max_output_tokens?.let { add("${it / 1000}K output" to false) }
            capabilities.model_family?.takeIf { it.isNotBlank() }?.let { add(it to false) }
        }
    if (labels.isNotEmpty()) {
        val statusColors = LocalHermesStatusColors.current
        val neutral = MaterialTheme.colorScheme.onSurfaceVariant
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            labels.forEachIndexed { index, (label, positive) ->
                if (index > 0) {
                    Text(
                        text = "·",
                        style = MaterialTheme.typography.labelSmall,
                        color = neutral,
                    )
                }
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (positive) statusColors.success else neutral,
                )
            }
        }
    }
}
