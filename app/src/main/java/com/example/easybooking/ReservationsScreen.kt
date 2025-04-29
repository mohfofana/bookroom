package com.example.easybooking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationsScreen(navController: NavController) {
    // Faux exemples de réservations
    val reservations = listOf(
        Reservation(1, "Salle Émeraude", "Confirmée", "15 Mai 2025", "14:00 - 16:00", "Réunion d'équipe"),
        Reservation(2, "Salle Rubis", "En attente", "18 Mai 2025", "09:30 - 11:30", "Présentation client"),
        Reservation(3, "Salle Saphir", "Annulée", "22 Mai 2025", "13:00 - 15:00", "Formation"),
        Reservation(4, "Salle Diamant", "Refusée", "25 Mai 2025", "10:00 - 12:00", "Entretien"),
        Reservation(5, "Salle Ambre", "Expirée", "10 Mai 2025", "16:00 - 17:30", "Brainstorming")
    )

    // Séparation des réservations
    val activeReservations = reservations.filter { it.status == "Confirmée" || it.status == "En attente" }
    val historyReservations = reservations.filter { it.status == "Annulée" || it.status == "Refusée" || it.status == "Expirée" }

    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mes Réservations",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Onglets stylisés
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth(),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                            .padding(horizontal = 40.dp),
                        height = 3.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = {
                        Text(
                            "Actives",
                            fontSize = 16.sp,
                            fontWeight = if (selectedTabIndex == 0) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = {
                        Text(
                            "Historique",
                            fontSize = 16.sp,
                            fontWeight = if (selectedTabIndex == 1) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }

            // Contenu selon l'onglet
            when (selectedTabIndex) {
                0 -> ReservationList(reservations = activeReservations)
                1 -> ReservationList(reservations = historyReservations)
            }
        }
    }
}

@Composable
fun ReservationList(reservations: List<Reservation>) {
    if (reservations.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.size(72.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Aucune réservation trouvée",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(reservations) { reservation ->
                ReservationCard(reservation)
            }
        }
    }
}

@Composable
fun ReservationCard(reservation: Reservation) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Indicateur de statut
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(getStatusBackgroundColor(reservation.status).copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = getStatusIcon(reservation.status),
                    contentDescription = null,
                    tint = getStatusColor(reservation.status),
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = reservation.salleName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = reservation.date,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = reservation.time,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = reservation.purpose,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Chip de statut
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = getStatusBackgroundColor(reservation.status).copy(alpha = 0.15f),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = reservation.status,
                    color = getStatusColor(reservation.status),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun getStatusColor(status: String): Color {
    return when (status) {
        "Confirmée" -> MaterialTheme.colorScheme.primary
        "En attente" -> MaterialTheme.colorScheme.tertiary
        "Annulée" -> MaterialTheme.colorScheme.error
        "Refusée" -> Color(0xFFD32F2F) // Rouge
        "Expirée" -> Color(0xFF9E9E9E) // Gris
        else -> MaterialTheme.colorScheme.onSurface
    }
}

@Composable
fun getStatusBackgroundColor(status: String): Color {
    return when (status) {
        "Confirmée" -> MaterialTheme.colorScheme.primary
        "En attente" -> MaterialTheme.colorScheme.tertiary
        "Annulée" -> MaterialTheme.colorScheme.error
        "Refusée" -> Color(0xFFD32F2F) // Rouge
        "Expirée" -> Color(0xFF9E9E9E) // Gris
        else -> MaterialTheme.colorScheme.onSurface
    }
}

@Composable
fun getStatusIcon(status: String) = when (status) {
    "Confirmée" -> Icons.Filled.Check
    "En attente" -> Icons.Filled.Info
    "Annulée" -> Icons.Filled.Close
    "Refusée" -> Icons.Filled.Warning
    "Expirée" -> Icons.Filled.Close
    else -> Icons.Filled.Check
}

// Data class améliorée
data class Reservation(
    val id: Int,
    val salleName: String,
    val status: String, // "Confirmée", "En attente", "Annulée", "Refusée", "Expirée"
    val date: String,
    val time: String,
    val purpose: String
)