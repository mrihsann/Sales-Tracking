package com.ihsanarslan.salestracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ihsanarslan.salestracking.presentation.add_product.AddProduct
import com.ihsanarslan.salestracking.ui.theme.SalesTrackingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SalesTrackingTheme {
                AddProduct()
            }
        }
    }
}