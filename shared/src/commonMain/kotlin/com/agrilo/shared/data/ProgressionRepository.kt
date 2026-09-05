package com.agrilo.shared.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.postgrest.postgrest
import com.agrilo.shared.domain.Progression
import kotlinx.serialization.Serializable

@Serializable
private data class ProgressionInsert(
    val user_id: String,
    val module_id: String,
    val statut: String
)

class ProgressionRepository(private val client: SupabaseClient) {

    suspend fun getMyProgression(): List<Progression> {
        val userId = client.gotrue.currentUserOrNull()?.id ?: return emptyList()
        return client.postgrest["progression"]
            .select { filter { eq("user_id", userId) } }
            .decodeList()
    }

    suspend fun updateProgression(moduleId: String, statut: String) {
        // user_id pris de la session courante, jamais passé en paramètre
        // par l'appelant — impossible d'écrire la progression de
        // quelqu'un d'autre même en modifiant l'app.
        val userId = client.gotrue.currentUserOrNull()?.id
            ?: throw IllegalStateException("Utilisateur non connecté")

        client.postgrest["progression"].upsert(
            ProgressionInsert(user_id = userId, module_id = moduleId, statut = statut)
        )
    }
}