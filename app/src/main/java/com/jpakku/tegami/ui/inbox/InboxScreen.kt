package com.jpakku.tegami.ui.inbox

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jpakku.tegami.R

@Composable
fun InboxScreen(onNavigateToReadLetterScreen: (String) -> Unit) {
    val viewModel = hiltViewModel<InboxScreenViewModel>()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(20.dp),
            text = "Inbox",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        LetterList(viewModel, onNavigateToReadLetterScreen)
    }
}

@Composable
fun LetterList(viewModel: InboxScreenViewModel, onNavigateToReadLetterScreen: (String) -> Unit) {
    val messages by viewModel.messages.observeAsState(mutableMapOf())

    if (messages.isEmpty()) {
        viewModel.getMessages()
        LetterLoading()
    } else {
        for (from in messages.keys) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 15.dp)
                    .clickable {
                        onNavigateToReadLetterScreen(from)
                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val lastMessage = messages[from]?.get(messages[from]?.size?.minus(1) ?: 0)
                Text(
                    text = lastMessage?.get("subject") as String,
                    fontWeight = if (lastMessage.get("read") as Boolean) FontWeight.Normal else FontWeight.Bold
                )

                if (!(lastMessage.get("read") as Boolean)) {
                    Icon(
                        imageVector = Icons.Filled.Mail,
                        contentDescription = stringResource(R.string.menu_icon_content)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.MarkEmailRead,
                        contentDescription = stringResource(R.string.menu_icon_content)
                    )
                }
            }
            Divider(color = Color.LightGray)
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
                    color = Color.LightGray,
                    radius = size.width / 2 - strokeWidth.toPx() / 2,
                    style = Stroke(strokeWidth.toPx())
                )
            },
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = strokeWidth
        )
    }
}