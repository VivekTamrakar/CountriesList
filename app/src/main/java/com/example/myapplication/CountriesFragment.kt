package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.viewmodel.CountriesViewModel
import com.example.myapplication.viewmodel.Result

/**
 * A fragment representing a list of Items.
 */
class CountriesFragment : Fragment() {

    lateinit var viewModel: CountriesViewModel

    val adapter = CountriesViewAdapter(listOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_countries, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            view.adapter = adapter
        }

        viewModel = ViewModelProvider(this)[CountriesViewModel::class.java]
        viewModel.lists.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> {
                    this.adapter.update(it.value)
                }
                is Result.Failure -> {
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        viewModel.fetchCountries()
    }

}
