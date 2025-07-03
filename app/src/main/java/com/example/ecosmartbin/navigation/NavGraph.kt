package com.example.ecosmartbin.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ecosmartbin.screens.*

@Composable
fun NavGraph(navController: NavHostController) {
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegistroScreen(navController) }
        composable(
            "welcome/{username}/{userId}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType },
                navArgument("userId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Usuario"
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            WelcomeScreen(username = username, userId = userId, navController = navController, context = context)
        }
        composable(
            "perfil/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            PerfilScreenContainer(id)
        }
    }
}
