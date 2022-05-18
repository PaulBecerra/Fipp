package com.fipp.ui.expenses

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
import com.fipp.controller.ExpenseController
import com.fipp.model.Category
import com.fipp.model.CategoryType
import com.fipp.model.Expense
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

class RegisterExpenseActivity : AppCompatActivity() {
    private var categoryList: ArrayList<Category> = ArrayList()
    private lateinit var expensesController: ExpenseController
    private var currentPosition = -1
    private val db = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth = Firebase.auth

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_expense)

        val btn_close: ImageButton = findViewById(R.id.btn_new_expense_close)
        val btn: Button = findViewById(R.id.btn_new_expense)

        expensesController = ExpenseController(this)


        btn.setOnClickListener{
            // Create an instance of income
            val catogory = Category("4SPIEKMdIz3E59Kdud9N","PruebaCategoria", "LLfg1Ds8zC1jNj0FZlL9", "car.jpg",CategoryType.EXPENSES)
            val amountEditView: EditText = findViewById(R.id.editTextAmount)
            val amount = amountEditView.text.toString()
            // Check if amount is null
            if (amount.isEmpty()) {
                amountEditView.error = "Ingrese un monto"
                return@setOnClickListener
            }
            val expense = Expense(amount, LocalDateTime.now(), catogory)
            expensesController.createExpense(expense)
            finish()
        }

        btn_close.setOnClickListener{
            finish()
        }

        this.getExpensesCategories( object: MyCallbackExpensesCategories {
            override fun onCallback(value: List<Category>) {
                categoryList = value as java.util.ArrayList<Category>

                configRecyclerview()
            }
        })
    }

    private fun configRecyclerview() {
        val adapter = CategoryExpenseAdapter(categoryList, this)

        val mGestureDetector = GestureDetector(this@RegisterExpenseActivity, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }
        })

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewNewExpenseCategory)

        val grid: GridLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = grid
        recyclerView.adapter = adapter

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
    fun getExpensesCategories(myCallback : MyCallbackExpensesCategories) {
        val user = auth.currentUser
        val userId = user?.uid

        db.collection("categories").whereEqualTo("user", userId)
            .whereEqualTo("categoryType", CategoryType.EXPENSES.toString()).get()
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