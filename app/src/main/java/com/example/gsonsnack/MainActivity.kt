package com.example.gsonsnack

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gsonsnack.adapter.CatAdapter
import com.example.gsonsnack.domain.objects.Photo
import com.example.gsonsnack.domain.objects.Wrapper
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    val picViewContract = registerForActivityResult(PicActivityContract(this), this::showSnack)

    private var photos = ArrayList<Photo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree())

        CoroutineScope(Dispatchers.Main).launch {
            photos = ArrayList(downloadJsonAsync(getString(R.string.link)).photos.photo)

            val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

            recyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)
            recyclerView.setHasFixedSize(true)

            Timber.e("PHOTOES RCHO $photos")
            recyclerView.adapter = CatAdapter(
                this@MainActivity,
                photos
            )

            Timber.e("final of coroutine")
        }
    }

    private fun showSnack(string: String?) {
        if (string != null) {
            Timber.e(string)

            val photo = Gson().fromJson(string, Photo::class.java)
            Timber.e(photo.toString())
            val index = photos.indexOf(photo)

            Timber.e(if (index != -1) photos[index].toString() else index.toString())

            if (index != -1)
                photos[index].isFavourite = photo.isFavourite

            Snackbar.make(
                findViewById(R.id.main_layout),
                if (photo.isFavourite)
                    getString(R.string.toast_added_to_fav)
                else
                    getString(R.string.toast_removed_from_fav),
                Snackbar.LENGTH_LONG
            ).apply {
                setAction("Открыть") {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(
                        photo.generateDownloadLink(getString(R.string.download_link))
                    ))
                    startActivity(browserIntent)
                }

                show()
            }
        }
    }

    private suspend fun downloadJsonAsync(link: String): Wrapper = withContext(Dispatchers.IO) {
        val connection = withContext(Dispatchers.IO) {
            URL(link).openConnection()
        } as HttpURLConnection

        val wrapper = Gson().fromJson(
            connection.inputStream.bufferedReader().readText(),
            Wrapper::class.java
        ).apply {
            photos.photo.forEachIndexed { index, photo ->
                if (index % 5 == 0)
                    Timber.d(photo.toString())
            }
        }

        wrapper
    }
}