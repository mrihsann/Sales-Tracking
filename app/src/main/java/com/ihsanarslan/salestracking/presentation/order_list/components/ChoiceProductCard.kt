package com.ihsanarslan.salestracking.presentation.order_list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ihsanarslan.salestracking.domain.model.ProductDto

@Composable
fun ChoiceProductCard(
    isSelected: Boolean,
    product: ProductDto,
    quantity: Int, // Ürünün mevcut adedi
    onQuantityChanged: (ProductDto, Int) -> Unit // Azaltma ve artırma işlemi
) {
    OutlinedCard(
        shape = MaterialTheme.shapes.small,
        border = if (isSelected) {
            BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
        } else {
            CardDefaults.outlinedCardBorder()
        },
        elevation = CardDefaults.elevatedCardElevation(),
        onClick = {
            if (quantity == 0) {
                onQuantityChanged(product, 1) // Adeti 1 yap
            }
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ürün adı ve fiyatı
            Text(
                text = product.name
            )
            Text(
                text = "₺${product.price}", // Ürün fiyatı
                color = Color.Green, // Yeşil renk
                modifier = Modifier.padding(start = 8.dp).weight(1f)
            )

            // Azaltma butonu
            IconButton(
                onClick = {
                    if (quantity > 0) {
                        onQuantityChanged(product, quantity - 1) // Adeti azalt
                    }
                }
            ) {
                Icon(Icons.Default.Remove, contentDescription = "Azalt")
            }

            // Ürün adedi
            Text(
                text = "$quantity",
                modifier = Modifier.padding(horizontal = 8.dp),
            )

            // Artırma butonu
            IconButton(
                onClick = {
                    onQuantityChanged(product, quantity + 1) // Adeti artır
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Artır")
            }
        }
    }
}
