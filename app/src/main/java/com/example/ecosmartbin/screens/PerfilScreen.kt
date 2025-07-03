package com.example.ecosmartbin.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ecosmartbin.models.Usuario

@Composable
fun PerfilScreen(
    usuario: Usuario,
    onGuardar: (Usuario) -> Unit,
    modifier: Modifier = Modifier
) {
    var nombre by remember { mutableStateOf(usuario.nombre) }
    var apellidoPaterno by remember { mutableStateOf(usuario.apellidoPaterno) }
    var apellidoMaterno by remember { mutableStateOf(usuario.apellidoMaterno) }
    var telefono by remember { mutableStateOf(usuario.telefono) }
    var fechaNacimiento by remember { mutableStateOf(usuario.fechaNacimiento) }
    var pais by remember { mutableStateOf(usuario.pais) }
    var correo by remember { mutableStateOf(usuario.correo) }

    androidx.compose.runtime.LaunchedEffect(usuario) {
        nombre = usuario.nombre
        apellidoPaterno = usuario.apellidoPaterno
        apellidoMaterno = usuario.apellidoMaterno
        telefono = usuario.telefono
        fechaNacimiento = usuario.fechaNacimiento
        pais = usuario.pais
        correo = usuario.correo
    }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
        OutlinedTextField(value = apellidoPaterno, onValueChange = { apellidoPaterno = it }, label = { Text("Apellido Paterno") })
        OutlinedTextField(value = apellidoMaterno, onValueChange = { apellidoMaterno = it }, label = { Text("Apellido Materno") })
        OutlinedTextField(value = telefono, onValueChange = { telefono = it }, label = { Text("Teléfono") })
        OutlinedTextField(value = fechaNacimiento, onValueChange = { fechaNacimiento = it }, label = { Text("Fecha Nacimiento") })
        OutlinedTextField(value = pais, onValueChange = { pais = it }, label = { Text("País") })
        OutlinedTextField(value = correo, onValueChange = { correo = it }, label = { Text("Correo") })

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            val actualizado = usuario.copy(
                nombre = nombre,
                apellidoPaterno = apellidoPaterno,
                apellidoMaterno = apellidoMaterno,
                telefono = telefono,
                fechaNacimiento = fechaNacimiento,
                pais = pais,
                correo = correo
            )
            onGuardar(actualizado)
        }) {
            Text("Guardar cambios")
        }
    }
}
