package com.example.aeroquickchat

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import com.example.aeroquickchat.databinding.ToastErrorBinding
import java.net.UnknownHostException
import java.net.SocketTimeoutException

class ErrorHandler(private val context: Context) {

    enum class ErrorType {
        WHATSAPP_NOT_FOUND,
        NETWORK_ERROR,
        INVALID_PHONE,
        PHONE_EMPTY,
        COUNTRY_NOT_SELECTED,
        GENERIC_ERROR
    }

    fun showError(errorType: ErrorType, exception: Exception? = null, anchorView: ViewGroup? = null) {
        val binding = ToastErrorBinding.inflate(LayoutInflater.from(context))

        when (errorType) {
            ErrorType.WHATSAPP_NOT_FOUND -> {
                binding.errorTitle.text = "WhatsApp Not Found"
                binding.errorMessage.text = context.getString(R.string.error_whatsapp_not_found)
            }

            ErrorType.NETWORK_ERROR -> {
                binding.errorTitle.text = "Network Issue"
                binding.errorMessage.text = context.getString(R.string.error_network_issue)
            }

            ErrorType.INVALID_PHONE -> {
                binding.errorTitle.text = "Invalid Phone Number"
                binding.errorMessage.text = context.getString(R.string.error_invalid_phone)
            }

            ErrorType.PHONE_EMPTY -> {
                binding.errorTitle.text = "Phone Number Required"
                binding.errorMessage.text = context.getString(R.string.error_phone_empty)
            }

            ErrorType.COUNTRY_NOT_SELECTED -> {
                binding.errorTitle.text = "Country Required"
                binding.errorMessage.text = context.getString(R.string.error_country_not_selected)
            }

            ErrorType.GENERIC_ERROR -> {
                binding.errorTitle.text = "Error"
                binding.errorMessage.text = exception?.message ?: context.getString(R.string.error_generic)
            }
        }

        // Create popup window for toast-like notification
        val popupWindow = PopupWindow(
            binding.root,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        popupWindow.elevation = 16f
        popupWindow.animationStyle = R.style.Animation_Toast

        // Show popup at top of screen
        if (anchorView != null) {
            popupWindow.showAtLocation(anchorView, Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)
        }

        // Auto-dismiss after 3 seconds
        binding.root.postDelayed({
            if (popupWindow.isShowing) {
                popupWindow.dismiss()
            }
        }, 3000)
    }

    companion object {
        fun getErrorTypeFromException(exception: Exception): ErrorType {
            return when (exception) {
                is ActivityNotFoundException -> ErrorType.WHATSAPP_NOT_FOUND
                is UnknownHostException, is SocketTimeoutException -> ErrorType.NETWORK_ERROR
                else -> ErrorType.GENERIC_ERROR
            }
        }
    }
}