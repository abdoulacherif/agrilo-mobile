package com.agrilo.shared.security

import platform.Foundation.NSMutableDictionary
import platform.Security.*
import kotlinx.cinterop.*

/**
 * Implémentation iOS : les tokens sont stockés dans le Keychain,
 * jamais dans NSUserDefaults (qui n'est pas chiffré).
 *
 * NOTE : implémentation simplifiée à titre d'exemple — en production,
 * préférer une librairie Keychain KMP éprouvée plutôt que réimplémenter
 * l'appel au Security framework à la main (la lecture getToken() ci-dessous
 * n'est pas complète).
 */
@OptIn(ExperimentalForeignApi::class)
actual class SecureSessionStorage {

    private val service = "com.agrilo.session"

    actual fun saveToken(key: String, value: String) {
        clearToken(key)
        val data = value.encodeToByteArray()
        val query = NSMutableDictionary().apply {
            setObject(kSecClassGenericPassword, forKey = kSecClass as Any)
            setObject(service, forKey = kSecAttrService as Any)
            setObject(key, forKey = kSecAttrAccount as Any)
            setObject(data.toNSData(), forKey = kSecValueData as Any)
            setObject(kSecAttrAccessibleWhenUnlockedThisDeviceOnly, forKey = kSecAttrAccessible as Any)
        }
        SecItemAdd(query as CFDictionaryRef, null)
    }

    actual fun getToken(key: String): String? {
        return null // placeholder — voir note ci-dessus
    }

    actual fun clearToken(key: String) {
        val query = NSMutableDictionary().apply {
            setObject(kSecClassGenericPassword, forKey = kSecClass as Any)
            setObject(service, forKey = kSecAttrService as Any)
            setObject(key, forKey = kSecAttrAccount as Any)
        }
        SecItemDelete(query as CFDictionaryRef)
    }

    private fun ByteArray.toNSData(): platform.Foundation.NSData = memScoped {
        platform.Foundation.NSData.create(bytes = allocArrayOf(this@toNSData), length = this@toNSData.size.toULong())
    }
}