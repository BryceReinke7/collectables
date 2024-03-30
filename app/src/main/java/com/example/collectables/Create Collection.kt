package com.example.collectables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.collectables.ui.theme.CollectablesTheme
import kotlin.reflect.KMutableProperty0

//var mainFieldCount = 1

@Composable
fun CreateCollectionView(modifier: Modifier = Modifier) {
    var fieldCount by remember { mutableIntStateOf(1) }

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

    Scaffold(
        topBar = {
            CollectTopBar()
        }

    ) { innerPadding ->

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
                    var name by remember { mutableStateOf("") }

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
            repeat(fieldCount) {
                InputFields()
            }




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
                            onClick = { fieldCount ++ },
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
                            onClick = { if (fieldCount > 1) fieldCount -- },
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
                    Button(
                        onClick = {  },
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
        }
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
        CreateCollectionView()
    }
}

@Preview
@Composable
fun PreviewTestTwo() {
    TestPreviewTwo()
}