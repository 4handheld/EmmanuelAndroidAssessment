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
import com.example.inventoryappflutterwaveassessment.extensions.getLoggedInUserId
import com.example.inventoryappflutterwaveassessment.extensions.onToast
import com.example.inventoryappflutterwaveassessment.extensions.verifyLogin
import com.example.inventoryappflutterwaveassessment.ui.activity.MainActivity
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
           val name = _binding?.includedInputLayout?.itemNameInput?.text.toString().trim()
           val desc = _binding?.includedInputLayout?.itemDescInput?.text.toString().trim()
           val price = _binding?.includedInputLayout?.itemPriceInput?.text.toString().trim()
           val qty = _binding?.includedInputLayout?.itemQtyInput?.text.toString().trim()

           val isNameValid = name.isNotEmpty()
           val isValidPrice = price.isNotEmpty()
           val isValidQty = qty.isNotEmpty()
           val isValidDesc = desc.split(" ").size >= 3

           if(!isNameValid){
               onToast("Name is required and must be unique")
               return@setOnClickListener
           }

           if(!isValidDesc){
               onToast("Description must be at least three words")
               return@setOnClickListener
           }

           if(!isValidPrice){
               onToast("Price is required and must be a number")
               return@setOnClickListener
           }

           if(!isValidQty){
               onToast("Qty is required and must be a number")
               return@setOnClickListener
           }

           val loggedInId = getLoggedInUserId(pref)
           viewModel.addItem(name, desc, price.toDouble(), qty.toInt(), loggedInId)
       }
    }



}