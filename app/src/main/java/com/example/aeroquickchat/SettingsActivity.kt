package com.example.aeroquickchat

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.aeroquickchat.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val PREFS_NAME = "AeroQuickChatPrefs"
        const val KEY_THEME_MODE = "theme_mode"
        const val THEME_LIGHT = 0
        const val THEME_DARK = 1
        const val THEME_SYSTEM = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        // Set up the Toolbar as the ActionBar
        setSupportActionBar(binding.toolbar) // Assuming the Toolbar ID is 'toolbar'
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Show the back button

        setupUI()
        loadSavedTheme()
    }

    private fun setupUI() {

        binding.themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.lightThemeRadio -> saveThemePreference(THEME_LIGHT)
                R.id.darkThemeRadio -> saveThemePreference(THEME_DARK)
                R.id.systemThemeRadio -> saveThemePreference(THEME_SYSTEM)
            }
        }
    }

    // Handle Toolbar back button clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed() // Or finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadSavedTheme() {
        val savedTheme = sharedPreferences.getInt(KEY_THEME_MODE, THEME_SYSTEM)
        when (savedTheme) {
            THEME_LIGHT -> binding.lightThemeRadio.isChecked = true
            THEME_DARK -> binding.darkThemeRadio.isChecked = true
            THEME_SYSTEM -> binding.systemThemeRadio.isChecked = true
        }
    }

    private fun saveThemePreference(themeMode: Int) {
        sharedPreferences.edit()
            .putInt(KEY_THEME_MODE, themeMode)
            .apply()

        applyTheme(themeMode)
    }

    private fun applyTheme(themeMode: Int) {
        when (themeMode) {
            THEME_LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            THEME_DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            THEME_SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    
}