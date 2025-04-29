package com.example.easybooking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.text.font.FontWeight


@Composable
fun SallesScreen(navController: NavController) {
    // Liste des salles (données de test)
    val salles = listOf(
        Salle(1, "Salle Émeraude", 20, true, R.drawable.salle1),
        Salle(2, "Salle Rubis", 15, false, R.drawable.salle2),
        Salle(3, "Salle Saphir", 30, true, R.drawable.salle3),
        Salle(4, "Salle Diamant", 50, true, R.drawable.salle1),
        Salle(5, "Salle Topaze", 10, true, R.drawable.salle2),
        Salle(6, "Salle Ambre", 25, false, R.drawable.salle3)
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Liste des Salles",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(salles) { salle ->
                SalleCard(salle, navController)
            }
        }
    }
}
