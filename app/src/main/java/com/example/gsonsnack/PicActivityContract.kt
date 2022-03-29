package com.example.gsonsnack

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import timber.log.Timber

class PicActivityContract(private val context: Context) : ActivityResultContract<String, String?>() {
    override fun createIntent(context: Context, input: String?): Intent {
        return Intent(context, PicViewer::class.java)
            .putExtra(context.getString(R.string.link_transfer_code), input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? = when (resultCode) {
        Activity.RESULT_OK -> {
            intent?.getStringExtra(
                context.getString(R.string.link_transfer_code)
            ).apply {
                Timber.e(this.toString())
            }
        }
        else -> null
    }

    override fun getSynchronousResult(context: Context, input: String?): SynchronousResult<String?>? =
        if (input.isNullOrEmpty())
            SynchronousResult(context.getString(R.string.screamer))
        else null
}