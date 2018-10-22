package com.ykrc17.shortcutmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v4.content.pm.ShortcutInfoCompat
import android.support.v4.content.pm.ShortcutManagerCompat
import android.support.v4.graphics.drawable.IconCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_create).apply {
            setOnClickListener {
                requestPinShortcut()
            }
        }
    }

    private fun requestPinShortcut() {
        val shortcutInfoCompat = ShortcutInfoCompat.Builder(this, packageName)
                .setShortLabel("哈哈哈")
                .setIcon(IconCompat.createWithResource(this, R.mipmap.ic_launcher))
                .setIntent(createAppDetailsIntent())
                .build()
        ShortcutManagerCompat.requestPinShortcut(this, shortcutInfoCompat, null)
    }

    private fun createAppDetailsIntent() = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.parse("package:$packageName")
    }
}
