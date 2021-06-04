package com.example.simplibuy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.simplibuy.R
import com.example.simplibuy.database.ShoppingItem
import com.example.simplibuy.database.ShoppingViewModel2
import kotlinx.android.synthetic.main.shopping_item2.view.*

class ShoppingItemAdapter2(
    var items: List<ShoppingItem>,
    private val viewModel: ShoppingViewModel2
): RecyclerView.Adapter<ShoppingItemAdapter2.ShoppingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_item2, parent, false)
        return ShoppingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val curShoppingItem = items[position]

        holder.itemView.tvName2.text = curShoppingItem.name
        holder.itemView.tvAmount.text = "${curShoppingItem.amount}"

        holder.itemView.ivDelete2.setOnClickListener {
            viewModel.delete(curShoppingItem)
        }

      holder.itemView.ivPlus2.setOnClickListener {
            curShoppingItem.amount++
            viewModel.upsert(curShoppingItem)
        }

        holder.itemView.ivMinus2.setOnClickListener {
            if(curShoppingItem.amount > 0) {
                curShoppingItem.amount--
                viewModel.upsert(curShoppingItem)
            }
        }
    }

    inner class ShoppingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}