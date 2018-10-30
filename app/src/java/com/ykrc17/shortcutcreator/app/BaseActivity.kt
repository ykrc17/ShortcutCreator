package com.ykrc17.shortcutcreator.app

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    private val activityCallbacks = hashMapOf<Int, (Intent) -> Unit>()

    fun startActivityForResult(intent: Intent, callback: (Intent) -> Unit) {
        val requestCode = intent.hashCode() and 0xFF
        activityCallbacks[requestCode] = callback
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activityCallbacks[requestCode]?.apply {
            if (resultCode == Activity.RESULT_OK && data != null) {
                invoke(data)
            }
        }
    }
}
