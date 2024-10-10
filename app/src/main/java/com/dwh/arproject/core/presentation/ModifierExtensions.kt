package com.dwh.arproject.core.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.Role
import kotlinx.coroutines.delay

fun Modifier.clickableSingle(
    delay: Long = 4000L,
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    onClick: () -> Unit
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickable"
        properties["enabled"] = enabled
        properties["onClickLabel"] = onClickLabel
        properties["role"] = role
        properties["onClick"] = onClick
    }
) {
    var isEnabled by remember { mutableStateOf(enabled) }

    LaunchedEffect(isEnabled) {
        if (!isEnabled) delay(delay)
        isEnabled = true
    }

    this@clickableSingle.clickable(
        enabled = isEnabled,
        onClickLabel = onClickLabel,
        onClick = { onClick(); isEnabled = false },
        role = role,
        indication = rememberRipple(),
        interactionSource = remember { MutableInteractionSource() }
    )
}