package com.fipp.ui.income

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fipp.R
import com.fipp.model.Category
import com.fipp.model.Subcategory

class SubcategoryIncomeAdapter(private var categoryList: List<Subcategory>, var activity: Activity) :
    RecyclerView.Adapter<SubcategoryIncomeAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image: ImageView
        val subCategoryName: TextView

        init {
            image = itemView.findViewById(R.id.imageViewSubCategoryIncome)
            subCategoryName = itemView.findViewById(R.id.textViewSubCategoryNameIncome)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_subcategory, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categoryList[position]
        holder.subCategoryName.text = category.name
        holder.image.setImageResource(category.image)

        holder.itemView.setOnClickListener{
            val intent = Intent()
            intent.putExtra("subcategory", category)
            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}