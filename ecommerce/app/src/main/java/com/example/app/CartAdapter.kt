package com.example.app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CartAdapter(private val context: Context) : BaseAdapter() {

    private val cartItems: MutableList<CartItemData> = mutableListOf()

    override fun getCount(): Int {
        return cartItems.size
    }

    override fun getItem(position: Int): Any {
        return cartItems[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false)
            viewHolder = ViewHolder()
            viewHolder.productNameTextView = view.findViewById(R.id.productNameTextView)
            viewHolder.productPriceTextView = view.findViewById(R.id.productPriceTextView)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = convertView.tag as ViewHolder
        }

        val cartItem = cartItems[position]

        // Set the product name and price
        viewHolder.productNameTextView.text = cartItem.productName
        viewHolder.productPriceTextView.text = cartItem.productPrice.toString()

        return view
    }

    fun addCartItem(cartItem: CartItemData) {
        cartItems.add(cartItem)
        notifyDataSetChanged()
    }

    fun clearCart() {
        cartItems.clear()
        notifyDataSetChanged()
    }

    private class ViewHolder {
        lateinit var productNameTextView: TextView
        lateinit var productPriceTextView: TextView
    }
}

data class CartItemData(val productName: String, val productPrice: Double)