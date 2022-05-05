package com.example.gsonsnack

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import com.example.gsonsnack.domain.objects.Photo
import timber.log.Timber

class PicActivityContract(private val context: Context) : ActivityResultContract<Photo.Package, Photo.Package?>() {
    override fun createIntent(context: Context, input: Photo.Package?): Intent {
        return Intent(context, PicViewer::class.java)
            .putExtras(Bundle().apply { putSerializable(context.getString(R.string.link_transfer_code), input) })
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Photo.Package? = when (resultCode) {
        Activity.RESULT_OK -> {
            try {
                intent!!.extras!!.getSerializable(context.getString(R.string.link_transfer_code)) as Photo.Package
            } catch (npe: NullPointerException) {
                Timber.e(npe)
                null
            }
        }
        else -> null
    }

    override fun getSynchronousResult(context: Context, input: Photo.Package?): SynchronousResult<Photo.Package?>? {
        return super.getSynchronousResult(context, input)
    }
}