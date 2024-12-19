package com.quotes.hindcash

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.appopen.AppOpenAd
import com.quotes.hindcash.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appOpenAdManager: AppOpenAdManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        MobileAds.initialize(this@MainActivity) {}
        appOpenAdManager = AppOpenAdManager()
        Handler(Looper.getMainLooper()).postDelayed({
            appOpenAdManager.loadAd(this)
        }, 1500)
    }

    private inner class AppOpenAdManager {
        private var appOpenAd: AppOpenAd? = null
        private var isLoadingAd = false
        var isShowingAd = false
        private fun isAdAvailable(): Boolean {
            return appOpenAd != null
        }

        fun loadAd(context: Context) {
            if (isLoadingAd || isAdAvailable()) {
                return
            }

            isLoadingAd = true
            val request = AdRequest.Builder().build()
            AppOpenAd.load(
                context,
                TinyDB.getString(
                    context,
                    "adx_app_open",
                    "/22475853447-23234804262/Hindcash_com.quotes.hindcash_appopen"
                )!!.replace("-", ","),
                request,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                object : AppOpenAd.AppOpenAdLoadCallback() {
                    override fun onAdLoaded(ad: AppOpenAd) {
                        appOpenAd = ad
                        isLoadingAd = false
                        appOpenAdManager.showAdIfAvailable(
                            activity = this@MainActivity,
                            object : OnShowAdCompleteListener {
                                override fun onShowAdComplete() {
                                    val intent =
                                        Intent(this@MainActivity, startActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            })
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        // Called when an app open ad has failed to load.
                        val intent =
                            Intent(this@MainActivity, startActivity::class.java)
                        startActivity(intent)
                        finish()
                        isLoadingAd = false;
                    }
                })
        }

        fun showAdIfAvailable(
            activity: Activity,
            onShowAdCompleteListener: OnShowAdCompleteListener
        ) {
            if (isShowingAd) {
                return
            }

            if (!isAdAvailable()) {
                onShowAdCompleteListener.onShowAdComplete()

                return
            }

            appOpenAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    appOpenAd = null
                    isShowingAd = false
                    onShowAdCompleteListener.onShowAdComplete()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {

                    appOpenAd = null
                    isShowingAd = false
                    onShowAdCompleteListener.onShowAdComplete()

                }

                override fun onAdShowedFullScreenContent() {

                }
            }
            isShowingAd = true
            appOpenAd?.show(activity)
        }
    }

    interface OnShowAdCompleteListener {
        fun onShowAdComplete()
    }

}