package com.fipp.ui.income

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.fipp.R
import com.fipp.databinding.FragmentIncomeCategoriesBinding
import com.fipp.model.Category

class IncomeCategoriesFragment : Fragment() {

    private var categoryList: ArrayList<Category> = ArrayList()
    private var _binding: FragmentIncomeCategoriesBinding? = null

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
        _binding = FragmentIncomeCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val boton: View = binding.buttonIncomes

        boton.setOnClickListener {
//            val intent = Intent (parentFragment?.activity, RegisterNewIncomeCategoryActivity::class.java)
//            parentFragment?.activity?.startActivity(intent)

            val act = parentFragment?.parentFragment?.activity
            act?.startActivity(Intent(act, RegisterNewIncomeCategoryActivity::class.java))
        }

        getIncomeCategoriesByUser();

        val adapter = CategoryIncomeAdapter(categoryList)

        val recyclerView = binding.recyclerViewIncomeCategory

        val grid = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = grid
        recyclerView.adapter = adapter
    }

    private fun getIncomeCategoriesByUser(){
        val category1 = Category("","test 1", "subtest 1", R.drawable.fipp_app_iconos_85)
        val category2 = Category("","test 2", "subtest 2", R.drawable.fipp_app_iconos_85)
        val category3 = Category("","test 3", "subtest 3", R.drawable.fipp_app_iconos_85)
        val category4 = Category("","test 4", "subtest 4", R.drawable.fipp_app_iconos_85)
        categoryList.addAll(listOf(category1, category2, category3, category4, category1, category2, category3, category4, category1, category2, category3, category4));
    }
}