package com.example.simplibuy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplibuy.R
import com.example.simplibuy.classes.Product
import com.example.simplibuy.database.ShoppingViewModel
import kotlinx.android.synthetic.main.shopping_item.view.*


class ShoppingItemAdapter(
    var items: List<Product>,
    private val viewModel: ShoppingViewModel
): RecyclerView.Adapter<ShoppingItemAdapter.ShoppingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_item, parent, false)
        return ShoppingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val curShoppingItem = items[position]

        holder.itemView.tvName.text = curShoppingItem.Name
        holder.itemView.tvPrice.text = "Rs. ${curShoppingItem.Price}"
        holder.itemView.tvWeight.text = "Kg. ${curShoppingItem.Weight}"
            //holder.itemView.tvWeight.text = get


        holder.itemView.ivDelete.setOnClickListener {
            viewModel.delete(curShoppingItem)
        }

       /*holder.itemView.ivPlus.setOnClickListener {
            curShoppingItem.amount++
            viewModel.upsert(curShoppingItem)
        }

        holder.itemView.ivMinus.setOnClickListener {
            if(curShoppingItem.amount > 0) {
                curShoppingItem.amount--
                viewModel.upsert(curShoppingItem)
            }
        }*/
    }

    inner class ShoppingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}