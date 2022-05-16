package com.fipp.ui.expenses

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.transition.Scene
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.fipp.LineChartXAxisValueFormatter
import com.fipp.R
import com.fipp.databinding.ActivityChartBinding
import com.fipp.databinding.FragmentExpensesBalanceBinding
import com.fipp.model.Category
import com.fipp.model.Expense
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*


class ExpensesBalanceFragment : Fragment() {

    private var _binding: FragmentExpensesBalanceBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExpensesBalanceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadProgressBar()

        setLineChart()


    }

    private fun loadProgressBar() {
        val progressBar = binding.progressBar

        progressBar.visibility = View.VISIBLE

        progressBar.progressTintList = ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.rojo))
    }

    /**
     *
     */
    @RequiresApi(Build.VERSION_CODES.O)
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




        val expenses: ArrayList<Expense> = ArrayList()
        val expenses2: ArrayList<Expense> = ArrayList()

        val category = Category("Miau", "miau", 1)

        val date = LocalDateTime.of(2022,5,5, 0, 0)
        val date2 = LocalDateTime.of(2022,5,15, 0, 0)
        val date3 = LocalDateTime.of(2022,5,20, 0, 0)
        val date4 = LocalDateTime.of(2022,5,22, 0, 0)

        expenses.add(Expense("500.0", date, category))
        expenses.add(Expense("600.0", date2, category))
        expenses.add(Expense("400.0", date3, category))
        expenses.add(Expense("800.0", date4, category))

        loadTableChart(expenses)

        val entries = loadChartData(expenses)

        expenses2.add(Expense("570.0", date, category))
        expenses2.add(Expense("800.0", date2, category))
        expenses2.add(Expense("450.0", date3, category))
        expenses2.add(Expense("160.0", date4, category))

        val entries2 = loadChartData(expenses2)


        val green = ContextCompat.getColor(requireActivity(), R.color.rojo)
        val darkGreen = ContextCompat.getColor(requireActivity(), R.color.rojo_obscuro)
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
//
//
//        val lineDataSet3 = LineDataSet(entries3, "PRESUPUESTO")
//        lineDataSet3.color = gris
//        lineDataSet3.lineWidth = 5f
//        lineDataSet3.setDrawCircles(false);
//        lineDataSet3.setDrawValues(false);


        lineChart.xAxis.labelRotationAngle = 0f

        lineChart.xAxis.valueFormatter = LineChartXAxisValueFormatter()

        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(lineDataSet)
        dataSet.add(lineDataSet2)
//        dataSet.add(lineDataSet3)

        val data = LineData(dataSet)

        lineChart.data = data

        lineChart.axisRight.isEnabled = false
        lineChart.description.text = ""
        lineChart.setNoDataText("No forex yet!")

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadChartData(data: ArrayList<Expense>): ArrayList<Entry> {
        val entries = ArrayList<Entry>()

        for (item in data) {
            val x: Float = item.createdAt.atZone(ZoneOffset.UTC).toEpochSecond().toFloat()
            val y: Float = item.amount.toFloat()
            entries.add(Entry(x, y))
        }
        return entries
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadTableChart(data: ArrayList<Expense>){
        val dateTimeFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        val table = binding.tableLayout

        for (item in data){
            val row = TableRow(requireActivity())
            // Add padding to the row
            row.setPadding(10, 10, 10, 10)
            val textView = TextView(requireActivity())
            val emissionsMilliSince1970Time = item.createdAt.atZone(ZoneOffset.UTC).toEpochSecond() * 1000
            val timeMilliseconds = Date(emissionsMilliSince1970Time)
            val date = dateTimeFormat.format(timeMilliseconds)
            textView.text = date
            textView.gravity = Gravity.CENTER
            // Set weight for column
            textView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            // set widht as wrap content
            textView.setTextColor(Color.BLACK)
            textView.setBackgroundColor(Color.WHITE)
            row.addView(textView)

            val textView2 = TextView(requireActivity())
            textView2.text = item.category.categoryName
            textView2.gravity = Gravity.CENTER
            // Set weight for column
            textView2.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            // set widht as wrap content
            textView2.setTextColor(Color.BLACK)
            textView2.setBackgroundColor(Color.WHITE)
            row.addView(textView2)

            val textView3 = TextView(requireActivity())
            textView3.text = item.amount
            textView3.gravity = Gravity.CENTER
            // Set weight for column
            textView3.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            // set widht as wrap content
            textView3.setTextColor(Color.BLACK)
            textView3.setBackgroundColor(Color.WHITE)
            row.addView(textView3)


            table.addView(row)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}