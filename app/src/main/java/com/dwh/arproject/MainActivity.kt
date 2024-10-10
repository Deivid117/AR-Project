package com.dwh.arproject

import android.graphics.Color.*
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.dwh.arproject.core.navigation.Navigation
import com.dwh.arproject.ui.theme.ArProjectTheme
import com.dwh.arproject.ui.theme.YellowStatusBar
import com.google.ar.core.ArCoreApk
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(scrim = YellowStatusBar.toArgb(), darkScrim = TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(scrim = TRANSPARENT, darkScrim = TRANSPARENT)
        )

        installSplashScreen()

        setContent {
            ArProjectTheme {
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                    ){*/
                        val navController = rememberNavController()

                        Navigation(navController = navController)
                    /*}
                }*/
            }
        }
    }
}