package com.jpakku.tegami.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun LoginScreen() {
    Column(modifier = Modifier.padding(12.dp)) {
        val visible = remember { mutableStateOf(false) }

        EmailTextInput(visible)
        PasswordTextInput(visible)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    Surface {
        LoginScreen()
    }
}


@Composable
fun SignupView() {

}