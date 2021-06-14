package com.example.simplibuy.adapter


import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simplibuy.R
import com.example.simplibuy.classes.Item
import com.example.simplibuy.classes.SuperMArket
import com.example.simplibuy.database.ShoppingViewModel
import kotlinx.android.synthetic.main.menu_list.view.*
import kotlinx.android.synthetic.main.super_market.view.*


class MenuAdapter(
    var context:Fragment,
    var items: ArrayList<String>,

    //private val viewModel: ShoppingViewModel
): RecyclerView.Adapter<MenuAdapter.ShoppingViewHolder>() {

    //private var onclickListener:OnclickListener? = null
    private var onItemClickListener: ((String) -> Unit)? = null

    inner class ShoppingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    /*fun setOnClickListener(onclickListener: OnclickListener)
    {
        this.onclickListener = onclickListener
    }*/
    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_list, parent, false)
        return ShoppingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val curShoppingItem = items[position]
        /*Glide
            .with(context)
            .load(curShoppingItem.Image)
            .into(holder.itemView.imgSuperMarket)*/
        holder.itemView.item1.text = curShoppingItem
        holder.itemView.add.setOnClickListener {
            onItemClickListener?.let { it(curShoppingItem) }
        }
        /*holder.itemView.setOnClickListener {
            if (onclickListener!=null){
                onclickListener!!.onClick(position,curShoppingItem)
            }
        }*/
        holder.itemView.setOnClickListener {
       //     onItemClickListener?.let { it(curShoppingItem) }
        }


    }

    override fun getItemCount(): Int {
        return items.size
    }
    interface OnclickListener{
        fun onClick(position: Int,model: String)
    }
}
