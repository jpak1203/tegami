package com.jpakku.tegami.activities

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jpakku.tegami.ui.home.HomeScreen
import com.jpakku.tegami.ui.inbox.InboxScreen
import com.jpakku.tegami.ui.letter.WriteLetterScreen
import com.jpakku.tegami.ui.rules.RulesScreen
import com.jpakku.tegami.ui.settings.SettingsScreen
import com.jpakku.tegami.ui.splash.SplashScreen
import com.jpakku.tegami.ui.theme.TegamiTheme
import com.jpakku.tegami.ui.user.UserAuthScreen
import com.jpakku.tegami.util.DataStoreUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var dataStoreUtil: DataStoreUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataStoreUtil = DataStoreUtil(applicationContext)

        val systemTheme = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> { true }
            Configuration.UI_MODE_NIGHT_NO -> { false }
            else -> { false }
        }

        setContent {
            val theme = dataStoreUtil.getTheme(systemTheme).collectAsState(initial = systemTheme)
            viewModel.setTheme(theme.value)

            TegamiTheme(darkTheme = theme.value) {

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
                                    popUpTo(navController.graph.id) { inclusive = true } }
                                }
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
                                onNavigateToWriteLetterScreen = { navController.navigate("write-letter/$it") },
                                onNavigateToInboxScreen = { navController.navigate("inbox") },
                                onNavigateToSettingsScreen = { navController.navigate("settings") }
                            )
                        }

                        composable(
                            "settings"
                        ) {
                            SettingsScreen(
                                dataStoreUtil,
                                viewModel,
                                onChangePassword = { navController.navigate("change-password") },
                                onChangeTheme = { navController.navigate("themes") },
                                onNavigateToRulesScreen = { navController.navigate("rules") },
                                onSignOut = {
                                    navController.navigate("splash") {
                                        popUpTo(navController.graph.id) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable("change-password") {

                        }

                        composable("themes") {

                        }

                        composable("rules") {
                            RulesScreen()
                        }

                        composable(
                            "write-letter/{writeTo}",
                            arguments = listOf(
                                navArgument("writeTo") { type = NavType.StringType}
                            )
                        ) { backStackEntry ->
                            val writeTo = backStackEntry.arguments?.getString("writeTo")

                            WriteLetterScreen(
                                writeTo,
                                onNavigateToHomeScreen = { navController.navigate("home/false") {
                                    popUpTo(navController.graph.id) { inclusive = true } }
                                }
                            )
                        }

                        composable(
                            "inbox"
                        ) {

                            InboxScreen(
                                onNavigateToReadLetterScreen = { navController.navigate("read-letter") },
                            )
                        }

                        composable(
                            "read-letter",
                            arguments = listOf(
                                navArgument("from") { type = NavType.StringType}
                            )
                        ) {backStackEntry ->
                            val from = backStackEntry.arguments?.getString("from")

                        }
                    }
                }
            }
        }
    }
}