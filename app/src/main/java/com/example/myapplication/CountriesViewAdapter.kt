package com.example.myapplication

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication.databinding.FragmentCountryBinding
import com.example.myapplication.model.Country


class CountriesViewAdapter(
    private var values: List<Country>
) : RecyclerView.Adapter<CountriesViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentCountryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.codeView.text = item.code
        holder.nameView.text = item.name + ", " + item.region
        holder.capitalView.text = item.capital
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val codeView: TextView = binding.itemCode
        val nameView: TextView = binding.itemName
        val capitalView: TextView = binding.itemCapital

        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }
    }

    fun update(items: List<Country>) {
        this.values = items
        notifyDataSetChanged()
    }
}