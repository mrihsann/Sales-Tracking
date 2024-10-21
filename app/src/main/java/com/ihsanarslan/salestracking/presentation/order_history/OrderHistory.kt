package com.ihsanarslan.salestracking.presentation.order_history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pushpal.jetlime.ItemsList
import com.pushpal.jetlime.JetLimeColumn
import com.pushpal.jetlime.JetLimeEvent
import com.pushpal.jetlime.JetLimeEventDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderHistory() {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Ürün ekle") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back navigation */ }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            //content here
            val items = remember { mutableListOf("Item1", "Item2", "Item3") } // Any type of items

            JetLimeColumn(
                modifier = Modifier.padding(16.dp),
                itemsList = ItemsList(items),
            ) { index, item, position ->
                JetLimeEvent(
                    style = JetLimeEventDefaults.eventStyle(
                        position = position
                    ),
                ) {
                    Text(modifier = Modifier.height(100.dp),text = item)
                    // Content here
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun OrderHistoryPreview(){
    OrderHistory()
}