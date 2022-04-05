package com.fipp.ui.gastos.mes

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fipp.R

class GastosMesCategoriasFragment : Fragment() {

    companion object {
        fun newInstance() = GastosMesCategoriasFragment()
    }

    private lateinit var viewModel: GastosMesCategoriasViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gastos_mes_categorias, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GastosMesCategoriasViewModel::class.java)
        // TODO: Use the ViewModel
    }

}