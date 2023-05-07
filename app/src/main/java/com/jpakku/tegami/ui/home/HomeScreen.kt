package com.jpakku.tegami.ui.home

import android.content.Context
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MarkunreadMailbox
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.jpakku.tegami.R

@Composable
fun HomeScreen(userId: String?, newUser: Boolean?) {

    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier
                .padding(40.dp)
                .clickable {
                   //TODO: add side menu
                },
            imageVector = Icons.Default.Menu,
            contentDescription = "Menu Icon"
        )
        Icon(
            modifier = Modifier
                .padding(40.dp)
                .clickable {
                   //TODO: add nav to mailbox
                },
            imageVector = Icons.Default.MarkunreadMailbox,
            contentDescription = "Mail Icon"
        )
    }
    RepeatingGraphic(context)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ExtendedFloatingActionButton(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 50.dp)
                .align(alignment = Alignment.BottomCenter),
            onClick = {
                //OnClick Method
            },
            containerColor = MaterialTheme.colorScheme.primary,
            text = { Text("Write a Letter") },
            icon = { Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Write a Letter FAB"
            ) }
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    Surface {
        HomeScreen("", false)
    }
}

@Composable
fun RepeatingGraphic(context: Context) {
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = R.drawable.letter_writing, //TODO: replace with custom gif
            imageLoader = imageLoader,
            contentScale = ContentScale.FillBounds,
            contentDescription = "Writing A Letter Gif"
        )
    }
}

