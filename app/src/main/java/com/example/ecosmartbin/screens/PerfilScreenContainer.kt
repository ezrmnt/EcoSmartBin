package com.example.ecosmartbin.screens

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ecosmartbin.viewmodel.UsuarioViewModel
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PerfilScreenContainer(
    id: Int,
    navController: NavHostController,
    viewModel: UsuarioViewModel = viewModel()
) {
    val usuarioState = viewModel.usuario.collectAsState()
    val isUpdatingState = viewModel.isUpdating.collectAsState()

    val usuario = usuarioState.value
    val isUpdating = isUpdatingState.value

    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(id) {
        viewModel.cargarUsuario(id)
    }

    LaunchedEffect(Unit) {
        viewModel.eventoUI.collectLatest { evento ->
            if (evento == "Usuario actualizado con éxito") {
                navController.navigate("welcome/${usuario?.nombre ?: "Usuario"}/${usuario?.id ?: id}") {
                    popUpTo("perfil/$id") { inclusive = true }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE8F5E9),
                        Color(0xFFC8E6C9)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = usuario,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) with
                        fadeOut(animationSpec = tween(300)) using
                        SizeTransform(clip = false)
            }
        ) { targetUsuario ->
            when {
                targetUsuario != null -> {
                    Log.d("PerfilScreenContainer", "Usuario recibido para PerfilScreen: $targetUsuario")
                    PerfilScreen(
                        usuario = targetUsuario,
                        onGuardar = { usuarioActualizado ->
                            viewModel.actualizarUsuario(usuarioActualizado)
                        },
                        isUpdating = isUpdating
                    )
                }
                isUpdating -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(64.dp),
                            color = Color(0xFF2E7D32),
                            strokeWidth = 6.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Cargando tu perfil...",
                            color = Color(0xFF1B5E20),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.alpha(alpha)
                        )
                    }
                }
                else -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Usuario no encontrado",
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF81C784))
                                .padding(16.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No se encontró el perfil",
                            color = Color(0xFF1B5E20),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}