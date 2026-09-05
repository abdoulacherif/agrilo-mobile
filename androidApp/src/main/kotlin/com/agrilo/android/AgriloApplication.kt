package com.agrilo.android

import android.app.Application
import com.agrilo.shared.data.SupabaseClientProvider
import io.github.jan.supabase.SupabaseClient

class AgriloApplication : Application() {

    lateinit var supabaseClient: SupabaseClient
        private set

    override fun onCreate() {
        super.onCreate()

        // Les clés sont injectées via BuildConfig (issues de local.properties
        // en dev, des secrets CI en prod) — jamais codées en dur ici.
        // Seule la clé anon publique existe côté mobile.
        supabaseClient = SupabaseClientProvider.create(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseAnonKey = BuildConfig.SUPABASE_ANON_KEY
        )
    }
}