package com.example.collectables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun SignUp(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CollectTopBar()
        }
    ) { thisdoesnothingbutneedstobehere ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()

        ) {

        }
    }
}

