package com.oropeza.ec04_asot.view

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.oropeza.ec04_asot.R
import com.oropeza.ec04_asot.databinding.ActivitySplachScreenBinding

class SplachScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplachScreenBinding
    private lateinit var sharePreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplachScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharePreferences = getSharedPreferences(LoginActivity.SESSION_PREFERENCE, MODE_PRIVATE)
        Handler().postDelayed({
            val email: String = sharePreferences.getString(LoginActivity.EMAIL,"")?: ""
            val intent = if (email.isNotEmpty()) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        },2000)
    }
}