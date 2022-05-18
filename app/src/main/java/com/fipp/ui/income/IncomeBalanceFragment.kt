package com.fipp.ui.income

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.fipp.R
import com.fipp.controller.CategoryController
import com.fipp.controller.ExpenseController
import com.fipp.controller.IncomeController
import com.fipp.controller.MyCallbackCategory
import com.fipp.databinding.ActivityChartBinding
import com.fipp.databinding.FragmentIncomeBalanceBinding
import com.fipp.formatters.LineChartXAxisValueFormatter
import com.fipp.model.Category
import com.fipp.model.CategoryType
import com.fipp.model.Expense
import com.fipp.model.Income
import com.fipp.ui.expenses.MyCallback
import com.fipp.ui.expenses.MyCallbackIncome
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneOffset
import java.util.*
import kotlin.collections.ArrayList


class IncomeBalanceFragment : Fragment() {

    private var _binding: FragmentIncomeBalanceBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var expenses = java.util.ArrayList<Expense>()
    private var income = java.util.ArrayList<Income>()
    private var budget = 0.0
    private var totalExpense = 0.0
    private lateinit var expensesController: ExpenseController
    private lateinit var IncomeController: IncomeController
    private val db = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth = Firebase.auth
    private lateinit var categoryController: CategoryController
    private var entries = ArrayList<Entry>()

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthExpenses(
        month: Int,
        year: Int,
        myCallback: MyCallback
    ): java.util.ArrayList<Expense> {
        val user = auth.currentUser
        val userId = user?.uid
        // Create instance of localDateTime with the month and year
        val yearMonthObject = YearMonth.of(year, month)
        val daysInMonth = yearMonthObject.lengthOfMonth();


        val startDate = LocalDateTime.of(year, month, 1, 0, 0)
        val endDate = LocalDateTime.of(year, month, daysInMonth, 0, 0)
        val algo =
            db.collection("expenses").whereEqualTo("user", userId)
                .whereGreaterThanOrEqualTo("createdAt", startDate)
                .whereLessThanOrEqualTo("createdAt", endDate).get()

        algo.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val simon: java.util.ArrayList<Expense> = java.util.ArrayList<Expense>()
                for (document in task.result!!) {
                    val createdAt = document.data["createdAt"] as Map<*, *>
                    val date = createdAt["dayOfMonth"] as Long
                    val month = createdAt["monthValue"] as Long
                    val year = createdAt["year"] as Long
                    var category: Category? = null
                    categoryController.getCategoryById(
                        document.data["category"].toString(),
                        object :
                            MyCallbackCategory {
                            override fun onCallback(value: Category) {
                                category = value

                            }
                        })
                    val expense = Expense(
                        document.data["amount"].toString(),
                        LocalDateTime.of(year.toInt(), month.toInt(), date.toInt(), 0, 0),
                        category,
                    )
//                    Toast.makeText(activity, expense.amount, Toast.LENGTH_LONG).show()
//                    Toast.makeText(activity, "Expenses: $expenses", Toast.LENGTH_LONG).show()
                    simon.add(expense)

                }
                myCallback.onCallback(simon)
//                expenses = simon
            }
        }
//        Toast.makeText(activity, "Expenses2: $expenses", Toast.LENGTH_LONG).show()
        return expenses
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthIncome(
        month: Int,
        year: Int,
        myCallback: MyCallbackIncome
    ): java.util.ArrayList<Income> {
        val miau: java.util.ArrayList<String> = java.util.ArrayList()
        val user = auth.currentUser
        val userId = user?.uid
        // Create instance of localDateTime with the month and year
        val yearMonthObject = YearMonth.of(year, month)
        val daysInMonth = yearMonthObject.lengthOfMonth();


        val startDate = LocalDateTime.of(year, month, 1, 0, 0)
        val endDate = LocalDateTime.of(year, month, daysInMonth, 0, 0)
        val algo =
            db.collection("income").whereEqualTo("user", userId)
                .whereGreaterThanOrEqualTo("createdAt", startDate)
                .whereLessThanOrEqualTo("createdAt", endDate).get()

        algo.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val simon: java.util.ArrayList<Income> = java.util.ArrayList<Income>()
                for (document in task.result!!) {
                    val createdAt = document.data["createdAt"] as Map<*, *>
                    val date = createdAt["dayOfMonth"] as Long
                    val month = createdAt["monthValue"] as Long
                    val year = createdAt["year"] as Long
                    var category: Category? = null
                    categoryController.getCategoryById(
                        document.data["category"].toString(),
                        object :
                            MyCallbackCategory {
                            override fun onCallback(value: Category) {
                                category = value

                            }
                        })
                    val income = Income(
                        document.data["amount"].toString(),
                        LocalDateTime.of(year.toInt(), month.toInt(), date.toInt(), 0, 0),
                        category,
                    )
//                    Toast.makeText(activity, expense.amount, Toast.LENGTH_LONG).show()
//                    Toast.makeText(activity, "Expenses: $expenses", Toast.LENGTH_LONG).show()
                    simon.add(income)

                }
                myCallback.onCallback(simon)
//                expenses = simon
            }
        }
