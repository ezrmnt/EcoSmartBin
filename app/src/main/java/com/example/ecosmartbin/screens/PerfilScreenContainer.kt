package com.example.ecosmartbin.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ecosmartbin.viewmodel.UsuarioViewModel

@Composable
fun PerfilScreenContainer(id: Int, viewModel: UsuarioViewModel = viewModel()) {
    val usuarioState = viewModel.usuario.collectAsState()
    val usuario = usuarioState.value

    LaunchedEffect(id) {
        viewModel.cargarUsuario(id)
    }

    if (usuario != null) {
        PerfilScreen(usuario = usuario, onGuardar = { usuarioActualizado ->
            viewModel.actualizarUsuario(usuarioActualizado)
        })
    } else {
        Text("Cargando usuario...")
    }
}
