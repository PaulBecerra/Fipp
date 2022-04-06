package com.fipp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class RegisterExpenseActivity : AppCompatActivity() {
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
    }
}