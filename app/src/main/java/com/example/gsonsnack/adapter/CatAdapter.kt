package com.example.gsonsnack.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.gsonsnack.MainActivity
import com.example.gsonsnack.R
import com.example.gsonsnack.domain.objects.Photo
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class CatAdapter(private val main: MainActivity, private val list: List<Photo>) :
    RecyclerView.Adapter<CatHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatHolder = CatHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
    )

    override fun onBindViewHolder(holder: CatHolder, position: Int) {
        val photo = list[position]

        CoroutineScope(Dispatchers.Main).launch {
            val link = photo.generateDownloadLink(
                holder.imageView.context.getString(R.string.download_link)
            )

            holder.imageView.setImageBitmap(downloadImage(link))
            holder.imageView.layoutParams = LinearLayout.LayoutParams(
                holder.imageView.width, holder.imageView.width
            )

            holder.imageView.setOnClickListener {
                main.picViewContract.launch(Gson().toJson(photo, Photo::class.java))
            }
        }
    }

    override fun getItemCount(): Int = list.size

    private suspend fun downloadImage(downloadLink: String): Bitmap {
        val bitmap = withContext(Dispatchers.IO) {
            var connection: HttpURLConnection? = null
            val result: Bitmap

            try {
                connection = withContext(Dispatchers.IO) {
                    URL(downloadLink).openConnection()
                } as HttpURLConnection
                result = BitmapFactory.decodeStream(connection.inputStream)
            } finally {
                connection!!.inputStream.close()
                connection.disconnect()
            }

            return@withContext result
        }

        return bitmap
    }
}