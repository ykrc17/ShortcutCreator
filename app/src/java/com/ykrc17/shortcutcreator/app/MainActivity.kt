package com.ykrc17.shortcutcreator.app

import android.Manifest
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import com.ykrc17.shortcutcreator.R
import com.ykrc17.shortcutcreator.app.layout.ActivityMainBinding
import com.ykrc17.shortcutcreator.app.model.ShortcutInfoModel
import com.ykrc17.shortcutcreator.pm.PermissionManager
import com.ykrc17.shortcutcreator.pm.ShortcutInstaller
import com.ykrc17.shortcutcreator.app.select.ui.SelectAppPresenter

class MainActivity : AppCompatActivity() {
    private lateinit var permissionManager: PermissionManager
    private lateinit var views: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        permissionManager = PermissionManager(this)
        views = ActivityMainBinding(window.decorView)

        views.apply {
            btn_choose_target.setOnClickListener {
                CommonActivity.jump(this@MainActivity, SelectAppPresenter::class.java)
            }
            btn_create.setOnClickListener {
                permissionManager.requestPermissions(Manifest.permission.READ_EXTERNAL_STORAGE) {
                    ShortcutInstaller.install(this@MainActivity, ShortcutInfoModel("永远的7日之都", Environment.getExternalStorageDirectory().path + "/icon.png", "com.netease.qrzd"))
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
