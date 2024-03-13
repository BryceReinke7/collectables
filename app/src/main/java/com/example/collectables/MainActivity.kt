package com.example.collectables

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.collectables.ui.theme.CollectablesTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            CollectablesTheme {
                 //A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OpeningPage()
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectTopBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Collectables",
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }
    )
}


@Composable
fun LogInSignIn(modifier: Modifier = Modifier) {
    Column (

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()

    ) {
        //Log in button
        Card(modifier = Modifier) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
                //.background(Color.Green)
                //.border(border = BorderStroke(width = 2.dp, Color.Red))
            ) {
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
        Card(modifier = Modifier) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
                //.background(Color.Green)
                //.border(border = BorderStroke(width = 2.dp, Color.Red))
            ) {
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

@Composable
fun OpeningPage(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CollectTopBar()
        }
    ) { thisdoesnothingbutneedstobehere ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()

        ) {
            LogInSignIn()
        }
    }
}








/*
@Preview()
@Composable
fun HomePreview() {
    CollectablesTheme {
        OpeningPage()
    }
}

@Preview
@Composable
fun HomePreviewDark() {
    CollectablesTheme(darkTheme = true) {
        OpeningPage()
    }
}

*/