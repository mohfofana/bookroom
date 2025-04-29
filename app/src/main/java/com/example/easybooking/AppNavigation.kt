package com.example.easybooking.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.easybooking.MainScreen
import com.example.easybooking.ReservationsScreen
import com.example.easybooking.SallesScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController)
        }
        composable("salles") {
            SallesScreen(navController)
        }
        composable("reserversalle/{salleId}") { backStackEntry ->
            val salleId = backStackEntry.arguments?.getString("salleId")?.toIntOrNull()
            if (salleId != null) {
                ReserverSalleScreen(
                    salleId = salleId,
                    navController = navController
                )
            }
        }
        composable("reservations") { ReservationsScreen(navController) }
    }
}
