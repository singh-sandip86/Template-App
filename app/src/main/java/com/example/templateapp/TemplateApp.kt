package com.example.templateapp

import android.app.Application
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.appcompat.app.AppCompatDelegate
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.templateapp.api.Environment
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TemplateApp : Application() {

    companion object {
        val environment = Environment.fromBuildFlavor(BuildConfig.FLAVOR)
        lateinit var sharedPrefs: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setupEncryptedSharedPreference()
    }

    private fun setupEncryptedSharedPreference() {
        val spec = KeyGenParameterSpec.Builder(
            MasterKey.DEFAULT_MASTER_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).apply {
            setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
        }.build()

        // creating a master key for encryption of shared preferences.
        val masterKey = MasterKey.Builder(applicationContext).apply {
            setKeyGenParameterSpec(spec)
        }.build()

        // Initialize/open an instance of EncryptedSharedPreferences on below line.
        sharedPrefs =
            EncryptedSharedPreferences.create(
                applicationContext,
                "${packageName}_${javaClass.simpleName}",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
    }
}