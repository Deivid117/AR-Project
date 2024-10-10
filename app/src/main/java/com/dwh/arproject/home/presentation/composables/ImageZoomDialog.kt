package com.dwh.arproject.home.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.window.Dialog
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Composable
fun ImageZoomDialog(
    isDialogVisible: Boolean,
    productImage: Painter,
    onDismiss: () -> Unit = {}
) {
    if (isDialogVisible) {
        val zoomState = rememberZoomState(contentSize = productImage.intrinsicSize)

        Dialog(onDismissRequest = { onDismiss() }) {
            Box(modifier = Modifier.fillMaxSize()) {

                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .zoomable(
                            zoomState = zoomState,
                            onDoubleTap = { position ->
                                val targetScale = when {
                                    zoomState.scale < 2f -> 2f
                                    zoomState.scale < 4f -> 4f
                                    else -> 1f
                                }
                                zoomState.changeScale(targetScale, position)
                            }),
                    painter = productImage,
                    contentDescription = "product image"
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = { onDismiss() },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Black)
                    ) {
                        Icon(imageVector = Icons.Outlined.Close, contentDescription = "close icon", tint = Color.White)
                    }
                }
            }
        }
    }
}