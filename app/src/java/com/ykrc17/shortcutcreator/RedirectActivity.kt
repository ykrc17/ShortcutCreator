package com.ykrc17.shortcutcreator

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle

class RedirectActivity : Activity() {
    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra(EXTRA_LAUNCH_PACKAGE)?.also {
            startActivity(packageManager.getLaunchIntentForPackage(it))
        }
        finish()
    }

    companion object {
        const val EXTRA_LAUNCH_PACKAGE = "extra.launch_package"
    }
}