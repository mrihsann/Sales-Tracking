package com.ihsanarslan.salestracking.presentation.order_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ihsanarslan.salestracking.domain.model.OrderDto

@Composable
fun AddOrUpdateOrderSheetContent(
    order: OrderDto? = null,
    onSave: (Int,String, String, String) -> Unit
) {

    var orderName by remember { mutableStateOf("") }
    var orderDescription by remember { mutableStateOf("") }
    var orderPrice by remember { mutableStateOf("") }
    val orderId = order?.id ?: 0
    var isButtonEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(order) {
        orderName = order?.name.orEmpty()
        orderDescription = order?.description.orEmpty()
        orderPrice = order?.price?.toString().orEmpty()
    }

    LaunchedEffect(orderName, orderDescription, orderPrice) {
        isButtonEnabled = orderName.isNotBlank() && orderDescription.isNotBlank() && orderPrice.isNotBlank()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = if (order == null) "Sipariş Ekle" else "Sipariş Güncelle",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = orderName,
            onValueChange = { orderName = it },
            label = { Text(text = "Sipariş Adı") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = orderDescription,
            onValueChange = { orderDescription = it },
            label = { Text(text = "Siparişin Açıklaması") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        OutlinedTextField(
            value = orderPrice,
            onValueChange = { orderPrice = it },
            label = { Text(text = "Sipariş Fiyatı") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                onSave(orderId, orderName, orderDescription, orderPrice)
            },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth(),
            enabled = isButtonEnabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ) {
            Text(text = if (order == null) "Siparişi Ekle" else "Değişiklikleri Kaydet")
        }
    }
}