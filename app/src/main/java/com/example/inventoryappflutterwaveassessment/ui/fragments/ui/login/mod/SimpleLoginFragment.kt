package com.example.inventoryappflutterwaveassessment.ui.fragments.ui.login.mod

import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.inventoryappflutterwaveassessment.R
import com.example.inventoryappflutterwaveassessment.databinding.FragmentLoginBinding
import com.example.inventoryappflutterwaveassessment.ui.activity.MainActivity.Companion.KEY_USER_ID
import com.example.inventoryappflutterwaveassessment.ui.fragments.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SimpleLoginFragment : Fragment() {

    val loginViewModel by viewModels<SimpleLoginViewModel>()

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupEventListeners()
    }

    private fun setupEventListeners() {
        loginViewModel.state.observe(viewLifecycleOwner){
            when(it){
                SimpleLoginViewModel.LoginUIState.Error -> TODO()
                SimpleLoginViewModel.LoginUIState.Loading -> TODO()
                is SimpleLoginViewModel.LoginUIState.Success -> {
                    pref.edit().putInt(KEY_USER_ID, it.user.uid).apply()
                    findNavController().popBackStack()
                }
            }
//            loginViewModel.clearState()
        }

    }

    private fun setupClickListeners() {
        binding.login.setOnClickListener {
            val email = binding.username.text.toString()
            val pass = binding.password.text.toString()
            loginViewModel.loginUser(email,pass)
        }
    }

}