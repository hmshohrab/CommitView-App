package com.hmshohrab.commitview.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hmshohrab.commitview.R
import com.hmshohrab.commitview.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        bottomNavigationView.setupWithNavController(navController!!)


        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.commitFragment -> {
                    navController?.navigate(R.id.commitFragment)
                }

                R.id.profileFragment -> {
                    navController?.navigate(R.id.profileFragment)
                }

                else -> {

                }
            }
            false
        }

    }


}

