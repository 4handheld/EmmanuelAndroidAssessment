package com.example.inventoryappflutterwaveassessment.ui.fragments.ui.login.mod

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.inventoryappflutterwaveassessment.databinding.FragmentLoginBinding
import com.example.inventoryappflutterwaveassessment.extensions.onToast
import com.example.inventoryappflutterwaveassessment.extensions.saveSessionAuth
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
        loginViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                SimpleLoginViewModel.LoginUIState.Error -> TODO()
                SimpleLoginViewModel.LoginUIState.Loading -> TODO()
                is SimpleLoginViewModel.LoginUIState.Success -> {
                    pref.saveSessionAuth(it.user)
                    findNavController().popBackStack()
                }
            }
        }

    }

    private fun setupClickListeners() {
        binding.login.setOnClickListener {
            val email = binding.username.text.toString()
            val pass = binding.password.text.toString()

            val isValidEmail = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
            val isValidPassword = pass.isNotEmpty()

            if (!isValidEmail) {
                onToast("Invalid Email. Can't log in")
                return@setOnClickListener
            }

            if (!isValidPassword) {
                onToast("Password must not be empty")
                return@setOnClickListener
            }

            loginViewModel.loginUser(email, pass)
        }
    }

}