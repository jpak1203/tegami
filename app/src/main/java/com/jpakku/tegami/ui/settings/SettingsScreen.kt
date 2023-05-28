package com.jpakku.tegami.ui.settings

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController

@Composable
fun SettingsScreen(onSignOut: () -> Unit) {

    val viewModel = hiltViewModel<SettingsScreenViewModel>()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.padding(20.dp),
            text = "Settings",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Profile(viewModel)
        AccountSettings()
        AppSettings()
        LogoutButton(viewModel, onSignOut)
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    val navController = rememberNavController()
    Surface {
        SettingsScreen {
            navController.navigate("splash") {
                popUpTo(navController.graph.id) { inclusive = true }
            }
        }
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
fun AccountSettings() {
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 20.dp, 0.dp, 0.dp),
            text = "User Settings",
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
                    imageVector = Icons.Filled.Password,
                    contentDescription = "Change Password Icon"
                )
                Text(
                    text = "Change Password",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Icon(
                imageVector = Icons.Filled.ArrowRight,
                contentDescription = "Change Password"
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
                    imageVector = Icons.Filled.Brush,
                    contentDescription = "Change Theme Icon"
                )
                Text(
                    text = "Change Theme",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Icon(
                imageVector = Icons.Filled.ArrowRight,
                contentDescription = "Change Theme"
            )
        }
    }
}

@Composable
fun AppSettings() {
    Column {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 20.dp, 0.dp, 0.dp),
            text = "App Settings",
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
                    contentDescription = "Dark Mode Icon"
                )
                Text(
                    text = "Dark Mode",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Switch(
                checked = false,
                onCheckedChange = {}
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
                    contentDescription = "Notifications Icon"
                )
                Text(
                    text = "Notifications",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Switch(
                checked = false,
                onCheckedChange = {}
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
                    imageVector = Icons.Filled.List,
                    contentDescription = "Rules Icon"
                )
                Text(
                    text = "Rules",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Icon(
                imageVector = Icons.Filled.ArrowRight,
                contentDescription = "Rules"
            )
        }
    }
}

@Composable
fun LogoutButton(viewModel: SettingsScreenViewModel, onSignOut: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxSize().padding(40.dp),
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
                text = "Log out".uppercase(),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}