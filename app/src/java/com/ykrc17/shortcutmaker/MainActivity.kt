package com.ykrc17.shortcutmaker

import android.Manifest
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.ykrc17.shortcutmaker.model.ShortcutInfoModel
import com.ykrc17.shortcutmaker.pm.PermissionManager
import com.ykrc17.shortcutmaker.pm.ShortcutInstaller

class MainActivity : AppCompatActivity() {
    private lateinit var permissionManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionManager = PermissionManager(this)

        findViewById<Button>(R.id.btn_create).setOnClickListener {
            permissionManager.requestPermissions(Manifest.permission.READ_EXTERNAL_STORAGE) {
                ShortcutInstaller.install(this, ShortcutInfoModel("永远的7日之都", Environment.getExternalStorageDirectory().path + "/icon.png", "com.netease.qrzd"))
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
