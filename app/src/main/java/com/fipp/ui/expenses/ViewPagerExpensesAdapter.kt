package com.fipp.ui.expenses

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fipp.ui.income.*

private const val NUM_TABS = 4

class ViewPagerExpensesAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return ExpensesBalanceFragment()
            1 -> return ExpensesClasificationFragment()
            2 -> return ExpensesSetFragment()
            3 -> return ExpensesCategoriesFragment()
        }
        return ExpensesBalanceFragment()
    }
}