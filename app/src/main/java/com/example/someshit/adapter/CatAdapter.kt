package com.example.someshit.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.someshit.R
import com.example.someshit.domain.objects.Photo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.HttpURLConnection
import java.net.URL

class CatAdapter(private val list: List<Photo>) : RecyclerView.Adapter<CatHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatHolder = CatHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false))

    override fun onBindViewHolder(holder: CatHolder, position: Int) {
        val photo = list[position]

        CoroutineScope(Dispatchers.Main).launch {
            holder.imageView.setImageBitmap(
                downloadImage(photo.generateDownloadLink(
                    holder.imageView.context.getString(R.string.download_link)
                )))

            holder.textView.text = photo.generateDownloadLink(
                holder.imageView.context.getString(R.string.download_link))
        }
    }

    override fun getItemCount(): Int = list.size

    private suspend fun downloadImage(downloadLink: String): Bitmap {
        val bitmap = withContext(Dispatchers.Default) {
            val connection = withContext(Dispatchers.IO) {
                URL(downloadLink).openConnection()
            } as HttpURLConnection

            BitmapFactory.decodeStream(connection.inputStream)
        }

        Timber.tag("IMAGE DOWNLOADED").i("IMAGE DOWNLOADING COMPLETED")

        return bitmap
    }
}