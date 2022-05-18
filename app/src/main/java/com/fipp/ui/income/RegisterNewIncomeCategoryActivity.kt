package com.fipp.ui.income

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.ipsec.ike.exceptions.InvalidMajorVersionException
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.fipp.R
import com.fipp.controller.CategoryController
import com.fipp.model.Category
import com.fipp.model.CategoryType
import com.fipp.model.Subcategory
import org.w3c.dom.Text
import java.lang.Exception
import java.util.*

class RegisterNewIncomeCategoryActivity : AppCompatActivity() {

    val SUBCATEGORY_REQUEST = 1
    private lateinit var categoryController: CategoryController
    private lateinit var cardView: CardView
    private lateinit var categoryTextView: TextView
    private lateinit var subcategoryTextView: TextView
    private lateinit var imagetView: ImageView
    private var imageUrl = ""
    val PERM_IMG = 22
    val PICK_IMG = 24

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_new_income_category)

        categoryController = CategoryController(this)

        val btn_close: ImageButton = findViewById(R.id.btn_new_income_category_close)

        val btnSubcategory: CardView = findViewById(R.id.cardViewSubcategoryIncome)

        var editTextCategory : EditText = findViewById(R.id.editTextNewCategoryIncome)

        cardView = findViewById(R.id.cardViewNewCategoryIncome)
        categoryTextView = cardView.findViewById(R.id.textViewCategoryIncome)

        subcategoryTextView = cardView.findViewById(R.id.textViewSubCategoryIncome)

        imagetView = cardView.findViewById(R.id.imageViewCategoryIncome)

        val btnSaveCategory: Button = findViewById(R.id.btn_new_income_category)

        val searchImage : ImageView = findViewById(R.id.searchImageIncomeCategory)

        editTextCategory.doOnTextChanged{ text, start, count, after ->
            categoryTextView.text = text

        }

        btn_close.setOnClickListener{
            finish()
        }

        btnSubcategory.setOnClickListener{
            startActivityForResult(Intent(this, RegisterNewIncomeSubcategoryActivity::class.java), SUBCATEGORY_REQUEST)
        }

        btnSaveCategory.setOnClickListener{
            val categoryName = categoryTextView.text.toString()

            subcategoryTextView = cardView.findViewById(R.id.textViewSubCategoryIncome)
            val subcategoryName = subcategoryTextView.text.toString()

            val category = Category(UUID.randomUUID().toString(), categoryName, subcategoryName, imageUrl, CategoryType.INCOMES)
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
                imagetView.setImageResource(subcategory.image)
            }
        } else if (requestCode == PICK_IMG){
            try{
                val imgUri = data?.data
                val imgStream = contentResolver.openInputStream(imgUri!!)
                val imgBitmap = BitmapFactory.decodeStream(imgStream)
                imagetView.setImageBitmap(imgBitmap)
                imageUrl = ""
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}