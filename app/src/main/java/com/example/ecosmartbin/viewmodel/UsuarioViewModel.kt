package com.example.ecosmartbin.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecosmartbin.models.Usuario
import com.example.ecosmartbin.network.RetrofitClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {
    private val _usuario = MutableStateFlow<Usuario?>(null)
    val usuario: StateFlow<Usuario?> = _usuario
    private val _isUpdating = MutableStateFlow(false)
    val isUpdating: StateFlow<Boolean> = _isUpdating

    private val _userId = MutableStateFlow<Int?>(null)
    val userId: StateFlow<Int?> = _userId

    private val _eventoUI = MutableSharedFlow<String>()
    val eventoUI = _eventoUI.asSharedFlow()

    fun cargarUsuario(id: Int) {
        viewModelScope.launch {
            val response = RetrofitClient.instance.obtenerUsuarioPorId(id)
            Log.d("UsuarioViewModel", "Response obtenerUsuarioPorId code: ${response.code()}")
            if (response.isSuccessful) {
                val usuarioResponse = response.body()
                if (usuarioResponse != null) {
                    _usuario.value = usuarioResponse
                    Log.d("UsuarioViewModel", "Usuario cargado: $usuarioResponse")
                    val usuarioSeguro = usuarioResponse.copy(
                        nombre = usuarioResponse.nombre ?: "",
                        apellidoPaterno = usuarioResponse.apellidoPaterno ?: "",
                        apellidoMaterno = usuarioResponse.apellidoMaterno ?: "",
                        telefono = usuarioResponse.telefono ?: "",
                        fechaNacimiento = usuarioResponse.fechaNacimiento ?: "",
                        pais = usuarioResponse.pais ?: "",
                        correo = usuarioResponse.correo ?: "",
                        contrasena = usuarioResponse.contrasena ?: "",
                        contrasenaConfirmacion = usuarioResponse.contrasenaConfirmacion ?: ""
                    )
                    _usuario.value = usuarioSeguro
                    Log.d("UsuarioViewModel", "Usuario cargado: $usuarioSeguro")
                } else {
                    _usuario.value = null
                    Log.d("UsuarioViewModel", "Usuario null en body")
                }
            } else {
                _usuario.value = null
                Log.d("UsuarioViewModel", "Error al cargar usuario")
            }
        }
    }

    fun cargarIdPorUsername(username: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.obtenerUsuarioPorUsername(username)
                Log.d("UsuarioViewModel", "Response obtenerUsuarioPorUsername code: ${response.code()}")
                if (response.isSuccessful) {
                    val usuario = response.body()
                    _userId.value = usuario?.id
                    Log.d("UsuarioViewModel", "ID obtenido: ${_userId.value}")
                } else {
                    _userId.value = null
                    Log.d("UsuarioViewModel", "Error al obtener ID por username")
                }
            } catch(e: Exception) {
                e.printStackTrace()
                _userId.value = null
            }
        }
    }

    fun actualizarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            try {
                _isUpdating.value = true
                val id = usuario.id
                if (id == null) {
                    Log.e("UsuarioViewModel", "ID del usuario es null, no se puede actualizar")
                    _isUpdating.value = false
                    return@launch
                }
                val response = RetrofitClient.instance.actualizarUsuario(id, usuario)
                if (response.isSuccessful) {
                    _usuario.value = response.body()
                    _eventoUI.emit("Usuario actualizado con éxito")
                } else {
                    Log.e("UsuarioViewModel", "Error en la respuesta al actualizar usuario: ${response.code()}")
                    _eventoUI.emit("Error al actualizar usuario")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _eventoUI.emit("Error en la conexión")
            } finally {
                _isUpdating.value = false
            }
        }
    }
}