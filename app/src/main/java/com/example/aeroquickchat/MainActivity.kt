package com.example.aeroquickchat

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import com.example.aeroquickchat.databinding.ActivityMainBinding
import android.content.ActivityNotFoundException
import android.view.Menu
import android.view.MenuItem



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var errorHandler: ErrorHandler
    private lateinit var sharedPreferences: SharedPreferences
    private val defaultCountryCode: String = "880" // Default Bangladesh
    
    companion object {
        const val SETTINGS_REQUEST_CODE = 1001
    }
    
    private fun setPhoneInputError(hasError: Boolean) {
        if (hasError) {
            binding.phoneNumberInputLayout.boxStrokeColor = ContextCompat.getColor(this, android.R.color.holo_red_dark)
            binding.phoneNumberInputLayout.hintTextColor = ContextCompat.getColorStateList(this, android.R.color.holo_red_dark)
        } else {
            binding.phoneNumberInputLayout.boxStrokeColor = ContextCompat.getColor(this, R.color.primary_color)
            binding.phoneNumberInputLayout.hintTextColor = ContextCompat.getColorStateList(this, R.color.text_secondary)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize shared preferences first
        sharedPreferences = getSharedPreferences(SettingsActivity.PREFS_NAME, MODE_PRIVATE)
        
        // Apply saved theme before setting content view
        applySavedTheme()
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        
        // Set up toolbar as action bar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        
        // Initialize components
        errorHandler = ErrorHandler(this)
        
        // Auto-detection setup - Bangladesh as default
        
        // Clear error styling when user starts typing
        binding.phoneNumberInput.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setPhoneInputError(false)
            }
            override fun afterTextChanged(s: android.text.Editable?) {}
        })

        binding.openWhatsAppButton.setOnClickListener {
            val phoneNumber = binding.phoneNumberInput.text.toString().trim()
            val message = binding.messageInput.text.toString().trim()
            
            // Validate input
            when {
                phoneNumber.isEmpty() -> {
                    setPhoneInputError(true)
                    errorHandler.showError(ErrorHandler.ErrorType.PHONE_EMPTY, null, binding.root)
                    return@setOnClickListener
                }
                !isValidPhoneNumber(phoneNumber) -> {
                    setPhoneInputError(true)
                    errorHandler.showError(ErrorHandler.ErrorType.INVALID_PHONE, null, binding.root)
                    return@setOnClickListener
                }
                else -> {
                    setPhoneInputError(false)
                }
            }

            val formattedNumber = formatPhoneNumber(phoneNumber)
            val encodedMessage = if (message.isNotEmpty()) {
                Uri.encode(message)
            } else {
                ""
            }
            
            val uri = if (encodedMessage.isNotEmpty()) {
                Uri.parse("https://wa.me/$formattedNumber?text=$encodedMessage")
            } else {
                Uri.parse("https://wa.me/$formattedNumber")
            }
            
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        // Back button removed - using toolbar navigation instead
    }
    
    private fun detectCountryFromNumber(phoneNumber: String): String {
        val number = phoneNumber.replace("[^0-9]".toRegex(), "")
        
        return when {
            number.startsWith("880") -> "880" // Bangladesh
            number.startsWith("91") -> "91"   // India
            number.startsWith("92") -> "92"   // Pakistan
            number.startsWith("1") -> "1"     // US/Canada
            number.startsWith("44") -> "44"   // UK
            number.startsWith("61") -> "61"   // Australia
            number.startsWith("49") -> "49"   // Germany
            number.startsWith("33") -> "33"   // France
            number.startsWith("81") -> "81"   // Japan
            else -> defaultCountryCode // Default to Bangladesh
        }
    }
    

    // Format phone numbers with auto-detection
    private fun formatPhoneNumber(phoneNumber: String): String {
        val number = phoneNumber.replace("[^0-9]".toRegex(), "")
        val detectedCountryCode = detectCountryFromNumber(number)
        
        return when (detectedCountryCode) {
            "880" -> { // Bangladesh
                when {
                    number.startsWith("880") -> number
                    number.startsWith("0") -> "880" + number.substring(1)
                    number.length == 10 -> "880" + number
                    else -> "880" + number
                }
            }
            "91" -> { // India
                when {
                    number.startsWith("91") -> number
                    number.length == 10 -> "91" + number
                    else -> "91" + number
                }
            }
            else -> { // Other countries
                if (number.startsWith(detectedCountryCode)) {
                    number
                } else {
                    detectedCountryCode + number
                }
            }
        }
    }

    // Phone number validation with auto-detection
    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val clean = phoneNumber.replace("[^0-9]".toRegex(), "")
        val detectedCountryCode = detectCountryFromNumber(clean)
        
        return when (detectedCountryCode) {
            "880" -> { // Bangladesh
                when {
                    clean.matches(Regex("^8801[3-9][0-9]{8}\$")) -> true
                    clean.matches(Regex("^01[3-9][0-9]{8}\$")) -> true
                    clean.matches(Regex("^1[3-9][0-9]{8}\$")) -> true
                    else -> false
                }
            }
            "91" -> { // India
                when {
                    clean.matches(Regex("^91[6-9][0-9]{9}\$")) -> true
                    clean.matches(Regex("^[6-9][0-9]{9}\$")) -> true
                    else -> false
                }
            }
            "92" -> { // Pakistan
                when {
                    clean.matches(Regex("^92[0-9]{10}\$")) -> true
                    clean.matches(Regex("^[0-9]{10,11}\$")) -> true
                    else -> false
                }
            }
            else -> { // Basic validation for other countries
                clean.length >= 7 && clean.length <= 15
            }
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                try {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivityForResult(intent, SETTINGS_REQUEST_CODE)
                } catch (e: Exception) {
                    errorHandler.showError(ErrorHandler.ErrorType.GENERIC_ERROR, e, binding.root)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SETTINGS_REQUEST_CODE && resultCode == RESULT_OK) {
            // Settings were updated, show confirmation
            Toast.makeText(this, "✅ Settings updated successfully!", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun applySavedTheme() {
        val savedTheme = sharedPreferences.getInt(SettingsActivity.KEY_THEME_MODE, SettingsActivity.THEME_SYSTEM)
        when (savedTheme) {
            SettingsActivity.THEME_LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            SettingsActivity.THEME_DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            SettingsActivity.THEME_SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}