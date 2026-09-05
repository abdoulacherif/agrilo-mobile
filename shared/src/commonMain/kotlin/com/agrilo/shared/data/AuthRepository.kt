package com.agrilo.shared.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import com.agrilo.shared.domain.UserRole

/**
 * Gère la connexion/inscription et la lecture du rôle utilisateur.
 *
 * Important : le rôle n'est JAMAIS stocké ou décidé localement dans l'app.
 * Il est relu depuis la table `profiles` à chaque besoin, via une requête
 * protégée par RLS — exactement comme côté web (requireAdmin() dans
 * agrilo-web). Un utilisateur qui modifierait le code de l'app ne peut
 * pas "se donner" le rôle admin : la base ne le lui accordera jamais
 * sans policy correspondante.
 */
class AuthRepository(private val client: SupabaseClient) {

    suspend fun signIn(email: String, password: String) {
        client.gotrue.loginWith(Email) {
            this.email = email
            this.password = password
        }
    }

    suspend fun signUp(email: String, password: String) {
        // Le compte créé ici est toujours "apprenant" par défaut côté
        // base (trigger handle_new_user()) — l'app n'envoie aucun rôle.
        client.gotrue.signUpWith(Email) {
            this.email = email
            this.password = password
        }
    }

    suspend fun signOut() {
        client.gotrue.logout()
    }

    suspend fun currentRole(): UserRole {
        val userId = client.gotrue.currentUserOrNull()?.id ?: return UserRole.APPRENANT

        val profile = client.postgrest["profiles"]
            .select(columns = Columns.list("role")) {
                filter { eq("id", userId) }
            }
            .decodeSingle<Map<String, String>>()

        return UserRole.fromString(profile["role"] ?: "apprenant")
    }
}