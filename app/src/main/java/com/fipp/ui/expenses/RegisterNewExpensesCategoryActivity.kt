package com.fipp.ui.expenses

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.ipsec.ike.exceptions.InvalidMajorVersionException
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.fipp.R
import com.fipp.controller.CategoryController
import com.fipp.controller.UserController
import com.fipp.model.Category
import com.fipp.model.CategoryType
import com.fipp.model.Subcategory
import java.lang.Exception
import java.util.*

class RegisterNewExpensesCategoryActivity : AppCompatActivity() {

    val SUBCATEGORY_REQUEST = 0
    private lateinit var categoryController: CategoryController
    val PERM_IMG = 20
    val PICK_IMG = 18
    private lateinit var imageView: ImageView
    private lateinit var cardView: CardView
    private lateinit var subcategoryTextView: TextView
    private var imageUrl: String = ""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_new_expenses_category)

        categoryController = CategoryController(this)

        val btn_close: ImageButton = findViewById(R.id.btn_new_expense_category_close)

        val btnSubcategory: CardView = findViewById(R.id.cardViewSubcategory)

        var editTextCategory: EditText = findViewById(R.id.editTextCategoryName)

        cardView = findViewById(R.id.cardViewCategoryExpense)
        var categoryTextView: TextView = cardView.findViewById(R.id.textViewCategoryExpense)

        imageView = cardView.findViewById(R.id.imageViewCategoryExpense)
        subcategoryTextView = cardView.findViewById(R.id.textViewSubCategoryExpense)

        val btnSaveCategory: Button = findViewById(R.id.btn_new_expense_category)

        val searchImage : ImageButton = findViewById(R.id.imageButtonNewExpenseCategory)

        editTextCategory.doOnTextChanged{ text, start, count, after ->
            categoryTextView.text = text

        }

        btn_close.setOnClickListener{
            finish()
        }

        btnSubcategory.setOnClickListener{
            startActivityForResult(Intent(this, RegisterNewExpensesSubcategoryActivity::class.java), SUBCATEGORY_REQUEST)
        }

        btnSaveCategory.setOnClickListener{
            val categoryName = categoryTextView.text.toString()
            val subcategoryName = subcategoryTextView.text.toString()

            val category = Category(UUID.randomUUID().toString(), categoryName, subcategoryName, imageUrl, CategoryType.EXPENSES)
            categoryController.saveCategory(category)
            finish()
        }

        searchImage.setOnClickListener{
            if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                selectImage()
            } else {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERM_IMG)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERM_IMG -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    selectImage()
                } else{
                    Toast.makeText(this, "No aceptó permisos", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMG)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SUBCATEGORY_REQUEST){
            if (resultCode == RESULT_OK){
                val subcategory = data?.extras?.getSerializable("subcategory") as Subcategory
                subcategoryTextView.text = subcategory.name
                imageUrl = subcategory.imageUrl
                imageView.setImageResource(subcategory.image)
            }
        } else if (requestCode == PICK_IMG){
            try{
                val imgUri = data?.data
                val imgStream = contentResolver.openInputStream(imgUri!!)
                val imgBitmap = BitmapFactory.decodeStream(imgStream)
                imageView.setImageBitmap(imgBitmap)
                imageUrl = ""
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}