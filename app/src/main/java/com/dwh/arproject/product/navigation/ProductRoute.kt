package com.dwh.arproject.product.navigation

import android.app.Activity
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dwh.arproject.core.navigation.NavigationScreens
import com.dwh.arproject.core.navigation.Screens
import com.dwh.arproject.core.presentation.sharedViewModel
import com.dwh.arproject.product.presentation.composables.ImageZoomDialog
import com.dwh.arproject.product.presentation.ProductScreen
import com.dwh.arproject.product.presentation.ProductViewModel

fun NavGraphBuilder.productGraph(navController: NavController) {
    composable(
        route = NavigationScreens.Product.route,
        exitTransition = { fadeOut(animationSpec = tween(800)) }
    ) { backStackEntry ->
        val context = LocalContext.current

        val viewModel = backStackEntry.sharedViewModel<ProductViewModel>(navController)

        val products by viewModel.products.collectAsStateWithLifecycle()
        val isZoomDialogVisible by viewModel.isZoomDialogVisible.collectAsStateWithLifecycle()
        val productImage by viewModel.productImage.collectAsStateWithLifecycle()
        val isSnackBarVisible by viewModel.isSnackBarVisible.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.getProducts()
        }

        ImageZoomDialog(
            isDialogVisible = isZoomDialogVisible,
            productImage = painterResource(id = productImage),
            onDismiss = { viewModel.hideImageZoomDialog() }
        )

        ProductScreen(
            products = products,
            isSnackBarVisible = isSnackBarVisible,
            onDismissSnackBar = { viewModel.hideSnackBar() },
            onShowZoomDialog = { viewModel.showImageZoomDialog(it) }
        ) { arProductModels ->
            viewModel.isDeviceSupportArCore(context = context, activity = context as Activity) {
                if (it) {
                    viewModel.setArProductModels(arProductModels)
                    navController.navigate(Screens.AR_PRODUCT_SCREEN.name)
                }
            }
        }
    }
}