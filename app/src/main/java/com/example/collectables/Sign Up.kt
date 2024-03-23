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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ComponentActivity
import androidx.navigation.NavHostController
import com.example.collectables.ui.theme.CollectablesTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


val auth: FirebaseAuth = FirebaseAuth.getInstance()

fun registerUser(email: String, password: String) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Registration successful
                val user = auth.currentUser
            } else {
                // Registration failed
                Log.w("Registration", "createUserWithEmail:failure", task.exception)
            }
        }
}


@Composable
fun SignUp(navController: NavHostController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CollectTopBar()
        }
    ) { innerPadding ->
        //val auth = Firebase.auth
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
                        text = "Sign Up",
                        style = MaterialTheme.typography.displayLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.size(100.dp))
                    TextField(
                        value = email,
                        onValueChange = {email = it},
                        label = { Text("Email")},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.size(16.dp))
                    TextField(
                        value = password,
                        onValueChange = {password = it},
                        label = { Text("Password (at least 6 characters)")},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Button(onClick = {
                        registerUser(email, password)
                        
                    }) {
                        Text(
                            text = "Create Account",
                            style = MaterialTheme.typography.displayMedium,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Button(onClick = {
                        navController.navigate(Routes.LogIn.route)
                    }) {
                        Text(
                            text = "Go to Login Page",
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

