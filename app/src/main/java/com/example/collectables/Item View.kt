package com.example.collectables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

//These three functions are used throughout the app to determine name of current item
var itemName = ""

fun assignItemName(name: String) {
    itemName = name
}

fun accessItemName(): String {
    return itemName
}

//UI for view of items
@Composable
fun ItemView(navController: NavHostController) {
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
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .height(64.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "Go Back",
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
            //Search bar not added
            /*
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

             */
            Spacer(modifier = Modifier.size(10.dp))
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.size(10.dp))
            DisplayDocumentNames(userId =(accessUserName()), collectionName =(accessCollectionName()), navController = navController)

        }
    }
}

// Gets names of users items in collection and displays them on cards
@Composable
fun DisplayDocumentNames(
    userId: String,
    collectionName: String,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val db = FirebaseFirestore.getInstance()



    // holds item names
    var documentNames by remember { mutableStateOf<List<String>>(emptyList()) }
    //runs a query to grab names
    LaunchedEffect(userId, collectionName) {
        try {
            delay(1000)
            val querySnapshot = db.collection("users")
                .document(userId)
                .collection(collectionName)
                .get()
                .await()

            // grab names from query
            val names = querySnapshot.documents
                .filter { document -> document.id != "collection_rules" } // Filter out collection rules which holds fields for items in collection
                .map { document ->
                    document.id
                }

            // Update documentNames with item names
            documentNames = names
        } catch (e: Exception) {
            // handles errors
            e.printStackTrace()
        }
    }


    // Display items in collection
    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(documentNames) { name ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                onClick = {
                    navController.navigate(Routes.ViewInItem.route)
                    assignItemName(name)
                },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = name,
                    modifier = Modifier
                        .padding(16.dp)
                        .wrapContentWidth()
                )
            }
        }
    }
}


//Previews
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