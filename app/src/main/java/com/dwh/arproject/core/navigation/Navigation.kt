package com.dwh.arproject.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import com.dwh.arproject.product.navigation.productGraph
import com.dwh.arproject.ar_product.navigation.arProductGraph

@Composable
fun Navigation(navController: NavController) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = "Products"
    ) {
        navigation(startDestination = NavigationScreens.Product.route, route = "Products") {
            productGraph(navController)

            arProductGraph(navController)
        }
    }
}