package com.quotes.hindcash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategoryBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_bottom_sheet_, container, false)
    }
}

   /* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dialog = dialog
        if (dialog != null) {
            val window = dialog.window
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, // Width is match_parent
                WindowManager.LayoutParams.MATCH_PARENT  // Height is match_parent (full screen)
            )
            window?.setWindowAnimations(android.R.style.Animation_Dialog)
        }
        // Set up your view interactions here, for example:
      //  val categoryItem1 = view.findViewById<View>(R.id.categoryItem1)
        val categoryItem2 = view.findViewById<View>(R.id.categoryItem2)
        val categoryItem3 = view.findViewById<View>(R.id.categoryItem3)

        categoryItem1.setOnClickListener {
            // Handle category 1 click
        }

        categoryItem2.setOnClickListener {
            // Handle category 2 click
        }

        categoryItem3.setOnClickListener {
            // Handle category 3 click
        }
    }
}

    */