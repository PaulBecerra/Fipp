package com.fipp.ui.income

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import com.fipp.R

class RegisterNewIncomeCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_new_income_category)

        val btn_close: ImageButton = findViewById(R.id.btn_new_income_category_close)

        val btn: Button = findViewById(R.id.btn_new_income_category)

        val btnSubcategory: CardView = findViewById(R.id.cardViewIncomeCategory)

        btn.setOnClickListener{
            finish()
        }

        btn_close.setOnClickListener{
            finish()
        }

        btnSubcategory.setOnClickListener{
            startActivity(Intent(this, RegisterNewIncomeSubcategoryActivity::class.java))
        }
    }
}