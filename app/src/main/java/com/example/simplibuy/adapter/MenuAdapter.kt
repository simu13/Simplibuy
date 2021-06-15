package com.example.simplibuy.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.simplibuy.R
import com.example.simplibuy.database.ShoppingViewModel2
import kotlinx.android.synthetic.main.menu_list.view.*


class MenuAdapter(
    var context:Fragment,
var viewModel:ShoppingViewModel2,
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
        holder.itemView.selected.setOnClickListener {
            holder.itemView.selected.visibility = View.GONE
            holder.itemView.minus.visibility = View.VISIBLE
            holder.itemView.tvAmount.visibility = View.VISIBLE
            holder.itemView.add.visibility = View.VISIBLE
            onItemClickListener?.let { it(curShoppingItem) }
        }
        holder.itemView.minus.setOnClickListener {
if (holder.itemView.tvAmount.text=="1" )
{
    holder.itemView.selected.visibility = View.VISIBLE
    holder.itemView.minus.visibility = View.GONE
    holder.itemView.tvAmount.visibility = View.GONE
    holder.itemView.add.visibility = View.GONE
}
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
}
