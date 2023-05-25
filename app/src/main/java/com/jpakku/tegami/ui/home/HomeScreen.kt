package com.jpakku.tegami.ui.home

import android.content.Context
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MarkunreadMailbox
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.jpakku.tegami.R

@Composable
fun HomeScreen(userId: String?, newUser: Boolean?) {
    val context = LocalContext.current
    val showDialog =  remember { mutableStateOf(newUser ?: true) }
    val showRulesDialog =  remember { mutableStateOf(false) }

    if(showDialog.value) {
        FirstTimeUserPopup(setShowDialog = {
            showDialog.value = it
            showRulesDialog.value = !it
        })
    }

    if (showRulesDialog.value) {
        RulesPopup(showRulesDialog = {
            showRulesDialog.value = it
        })
    }

    IconBar()
    RepeatingGraphic(context)
    WriteALetterButton()
}

@Preview
@Composable
fun HomeScreenPreview() {
    Surface {
        HomeScreen("", false)
    }
}

@Composable
fun IconBar() {
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
            contentDescription = stringResource(R.string.menu_icon_content)
        )
        Icon(
            modifier = Modifier
                .padding(40.dp)
                .clickable {
                    //TODO: add nav to mailbox
                },
            imageVector = Icons.Default.MarkunreadMailbox,
            contentDescription = stringResource(R.string.mail_icon_content)
        )
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
            contentDescription = stringResource(R.string.writing_a_letter_gif_content)
        )
    }
}

@Composable
fun WriteALetterButton() {
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
            text = { Text(stringResource(R.string.write_a_letter)) },
            icon = { Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(R.string.write_a_letter_fab_content)
            ) }
        )
    }
}
@Composable
fun FirstTimeUserPopup(setShowDialog: (Boolean) -> Unit) {

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Text(
                        text = stringResource(R.string.welcome_to_tegami),
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Column {
                        Text(
                            modifier = Modifier.padding(0.dp, 10.dp),
                            text = stringResource(R.string.first_time_user_message_1),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            modifier = Modifier.padding(0.dp, 10.dp),
                            text = stringResource(R.string.first_time_user_message_2),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            modifier = Modifier.padding(0.dp, 10.dp),
                            text = stringResource(R.string.first_time_user_message_3),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {
                                setShowDialog(false)
                            },
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = stringResource(R.string.next))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RulesPopup(showRulesDialog: (Boolean) -> Unit) {

    Dialog(
        onDismissRequest = { showRulesDialog(false) }
    ) {
        Surface(
            modifier = Modifier.height(500.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    Text(
                        text = stringResource(R.string.rules),
                        style = MaterialTheme.typography.titleSmall
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Column {
                        Text(
                            modifier = Modifier.padding(0.dp, 10.dp),
                            text = stringResource(R.string.rule_1),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            modifier = Modifier.padding(0.dp, 10.dp),
                            text = stringResource(R.string.rule_2),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            modifier = Modifier.padding(0.dp, 10.dp),
                            text = stringResource(R.string.rule_3),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            modifier = Modifier.padding(0.dp, 10.dp),
                            text = stringResource(R.string.rule_4),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            modifier = Modifier.padding(0.dp, 10.dp),
                            text = stringResource(R.string.rule_5),
                            style = MaterialTheme.typography.bodySmall
                        )


                        Text(
                            modifier = Modifier.padding(0.dp, 10.dp),
                            text = stringResource(R.string.rule_6),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            modifier = Modifier.padding(0.dp, 10.dp),
                            text = stringResource(R.string.rule_7),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                        Button(
                            onClick = {
                                showRulesDialog(false)
                            },
                            shape = RoundedCornerShape(50.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = stringResource(R.string.start_writing))
                        }
                    }
                }
            }
        }
    }
}