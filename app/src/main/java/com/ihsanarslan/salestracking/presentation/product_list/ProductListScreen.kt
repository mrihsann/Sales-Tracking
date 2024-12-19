package com.ihsanarslan.salestracking.presentation.product_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ihsanarslan.salestracking.domain.model.OrderDto
import com.ihsanarslan.salestracking.domain.model.ProductDto
import com.ihsanarslan.salestracking.presentation.product_list.components.AddOrUpdateProductSheetContent
import com.ihsanarslan.salestracking.presentation.product_list.components.ProductItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    navController: NavController
){

    val vm = hiltViewModel<ProductListViewModel>()
    val products = vm.productList.collectAsStateWithLifecycle()
    val loading = vm.isLoading.collectAsStateWithLifecycle()
    val error = vm.errorMessage.collectAsStateWithLifecycle()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var productToDelete by remember { mutableStateOf<Int?>(null) }

    var selectedProduct by remember { mutableStateOf<ProductDto?>(null) }

    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        modifier = Modifier
            .navigationBarsPadding(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Ürünler") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            AddOrUpdateProductSheetContent(
                product = selectedProduct,
                onSave = { id, name, description, price ->
                    if (selectedProduct == null) {
                        vm.insert(
                            name = name,
                            description = description,
                            price = price
                        )
                    } else {
                        vm.update(
                            ProductDto(
                                id = id,
                                name= name,
                                description= description,
                                price = price.toDouble()
                            )
                        )
                    }
                    scope.launch {
                        scaffoldState.bottomSheetState.partialExpand()
                    }
                }
            )
        },
        sheetContentColor = MaterialTheme.colorScheme.onSurface,
        sheetShadowElevation = 8.dp,
    ) { innerPadding ->
        Box (modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)){
            LazyColumn {
                items(products.value.size) { index ->
                    ProductItem(
                        product = products.value[index],
                        onDeleteClick = {
                            showDeleteDialog = true
                            productToDelete = products.value[index].id
                        },
                        onEditClick = {
                            selectedProduct = products.value[index]
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        }
                    )
                }
            }
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(15.dp),
                onClick = {
                selectedProduct = null
                scope.launch {
                    scaffoldState.bottomSheetState.expand()
                }
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(text = "Ürünü Sil") },
            text = { Text(text = "Bu ürünü silmek istediğinizden emin misiniz?") },
            confirmButton = {
                TextButton(onClick = {
                    productToDelete?.let {
                        vm.delete(it)
                    }
                    showDeleteDialog = false
                }) {
                    Text(text = "Evet")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                }) {
                    Text(text = "Hayır")
                }
            }
        )
    }
}