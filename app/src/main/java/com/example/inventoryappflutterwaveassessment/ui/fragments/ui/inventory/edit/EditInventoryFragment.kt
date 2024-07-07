package com.example.inventoryappflutterwaveassessment.ui.fragments.ui.edit

import android.app.AlertDialog
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
            itemNameInput.setText(args.item.name)
            itemDescInput.setText(args.item.description)
            itemPriceInput.setText(args.item.price.toString())
            itemQtyInput.setText(args.item.qty.toString())
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
            getString(R.string.deleted_done)
        }else{
            getString(R.string.update_done)
        }
        onToast(text)
        findNavController().navigateUp()
    }

    private fun setupClickListeners() {
      _binding?.delBtn?.setOnClickListener{
          val item = args.item.run {
              Items(ownerId, name, description, price, qty, uid)
          }
          confirmDeleteDialog(item)
      }
      _binding?.saveBtn?.setOnClickListener{
          val item = args.item.run {
              val name = _binding?.includedInputLayout?.itemNameInput?.text.toString().trim()
              val desc = _binding?.includedInputLayout?.itemDescInput?.text.toString().trim()
              val price = _binding?.includedInputLayout?.itemPriceInput?.text.toString().trim()
              val qty = _binding?.includedInputLayout?.itemQtyInput?.text.toString().trim()

              val isNameValid = name.isNotEmpty()
              val isValidPrice = price.isNotEmpty()
              val isValidQty = qty.isNotEmpty()
              val isValidDesc = desc.split(" ").size >= 3

              if(!isNameValid){
                  onToast(getString(R.string.name_is_required_and_must_be_unique))
                  return@setOnClickListener
              }

              if(!isValidDesc){
                  onToast(getString(R.string.description_must_be_at_least_three_words))
                  return@setOnClickListener
              }

              if(!isValidPrice){
                  onToast(getString(R.string.price_is_required_and_must_be_a_number))
                  return@setOnClickListener
              }

              if(!isValidQty){
                  onToast(getString(R.string.qty_is_required_and_must_be_a_number))
                  return@setOnClickListener
              }

              Items( ownerId, name, desc, price.toDouble(), qty.toInt(), uid)
          }

          viewModel.updateItem(item)
      }
    }

    private fun confirmDeleteDialog(item: Items){
        val dlg = AlertDialog.Builder(activity).apply {
            setMessage(getString(R.string.you_are_about_to_delete, item.name))
            setPositiveButton(getString(R.string.okay)) { dialog, _ ->
                viewModel.deleteItem(item)
                dialog.dismiss()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.dismiss() }
            create()
        }
        dlg.show()
    }

}