package com.fipp.ui.home

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.fipp.R
import com.fipp.controller.CategoryController
import com.fipp.controller.ExpenseController
import com.fipp.controller.MyCallbackCategory
import com.fipp.ui.expenses.RegisterExpenseActivity
import com.fipp.ui.income.RegisterIncomeActivity
import com.fipp.databinding.FragmentDashboardBinding
import com.fipp.model.Category
import com.fipp.model.CategoryType
import com.fipp.model.Expense
import com.fipp.model.Income
import com.fipp.ui.expenses.MyCallback
import com.fipp.ui.expenses.MyCallbackIncome
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.YearMonth


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var expenses = java.util.ArrayList<Expense>()
    private var income = java.util.ArrayList<Income>()
    private var budget = 0.0
    private var totalExpense = 0.0
    private val db = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth = Firebase.auth
    private lateinit var categoryController: CategoryController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthExpenses(month: Int, year: Int, myCallback : MyCallback): java.util.ArrayList<Expense> {
        val miau: java.util.ArrayList<String> = java.util.ArrayList()
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
                val simon : java.util.ArrayList<Expense> = java.util.ArrayList<Expense>()
                for (document in task.result!!) {
                    val createdAt = document.data["createdAt"] as Map<*, *>
                    val date = createdAt["dayOfMonth"] as Long
                    val month = createdAt["monthValue"] as Long
                    val year = createdAt["year"] as Long
                    var category: Category? = null
                    categoryController.getCategoryById(document.data["category"].toString(), object:
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
        return expenses    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthIncome(month: Int, year: Int, myCallback : MyCallbackIncome): java.util.ArrayList<Income> {
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
                val simon : java.util.ArrayList<Income> = java.util.ArrayList<Income>()
                for (document in task.result!!) {
                    val createdAt = document.data["createdAt"] as Map<*, *>
                    val date = createdAt["dayOfMonth"] as Long
                    val month = createdAt["monthValue"] as Long
                    val year = createdAt["year"] as Long
                    var category: Category? = null
                    categoryController.getCategoryById(document.data["category"].toString(), object:
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

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        categoryController = CategoryController(requireActivity())


        // Create instance of LocalDateTime with the month and year
        val actualDate = LocalDateTime.now()

        this.getMonthExpenses(actualDate.monthValue, actualDate.year, object: MyCallback {
            override fun onCallback(value: List<Expense>) {
                expenses = value as java.util.ArrayList<Expense>

            }
        })



        this.getMonthIncome(actualDate.monthValue, actualDate.year, object: MyCallbackIncome {

            override fun onCallback(value: List<Income>) {
                income = value as java.util.ArrayList<Income>



                loadProgressBar()
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        loadProgressBar()

        setHalfPieChartIncome()
        setHalfPieChartExpenses()

        val textViewIncome: TextView = binding.tvIncome
        val textViewExpense: TextView = binding.tvExpense

        textViewIncome.text = budget.toString()
        textViewExpense.text = totalExpense.toString()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        loadProgressBar()

        setHalfPieChartIncome()
        setHalfPieChartExpenses()

        val textViewIncome: TextView = binding.tvIncome
        val textViewExpense: TextView = binding.tvExpense

        textViewIncome.text = budget.toString()
        textViewExpense.text = totalExpense.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val category = Category("","Miau", "miau", "car.jpg",CategoryType.EXPENSES)
//
//        val date = LocalDateTime.of(2022,5,5, 0, 0)
//        val date2 = LocalDateTime.of(2022,5,15, 0, 0)
//        val date3 = LocalDateTime.of(2022,5,20, 0, 0)
//        val date4 = LocalDateTime.of(2022,5,22, 0, 0)
//
//        expenses.add(Expense("500.0", date, category))
//        expenses.add(Expense("600.0", date2, category))
//        expenses.add(Expense("400.0", date3, category))
//        expenses.add(Expense("800.0", date4, category))
//
//
//        income.add(Income("500.0", date, category))
//        income.add(Income("1000.0", date2, category))
//        income.add(Income("2500.0", date4, category))
//
//        for (money in income) {
//            budget += money.amount.toFloat()
//        }
//
//        for (expense in expenses){
//            totalExpense += expense.amount.toFloat()
//        }


//        loadProgressBar()
//
//        setHalfPieChartIncome()
//        setHalfPieChartExpenses()

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
        val progressBarIncome = binding.progressBar1
        progressBarIncome.visibility = View.VISIBLE

        var progress =  (totalExpense * 100 ) / budget

        progressBarIncome.progress = 100 - progress.toInt()

        progressBarIncome.progressTintList =
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.verde_principal))

        val progressBarExpenses = binding.progressBar2
        progressBarExpenses.visibility = View.VISIBLE
        progressBarExpenses.progress = progress.toInt()
        progressBarExpenses.progressTintList =
            ColorStateList.valueOf(ContextCompat.getColor(requireActivity(), R.color.rojo))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setHalfPieChartIncome(){
        val pieChart: PieChart = binding.pieChart

        pieChart.setUsePercentValues(true)

        val green = ContextCompat.getColor(requireActivity(), R.color.verde)
        val gris = ContextCompat.getColor(requireActivity(), R.color.gris_claro)

        var entries =  loadIncomePieChart()

        val dataSet = PieDataSet(entries, "Reward Points")

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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setHalfPieChartExpenses(){
        val pieChart: PieChart = binding.pieChart2

        pieChart.setUsePercentValues(true)

        val rojo = ContextCompat.getColor(requireActivity(), R.color.rojo)
        val gris = ContextCompat.getColor(requireActivity(), R.color.gris_claro)

        val enties = loadExpensesPieChart()



        val dataSet = PieDataSet(enties, "")

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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadIncomePieChart(): java.util.ArrayList<PieEntry> {
        val entries = java.util.ArrayList<PieEntry>()

        var progress =  (totalExpense * 100 ) / budget


        entries.add(PieEntry((100-progress).toFloat(), 0))
        entries.add(PieEntry(progress.toFloat(), 2))
        return entries
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadExpensesPieChart(): java.util.ArrayList<PieEntry> {
        val entries = java.util.ArrayList<PieEntry>()

        var progress =  (totalExpense * 100 ) / budget


        entries.add(PieEntry(progress.toFloat(), 0))
        entries.add(PieEntry((100-progress).toFloat(), 2))
        return entries
    }

}