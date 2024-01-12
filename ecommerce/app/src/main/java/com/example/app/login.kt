package com.example.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var buttont: Button
    private lateinit var exitbuttont: Button
    private lateinit var pbuttont: ImageButton
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        button = findViewById(R.id.btn)
        button.setOnClickListener {
            val intent = Intent(this@login, vendorActivity::class.java)
            startActivity(intent)
        }

        buttont = findViewById(R.id.btnn)
        buttont.setOnClickListener {
            val intent = Intent(this@login, BuyerActivity::class.java)
            startActivity(intent)
        }

        exitbuttont = findViewById(R.id.btn4)
        exitbuttont.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, login1::class.java)
            startActivity(intent)
            finish()
            val toastMessage = "Clear data of the app if logout button didn't work"
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()





        }

        pbuttont = findViewById(R.id.btn5)
        pbuttont.setOnClickListener {
            val intent = Intent(this, profile::class.java)
            startActivity(intent)



        }

    }
}
