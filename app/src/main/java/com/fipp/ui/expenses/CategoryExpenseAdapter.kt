package com.fipp.ui.expenses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fipp.R
import com.fipp.model.Category

class CategoryExpenseAdapter(private var categoryList: List<Category>) :
    RecyclerView.Adapter<CategoryExpenseAdapter.ViewHolder>() {

        class ViewHolder(view: View): RecyclerView.ViewHolder(view){
            val image: ImageView
            val categoryName: TextView
            val subCategoryName: TextView

            init {
                image = itemView.findViewById(R.id.imageViewCategoryExpense)
                categoryName = itemView.findViewById(R.id.textViewCategoryExpense)
                subCategoryName = itemView.findViewById(R.id.textViewSubCategoryExpense)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_categories_expense, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categoryList[position]
        holder.categoryName.text = category.categoryName
        holder.subCategoryName.text = category.subCategory
        holder.image.setImageResource(category.image)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}