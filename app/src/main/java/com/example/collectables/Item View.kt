package com.example.collectables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.collectables.ui.theme.CollectablesTheme

@Composable
fun ItemView(navController: NavHostController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CollectTopBar()
        }

    ) { innerPadding ->

        Column(modifier = Modifier.padding(innerPadding)) {
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                modifier = Modifier
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(3f)
                ){
                    Button(
                        onClick = { navController.navigate(Routes.CreateItem.route) },
                        modifier = Modifier
                            .height(64.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Add Item",
                            style = MaterialTheme.typography.displaySmall,
                            modifier = Modifier,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(4f)

                ) {
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = accessCollectionName(),
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(3f)
                ){
                    Button(
                        onClick = {  },
                        modifier = Modifier
                            .height(64.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Sort",
                            style = MaterialTheme.typography.displaySmall,
                            modifier = Modifier,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                modifier = Modifier
            ) {
                var search by remember { mutableStateOf("") }

                OutlinedTextField(
                    modifier = Modifier.weight(3f),
                    value = search,
                    onValueChange = {search = it},
                    label = { Text("Search") }
                )
                Button(
                    onClick = {  },
                    colors = ButtonDefaults.buttonColors( MaterialTheme.colorScheme.secondary),
                    modifier = Modifier
                        .height(64.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "Search",
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.size(10.dp))

        }
    }
}

@Composable
fun TestPreviewItem() {
    CollectablesTheme {
        ItemView(rememberNavController())
    }
}

@Preview
@Composable
fun PreviewTestItem() {
    TestPreviewItem()
}