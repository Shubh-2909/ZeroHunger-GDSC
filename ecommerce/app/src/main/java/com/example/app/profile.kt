package com.example.app

import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream



class profile : AppCompatActivity()  {
    private lateinit var profile:de.hdodenhof.circleimageview.CircleImageView
    private lateinit var editprf:ImageView
    private lateinit var name:TextView
    private lateinit var status:TextView
    private lateinit var editname:TextInputLayout
    private lateinit var etname:TextInputEditText
    private lateinit var etstatus:TextInputEditText
    private lateinit var editstatus:TextInputLayout
    private lateinit var updatebtn:Button
    private lateinit var savebtn:Button
    private lateinit var userid: String

    private lateinit var auth:FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var image: ByteArray
    private lateinit var storageReference: StorageReference
    private val register = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        uploadImage(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profile = findViewById(R.id.profile_image)
        auth = FirebaseAuth.getInstance()
        editprf = findViewById(R.id.editimg)
        name = findViewById(R.id.name)
        status = findViewById(R.id.status)
        userid = auth.currentUser!!.uid

        editname = findViewById(R.id.nametr)
        etstatus = findViewById(R.id.etstatus)
        editstatus = findViewById(R.id.statustr)
        updatebtn = findViewById(R.id.updatebtn)
        savebtn = findViewById(R.id.savebtn)
        etname = findViewById(R.id.etname)

        val database = FirebaseDatabase.getInstance()
        db = database.reference.child("users").child(userid)
        storageReference = FirebaseStorage.getInstance().reference.child("$userid/pfp")

        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val nameValue = snapshot.child("name").value
                val statusValue = snapshot.child("status").value
                val pfpValue = snapshot.child("pfp").value

                // Check if name value is null or not available
                if (nameValue != null) {
                    name.text = nameValue.toString()
                }

                // Check if status value is null or not available
                if (statusValue != null) {
                    status.text = statusValue.toString()
                }

                // Check if pfp value is null or not available
                if (pfpValue != null) {
                    Picasso.get().load(pfpValue.toString()).into(profile)
                } else {
                    // Set default profile image if pfp value is null or not available
                    profile.setImageResource(R.drawable.default_image)
                }
            }


            override fun onCancelled(error: DatabaseError) {
                Log.d("Error", "Unable to fetch data: ${error.message}")
            }
        })

        updatebtn.visibility = View.VISIBLE
        updatebtn.setOnClickListener {
            name.visibility = View.GONE
            status.visibility = View.GONE
            updatebtn.visibility = View.GONE
            editname.visibility = View.VISIBLE
            editstatus.visibility = View.VISIBLE
            savebtn.visibility = View.VISIBLE
            etname.text = Editable.Factory.getInstance().newEditable(name.text.toString())
            etstatus.text = Editable.Factory.getInstance().newEditable(status.text.toString())
        }

        savebtn.setOnClickListener {
            name.visibility = View.VISIBLE
            status.visibility = View.VISIBLE
            editname.visibility = View.GONE
            editstatus.visibility = View.GONE
            savebtn.visibility = View.GONE
            updatebtn.visibility = View.VISIBLE
            val obj = mutableMapOf<String, Any>()
            obj["name"] = etname.text.toString()
            obj["status"] = etstatus.text.toString()
            db.updateChildren(obj).addOnSuccessListener {
                Log.d("Success", "Data successfully updated")
            }

        }

        editprf.setOnClickListener {
            photo()
        }
    }

    private fun photo() {
        register.launch(null)
    }

    private fun uploadImage(bitmap: Bitmap?) {
        val baos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        image = baos.toByteArray()
        storageReference.putBytes(image).addOnSuccessListener {
            storageReference.downloadUrl.addOnSuccessListener { uri ->
                val obj = mutableMapOf<String, Any>()
                obj["pfp"] = uri.toString()
                db.updateChildren(obj).addOnSuccessListener {
                    Log.d("OnSuccess", "pfp updated")
                }
            }
        }
    }
}
