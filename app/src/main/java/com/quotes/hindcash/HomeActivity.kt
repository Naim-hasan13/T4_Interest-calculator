package com.quotes.hindcash

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.model.KeyPath
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback
import com.google.android.material.card.MaterialCardView
import com.kabir.moneytree.extrazz.videoplayyer
import com.quotes.hindcash.databinding.ActivityHomeBinding
import com.quotes.hindcash.extrazz.AdmobX
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Base64
import kotlin.random.Random

class HomeActivity : AppCompatActivity() {
    private var mAdManagerInterstitialAd: AdManagerInterstitialAd? = null

    init {
        System.loadLibrary("keys")
    }

    var isApiCallable = true
    external fun Hatbc(): String
    private val bgList = listOf(
        R.drawable.bg,
        R.drawable.theme_bg1,
        R.drawable.theme_bg2,
        R.drawable.theme_bg3,
        R.drawable.theme_bg4,
        R.drawable.theme_bg5,
        R.drawable.theme_bg6,
        R.drawable.theme_bg7,
        R.drawable.theme_bg8,
        R.drawable.theme_bg9,
        R.drawable.theme_bg10
    )
    var selectedBg = 0
    lateinit var binding: ActivityHomeBinding
    private val allshayariList = listOf(
        Shayari("Breathe in, breathe out. The storm will pass.", "Reducing Anxiety"),
        Shayari("Inhale calm, exhale stress.", "Reducing Anxiety"),
        Shayari("Let go of what you can't control.", "Reducing Anxiety"),
        Shayari("It’s okay to not be okay.", "Reducing Anxiety"),
        Shayari("Peace begins with a smile.", "Reducing Anxiety"),
        Shayari(
            "Do not stress the could haves. If it should have, it would have.",
            "Reducing Anxiety"
        ),
        Shayari("When you feel like giving up, remember why you started.", "Reducing Anxiety"),
        Shayari("Breathe in peace, breathe out negativity.", "Reducing Anxiety"),
        Shayari(
            "Don’t let the noise of others’ opinions drown out your own inner voice.",
            "Reducing Anxiety"
        ),
        Shayari("The only way out is through.", "Reducing Anxiety"),
        Shayari(
            "You don’t have to control your thoughts, just stop letting them control you.",
            "Reducing Anxiety"
        ),
        Shayari("Self-care is how you take your power back.", "Reducing Anxiety"),
        Shayari("Your mind is a garden. Your thoughts are the seeds.", "Reducing Anxiety"),
        Shayari("Take it one step at a time.", "Reducing Anxiety"),
        Shayari("This too shall pass.", "Reducing Anxiety"),
        Shayari(
            "Worrying does not take away tomorrow’s troubles, it takes away today’s peace.",
            "Reducing Anxiety"
        ),
        Shayari("You don’t have to face everything alone.", "Reducing Anxiety"),
        Shayari(
            "Sometimes the hardest thing is to let go of the things you can’t change.",
            "Reducing Anxiety"
        ),
        Shayari("Be kind to yourself. You’re doing your best.", "Reducing Anxiety"),
        Shayari("The present moment is all you need.", "Reducing Anxiety"),
        Shayari("Let your breath be your anchor.", "Reducing Anxiety"),
        Shayari("You are allowed to rest, but never to quit.", "Reducing Anxiety"),
        Shayari("It’s okay to take a break.", "Reducing Anxiety"),
        Shayari("Your thoughts do not control you.", "Reducing Anxiety"),
        Shayari("Focus on what you can control.", "Reducing Anxiety"),
        Shayari("Quiet the mind, and the soul will speak.", "Reducing Anxiety"),
        Shayari("Keep calm and carry on.", "Reducing Anxiety"),
        Shayari(
            "No matter how much it hurts now, someday you’ll look back and realize your struggles changed your life.",
            "Reducing Anxiety"
        ),
        Shayari("Healing takes time, but it’s worth it.", "Reducing Anxiety"),
        Shayari("Don’t be afraid to rest.", "Reducing Anxiety"),
        Shayari("Focus on the good.", "Reducing Anxiety"),
        Shayari(
            "Everything will be okay in the end. If it’s not okay, it’s not the end.",
            "Reducing Anxiety"
        ),
        Shayari("Life is beautiful, full of unexpected surprises.", "General"),
        Shayari("Chase your dreams, they are worth the run.", "General"),
        Shayari("Every moment is a fresh beginning.", "General"),
        Shayari("Let your dreams be your wings.", "General"),
        Shayari("Life is 10% what happens to us and 90% how we react to it.", "General"),
        Shayari(
            "Sometimes the smallest step in the right direction ends up being the biggest step of your life.",
            "General"
        ),
        Shayari("Life is a beautiful struggle.", "General"),
        Shayari("The best way to predict your future is to create it.", "General"),
        Shayari("Embrace the glorious mess that you are.", "General"),
        Shayari(
            "Don’t wait for the perfect moment. Take the moment and make it perfect.",
            "General"
        ),
        Shayari("A smile is a curve that sets everything straight.", "General"),
        Shayari("Success is the sum of small efforts, repeated day in and day out.", "General"),
        Shayari("Make today so awesome that yesterday gets jealous.", "General"),
        Shayari("The best time for new beginnings is now.", "General"),
        Shayari("Never stop dreaming.", "General"),
        Shayari(
            "Keep your face always toward the sunshine—and shadows will fall behind you.",
            "General"
        ),
        Shayari("The only way to do great work is to love what you do.", "General"),
        Shayari("Dream it. Believe it. Achieve it.", "General"),
        Shayari("When one door of happiness closes, another opens.", "General"),
        Shayari("Believe in yourself, and you’ll be unstoppable.", "General"),
        Shayari("Your life does not get better by chance, it gets better by change.", "General"),
        Shayari("Stay positive and happy.", "General"),
        Shayari("Life is short, make it sweet.", "General"),
        Shayari("Everything you can imagine is real.", "General"),
        Shayari("Be yourself; everyone else is already taken.", "General"),
        Shayari("The best way out is always through.", "General"),
        Shayari("Sometimes you have to create your own sunshine.", "General"),
        Shayari("Start where you are. Use what you have. Do what you can.", "General"),
        Shayari("Dream big. Work hard. Stay focused.", "General"),
        Shayari("Be the change you want to see in the world.", "General"),
        Shayari("You are stronger than you think.", "General"),
        Shayari("In the middle of difficulty lies opportunity.", "General"),
        Shayari(
            "Success doesn’t come from what you do occasionally, it comes from what you do consistently.",
            "General"
        ),
        Shayari("Growth comes when you step out of your comfort zone.", "Personal Growth"),
        Shayari("Believe in yourself, your potential is limitless.", "Personal Growth"),
        Shayari("Every struggle is a step toward growth.", "Personal Growth"),
        Shayari(
            "Success is the sum of small efforts, repeated day in and day out.",
            "Personal Growth"
        ),
        Shayari(
            "Don’t wait for the perfect moment. Take the moment and make it perfect.",
            "Personal Growth"
        ),
        Shayari("The best way to predict the future is to create it.", "Personal Growth"),
        Shayari(
            "Success is not final, failure is not fatal: It is the courage to continue that counts.",
            "Personal Growth"
        ),
        Shayari("The only way to do great work is to love what you do.", "Personal Growth"),
        Shayari("Doubt kills more dreams than failure ever will.", "Personal Growth"),
        Shayari("The secret of getting ahead is getting started.", "Personal Growth"),
        Shayari("You grow through what you go through.", "Personal Growth"),
        Shayari("You are your only limit.", "Personal Growth"),
        Shayari("Don’t limit your challenges. Challenge your limits.", "Personal Growth"),
        Shayari("Take the risk or lose the chance.", "Personal Growth"),
        Shayari("Your growth will determine your path.", "Personal Growth"),
        Shayari("Don’t be afraid to fail, be afraid not to try.", "Personal Growth"),
        Shayari("Believe in yourself and all that you are.", "Personal Growth"),
        Shayari("Great things never come from comfort zones.", "Personal Growth"),
        Shayari("Push yourself because no one else is going to do it for you.", "Personal Growth"),
        Shayari("Be stronger than your strongest excuse.", "Personal Growth"),
        Shayari("Success starts with self-discipline.", "Personal Growth"),
        Shayari("If you can dream it, you can achieve it.", "Personal Growth"),
        Shayari("Believe in the power of yet.", "Personal Growth"),
        Shayari(
            "Your time is limited, don’t waste it living someone else’s life.",
            "Personal Growth"
        ),
        Shayari("Your only limit is you.", "Personal Growth"),
        Shayari("Stay focused and never give up.", "Personal Growth"),
        Shayari(
            "Success is not how high you have climbed, but how you make a positive difference to the world.",
            "Personal Growth"
        ),
        Shayari("Do something today that your future self will thank you for.", "Personal Growth"),
        Shayari(
            "If you want to fly, you have to give up the things that weigh you down.",
            "Personal Growth"
        ),
        Shayari(
            "Success is the result of preparation, hard work, and learning from failure.",
            "Personal Growth"
        ),
        Shayari("Your strength lies in your belief in yourself.", "Believing in Yourself"),
        Shayari(
            "Don’t doubt your power. Believe in what you can achieve.",
            "Believing in Yourself"
        ),
        Shayari("You are stronger than you think.", "Believing in Yourself"),
        Shayari(
            "The only limit to our realization of tomorrow is our doubts of today.",
            "Believing in Yourself"
        ),
        Shayari("Believe in yourself and all that you are.", "Believing in Yourself"),
        Shayari("Trust yourself. You know more than you think you do.", "Believing in Yourself"),
        Shayari("Don’t be afraid to be amazing.", "Believing in Yourself"),
        Shayari(
            "The power of believing in yourself will change your world.",
            "Believing in Yourself"
        ),
        Shayari("You were born to be real, not to be perfect.", "Believing in Yourself"),
        Shayari("Believe in your dreams and they may come true.", "Believing in Yourself"),
        Shayari("With self-belief, you can accomplish anything.", "Believing in Yourself"),
        Shayari("You are capable of amazing things.", "Believing in Yourself"),
        Shayari("Self-belief is the key to success.", "Believing in Yourself"),
        Shayari("The biggest limit to success is self-doubt.", "Believing in Yourself"),
        Shayari("Believe you can and you're halfway there.", "Believing in Yourself"),
        Shayari("Your dreams are valid.", "Believing in Yourself"),
        Shayari("Confidence comes from within.", "Believing in Yourself"),
        Shayari(
            "Never doubt yourself. Your courage will take you to places.",
            "Believing in Yourself"
        ),
        Shayari("You are worthy of success and happiness.", "Believing in Yourself"),
        Shayari("If you believe in yourself, anything is possible.", "Believing in Yourself"),
        Shayari("Believe in your ability to unlock the magic within.", "Believing in Yourself"),
        Shayari("Your self-belief is your greatest asset.", "Believing in Yourself"),
        Shayari("If you don’t believe in yourself, who will?", "Believing in Yourself"),
        Shayari("You have the power to shape your future.", "Believing in Yourself"),
        Shayari(
            "The way to get started is to quit talking and begin doing.",
            "Believing in Yourself"
        ),
        Shayari("Believe in yourself, and the world will believe in you.", "Believing in Yourself"),
        Shayari(
            "If you believe in yourself, there is nothing you can’t achieve.",
            "Believing in Yourself"
        ),
        Shayari(
            "Your belief in yourself is what will bring you the best success.",
            "Believing in Yourself"
        )


    )

