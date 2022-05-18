package com.fipp.ui.notifications
import com.fipp.model.User
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fipp.LoginActivity
import com.fipp.R
import com.fipp.databinding.FragmentNotificationsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class NotificationsFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentNotificationsBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = Firebase.auth
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnLogout = binding.btnLogout
        var nombre: EditText = binding.editTextNombre
        var correo: EditText= binding.editTextEmail

        if (auth.currentUser != null) {

            db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener {
                val test = it.get("email")
                correo.setText(test.toString())
            }

            db.collection("users").document(auth.currentUser!!.uid).get().addOnSuccessListener {
                val test = it.get("name")
                nombre.setText(test.toString())

            }
        }




        btnLogout.setOnClickListener{
            signOut()
        }
    }
    fun updateUser(user: User) {
        val user1 = auth.currentUser
        val userId = user1?.uid

        db.collection("users").document(userId!!).set(
            hashMapOf("name" to user1.name)
        )
            .addOnSuccessListener {
                Toast.makeText(activity, "¡Edición exitosa!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(activity, "Hubo un error editando el nombre de usuario", Toast.LENGTH_SHORT).show()
            }
    }

    private fun signOut(){
        val prefs = activity?.getSharedPreferences(getString(R.string.shared_prefences), Context.MODE_PRIVATE)?.edit()
        prefs?.clear()
        prefs?.apply()

        Firebase.auth.signOut()

        val act = parentFragment?.activity
        act?.startActivity(Intent(act, LoginActivity::class.java))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}