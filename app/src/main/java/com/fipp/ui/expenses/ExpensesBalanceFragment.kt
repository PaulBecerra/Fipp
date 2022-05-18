package com.fipp.ui.expenses

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.fipp.formatters.LineChartXAxisValueFormatter
import com.fipp.R
import com.fipp.databinding.ActivityChartBinding
import com.fipp.databinding.FragmentExpensesBalanceBinding
import com.fipp.model.Category
import com.fipp.model.Expense
import com.fipp.model.Income
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
    private var expenses = ArrayList<Expense>()
    private var income = ArrayList<Income>()
    private var budget = 0.0
    val expensesCollection = db.collection("expenses")
    val incomeCollection = db.collection("income")

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val category = Category("Miau", "miau", 1)

        val date = LocalDateTime.of(2022,5,5, 0, 0)
        val date2 = LocalDateTime.of(2022,5,15, 0, 0)
        val date3 = LocalDateTime.of(2022,5,20, 0, 0)
        val date4 = LocalDateTime.of(2022,5,22, 0, 0)

        expenses.add(Expense("500.0", date, category))
        expenses.add(Expense("600.0", date2, category))
        expenses.add(Expense("400.0", date3, category))
        expenses.add(Expense("800.0", date4, category))


        income.add(Income("500.0", date, category))
        income.add(Income("1000.0", date2, category))
        income.add(Income("2500.0", date4, category))

        for (money in income) {
            budget += money.amount.toFloat()
        }
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

        var expenses = 0.0
        for (expense in this.expenses) {
            expenses += expense.amount.toFloat()
        }
        // set progress
        val progress =  (expenses * 100 ) / budget
        progressBar.progress = progress.toInt()


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

        loadTableChart()

        val entries = loadChartData()

        val red = ContextCompat.getColor(requireActivity(), R.color.rojo)
        // First line
        val lineDataSet = LineDataSet(entries, "ACTUAL")
        lineDataSet.color = red
        lineDataSet.lineWidth = 5f
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);


        lineChart.xAxis.labelRotationAngle = 0f

        lineChart.xAxis.valueFormatter = LineChartXAxisValueFormatter()

        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(lineDataSet)

        val data = LineData(dataSet)

        lineChart.data = data

        lineChart.axisRight.isEnabled = false
        lineChart.description.text = ""
        lineChart.setNoDataText("No forex yet!")

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadChartData(): ArrayList<Entry> {
        val entries = ArrayList<Entry>()

        for (item in expenses) {
            val x: Float = item.createdAt.atZone(ZoneOffset.UTC).toEpochSecond().toFloat()
            val y: Float = item.amount.toFloat()
            entries.add(Entry(x, y))
        }
        return entries
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadTableChart(){
        val dateTimeFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        val table = binding.tableLayout

        for (item in expenses) {
            val row = TableRow(requireActivity())
            // Add padding to the row
            row.setPadding(10, 10, 10, 10)
            // Set background to transparent
            row.setBackgroundColor(Color.TRANSPARENT)


            val textView = TextView(requireActivity())
            val emissionsMilliSince1970Time = item.createdAt.atZone(ZoneOffset.UTC).toEpochSecond() * 1000
            val timeMilliseconds = Date(emissionsMilliSince1970Time)
            val date = dateTimeFormat.format(timeMilliseconds)
            textView.text = date
            textView.gravity = Gravity.CENTER
            textView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            row.addView(textView)

            val textView2 = TextView(requireActivity())
            textView2.text = item.category.categoryName
            textView2.gravity = Gravity.CENTER
            textView2.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            row.addView(textView2)

            val textView3 = TextView(requireActivity())
            textView3.text = item.amount
            textView3.gravity = Gravity.CENTER
            textView3.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            row.addView(textView3)


            table.addView(row)
        }

    }

    fun setExpensesList(expenses: ArrayList<Expense>) {
        this.expenses = expenses
    }

    fun setIncomeList(income: ArrayList<Income>) {
        this.income = income
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}