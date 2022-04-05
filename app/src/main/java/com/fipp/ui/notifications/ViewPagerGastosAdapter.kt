package com.fipp.ui.notifications

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fipp.GastosBalanceFragment
import com.fipp.GastosCategoriasFragment
import com.fipp.GastosClasificacionFragment
import com.fipp.GastosFijosFragment

private const val NUM_TABS = 4

public class ViewPagerGastosAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return GastosBalanceFragment()
            1 -> return GastosClasificacionFragment()
            2 -> return GastosFijosFragment()
            3 -> return GastosCategoriasFragment()
        }
        return GastosBalanceFragment()
    }
}