package com.fipp.ui.gastos.mes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fipp.R

class GastosMesClasificacionFragment : Fragment() {

    companion object {
        fun newInstance() = GastosMesClasificacionFragment()
    }

    private lateinit var viewModel: GastosMesClasificacionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gastos_mes_clasificacion, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GastosMesClasificacionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}