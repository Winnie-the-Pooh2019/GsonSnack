package com.example.gsonsnack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gsonsnack.adapter.CatAdapter
import com.example.gsonsnack.domain.objects.Photo
import com.example.gsonsnack.domain.objects.Wrapper
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
            val photo = downloadJsonAsync(getString(R.string.link)).photos.photo

            findViewById<RecyclerView>(R.id.recycler_view).apply {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)

                adapter = CatAdapter(
                    photo
                )
            }
        }
    }

    private suspend fun parseJson(): List<Photo> =
        downloadJsonAsync(getString(R.string.link)).photos.photo

    private suspend fun downloadJsonAsync(link: String): Wrapper = withContext(Dispatchers.Default) {
        val connection = withContext(Dispatchers.IO) {
            URL(link).openConnection()
        } as HttpURLConnection

        val wrapper = Gson().fromJson(
            connection.inputStream.bufferedReader().readText(),
            Wrapper::class.java
        ).apply {
            photos.photo.forEachIndexed { index, photo ->
                if (index % 5 == 0) {
//                    println(photo.toString())
                    Timber.d(photo.toString())
                }
            }
        }

        wrapper
    }
}