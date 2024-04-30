package com.example.collectables.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.collectables.CollectTopBar
import com.example.collectables.accessCollectionName
import com.example.collectables.accessItemName
import com.example.collectables.saveItemToFirestore

@Composable
fun ViewInItemView(navController: NavHostController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CollectTopBar()
        }
    ) { innerPadding ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = accessCollectionName(),
                style = MaterialTheme.typography.displayLarge,
                modifier = modifier

            )
            Spacer(modifier = Modifier.size(10.dp))
            Card(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = accessItemName(),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Detail",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Detail",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .padding(16.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Button(
                onClick = {
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                modifier = Modifier
            ) {
                Text(
                    text = "Go Back",
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Center
                )
            }



        }
    }
}

//Previews
@Composable
fun TestPreview31() {
    CollectablesTheme {
        ViewInItemView(rememberNavController())
    }
}

@Preview
@Composable
fun PreviewTest32() {
    TestPreview31()
}