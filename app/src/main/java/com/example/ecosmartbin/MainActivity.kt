package com.example.ecosmartbin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.size
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecosmartbin.navigation.NavGraph
import com.example.ecosmartbin.ui.theme.EcoSmartBinTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcoSmartBinTheme {
                AppEntryPoint()
            }
        }
    }
}

@Composable
fun AppEntryPoint() {
    var isLoading by remember { mutableStateOf(true) }
    val navController = rememberNavController()

    if (isLoading) {
        SplashScreen {
            isLoading = false
        }
    } else {
        NavGraph(navController = navController)
    }
}

@Composable
fun SplashScreen(onLoadingComplete: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logoeco), // Reemplaza con tu logo
            contentDescription = "App Logo",
            modifier = Modifier.size(120.dp)
        )

        // Simula carga y redirige
        LaunchedEffect(Unit) {
            delay(2000) // Duración del splash (2 segundos)
            onLoadingComplete()
        }
    }
}