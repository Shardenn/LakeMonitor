package com.example.lakes

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.example.lakes.placeholder.PlaceholderContent.PlaceholderItem
import com.example.lakes.databinding.FragmentSuggestedLakeBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class SuggestedLakeRecyclerViewAdapter(
    private val values: List<PlaceholderItem>,
) : RecyclerView.Adapter<SuggestedLakeRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(FragmentSuggestedLakeBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.lakeID.text = item.lakeID
        holder.lakeLocality.text = item.lakeLocality
        holder.lakeName.text = item.lakeName
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentSuggestedLakeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val lakeID: TextView = binding.lakeId
        val lakeLocality: TextView = binding.locality
        val lakeName: TextView = binding.name

        override fun toString(): String {
            return super.toString() + " '" + lakeName.text + "'"
        }
    }

}