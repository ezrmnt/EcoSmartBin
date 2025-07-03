package com.example.ecosmartbin.response

import android.service.autofill.UserData
import com.example.ecosmartbin.models.Usuario


data class LoginResponse(
    val message: String,
    val user: Usuario? = null
)