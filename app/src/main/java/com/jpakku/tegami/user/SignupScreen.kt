package com.jpakku.tegami.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jpakku.tegami.R
import com.jpakku.tegami.ui.theme.Typography


@Composable
fun SignupScreen(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize().padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        WelcomeMessageText()
        EmailTextInput()

        //TODO: make password invisible until email is inputted
//        PasswordTextInput()
    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    Surface {
        SignupScreen(rememberNavController())
    }
}

@Composable
fun PasswordTextInput() {
    var password by remember {
        mutableStateOf("")
    }

    var showPassword by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = password,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(text = stringResource(R.string.password), color = Color.Gray) },
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        onValueChange = {
            password = it
        },
        trailingIcon = {
            IconButton(onClick = { showPassword = !showPassword }) {
                Icon(
                    imageVector = if (showPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                    contentDescription = if (showPassword) stringResource(R.string.show_password) else stringResource(R.string.hide_password),
                    tint = Color.Gray
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun EmailTextInput() {
    var email by remember {
        mutableStateOf("")
    }

    val trailingIconView = @Composable {
        IconButton(
            onClick = {
                email = ""
            },
        ) {
            Icon(
                Icons.Default.ArrowRight,
                contentDescription = "Continue",
            )
        }
    }

    TextField(
        value = email,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email Icon"
            )
        },
        label = { Text(text = stringResource(R.string.email), color = Color.Gray) },
        onValueChange = {
            email = it
        },
        singleLine = true,
        trailingIcon = if (email.isNotBlank()) trailingIconView else null,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { email = "" }
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            errorTextColor = MaterialTheme.colorScheme.error
        )
    )
}

@Composable
fun WelcomeMessageText() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Make new friends from all over the world",
            textAlign = TextAlign.Center,
            style = Typography.labelLarge,
        )

        Text(
            text = "Send letters to anyone",
            modifier = Modifier.padding(0.dp, 20.dp),
            textAlign = TextAlign.Center,
            style = Typography.labelLarge,
        )

        Text(
            text = "To start, enter you email:",
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 20.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = Typography.bodyLarge
        )
    }
}