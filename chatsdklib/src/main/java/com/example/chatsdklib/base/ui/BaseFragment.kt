package com.example.chatsdklib.base.ui

import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    fun showToast(msg: String) {
        if (msg.isNotEmpty()) {
            try {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
            }
        }
    }
}