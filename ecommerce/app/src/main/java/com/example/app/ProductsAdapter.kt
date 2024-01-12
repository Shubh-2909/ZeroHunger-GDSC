
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.app.R
import com.squareup.picasso.Picasso

class ProductsAdapter(private val context: Context, private val products: List<Product>) : BaseAdapter() {

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun getCount(): Int {
        return products.size
    }

    override fun getItem(position: Int): Any {
        return products[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item_layout2, null)
            viewHolder = ViewHolder()
            viewHolder.imageView = view.findViewById(R.id.product_image)
            viewHolder.nameTextView = view.findViewById(R.id.product_name)
            viewHolder.priceTextView = view.findViewById(R.id.product_price)

            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = convertView.tag as ViewHolder
        }

        val product = products[position]

        // Set the product image using Picasso library
        if (product.imageUrl.isNotBlank()) {
            Picasso.get()
                .load(product.imageUrl)
                .fit()
                .centerCrop()
                .into(viewHolder.imageView)
        }

        // Set the product name and price
        viewHolder.nameTextView.text = product.productName
        viewHolder.priceTextView.text = product.price.toString()

        // Set click listener for the delete button


        return view
    }

    private class ViewHolder {
        lateinit var imageView: ImageView
        lateinit var nameTextView: TextView
        lateinit var priceTextView: TextView

    }

    interface OnItemClickListener {
        fun onDeleteClick(product: Product)
    }
}
