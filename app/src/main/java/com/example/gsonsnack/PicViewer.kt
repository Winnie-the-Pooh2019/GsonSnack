package com.example.gsonsnack

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class PicViewer : AppCompatActivity() {
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pic_viewer)
        setSupportActionBar(findViewById(R.id.details_toolbar))

        isFavorite = intent.getBooleanExtra(getString(R.string.bool_key), false)

        findViewById<ImageView>(R.id.image_details).also {
            val link = intent.getStringExtra(getString(R.string.link_transfer_code))

            Glide.with(this)
                .load(link)
                .fallback(R.mipmap.herewego)
                .into(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val drawableId: Int
        val toastText: Int

        if (isFavorite) {
            drawableId = R.drawable.baseline_favorite_border_24
            toastText = R.string.toast_removed_from_fav
        } else {
            drawableId = R.drawable.baseline_favorite_24
            toastText = R.string.toast_added_to_fav
        }
        item.setIcon(drawableId)
        Toast.makeText(this, toastText, Toast.LENGTH_LONG).show()

        isFavorite = !isFavorite

        return true
    }
}