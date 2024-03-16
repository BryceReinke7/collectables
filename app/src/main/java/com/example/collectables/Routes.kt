package com.example.collectables

sealed class Routes (val route: String) {
    object Home : Routes("home")
    object SignUp : Routes("signup")
    object LogIn : Routes("login")
    object Collections : Routes("collect")
}