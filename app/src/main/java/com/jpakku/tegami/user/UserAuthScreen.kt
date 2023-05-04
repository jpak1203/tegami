package com.jpakku.tegami.user

import android.content.Context
import android.view.KeyEvent.ACTION_DOWN
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jpakku.tegami.R
import com.jpakku.tegami.ui.theme.Typography
import com.jpakku.tegami.util.ValidatorUtils
import timber.log.Timber


@Composable
fun UserAuthScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp, 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val context = LocalContext.current
        val viewModel = hiltViewModel<UserAuthScreenViewModel>()

        val email by viewModel.emailInput.observeAsState("")
        val password by viewModel.passwordInput.observeAsState("")
        val isNewUser by viewModel.isNewUser.observeAsState(false)

        WelcomeMessageText(viewModel)
        val visible = remember { mutableStateOf(false) }

        EmailTextInput(viewModel, email, visible)
        PasswordTextInput(context, visible, viewModel, email, password, isNewUser, navController)
        UserAuthButton(context, visible, viewModel, email, password, isNewUser, navController)
    }
}

@Preview(showBackground = true)
@Composable
fun UserAuthScreenPreview() {
    Surface {
        UserAuthScreen(rememberNavController())
    }
}

@Composable
fun PasswordTextInput(
    context: Context,
    state: MutableState<Boolean>,
    viewModel: UserAuthScreenViewModel,
    email: String,
    password: String,
    isNewUser: Boolean,
    navController: NavController
) {
    val focusManager = LocalFocusManager.current

    val showPassword by viewModel.showPasswordFlag.observeAsState(false)
    var isError by rememberSaveable { mutableStateOf(false) }

    AnimatedVisibility(visible = state.value) {
        TextField(
            value = password,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .onPreviewKeyEvent {
                    if (it.key == Key.Tab && it.nativeKeyEvent.action == ACTION_DOWN) {
                        focusManager.moveFocus(FocusDirection.Down)
                        true
                    } else {
                        false
                    }
                },
            label = { Text(text = stringResource(R.string.password), color = Color.Gray) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Password,
                    contentDescription = "Password Icon"
                )
            },
            singleLine = true,
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            onValueChange = {
                viewModel.setPassword(it)
            },
            isError = isError,
            supportingText = {
                 if (isError) {
                     Text(
                         modifier = Modifier.fillMaxWidth(),
                         text = stringResource(id = R.string.password_error),
                         color = MaterialTheme.colorScheme.error
                     )
                 }
            },
            trailingIcon = {
                IconButton(onClick = { viewModel.toggleShowPassword() }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription = if (showPassword) stringResource(R.string.show_password) else stringResource(R.string.hide_password),
                        tint = Color.Gray
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    isError = !ValidatorUtils.validPassword(password)
                    if (!isError) {
                        userAuth(
                            context = context,
                            viewModel = viewModel,
                            email = email,
                            password = password,
                            isNewUser = isNewUser,
                            navController = navController
                        )
                    } else {
                        Timber.e(context.getString(R.string.user_auth_error))
                    }
                }
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                errorTextColor = MaterialTheme.colorScheme.error,
                errorContainerColor = Color.Transparent
            )
        )
    }
}

@Composable
fun EmailTextInput(viewModel: UserAuthScreenViewModel, email: String, state: MutableState<Boolean>) {

    var isError by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = email,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .onFocusChanged {
                if (email.isNotBlank()) {
                    isError = !ValidatorUtils.validEmailAddress(email)
                    viewModel
                        .doesEmailExist(email)
                        .addOnCompleteListener {
                            viewModel.setIsNewUser(it.result.signInMethods.isNullOrEmpty())
                        }
                }
            },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email Icon"
            )
        },
        isError = isError,
        label = { Text(text = stringResource(R.string.email), color = Color.Gray) },
        onValueChange = {
            viewModel.setEmailAddress(it)
            state.value = email.isNotBlank()
        },
        supportingText = {
             if (isError) {
                 Text(
                     modifier = Modifier.fillMaxWidth(),
                     text = stringResource(R.string.email_error),
                     color = MaterialTheme.colorScheme.error
                 )
             }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            errorTextColor = MaterialTheme.colorScheme.error,
            errorContainerColor = Color.Transparent
        )
    )
}

@Composable
fun WelcomeMessageText(viewModel: UserAuthScreenViewModel) {

    val userAuthErrorMessage by viewModel.userAuthErrorMessage.observeAsState("")
    val userAuthError by viewModel.userAuthError.observeAsState(false)

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.welcome_message_1),
            textAlign = TextAlign.Center,
            style = Typography.labelLarge,
        )

        Text(
            text = stringResource(R.string.welcome_message_2),
            modifier = Modifier.padding(0.dp, 20.dp),
            textAlign = TextAlign.Center,
            style = Typography.labelLarge,
        )

        Text(
            text = stringResource(R.string.start_message),
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 20.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = Typography.bodyLarge
        )

        if (userAuthError) {
            Text(
                text = userAuthErrorMessage,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = Typography.labelSmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun UserAuthButton(
    context: Context,
    state: MutableState<Boolean>,
    viewModel: UserAuthScreenViewModel,
    email: String,
    password: String,
    isNewUser: Boolean,
    navController: NavController
) {

    AnimatedVisibility(visible = state.value) {
        Button(
            onClick = { userAuth(context, viewModel, email, password, isNewUser, navController) },
            elevation = ButtonDefaults.buttonElevation(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowRight,
                contentDescription = "User Authentication Button"
            )
        }
    }
}

private fun userAuth(
    context: Context,
    viewModel: UserAuthScreenViewModel,
    email: String,
    password: String,
    isNewUser: Boolean,
    navController: NavController
) {
    if (email.isEmpty() || password.isEmpty()) {
        viewModel.setUserAuthErrorMessage(context.getString(R.string.empty_credentials_error))
        viewModel.setUserAuthError(true)
    } else {
        viewModel.userAuthResult(email, password)
            .addOnCompleteListener { auth ->
                if (auth.isSuccessful) {
                    viewModel.setUserAuthError(false)
                    navController.navigate("home/${auth.result.user!!.uid}/$isNewUser")
                } else {
                    viewModel.setUserAuthErrorMessage(context.getString(R.string.wrong_credentials_error))
                    viewModel.setUserAuthError(true)
                }
            }
    }
}