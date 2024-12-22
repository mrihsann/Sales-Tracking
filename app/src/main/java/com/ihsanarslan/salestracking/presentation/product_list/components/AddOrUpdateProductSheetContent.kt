package com.ihsanarslan.salestracking.presentation.product_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import com.ihsanarslan.salestracking.domain.model.ProductDto

@Composable
fun AddOrUpdateProductSheetContent(
    product: ProductDto? = null,
    onSave: (Int,String, String, String) -> Unit
) {

    var productName by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    val productId = product?.productId ?: 0
    var isButtonEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(product) {
        productName = product?.name.orEmpty()
        productDescription = product?.description.orEmpty()
        productPrice = product?.price?.toString().orEmpty()
    }

    LaunchedEffect(productName, productDescription, productPrice) {
        isButtonEnabled = productName.isNotBlank() && productDescription.isNotBlank() && productPrice.isNotBlank()
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        bottomBar = {
            BottomBarProduct(
                isButtonEnabled = isButtonEnabled,
                onSave = {
                    onSave(productId, productName, productDescription, productPrice)
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = if (product == null) "Ürün Ekle" else "Ürünü Güncelle",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                value = productName,
                onValueChange = { productName = it },
                label = { Text(text = "Ürün Adı") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = productDescription,
                onValueChange = { productDescription = it },
                label = { Text(text = "Ürünün Açıklaması") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            OutlinedTextField(
                value = productPrice,
                onValueChange = { productPrice = it },
                label = { Text(text = "Ürün Fiyatı") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}