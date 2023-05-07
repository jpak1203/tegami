package com.jpakku.tegami

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jpakku.tegami.ui.home.HomeScreen
import com.jpakku.tegami.ui.splash.SplashScreen
import com.jpakku.tegami.ui.theme.TegamiTheme
import com.jpakku.tegami.ui.user.UserAuthScreen
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
                        composable("user-auth") {
                            UserAuthScreen(navController)
                        }
                        composable(
                            "home/{userId}/{newUser}",
                            arguments = listOf(
                                navArgument("userId") { type = NavType.StringType},
                                navArgument("newUser") { type = NavType.BoolType }
                            )
                        ) { backStackEntry ->
                            val userId = backStackEntry.arguments?.getString("userId")
                            val newUser = backStackEntry.arguments?.getBoolean("newUser")

                            //TODO: HomeScreen(userId, newUser)
                            HomeScreen(userId, newUser)
                        }
                    }
                }
            }
        }
    }
}