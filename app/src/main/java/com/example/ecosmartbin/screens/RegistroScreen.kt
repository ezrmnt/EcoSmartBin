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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecosmartbin.R
import com.example.ecosmartbin.dao.UsuarioDao
import com.example.ecosmartbin.models.Usuario
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

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

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())  // formato para DB

    val listaPaises = listOf(
        "Afganistán", "Albania", "Alemania", "Andorra", "Angola", "Antigua y Barbuda",
        "Arabia Saudita", "Argelia", "Argentina", "Armenia", "Australia", "Austria",
        "Azerbaiyán", "Bahamas", "Bangladés", "Barbados", "Baréin", "Bélgica",
        "Belice", "Benín", "Bielorrusia", "Birmania", "Bolivia", "Bosnia y Herzegovina",
        "Botsuana", "Brasil", "Brunéi", "Bulgaria", "Burkina Faso", "Burundi",
        "Bután", "Cabo Verde", "Camboya", "Camerún", "Canadá", "Catar", "Chad",
        "Chile", "China", "Chipre", "Colombia", "Comoras", "Corea del Norte",
        "Corea del Sur", "Costa de Marfil", "Costa Rica", "Croacia", "Cuba",
        "Dinamarca", "Dominica", "Ecuador", "Egipto", "El Salvador", "Emiratos Árabes Unidos",
        "Eritrea", "Eslovaquia", "Eslovenia", "España", "Estados Unidos", "Estonia",
        "Esuatini", "Etiopía", "Filipinas", "Finlandia", "Fiyi", "Francia", "Gabón",
        "Gambia", "Georgia", "Ghana", "Granada", "Grecia", "Guatemala", "Guinea",
        "Guinea-Bisáu", "Guinea Ecuatorial", "Guyana", "Haití", "Honduras", "Hungría",
        "India", "Indonesia", "Irak", "Irán", "Irlanda", "Islandia", "Islas Marshall",
        "Islas Salomón", "Israel", "Italia", "Jamaica", "Japón", "Jordania",
        "Kazajistán", "Kenia", "Kirguistán", "Kiribati", "Kuwait", "Laos",
        "Lesoto", "Letonia", "Líbano", "Liberia", "Libia", "Liechtenstein",
        "Lituania", "Luxemburgo", "Madagascar", "Malasia", "Malaui", "Maldivas",
        "Malí", "Malta", "Marruecos", "Mauricio", "Mauritania", "México",
        "Micronesia", "Moldavia", "Mónaco", "Mongolia", "Montenegro", "Mozambique",
        "Namibia", "Nauru", "Nepal", "Nicaragua", "Níger", "Nigeria",
        "Noruega", "Nueva Zelanda", "Omán", "Países Bajos", "Pakistán",
        "Palaos", "Palestina", "Panamá", "Papúa Nueva Guinea", "Paraguay", "Perú",
        "Polonia", "Portugal", "Reino Unido", "República Centroafricana",
        "República Checa", "República del Congo", "República Democrática del Congo",
        "República Dominicana", "Ruanda", "Rumanía", "Rusia", "Samoa",
        "San Cristóbal y Nieves", "San Marino", "San Vicente y las Granadinas",
        "Santa Lucía", "Santo Tomé y Príncipe", "Senegal", "Serbia",
        "Seychelles", "Sierra Leona", "Singapur", "Siria", "Somalia",
        "Sri Lanka", "Sudáfrica", "Sudán", "Sudán del Sur", "Suecia",
        "Suiza", "Surinam", "Tailandia", "Tanzania", "Tayikistán",
        "Timor Oriental", "Togo", "Tonga", "Trinidad y Tobago", "Túnez",
        "Turkmenistán", "Turquía", "Tuvalu", "Ucrania", "Uganda",
        "Uruguay", "Uzbekistán", "Vanuatu", "Vaticano", "Venezuela",
        "Vietnam", "Yemen", "Yibuti", "Zambia", "Zimbabue"
    )

    var expanded by remember { mutableStateOf(false) }
    val paisFiltrado = listaPaises.filter { it.contains(pais, ignoreCase = true) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF4CAF50),
                            Color(0xFF2E7D32)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(top = 48.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Únete a EcoSmartBin",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "Crea tu cuenta para comenzar",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Crear cuenta",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color(0xFF2E7D32),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Nombre") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = apellidoPaterno,
                        onValueChange = { apellidoPaterno = it },
                        label = { Text("Apellido Paterno") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Apellido Paterno") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = apellidoMaterno,
                        onValueChange = { apellidoMaterno = it },
                        label = { Text("Apellido Materno") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Apellido Materno") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = telefono,
                        onValueChange = {
                            if (it.all { char -> char.isDigit() }) {
                                telefono = it
                            }
                        },
                        label = { Text("Teléfono") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = "Teléfono") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

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
                            leadingIcon = { Icon(Icons.Default.Place, contentDescription = "País") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            shape = RoundedCornerShape(12.dp),
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

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = correo,
                        onValueChange = { correo = it },
                        label = { Text("Correo electrónico") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Correo") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = { datePickerDialog.show() },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = fechaNacimiento?.let { d ->
                                java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(d)
                            } ?: "Selecciona tu fecha de nacimiento",
                            color = if (fechaNacimiento == null) Color.Gray else Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = contrasena,
                        onValueChange = { contrasena = it },
                        label = { Text("Contraseña") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = contrasenaConfirmacion,
                        onValueChange = { contrasenaConfirmacion = it },
                        label = { Text("Confirmar contraseña") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Confirmar contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

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
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50)
                        )
                    ) {
                        Text(
                            text = "Registrarse",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }


                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "¿Ya tienes cuenta? Inicia sesión",
                            color = Color(0xFF4CAF50),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
