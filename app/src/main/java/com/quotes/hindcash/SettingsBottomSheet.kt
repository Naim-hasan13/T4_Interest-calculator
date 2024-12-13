package com.example.app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.quotes.hindcash.databinding.BottomSheetSettingsBinding

class SettingsBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetSettingsBinding.inflate(inflater, container, false)

        // Handle "How to install widgets" click
        binding.tvInstallWidgets.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com/install-widgets"))
            startActivity(intent)
        }

        // Handle "Close" button click
        binding.ivClose.setOnClickListener {
            dismiss()
        }

        // Handle "Share App" click
        binding.tvShare.setOnClickListener {
            shareApp()
        }

        // Handle "Help and Feedback" click
        binding.tvHelpFeedback.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("support@example.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Help & Feedback")
            }
            startActivity(Intent.createChooser(emailIntent, "Send Email"))
        }

        // Handle "Rate app" click
        binding.tvRateApp.setOnClickListener {
            val rateIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=${requireContext().packageName}")
            )
            startActivity(rateIntent)
        }

        return binding.root
    }

    private fun shareApp() {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(
                Intent.EXTRA_TEXT,
                "Check out this amazing app: https://play.google.com/store/apps/details?id=${requireContext().packageName}"
            )
        }
        startActivity(Intent.createChooser(shareIntent, "Share App"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
