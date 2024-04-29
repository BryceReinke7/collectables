@file:Suppress("UNCHECKED_CAST")

package com.example.collectables

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.collectables.ui.theme.CollectablesTheme
import com.google.firebase.appcheck.internal.util.Logger.TAG
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



// UI for page
@Composable
fun CreateItemView(navController: NavHostController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CollectTopBar()
        }
    ) { innerPadding ->
        val db = Firebase.firestore
        val userId = accessUserName()
        val collectionName = accessCollectionName()

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

        //for input fields
        var name by remember { mutableStateOf("") }
        val fieldValues = remember { mutableStateListOf<String>() }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            Row(modifier = Modifier) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "New Item", style = MaterialTheme.typography.displayLarge)
                    Spacer(modifier = Modifier.size(10.dp))
                    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row(modifier = Modifier) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Item Name") }
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            Button(
                onClick = { Log.d("TAG", "TESTING $collectionRules") },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                modifier = Modifier
            ) {
                Text(
                    text = "Add Image",
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.size(10.dp))
            // Display collection rules
            collectionRules.forEachIndexed { index, rule ->
                OutlinedTextField(
                    value = fieldValues.getOrNull(index) ?: "",
                    onValueChange = { newValue ->
                        fieldValues.getOrNull(index)?.let { fieldValues[index] = newValue } ?: run {
                            fieldValues.add(newValue)
                        }
                    },
                    label = { Text(rule) }
                )
                Spacer(modifier = Modifier.size(10.dp))
            }

            Button(
                onClick = {
                    // Call the saveItemToFirestore function
                    saveItemToFirestore(
                        db = db,
                        userId = userId,
                        collectionName = collectionName,
                        itemName = name,
                        fieldValues = fieldValues,
                        onSuccess = {
                            Log.d("TAG", "Item saved successfully")
                            navController.popBackStack()
                        },
                        onFailure = { e ->
                            Log.e("TAG", "Failed to save item", e)
                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                modifier = Modifier
            ) {
                Text(
                    text = "Save",
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

//Grabs data from firestore
//ChatGPT with modifications from me
fun getCollectionRulesFromFirestore(
    db: FirebaseFirestore,
    userId: String,
    collectionName: String,
    onSuccess: (List<String>) -> Unit,
    onFailure: (Exception) -> Unit
) {
    db.collection("users")
        .document(userId)
        .collection(collectionName)
        .document("collection_rules")
        .get()
        .addOnSuccessListener { document ->
            val fields = document.get("fields") as? List<String>
            if (fields != null) {
                onSuccess(fields)
            } else {
                onFailure(Exception("No fields array found"))
            }
        }
        .addOnFailureListener { e ->
            onFailure(e)
        }
}

// Function to save item data to Firestore
fun saveItemToFirestore(
    db: FirebaseFirestore,
    userId: String,
    collectionName: String,
    itemName: String,
    fieldValues: List<String>,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    // Create a map to hold the item data
    val itemData = hashMapOf<String, Any>()

    // Add the field values to the item data
    for ((index, value) in fieldValues.withIndex()) {
        itemData["field${index + 1}"] = value
    }

    // Add the item data to Firestore with the itemName as document ID
    db.collection("users")
        .document(userId)
        .collection(collectionName)
        .document(itemName) // Set document ID as itemName
        .set(itemData) // Use set() instead of add()
        .addOnSuccessListener {
            onSuccess()
        }
        .addOnFailureListener { e ->
            onFailure(e)
        }
}










//Previews

@Composable
fun TestPreviewCItem() {
    CollectablesTheme {
        CreateItemView(rememberNavController())
    }
}

@Preview
@Composable
fun PreviewTestCItem() {
    TestPreviewCItem()
}