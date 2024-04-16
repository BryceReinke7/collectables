package com.example.collectables

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

//Function for logging in
fun loginUser(navController: NavHostController, email: String, password: String) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Login successful
                val user = auth.currentUser
                Log.d("Login", "signInWithEmail:success")

                navController.navigate(Routes.Collections.route)
            } else {
                // Login failed
                Log.w("Login", "signInWithEmail:failure", task.exception)
            }
        }
}
//These next three functions are used throughout the app for seeing who is currently logged in
var userName = ""

fun assignUserName(user: String) {
    userName = user
}

fun accessUserName(): String {
    return userName
}

//UI for login page
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
                        label = { Text("Email") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    TextField(
                        value = password,
                        onValueChange = {password = it},
                        label = { Text("Password") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Button(onClick = {
                        when (password) {
                            "" -> loginUser(navController = navController, " ", " ")
                            else -> {
                                loginUser(navController = navController, email, password)
                                assignUserName(email)
                            }
                        }
                    }) {
                        Text(
                            text = "Log In",
                            style = MaterialTheme.typography.displayMedium,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }
        Spacer(modifier = Modifier.padding(innerPadding))
    }
}

