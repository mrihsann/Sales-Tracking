package com.ihsanarslan.salestracking.presentation.order_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomBarOrder(
    orderAmount: Double = 0.0, // Sipariş tutarı
    isButtonEnabled : Boolean = false,
    onSave: () -> Unit // Kaydet butonu tıklama işlemi
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(5.dp),
        shape = MaterialTheme.shapes.medium.copy(topStart = CornerSize(15.dp), topEnd = CornerSize(15.dp), bottomStart = CornerSize(0.dp), bottomEnd = CornerSize(0.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Sipariş Tutarı",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "₺${"%.2f".format(orderAmount)}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Button(
                onClick = onSave,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(.5f),
                enabled = isButtonEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text(text = "Kaydet")
            }
        }
    }
}