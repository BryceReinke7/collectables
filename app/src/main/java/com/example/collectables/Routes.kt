package com.example.collectables

sealed class Routes (val route: String) {
    data object Home : Routes("home")
    data object SignUp : Routes("signup")
    data object LogIn : Routes("login")
    data object Collections : Routes("collect")
    data object CreateCollection : Routes("createcollect")
    data object ItemView : Routes("itemview")
    data object CreateItem : Routes("createitem")
    data object ViewInItem : Routes( "viewinitem")
}