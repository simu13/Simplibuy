package com.example.simplibuy.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.simplibuy.R
import com.example.simplibuy.database.MenuCart
import com.example.simplibuy.database.ShoppingViewModel2
import kotlinx.android.synthetic.main.menu_list.view.*
import kotlinx.android.synthetic.main.menu_list.view.add
import kotlinx.android.synthetic.main.menu_list.view.edit
import kotlinx.android.synthetic.main.menu_list.view.item1
import kotlinx.android.synthetic.main.menu_list.view.item2
import kotlinx.android.synthetic.main.menu_list.view.minus
import kotlinx.android.synthetic.main.menu_list.view.tvAmount
import kotlinx.android.synthetic.main.seller_menu_list.view.*


class SellerMenuAdapter(
    var context:Fragment,
    var items: ArrayList<MenuCart>

    //private val viewModel: ShoppingViewModel
): RecyclerView.Adapter<SellerMenuAdapter.ShoppingViewHolder>() {

    //private var onclickListener:OnclickListener? = null
    private var onItemClickListener: ((MenuCart) -> Unit)? = null

    inner class ShoppingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    /*fun setOnClickListener(onclickListener: OnclickListener)
    {
        this.onclickListener = onclickListener
    }*/
    fun setOnItemClickListener(listener: (MenuCart) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.seller_menu_list, parent, false)
        return ShoppingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val curShoppingItem = items[position]
        /*Glide
            .with(context)
            .load(curShoppingItem.Image)
            .into(holder.itemView.imgSuperMarket)*/
        holder.itemView.item1.text = curShoppingItem.name
        holder.itemView.item2.text = "${curShoppingItem.amount}"
        holder.itemView.edit.setOnClickListener {

        }
        holder.itemView.delete.setOnClickListener{
            onItemClickListener?.let { it(curShoppingItem) }
        }
        /*holder.itemView.setOnClickListener {
            if (onclickListener!=null){
                onclickListener!!.onClick(position,curShoppingItem)
            }
        }*/
       /* holder.itemView.setOnClickListener {
       //     onItemClickListener?.let { it(curShoppingItem) }
        }*/


    }

    override fun getItemCount(): Int {
        return items.size
    }
    interface OnclickListener{
        fun onClick(position: Int,model: String)
    }
    fun setData(item: ArrayList<MenuCart>) {
        this.items = item
        notifyDataSetChanged()
    }
}
