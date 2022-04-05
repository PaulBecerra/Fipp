package com.fipp.ui.income

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fipp.R
import com.fipp.databinding.ActivityChartBinding
import com.fipp.databinding.FragmentIncomeBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

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

        loadProgressBar()

        setLineChart()


//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }


    private fun loadProgressBar() {
        val progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE
        // Change color to green

        progressBar.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.verde_principal))
    }

    /**
     *
     */
    private fun setLineChart(){
        val lineChartBinding: ActivityChartBinding = binding.lineChart
        var lineChart: LineChart = lineChartBinding.lineChart

        //Axe y
        // Get y axis
        val y = lineChart.axisLeft
        //Axe X
        val x: XAxis = lineChart.xAxis

        x.position = XAxis.XAxisPosition.BOTTOM
        lineChart.legend.isEnabled = false
        // Remove interceptions values
        lineChart.setDrawBorders(false)
        // Set label count to 4
        x.setLabelCount(4, true)
        // Set label count y axis to 5
        y.setLabelCount(5, true)


        val entries = ArrayList<Entry>()
        val entries2 = ArrayList<Entry>()
        val entries3 = ArrayList<Entry>()

        entries.add(Entry(1f, 4000f))
        entries.add(Entry(2f, 8000f))
        entries.add(Entry(3f, 15000f))
        entries.add(Entry(4f, 16000f))

        entries2.add(Entry(1f, 5000f))
        entries2.add(Entry(2f, 7000f))
        entries2.add(Entry(3f, 13000f))
        entries2.add(Entry(4f, 18000f))

        entries3.add(Entry(1f, 1000f))
        entries3.add(Entry(2f, 6000f))
        entries3.add(Entry(3f, 11000f))
        entries3.add(Entry(4f, 14000f))


        val green = ContextCompat.getColor(requireActivity(), R.color.verde_principal)
        val darkGreen = ContextCompat.getColor(requireActivity(), R.color.verde_obscuro)
        val gris = ContextCompat.getColor(requireActivity(), R.color.gris)
        // First line
        val lineDataSet = LineDataSet(entries, "ACTUAL")
        lineDataSet.color = green
        lineDataSet.lineWidth = 5f
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);


        val lineDataSet2 = LineDataSet(entries2, "DEFASE")
        lineDataSet2.color = darkGreen
        lineDataSet2.lineWidth = 5f
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setDrawValues(false);


        val lineDataSet3 = LineDataSet(entries3, "PRESUPUESTO")
        lineDataSet3.color = gris
        lineDataSet3.lineWidth = 5f
        lineDataSet3.setDrawCircles(false);
        lineDataSet3.setDrawValues(false);


        lineChart.xAxis.labelRotationAngle = 0f

        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(lineDataSet)
        dataSet.add(lineDataSet2)
        dataSet.add(lineDataSet3)

        var data = LineData(dataSet)

        lineChart.data = data

        lineChart.axisRight.isEnabled = false
        lineChart.description.text = ""
        lineChart.setNoDataText("No forex yet!")

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}