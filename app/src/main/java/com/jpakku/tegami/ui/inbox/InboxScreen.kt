package com.jpakku.tegami.ui.inbox

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import timber.log.Timber

@Composable
fun InboxScreen(onNavigateToWriteLetterScreen: (String?) -> Unit) {
    val viewModel = hiltViewModel<InboxViewModel>()
    viewModel.getMessages()
    
    LetterList(viewModel)
}

@Composable
fun LetterList(viewModel: InboxViewModel) {
    val messages by viewModel.messages.observeAsState(mutableMapOf())

    Timber.i("${messages.size}")
    if (messages.isEmpty()) {
        LetterLoading()
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp)
        ) {
            for (from in messages.keys) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp)
                ) {
                    val lastMessage = messages[from]?.get(messages[from]?.size?.minus(1) ?: 0)
                    Text(
                        lastMessage?.get("subject") as String
                    )
                }
            }
        }
    }
}

@Composable
fun LetterLoading() {
    val strokeWidth = 5.dp

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.drawBehind {
                drawCircle(
                    Color.Gray,
                    radius = size.width / 2 - strokeWidth.toPx() / 2,
                    style = Stroke(strokeWidth.toPx())
                )
            },
            color = Color.LightGray,
            strokeWidth = strokeWidth
        )
    }
}