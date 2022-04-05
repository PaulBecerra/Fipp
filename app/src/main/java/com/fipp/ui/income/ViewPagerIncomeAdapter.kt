package com.fipp.ui.income

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fipp.*

private const val NUM_TABS = 4

public class ViewPagerIncomeAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return IncomeBalanceFragment()
            1 -> return IncomeClasificationFragment()
            2 -> return IncomeSetFragment()
            3 -> return IncomeCategoriesFragment()
        }
        return IncomeBalanceFragment()
    }
}