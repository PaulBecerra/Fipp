package com.fipp.ui.expenses

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import com.fipp.R
import com.fipp.ui.income.RegisterNewIncomeSubcategoryActivity

class RegisterNewExpensesCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_new_expenses_category)

        val btn_close: ImageButton = findViewById(R.id.btn_new_expense_category_close)

        val btn: Button = findViewById(R.id.btn_new_expense_category)

        val btnSubcategory: CardView = findViewById(R.id.cardViewExpenseCategory)

        btn.setOnClickListener{
            finish()
        }

        btn_close.setOnClickListener{
            finish()
        }

        btnSubcategory.setOnClickListener{
            startActivity(Intent(this, RegisterNewExpensesSubcategoryActivity::class.java))
        }
    }
}