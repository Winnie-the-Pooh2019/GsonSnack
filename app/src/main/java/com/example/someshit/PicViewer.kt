package com.example.someshit

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide

class PicViewer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pic_viewer)
        setSupportActionBar(findViewById(R.id.details_toolbar))

        findViewById<ImageView>(R.id.image_details).also {
            val link = intent.getStringExtra(getString(R.string.link_transfer_code))

            Glide.with(this)
                .load(link)
                .placeholder(R.mipmap.herewego)
                .into(it)

//            it.setOnClickListener {
//                Toast
//                    .makeText(this, getText(R.string.toast_text), Toast.LENGTH_SHORT)
//                    .show()
//            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this, getString(R.string.toast_text), Toast.LENGTH_LONG).show()

        return true
    }
}