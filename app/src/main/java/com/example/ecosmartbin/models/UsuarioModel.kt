package com.example.ecosmartbin.models



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
    val apellidoPaterno: String = "",
    val apellidoMaterno: String = "",
    val telefono: String = "",
    val fechaNacimiento: String = "",
    val pais: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val contrasenaConfirmacion: String = ""
)

