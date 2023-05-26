package com.jpakku.tegami.ui.letter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController

@Composable
fun WriteLetterScreen(writeTo: String?, onNavigateToHomeScreen: () -> Unit) {
    val viewModel = hiltViewModel<WriteLetterScreenViewModel>()

    Column {
        MessageSubject(viewModel)
        MessageBody(viewModel)
    }
    SendButton(viewModel, writeTo, onNavigateToHomeScreen)
}

@Preview
@Composable
fun WriteLetterScreenPreview() {
    val navController = rememberNavController()
    Surface {
        WriteLetterScreen(
            null,
            onNavigateToHomeScreen = { navController.navigate("home/false") {
                popUpTo(navController.graph.id) { inclusive = true } }
            }
        )
    }
}

@Composable
fun MessageSubject(viewModel: WriteLetterScreenViewModel) {
    val messageSubject by viewModel.messageSubject.observeAsState("")

    OutlinedTextField(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        value = messageSubject,
        onValueChange =  { viewModel.setMessageSubject(it) },
        singleLine = true,
        label = { Text(text = "Subject Line", color = Color.Gray) },
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
fun MessageBody(viewModel: WriteLetterScreenViewModel) {
    val messageBody by viewModel.messageBody.observeAsState("")

    OutlinedTextField(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        value = messageBody,
        onValueChange =  { viewModel.setMessageBody(it) },
        label = { Text(text = "Message Body", color = Color.Gray) },
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
fun SendButton(viewModel: WriteLetterScreenViewModel, writeTo: String?, onNavigateToHomeScreen: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        FloatingActionButton(
            modifier = Modifier
                .padding(25.dp)
                .align(alignment = Alignment.BottomEnd),
            onClick = {
                viewModel.sendMessage(writeTo)
                onNavigateToHomeScreen()
            },
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.tertiary,
            content = { Icon(
                imageVector = Icons.Filled.Send,
                contentDescription = "Send Letter"
            ) }
        )
    }
}
