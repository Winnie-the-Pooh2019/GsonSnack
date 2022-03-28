package com.example.gsonsnack

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class PicViewer : AppCompatActivity() {
    private var link: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pic_viewer)
        setSupportActionBar(findViewById(R.id.details_toolbar))

        findViewById<ImageView>(R.id.image_details).also {
            link = intent.getStringExtra(getString(R.string.link_transfer_code))

            Glide.with(this)
                .load(link)
                .fallback(R.mipmap.herewego)
                .error(R.mipmap.herewego)
                .into(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val drawableId: Int = R.drawable.baseline_favorite_24
        R.string.toast_added_to_fav
        item.setIcon(drawableId)

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(getString(R.string.link_transfer_code), link)
        setResult(RESULT_OK, intent)
        finish()

        return true
    }
}