package com.fipp.controller

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.fipp.R
import com.fipp.model.Category
import com.fipp.model.CategoryType
import com.fipp.model.Expense
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class CategoryController(private var activity: Activity) {

    private var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private val collection = "categories"
    private val storage = Firebase.storage
    private var imageName = ""
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
                it.data?.get("image").toString(),
                categoryType
            )
        }
        category?.let { callbackCategory.onCallback(it) }
//        return category
    }

    fun saveCategory(category: Category){
        val user = auth.currentUser
        val userId = user?.uid
        var imageView: ImageView? = null

        // si la instancia lleva la url
        if (category.image.isEmpty()){
            if (category.categoryType == CategoryType.EXPENSES){
                val cardView: CardView = activity.findViewById(R.id.cardViewCategoryExpense)
                imageView = cardView.findViewById(R.id.imageViewCategoryExpense)
                uploadImage(imageView)
            } else {
                val cardView: CardView = activity.findViewById(R.id.cardViewNewCategoryIncome)
                imageView = cardView.findViewById(R.id.imageViewCategoryIncome)

            }
            uploadImage(imageView)
        } else{
            imageName = category.image
        }



        db.collection(collection)
            .document(category.uid.toString())
            .set(
                hashMapOf(
                    "uuid" to category.uid.toString(),
                    "categoryName" to category.categoryName,
                        "subCategory" to category.subCategory,
                        "categoryType" to category.categoryType.toString(),
                        "image" to imageName,
                        "user" to userId)
            )
    }

    fun uploadImage(imageView: ImageView){
            val storageRf = storage.reference
            val df = SimpleDateFormat("yyyyMMdd_HHmmss")
            val fecha = Date()
            imageName = df.format(fecha) + ".jpg"
            val imageReference = storageRf.child("img/$imageName")

            imageView.isDrawingCacheEnabled = true
            imageView.buildDrawingCache()
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            var uploadTask = imageReference.putBytes(data)
            uploadTask.addOnFailureListener {
                Toast.makeText(
                    activity.baseContext,
                    "problemas al subir la imagen",
                    Toast.LENGTH_SHORT
                ).show()
            }.addOnSuccessListener { taskSnapshot ->

            }

    }
}
interface MyCallbackCategory {
    fun onCallback(value: Category)
}