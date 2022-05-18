package com.fipp.ui.income

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fipp.R
import com.fipp.controller.IncomeController
import com.fipp.model.Category
import com.fipp.model.CategoryType
import com.fipp.model.Income
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

class RegisterIncomeActivity : AppCompatActivity() {

    private var categoryList: ArrayList<Category> = ArrayList()
    private var currentPosition = -1
    private lateinit var incomeController: IncomeController
    private val db = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth = Firebase.auth

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_income)

        val btn_close: ImageButton = findViewById(R.id.btn_new_income_close)
        val btn: Button = findViewById(R.id.btn_new_income)

        incomeController = IncomeController(this)

        btn.setOnClickListener{
            // Create an instance of income
            if (currentPosition == -1){
                Toast.makeText(baseContext, "Debe seleccionar una categoria", Toast.LENGTH_SHORT).show()
            } else {
                val category = categoryList[currentPosition]
                val amountEditView: EditText = findViewById(R.id.editTextAmount)
                val amount = amountEditView.text.toString()
                // Check if amount is null
                if (amount.isEmpty()) {
                    amountEditView.error = "Ingrese un monto"
                    return@setOnClickListener
                }
                val income = Income(amount, LocalDateTime.now(), category)
                incomeController.createIncome(income)
                finish()
            }
        }

        btn_close.setOnClickListener{

            finish()
        }

        this.getIncomeCategories( object: MyCallbackIncomeCategories {
            override fun onCallback(value: List<Category>) {
                categoryList = value as java.util.ArrayList<Category>

                configRecyclerview()
            }
        })
    }

    private fun configRecyclerview() {
        val adapter = CategoryIncomeAdapter(categoryList, this)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewNewIncomeCategory)

        val grid: GridLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = grid
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        val mGestureDetector = GestureDetector(this@RegisterIncomeActivity, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }
        })

        recyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onRequestDisallowInterceptTouchEvent(b: Boolean) {}
            override fun onInterceptTouchEvent(
                recyclerView: RecyclerView,
                motionEvent: MotionEvent
            ): Boolean {
                try {
                    val child: View? = recyclerView.findChildViewUnder(motionEvent.x, motionEvent.y)
                    if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                        val pos = recyclerView.getChildAdapterPosition(child)

                        currentPosition = pos

                        Toast.makeText(baseContext, "the item selected is $currentPosition", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return false
            }

            override fun onTouchEvent(recyclerView: RecyclerView, motionEvent: MotionEvent) {}
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getIncomeCategories(myCallback : MyCallbackIncomeCategories) {
        val user = auth.currentUser
        val userId = user?.uid

        db.collection("categories").whereEqualTo("user", userId)
            .whereEqualTo("categoryType", CategoryType.INCOMES.toString()).get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    val categoryList : ArrayList<Category> = ArrayList<Category>()
                    for (document in task.result!!){
                        val uuid = document.data["uuid"].toString()
                        val name = document.data["categoryName"].toString()
                        val subcategory = document.data["subCategory"].toString()
                        val image = document.data["image"].toString()
                        val category = Category(uuid, name, subcategory, image, CategoryType.INCOMES)
                        categoryList.add(category)
                    }
                    myCallback.onCallback(categoryList)
                }
            }
    }
}