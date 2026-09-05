package com.agrilo.shared.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest

/**
 * Client Supabase partagé Android/iOS.
 *
 * RÈGLES DE SÉCURITÉ :
 * - Seule la clé "anon" publique est utilisée ici. La clé service_role
 *   ne doit JAMAIS exister dans le code de l'app mobile.
 * - Toute la sécurité repose sur les policies RLS définies côté Supabase.
 * - supabaseUrl et supabaseAnonKey sont injectés au build, jamais codés
 *   en dur ici.
 */
object SupabaseClientProvider {

    fun create(supabaseUrl: String, supabaseAnonKey: String) =
        createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseAnonKey
        ) {
            install(GoTrue)
            install(Postgrest)
        }
}