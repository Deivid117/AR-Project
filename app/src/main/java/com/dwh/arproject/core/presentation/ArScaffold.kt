package com.dwh.arproject.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dwh.arproject.R

@Composable
fun ArScaffold(
    isSnackBarVisible: Boolean = false,
    onDismissSnackBar: () -> Unit = {},
    content: @Composable() (BoxScope.() -> Unit) = {}
) {
    val snackBarHostState = remember { SnackbarHostState() }

    HandleSnackBar(
        snackBarHostState = snackBarHostState,
        isSnackBarVisible = isSnackBarVisible,
        onDismiss = onDismissSnackBar
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) { content() }
    }
}

@Composable
private fun HandleSnackBar(
    snackBarHostState: SnackbarHostState,
    isSnackBarVisible: Boolean,
    onDismiss: () -> Unit
) {
    val snackBarMessage = stringResource(id = R.string.scaffold_snackbar_message)

    LaunchedEffect(isSnackBarVisible) {
        if (isSnackBarVisible) {
            val snackBarResult = snackBarHostState.showSnackbar(
                message = snackBarMessage,
                withDismissAction = true,
                duration = SnackbarDuration.Short
            )

            when (snackBarResult) {
                SnackbarResult.ActionPerformed -> {}
                SnackbarResult.Dismissed -> onDismiss()
            }
        }
    }
}