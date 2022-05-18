package com.fipp.ui.income

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.widget.doOnTextChanged
import com.fipp.R
import com.fipp.controller.CategoryController
import com.fipp.model.Category
import com.fipp.model.CategoryType
import com.fipp.model.Subcategory
import java.util.*

class RegisterNewIncomeCategoryActivity : AppCompatActivity() {

    val SUBCATEGORY_REQUEST = 1
    private lateinit var categoryController: CategoryController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_new_income_category)

        categoryController = CategoryController(this)

        val btn_close: ImageButton = findViewById(R.id.btn_new_income_category_close)

        val btn: Button = findViewById(R.id.btn_new_income_category)

        val btnSubcategory: CardView = findViewById(R.id.cardViewSubcategoryIncome)

        var editTextCategory : EditText = findViewById(R.id.editTextNewCategoryIncome)

        val cardView: CardView = findViewById(R.id.cardViewNewCategoryIncome)
        var categoryTextView: TextView = cardView.findViewById(R.id.textViewCategoryIncome)

        val btnSaveCategory: Button = findViewById(R.id.btn_new_income_category)

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
            startActivityForResult(Intent(this, RegisterNewIncomeSubcategoryActivity::class.java), SUBCATEGORY_REQUEST)
        }

        btnSaveCategory.setOnClickListener{
            val categoryName = categoryTextView.text.toString()

            var subcategoryTextView: TextView = cardView.findViewById(R.id.textViewSubCategoryIncome)
            val subcategoryName = subcategoryTextView.text.toString()

            var imagetView: ImageView = cardView.findViewById(R.id.imageViewCategoryIncome)

            val category = Category(UUID.randomUUID().toString(), categoryName, subcategoryName, 0, CategoryType.INCOMES)
            categoryController.saveCategory(category)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SUBCATEGORY_REQUEST){
            if (resultCode == RESULT_OK){
                val subcategory = data?.extras?.getSerializable("subcategory") as Subcategory
                val cardView: CardView = findViewById(R.id.cardViewNewCategoryIncome)
                val subcategoryTextView: TextView = cardView.findViewById(R.id.textViewSubCategoryIncome)
                val subcategoryImage: ImageView = cardView.findViewById(R.id.imageViewCategoryIncome)
                subcategoryTextView.text = subcategory.name
                subcategoryImage.setImageResource(subcategory.image)
            }
        }
    }
}