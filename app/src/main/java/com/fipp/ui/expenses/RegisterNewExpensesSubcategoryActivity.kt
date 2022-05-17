package com.fipp.ui.expenses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fipp.R
import com.fipp.model.Category
import com.fipp.model.Subcategory

class RegisterNewExpensesSubcategoryActivity : AppCompatActivity() {
    private var categoryList: ArrayList<Subcategory> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_new_expenses_subcategory)

        getExpensesSubCategoriesByUser();

        val adapter = SubcategoryExpensesAdapter(categoryList)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewSubcategoryExpenses)

        val grid = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = grid
        recyclerView.adapter = adapter

        val btn_close: ImageButton = findViewById(R.id.btn_new_expenses_subcategory_close)

        btn_close.setOnClickListener{
            finish()
        }
    }

    private fun getExpensesSubCategoriesByUser(){
        val category1 = Subcategory("Bills", R.drawable.ic_bills_24)
        val category2 = Subcategory("Car", R.drawable.ic_car_24)
        //val category3 = Subcategory("Clothes", R.drawable.)
        val category4 = Subcategory("Communication", R.drawable.ic_communication_24)
        val category5 = Subcategory("Eating out", R.drawable.ic_eating_out_24)
        //val category6 = Subcategory("Entertainment", R.drawable.)
        val category7 = Subcategory("Food", R.drawable.ic_food_24)
        val category8 = Subcategory("Gifts", R.drawable.ic_gifts_24)
        val category9 = Subcategory("Health", R.drawable.ic_health_24)
        val category10 = Subcategory("House", R.drawable.ic_baseline_home_24)
        val category11 = Subcategory("Pets", R.drawable.ic_pets_24)
        val category12 = Subcategory("Sports", R.drawable.ic_sports_24)
        val category13 = Subcategory("Taxi", R.drawable.ic_taxi_24)
        //val category14 = Subcategory("Toiletry", R.drawable.fipp_app_iconos_85)
        val category15 = Subcategory("Transport", R.drawable.ic_transport_24)
        categoryList.addAll(listOf(category1, category2, category4, category5, category7, category8, category9, category10, category11, category12, category13, category15));
    }
}