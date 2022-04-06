package com.fipp.ui.dashboard

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fipp.R
import com.fipp.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        loadProgressBar()


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadProgressBar() {
        val progressBar = binding.progressBar1
        progressBar.visibility = View.VISIBLE
        progressBar.progressTintList =
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.verde_principal))

        val progressBar2 = binding.progressBar2
        progressBar2.visibility = View.VISIBLE
        progressBar2.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.rojo))
    }
}