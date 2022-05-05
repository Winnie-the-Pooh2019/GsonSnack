package com.example.gsonsnack

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.gsonsnack.domain.objects.Photo
import timber.log.Timber

class PicViewer : AppCompatActivity() {
    private val pack: Photo.Package by lazy {
        try {
            intent.extras!!.getSerializable(getString(R.string.link_transfer_code)) as Photo.Package
        } catch (npe: NullPointerException) {
            Timber.e(npe)
            Photo.Package()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pic_viewer)
        setSupportActionBar(findViewById(R.id.details_toolbar))

        findViewById<ImageView>(R.id.image_details).also {
            Glide.with(this)
                .load(pack.link)
                .fallback(R.mipmap.herewego)
                .error(R.mipmap.herewego)
                .into(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        menu.findItem(R.id.heart).setIcon(
            if (pack.isFavourite)
                R.drawable.baseline_favorite_24
            else
                R.drawable.baseline_favorite_border_24
        )

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (!pack.isFavourite)
            item.setIcon(R.drawable.baseline_favorite_border_24)
        else
            item.setIcon(R.drawable.baseline_favorite_24)

        pack.isFavourite = !pack.isFavourite

        Timber.e(pack.toString())

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtras(Bundle().apply { putSerializable(getString(R.string.link_transfer_code), pack) })
        setResult(RESULT_OK, intent)
        finish()

        return true
    }
}