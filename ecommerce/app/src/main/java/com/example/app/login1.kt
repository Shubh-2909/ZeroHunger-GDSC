package com.example.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [login1.newInstance] factory method to
 * create an instance of this fragment.
 */
class login1 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var progressBar: ProgressBar
    private lateinit var button: Button
    private lateinit var ggl: Button
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var result: ActivityResultLauncher<Intent?>
    private val RC_SIGN_IN = 1011
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login1, container, false)
        email = view.findViewById(R.id.etlogin)
        password = view.findViewById(R.id.etpassword)
        button = view.findViewById(R.id.btn)

        auth = FirebaseAuth.getInstance()

        button.setOnClickListener {
            val eml = email.text.toString()
            val pass = password.text.toString()
            if (TextUtils.isEmpty(eml)) {
                email.error = "email is required"
            } else if (TextUtils.isEmpty(pass)) {
                password.error = "password is required"
            } else if (pass.length < 8) {
                password.error = "password must be greater than 8 characters"
            } else {
                signIn(eml, pass)
            }
        }

        result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val launchData = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(launchData)

                try {
                    val account = task.getResult(ApiException::class.java)
                    Log.d("Gmail ID", "firebaseAuthWith Google: $account")
                    firebaseAuthWithGoogle(account?.idToken)
                } catch (e: ApiException) {
                    Log.w("Error", "Google Sign IN Failed", e)
                }
            }
        }

        return view
    }

    private fun createRequest() {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("Google ID")
            .requestEmail()
            .build()
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        // Handle Google Sign-In with Firebase Authentication
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Login successful", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Login failed", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun signOut() {
        auth.signOut()
        // Perform any additional logout operations
        Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
    }
}
