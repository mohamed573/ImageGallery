package com.example.githubapiparsing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val controll = this.findNavController(R.id.my_nav_host)
        NavigationUI.setupActionBarWithNavController(this, controll)
    }

    override fun onSupportNavigateUp(): Boolean {
        val controller = this.findNavController(R.id.my_nav_host)
        return controller.navigateUp()
    }
}
