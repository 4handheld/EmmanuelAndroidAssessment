package com.example.inventoryappflutterwaveassessment.ui.fragments.ui.inventory.add

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
import com.example.inventoryappflutterwaveassessment.data.storage.entities.Items
import com.example.inventoryappflutterwaveassessment.databinding.FragmentAddInventoryBinding
import com.example.inventoryappflutterwaveassessment.databinding.FragmentLoginBinding
import com.example.inventoryappflutterwaveassessment.extensions.onToast
import com.example.inventoryappflutterwaveassessment.extensions.verifyLogin
import com.example.inventoryappflutterwaveassessment.ui.fragments.ui.inventory.view.MyItemRecyclerViewAdapter
import com.example.inventoryappflutterwaveassessment.ui.fragments.ui.login.mod.SimpleLoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddInventoryFragment : Fragment() {

    val viewModel by viewModels<AddInventoryViewModel>()

    private var _binding: FragmentAddInventoryBinding? = null

    @Inject
    lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddInventoryBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        verifyLogin(pref)
        setupClickListeners()
        setupEventListeners()
    }

    private fun setupEventListeners() {
        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                is AddInventoryViewModel.AddInventoryState.Error -> onError(it.error)
                AddInventoryViewModel.AddInventoryState.Loading -> {}
                is AddInventoryViewModel.AddInventoryState.Success -> onSuccess()
            }
        }
    }

    private fun onError(msg: String){
        onToast(msg)
    }
    private fun onSuccess(){
        onToast("Add done") //Localize
        findNavController().navigateUp()
    }

    private fun setupClickListeners() {
       _binding?.addButton?.setOnClickListener{
           val name = _binding?.includedInputLayout?.itemNameInput.toString()
           val desc = _binding?.includedInputLayout?.itemDescInput.toString()
           val price = _binding?.includedInputLayout?.itemPriceInput.toString().toDouble()
           val qty = _binding?.includedInputLayout?.itemQtyInput.toString().toInt()
           viewModel.addItem(name, desc, price, qty, 1)
       }
    }



}