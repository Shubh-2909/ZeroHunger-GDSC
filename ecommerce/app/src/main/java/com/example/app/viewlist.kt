package com.example.app

import Product
import ProductsAdapter
import android.os.Bundle
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class viewlist : AppCompatActivity() {

    private lateinit var gridView: GridView
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewlist)

        // Initialize views
        gridView = findViewById(R.id.gridView)

        // Initialize Firebase Firestore
        firestore = FirebaseFirestore.getInstance()

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Get the current user's ID
        val userId = auth.currentUser?.uid

        if (userId != null) {
            // Retrieve products from the user's folder in Firestore
            val productsRef = firestore.collection("users").document(userId).collection("products")
                .orderBy("productName", Query.Direction.ASCENDING)

            productsRef.get()
                .addOnSuccessListener { querySnapshot ->
                    val productsList = querySnapshot.toObjects(Product::class.java)
                    displayProducts(productsList)
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Failed to retrieve products", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun displayProducts(productsList: List<Product>) {
        productsAdapter = ProductsAdapter(this, productsList)
        productsAdapter.setOnItemClickListener(object : ProductsAdapter.OnItemClickListener {
            override fun onDeleteClick(product: Product) {
                // Delete the product from Firestore
                deleteProduct(product)
            }
        })
        gridView.adapter = productsAdapter
    }

    private fun deleteProduct(product: Product) {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            // Reference to the product document in Firestore
            val productRef = firestore.collection("users").document(userId).collection("products")
                .document(product.productId)

            // Delete the product document
            productRef.delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Product deleted", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Failed to delete product", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
