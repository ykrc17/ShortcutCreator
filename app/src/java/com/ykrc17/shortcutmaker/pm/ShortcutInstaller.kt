package com.ykrc17.shortcutmaker.pm

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.content.pm.ShortcutInfoCompat
import android.support.v4.content.pm.ShortcutManagerCompat
import android.support.v4.graphics.drawable.IconCompat
import com.ykrc17.shortcutmaker.BuildConfig
import com.ykrc17.shortcutmaker.RedirectActivity
import com.ykrc17.shortcutmaker.model.ShortcutInfoModel

object ShortcutInstaller {
    private const val ID = BuildConfig.APPLICATION_ID
    private const val ACTION = "com.ykrc17.intent.action.REDIRECT"

    fun install(context: Context, info: ShortcutInfoModel) {
        // 必须在应用存活的时候才能打开其他应用
        requestShortcutPermission(context) {
            val shortcutInfoCompat = ShortcutInfoCompat.Builder(context, ID)
                    .setShortLabel(info.label)
                    .setIcon(IconCompat.createWithBitmap(BitmapFactory.decodeFile(info.iconPath)))
                    .setIntent(createRedirectIntent(context, info))
                    .build()
            ShortcutManagerCompat.requestPinShortcut(context, shortcutInfoCompat, null)
        }
    }

    private fun requestShortcutPermission(context: Context, callback: () -> Unit) {
        if (Build.VERSION.SDK_INT >= 26) {
            AlertDialog.Builder(context).apply {
                setMessage("创建快捷方式可能需要权限")
                setNegativeButton("取消") { _: DialogInterface, _: Int -> }
                setNeutralButton("前往权限页面") { _: DialogInterface, _: Int -> context.startActivity(createAppDetailsIntent(context)) }
                setPositiveButton("直接创建") { _: DialogInterface, _: Int -> callback() }
                show()
            }
        } else {
            callback()
        }
    }

    private fun createAppDetailsIntent(context: Context) = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.parse("package:${context.packageName}")
    }

    private fun createRedirectIntent(context: Context, info: ShortcutInfoModel) = Intent(context, RedirectActivity::class.java)
            .setAction(ACTION)
            .putExtra(RedirectActivity.EXTRA_LAUNCH_PACKAGE, info.packageName)
}