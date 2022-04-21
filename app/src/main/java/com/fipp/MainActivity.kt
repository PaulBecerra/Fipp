package com.fipp

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.fipp.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.selectedItemId = R.id.navigation_dashboard

        // email received from login activity
        val bundle: Bundle? = intent.extras
        val email: String = bundle?.getString("email")?:""

        // email saved in shared preferences
        val prefs = getSharedPreferences(getString(R.string.shared_prefences), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.apply()


        /* save data
        db.collection("users")
            .document(email)
            .set(hashMapOf("key_test" to "field test"))
         */

        /* get data
        db.collection("users")
            .document(email)
            .get().addOnSuccessListener {
                 val test = it.get("key_test")
            }
         */

        //db.collection("users").document(email).delete()
    }
}