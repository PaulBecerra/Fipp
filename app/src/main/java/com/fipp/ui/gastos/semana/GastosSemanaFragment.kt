package com.fipp.ui.gastos.semana

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fipp.R

class GastosSemanaFragment : Fragment() {

    companion object {
        fun newInstance() = GastosSemanaFragment()
    }

    private lateinit var viewModel: GastosSemanaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gastos_semana, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GastosSemanaViewModel::class.java)
        // TODO: Use the ViewModel
    }

}