package com.fipp.ui.expenses

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.fipp.R
import com.fipp.databinding.FragmentExpensesCategoriesBinding
import com.fipp.model.Category

class ExpensesCategoriesFragment : Fragment() {
    private var categoryList: ArrayList<Category> = ArrayList()
    private var _binding: FragmentExpensesCategoriesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentExpensesCategoriesBinding.inflate(inflater, container, false)

        return binding.root
//        return inflater.inflate(R.layout.fragment_income_categories, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val boton: View = binding.buttonRegistrar

        boton.setOnClickListener {
//            val intent = Intent (parentFragment?.activity, RegisterNewIncomeCategoryActivity::class.java)
//            parentFragment?.activity?.startActivity(intent)

            val act = parentFragment?.parentFragment?.activity
            act?.startActivity(Intent(act, RegisterNewExpensesCategoryActivity::class.java))
        }

        getExpenseCategoriesByUser();

        val adapter = CategoryExpenseAdapter(categoryList, parentFragment?.parentFragment?.context)

        val recyclerView = binding.recyclerViewExpensesCategory

        val grid = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = grid
        recyclerView.adapter = adapter
    }

    private fun getExpenseCategoriesByUser(){
        val category1 = Category("","test 1", "subtest 1", R.drawable.fipp_app_iconos_22)
        val category2 = Category("", "test 2", "subtest 2", R.drawable.fipp_app_iconos_22)
        val category3 = Category("", "test 3", "subtest 3", R.drawable.fipp_app_iconos_22)
        val category4 = Category("", "test 4", "subtest 4", R.drawable.fipp_app_iconos_22)
        categoryList.addAll(listOf(category1, category2, category3, category4, category1, category2, category3, category4, category1, category2, category3, category4));
    }
}