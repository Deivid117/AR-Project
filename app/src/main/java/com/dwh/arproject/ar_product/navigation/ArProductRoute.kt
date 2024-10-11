package com.dwh.arproject.ar_product.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dwh.arproject.core.navigation.NavigationScreens
import com.dwh.arproject.core.presentation.sharedViewModel
import com.dwh.arproject.product.presentation.ProductViewModel
import com.dwh.arproject.ar_product.presentation.ArProductScreen
import com.dwh.arproject.ar_product.presentation.ArProductViewModel

fun NavGraphBuilder.arProductGraph(navController: NavController) {
    composable(
        route = NavigationScreens.ArProduct.route,
        enterTransition = {
            slideIntoContainer(
                animationSpec = tween(800),
                towards = AnimatedContentTransitionScope.SlideDirection.Left
            )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween(800)
            ) + slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(800)
            )
        }
    ) { backStackEntry ->

        val sharedViewModel = backStackEntry.sharedViewModel<ProductViewModel>(navController)
        val arProductModels by sharedViewModel.arProductModels.collectAsStateWithLifecycle()

        val viewModel = hiltViewModel<ArProductViewModel>()
        val modelName by viewModel.modelName.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.setModelName(arProductModels.first().name)
        }

        ArProductScreen(
            arProductModelS = arProductModels,
            modelName = modelName,
            onSetModelName = { viewModel.setModelName(it) },
            onBackClick = { navController.navigateUp() }
        )
    }
}