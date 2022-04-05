package com.fipp.ui.notifications

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fipp.ui.gastos.mes.GastosMesFragment
import com.fipp.ui.gastos.semana.GastosSemanaFragment

private const val NUM_TABS = 2

public class ViewPagerGastosAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return GastosMesFragment()
            1 -> return GastosSemanaFragment()
        }
        return GastosMesFragment()
    }
}