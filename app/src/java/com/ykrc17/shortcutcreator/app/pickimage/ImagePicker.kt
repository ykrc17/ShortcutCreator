package com.ykrc17.shortcutcreator.app.pickimage

import android.content.Intent
import android.net.Uri
import com.ykrc17.shortcutcreator.app.BaseActivity

object ImagePicker {
    fun pick(activity: BaseActivity, success: (Uri) -> Unit) {
        Intent().apply {
            action = Intent.ACTION_PICK
            type = "image/*"
            activity.startActivityForResult(this) {
                it.data?.also(success)
            }
        }
    }
}