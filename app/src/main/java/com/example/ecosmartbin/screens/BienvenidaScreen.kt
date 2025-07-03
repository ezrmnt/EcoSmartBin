package com.example.ecosmartbin.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ecosmartbin.viewmodel.UsuarioViewModel

@Composable
fun WelcomeScreen(
    username: String,
    userId: Int,
    navController: NavController,
    context: Context,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("¡Bienvenido, $username, $userId!", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text("Gracias por usar EcoSmartBin.")
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("perfil/$userId")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Editar perfil")
        }
    }
}
