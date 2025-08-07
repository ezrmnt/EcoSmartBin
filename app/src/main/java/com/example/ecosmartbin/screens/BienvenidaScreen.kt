package com.example.ecosmartbin.screens

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecosmartbin.R

@Composable
fun WelcomeScreen(
    username: String,
    userId: Int,
    navController: NavController,
    context: Context
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE8F5E9).copy(alpha = 0.7f),
                        Color.White
                    ),
                    startY = 0f,
                    endY = 1200f
                )
            )
    ) {
        // Decoración de hojas animada
        val infiniteTransition = rememberInfiniteTransition()
        val leafOffset by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 15f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        Image(
            painter = painterResource(id = R.drawable.logoeco),
            contentDescription = "Decoración de hojas",
            modifier = Modifier
                .size(140.dp)
                .align(Alignment.TopEnd)
                .offset(x = 30.dp, y = (-30 + leafOffset).dp),
            colorFilter = ColorFilter.tint(Color(0xFF4CAF50).copy(alpha = 0.15f))
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Tarjeta con logo animada
            val cardScale by animateFloatAsState(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = DampingRatioLowBouncy,
                    stiffness = StiffnessLow
                )
            )

            Card(
                modifier = Modifier
                    .size(140.dp)
                    .scale(cardScale)
                    .shadow(
                        elevation = 16.dp,
                        shape = RoundedCornerShape(28.dp),
                        spotColor = Color(0xFF4CAF50).copy(alpha = 0.3f))
                    .clickable { },
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logoeco),
                        contentDescription = "Eco Icon",
                        colorFilter = ColorFilter.tint(Color(0xFF4CAF50)),
                        modifier = Modifier.size(100.dp))
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Mensaje de bienvenida con animación
            var showWelcome by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                showWelcome = true
            }

            AnimatedVisibility(
                visible = showWelcome,
                enter = fadeIn() + slideInVertically { it / 2 },
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(20.dp),
                            spotColor = Color(0xFF4CAF50).copy(alpha = 0.1f))
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(20.dp))
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¡Bienvenido!",
                        style = MaterialTheme.typography.displaySmall,
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = username,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    Text(
                        text = "Gracias por unirte a nuestra comunidad ecológica",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.DarkGray.copy(alpha = 0.8f),
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Tarjetas de beneficios con animación escalonada
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf(
                    Pair(Icons.Default.CheckCircle, "Sostenible"),
                    Pair(Icons.Default.Face, "Ecológico"),
                    Pair(Icons.Default.Favorite, "Comunidad")
                ).forEachIndexed { index, (icon, text) ->
                    AnimatedVisibility(
                        visible = showWelcome,
                        enter = fadeIn() + slideInHorizontally(
                            initialOffsetX = { if (index % 2 == 0) it else -it },
                            animationSpec = tween(
                                durationMillis = 500 + index * 200,
                                delayMillis = 300
                            )
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        BenefitCard(
                            icon = icon,
                            text = text,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }

        // Barra de navegación inferior (sin modificaciones)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .shadow(elevation = 16.dp),
            tonalElevation = 8.dp,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavigationButton(
                    icon = Icons.Default.Person,
                    label = "Perfil",
                    onClick = { navController.navigate("perfil/$userId") }
                )

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(24.dp)
                        .background(Color.LightGray.copy(alpha = 0.5f))
                )

                NavigationButton(
                    icon = Icons.Default.Add,
                    label = "Recomendaciones",
                    onClick = { navController.navigate("recomendaciones") }
                )

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(24.dp)
                        .background(Color.LightGray.copy(alpha = 0.5f))
                )

                NavigationButton(
                    icon = Icons.Default.Info,
                    label = "Estadísticas",
                    onClick = { navController.navigate("estadisticas") }
                )
            }
        }
    }
}

@Composable
fun BenefitCard(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    var isElevated by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .height(100.dp)
            .padding(vertical = 8.dp)
            .shadow(
                elevation = if (isElevated) 12.dp else 4.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = Color(0xFF4CAF50).copy(alpha = 0.2f))
            .clickable { }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isElevated = true
                        tryAwaitRelease()
                        isElevated = false
                    }
                )
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = text,
                color = Color(0xFF2E7D32),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun NavigationButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color(0xFF2E7D32),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = Color(0xFF2E7D32),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}