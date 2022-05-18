package com.fipp.ui.expenses

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.widget.doOnTextChanged
import com.fipp.R
import com.fipp.model.Subcategory

class RegisterNewExpensesCategoryActivity : AppCompatActivity() {

    val SUBCATEGORY_REQUEST = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_new_expenses_category)

        val btn_close: ImageButton = findViewById(R.id.btn_new_expense_category_close)

        val btn: Button = findViewById(R.id.btn_new_expense_category)

        val btnSubcategory: CardView = findViewById(R.id.cardViewSubcategory)

        var editTextCategory: EditText = findViewById(R.id.editTextCategoryName)

        val cardView: CardView = findViewById(R.id.cardViewCategoryExpense)
        var categoryTextView: TextView = cardView.findViewById(R.id.textViewCategoryExpense)

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
            startActivityForResult(Intent(this, RegisterNewExpensesSubcategoryActivity::class.java), SUBCATEGORY_REQUEST)
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