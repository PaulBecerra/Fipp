package com.fipp.ui.dashboard

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.fipp.R
import com.fipp.ui.expenses.RegisterExpenseActivity
import com.fipp.ui.income.RegisterIncomeActivity
import com.fipp.databinding.FragmentDashboardBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter


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

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadProgressBar()

        setHalfPieChart()
        setHalfPieChart2()

        val botonIncomes: View = binding.buttonIncomesHome
        val botonExpense: View = binding.buttonExpenseHome

        botonIncomes.setOnClickListener {
            val act = parentFragment?.activity
            act?.startActivity(Intent(act, RegisterIncomeActivity::class.java))
        }

        botonExpense.setOnClickListener {
            val act = parentFragment?.activity
            act?.startActivity(Intent(act, RegisterExpenseActivity::class.java))
        }
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

    private fun setHalfPieChart(){
        val pieChart: PieChart = binding.pieChart

        pieChart.setUsePercentValues(true)

        val green = ContextCompat.getColor(requireActivity(), R.color.verde)
        val gris = ContextCompat.getColor(requireActivity(), R.color.gris_claro)

        val yvalues = ArrayList<PieEntry>()
        yvalues.add(PieEntry(8f, 0))
        yvalues.add(PieEntry(12f, 2))


        val dataSet = PieDataSet(yvalues, "Reward Points")

        val data = PieData(dataSet)

        data.setValueFormatter(PercentFormatter())
        pieChart.data = data
        //pieChart.setDescription("This is Pie Chart");

        //pieChart.setDescription("This is Pie Chart");
        pieChart.setBackgroundColor(Color.TRANSPARENT)
        pieChart.setHoleColor(Color.TRANSPARENT)
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(0)
        pieChart.holeRadius = 18f
        pieChart.setDrawCenterText(true)
        pieChart.isRotationEnabled
        pieChart.isHighlightPerTapEnabled
        pieChart.setCenterTextOffset(0f, -20f)
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTypeface(Typeface.DEFAULT)
        pieChart.setEntryLabelTextSize(16f)
        pieChart.transparentCircleRadius = 11f

        pieChart.isDrawHoleEnabled = true


        pieChart.maxAngle = 180.0f
        pieChart.rotationAngle = 180.0f
        pieChart.setCenterTextSize(30f)

        dataSet.setColors(green, gris)
        data.setValueTextSize(13f)

        data.setValueTextColor(Color.DKGRAY)
        data.setDrawValues(false)
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        pieChart.description.text = ""
        pieChart.legend.isEnabled = false

        pieChart.holeRadius = 90f
        pieChart.setCenterTextSize(0f)
        pieChart.centerText = ""

        pieChart.setExtraOffsets(5f,0f,10f,-100f);



    }
    
    private fun setHalfPieChart2(){
        val pieChart: PieChart = binding.pieChart2

        pieChart.setUsePercentValues(true)

        val rojo = ContextCompat.getColor(requireActivity(), R.color.rojo)
        val gris = ContextCompat.getColor(requireActivity(), R.color.gris_claro)

        val yvalues = ArrayList<PieEntry>()
        yvalues.add(PieEntry(8f, 0))
        yvalues.add(PieEntry(12f, 2))


        val dataSet = PieDataSet(yvalues, "Reward Points")

        val data = PieData(dataSet)

        data.setValueFormatter(PercentFormatter())
        pieChart.data = data
        //pieChart.setDescription("This is Pie Chart");

        //pieChart.setDescription("This is Pie Chart");
        pieChart.setBackgroundColor(Color.TRANSPARENT)
        pieChart.setHoleColor(Color.TRANSPARENT)
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(0)
        pieChart.holeRadius = 18f
        pieChart.setDrawCenterText(true)
        pieChart.isRotationEnabled
        pieChart.isHighlightPerTapEnabled
        pieChart.setCenterTextOffset(0f, -20f)
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTypeface(Typeface.DEFAULT)
        pieChart.setEntryLabelTextSize(16f)
        pieChart.transparentCircleRadius = 11f

        pieChart.isDrawHoleEnabled = true


        pieChart.maxAngle = 180.0f
        pieChart.rotationAngle = 180.0f
        pieChart.setCenterTextSize(30f)

        dataSet.setColors(rojo, gris)
        data.setValueTextSize(13f)

        data.setValueTextColor(Color.DKGRAY)
        data.setDrawValues(false)
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        pieChart.description.text = ""
        pieChart.legend.isEnabled = false

        pieChart.holeRadius = 90f
        pieChart.setCenterTextSize(0f)
        pieChart.centerText = ""

        pieChart.setExtraOffsets(5f,0f,10f,-100f);



    }
}
