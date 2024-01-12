package com.example.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class vendorActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var listbutton: Button
    private lateinit var selectImageButton: Button
    private lateinit var priceEditText: EditText
    private lateinit var productNameEditText: EditText
    private lateinit var storeNameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var uploadButton: Button
    private lateinit var imageUri: Uri
    private lateinit var storageReference: StorageReference
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val STORAGE_PATH = "product_images/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendor)

        // Initialize views
        imageView = findViewById(R.id.imageView)
        selectImageButton = findViewById(R.id.selectImageButton)
        priceEditText = findViewById(R.id.priceEditText)
        productNameEditText = findViewById(R.id.productNameEditText)
        storeNameEditText = findViewById(R.id.storeNameEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        uploadButton = findViewById(R.id.uploadButton)
        listbutton= findViewById(R.id.listButton)

        // Initialize Firebase Storage
        storageReference = FirebaseStorage.getInstance().reference

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Set click listener for select image button
        selectImageButton.setOnClickListener {
            openFileChooser()
        }

        // Set click listener for upload button
        uploadButton.setOnClickListener {
            uploadImage()
        }

        listbutton.setOnClickListener {
            val intent= Intent(this,viewlist::class.java)
            startActivity(intent)
        }
    }

    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data!!
            imageView.setImageURI(imageUri)
        }
    }

    private fun uploadImage() {
        if (imageUri != null) {
            val fileReference = storageReference.child(STORAGE_PATH + System.currentTimeMillis() + ".jpg")
            fileReference.putFile(imageUri)
                .addOnSuccessListener { uploadTask ->
                    uploadTask.storage.downloadUrl.addOnCompleteListener { downloadUrlTask ->
                        if (downloadUrlTask.isSuccessful) {
                            val imageUrl = downloadUrlTask.result.toString()
                            val price = priceEditText.text.toString()
                            val productName = productNameEditText.text.toString()
                            val storeName = storeNameEditText.text.toString()
                            val description = descriptionEditText.text.toString()
                            val userId = auth.currentUser?.uid
                            if (userId != null) {
                                saveProductToFirestore(userId, imageUrl, price, productName, storeName, description)
                            }

                            // Clear the input fields
                            imageView.setImageURI(null)
                            priceEditText.text.clear()
                            productNameEditText.text.clear()
                            storeNameEditText.text.clear()
                            descriptionEditText.text.clear()

                            Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Failed to get image URL", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveProductToFirestore(userId: String, imageUrl: String, price: String, productName: String, storeName: String, description: String) {
        // Get the collection reference for the products
        val productsRef = firestore.collection("products")

        // Create a new product document
        val product = hashMapOf(
            "userId" to userId,
            "imageUrl" to imageUrl,
            "price" to price,
            "productName" to productName,
            "storeName" to storeName,
            "description" to description
        )

        // Add the product document to the products collection
        productsRef.add(product)
            .addOnSuccessListener { documentReference ->
                // Product added successfully
                val productId = documentReference.id
                // Add the product to the user's folder
                val userProductRef = firestore.collection("users").document(userId).collection("products").document(productId)
                userProductRef.set(product)
                    .addOnSuccessListener {
                        // Product added to the user's folder successfully
                    }
                    .addOnFailureListener { exception ->
                        // Failed to add the product to the user's folder
                        // Handle the error or retry the operation
                    }
            }
            .addOnFailureListener { exception ->
                // Failed to add the product
                // Handle the error or retry the operation
            }
    }
}
