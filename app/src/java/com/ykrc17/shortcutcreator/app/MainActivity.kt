package com.ykrc17.shortcutcreator.app

import android.app.Activity
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import com.ykrc17.shortcutcreator.BuildConfig
import com.ykrc17.shortcutcreator.R
import com.ykrc17.shortcutcreator.app.layout.MainBinding
import com.ykrc17.shortcutcreator.app.pickapp.ui.PickAppView
import com.ykrc17.shortcutcreator.app.pickimage.ImagePicker
import com.ykrc17.shortcutcreator.app.shortcut.ShortcutInstaller
import com.ykrc17.shortcutcreator.app.shortcut.ShortcutInstallerCallback
import com.ykrc17.shortcutcreator.pm.PermissionManager

class MainActivity : BaseActivity(), ShortcutInstallerCallback {
    private lateinit var permissionManager: PermissionManager

    private lateinit var views: MainBinding
    private lateinit var shortcutInstaller: ShortcutInstaller
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        permissionManager = PermissionManager(this)
        views = MainBinding(window.decorView)
        shortcutInstaller = ShortcutInstaller(this)

        views.apply {
            btn_choose_target.setOnClickListener {
                CommonActivity.jump(this@MainActivity, PickAppView::class.java, PickAppView::class.java.hashCode() and 0xFF)
            }
            btn_choose_icon.setOnClickListener {
                ImagePicker.pick(this@MainActivity) { uri ->
                    shortcutInstaller.setShortcutIcon(this@MainActivity, uri)
                }
            }
            btn_create.setOnClickListener {
                this@MainActivity.createShortcut()
            }
            tv_version.text = "Ver.${BuildConfig.VERSION_NAME}"
        }

        setDefaultShortcutInfo()
    }

    private fun setDefaultShortcutInfo() {
        // 如果安装了官服
        packageManager.getInstalledApplications(0).find { it.packageName == QRZD_PACKAGE_NAME }?.also {
            shortcutInstaller.setTargetAppInfo(it)
        }
        shortcutInstaller.setShortcutIcon(BitmapFactory.decodeResource(resources, R.drawable.llz))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PickAppView::class.java.hashCode() and 0xFF && resultCode == Activity.RESULT_OK) {
            shortcutInstaller.setTargetAppInfo(data?.getParcelableExtra(ApplicationInfo::class.java.canonicalName) as ApplicationInfo)
        }
    }

    override fun showTargetAppInfo(targetAppInfo: ApplicationInfo) {
        views.iv_target_icon.setImageDrawable(targetAppInfo.loadIcon(packageManager))
        val label = targetAppInfo.loadLabel(packageManager)
        views.tv_target_label.text = label
        views.et_shortcut_label.setText(label)
    }

    override fun showShortcutIcon(shortcutIcon: Bitmap) {
        views.iv_shortcut_icon.setImageBitmap(shortcutIcon)
    }

    private fun createShortcut() {
        shortcutInstaller.setShortcutLabel(views.et_shortcut_label.text.toString())
        shortcutInstaller.install(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object CONSTANTS {
        const val QRZD_PACKAGE_NAME = "com.netease.qrzd"
        val TAG = MainActivity::class.java.simpleName
    }
}
