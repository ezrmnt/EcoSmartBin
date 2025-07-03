package com.example.ecosmartbin.dao


import android.content.ContentValues
import android.content.Context
import com.example.ecosmartbin.models.Usuario
import com.example.ecosmartbin.models.LoginRequest
import com.example.ecosmartbin.network.RetrofitClient

class UsuarioDao(context: Context) {

    suspend fun insertarUsuario(usuario: Usuario): Boolean {
        return try {
            val response = RetrofitClient.instance.registrarUsuario(usuario)

            if (response.isSuccessful) {
                println(" Registro exitoso: ${response.body()?.message}")
                true
            } else {
                println(" Registro fallido | Código: ${response.code()} | Error: ${response.errorBody()?.string()}")
                false
            }

        } catch (e: Exception) {
            e.printStackTrace()
            println("Excepción en el registro: ${e.localizedMessage}")
            false
        }
    }

    suspend fun validarUsuario(correo: String, contrasena: String): Boolean {
        return try {
            val response = RetrofitClient.instance.loginUsuario(LoginRequest(correo, contrasena))
            if (response.isSuccessful && response.body()?.user != null) {
                true
            } else {
                val errorBody = response.errorBody()?.string()
                println("Error al validar usuario: $errorBody")
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    suspend fun actualizarUsuario(usuario: Usuario): Boolean {
        return try {
            val response = RetrofitClient.instance.actualizarUsuario(usuario.id!!, usuario)
            response.isSuccessful
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
