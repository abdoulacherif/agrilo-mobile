package com.agrilo.shared.security

/**
 * Stockage sécurisé du token de session.
 *
 * Jamais de token en clair (SharedPreferences brut, UserDefaults brut,
 * fichier texte, log). Chaque plateforme fournit sa propre implémentation
 * chiffrée : EncryptedSharedPreferences sur Android, Keychain sur iOS.
 */
expect class SecureSessionStorage {
    fun saveToken(key: String, value: String)
    fun getToken(key: String): String?
    fun clearToken(key: String)
}