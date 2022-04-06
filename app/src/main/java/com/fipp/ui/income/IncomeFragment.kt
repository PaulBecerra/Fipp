package com.fipp.ui.income

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.fipp.R
import com.fipp.databinding.FragmentIncomeBinding
import com.google.android.material.tabs.TabLayout

class IncomeFragment : Fragment() {

    private var _binding: FragmentIncomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentIncomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val viewPager: ViewPager2 = root.findViewById(R.id.viewPager)
        val tabLayout: TabLayout = root.findViewById(R.id.tabLayout)

        viewPager.adapter = ViewPagerIncomeAdapter(childFragmentManager, lifecycle)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                viewPager.currentItem = tabLayout.selectedTabPosition
            }


        })

        viewPager.registerOnPageChangeCallback( object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        })

        return root
    }



}