    // Expanded Shayari lists for each category
    private val generalShayariList = listOf(
        Shayari("Life is beautiful, full of unexpected surprises.", "General"),
        Shayari("Chase your dreams, they are worth the run.", "General"),
        Shayari("Every moment is a fresh beginning.", "General"),
        Shayari("Let your dreams be your wings.", "General"),
        Shayari("Life is 10% what happens to us and 90% how we react to it.", "General"),
        Shayari(
            "Sometimes the smallest step in the right direction ends up being the biggest step of your life.",
            "General"
        ),
        Shayari("Life is a beautiful struggle.", "General"),
        Shayari("The best way to predict your future is to create it.", "General"),
        Shayari("Embrace the glorious mess that you are.", "General"),
        Shayari(
            "Don’t wait for the perfect moment. Take the moment and make it perfect.",
            "General"
        ),
        Shayari("A smile is a curve that sets everything straight.", "General"),
        Shayari("Success is the sum of small efforts, repeated day in and day out.", "General"),
        Shayari("Make today so awesome that yesterday gets jealous.", "General"),
        Shayari("The best time for new beginnings is now.", "General"),
        Shayari("Never stop dreaming.", "General"),
        Shayari(
            "Keep your face always toward the sunshine—and shadows will fall behind you.",
            "General"
        ),
        Shayari("The only way to do great work is to love what you do.", "General"),
        Shayari("Dream it. Believe it. Achieve it.", "General"),
        Shayari("When one door of happiness closes, another opens.", "General"),
        Shayari("Believe in yourself, and you’ll be unstoppable.", "General"),
        Shayari("Your life does not get better by chance, it gets better by change.", "General"),
        Shayari("Stay positive and happy.", "General"),
        Shayari("Life is short, make it sweet.", "General"),
        Shayari("Everything you can imagine is real.", "General"),
        Shayari("Be yourself; everyone else is already taken.", "General"),
        Shayari("The best way out is always through.", "General"),
        Shayari("Sometimes you have to create your own sunshine.", "General"),
        Shayari("Start where you are. Use what you have. Do what you can.", "General"),
        Shayari("Dream big. Work hard. Stay focused.", "General"),
        Shayari("Be the change you want to see in the world.", "General"),
        Shayari("You are stronger than you think.", "General"),
        Shayari("In the middle of difficulty lies opportunity.", "General"),
        Shayari(
            "Success doesn’t come from what you do occasionally, it comes from what you do consistently.",
            "General"
        )
    )

