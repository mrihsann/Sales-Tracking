package com.ihsanarslan.salestracking.presentation.list_product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ihsanarslan.salestracking.R
import com.ihsanarslan.salestracking.domain.model.ProductsDto

@Composable
fun ListProduct(){

    val vm = hiltViewModel<ListProductViewModel>()
    val products = vm.productList.collectAsStateWithLifecycle()
    val loading = vm.isLoading.collectAsStateWithLifecycle()
    val error = vm.errorMessage.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            items(products.value.size) { id ->
                ProductItem(products.value[id])
            }
        }
    }
}


@Composable
private fun ProductItem(product: ProductsDto) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White)
            .clickable {}
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Default Image",
            modifier = Modifier
                .size(100.dp)
                .border(1.dp, Color.Gray),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = product.name, style = MaterialTheme.typography.titleMedium)
            Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
            Text(text = "${product.price} ₺", style = MaterialTheme.typography.bodyLarge)
        }
    }
}