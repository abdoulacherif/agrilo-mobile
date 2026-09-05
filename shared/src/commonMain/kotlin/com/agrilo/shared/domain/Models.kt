package com.agrilo.shared.domain

import kotlinx.serialization.Serializable

@Serializable
data class Formation(
    val id: String,
    val title: String,
    val slug: String,
    val description: String,
    val filiere: String,
    val niveau: String
)

@Serializable
data class Progression(
    val moduleId: String,
    val statut: String, // "en_cours" | "termine"
    val updatedAt: String? = null
)

enum class UserRole {
    ADMIN, FORMATEUR, APPRENANT;

    companion object {
        fun fromString(value: String) = when (value) {
            "admin" -> ADMIN
            "formateur" -> FORMATEUR
            else -> APPRENANT
        }
    }
}