    private val reducingAnxietyShayariList = listOf(
        Shayari("Breathe in, breathe out. The storm will pass.", "Reducing Anxiety"),
        Shayari("Inhale calm, exhale stress.", "Reducing Anxiety"),
        Shayari("Let go of what you can't control.", "Reducing Anxiety"),
        Shayari("It’s okay to not be okay.", "Reducing Anxiety"),
        Shayari("Peace begins with a smile.", "Reducing Anxiety"),
        Shayari(
            "Do not stress the could haves. If it should have, it would have.",
            "Reducing Anxiety"
        ),
        Shayari("When you feel like giving up, remember why you started.", "Reducing Anxiety"),
        Shayari("Breathe in peace, breathe out negativity.", "Reducing Anxiety"),
        Shayari(
            "Don’t let the noise of others’ opinions drown out your own inner voice.",
            "Reducing Anxiety"
        ),
        Shayari("The only way out is through.", "Reducing Anxiety"),
        Shayari(
            "You don’t have to control your thoughts, just stop letting them control you.",
            "Reducing Anxiety"
        ),
        Shayari("Self-care is how you take your power back.", "Reducing Anxiety"),
        Shayari("Your mind is a garden. Your thoughts are the seeds.", "Reducing Anxiety"),
        Shayari("Take it one step at a time.", "Reducing Anxiety"),
        Shayari("This too shall pass.", "Reducing Anxiety"),
        Shayari(
            "Worrying does not take away tomorrow’s troubles, it takes away today’s peace.",
            "Reducing Anxiety"
        ),
        Shayari("You don’t have to face everything alone.", "Reducing Anxiety"),
        Shayari(
            "Sometimes the hardest thing is to let go of the things you can’t change.",
            "Reducing Anxiety"
        ),
        Shayari("Be kind to yourself. You’re doing your best.", "Reducing Anxiety"),
        Shayari("The present moment is all you need.", "Reducing Anxiety"),
        Shayari("Let your breath be your anchor.", "Reducing Anxiety"),
        Shayari("You are allowed to rest, but never to quit.", "Reducing Anxiety"),
        Shayari("It’s okay to take a break.", "Reducing Anxiety"),
        Shayari("Your thoughts do not control you.", "Reducing Anxiety"),
        Shayari("Focus on what you can control.", "Reducing Anxiety"),
        Shayari("Quiet the mind, and the soul will speak.", "Reducing Anxiety"),
        Shayari("Keep calm and carry on.", "Reducing Anxiety"),
        Shayari(
            "No matter how much it hurts now, someday you’ll look back and realize your struggles changed your life.",
            "Reducing Anxiety"
        ),
        Shayari("Healing takes time, but it’s worth it.", "Reducing Anxiety"),
        Shayari("Don’t be afraid to rest.", "Reducing Anxiety"),
        Shayari("Focus on the good.", "Reducing Anxiety"),
        Shayari(
            "Everything will be okay in the end. If it’s not okay, it’s not the end.",
            "Reducing Anxiety"
        )
    )

