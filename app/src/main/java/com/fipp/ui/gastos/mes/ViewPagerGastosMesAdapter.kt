package com.fipp.ui.gastos.mes

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fipp.ui.gastos.mes.GastosMesBalanceFragment
import com.fipp.ui.gastos.mes.GastosMesCategoriasFragment
import com.fipp.ui.gastos.mes.GastosMesClasificacionFragment
import com.fipp.ui.gastos.mes.GastosMesFijosFragment

private const val NUM_TABS = 4

class ViewPagerGastosMesAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return GastosMesBalanceFragment()
            1 -> return GastosMesClasificacionFragment()
            2 -> return GastosMesFijosFragment()
            3 -> return GastosMesCategoriasFragment()
        }
        return GastosMesBalanceFragment()
    }
}