package com.oropeza.ec04_asot.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.oropeza.ec04_asot.R
import com.oropeza.ec04_asot.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var emailExtra: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        emailExtra = intent.getStringExtra("email")?: ""
        guardarEmail(emailExtra)
        binding.fabAddTitan.setOnClickListener {
            val intent = Intent(this, AddTitanActivity::class.java)
            startActivity(intent)
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_titan) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bnvMenu.setupWithNavController(navController)
    }

    private fun guardarEmail(email: String) {
        val sharedPreferences = getSharedPreferences("myPreferences",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email",email)
        editor.apply()
    }
}