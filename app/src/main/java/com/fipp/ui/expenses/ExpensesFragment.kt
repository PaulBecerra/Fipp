package com.fipp.ui.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fipp.databinding.FragmentExpensesBinding
import com.google.android.material.tabs.TabLayout

class ExpensesFragment : Fragment() {

    private var _binding: FragmentExpensesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val expensesViewModel =
//            ViewModelProvider(this).get(ExpensesViewModel::class.java)

        _binding = FragmentExpensesBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val viewPager = binding.viewPagerExpenses
        val tabLayout = binding.tabLayoutExpense

        viewPager.adapter = ViewPagerExpensesAdapter(childFragmentManager, lifecycle)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                viewPager.currentItem = tabLayout.selectedTabPosition
            }


        })

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }



}