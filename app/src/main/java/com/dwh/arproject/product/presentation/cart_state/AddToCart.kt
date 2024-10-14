package com.dwh.arproject.product.presentation.cart_state

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import com.dwh.arproject.ui.theme.LocalAddToCartState
import kotlinx.coroutines.launch

@Composable
fun AddToCartContainer(
    addToCardState: AddToCartState,
    content: @Composable () -> Unit
) {
    Box {
        content()
        val itemKey by addToCardState.selectedKey
        val info = addToCardState.getInfo(itemKey)

        if (info != null) {
            AnimateToTarget(
                info = info,
                target = addToCardState.targetPosition
            )
        }
    }
}

@Composable
private fun AnimateToTarget(
    info: CartItemModelInfo,
    target: Offset
) {
    val state = LocalAddToCartState.current

    val animatable = remember { Animatable(0f) }

    val scale = (1.5f - animatable.value).coerceAtLeast(0.5f)
    val newTranslationX = info.run {
        position.x + (target.x - position.x) * animatable.value - 30
    }
    val newTranslationY = info.run {
        position.y + (target.y - position.y) * animatable.value - size.height / 2
    }

    Box(
        modifier = Modifier.graphicsLayer {
            translationX = newTranslationX
            translationY = newTranslationY
            scaleX = scale
            scaleY = scale
        }
    ) {
        info.composable()
    }

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = info) {
        scope.launch {
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 300)
            )
            state.stop()
        }
    }
}

@Composable
fun CartItemTarget(
    key: String,
    content: @Composable () -> Unit
) {
    val addToCartState = LocalAddToCartState.current

    val itemInfo = remember(key1 = key) {
        mutableStateOf(CartItemModelInfo(composable = content))
    }

    Box(modifier = Modifier.onGloballyPositioned {
        itemInfo.value = itemInfo.value.copy(
            position = it.localToWindow(Offset.Zero),
            size = it.size,
        )
    }) {
        content()
    }

    DisposableEffect(key1 = key, key2 = itemInfo.value, effect = {
        addToCartState.itemInfo(key, itemInfo.value)

        onDispose {
            addToCartState.itemInfo(key, null)
        }
    })
}