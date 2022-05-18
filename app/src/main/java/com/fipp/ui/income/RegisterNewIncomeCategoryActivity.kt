package com.fipp.ui.income

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.widget.doOnTextChanged
import com.fipp.R
import com.fipp.model.Subcategory

class RegisterNewIncomeCategoryActivity : AppCompatActivity() {

    val SUBCATEGORY_REQUEST = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_new_income_category)

        val btn_close: ImageButton = findViewById(R.id.btn_new_income_category_close)

        val btn: Button = findViewById(R.id.btn_new_income_category)

        val btnSubcategory: CardView = findViewById(R.id.cardViewSubcategoryIncome)

        var editTextCategory : EditText = findViewById(R.id.editTextNewCategoryIncome)

        val cardView: CardView = findViewById(R.id.cardViewNewCategoryIncome)
        var categoryTextView: TextView = cardView.findViewById(R.id.textViewCategoryIncome)

        editTextCategory.doOnTextChanged{ text, start, count, after ->
            categoryTextView.text = text

        }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SUBCATEGORY_REQUEST){
            if (resultCode == RESULT_OK){
                val subcategory = data?.extras?.getSerializable("subcategory") as Subcategory
                val cardView: CardView = findViewById(R.id.cardViewCategoryExpense)
                val subcategoryTextView: TextView = cardView.findViewById(R.id.textViewSubCategoryExpense)
                val subcategoryImage: ImageView = cardView.findViewById(R.id.imageViewCategoryExpense)
                subcategoryTextView.text = subcategory.name
                subcategoryImage.setImageResource(subcategory.image)
            }
        }
    }
}