    private val personalGrowthShayariList = listOf(
        Shayari("Growth comes when you step out of your comfort zone.", "Personal Growth"),
        Shayari("Believe in yourself, your potential is limitless.", "Personal Growth"),
        Shayari("Every struggle is a step toward growth.", "Personal Growth"),
        Shayari(
            "Success is the sum of small efforts, repeated day in and day out.",
            "Personal Growth"
        ),
        Shayari(
            "Don’t wait for the perfect moment. Take the moment and make it perfect.",
            "Personal Growth"
        ),
        Shayari("The best way to predict the future is to create it.", "Personal Growth"),
        Shayari(
            "Success is not final, failure is not fatal: It is the courage to continue that counts.",
            "Personal Growth"
        ),
        Shayari("The only way to do great work is to love what you do.", "Personal Growth"),
        Shayari("Doubt kills more dreams than failure ever will.", "Personal Growth"),
        Shayari("The secret of getting ahead is getting started.", "Personal Growth"),
        Shayari("You grow through what you go through.", "Personal Growth"),
        Shayari("You are your only limit.", "Personal Growth"),
        Shayari("Don’t limit your challenges. Challenge your limits.", "Personal Growth"),
        Shayari("Take the risk or lose the chance.", "Personal Growth"),
        Shayari("Your growth will determine your path.", "Personal Growth"),
        Shayari("Don’t be afraid to fail, be afraid not to try.", "Personal Growth"),
        Shayari("Believe in yourself and all that you are.", "Personal Growth"),
        Shayari("Great things never come from comfort zones.", "Personal Growth"),
        Shayari("Push yourself because no one else is going to do it for you.", "Personal Growth"),
        Shayari("Be stronger than your strongest excuse.", "Personal Growth"),
        Shayari("Success starts with self-discipline.", "Personal Growth"),
        Shayari("If you can dream it, you can achieve it.", "Personal Growth"),
        Shayari("Believe in the power of yet.", "Personal Growth"),
        Shayari(
            "Your time is limited, don’t waste it living someone else’s life.",
            "Personal Growth"
        ),
        Shayari("Your only limit is you.", "Personal Growth"),
        Shayari("Stay focused and never give up.", "Personal Growth"),
        Shayari(
            "Success is not how high you have climbed, but how you make a positive difference to the world.",
            "Personal Growth"
        ),
        Shayari("Do something today that your future self will thank you for.", "Personal Growth"),
        Shayari(
            "If you want to fly, you have to give up the things that weigh you down.",
            "Personal Growth"
        ),
        Shayari(
            "Success is the result of preparation, hard work, and learning from failure.",
            "Personal Growth"
        )
    )

