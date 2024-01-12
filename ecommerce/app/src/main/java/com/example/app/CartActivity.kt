package com.example.app

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CartActivity : AppCompatActivity() {

    private lateinit var clearButton: Button
    private lateinit var totalCostTextView: TextView
    private lateinit var listView: ListView
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        totalCostTextView = findViewById(R.id.totalCostTextView)
        clearButton = findViewById(R.id.clearButton)
        listView = findViewById(R.id.listView)

        // Retrieve the total cost from the BuyerActivity
        val totalCost = intent.getDoubleExtra("totalCost", 0.0)

        // Display the total cost in the TextView
        totalCostTextView.text = totalCost.toString()

        // Initialize the cart adapter
        cartAdapter = CartAdapter(this)

        // Set the adapter for the ListView
        listView.adapter = cartAdapter

        // Set click listener for the Clear button
        clearButton.setOnClickListener {
            // Clear the total cost
            totalCostTextView.text = "0.0"

            // Clear the cart items
            cartAdapter.clearCart()
        }
    }
}