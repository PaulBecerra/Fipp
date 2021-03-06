package com.fipp.ui.expenses

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fipp.ui.income.*

// cambiar a 4 si vas a agregar clasificaciones y fijos!!
private const val NUM_TABS = 2

class ViewPagerExpensesAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return ExpensesBalanceFragment()
            // comenta la línea de abajo y descomenta las demás
            1 -> return ExpensesCategoriesFragment()
//            1 -> return ExpensesClasificationFragment()
//            2 -> return ExpensesSetFragment()
//            3 -> return ExpensesCategoriesFragment()
        }
        return ExpensesBalanceFragment()
    }
}