    private val believingInYourselfShayariList = listOf(
        Shayari("Your strength lies in your belief in yourself.", "Believing in Yourself"),
        Shayari(
            "Don’t doubt your power. Believe in what you can achieve.",
            "Believing in Yourself"
        ),
        Shayari("You are stronger than you think.", "Believing in Yourself"),
        Shayari(
            "The only limit to our realization of tomorrow is our doubts of today.",
            "Believing in Yourself"
        ),
        Shayari("Believe in yourself and all that you are.", "Believing in Yourself"),
        Shayari("Trust yourself. You know more than you think you do.", "Believing in Yourself"),
        Shayari("Don’t be afraid to be amazing.", "Believing in Yourself"),
        Shayari(
            "The power of believing in yourself will change your world.",
            "Believing in Yourself"
        ),
        Shayari("You were born to be real, not to be perfect.", "Believing in Yourself"),
        Shayari("Believe in your dreams and they may come true.", "Believing in Yourself"),
        Shayari("With self-belief, you can accomplish anything.", "Believing in Yourself"),
        Shayari("You are capable of amazing things.", "Believing in Yourself"),
        Shayari("Self-belief is the key to success.", "Believing in Yourself"),
        Shayari("The biggest limit to success is self-doubt.", "Believing in Yourself"),
        Shayari("Believe you can and you're halfway there.", "Believing in Yourself"),
        Shayari("Your dreams are valid.", "Believing in Yourself"),
        Shayari("Confidence comes from within.", "Believing in Yourself"),
        Shayari(
            "Never doubt yourself. Your courage will take you to places.",
            "Believing in Yourself"
        ),
        Shayari("You are worthy of success and happiness.", "Believing in Yourself"),
        Shayari("If you believe in yourself, anything is possible.", "Believing in Yourself"),
        Shayari("Believe in your ability to unlock the magic within.", "Believing in Yourself"),
        Shayari("Your self-belief is your greatest asset.", "Believing in Yourself"),
        Shayari("If you don’t believe in yourself, who will?", "Believing in Yourself"),
        Shayari("You have the power to shape your future.", "Believing in Yourself"),
        Shayari(
            "The way to get started is to quit talking and begin doing.",
            "Believing in Yourself"
        ),
        Shayari("Believe in yourself, and the world will believe in you.", "Believing in Yourself"),
        Shayari(
            "If you believe in yourself, there is nothing you can’t achieve.",
            "Believing in Yourself"
        ),
        Shayari(
            "Your belief in yourself is what will bring you the best success.",
            "Believing in Yourself"
        )
    )

    // Now add all categories into the shayariList
    val shayariList = allshayariList

