package com.example.easybooking.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReserverSalleScreen(
    salleId: Int,
    navController: NavController
) {
    var nom by remember { mutableStateOf(TextFieldValue("")) }
    var classe by remember { mutableStateOf(TextFieldValue("")) }
    var nombrePersonnes by remember { mutableStateOf(TextFieldValue("")) }
    var raison by remember { mutableStateOf(TextFieldValue("")) }
    var heureDebut by remember { mutableStateOf(TextFieldValue("")) }
    var heureFin by remember { mutableStateOf(TextFieldValue("")) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Réservation de salle",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Titre de section pour information personnelle
            SectionTitle(title = "Informations personnelles")

            OutlinedTextField(
                value = nom,
                onValueChange = { nom = it },
                label = { Text("Votre nom") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = classe,
                onValueChange = { classe = it },
                label = { Text("Votre classe") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(12.dp)
            )

            // Titre de section pour détails de réservation
            SectionTitle(title = "Détails de la réservation")

            OutlinedTextField(
                value = nombrePersonnes,
                onValueChange = { nombrePersonnes = it },
                label = { Text("Nombre de personnes") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = raison,
                onValueChange = { raison = it },
                label = { Text("Motif de la réservation") },
                leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(120.dp),
                shape = RoundedCornerShape(12.dp)
            )

            // Titre de section pour horaires
            SectionTitle(title = "Horaires")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = heureDebut,
                    onValueChange = { heureDebut = it },
                    label = { Text("Début") },
                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                    placeholder = { Text("ex: 14h00") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = heureFin,
                    onValueChange = { heureFin = it },
                    label = { Text("Fin") },
                    leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                    placeholder = { Text("ex: 16h00") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Résumé de la réservation
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        "Résumé de votre réservation",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    val infoToShow = listOf(
                        "Salle" to "Salle #$salleId",
                        "Demandeur" to if (nom.text.isNotEmpty()) nom.text else "Non spécifié",
                        "Horaire" to if (heureDebut.text.isNotEmpty() && heureFin.text.isNotEmpty())
                            "${heureDebut.text} - ${heureFin.text}" else "Non spécifié"
                    )

                    infoToShow.forEach { (label, value) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Text(
                                "$label: ",
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                value,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            // Bouton d'envoi
            Button(
                onClick = { showConfirmationDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Envoyer la demande",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    // Dialogue de confirmation
    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = { Text("Confirmer la réservation") },
            text = { Text("Voulez-vous confirmer votre demande de réservation pour la salle #$salleId?") },
            confirmButton = {
                Button(
                    onClick = {
                        // Ici traiter la réservation
                        showConfirmationDialog = false
                        navController.popBackStack()
                    }
                ) {
                    Text("Confirmer")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showConfirmationDialog = false }
                ) {
                    Text("Annuler")
                }
            }
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f))
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}