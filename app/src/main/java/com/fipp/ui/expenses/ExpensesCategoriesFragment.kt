package com.fipp.ui.expenses

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fipp.R
import com.fipp.RegisterNewExpensesCategoryActivity
import com.fipp.RegisterNewIncomeCategoryActivity
import com.fipp.databinding.FragmentExpensesCategoriesBinding

class ExpensesCategoriesFragment : Fragment() {
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
    }

}