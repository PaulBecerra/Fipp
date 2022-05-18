package com.fipp.ui.income

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fipp.R
import com.fipp.controller.IncomeController
import com.fipp.model.Category
import com.fipp.model.CategoryType
import com.fipp.model.Income
import java.time.LocalDateTime

class RegisterIncomeActivity : AppCompatActivity() {

    private var categoryList: ArrayList<Category> = ArrayList()
    private var currentPosition = -1
    private lateinit var incomeController: IncomeController

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_income)

        val btn_close: ImageButton = findViewById(R.id.btn_new_income_close)
        val btn: Button = findViewById(R.id.btn_new_income)

        incomeController = IncomeController(this)

        btn.setOnClickListener{
            // Create an instance of income
            val catogory = Category("4SPIEKMdIz3E59Kdud9N","PruebaCategoria", "LLfg1Ds8zC1jNj0FZlL9",0,CategoryType.INCOMES)
            val amountEditView: EditText = findViewById(R.id.editTextAmount)
            val amount = amountEditView.text.toString()
            // Check if amount is null
            if (amount.isEmpty()) {
                amountEditView.error = "Ingrese un monto"
                return@setOnClickListener
            }
            val income = Income(amount, LocalDateTime.now(), catogory)
            incomeController.createIncome(income)
            finish()
        }

        btn_close.setOnClickListener{

            finish()
        }

        getIncomeCategoriesByUser();

        val adapter = CategoryIncomeAdapter(categoryList, this)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewNewIncomeCategory)

        val grid: GridLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = grid
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        val mGestureDetector = GestureDetector(this@RegisterIncomeActivity, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }
        })

        recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onRequestDisallowInterceptTouchEvent(b: Boolean) {}
            override fun onInterceptTouchEvent(
                recyclerView: RecyclerView,
                motionEvent: MotionEvent
            ): Boolean {
                try {
                    val child: View? = recyclerView.findChildViewUnder(motionEvent.x, motionEvent.y)
                    if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                        val pos = recyclerView.getChildAdapterPosition(child)

                        currentPosition = pos

                        Toast.makeText(baseContext, "the item selected is $currentPosition", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return false
            }

            override fun onTouchEvent(recyclerView: RecyclerView, motionEvent: MotionEvent) {}
        })
    }

    private fun getIncomeCategoriesByUser(){
        val category1 = Category("","test 1", "subtest 1", R.drawable.fipp_app_iconos_22, CategoryType.INCOMES)
        val category2 = Category("","test 2", "subtest 2", R.drawable.fipp_app_iconos_22, CategoryType.INCOMES)
        val category3 = Category("","test 3", "subtest 3", R.drawable.fipp_app_iconos_22, CategoryType.INCOMES)
        val category4 = Category("","test 4", "subtest 4", R.drawable.fipp_app_iconos_22, CategoryType.INCOMES)
        categoryList.addAll(listOf(category1, category2, category3, category4, category1, category2, category3, category4, category1, category2, category3, category4));
    }
}