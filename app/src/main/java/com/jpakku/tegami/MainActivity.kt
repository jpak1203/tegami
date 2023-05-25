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
import com.jpakku.tegami.ui.letter.WriteLetterScreen
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
                            SplashScreen(
                                onNavigateToHomeScreen = { navController.navigate("home/false") {
                                    popUpTo(navController.graph.id) { inclusive = true }
                                } },
                                onNavigateToUserAuthScreen = { navController.navigate("user-auth") }
                            )
                        }
                        composable("user-auth") {
                            UserAuthScreen(
                                onNavigateToHomeScreen = { navController.navigate("home/$it") {
                                    popUpTo(navController.graph.id) { inclusive = true }
                                } }
                            )
                        }
                        composable(
                            "home/{newUser}",
                            arguments = listOf(
                                navArgument("newUser") { type = NavType.BoolType }
                            )
                        ) { backStackEntry ->
                            val newUser = backStackEntry.arguments?.getBoolean("newUser")

                            HomeScreen(
                                newUser,
                                onNavigateToWriteLetterScreen = { navController.navigate("write-letter/$it") }
                            )
                        }
                        composable(
                            "write-letter/{writeTo}",
                            arguments = listOf(
                                navArgument("writeTo") { type = NavType.StringType}
                            )
                        ) { backStackEntry ->
                            val writeTo = backStackEntry.arguments?.getString("writeTo")

                            WriteLetterScreen(writeTo)
                        }
                    }
                }
            }
        }
    }
}