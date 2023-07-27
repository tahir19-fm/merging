package com.taomish.app.android.farmsanta.farmer.utils

import android.content.Context
import android.content.Intent

object Share {

    fun share(
        context: Context,
        message: String
    ) {

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }
         context.startActivity(sendIntent)
    }

}