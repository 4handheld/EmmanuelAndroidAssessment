package com.example.inventoryappflutterwaveassessment.extensions

import android.content.SharedPreferences
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.inventoryappflutterwaveassessment.R
import com.example.inventoryappflutterwaveassessment.ui.activity.MainActivity

fun Fragment.onToast(s: String){
    Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
}

fun Fragment.verifyLogin(pref: SharedPreferences){
    val loggedInId = pref.getInt(MainActivity.KEY_USER_ID, -1)

    if (loggedInId == -1) {
        findNavController().navigate(R.id.action_itemFragment_to_simpleLoginFragment)
    }
}