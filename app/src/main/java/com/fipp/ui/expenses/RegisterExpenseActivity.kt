package com.fipp.ui.expenses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fipp.R
import com.fipp.databinding.FragmentExpensesCategoriesBinding
import com.fipp.model.Category

class RegisterExpenseActivity : AppCompatActivity() {
    private var categoryList: ArrayList<Category> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_expense)

        val btn_close: ImageButton = findViewById(R.id.btn_new_expense_close)
        val btn: Button = findViewById(R.id.btn_new_expense)

        btn.setOnClickListener{
            finish()
        }

        btn_close.setOnClickListener{
            finish()
        }

        getExpenseCategoriesByUser();

        val adapter = CategoryExpenseAdapter(categoryList)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewNewExpenseCategory)

        val grid: GridLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = grid
        recyclerView.adapter = adapter
    }

    private fun getExpenseCategoriesByUser(){
        val category1 = Category("test 1", "subtest 1", R.drawable.fipp_app_iconos_22)
        val category2 = Category("test 2", "subtest 2", R.drawable.fipp_app_iconos_22)
        val category3 = Category("test 3", "subtest 3", R.drawable.fipp_app_iconos_22)
        val category4 = Category("test 4", "subtest 4", R.drawable.fipp_app_iconos_22)
        categoryList.addAll(listOf(category1, category2, category3, category4, category1, category2, category3, category4, category1, category2, category3, category4));
    }
}