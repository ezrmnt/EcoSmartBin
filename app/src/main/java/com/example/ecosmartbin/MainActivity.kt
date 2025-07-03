package com.example.ecosmartbin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.ecosmartbin.ui.theme.EcoSmartBinTheme
import com.example.ecosmartbin.navigation.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoSmartBinTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
