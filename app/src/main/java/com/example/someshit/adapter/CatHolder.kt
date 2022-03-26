package com.example.someshit.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.someshit.R

class CatHolder(view: View) : ViewHolder(view) {
    val imageView = view.findViewById<ImageView>(R.id.image_item)
    val textView = view.findViewById<TextView>(R.id.text_view)
}