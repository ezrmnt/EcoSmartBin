package com.example.ecosmartbin.response

data class RegistroResponse(
    val message: String,
    val user: UsuarioResponseDTO? = null
)

data class UsuarioResponseDTO(
    val id: Int,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val telefono: String,
    val fechaNacimiento: String,
    val pais: String,
    val correo: String
)

