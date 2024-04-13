package com.example.collectables

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.collectables.ui.theme.CollectablesTheme
import com.google.firebase.appcheck.internal.util.Logger.TAG
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay


@Composable
fun CollectView(navController: NavHostController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CollectTopBar()
        }

    ) { innerPadding ->

        //val db = Firebase.firestore

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
                        onClick = {navController.navigate(Routes.CreateCollection.route) },
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
                        onClick = {  },
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
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
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
                    onClick = {  },
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
            HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.size(10.dp))
            //Put collections here
            DisplayCollectionNames(navController = navController)
        }
    }
}
//ChatGPT modified by me
// Function to fetch collection names from Firestore
fun getCollectionNamesFromFirestore(
    db: FirebaseFirestore,
    userId: String,
    onSuccess: (List<String>) -> Unit,
    onFailure: (Exception) -> Unit
) {
    db.collection("users")
        .document(userId)
        .collection("collections")
        .get()
        .addOnSuccessListener { querySnapshot ->
            val collectionNames = querySnapshot.documents.map { it.getString("collectionName")!! }
            onSuccess(collectionNames)
        }
        .addOnFailureListener { e ->
            onFailure(e)
        }
}
//ChatGPT modified by me
@Composable
fun DisplayCollectionNames(navController: NavHostController) {
    val db = Firebase.firestore
    val userId = accessUserName() // Assuming you have a function to retrieve the current user's ID

    // State to hold the collection names
    var collectionNames by remember { mutableStateOf<List<String>>(emptyList()) }

    // Function to fetch collection names from Firestore
    fun fetchCollectionNames() {
        getCollectionNamesFromFirestore(
            db = db,
            userId = userId,
            onSuccess = { names ->
                collectionNames = names
            },
            onFailure = { e ->
                Log.e("TAG", "Failed to fetch collection names", e)
            }
        )
    }

    // Fetch collection names from Firestore when the component is composed
    //key1 is set to userID so if the user changes
    DisposableEffect(key1 = userId) {
        fetchCollectionNames()
        onDispose { }
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        // Display collection names on UI cards
        collectionNames.forEach { collectionName ->
            //CollectionCard(name = collectionName)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                onClick = {
                    // Handle card click, navigate to collection details or perform other actions
                    navController.navigate("collectionDetails/$collectionName")
                }
            ) {
                Text(
                    text = collectionName,
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }         

        }
    }
    // Delay and refresh mechanism
    LaunchedEffect(Unit) {
        delay(4000) // Adjust the delay duration as needed
        fetchCollectionNames()
    }
}



@Composable
fun CollectionCard(name: String, modifier: Modifier = Modifier) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 12.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            Box(modifier = modifier) {
                Image(
                    //Icon from FreePik.com made by Vitaly Gorbachev
                    painter = painterResource(id = R.drawable.box_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp, 50.dp)
                        .padding(10.dp)
                )
            }
            Box(modifier = modifier) {
                Text(
                    text = name,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
        }
    }
    Spacer(modifier = Modifier.size(10.dp))
}

@Composable
fun TestPreview() {
    CollectablesTheme {
        CollectView(rememberNavController())
    }
}

@Preview
@Composable
fun PreviewTest() {
    TestPreview()
}