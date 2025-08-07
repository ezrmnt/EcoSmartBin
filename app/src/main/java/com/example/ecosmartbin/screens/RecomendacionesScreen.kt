package com.example.ecosmartbin.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ecosmartbin.R

@Composable
fun RecomendacionesScreen(
    navController: NavController) {
    val recomendaciones = listOf(
        Triple("Orgánico", "Restos de comida, cáscaras, papel sucio. → Usa bolsas compostables.", R.drawable.organic),
        Triple("Inorgánico reciclable", "Botellas PET, cartón limpio, vidrio. → Enjuaga antes de tirar.", R.drawable.recyclable),
        Triple("Inorgánico no reciclable", "Papel plastificado, pañales, colillas. → Evita mezclarlos.", R.drawable.non_recyclable),
        Triple("Peligrosos", "Pilas, medicinas, químicos. → Lleva a centros especiales.", R.drawable.danger),
        Triple("Tecnológicos", "Celulares, cables, cargadores. → No los deseches en casa.", R.drawable.tecno)
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFE8F5E9),
                    Color(0xFFF0F0F0)
                ),
                startY = 0f,
                endY = 1000f
            )
        )
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Guía de Reciclaje",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF2E7D32),
                modifier = Modifier.padding(bottom = 8.dp),
                fontWeight = FontWeight.Bold
            )

            recomendaciones.forEachIndexed { index, (tipo, detalle, iconRes) ->
                val enterAnimation by animateFloatAsState(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = 500 + (index * 100),
                        delayMillis = 100,
                        easing = FastOutSlowInEasing
                    )
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(enterAnimation)
                        .clickable { },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = if (index % 2 == 0) 4.dp else 8.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        color = Color(0xFF2E7D32).copy(alpha = 0.1f),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = iconRes),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = tipo,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32),
                                fontSize = 18.sp,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Text(
                            text = detalle,
                            color = Color.DarkGray,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            modifier = Modifier.padding(start = 52.dp)
                        )
                    }
                }
            }
        }

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
                    icon = Icons.Default.Home,
                    label = "Inicio",
                    onClick = { navController.popBackStack()}
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
                    onClick = { /* Ya estás en esta screen */ }
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
fun NavigationButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val animatedColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF2E7D32) else Color.Gray,
        animationSpec = tween(durationMillis = 300)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
            .width(80.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = animatedColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = animatedColor,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}