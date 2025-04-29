package com.example.easybooking

data class Salle(
    val id: Int,
    val nom: String,
    val capacite: Int,
    val disponible: Boolean,
    val imageResId: Int
)
