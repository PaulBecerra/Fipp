package com.fipp.ui.income

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

// cambiar a 4 si vas a agregar clasificaciones y fijos!!
private const val NUM_TABS = 2

public class ViewPagerIncomeAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return IncomeBalanceFragment()
            // comenta la línea de abajo y descomenta las demás
            1 -> return IncomeCategoriesFragment()
//            1 -> return IncomeClasificationFragment()
//            2 -> return IncomeSetFragment()
//            3 -> return IncomeCategoriesFragment()
        }
        return IncomeBalanceFragment()
    }
}