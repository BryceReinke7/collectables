@file:Suppress("UNCHECKED_CAST")

package com.example.collectables

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
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.collectables.ui.theme.CollectablesTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun ViewInItemView(navController: NavHostController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CollectTopBar()
        }
    ) { innerPadding ->

        //Firebase variables
        val db = Firebase.firestore
        val userId = accessUserName()
        val collectionName = accessCollectionName()
        val itemName = accessItemName()

        var items by remember { mutableStateOf<List<String>>(emptyList()) }

        DisposableEffect(key1 = userId) {
            getCollectionItemsFromFirestore(
                db = db,
                userId = userId,
                collectionName = collectionName,
                itemName = itemName,
                onSuccess = { item ->
                    items = item
                },
                onFailure = { e ->
                    Log.e("TAG", "Failed to fetch collection rules", e)
                }
            )
            onDispose { }
        }

        var collectionRules by remember { mutableStateOf<List<String>>(emptyList()) }

        // Fetch collection rules from Firestore
        DisposableEffect(key1 = userId) {
            getCollectionRulesFromFirestore(
                db = db,
                userId = userId,
                collectionName = collectionName,
                onSuccess = { rules ->
                    collectionRules = rules
                },
                onFailure = { e ->
                    Log.e("TAG", "Failed to fetch collection rules", e)
                }
            )

            onDispose { }
        }

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
                fontSize = 50.sp,
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
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.size(10.dp))
            //Goes through all fields and creates text for each
            collectionRules.forEachIndexed { index, rule ->
                Text(
                    text = rule,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = items[index],
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.size(10.dp))
            }

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
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

//Fetches data from firestore
fun getCollectionItemsFromFirestore(
    db: FirebaseFirestore,
    userId: String,
    collectionName: String,
    itemName: String,
    onSuccess: (List<String>) -> Unit,
    onFailure: (Exception) -> Unit
) {
    db.collection("users")
        .document(userId)
        .collection(collectionName)
        .document(itemName)
        .get()
        .addOnSuccessListener { document ->
            val details = document.get("details") as? List<String>
            if (details != null) {
                onSuccess(details)
            } else {
                onFailure(Exception("No fields array found"))
            }
        }
        .addOnFailureListener { e ->
            onFailure(e)
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