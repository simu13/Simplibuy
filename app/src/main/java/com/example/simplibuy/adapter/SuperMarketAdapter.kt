package com.example.simplibuy.adapter


import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simplibuy.R
import com.example.simplibuy.classes.SuperMArket
import com.example.simplibuy.database.ShoppingViewModel
import kotlinx.android.synthetic.main.super_market.view.*


class SuperMarketAdapter(
    var context:Fragment,
    var items: List<SuperMArket>,

    //private val viewModel: ShoppingViewModel
): RecyclerView.Adapter<SuperMarketAdapter.ShoppingViewHolder>() {

    private var onclickListener:OnclickListener? = null

    inner class ShoppingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    fun setOnClickListener(onclickListener: OnclickListener)
    {
        this.onclickListener = onclickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.super_market, parent, false)
        return ShoppingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val curShoppingItem = items[position]
        Glide
            .with(context)
            .load(curShoppingItem.Image)
            .into(holder.itemView.imgSuperMarket)
        holder.itemView.tvSuperMarket.text = curShoppingItem.Name


    }

    override fun getItemCount(): Int {
        return items.size
    }
    interface OnclickListener{
        fun onClick(position: Int,model: SuperMArket)
    }
}
