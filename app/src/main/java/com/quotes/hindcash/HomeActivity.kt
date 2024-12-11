package com.quotes.hindcash

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.quotes.hindcash.databinding.ActivityHomeBinding
import java.io.File
import java.io.FileOutputStream

class HomeActivity : AppCompatActivity() {

    private val bgList = listOf(R.drawable.bg, R.drawable.theme_bg1, R.drawable.theme_bg2,R.drawable.theme_bg3, R.drawable.theme_bg4, R.drawable.theme_bg5,R.drawable.theme_bg6, R.drawable.theme_bg7, R.drawable.theme_bg8 ,R.drawable.theme_bg9, R.drawable.theme_bg10)
    private lateinit var binding: ActivityHomeBinding
    private val shayariList = listOf(
        Shayari("Roses are red, violets are blue, Shayari is here, just for you."),
        Shayari("Life is a journey, enjoy every step with a Shayari."),
        Shayari("The stars shine bright, as does your soul in the night."),
        Shayari("Shayari brings meaning to every emotion."),
        Shayari("In every heartbeat, there's a Shayari waiting to be heard."),
        Shayari("The moon whispers secrets only Shayari can reveal."),
        Shayari("Every drop of rain carries a Shayari's refrain."),
        Shayari("Love is eternal, as is the beauty of Shayari."),
        Shayari("The sun rises to paint the world with Shayari."),
        Shayari("Through the valleys and hills, Shayari echoes still."),
        Shayari("Words crafted with care, that’s what Shayari declares."),
        Shayari("Every smile hides a Shayari untold."),
        Shayari("In the silence of the night, Shayari takes flight."),
        Shayari("The wind carries whispers of Shayari's charm."),
        Shayari("Shayari dances in the rhythm of your heartbeat."),
        Shayari("Every tear that falls is a Shayari untapped."),
        Shayari("Life’s poetry comes alive with Shayari's light."),
        Shayari("Through every trial, Shayari finds a way to smile."),
        Shayari("The colors of the rainbow sing Shayari's melody."),
        Shayari("Each grain of sand holds a Shayari so grand."),
        Shayari("In the stillness of the forest, Shayari whispers peace."),
        Shayari("The melody of the river sings a Shayari untamed."),
        Shayari("With every goodbye, Shayari bids farewell softly."),
        Shayari("Under the vast sky, Shayari connects every soul."),
        Shayari("In the realm of dreams, Shayari rules supreme."),
        Shayari("The heart’s every beat chants a Shayari sweet."),
        Shayari("Through the lens of love, Shayari sees the world anew."),
        Shayari("In the cradle of hope, Shayari finds its birth."),
        Shayari("The whisper of leaves tells a Shayari serene."),
        Shayari("Through laughter and tears, Shayari perseveres."),
        Shayari("The touch of a loved one is Shayari's greatest muse."),
        Shayari("Every dawn brings a Shayari of fresh beginnings."),
        Shayari("Beneath the starlit sky, Shayari's secrets lie."),
        Shayari("The essence of life is captured in Shayari's rhyme."),
        Shayari("Through joy and sorrow, Shayari builds tomorrow."),
        Shayari("In the tapestry of life, Shayari weaves its magic."),
        Shayari("Each step forward is a Shayari of courage."),
        Shayari("The aroma of chai brings a Shayari to life."),
        Shayari("Through the storms, Shayari finds its calm."),
        Shayari("Every fleeting moment carries a Shayari's soul."),
        Shayari("The beauty of a smile is Shayari's quiet triumph."),
        Shayari("In every corner of the world, Shayari whispers unity."),
        Shayari("The rhythm of the heart beats to Shayari's tune."),
        Shayari("Through the lens of longing, Shayari finds its path."),
        Shayari("With every breath, Shayari fills the soul."),
        Shayari("In the simplicity of life, Shayari finds its muse."),
        Shayari("A flickering candlelight holds a Shayari in its glow."),
        Shayari("The scent of flowers blooms into Shayari's verse."),
        Shayari("In the warmth of friendship, Shayari feels complete."),
        Shayari("Each star in the sky holds a Shayari's wish."),
        Shayari("The silence of the mountains echoes Shayari's depth."),
        Shayari("Through the eyes of a child, Shayari rediscovers wonder."),
        Shayari("In the melody of rain, Shayari dances in delight."),
        Shayari("The touch of morning dew whispers Shayari anew."),
        Shayari("Each smile shared crafts a Shayari cherished."),
        Shayari("Through every farewell, Shayari leaves hope behind."),
        Shayari("In the depths of despair, Shayari offers light."),
        Shayari("Every hug holds a Shayari untold."),
        Shayari("The colors of sunset sing a Shayari profound."),
        Shayari("With every prayer, Shayari touches the heavens."),
        Shayari("The bond of love binds Shayari's essence together."),
        Shayari("Through the darkest night, Shayari finds its voice."),
        Shayari("In every journey, Shayari paves the way."),
        Shayari("The laughter of children is Shayari's sweetest sound."),
        Shayari("The tides of the ocean echo Shayari's timeless truth."),
        Shayari("Through the fire of passion, Shayari is forged."),
        Shayari("The pages of a book carry Shayari's eternal story."),
        Shayari("The smile of a stranger whispers a Shayari shared."),
        Shayari("In the chaos of the world, Shayari finds its order."),
        Shayari("The warmth of the sun brings a Shayari alive."),
        Shayari("Through the cracks of pain, Shayari grows strong."),
        Shayari("Every sunrise paints a Shayari of hope."),
        Shayari("In the shadow of the moon, Shayari finds its glow."),
        Shayari("Every heartbeat sings a Shayari unique."),
        Shayari("The spirit of kindness breathes life into Shayari."),
        Shayari("Through every goodbye, Shayari finds its reason to stay."),
        Shayari("The rhythm of the waves carries Shayari's wisdom."),
        Shayari("In the solitude of the night, Shayari whispers comfort."),
        Shayari("The colors of Holi splash Shayari's joy around."),
        Shayari("Through the eyes of love, Shayari finds its clarity."),
        Shayari("The silence of the soul speaks Shayari profound."),
        Shayari("In the laughter of friends, Shayari celebrates life."),
        Shayari("Every victory holds a Shayari of hard work."),
        Shayari("The melody of life resonates with Shayari's charm."),
        Shayari("Through the tapestry of time, Shayari weaves eternity."),
        Shayari("The fragrance of memories breathes Shayari's essence."),
        Shayari("Every breath of life is Shayari's silent gift."),
        Shayari("The glow of a diya carries Shayari's warmth."),
        Shayari("In every moment of doubt, Shayari finds faith."),
        Shayari("Through the cracks of life, Shayari brings light."),
        Shayari("Every tear holds a Shayari waiting to be penned.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Set up the ViewPager2 with ShayariAdapter
        val adapter = ShayariAdapter(shayariList)
        binding.viewPager.adapter = adapter
        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        // Handle Share button click in HomeActivity
        binding.cvShare.setOnClickListener {
            shareImage()
        }

        binding.cvTheme.setOnClickListener {

            binding.screenshot.background = ContextCompat.getDrawable(this, bgList.random())
        }
        binding.cvCategories.setOnClickListener {

        }
        binding.cvSettings.setOnClickListener {

        }
    }

    private fun shareImage() {
        // Find the RelativeLayout with all overlays
        val screenshotLayout = binding.screenshot

        // Create a Bitmap from the view
        val bitmap = Bitmap.createBitmap(
            screenshotLayout.width,
            screenshotLayout.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        screenshotLayout.draw(canvas)

        // Save the bitmap to a temporary file
        try {
            val file = File(applicationContext.cacheDir, "shared_image.jpg")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            // Share the image using an Intent
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


}
