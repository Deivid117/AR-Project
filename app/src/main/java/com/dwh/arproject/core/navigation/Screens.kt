package com.dwh.arproject.core.navigation

enum class Screens {
    PRODUCT_SCREEN,
    AR_PRODUCT_SCREEN
}

sealed class NavigationScreens(val route: String) {
    data object Product : NavigationScreens(Screens.PRODUCT_SCREEN.name)
    data object ArProduct : NavigationScreens(Screens.AR_PRODUCT_SCREEN.name)
}