//        Toast.makeText(activity, "Expenses2: $expenses", Toast.LENGTH_LONG).show()
        return income
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        expensesController = ExpenseController(requireActivity())
        categoryController = CategoryController(requireActivity())


        // Create instance of LocalDateTime with the month and year
        val actualDate = LocalDateTime.now()

        this.getMonthIncome(actualDate.monthValue, actualDate.year, object : MyCallbackIncome {

            override fun onCallback(value: List<Income>) {
                income = value as java.util.ArrayList<Income>

            }

        })

        this.getMonthExpenses(actualDate.monthValue, actualDate.year, object : MyCallback {
            override fun onCallback(value: List<Expense>) {
                expenses = value as java.util.ArrayList<Expense>

                for(i in income){
                    budget += i.amount.toFloat()
                }
                for(i in expenses){
                    totalExpense += i.amount.toFloat()
                }

                setLineChart()

                loadProgressBar()

            }
        })



    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        loadProgressBar()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentIncomeBalanceBinding.inflate(inflater, container, false)
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
        var progress = (expenses * 100) / budget

        progress = 100 - progress
        progressBar.progress = progress.toInt()

        progressBar.progressTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                requireActivity(),
                R.color.verde_principal
            )
        )

    }


    /**
     *
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setLineChart() {
        Log.d("Expenses", "Entró a expenses")
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

        for (entry in entries) {
            Log.d("Entry", entry.toString())
        }

        val red = ContextCompat.getColor(requireActivity(), R.color.verde_principal)
        // First line
        val lineDataSet = LineDataSet(entries, "ACTUAL")
        lineDataSet.color = red
        lineDataSet.lineWidth = 5f
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);


        lineChart.xAxis.labelRotationAngle = 0f

        lineChart.xAxis.valueFormatter = LineChartXAxisValueFormatter()

        val dataSet = java.util.ArrayList<ILineDataSet>()
        dataSet.add(lineDataSet)

        val data = LineData(dataSet)

        lineChart.data = data

        lineChart.axisRight.isEnabled = false
        lineChart.description.text = ""
        lineChart.setNoDataText("No forex yet!")


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadChartData(): java.util.ArrayList<Entry> {
        val entries = java.util.ArrayList<Entry>()

        for (item in income) {
            Log.d("Income", item.toString())
            val x: Float = item.createdAt.atZone(ZoneOffset.UTC).toEpochSecond().toFloat()
            val y: Float = item.amount.toFloat()
            val entry = Entry(x, y)
            Log.d("Entry", entry.toString())
            entries.add(entry)
        }
//        Toast.makeText(activity, "Entries: $entries", Toast.LENGTH_LONG).show()
        return entries
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadTableChart() {
        val dateTimeFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        val table = binding.tableLayout

        for (item in income) {
            val row = TableRow(requireActivity())
            // Add padding to the row
            row.setPadding(10, 10, 10, 10)
            // Set background to transparent
            row.setBackgroundColor(Color.TRANSPARENT)


            val textView = TextView(requireActivity())
            val emissionsMilliSince1970Time =
                item.createdAt.atZone(ZoneOffset.UTC).toEpochSecond() * 1000
            val timeMilliseconds = Date(emissionsMilliSince1970Time)
            val date = dateTimeFormat.format(timeMilliseconds)
            textView.text = date
            textView.gravity = Gravity.CENTER
            textView.layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            row.addView(textView)

            val textView2 = TextView(requireActivity())
            textView2.text = item.category?.categoryName ?: "-"
            textView2.gravity = Gravity.CENTER
            textView2.layoutParams =
                TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            row.addView(textView2)

            val textView3 = TextView(requireActivity())
            textView3.text = item.amount
            textView3.gravity = Gravity.CENTER
            textView3.layoutParams =
                TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            row.addView(textView3)


            table.addView(row)
        }

    }

    fun setExpensesList(expenses: java.util.ArrayList<Expense>) {
        this.expenses = expenses
    }

    fun setIncomeList(income: java.util.ArrayList<Income>) {
        this.income = income
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause() {
        super.onPause()
        // Empty table
//        val table = binding.tableLayout
//        table.removeAllViews()

    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onResume() {
//        super.onResume()
//        Log.d("ExpensesBalanceFragment", "onResume")
//        setLineChart()
//    }
}

interface MyCallback {
    fun onCallback(value: List<Expense>)
}

interface MyCallbackIncome {
    fun onCallback(value: List<Income>)
}