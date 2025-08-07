package com.example.ecosmartbin.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecosmartbin.R
import com.example.ecosmartbin.models.Usuario
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    usuario: Usuario,
    onGuardar: (Usuario) -> Unit,
    isUpdating: Boolean,
    navController: NavController? = null,
    modifier: Modifier = Modifier
) {
    // Estados para los campos del formulario
    var nombre by rememberSaveable { mutableStateOf(usuario.nombre) }
    var apellidoPaterno by rememberSaveable { mutableStateOf(usuario.apellidoPaterno) }
    var apellidoMaterno by rememberSaveable { mutableStateOf(usuario.apellidoMaterno) }
    var telefono by rememberSaveable { mutableStateOf(usuario.telefono) }
    var fechaNacimiento by rememberSaveable { mutableStateOf(usuario.fechaNacimiento) }
    var pais by rememberSaveable { mutableStateOf(usuario.pais) }
    var correo by rememberSaveable { mutableStateOf(usuario.correo) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showContent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showContent = true
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent,
        bottomBar = {
            BottomNavigationBar(navController = navController, currentScreen = "perfil")
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2E7D32),
                            Color(0xFF1B5E20)
                        ),
                        startY = 0f,
                        endY = 1000f
                    )
                )
        ) {
            AnimatedVisibility(
                visible = showContent,
                enter = fadeIn() + slideInVertically { it / 2 },
                exit = fadeOut(),
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(top = 48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar con animación
                    val avatarScale by animateFloatAsState(
                        targetValue = 1f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )

                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .scale(avatarScale)
                            .shadow(
                                elevation = 12.dp,
                                shape = CircleShape,
                                spotColor = Color(0xFF4CAF50).copy(alpha = 0.3f)
                            )
                            .background(
                                color = Color.White,
                                shape = CircleShape
                            )
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logoeco),
                            contentDescription = "User Profile",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Mi Perfil",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.shadow(2.dp)
                    )
                }

                // Tarjeta de formulario con elevación dinámica
                var cardElevated by remember { mutableStateOf(false) }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                        .shadow(
                            elevation = if (cardElevated) 24.dp else 16.dp,
                            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                            spotColor = Color(0xFF4CAF50).copy(alpha = 0.2f)
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
                        // Campos del formulario con efectos de foco
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            // Campo Nombre
                            var nombreFocused by remember { mutableStateOf(false) }
                            OutlinedTextField(
                                value = nombre,
                                onValueChange = { nombre = it },
                                label = {
                                    Text(
                                        "Nombre",
                                        color = if (nombreFocused) Color(0xFF4CAF50) else Color.Gray
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Nombre",
                                        tint = if (nombreFocused) Color(0xFF4CAF50) else Color.Gray
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onFocusChanged { nombreFocused = it.isFocused },
                                shape = RoundedCornerShape(14.dp),
                                colors = textFieldColors()
                            )

                            // Campos Apellidos
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                var apellidoPFocused by remember { mutableStateOf(false) }
                                OutlinedTextField(
                                    value = apellidoPaterno,
                                    onValueChange = { apellidoPaterno = it },
                                    label = {
                                        Text(
                                            "Apellido Paterno",
                                            color = if (apellidoPFocused) Color(0xFF4CAF50) else Color.Gray
                                        )
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .onFocusChanged { apellidoPFocused = it.isFocused },
                                    shape = RoundedCornerShape(14.dp),
                                    colors = textFieldColors()
                                )

                                var apellidoMFocused by remember { mutableStateOf(false) }
                                OutlinedTextField(
                                    value = apellidoMaterno,
                                    onValueChange = { apellidoMaterno = it },
                                    label = {
                                        Text(
                                            "Apellido Materno",
                                            color = if (apellidoMFocused) Color(0xFF4CAF50) else Color.Gray
                                        )
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .onFocusChanged { apellidoMFocused = it.isFocused },
                                    shape = RoundedCornerShape(14.dp),
                                    colors = textFieldColors()
                                )
                            }

                            // Campo Teléfono
                            var telefonoFocused by remember { mutableStateOf(false) }
                            OutlinedTextField(
                                value = telefono,
                                onValueChange = { telefono = it },
                                label = {
                                    Text(
                                        "Teléfono",
                                        color = if (telefonoFocused) Color(0xFF4CAF50) else Color.Gray
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Phone,
                                        contentDescription = "Teléfono",
                                        tint = if (telefonoFocused) Color(0xFF4CAF50) else Color.Gray
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onFocusChanged { telefonoFocused = it.isFocused },
                                shape = RoundedCornerShape(14.dp),
                                colors = textFieldColors(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                            )

                            // Campo Fecha de Nacimiento
                            var fechaFocused by remember { mutableStateOf(false) }
                            OutlinedTextField(
                                value = fechaNacimiento,
                                onValueChange = { fechaNacimiento = it },
                                label = {
                                    Text(
                                        "Fecha de Nacimiento",
                                        color = if (fechaFocused) Color(0xFF4CAF50) else Color.Gray
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = "Fecha Nacimiento",
                                        tint = if (fechaFocused) Color(0xFF4CAF50) else Color.Gray
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onFocusChanged { fechaFocused = it.isFocused },
                                shape = RoundedCornerShape(14.dp),
                                colors = textFieldColors()
                            )

                            // Campo País
                            var paisFocused by remember { mutableStateOf(false) }
                            OutlinedTextField(
                                value = pais,
                                onValueChange = { pais = it },
                                label = {
                                    Text(
                                        "País",
                                        color = if (paisFocused) Color(0xFF4CAF50) else Color.Gray
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "País",
                                        tint = if (paisFocused) Color(0xFF4CAF50) else Color.Gray
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onFocusChanged { paisFocused = it.isFocused },
                                shape = RoundedCornerShape(14.dp),
                                colors = textFieldColors()
                            )

                            // Campo Correo
                            var correoFocused by remember { mutableStateOf(false) }
                            OutlinedTextField(
                                value = correo,
                                onValueChange = { correo = it },
                                label = {
                                    Text(
                                        "Correo Electrónico",
                                        color = if (correoFocused) Color(0xFF4CAF50) else Color.Gray
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = "Correo",
                                        tint = if (correoFocused) Color(0xFF4CAF50) else Color.Gray
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onFocusChanged { correoFocused = it.isFocused },
                                shape = RoundedCornerShape(14.dp),
                                colors = textFieldColors(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                            )
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        // Botón Guardar con animación
                        val buttonColor by animateColorAsState(
                            targetValue = if (isUpdating) Color(0xFF2E7D32) else Color(0xFF4CAF50),
                            animationSpec = tween(300)
                        )

                        var buttonPressed by remember { mutableStateOf(false) }
                        Button(
                            onClick = {
                                buttonPressed = true
                                Log.d("PerfilScreen", "Guardando cambios del perfil")
                                val actualizado = usuario.copy(
                                    nombre = nombre,
                                    apellidoPaterno = apellidoPaterno,
                                    apellidoMaterno = apellidoMaterno,
                                    telefono = telefono,
                                    fechaNacimiento = fechaNacimiento,
                                    pais = pais,
                                    correo = correo
                                )

                                scope.launch {
                                    try {
                                        onGuardar(actualizado)
                                        snackbarHostState.showSnackbar("Perfil actualizado con éxito")
                                    } catch (e: Exception) {
                                        Log.e("PerfilScreen", "Error al guardar: ${e.message}")
                                        snackbarHostState.showSnackbar("Error al actualizar el perfil: ${e.message ?: "Error desconocido"}")
                                    } finally {
                                        buttonPressed = false
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .scale(if (buttonPressed) 0.98f else 1f),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = buttonColor,
                                contentColor = Color.White
                            ),
                            enabled = !isUpdating
                        ) {
                            if (isUpdating) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(24.dp),
                                    strokeWidth = 3.dp
                                )
                            } else {
                                Text(
                                    text = "Guardar Cambios",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Botón Cancelar con efecto hover
                        navController?.let {
                            var cancelHover by remember { mutableStateOf(false) }
                            TextButton(
                                onClick = {
                                    Log.d("PerfilScreen", "Regresando a pantalla anterior")
                                    navController.popBackStack()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .pointerInput(Unit) {
                                        detectTapGestures(
                                            onPress = {
                                                cancelHover = true
                                                tryAwaitRelease()
                                                cancelHover = false
                                            }
                                        )
                                    }
                            ) {
                                Text(
                                    text = "Cancelar",
                                    color = if (cancelHover) Color(0xFF2E7D32) else Color(0xFF4CAF50),
                                    fontSize = 16.sp,
                                    fontWeight = if (cancelHover) FontWeight.Bold else FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController?, currentScreen: String) {
    val items = listOf(
        BottomNavItem("perfil", "Perfil", Icons.Default.Person),
        BottomNavItem("recomendaciones", "Recomendaciones", Icons.Default.Add),
        BottomNavItem("estadisticas", "Estadísticas", Icons.Default.Info)
    )

    NavigationBar(
        containerColor = Color(0xFFE8F5E9),
        contentColor = Color(0xFF2E7D32)
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentScreen == item.route,
                onClick = {
                    navController?.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF1B5E20),
                    selectedTextColor = Color(0xFF1B5E20),
                    unselectedIconColor = Color(0xFF2E7D32),
                    unselectedTextColor = Color(0xFF2E7D32),
                    indicatorColor = Color(0xFFC8E6C9)
                )
            )
        }
    }
}

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
)

@Composable
private fun textFieldColors() = TextFieldDefaults.colors(
    focusedContainerColor = Color.White,
    unfocusedContainerColor = Color.White,
    focusedIndicatorColor = Color(0xFF4CAF50),
    unfocusedIndicatorColor = Color.LightGray,
    focusedLabelColor = Color(0xFF4CAF50),
    focusedLeadingIconColor = Color(0xFF4CAF50)
)