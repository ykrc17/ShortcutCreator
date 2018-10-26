package com.ykrc17.shortcutcreator.app

import android.Manifest
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ykrc17.shortcutcreator.R
import com.ykrc17.shortcutcreator.app.layout.MainBinding
import com.ykrc17.shortcutcreator.app.model.ShortcutInfoModel
import com.ykrc17.shortcutcreator.app.select.model.AppInfoModel
import com.ykrc17.shortcutcreator.app.select.ui.SelectAppPresenter
import com.ykrc17.shortcutcreator.pm.PermissionManager
import com.ykrc17.shortcutcreator.pm.ShortcutInstaller

class MainActivity : AppCompatActivity() {
    private lateinit var permissionManager: PermissionManager
    private lateinit var views: MainBinding
    private var targetAppInfo: AppInfoModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        supportActionBar?.title = "快捷方式生成器"
        permissionManager = PermissionManager(this)
        views = MainBinding(window.decorView)

        views.apply {
            btn_choose_target.setOnClickListener {
                CommonActivity.jump(this@MainActivity, SelectAppPresenter::class.java)
            }
            btn_create.setOnClickListener(this@MainActivity::createShortcut)
        }

        // 如果安装了官服
        packageManager.getInstalledApplications(0).find { it.packageName == QRZD_PACKAGE_NAME }?.also {
            targetAppInfo = AppInfoModel(it.packageName, it.loadLabel(packageManager), it.loadIcon(packageManager))
            showTargetAppInfo()
        }
    }

    private fun showTargetAppInfo() {
        targetAppInfo?.also {
            views.iv_target_icon.setImageDrawable(it.icon)
            views.tv_target_label.text = it.label
            views.et_shortcut_label.setText(it.label)
        }
    }

    private fun createShortcut(view: View) {
        permissionManager.requestPermissions(Manifest.permission.READ_EXTERNAL_STORAGE) {
            targetAppInfo?.also {
                val shortcutLabel = views.et_shortcut_label.text.toString()
                val shortcutIconPath = Environment.getExternalStorageDirectory().path + "/icon.png"
                val shortcutPackageName = it.packageName
                ShortcutInstaller.install(this@MainActivity, ShortcutInfoModel(shortcutLabel, shortcutIconPath, shortcutPackageName))
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object CONSTANTS {
        const val QRZD_PACKAGE_NAME = "com.netease.qrzd"
    }
}
