package com.example.inventoryappflutterwaveassessment.ui.fragments.ui.inventory.view

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.inventoryappflutterwaveassessment.R
import com.example.inventoryappflutterwaveassessment.data.storage.entities.Items
import com.example.inventoryappflutterwaveassessment.databinding.FragmentItemListBinding
import com.example.inventoryappflutterwaveassessment.extensions.onToast
import com.example.inventoryappflutterwaveassessment.extensions.verifyLogin
import com.example.inventoryappflutterwaveassessment.ui.activity.MainActivity
import com.example.inventoryappflutterwaveassessment.ui.fragments.ui.inventory.model.InventoryItem
import com.example.inventoryappflutterwaveassessment.ui.fragments.ui.login.mod.SimpleLoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.system.exitProcess


/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class ItemFragment : Fragment(R.layout.fragment_item_list) {

//    private val binding by viewBinding(FragmentPayTaxBinding::bind)

    val viewModel by viewModels<ItemFragmentViewModel>()

    private var _binding: FragmentItemListBinding? = null

    @Inject
    lateinit var pref: SharedPreferences

    private val adapter by lazy{
        MyItemRecyclerViewAdapter(listOf()
        ) { clickedItem -> onEdit(clickedItem) }.apply {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentItemListBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        verifyLogin(pref)

        setAdapter()
        setupClickListeners()
        setupEventListeners()
        viewModel.fetchInventory()
    }

    private fun setupClickListeners(){
        _binding?.toolbar?.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.logout -> {
                    pref.edit().clear().apply()
                    findNavController().navigate(ItemFragmentDirections.actionItemFragmentToSimpleLoginFragment())
                    true
                }
                else -> false
            }
        }
        _binding?.floatingActionButton2?.setOnClickListener {
            findNavController().navigate(ItemFragmentDirections.actionItemFragmentToAddInventoryFragment())
        }
    }
    private fun setAdapter(){
        _binding?.itemsRv?.adapter = adapter
    }

    private fun setupEventListeners(){
        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                is ItemFragmentViewModel.ItemFragmentState.Error -> onError(it.error)
                ItemFragmentViewModel.ItemFragmentState.Loading -> {}
                is ItemFragmentViewModel.ItemFragmentState.Success -> onSuccess(it.list)
            }
        }
    }

    private fun onError(error: String){
        onToast(error)
    }

    private fun onSuccess(list: List<Items>){
        adapter.submitList(list)
    }

    private fun onEdit(item: Items){
        val inventoryItem = item.run {
            InventoryItem(uid, ownerId, name, description, price, qty)
        }
        findNavController().navigate(ItemFragmentDirections.actionItemFragmentToEditInventoryFragment(inventoryItem))
    }

}