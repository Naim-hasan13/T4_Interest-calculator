package com.quotes.hindcash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.quotes.hindcash.databinding.FragmentCategoryBottomSheetBinding

class CategoryBottomSheetFragment(private val onCategorySelected: (String) -> Unit) : BottomSheetDialogFragment() {

    private var _binding: FragmentCategoryBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using View Binding
        _binding = FragmentCategoryBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set onClick listeners for category selection
        binding.cvGeneral.setOnClickListener {
            onCategorySelected("General")
            dismiss()
        }

        binding.cvReducingAnxiety.setOnClickListener {
            onCategorySelected("Reducing Anxiety")
            dismiss()
        }
        binding.ivClose.setOnClickListener {
            dismiss()

        }

        binding.cvPersonalGrowth.setOnClickListener {
            onCategorySelected("Personal Growth")
            dismiss()
        }

        binding.cvBelievingInYourself.setOnClickListener {
            onCategorySelected("Believing in Yourself")
            dismiss()
        }

        // Handle closing the bottom sheet
        binding.ivClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prevent memory leaks
    }
}
