package com.ihsanarslan.salestracking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.ihsanarslan.salestracking.navigation.NavigationGraph
import com.ihsanarslan.salestracking.ui.theme.SalesTrackingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SalesTrackingTheme {
                NavigationGraph()
            }
        }
    }
}