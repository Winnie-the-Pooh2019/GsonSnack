package com.example.someshit

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.someshit.domain.objects.Photo
import com.example.someshit.domain.objects.Wrapper
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())

        CoroutineScope(Dispatchers.Main).launch {
            val builder = StringBuilder()
            val photos = parseJson().onEach(builder::append)

            val photosText = builder.toString()
            findViewById<TextView>(R.id.text_view).text = photosText

            val source = getString(R.string.download_link)
            photos.forEach {
                println(it.generateDownloadLink(source))
            }
        }
    }

    private suspend fun downloadImage(downloadLink: String) {
        val bitmap = withContext(Dispatchers.Default) {
            val connection = withContext(Dispatchers.IO) {
                URL(downloadLink).openConnection()
            } as HttpURLConnection

            BitmapFactory.decodeStream(connection.inputStream)
        }

//        findViewById<ImageView>(id).setImageBitmap(bitmap)
        Timber.tag("IMAGE DOWNLOADED").i("IMAGE DOWNLOADING COMPLETED")
    }

    private suspend fun parseJson(): List<Photo> =
        downloadJsonAsync(getString(R.string.link)).photos.photo

    private suspend fun downloadJsonAsync(link: String): Wrapper = withContext(Dispatchers.Default) {
            val connection = withContext(Dispatchers.IO) {
                URL(link).openConnection()
            } as HttpURLConnection

            println("CONNECTION ESTABLISHED")

            Log.i("CONNECTION INFO", "CONNECTION ESTABLISHED: ${connection.url}")

            val gson = Gson()
//        val listPhotosType = object : TypeToken<List<Photo>>() {}.type

            val wrapper = gson.fromJson(
                connection.inputStream.bufferedReader().readText(),
                Wrapper::class.java
            )

            wrapper
        }
}