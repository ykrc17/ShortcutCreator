package com.ykrc17.shortcutcreator.app

import android.app.Activity
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ykrc17.shortcutcreator.R
import com.ykrc17.shortcutcreator.app.layout.MainBinding
import com.ykrc17.shortcutcreator.app.model.ShortcutInfoModel
import com.ykrc17.shortcutcreator.app.select.ui.SelectAppPresenter
import com.ykrc17.shortcutcreator.pm.PermissionManager
import com.ykrc17.shortcutcreator.pm.ShortcutInstaller
import com.ykrc17.shortcutcreator.res.DP

class MainActivity : AppCompatActivity() {
    private lateinit var permissionManager: PermissionManager
    private lateinit var views: MainBinding
    private var targetAppInfo: ApplicationInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        supportActionBar?.title = "快捷方式生成器"
        permissionManager = PermissionManager(this)
        views = MainBinding(window.decorView)

        views.apply {
            iv_shortcut_icon.setImageResource(R.drawable.llz)
            btn_choose_target.setOnClickListener {
                CommonActivity.jump(this@MainActivity, SelectAppPresenter::class.java, SelectAppPresenter::class.java.hashCode() and 0xFF)
            }
            btn_create.setOnClickListener(this@MainActivity::createShortcut)
        }

        // 如果安装了官服
        packageManager.getInstalledApplications(0).find { it.packageName == QRZD_PACKAGE_NAME }?.also {
            targetAppInfo = it
            showTargetAppInfo()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SelectAppPresenter::class.java.hashCode() and 0xFF && resultCode == Activity.RESULT_OK) {
            targetAppInfo = data?.getParcelableExtra(ApplicationInfo::class.java.canonicalName) as ApplicationInfo
            showTargetAppInfo()
        }
    }

    private fun showTargetAppInfo() {
        targetAppInfo?.also {
            views.iv_target_icon.setImageDrawable(it.loadIcon(packageManager))
            val label = it.loadLabel(packageManager)
            views.tv_target_label.text = label
            views.et_shortcut_label.setText(label)
        }
    }

    private fun createShortcut(view: View) {
        targetAppInfo?.also {
            val shortcutLabel = views.et_shortcut_label.text.toString()
            val originBmp = BitmapFactory.decodeResource(resources, R.drawable.llz)
            val size = DP(80).toPxInt()
            val shortcutIcon = Bitmap.createScaledBitmap(originBmp, size, size, true)
            if (originBmp !== shortcutIcon) {
                originBmp.recycle()
            }
            val shortcutPackageName = it.packageName
            ShortcutInstaller.install(this@MainActivity, ShortcutInfoModel(shortcutLabel, shortcutIcon, shortcutPackageName))
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
