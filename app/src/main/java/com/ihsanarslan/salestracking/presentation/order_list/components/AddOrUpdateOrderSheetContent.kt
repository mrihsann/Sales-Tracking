package com.ihsanarslan.salestracking.presentation.order_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ihsanarslan.salestracking.domain.model.OrderWithProducts
import com.ihsanarslan.salestracking.domain.model.ProductDto

@Composable
fun AddOrUpdateOrderSheetContent(
    products: List<ProductDto>,
    order: OrderWithProducts? = null,
    onSave: (Int, String, String, String, Map<Int, Int>) -> Unit
) {
    var orderName by remember { mutableStateOf("") }
    var orderDescription by remember { mutableStateOf("") }
    var selectedProducts by remember { mutableStateOf<Map<Int, Int>>(emptyMap()) }
    val orderId = order?.order?.orderId ?: 0
    var isButtonEnabled by remember { mutableStateOf(false) }

    // Fiyat hesaplama
    val totalPrice = selectedProducts.entries.sumOf { (productId, quantity) ->
        products.find { it.productId == productId }?.price?.times(quantity) ?: 0.0
    }

    // Order verilerini başlangıçta doldurma
    LaunchedEffect(order) {
        orderName = order?.order?.name.orEmpty()
        orderDescription = order?.order?.description.orEmpty()
        // Her ürün için miktarı mappedQuantities listesinden alarak eşleştir
        selectedProducts = order?.products?.associate { product ->
            product.productId to (order.mappedQuantity.find { it.productId == product.productId }?.quantity ?: 0)
        } ?: emptyMap()
    }

    LaunchedEffect(orderName, orderDescription, selectedProducts) {
        isButtonEnabled = orderName.isNotBlank() && orderDescription.isNotBlank() && selectedProducts.isNotEmpty()
    }

    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        bottomBar = {
            BottomBarOrder(
                orderAmount = totalPrice,
                isButtonEnabled = isButtonEnabled,
                onSave = { onSave(orderId, orderName, orderDescription, totalPrice.toString(), selectedProducts) }
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

            // Ürünleri listele
            products.forEach { product ->
                ChoiceProductCard(
                    isSelected = selectedProducts.containsKey(product.productId), // Adet 0 ise seçilmemiş görünecek
                    product = product,
                    quantity = selectedProducts[product.productId] ?: 0,
                    onQuantityChanged = { selectedProduct, newQuantity ->
                        // Ürün adedini artırma ve azaltma işlemi
                        if (newQuantity > 0) {
                            selectedProducts = selectedProducts.toMutableMap().apply {
                                this[selectedProduct.productId] = newQuantity
                            }
                        } else {
                            selectedProducts = selectedProducts.toMutableMap().apply {
                                remove(selectedProduct.productId) // Adet 0 ise ürünü listeden kaldır
                            }
                        }
                    }
                )
            }
        }
    }
}