    private lateinit var shayariAdapter: ShayariAdapter

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up the ViewPager2 with ShayariAdapter
        val adapter = ShayariAdapter(shayariList)
        binding.viewPager.adapter = adapter
        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        // Handle Share button
        binding.cvShare.setOnClickListener {
            shareImage()
        }
        binding.lottieAnimationView.addValueCallback(
            KeyPath("**"),
            LottieProperty.COLOR_FILTER
        ) { PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP) }

        // GestureDetector to detect swipe gestures


        var initialY = 0f
        // Attach OnTouchListener to LottieAnimationView
        binding.lottieAnimationView.setOnTouchListener { _, event ->

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialY = event.y
                }

                MotionEvent.ACTION_MOVE -> {
                    val deltaY = event.y - initialY
                    if (deltaY < -60f || deltaY > 60f) { // Swipe Up Threshold (negative delta)
                        binding.lottieAnimationView.visibility = View.GONE
                        binding.viewPager.visibility = View.VISIBLE
                    }
                }
            }
            return@setOnTouchListener true
        }

        shayariAdapter = ShayariAdapter(shayariList)
        binding.viewPager.adapter = shayariAdapter
        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        binding.cvCategories.setOnClickListener {
            val bottomSheetFragment = CategoryBottomSheetFragment { selectedCategory ->
                displayShayariByCategory(selectedCategory)
            }
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }

        // Handle Theme button
        binding.cvTheme.setOnClickListener {
            selectedBg = bgList.filter { it != selectedBg }.random()
            binding.screenshot.background = ContextCompat.getDrawable(this, selectedBg)
            binding.main.background = ContextCompat.getDrawable(this, selectedBg)
        }

        // Handle Copy button
        binding.cvCopy.setOnClickListener {
            loadInterstitial()
            AlertDialog.Builder(this, R.style.updateDialogTheme).setView(R.layout.popup_copying)
                .setCancelable(false).create().apply {
                    show()
                    findViewById<MaterialCardView>(R.id.innerCardView)!!.post {
                        animateProgress(
                            findViewById(R.id.innerCardView)!!,
                            findViewById<MaterialCardView>(R.id.outerCardView)!!.width,
                            Random.nextLong(15000, 20000),
                            this
                        )
                    }

                }
        }


        // Handle Settings button (currently empty)
        binding.cvSettings.setOnClickListener {
            val bottomSheet = SettingsBottomSheet()
            bottomSheet.show(supportFragmentManager, "SettingsBottomSheet")
        }
        binding.cvBalance.setOnClickListener {
            if (TinyDB.getString(this, "balance", "0")!!.toInt() >= TinyDB.getString(
                    this,
                    "balance_withdrawal_limit",
                    "0"
                )!!.toInt()
            ) {
                val bottomSheet = RedeemBottomSheet()
                bottomSheet.show(supportFragmentManager, "SettingsBottomSheet")
            } else {
                Toast.makeText(this, "Insufficient Diamonds", Toast.LENGTH_SHORT).show()
            }
        }


        shayariAdapter = ShayariAdapter(shayariList)
        binding.viewPager.adapter = shayariAdapter
        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
    }

    private fun animateProgress(
        cardView: MaterialCardView,
        finalWidth: Int,
        duration: Long,
        alertDialog: AlertDialog
    ) {
        val startWidth = 0
        val layoutParams = cardView.layoutParams as ViewGroup.LayoutParams

        val handler = Handler(Looper.getMainLooper())
        val startTime = System.currentTimeMillis()

        handler.post(object : Runnable {
            override fun run() {
                val elapsedTime = System.currentTimeMillis() - startTime
                val progress = elapsedTime.toFloat() / duration
                layoutParams.width = (startWidth + (finalWidth * progress)).toInt()
                cardView.layoutParams = layoutParams

                if (progress < 1.0f) {
                    handler.postDelayed(this, 16)
                } else {
                    alertDialog.dismiss()
                    addPoint()
                }
            }
        })
    }

    private fun addPoint() {
        Utils.showLoadingPopUp(this)
        if (TinyDB.getString(this, "play_limit", "0") == "0") {
            Utils.dismissLoadingPopUp()
            Toast.makeText(this, "Today's Limit End, Come Back Tomorrow !", Toast.LENGTH_SHORT)
                .show()
            finish()
        } else {
            val deviceid: String = Settings.Secure.getString(
                contentResolver, Settings.Secure.ANDROID_ID
            )
            val time = System.currentTimeMillis()

            val url3 = "${Companions.siteUrl}play_point.php"
            val email = TinyDB.getString(this, "email", "")

            val queue3: RequestQueue = Volley.newRequestQueue(this)
            val stringRequest =
                object : StringRequest(Method.POST, url3, { response ->

                    val yes = Base64.getDecoder().decode(response)
                    val res = String(yes, Charsets.UTF_8)

                    if (res.contains(",")) {
                        Utils.dismissLoadingPopUp()
                        val alldata = res.trim().split(",")
                        TinyDB.saveString(this, "play_limit", alldata[2])
                        TinyDB.saveString(this, "balance", alldata[1])
                        isApiCallable = true
                        binding.tvBalance.text = alldata[1]
                        copyCurrentShayari()
                    } else {
                        Toast.makeText(this, res, Toast.LENGTH_SHORT).show()
                        finish()
                    }

                }, { error ->
                    Utils.dismissLoadingPopUp()
                    Toast.makeText(this, "Internet Slow", Toast.LENGTH_SHORT).show()
                    // requireActivity().finish()
                }) {
                    override fun getParams(): Map<String, String> {
                        val params: MutableMap<String, String> = HashMap()

                        val dbit32 = videoplayyer.encrypt(deviceid, Hatbc()).toString()
                        val tbit32 = videoplayyer.encrypt(time.toString(), Hatbc()).toString()
                        val email = videoplayyer.encrypt(email.toString(), Hatbc()).toString()

                        val den64 = Base64.getEncoder().encodeToString(dbit32.toByteArray())
                        val ten64 = Base64.getEncoder().encodeToString(tbit32.toByteArray())
                        val email64 = Base64.getEncoder().encodeToString(email.toByteArray())

                        val encodemap: MutableMap<String, String> = HashMap()
                        encodemap["deijvfijvmfhvfvhfbhbchbfybebd"] = den64
                        encodemap["waofhfuisgdtdrefssfgsgsgdhddgd"] = ten64
                        encodemap["fdvbdfbhbrthyjsafewwt5yt5"] = email64

                        val jason = Json.encodeToString(encodemap)

                        val den264 = Base64.getEncoder().encodeToString(jason.toByteArray())

                        val final = URLEncoder.encode(den264, StandardCharsets.UTF_8.toString())

                        params["dase"] = final

                        val encodedAppID = Base64.getEncoder()
                            .encodeToString(
                                Companions.APP_ID.toString().toByteArray()
                            )
                        params["app_id"] = encodedAppID

                        return params
                    }
                }

            queue3.add(stringRequest)
        }


    }

    private fun copyCurrentShayari() {
        // Get the current Shayari text displayed in the ViewPager
        val currentItem = binding.viewPager.currentItem
        val currentShayari = shayariList[currentItem].text

        // Copy text to clipboard
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Shayari", currentShayari)
        clipboard.setPrimaryClip(clip)

        // Notify the user
        Toast.makeText(this, "Shayari copied to clipboard!", Toast.LENGTH_SHORT).show()
    }

    // Inside HomeActivity:

    // This method is called when a category is selected from the bottom sheet
    private fun displayShayariByCategory(category: String) {
        val filteredShayari = when (category) {

            "General" -> generalShayariList
            "Reducing Anxiety" -> reducingAnxietyShayariList
            "Personal Growth" -> personalGrowthShayariList
            "Believing in Yourself" -> believingInYourselfShayariList
            else -> listOf()  // Default empty list if no category selected
        }

        // Update the RecyclerView adapter with the filtered list
        shayariAdapter.updateShayariList(filteredShayari)
    }


    private fun shareImage() {
        val screenshotLayout = binding.screenshot
        val bitmap = Bitmap.createBitmap(
            screenshotLayout.width,
            screenshotLayout.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        screenshotLayout.draw(canvas)

        try {
            val file = File(applicationContext.cacheDir, "shared_image.jpg")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            val uri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", file)
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "image/jpeg"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivity(Intent.createChooser(shareIntent, "Share Image"))
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to share image", Toast.LENGTH_SHORT).show()
        }
    }

    fun getUserValue() {
        Utils.showLoadingPopUp(this)
        val deviceid: String = Settings.Secure.getString(
            this.contentResolver, Settings.Secure.ANDROID_ID
        )
        val time = System.currentTimeMillis()

        val url2 = "${Companions.siteUrl}getuservalue.php"
        val email = TinyDB.getString(this, "email", "")

        val queue1: RequestQueue = Volley.newRequestQueue(this)
        val stringRequest =
            object : StringRequest(Method.POST, url2, { response ->
                val ytes = Base64.getDecoder().decode(response)
                val res = String(ytes, Charsets.UTF_8)

                if (res.contains(",")) {
                    val alldata = res.trim().split(",")
                    TinyDB.saveString(this, "phone", alldata[0])
                    TinyDB.saveString(this, "maintenance", alldata[1])
                    TinyDB.saveString(this, "version", alldata[2])
                    TinyDB.saveString(this, "balance", alldata[3])
                    TinyDB.saveString(this, "earning_ad_limit", alldata[4])
                    TinyDB.saveString(this, "telegram_link", alldata[5])
                    TinyDB.saveString(this, "refer_code", alldata[6])
                    TinyDB.saveString(this, "sponsor_link", alldata[7])
                    TinyDB.saveString(this, "app_link", alldata[8])
                    TinyDB.saveString(this, "play_limit", alldata[9])
                    TinyDB.saveString(this, "show_ads_after_each", alldata[10])
                    TinyDB.saveString(this, "adx_app_id", alldata[11])
                    TinyDB.saveString(this, "adx_inter", alldata[12])
                    TinyDB.saveString(this, "adx_native", alldata[13])
                    TinyDB.saveString(this, "balance_show_limit", alldata[14])
                    TinyDB.saveString(this, "balance_withdrawal_limit", alldata[15])
                    TinyDB.saveString(this, "adx_banner", alldata[16])

                    if (alldata[3].toString().toInt() >= alldata[14].toInt()) {
                        binding.cvBalance.visibility = View.VISIBLE
                    } else {
                        binding.cvBalance.visibility = View.GONE
                    }
                    updateAdMobAppId(alldata[11])
                    //setBalanceText()
                    binding.tvBalance.text = TinyDB.getString(this, "balance", "")
                    if (alldata[2].toInt() > Companions.APP_VERSION) {
                        showUpdatePopup()
                    } else if (alldata[1] == "1") {
                        showMaintaincePopup()
                    }

                    Handler(Looper.getMainLooper()).postDelayed({
                        if (binding.lottieAnimationView.isVisible) {
                            binding.lottieAnimationView.playAnimation()
                        }
                        Utils.dismissLoadingPopUp()
                    }, 1000)

                } else {
                    Toast.makeText(this, res, Toast.LENGTH_LONG).show()
                    finish()
                }


            }, { _ ->
                Utils.dismissLoadingPopUp()
                Toast.makeText(this, "Internet Slow", Toast.LENGTH_SHORT).show()
                finish()
            }) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()

                    val dbit32 = videoplayyer.encrypt(deviceid, Hatbc()).toString()
                    val tbit32 = videoplayyer.encrypt(time.toString(), Hatbc()).toString()
                    val email = videoplayyer.encrypt(email.toString(), Hatbc()).toString()

                    val den64 = Base64.getEncoder().encodeToString(dbit32.toByteArray())
                    val ten64 = Base64.getEncoder().encodeToString(tbit32.toByteArray())
                    val email64 = Base64.getEncoder().encodeToString(email.toByteArray())

                    val encodemap: MutableMap<String, String> = HashMap()
                    encodemap["deijvfijvmfhvfvhfbhbchbfybebd"] = den64
                    encodemap["waofhfuisgdtdrefssfgsgsgdhddgd"] = ten64

                    val jason = Json.encodeToString(encodemap)

                    val den264 = Base64.getEncoder().encodeToString(jason.toByteArray())

                    val final = URLEncoder.encode(den264, StandardCharsets.UTF_8.toString())

                    params["dase"] = final

                    val encodedAppID = Base64.getEncoder()
                        .encodeToString(Companions.APP_ID.toString().toByteArray())
                    params["app_id"] = encodedAppID

                    return params
                }
            }

        queue1.add(stringRequest)
    }

    private fun showMaintaincePopup() {
        AlertDialog.Builder(this, R.style.updateDialogTheme).setView(R.layout.popup_maintaince)
            .setCancelable(false).create().apply {
                show()
                findViewById<MaterialCardView>(R.id.cv_okay)?.setOnClickListener {
                    Utils.openUrl(
                        this@HomeActivity,
                        TinyDB.getString(this@HomeActivity, "telegram_link", "0")!!
                    )
                }
            }
    }

    private fun showUpdatePopup() {
        AlertDialog.Builder(this, R.style.updateDialogTheme).setView(R.layout.popup_newupdate)
            .setCancelable(false).create().apply {
                show()
                findViewById<MaterialCardView>(R.id.cv_okay)?.setOnClickListener {
                    Utils.openUrl(
                        this@HomeActivity,
                        TinyDB.getString(this@HomeActivity, "app_link", "")!!
                    )
                }
            }

    }

    override fun onResume() {
        super.onResume()
        getUserValue()
    }

    private fun updateAdMobAppId(adMobAppId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val applicationInfo = packageManager.getApplicationInfo(
                    packageName,
                    PackageManager.GET_META_DATA
                )
                val metaData = applicationInfo.metaData

                if (metaData != null) {
                    metaData.putString("com.google.android.gms.ads.APPLICATION_ID", adMobAppId)
                    println("AdMob App ID updated: $adMobAppId")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            MobileAds.initialize(this@HomeActivity) {

            }
            loadInterstitial()
            loadNativeAd()
        }
    }

    private fun loadInterstitial() {
        val adRequest = AdManagerAdRequest.Builder().build()
        AdManagerInterstitialAd.load(
            this,
            TinyDB.getString(this@HomeActivity, "adx_inter", "")!!.replace("-", ","),
            adRequest,
            object : AdManagerInterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: com.google.android.gms.ads.LoadAdError) {
                    adError.toString().let { Log.d("AdMob", it) }
                    mAdManagerInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: AdManagerInterstitialAd) {
                    Log.d("AdMob", "Ad was loaded.")
                    mAdManagerInterstitialAd = interstitialAd
                }
            }
        )
    }

    private fun loadNativeAd() {
        val adLoader = com.google.android.gms.ads.AdLoader.Builder(
            this,
            TinyDB.getString(this@HomeActivity, "adx_native", "")!!.replace("-", ",")
        ).forNativeAd { nativeAd ->
            Log.d("AdMob", "Native Ad Loaded.")
            AdmobX.loadNativeMediaumX(
                this,
                binding.myTemplate,
                TinyDB.getString(this@HomeActivity, "adx_native", "")!!.replace("-", ",")
            )
        }.build()

        adLoader.loadAd(AdManagerAdRequest.Builder().build())
    }
}
