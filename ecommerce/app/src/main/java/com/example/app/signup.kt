package com.example.app

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class signup : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var btn: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var userDocument: DocumentReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_signup, container, false)

        email = view.findViewById(R.id.etmmnk)
        password = view.findViewById(R.id.etpassword)
        btn = view.findViewById(R.id.btn)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        btn.setOnClickListener {
            val userEmail = email.text.toString()
            val userPassword = password.text.toString()

            if (TextUtils.isEmpty(userEmail)) {
                email.error = "Email is required"
            } else if (!isValidEmail(userEmail)) {
                email.error = "Invalid email"
            } else if (TextUtils.isEmpty(userPassword)) {
                password.error = "Password is required"
            } else if (userPassword.length < 8) {
                password.error = "Password must be at least 8 characters"
            } else {
                createAccount(userEmail, userPassword)
            }
        }

        return view
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid
                userDocument = firestore.collection("users").document(userId.toString())

                val userInfo = mutableMapOf<String, String>()
                userInfo["user email"] = email
                userInfo["user password"] = password
                userInfo["name"] = ""
                userInfo["status"] = ""

                userDocument.set(userInfo).addOnSuccessListener {
                    Log.d("onSuccess", "Account created successfully")
                }
            } else {
                Log.d("onFailure", "Account creation failed")
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}
