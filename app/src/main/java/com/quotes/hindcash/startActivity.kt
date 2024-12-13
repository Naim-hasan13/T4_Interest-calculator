package com.quotes.hindcash

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.quotes.hindcash.databinding.ActivityHomeBinding
import com.quotes.hindcash.databinding.ActivityStartBinding


class startActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up click listener for cv_Start_Calculator
        binding.cvStart.setOnClickListener {
            val intent = Intent(this, loadingActivity::class.java)
            startActivity(intent)
            finish() // Optionally close StartActivity
        }
    }
}
