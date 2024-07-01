package com.example.inventoryappflutterwaveassessment.ui.fragments.ui.inventory.view

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.inventoryappflutterwaveassessment.data.storage.entities.Items

import com.example.inventoryappflutterwaveassessment.ui.fragments.ui.inventory.view.placeholder.PlaceholderContent.PlaceholderItem
import com.example.inventoryappflutterwaveassessment.databinding.FragmentItemBinding

class MyItemRecyclerViewAdapter(
    private val values: List<PlaceholderItem>,
    private val onClick : (Items) -> Unit
) : ListAdapter<Items, MyItemRecyclerViewAdapter.ViewHolder>( _diff ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = getItem(position)
        holder.nameTv.text = item.name
        holder.descTv.text = item.description
        holder.qtyTv.text = item.qty.toString()
        holder.priceTv.text = item.price.toString()
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val nameTv: TextView = binding.nameTv
        val descTv: TextView = binding.descTv
        val qtyTv: TextView = binding.qtyTv
        val priceTv: TextView = binding.priceTv
    }

}

private val _diff = object : DiffUtil.ItemCallback<Items>() {

    override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean =
        oldItem.uid == newItem.uid

    override fun areContentsTheSame(
        oldItem: Items,
        newItem: Items
    ): Boolean =
        (oldItem.uid == newItem.uid) &&
        (oldItem.name == newItem.name) &&
        (oldItem.description == newItem.description) &&
        (oldItem.price == newItem.price) &&
        (oldItem.qty == newItem.qty) &&
        (oldItem.ownerId == newItem.ownerId)
}