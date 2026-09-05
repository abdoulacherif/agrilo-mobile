package com.agrilo.shared.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * Implémentation Android : les tokens sont chiffrés au repos via
 * EncryptedSharedPreferences (AES256-GCM), clé gérée par le Keystore
 * matériel de l'appareil quand disponible.
 */
actual class SecureSessionStorage(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "agrilo_secure_session",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    actual fun saveToken(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    actual fun getToken(key: String): String? = prefs.getString(key, null)

    actual fun clearToken(key: String) {
        prefs.edit().remove(key).apply()
    }
}