package com.example.simplibuy.onlineCart


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.simplibuy.R
import com.example.simplibuy.database.MenuCart
import com.example.simplibuy.database.ShoppingViewModel2
import kotlinx.android.synthetic.main.cart_list.view.*
import kotlinx.android.synthetic.main.menu_list.view.*
import kotlinx.android.synthetic.main.menu_list.view.item1


class CartAdapter(
    var context:Fragment,
    var viewModel:ShoppingViewModel2,
    var items: List<MenuCart>,
): RecyclerView.Adapter<CartAdapter.ShoppingViewHolder>() {

    inner class ShoppingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_list, parent, false)
        return ShoppingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val curShoppingItem = items[position]
        holder.itemView.item1.text = curShoppingItem.name
        holder.itemView.amount.text = "${curShoppingItem.quantity}"
        holder.itemView.IvMinus.setOnClickListener {
            if (holder.itemView.amount.text == "1") {
                viewModel.deleteFodd(curShoppingItem)
            } else {
                curShoppingItem.quantity--
                viewModel.upsertFood(curShoppingItem)
            }
        }

        holder.itemView.IvAdd.setOnClickListener {
            curShoppingItem.quantity++
            viewModel.upsertFood(curShoppingItem)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
