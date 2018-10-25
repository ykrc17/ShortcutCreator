package com.ykrc17.shortcutmaker

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.ykrc17.shortcutmaker.model.ShortcutInfoModel
import com.ykrc17.shortcutmaker.pm.ShortcutCreator

class MainActivity : AppCompatActivity() {
    private val callbacks = hashMapOf<Int, () -> Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_create).setOnClickListener {
            requestPermissions(Manifest.permission.READ_EXTERNAL_STORAGE) {
                requestShortcutPermission {
                    ShortcutCreator.create(this, ShortcutInfoModel("永远的7日之都", Environment.getExternalStorageDirectory().path + "/icon.png", "com.netease.qrzd"))
                }
            }
        }
    }

    private fun requestPermissions(permission: String, callback: () -> Unit) {
        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
            val permissions = arrayOf(permission)
            val requestCode = permissions.hashCode()
            callbacks[requestCode] = callback
            requestPermissions(permissions, requestCode)
        } else {
            callback()
        }
    }

    private fun requestShortcutPermission(callback: () -> Unit) {
        if (Build.VERSION.SDK_INT >= 26) {
            AlertDialog.Builder(this).apply {
                setMessage("创建快捷方式可能需要权限")
                setNegativeButton("取消") { _: DialogInterface, _: Int -> }
                setNeutralButton("前往权限页面") { _: DialogInterface, _: Int -> startActivity(createAppDetailsIntent()) }
                setPositiveButton("直接创建") { _: DialogInterface, _: Int -> callback() }
                show()
            }
        } else {
            callback()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        callbacks[requestCode]?.also { callback ->
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                callback()
            }
        }
        callbacks.remove(requestCode)
    }

    private fun createAppDetailsIntent() = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.parse("package:$packageName")
    }
}
