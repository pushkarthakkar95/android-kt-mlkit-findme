package com.mlkitexample.whatami.question

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mlkitexample.whatami.R
import kotlinx.android.synthetic.main.item_item.view.*

class ElementListAdapter(var context: Context, var listOfElements: MutableList<Element>):
    RecyclerView.Adapter<ElementListAdapter.ElementViewHolder>() {

    class ElementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val elementName: TextView = itemView.itemTextView
        val elementImage: ImageView = itemView.itemImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_item,parent,false)
        return ElementViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfElements.size
    }

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        val element = listOfElements.get(position)
        holder.elementName.text = element.name
        holder.elementImage.setImageResource(element.imageResource)
    }
}