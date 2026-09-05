package com.agrilo.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // L'app se connecte via (application as AgriloApplication).supabaseClient
        // Écrans à brancher : connexion, catalogue, détail formation, profil.
    }
}