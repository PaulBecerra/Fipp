package com.fipp.controller

import android.app.Activity
import android.widget.Toast
import com.fipp.model.Category
import com.fipp.model.CategoryType
import com.fipp.model.Expense
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

class CategoryController(private var activity: Activity) {

    private var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private val collection = "categories"

    init {
        this.auth = Firebase.auth
    }

    fun getCategoryById(id: String, callbackCategory: MyCallbackCategory) {
        var category: Category? = null
        db.collection(collection).document(id).get().addOnSuccessListener {
            val categoryType: CategoryType
            if (it.data?.get("categoryType").toString() == CategoryType.INCOMES.toString()){
                categoryType = CategoryType.INCOMES
            } else{
                categoryType = CategoryType.EXPENSES
            }
            category = Category(
                it.id,
                it.data?.get("categoryName").toString(),
                it.data?.get("subCategory").toString(),
                0,
                categoryType
            )
        }
        category?.let { callbackCategory.onCallback(it) }
//        return category
    }

    fun saveCategory(category: Category){
        db.collection(collection)
            .document(category.uid.toString())
            .set(
                hashMapOf("categoryName" to category.categoryName,
                        "subCategory" to category.subCategory,
                        "categoryType" to category.categoryType.toString())
            )
    }
}
interface MyCallbackCategory {
    fun onCallback(value: Category)
}