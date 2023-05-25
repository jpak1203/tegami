package com.jpakku.tegami.ui.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.jpakku.tegami.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateToHomeScreen: () -> Unit, onNavigateToUserAuthScreen: () -> Unit) {
    val viewModel = hiltViewModel<SplashScreenViewModel>()
    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1.5f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(3f).getInterpolation(it)
                }
            )
        )
        delay(2000L)
        val user = viewModel.getUser()
        if (user != null) {
            onNavigateToHomeScreen()
        } else {
            onNavigateToUserAuthScreen()
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier.scale(scale.value),
            model = R.drawable.tegami_foreground,
            contentDescription = "Logo"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    val navController = rememberNavController()
    Surface {
        SplashScreen(onNavigateToHomeScreen = { navController.navigate("home/false") {
            popUpTo(navController.graph.id) { inclusive = true }
        } },
            onNavigateToUserAuthScreen = { navController.navigate("user-auth") })
    }
}