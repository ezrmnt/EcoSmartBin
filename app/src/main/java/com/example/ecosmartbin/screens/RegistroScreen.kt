package com.example.ecosmartbin.screens

import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecosmartbin.R
import com.example.ecosmartbin.dao.UsuarioDao
import com.example.ecosmartbin.models.Usuario
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var apellidoPaterno by remember { mutableStateOf("") }
    var apellidoMaterno by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf<Date?>(null) }
    var pais by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var contrasenaConfirmacion by remember { mutableStateOf("") }

    val context = LocalContext.current
    val usuarioDAO = remember { UsuarioDao(context) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            calendar.set(selectedYear, selectedMonth, selectedDay)
            fechaNacimiento = calendar.time
        }, year, month, day
    )

    val listaPaises = listOf(
        "México", "Estados Unidos", "España", "Colombia", "Argentina",
        "Chile", "Perú", "Ecuador", "Venezuela", "Guatemala",
        "Costa Rica", "Panamá", "República Dominicana", "Uruguay", "Paraguay",
        "Bolivia", "El Salvador", "Honduras", "Nicaragua", "Cuba",
        "Canadá", "Brasil", "Portugal", "Francia", "Alemania",
        "Italia", "Reino Unido", "China", "Japón", "Australia"
    )

    var expanded by remember { mutableStateOf(false) }
    val paisFiltrado = listaPaises.filter { it.contains(pais, ignoreCase = true) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color(0xFFF5F5F5)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF5F5F5))
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo y título
            Image(
                painter = painterResource(id = R.drawable.logoeco),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 32.dp, bottom = 16.dp)
            )

            Text(
                text = "Crear cuenta",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF2E7D32),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Únete a nuestra comunidad ecológica",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Formulario
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                ) {
                    // Campos de nombre
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = { Text("Nombre") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = textFieldColors()
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Campos de apellidos
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = apellidoPaterno,
                            onValueChange = { apellidoPaterno = it },
                            label = { Text("Apellido Paterno") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = textFieldColors()
                        )

                        OutlinedTextField(
                            value = apellidoMaterno,
                            onValueChange = { apellidoMaterno = it },
                            label = { Text("Apellido Materno") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = textFieldColors()
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Información de contacto
                    OutlinedTextField(
                        value = telefono,
                        onValueChange = {
                            if (it.all { char -> char.isDigit() } && it.length == 20) {
                                telefono = it
                            }
                        },
                        label = { Text("Teléfono") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = "Teléfono", tint = Color(0xFF4CAF50)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = textFieldColors(),
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = correo,
                        onValueChange = { correo = it },
                        label = { Text("Correo electrónico") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Correo", tint = Color(0xFF4CAF50)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = textFieldColors()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Selector de país
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = pais,
                            onValueChange = {
                                pais = it
                                expanded = true
                            },
                            label = { Text("País") },
                            leadingIcon = { Icon(Icons.Default.Place, contentDescription = "País", tint = Color(0xFF4CAF50)) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            shape = RoundedCornerShape(12.dp),
                            colors = textFieldColors(),
                            singleLine = true
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            paisFiltrado.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        pais = option
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Fecha de nacimiento
                    OutlinedButton(
                        onClick = { datePickerDialog.show() },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = if (fechaNacimiento == null) Color.Gray else Color.Black
                        )
                    ) {
                        Text(
                            text = fechaNacimiento?.let { d ->
                                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(d)
                            } ?: "Fecha de nacimiento",
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Contraseñas
                    OutlinedTextField(
                        value = contrasena,
                        onValueChange = { contrasena = it },
                        label = { Text("Contraseña") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Contraseña", tint = Color(0xFF4CAF50)) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = textFieldColors()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = contrasenaConfirmacion,
                        onValueChange = { contrasenaConfirmacion = it },
                        label = { Text("Confirmar contraseña") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Confirmar contraseña", tint = Color(0xFF4CAF50)) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = textFieldColors()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón de registro
                    Button(
                        onClick = {
                            scope.launch {
                                Log.d("RegistroScreen", "Botón Registrarse presionado")

                                val nombreRegex = Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")
                                val paisRegex = Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")
                                val correoRegex = Regex("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")

                                when {
                                    nombre.isBlank() || apellidoPaterno.isBlank() || apellidoMaterno.isBlank() ||
                                            telefono.isBlank() || pais.isBlank() || correo.isBlank() ||
                                            contrasena.isBlank() || contrasenaConfirmacion.isBlank() || fechaNacimiento == null -> {
                                        snackbarHostState.showSnackbar("Por favor completa todos los campos")
                                        Log.d("RegistroScreen", "Error: Campos incompletos")
                                    }

                                    !nombreRegex.matches(nombre) -> {
                                        snackbarHostState.showSnackbar("Nombre no válido (solo letras y espacios)")
                                    }

                                    !nombreRegex.matches(apellidoPaterno) -> {
                                        snackbarHostState.showSnackbar("Apellido paterno no válido")
                                    }

                                    !nombreRegex.matches(apellidoMaterno) -> {
                                        snackbarHostState.showSnackbar("Apellido materno no válido")
                                    }

                                    !paisRegex.matches(pais) -> {
                                        snackbarHostState.showSnackbar("País no válido (solo letras y espacios)")
                                    }

                                    !telefono.matches(Regex("^\\d{7,15}$")) -> {
                                        snackbarHostState.showSnackbar("Teléfono no válido (debe tener entre 7 y 15 dígitos)")
                                    }

                                    !correoRegex.matches(correo) -> {
                                        snackbarHostState.showSnackbar("Correo electrónico no válido")
                                    }

                                    contrasena.length < 6 -> {
                                        snackbarHostState.showSnackbar("La contraseña debe tener al menos 6 caracteres")
                                    }

                                    contrasena != contrasenaConfirmacion -> {
                                        snackbarHostState.showSnackbar("Las contraseñas no coinciden")
                                        Log.d("RegistroScreen", "Error: Contraseñas no coinciden")
                                    }

                                    else -> {
                                        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                                        val fechaFormateada = sdf.format(fechaNacimiento!!)

                                        val nuevoUsuario = Usuario(
                                            nombre = nombre,
                                            apellidoPaterno = apellidoPaterno,
                                            apellidoMaterno = apellidoMaterno,
                                            telefono = telefono,
                                            fechaNacimiento = fechaFormateada,
                                            pais = pais,
                                            correo = correo,
                                            contrasena = contrasena,
                                            contrasenaConfirmacion = contrasenaConfirmacion
                                        )

                                        Log.d("RegistroScreen", "Intentando registrar usuario: $nuevoUsuario")
                                        val exito = usuarioDAO.insertarUsuario(usuario = nuevoUsuario)
                                        if (exito) {
                                            Log.d("RegistroScreen", "Registro exitoso")
                                            snackbarHostState.showSnackbar("Registro exitoso")
                                            navController.popBackStack()
                                        } else {
                                            Log.d("RegistroScreen", "Error al registrar usuario")
                                            snackbarHostState.showSnackbar("Error al registrar, el usuario puede existir")
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Registrarse", fontWeight = FontWeight.Medium)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "¿Ya tienes cuenta? Inicia sesión",
                            color = Color(0xFF4CAF50),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun textFieldColors() = TextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    focusedIndicatorColor = Color(0xFF4CAF50),
    unfocusedIndicatorColor = Color.LightGray,
    focusedLabelColor = Color(0xFF4CAF50),
    focusedLeadingIconColor = Color(0xFF4CAF50)
)