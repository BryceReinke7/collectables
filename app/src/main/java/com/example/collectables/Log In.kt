package com.example.collectables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun LogIn(navController: NavHostController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CollectTopBar()
        }
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()

        ) {
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            Column(
            ) {
                Column (
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight()
                )
                {
                    Text(
                        text = "Log In",
                        style = MaterialTheme.typography.displayLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.size(100.dp))
                    TextField(
                        value = email,
                        onValueChange = {email = it},
                        label = { Text("Email") }
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    TextField(
                        value = password,
                        onValueChange = {password = it},
                        label = { Text("Password") }
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Button(onClick = { navController.navigate(Routes.Collections.route) }) {
                        Text(
                            text = "Log In",
                            style = MaterialTheme.typography.displayMedium,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EnterLoginInfo(modifier: Modifier = Modifier) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
        )
        {
            Text(
                text = "Log In",
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.size(100.dp))
            TextField(
                value = email,
                onValueChange = {email = it},
                label = { Text("Email") }
            )
            Spacer(modifier = Modifier.size(16.dp))
            TextField(
                value = password,
                onValueChange = {password = it},
                label = { Text("Password") }
            )
            Spacer(modifier = Modifier.size(16.dp))
            Button(onClick = { }) {
                Text(
                    text = "Log In",
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier
                        .padding(10.dp)
                )
            }
        }
    }
}

