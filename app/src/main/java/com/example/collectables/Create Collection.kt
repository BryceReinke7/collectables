package com.example.collectables

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


//var mainFieldCount = 1

@Composable
fun CreateCollectionView(navController: NavHostController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CollectTopBar()
        }
    ) { innerPadding ->
        val db = Firebase.firestore

        var numberOfFields by remember { mutableStateOf(1) }
        var savedValues by remember { mutableStateOf<List<String>>(emptyList()) }
        var name by remember { mutableStateOf("") }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                modifier = Modifier
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                ){
                    Text(
                        text = "New Collection",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                modifier = Modifier
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                ){
                    OutlinedTextField(
                        value = name,
                        onValueChange = {name = it},
                        label = { Text("Collection Name") }
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors( MaterialTheme.colorScheme.secondary),
                modifier = Modifier
            ) {
                Text(
                    text = "Add Image",
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.size(10.dp))
            CustomizableInputFields(
                navController = navController,
                numberOfFields = numberOfFields,
                onSave = { values ->
                    savedValues = values
                    val userId = accessUserName() // Assuming you have a function to retrieve the current user's ID
                    val collectionName = name
                    saveToFirestore(
                        db = db,
                        userId = userId,
                        collectionName = collectionName,
                        values = values,
                        onSuccess = {
                            // Handle success, such as showing a toast or navigating to another screen
                            Log.d("TAG", "Data saved successfully")
                        },
                        onFailure = { e ->
                            // Handle failure, such as showing an error message
                            Log.e("TAG", "Failed to save data", e)
                        }
                    )
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                ) {
                    Row {
                        Button(
                            onClick = { numberOfFields ++ },
                            colors = ButtonDefaults.buttonColors( MaterialTheme.colorScheme.secondary),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Add another field",
                                style = MaterialTheme.typography.displaySmall,
                                modifier = Modifier,
                                textAlign = TextAlign.Center
                            )
                        }
                        Spacer(modifier = Modifier.size(25.dp))
                        Button(
                            onClick = { if (numberOfFields > 1) numberOfFields -- },
                            colors = ButtonDefaults.buttonColors( MaterialTheme.colorScheme.secondary),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Remove field",
                                style = MaterialTheme.typography.displaySmall,
                                modifier = Modifier,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

//This code is from chatgpt modified by me
@Composable
fun CustomizableInputFields(
    navController: NavHostController,
    numberOfFields: Int,
    onSave: (List<String>) -> Unit
) {
    val textValues = remember { mutableStateListOf<String>() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        repeat(numberOfFields) { index ->
            var textValue by remember { mutableStateOf("") }

            TextField(
                value = textValue,
                onValueChange = { newText ->
                    textValue = newText
                    textValues.getOrNull(index)?.let { textValues[index] = newText } ?: run { textValues.add(newText) }
                },
                //label = { Text("Field $index") }
            )
            Spacer(Modifier.height(8.dp))
        }

        Button(
            onClick = {
                Log.d("TAG", "Values to save: $textValues")
                onSave(textValues)
                navController.popBackStack()
            },
            colors = ButtonDefaults.buttonColors( MaterialTheme.colorScheme.secondary),
            modifier = Modifier
        ) {
            Text(
                text = "Save",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier,
                textAlign = TextAlign.Center
            )
        }
    }
}

//Also from chaptgpt modified by me
fun saveCollectionToFirestore(
    db: FirebaseFirestore,
    userId: String,
    collectionName: String,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    val collectionData = hashMapOf(
        "collectionName" to collectionName
        // You can add more fields as needed
    )

    db.collection("users")
        .document(userId)
        .collection("collections")
        .add(collectionData)
        .addOnSuccessListener { documentReference ->
            Log.d("TAG", "Collection document added with ID: ${documentReference.id}")
            onSuccess()
        }
        .addOnFailureListener { e ->
            Log.w("TAG", "Error adding collection document", e)
            onFailure(e)
        }
}

fun saveToFirestore(
    db: FirebaseFirestore,
    userId: String,
    collectionName: String,
    values: List<String>,
    onSuccess: () -> Unit,
    onFailure: (Exception) -> Unit
) {
    if (values.isEmpty() || values.all { it.isEmpty() }) {
        Log.w("TAG", "No data to save to Firestore")
        return
    }

    // Saving data under collection rules document
    val data = hashMapOf<String, Any>()
    values.forEachIndexed { index, value ->
        data["Field$index"] = value
    }

    db.collection("users")
        .document(userId)
        .collection(collectionName)
        .document("collection_rules")
        .set(data)
        .addOnSuccessListener {
            Log.d("TAG", "DocumentSnapshot successfully written with ID: collection_rules")
            // Once the collection rules document is saved, save the collection name
            saveCollectionToFirestore(db, userId, collectionName, onSuccess, onFailure)
        }
        .addOnFailureListener { e ->
            Log.w("TAG", "Error adding collection rules document", e)
            onFailure(e)
        }
}




@Composable
fun TestPreviewTwo() {
    CollectablesTheme {
        CreateCollectionView(rememberNavController())
    }
}

@Preview
@Composable
fun PreviewTestTwo() {
    TestPreviewTwo()
}