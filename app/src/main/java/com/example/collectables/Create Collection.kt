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
import kotlin.reflect.KMutableProperty0

//var mainFieldCount = 1

@Composable
fun CreateCollectionView(navController: NavHostController, modifier: Modifier = Modifier) {
    var fieldCount by remember { mutableIntStateOf(1) }

    /*
    //Input fields is separate function so it can multiply
    @Composable
    fun InputFields(

        modifier: Modifier = Modifier
    ) {

        var fieldName by remember { mutableStateOf("") }

        Column {
            Row {
                OutlinedTextField(
                    value = fieldName,
                    onValueChange = {fieldName = it}
                )
        }
            Spacer(modifier = Modifier.size(10.dp))
        }
    }

     */

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
            /*
            repeat(fieldCount) {
                InputFields()
            }
            */
            CustomizableInputFields(
                navController = navController,
                numberOfFields = numberOfFields,
                onSave = { values ->
                    savedValues = values
                    val userName = accessUserName()
                    val collectionName = name
                    saveToFirestore(db, userName, collectionName, values)
                }
            )








            //End
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
fun saveToFirestore(
    db: FirebaseFirestore,
    userName: String,
    collectionName: String,
    values: List<String>
) {
    if (values.isEmpty() || values.all { it.isEmpty() }) {
        Log.w("TAG", "No data to save to Firestore")
        return
    }

    val data = hashMapOf<String, Any>()
    values.forEachIndexed { index, value ->
        data["Field$index"] = value
    }

    db.collection("users")
        .document(userName)
        .collection(collectionName) // Create a collection under the user document
        .document("collection_rules") // Create a document under the collection with a fixed name "collection_rules"
        .set(data) // Set the data under the "collection_rules" document
        .addOnSuccessListener {
            Log.d("TAG", "DocumentSnapshot successfully written with ID: $collectionName")
        }
        .addOnFailureListener { e ->
            Log.w("TAG", "Error adding document", e)
        }
}






/*
@Composable
fun InputFields(

    modifier: Modifier = Modifier
) {

    var fieldName by remember { mutableStateOf("") }

    Row {
        //var field by remember { mutableStateOf("") }

        OutlinedTextField(
            value = fieldName,
            onValueChange = {fieldName = it}
        )
        Button(
            onClick = {  },
            colors = ButtonDefaults.buttonColors( MaterialTheme.colorScheme.secondary),
            modifier = Modifier
        ) {
            Text(
                text = "Add another field",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
    }
}

*/



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