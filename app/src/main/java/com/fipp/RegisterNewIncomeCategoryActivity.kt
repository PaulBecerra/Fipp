package com.fipp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton

class RegisterNewIncomeCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_new_income_category)

        val btn_close: ImageButton = findViewById(R.id.btn_new_income_category_close)

        val btn: Button = findViewById(R.id.btn_new_income_category)

        btn.setOnClickListener{
            finish()
        }

        btn_close.setOnClickListener{
            finish()
        }
    }
}