package com.agrilo.shared.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import com.agrilo.shared.domain.Formation

/**
 * L'app mobile ne fait QUE lire les formations publiées.
 * La création/édition reste réservée à l'admin panel web
 * (agrilo-web/src/app/admin) — aucune route d'écriture ici : même si
 * le code de l'app était modifié pour tenter d'écrire, la policy RLS
 * `modules` ne donne aucun droit INSERT/UPDATE aux rôles standards.
 */
class FormationRepository(private val client: SupabaseClient) {

    suspend fun getPublishedFormations(): List<Formation> {
        return client.postgrest["modules"]
            .select {
                filter { eq("published", true) }
                order("title", io.github.jan.supabase.postgrest.query.Order.ASCENDING)
            }
            .decodeList()
    }
}