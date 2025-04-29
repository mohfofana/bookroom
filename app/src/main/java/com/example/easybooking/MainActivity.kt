// MainActivity.kt
package com.example.easybooking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.easybooking.ui.theme.EasyBookingTheme

data class Salle(
    val id: Int,
    val nom: String,
    val capacite: Int,
    val disponible: Boolean,
    val imageResId: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyBookingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController)
        }
        composable("salles") {
            SallesScreen()
        }
    }
}

@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenue sur EasyBooking !",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Button(
            onClick = { navController.navigate("salles") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Voir les Salles")
        }
    }
}

@Composable
fun SallesScreen() {
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
                SalleCard(salle)
            }
        }
    }
}

// Carte pour afficher chaque salle
@Composable
fun SalleCard(salle: Salle) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            // Image de couverture
            Box(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = salle.imageResId),
                    contentDescription = "Image de ${salle.nom}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Badge de disponibilité
                val backgroundColor = if (salle.disponible)
                    Color(0xFF4CAF50) else Color(0xFFE53935)

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (salle.disponible) "Disponible" else "Occupée",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Informations sur la salle
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = salle.nom,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Capacité: ${salle.capacite} personnes",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { /* Logique pour afficher les infos */ },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        ),
                        shape = RoundedCornerShape(10.dp),  // Moins arrondi (4dp au lieu de la valeur par défaut)
                        contentPadding = PaddingValues(vertical = 6.dp)  // Moins haut
                    ) {
                        Text(
                            "Infos",
                            fontSize = 12.sp  // Texte plus petit
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { /* Logique pour réserver */ },
                        modifier = Modifier.weight(1f),
                        enabled = salle.disponible,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(10.dp),  // Moins arrondi
                        contentPadding = PaddingValues(vertical = 6.dp)  // Moins haut
                    ) {
                        Text(
                            "Réserver",
                            fontSize = 12.sp  // Texte plus petit
                        )
                    }
                }
            }
        }
    }
}