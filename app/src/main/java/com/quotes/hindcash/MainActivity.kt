package com.quotes.hindcash
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.quotes.hindcash.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Delay for 2 seconds before navigating to StartActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, startActivity::class.java)
            startActivity(intent)
            finish() // Close MainActivity
        }, 2000) // 2000 ms = 2 seconds
    }
}