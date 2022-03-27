package com.example.someshit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

class PicViewer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pic_viewer)

        findViewById<ImageView>(R.id.image_details).also {
            val link = intent.getStringExtra(getString(R.string.link_transfer_code))

            Glide.with(this)
                .load(link)
                .placeholder(R.mipmap.herewego)
                .into(it)

            it.setOnClickListener {
                Toast
                    .makeText(this, getText(R.string.toast_text), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}