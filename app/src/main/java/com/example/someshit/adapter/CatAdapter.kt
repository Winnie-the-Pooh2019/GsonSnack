package com.example.someshit.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
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

class CatAdapter(private val list: List<Photo>, private val clipboardManager: ClipboardManager)
    : RecyclerView.Adapter<CatHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatHolder = CatHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false))

    override fun onBindViewHolder(holder: CatHolder, position: Int) {
        val photo = list[position]

        CoroutineScope(Dispatchers.Main).launch {
            val link = photo.generateDownloadLink(
                holder.imageView.context.getString(R.string.download_link))

            holder.imageView.setImageBitmap(downloadImage(link))
            holder.imageView.layoutParams = LinearLayout.LayoutParams(
                holder.imageView.width, holder.imageView.width)
            holder.imageView.setOnClickListener {
                link.copyToClipboard()
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

    private fun String.copyToClipboard() {
        clipboardManager.setPrimaryClip(
            ClipData.newPlainText("text", this)
        )

        Timber.i(this)
    }
}