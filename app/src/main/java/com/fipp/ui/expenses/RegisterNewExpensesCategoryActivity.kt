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
import com.fipp.controller.CategoryController
import com.fipp.controller.UserController
import com.fipp.model.Category
import com.fipp.model.Subcategory
import java.util.*

class RegisterNewExpensesCategoryActivity : AppCompatActivity() {

    val SUBCATEGORY_REQUEST = 0
    private lateinit var categoryController: CategoryController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_new_expenses_category)

        categoryController = CategoryController(this)

        val btn_close: ImageButton = findViewById(R.id.btn_new_expense_category_close)

        val btn: Button = findViewById(R.id.btn_new_expense_category)

        val btnSubcategory: CardView = findViewById(R.id.cardViewSubcategory)

        var editTextCategory: EditText = findViewById(R.id.editTextCategoryName)

        val cardView: CardView = findViewById(R.id.cardViewCategoryExpense)
        var categoryTextView: TextView = cardView.findViewById(R.id.textViewCategoryExpense)

        val btnSaveCategory: Button = findViewById(R.id.btn_new_expense_category)

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

        btnSaveCategory.setOnClickListener{
            val categoryName = categoryTextView.text.toString()

            var subcategoryTextView: TextView = cardView.findViewById(R.id.textViewSubCategoryExpense)
            val subcategoryName = subcategoryTextView.text.toString()

            var imagetView: ImageView = cardView.findViewById(R.id.imageViewCategoryExpense)

            val category = Category(UUID.randomUUID().toString(), categoryName, subcategoryName, 0, "Expenses")
            categoryController.saveCategory(category)
            finish()
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