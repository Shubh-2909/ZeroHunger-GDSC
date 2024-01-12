package com.example.app

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.app.databinding.ActivityLandingBinding

class landing : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

// MainActivity.kt


                val button = findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.profile_image)

                // Set a click listener on the button
                button.setOnClickListener {
                    // Create an Intent to start SecondActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

