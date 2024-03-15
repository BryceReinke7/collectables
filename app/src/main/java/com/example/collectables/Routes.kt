package com.example.collectables

sealed class Routes (val route: String) {
    object Home : Routes("home")
    object  SignUp : Routes("signup")
}