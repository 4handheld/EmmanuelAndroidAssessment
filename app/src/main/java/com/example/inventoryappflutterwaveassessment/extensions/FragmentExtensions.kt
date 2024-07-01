package com.example.inventoryappflutterwaveassessment.extensions

import android.content.SharedPreferences
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.inventoryappflutterwaveassessment.R
import com.example.inventoryappflutterwaveassessment.data.storage.entities.User

object KEYS{
    val KEY_USER_ID = "LOGGED_IN_USER_ID"
}

fun Fragment.onToast(s: String){
    Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
}

fun Fragment.verifyLogin(pref: SharedPreferences){
    val loggedInId = getLoggedInUserId(pref)

    if (loggedInId == -1) {
        findNavController().navigate(R.id.action_itemFragment_to_simpleLoginFragment)
    }
}

fun Fragment.getLoggedInUserId(pref: SharedPreferences) = pref.getInt(KEYS.KEY_USER_ID, -1)

fun SharedPreferences.saveSessionAuth(user: User){
    this.edit().putInt(KEYS.KEY_USER_ID, user.uid!!).apply()
}

fun SharedPreferences.clearSessionAuth(){
    this.edit().clear().apply()
}
