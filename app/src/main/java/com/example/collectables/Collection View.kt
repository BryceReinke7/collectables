package com.example.collectables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.collectables.ui.theme.CollectablesTheme


@Composable
fun CollectView(modifier: Modifier = Modifier) {
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
                        onClick = { /*TODO*/ },
                        modifier = Modifier.height(64.dp)
                    ) {
                        Text(
                            text = "Add Collection",
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
                        text = "My Collections",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold
                        )
                }
                Column(
                    modifier = Modifier
                        .weight(3f)
                ){
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.height(64.dp)
                    ) {
                        Text(
                            text = "Templates",
                            style = MaterialTheme.typography.displaySmall,
                            modifier = Modifier,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                modifier = Modifier
            ) {
                var search by remember { mutableStateOf("") }

                OutlinedTextField(
                    modifier = Modifier.weight(2f),
                    value = search,
                    onValueChange = {search = it},
                    label = { Text("Search") }
                )
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors( MaterialTheme.colorScheme.secondary),
                    modifier = Modifier
                        .height(64.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "Sort",
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}

@Composable
fun TestPreview() {
    CollectablesTheme {
        CollectView()
    }
}

@Preview
@Composable
fun PreviewTest() {
    TestPreview()
}