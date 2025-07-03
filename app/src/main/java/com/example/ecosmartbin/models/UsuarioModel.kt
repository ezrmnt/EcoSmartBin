package com.example.ecosmartbin.models
import android.adservices.ondevicepersonalization.UserData
import kotlinx.serialization.Serializer
import java.util.Date
import com.google.gson.annotations.SerializedName


data class LoginRequest(
    val correo: String,
    val contrasena: String
)

data class LoginResponse(
    val user: Usuario? = null
)

data class Usuario(
    val id: Int? = null,
    val nombre: String = "",
    @SerializedName("Apellido_paterno")
    val apellidoPaterno: String = "",
    @SerializedName("Apellido_materno")
    val apellidoMaterno: String = "",
    val telefono: String = "",
    val fechaNacimiento: String = "",
    val pais: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val contrasenaConfirmacion: String = ""
)

