package com.example.app

import Product
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.squareup.picasso.Picasso

class BuyerActivity : AppCompatActivity() {

    private lateinit var gridView: GridView
    private lateinit var button: Button
    private lateinit var clearButton: Button
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: MutableList<Product>
    private var totalCost: Double = 0.0

    private lateinit var databaseReference: DatabaseReference
    private lateinit var firestore: FirebaseFirestore

    private val selectedProducts: MutableList<Product> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // Initialize GridView and ProductAdapter
        gridView = findViewById(R.id.categories_grid)
        productList = mutableListOf()
        productAdapter = ProductAdapter()
        gridView.adapter = productAdapter
        button = findViewById(R.id.button)
        clearButton = findViewById(R.id.clear)

        // Initialize the Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().reference

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Retrieve the total cost from the Firebase Realtime Database
        retrieveTotalCost()

        // Retrieve products from Firestore
        retrieveProducts()

        button.setOnClickListener {
            val intent = Intent(this@BuyerActivity, CartActivity::class.java)
            intent.putExtra("totalCost", totalCost) // Pass the total cost to the CartActivity
            intent.putParcelableArrayListExtra("selectedProducts", ArrayList(selectedProducts)) // Pass the selected products to the CartActivity
            startActivity(intent)
        }

        clearButton.setOnClickListener {
            // Clear the cart or perform any other action as needed
            totalCost = 0.0
            selectedProducts.clear()
            updateTotalCostInDatabase()
        }
    }

    private fun retrieveProducts() {
        // Get the collection reference for the products
        val productsRef = firestore.collection("products")

        // Query the products collection
        productsRef.get()
            .addOnSuccessListener { querySnapshot: QuerySnapshot? ->
                // Clear the existing product list
                productList.clear()

                // Iterate through the query snapshot and add products to the list
                querySnapshot?.forEach { documentSnapshot ->
                    val product = documentSnapshot.toObject(Product::class.java)
                    productList.add(product)
                }

                // Notify the adapter that the data has changed
                productAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle any errors that occur while retrieving products
                // Display an error message or retry the retrieval
            }
    }

    inner class ProductAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return productList.size
        }

        override fun getItem(position: Int): Any {
            return productList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view: View
            val viewHolder: ViewHolder

            if (convertView == null) {
                view = LayoutInflater.from(this@BuyerActivity).inflate(R.layout.grid_item_layout, null)
                viewHolder = ViewHolder()
                viewHolder.imageView = view.findViewById(R.id.product_image)
                viewHolder.priceTextView = view.findViewById(R.id.product_price)
                viewHolder.nameTextView = view.findViewById(R.id.product_name)
                viewHolder.storeTextView = view.findViewById(R.id.store_name)
                viewHolder.plusButton = view.findViewById(R.id.plus_button)
                viewHolder.minusButton = view.findViewById(R.id.minus_button)
                view.tag = viewHolder
            } else {
                view = convertView
                viewHolder = convertView.tag as ViewHolder
            }

            val product = productList[position]

            // Set the product image using Picasso library
            val imageUrl = product.imageUrl
            if (imageUrl.isNotBlank()) {
                Picasso.get()
                    .load(imageUrl)
                    .fit()
                    .centerCrop()
                    .into(viewHolder.imageView)
            }

            // Set the product price
            val price = product.price
            viewHolder.priceTextView.text = price

            // Set the product name
            val name = product.productName
            viewHolder.nameTextView.text = name

            // Set the store name
            val storeName = product.storeName
            viewHolder.storeTextView.text = storeName

            // Set the plus button click listener
            viewHolder.plusButton.setOnClickListener {
                val priceValue = price.toDoubleOrNull()
                if (priceValue != null) {
                    totalCost += priceValue
                    selectedProducts.add(product)
                    updateTotalCostInDatabase()
                }
            }

            // Set the minus button click listener
            viewHolder.minusButton.setOnClickListener {
                val priceValue = price.toDoubleOrNull()
                if (priceValue != null && totalCost > 0) {
                    totalCost -= priceValue
                    selectedProducts.remove(product)
                    updateTotalCostInDatabase()
                }
            }

            return view
        }
    }

    internal class ViewHolder {
        lateinit var imageView: ImageView
        lateinit var priceTextView: TextView
        lateinit var nameTextView: TextView
        lateinit var storeTextView: TextView
        lateinit var plusButton: Button
        lateinit var minusButton: Button
    }

    private fun updateTotalCostInDatabase() {
        // Update the total cost in the Firebase Realtime Database
        databaseReference.child("totalCost").setValue(totalCost)
    }

    private fun retrieveTotalCost() {
        // Retrieve the total cost from the Firebase Realtime Database
        val totalCostRef = databaseReference.child("totalCost")

        totalCostRef.get()
            .addOnSuccessListener { dataSnapshot ->
                // Check if the total cost value exists
                if (dataSnapshot.exists()) {
                    // Retrieve the total cost value
                    val value = dataSnapshot.getValue(Double::class.java)
                    totalCost = value ?: 0.0
                }
            }
            .addOnFailureListener { exception ->
                // Handle any errors that occur while retrieving the total cost
                // Display an error message or handle the failure
            }
    }
}
