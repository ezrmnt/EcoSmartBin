package com.example.ecosmartbin.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecomendacionesScreen(navController: NavController) {
    val recomendaciones = listOf(
        "Orgánico" to "Restos de comida, cáscaras, papel sucio. → Usa bolsas compostables.",
        "Inorgánico reciclable" to "Botellas PET, cartón limpio, vidrio. → Enjuaga antes de tirar.",
        "Inorgánico no reciclable" to "Papel plastificado, pañales, colillas. → Evita mezclarlos.",
        "Peligrosos" to "Pilas, medicinas, químicos. → Lleva a centros especiales.",
        "Tecnológicos" to "Celulares, cables, cargadores. → No los deseches en casa."
    )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF0F0F0))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            recomendaciones.forEach { (tipo, detalle) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = tipo,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32),
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = detalle,
                            color = Color.DarkGray,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }

