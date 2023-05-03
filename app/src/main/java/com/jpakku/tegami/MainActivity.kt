package com.jpakku.tegami

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jpakku.tegami.splash.SplashScreen
import com.jpakku.tegami.ui.theme.TegamiTheme
import com.jpakku.tegami.user.SignupScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TegamiTheme(darkTheme = false) {

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()

                    NavHost(navController, "splash") {
                        composable("splash") {
                            SplashScreen(navController)
                        }
                        composable("login") { SignupScreen(navController) }
                    }
                }
            }
        }
    }
}