package com.example.ecosmartbin.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecosmartbin.R
import com.example.ecosmartbin.network.RetrofitClient
import com.example.ecosmartbin.models.LoginRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showContent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showContent = true
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color(0xFFF5F5F5)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFE8F5E9).copy(alpha = 0.3f),
                            Color(0xFFF5F5F5)
                        ),
                        startY = 0f,
                        endY = 1000f
                    )
                )
                .padding(innerPadding)
        ) {
            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn() + slideInVertically { it / 2 },
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Logo con animación
                    val logoScale by animateFloatAsState(
                        targetValue = if (isLoading) 0.95f else 1f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )

                    Image(
                        painter = painterResource(id = R.drawable.logoeco),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(140.dp)
                            .scale(logoScale)
                            .padding(bottom = 24.dp)
                            .shadow(
                                elevation = 8.dp,
                                shape = CircleShape,
                                spotColor = Color(0xFF4CAF50).copy(alpha = 0.2f)
                            )
                    )

                    Text(
                        text = "EcoSmart Bin",
                        style = MaterialTheme.typography.displaySmall,
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = "Gestión inteligente de residuos",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray.copy(alpha = 0.8f),
                        modifier = Modifier.padding(bottom = 40.dp)
                    )

                    // Tarjeta de login con elevación dinámica
                    var cardElevated by remember { mutableStateOf(false) }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(
                                elevation = if (cardElevated) 16.dp else 8.dp,
                                shape = RoundedCornerShape(24.dp),
                                spotColor = Color(0xFF4CAF50).copy(alpha = 0.1f)
                            )
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        cardElevated = true
                                        tryAwaitRelease()
                                        cardElevated = false
                                    }
                                )
                            },
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(28.dp)
                                .fillMaxWidth()
                        ) {
                            // Input de email con efecto de foco
                            var emailFocused by remember { mutableStateOf(false) }

                            OutlinedTextField(
                                value = correo,
                                onValueChange = { correo = it },
                                label = {
                                    Text(
                                        "Correo electrónico",
                                        color = if (emailFocused) Color(0xFF4CAF50) else Color.Gray
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = "Correo",
                                        tint = if (emailFocused) Color(0xFF4CAF50) else Color.Gray
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onFocusChanged { emailFocused = it.isFocused },
                                shape = RoundedCornerShape(14.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedIndicatorColor = Color(0xFF4CAF50),
                                    unfocusedIndicatorColor = Color.LightGray,
                                    focusedLabelColor = Color(0xFF4CAF50),
                                    focusedLeadingIconColor = Color(0xFF4CAF50),
                                    unfocusedLeadingIconColor = Color.Gray
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            // Input de contraseña con efecto de foco
                            var passwordFocused by remember { mutableStateOf(false) }

                            OutlinedTextField(
                                value = contrasena,
                                onValueChange = { contrasena = it },
                                label = {
                                    Text(
                                        "Contraseña",
                                        color = if (passwordFocused) Color(0xFF4CAF50) else Color.Gray
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Contraseña",
                                        tint = if (passwordFocused) Color(0xFF4CAF50) else Color.Gray
                                    )
                                },
                                visualTransformation = PasswordVisualTransformation(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onFocusChanged { passwordFocused = it.isFocused },
                                shape = RoundedCornerShape(14.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    focusedIndicatorColor = Color(0xFF4CAF50),
                                    unfocusedIndicatorColor = Color.LightGray,
                                    focusedLabelColor = Color(0xFF4CAF50),
                                    focusedLeadingIconColor = Color(0xFF4CAF50),
                                    unfocusedLeadingIconColor = Color.Gray
                                )
                            )

                            Spacer(modifier = Modifier.height(28.dp))

                            // Botón de login con animación
                            val buttonColor by animateColorAsState(
                                targetValue = if (isLoading) Color(0xFF2E7D32) else Color(0xFF4CAF50),
                                animationSpec = tween(300)
                            )

                            Button(
                                onClick = {
                                    if (correo.isBlank() || contrasena.isBlank()) {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Por favor completa todos los campos")
                                        }
                                    } else {
                                        isLoading = true
                                        scope.launch {
                                            try {
                                                val loginRequest = LoginRequest(correo = correo, contrasena = contrasena)
                                                val response = RetrofitClient.instance.loginUsuario(loginRequest)

                                                if (response.isSuccessful) {
                                                    val body = response.body()
                                                    if (body?.user != null) {
                                                        snackbarHostState.showSnackbar("Bienvenido ${body.user.nombre}")
                                                        navController.navigate("welcome/${body.user.nombre}/${body.user.id}")
                                                    } else {
                                                        snackbarHostState.showSnackbar("Usuario o contraseña incorrectos")
                                                    }
                                                } else {
                                                    snackbarHostState.showSnackbar("Error ${response.code()}: ${response.message()}")
                                                }
                                            } catch (e: HttpException) {
                                                snackbarHostState.showSnackbar("Error en la respuesta del servidor: ${e.code()}")
                                            } catch (e: IOException) {
                                                snackbarHostState.showSnackbar("Error de red: ${e.message ?: "verifica tu conexión"}")
                                            } catch (e: Exception) {
                                                snackbarHostState.showSnackbar("Error inesperado: ${e.localizedMessage}")
                                            } finally {
                                                isLoading = false
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(52.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = buttonColor,
                                    contentColor = Color.White
                                ),
                                enabled = !isLoading
                            ) {
                                if (isLoading) {
                                    CircularProgressIndicator(
                                        color = Color.White,
                                        modifier = Modifier.size(24.dp),
                                        strokeWidth = 3.dp
                                    )
                                } else {
                                    Text(
                                        text = "Iniciar sesión",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Enlace de registro con efecto hover
                    var registerHover by remember { mutableStateOf(false) }

                    TextButton(
                        onClick = { navController.navigate("register") },
                        modifier = Modifier
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        registerHover = true
                                        tryAwaitRelease()
                                        registerHover = false
                                    }
                                )
                            }
                    ) {
                        Text(
                            text = "¿No tienes cuenta? Regístrate",
                            color = if (registerHover) Color(0xFF2E7D32) else Color(0xFF4CAF50),
                            fontSize = 14.sp,
                            fontWeight = if (registerHover) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}