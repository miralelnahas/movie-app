package com.trianglz.ui

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleOnBackPressed(supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
    }

    private fun handleOnBackPressed(navHostFragment: NavHostFragment) {
        onBackPressedDispatcher.addCallback(this) {
            navHostFragment.navController.currentDestination?.id.let {
                navHostFragment.navController.popBackStack()
            }
        }
    }

}