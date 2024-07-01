package com.example.inventoryappflutterwaveassessment.ui.fragments.ui.edit

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventoryappflutterwaveassessment.R
import com.example.inventoryappflutterwaveassessment.data.storage.entities.Items
import com.example.inventoryappflutterwaveassessment.databinding.FragmentAddInventoryBinding
import com.example.inventoryappflutterwaveassessment.databinding.FragmentEditInventoryBinding
import com.example.inventoryappflutterwaveassessment.extensions.onToast
import com.example.inventoryappflutterwaveassessment.extensions.verifyLogin
import com.example.inventoryappflutterwaveassessment.ui.fragments.ui.inventory.add.AddInventoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditInventoryFragment : Fragment() {

    val viewModel by viewModels<EditInventoryViewModel>()

    private var _binding: FragmentEditInventoryBinding? = null

    private val args: EditInventoryFragmentArgs by navArgs()

    @Inject
    lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditInventoryBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        verifyLogin(pref)
        setupEditors()
        setupClickListeners()
        setupEventListeners()

    }

    private fun setupEditors() {
        _binding?.includedInputLayout?.apply {
            itemNameInput.editText?.setText(args.item.name)
            itemDescInput.editText?.setText(args.item.description)
            itemPriceInput.editText?.setText(args.item.price.toString())
            itemQtyInput.editText?.setText(args.item.qty.toString())
        }
    }

    private fun setupEventListeners() {
        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                EditInventoryViewModel.EditInventoryState.Loading -> {}
                is EditInventoryViewModel.EditInventoryState.Error -> onError(it.error)
                is EditInventoryViewModel.EditInventoryState.SuccessDelete -> onSuccess(true)
                is EditInventoryViewModel.EditInventoryState.SuccessUpdate -> onSuccess(false)
                else -> {}
            }
        }
    }

    private fun onError(text: String){
        onToast(text)
    }

    private fun onSuccess(isdel: Boolean){
        val text = if(isdel){
            "Deleted done"
        }else{
            "Update done"
        }
        onToast(text)
        findNavController().navigateUp()
    }

    private fun setupClickListeners() {
      _binding?.delBtn?.setOnClickListener{
          val item = args.item.run {
              Items(uid, ownerId, name, description, price, qty)
          }
          viewModel.deleteItem(item)
      }
      _binding?.saveBtn?.setOnClickListener{
          val item = args.item.run {
              val name = _binding?.includedInputLayout?.itemNameInput.toString()
              val desc = _binding?.includedInputLayout?.itemDescInput.toString()
              val price = _binding?.includedInputLayout?.itemPriceInput.toString().toDouble()
              val qty = _binding?.includedInputLayout?.itemQtyInput.toString().toInt()
              Items(uid, ownerId, name, desc, price, qty)
          }
          viewModel.updateItem(item)
      }
    }

}