package com.quotes.hindcash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.quotes.hindcash.databinding.ActivityStartBinding


class startActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up click listener for cv_Start_Calculator
        binding.cvStart.setOnClickListener {
            val intent = Intent(this, loadingActivity::class.java)
            startActivity(intent)
            finish() // Optionally close StartActivity
        }
    }
}
