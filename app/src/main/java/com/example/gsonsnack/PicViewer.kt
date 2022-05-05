package com.example.gsonsnack

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.gsonsnack.domain.objects.Photo
import com.google.gson.Gson
import timber.log.Timber

class PicViewer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pic_viewer)
        setSupportActionBar(findViewById(R.id.details_toolbar))

        findViewById<ImageView>(R.id.image_details).also {
            Glide.with(this)
                .load(intent.getStringExtra(getString(R.string.link_transfer_code)))
                .fallback(R.mipmap.herewego)
                .error(R.mipmap.herewego)
                .into(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        menu.findItem(R.id.heart).setIcon(
            if (photo != null && photo!!.isFavourite)
                R.drawable.baseline_favorite_24
            else
                R.drawable.baseline_favorite_border_24
        )

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (photo != null) {
            if (photo!!.isFavourite)
                item.setIcon(R.drawable.baseline_favorite_border_24)
            else
                item.setIcon(R.drawable.baseline_favorite_24)

            photo!!.isFavourite = !photo!!.isFavourite
        }

        Timber.e(photo.toString())

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(getString(R.string.link_transfer_code), Gson().toJson(photo))
        setResult(RESULT_OK, intent)
        finish()

        return true
    }
}