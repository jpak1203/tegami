package com.jpakku.tegami.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jpakku.tegami.R
import com.jpakku.tegami.activities.MainActivityViewModel
import com.jpakku.tegami.util.DataStoreUtil
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(dataStoreUtil: DataStoreUtil,
                   mainActivityViewModel: MainActivityViewModel,
                   onChangePassword: () -> Unit,
                   onChangeTheme: () -> Unit,
                   onNavigateToRulesScreen: () -> Unit,
                   onSignOut: () -> Unit
) {

    val viewModel = hiltViewModel<SettingsScreenViewModel>()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(20.dp),
            text = stringResource(R.string.settings_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Profile(viewModel)
        AccountSettings(onChangePassword, onChangeTheme)
        AppSettings(dataStoreUtil, mainActivityViewModel, onNavigateToRulesScreen)
        LogoutButton(viewModel, onSignOut)
    }
}

@Composable
fun Profile(viewModel: SettingsScreenViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = viewModel.getEmail() ?: "",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun AccountSettings(onChangePassword: () -> Unit, onChangeTheme: () -> Unit) {
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 20.dp, 0.dp, 0.dp),
            text = stringResource(R.string.user_settings),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 20.dp, 0.dp)
                .clickable {
                    onChangePassword()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.padding(20.dp, 10.dp),
            ) {
                Icon(
                    modifier = Modifier.padding(20.dp, 0.dp),
                    imageVector = Icons.Filled.Password,
                    contentDescription = stringResource(R.string.change_password_icon_content)
                )
                Text(
                    text = stringResource(R.string.change_password),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Icon(
                imageVector = Icons.Filled.ArrowRight,
                contentDescription = stringResource(R.string.change_password)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 20.dp, 0.dp)
                .clickable {
                    onChangeTheme()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.padding(20.dp, 10.dp),
            ) {
                Icon(
                    modifier = Modifier.padding(20.dp, 0.dp),
                    imageVector = Icons.Filled.Brush,
                    contentDescription = stringResource(R.string.change_theme_icon_content)
                )
                Text(
                    text = stringResource(R.string.change_theme),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Icon(
                imageVector = Icons.Filled.ArrowRight,
                contentDescription = stringResource(R.string.change_theme)
            )
        }
    }
}

@Composable
fun AppSettings(dataStoreUtil: DataStoreUtil,
                mainActivityViewModel: MainActivityViewModel,
                onNavigateToRulesScreen: () -> Unit) {
    var switchState by remember {mainActivityViewModel.isDarkThemeEnabled }
    val coroutineScope = rememberCoroutineScope()

    Column {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 20.dp, 0.dp, 0.dp),
            text = stringResource(R.string.app_settings),
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 20.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.padding(20.dp, 10.dp),
            ) {
                Icon(
                    modifier = Modifier.padding(20.dp, 0.dp),
                    imageVector = Icons.Filled.DarkMode,
                    contentDescription = stringResource(R.string.dark_mode_icon_content)
                )
                Text(
                    text = stringResource(R.string.dark_mode),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Switch(
                checked = switchState,
                onCheckedChange = {
                    switchState = it

                    coroutineScope.launch {
                        dataStoreUtil.saveTheme(it)
                    }
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 20.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.padding(20.dp, 10.dp),
            ) {
                Icon(
                    modifier = Modifier.padding(20.dp, 0.dp),
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = stringResource(R.string.notifications_icon_content)
                )
                Text(
                    text = stringResource(R.string.notifications),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Switch(
                checked = false,
                onCheckedChange = {
                    // TODO: add notification
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 20.dp, 0.dp)
                .clickable{
                    onNavigateToRulesScreen()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.padding(20.dp, 10.dp),
            ) {
                Icon(
                    modifier = Modifier.padding(20.dp, 0.dp),
                    imageVector = Icons.Filled.List,
                    contentDescription = stringResource(R.string.rules_icon_content)
                )
                Text(
                    text = stringResource(id = R.string.rules),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Icon(
                imageVector = Icons.Filled.ArrowRight,
                contentDescription = stringResource(id = R.string.rules)
            )
        }
    }
}

@Composable
fun LogoutButton(viewModel: SettingsScreenViewModel, onSignOut: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        Button(
            onClick = {
                viewModel.logOut()
                onSignOut()
            }
        ) {
            Text(
                text = stringResource(R.string.log_out).uppercase(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}