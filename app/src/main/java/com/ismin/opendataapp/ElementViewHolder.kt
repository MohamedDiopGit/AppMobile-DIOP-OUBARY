package com.ismin.opendataapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ElementViewHolder(rootView: View, private val onItemClick: (Int) -> Unit) : RecyclerView.ViewHolder(rootView){

    var txvName: TextView
    var imvImage: ImageView

    init {
        this.txvName = rootView.findViewById(R.id.titleClick)
        this.imvImage = rootView.findViewById(R.id.imageClick)
        //listener qui détecte l'appuie sur cet item
        rootView.setOnClickListener{
            onItemClick(adapterPosition)

        }
    }
    fun updateWithUrl(url: String) {
        Picasso.get()
            .load(url)
            .resize(60, 90)
            .into(imvImage)
    }
}
