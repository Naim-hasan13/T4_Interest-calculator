package com.quotes.hindcash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.quotes.hindcash.databinding.ActivityHomeBinding
import com.quotes.hindcash.databinding.ActivityLoadingBinding

class loadingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Delay for 3 seconds before navigating to EgoActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Close LoadingActivity
        }, 3000) // 3000 ms = 3 seconds
    }
}
