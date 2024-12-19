package com.ihsanarslan.salestracking.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ihsanarslan.salestracking.domain.model.OrderDto

@Composable
fun OrderHistory(
    orders : List<OrderDto>,
    navigateToDetail : () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sipariş Geçmişi",
                    style = MaterialTheme.typography.bodyLarge
                )
                TextButton(onClick = navigateToDetail ) {
                    Text(text = "Hepsini gör")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Sipariş listesi
            orders.forEach { data ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = data.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "₺${data.price}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

//
//@Preview
//@Composable
//fun pr(){
//    OrderHistory()
//}