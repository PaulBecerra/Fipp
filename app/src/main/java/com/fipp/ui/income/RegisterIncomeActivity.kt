package com.fipp.ui.income

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fipp.R
import com.fipp.controller.IncomeController
import com.fipp.model.Category
import com.fipp.model.Income
import java.time.LocalDateTime

class RegisterIncomeActivity : AppCompatActivity() {

    private var categoryList: ArrayList<Category> = ArrayList()

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
            val catogory = Category("4SPIEKMdIz3E59Kdud9N","PruebaCategoria", "LLfg1Ds8zC1jNj0FZlL9")
            val amountEditView: EditText = findViewById(R.id.editTextTextPersonName2)
            val amount = amountEditView.text.toString()
            val income = Income(amount, LocalDateTime.now(), catogory)
            incomeController.createIncome(income)
            finish()
        }

        btn_close.setOnClickListener{

            finish()
        }

        getIncomeCategoriesByUser();

        val adapter = CategoryIncomeAdapter(categoryList)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewNewIncomeCategory)

        val grid: GridLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = grid
        recyclerView.adapter = adapter
    }

    private fun getIncomeCategoriesByUser(){
        val category1 = Category("","test 1", "subtest 1", R.drawable.fipp_app_iconos_22)
        val category2 = Category("","test 2", "subtest 2", R.drawable.fipp_app_iconos_22)
        val category3 = Category("","test 3", "subtest 3", R.drawable.fipp_app_iconos_22)
        val category4 = Category("","test 4", "subtest 4", R.drawable.fipp_app_iconos_22)
        categoryList.addAll(listOf(category1, category2, category3, category4, category1, category2, category3, category4, category1, category2, category3, category4));
    }
}