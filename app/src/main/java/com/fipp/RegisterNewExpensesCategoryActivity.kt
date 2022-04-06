package com.fipp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class RegisterNewExpensesCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_new_expenses_category)
        val btn_close: ImageButton = findViewById(R.id.btn_new_expense_category_close)

        val btn: Button = findViewById(R.id.btn_new_expense_category)

        btn.setOnClickListener{
            finish()
        }

        btn_close.setOnClickListener{
            finish()
        }
    }
}