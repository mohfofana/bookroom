package com.example.easybooking.screens

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.easybooking.Salle
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController


@Composable
fun InfoSalleDialog(salle: Salle, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Informations sur ${salle.nom}", fontWeight = FontWeight.Bold)
        },
        text = {
            Column {
                Text(text = "Capacité: ${salle.capacite} personnes", fontSize = 16.sp)
                Text(text = "Statut: ${if (salle.disponible) "Disponible" else "Occupée"}", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Détails supplémentaires à venir...", fontSize = 14.sp)
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Fermer")
            }
        }
    )
}
