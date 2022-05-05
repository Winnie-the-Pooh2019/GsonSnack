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
import timber.log.Timber


class CatAdapter(private val main: MainActivity, private val list: List<Photo>) :
    RecyclerView.Adapter<CatHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatHolder {
        Timber.e("ONCREATE VIEW HOLDER")
        return CatHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: CatHolder, position: Int) {
        Timber.e("onBindViewHolder")
        val photo = list[position]

        CoroutineScope(Dispatchers.Main).launch {
            val link = photo.generateDownloadLink(
                holder.imageView.context.getString(R.string.download_link)
            )

            Glide.with(main)
                .asBitmap()
                .load(link)
                .into(holder.imageView)
            holder.imageView.layoutParams = LinearLayout.LayoutParams(
                holder.imageView.width, holder.imageView.width
            )

            holder.imageView.setOnClickListener {
                main.picViewContract.launch(link)
            }
        }
    }

    override fun getItemCount(): Int = list.size
}
