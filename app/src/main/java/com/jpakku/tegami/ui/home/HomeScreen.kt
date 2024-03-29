package com.jpakku.tegami.ui.home

import android.content.Context
import android.os.Build
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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.jpakku.tegami.R

@Composable
fun HomeScreen(
    newUser: Boolean?,
    onNavigateToWriteLetterScreen: (String?) -> Unit,
    onNavigateToInboxScreen: () -> Unit,
    onNavigateToSettingsScreen: () -> Unit
) {
    val context = LocalContext.current
    val viewModel = hiltViewModel<HomeScreenViewModel>()
    val showDialog by viewModel.showFirstTimeUserDialog.observeAsState(newUser ?: true)
    val showRulesDialog by viewModel.showRulesDialog.observeAsState(false)

    if(showDialog) {
        FirstTimeUserPopup(setShowDialog = {
            viewModel.showFirstTimeUserDialog(it)
            viewModel.showRulesDialog(!it)
        })
    }

    if (showRulesDialog) {
        RulesPopup(showRulesDialog = {
            viewModel.showRulesDialog(it)
        })
    }

    IconBar(onNavigateToSettingsScreen, onNavigateToInboxScreen)
    RepeatingGraphic(context)
    WriteALetterButton(onNavigateToWriteLetterScreen)
}

@Preview
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    Surface {
        HomeScreen(
            false,
            onNavigateToWriteLetterScreen = { navController.navigate("write-letter") },
            onNavigateToInboxScreen = { navController.navigate("inbox") },
            onNavigateToSettingsScreen = { navController.navigate("settings") }
        )
    }
}

@Composable
fun IconBar(onNavigateToSettingsScreen: () -> Unit, onNavigateToInboxScreen: () -> Unit) {
    val contextForToast = LocalContext.current.applicationContext

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val openDialog = remember { mutableStateOf(false) }
        val buttonIcon = remember {
            mutableStateOf(Icons.Default.Menu)
        }

        IconButton(
            modifier = Modifier.padding(40.dp),
            onClick = {
                onNavigateToSettingsScreen()
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = stringResource(R.string.menu_icon_content)
            )
        }

        IconButton(
            modifier = Modifier.padding(40.dp),
            onClick = {
                onNavigateToInboxScreen()
            }
        ) {
            Icon(
                imageVector = Icons.Filled.MarkunreadMailbox,
                contentDescription = stringResource(R.string.menu_icon_content)
            )
        }
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
fun WriteALetterButton(onNavigateToWriteLetterScreen: (String?) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ExtendedFloatingActionButton(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 50.dp)
                .align(alignment = Alignment.BottomCenter),
            onClick = {
                onNavigateToWriteLetterScreen(null)
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
                    modifier = Modifier
                        .padding(20.dp)
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
                            text = "${stringResource(R.string.rule_1_title)}: ${stringResource(R.string.rule_1_body)}",
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            modifier = Modifier.padding(0.dp, 10.dp),
                            text = "${stringResource(R.string.rule_2_title)}: ${stringResource(R.string.rule_2_body)}",
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            modifier = Modifier.padding(0.dp, 10.dp),
                            text = "${stringResource(R.string.rule_3_title)}: ${stringResource(R.string.rule_3_body)}",
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            modifier = Modifier.padding(0.dp, 10.dp),
                            text = "${stringResource(R.string.rule_4_title)}: ${stringResource(R.string.rule_4_body)}",
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            modifier = Modifier.padding(0.dp, 10.dp),
                            text = "${stringResource(R.string.rule_5_title)}: ${stringResource(R.string.rule_5_body)}",
                            style = MaterialTheme.typography.bodySmall
                        )


                        Text(
                            modifier = Modifier.padding(0.dp, 10.dp),
                            text = "${stringResource(R.string.rule_6_title)}: ${stringResource(R.string.rule_6_body)}",
                            style = MaterialTheme.typography.bodySmall
                        )

                        Text(
                            modifier = Modifier.padding(0.dp, 10.dp),
                            text = "${stringResource(R.string.rule_7_title)}: ${stringResource(R.string.rule_7_body)}",
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