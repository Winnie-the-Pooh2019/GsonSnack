package com.example.gsonsnack.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gsonsnack.MainActivity
import com.example.gsonsnack.R
import com.example.gsonsnack.domain.objects.Photo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CatAdapter(private val main: MainActivity, private val list: List<Photo>) :
    RecyclerView.Adapter<CatHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatHolder {
        return CatHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false))
    }

    // if u remove coroutine it wont work idk why -_-
    override fun onBindViewHolder(holder: CatHolder, position: Int) {
        val photo = list[position]

        CoroutineScope(Dispatchers.Main).launch {
            val link = photo.generateDownloadLink(
                holder.imageView.context.getString(R.string.download_link)
            )

            Glide.with(main)
                .asBitmap()
                .load(link)
                .fallback(R.mipmap.herewego)
                .error(R.mipmap.herewego)
                .into(holder.imageView)
            holder.imageView.layoutParams = LinearLayout.LayoutParams(
                holder.imageView.width, holder.imageView.width
            )

            holder.imageView.setOnClickListener {
                main.picViewContract.launch(Photo.Package(link, photo.id, photo.isFavourite))
            }
        }
    }

    override fun getItemCount(): Int = list.size
}
