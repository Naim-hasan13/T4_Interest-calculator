package com.quotes.hindcash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kabir.moneytree.extrazz.videoplayyer
import com.quotes.hindcash.databinding.BottomSheetRedeemBinding
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Base64

class RedeemBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetRedeemBinding? = null
    private val binding get() = _binding!!

    init {
        System.loadLibrary("keys")
    }

    external fun Hatbc(): String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetRedeemBinding.inflate(inflater, container, false)

        // Handle "How to install widgets" click
//        binding.tvInstallWidgets.setOnClickListener {
//            val intent =
//                Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com/install-widgets"))
//            startActivity(intent)
//        }
        val copyLimit = TinyDB.getString(requireContext(), "play_limit", "0")!!.toInt()

        // Handle "Close" button click
        binding.ivClose.setOnClickListener {
            dismiss()
        }
        binding.cvStart.setOnClickListener {
            val upi = binding.etUpi.text.toString()
            if (upi.isNotEmpty()) {
                redeemPoint(upi)
            } else {
                binding.etUpi.error = "Enter UPI"
            }
        }
        return binding.root
    }

    private fun redeemPoint(upi: String) {
        Utils.showLoadingPopUp(requireContext())
        if (upi.isEmpty()) {
            Toast.makeText(requireContext(), "Enter UPI", Toast.LENGTH_SHORT).show()
            return
        }

        val deviceid: String = Settings.Secure.getString(
            requireContext().contentResolver, Settings.Secure.ANDROID_ID
        )
        val time = System.currentTimeMillis()

        val url3 = "${Companions.siteUrl}redeem_point.php"
        val email = TinyDB.getString(requireContext(), "email", "")

        val queue3: RequestQueue = Volley.newRequestQueue(requireContext())
        val stringRequest = object : StringRequest(Method.POST, url3, { response ->

            val yes = Base64.getDecoder().decode(response)
            val res = String(yes, Charsets.UTF_8)

            if (res.contains(",")) {
                Utils.dismissLoadingPopUp()
                val alldata = res.trim().split(",")

                (requireActivity() as HomeActivity).binding.tvBalance.text = alldata[1]
                TinyDB.saveString(requireContext(), "balance", alldata[1])
                Toast.makeText(requireContext(), alldata[0], Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    Utils.dismissLoadingPopUp()

                    dismiss()

                }, 1000)

            } else {
                Toast.makeText(requireContext(), res, Toast.LENGTH_LONG).show()
                dismiss()
            }

        }, { error ->
            Utils.dismissLoadingPopUp()
            Toast.makeText(requireContext(), "Internet Slow", Toast.LENGTH_SHORT).show()
            // requireActivity().finish()
        }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()

                val dbit32 = videoplayyer.encrypt(deviceid, Hatbc()).toString()
                val tbit32 = videoplayyer.encrypt(time.toString(), Hatbc()).toString()
                val email = videoplayyer.encrypt(email.toString(), Hatbc()).toString()
                val upi32 = videoplayyer.encrypt(upi.toString(), Hatbc()).toString()

                val den64 = Base64.getEncoder().encodeToString(dbit32.toByteArray())
                val ten64 = Base64.getEncoder().encodeToString(tbit32.toByteArray())
                val email64 = Base64.getEncoder().encodeToString(email.toByteArray())
                val upi64 = Base64.getEncoder().encodeToString(upi32.toByteArray())

                val encodemap: MutableMap<String, String> = HashMap()
                encodemap["deijvfijvmfhvfvhfbhbchbfybebd"] = den64
                encodemap["waofhfuisgdtdrefssfgsgsgdhddgd"] = ten64
                encodemap["fdvbdfbhbrthyjsafewwt5yt5"] = email64
                encodemap["defsdfefsefwefwefewfwefvfvdfbdbd"] = upi64

                val jason = Json.encodeToString(encodemap)

                val den264 = Base64.getEncoder().encodeToString(jason.toByteArray())

                val final = URLEncoder.encode(den264, StandardCharsets.UTF_8.toString())

                params["dase"] = final

                val encodedAppID = Base64.getEncoder().encodeToString(
                    Companions.APP_ID.toString().toByteArray()
                )
                params["app_id"] = encodedAppID

                return params
            }
        }

        queue3.add(stringRequest)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
