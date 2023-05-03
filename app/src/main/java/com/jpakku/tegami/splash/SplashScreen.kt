package com.jpakku.tegami.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jpakku.tegami.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

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
        navController.navigate("login")
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.scale(scale.value),
            painter = painterResource(id = R.drawable.tegami_foreground),
            contentDescription = "Logo"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    Surface {
        SplashScreen(rememberNavController())
    }
}