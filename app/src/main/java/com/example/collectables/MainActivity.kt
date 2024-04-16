package com.example.collectables

import android.graphics.Color.parseColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.collectables.ui.theme.CollectablesTheme
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {

            val navController = rememberNavController()

            CollectablesTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //This defines paths for pages in app
                    NavHost(navController = navController, startDestination = Routes.Home.route) {
                        composable(Routes.Home.route) { OpeningPage(navController = navController) }
                        composable(Routes.SignUp.route) { SignUp(navController = navController) }
                        composable(Routes.LogIn.route) { LogIn(navController = navController) }
                        composable(Routes.Collections.route) { CollectView(navController = navController) }
                        composable(Routes.CreateCollection.route) { CreateCollectionView(navController = navController) }
                        composable(Routes.ItemView.route) { ItemView(navController = navController)}
                        composable(Routes.CreateItem.route) { CreateItemView(navController = navController) }
                    }

                }
            }
        }
    }
}






@OptIn(ExperimentalMaterial3Api::class)
@Composable
//Top bar of app
fun CollectTopBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceTint,
        ),
        title = {

            Image(
                //Icon from FreePik.com made by Vitaly Gorbachev
                painter = painterResource(id = R.drawable.name_crop),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
            )
        }
    )
}

@Composable
//Sets up opening page
fun OpeningPage(navController: NavHostController, modifier: Modifier = Modifier) {
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
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                //Log in button
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    Button(onClick = {navController.navigate(Routes.LogIn.route)}) {
                        Text(
                            text = "Log In",
                            style = MaterialTheme.typography.displayMedium,
                            modifier = Modifier
                                .padding(10.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
                //Sign Up button
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                ) {
                    Button(onClick = {navController.navigate(Routes.SignUp.route) }) {
                        Text(
                            text = "Sign Up",
                            style = MaterialTheme.typography.displayMedium,
                            modifier = Modifier
                                .padding(10.dp)

                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(innerPadding))
    }
}







@Preview()
@Composable
fun HomePreview() {
    CollectablesTheme {
        OpeningPage(rememberNavController())
    }
}



