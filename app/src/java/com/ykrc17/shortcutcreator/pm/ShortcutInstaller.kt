package com.ykrc17.shortcutcreator.pm

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.content.pm.ShortcutInfoCompat
import android.support.v4.content.pm.ShortcutManagerCompat
import android.support.v4.graphics.drawable.IconCompat
import android.widget.Toast
import com.ykrc17.shortcutcreator.app.RedirectActivity
import com.ykrc17.shortcutcreator.app.model.ShortcutInfoModel
import com.ykrc17.shortcutcreator.res.DP

object ShortcutInstaller {
    private const val ACTION = "com.ykrc17.intent.action.REDIRECT"

    fun install(context: Context, info: ShortcutInfoModel) {
        // 必须在应用存活的时候才能打开其他应用
        requestShortcutPermission(context) {
            val shortcutInfoCompat = ShortcutInfoCompat.Builder(context, info.packageName)
                    .setShortLabel(info.label)
                    .setIcon(IconCompat.createWithBitmap(createRoundedBitmap(info.icon)))
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
            Toast.makeText(context, "已尝试创建快捷方式", Toast.LENGTH_SHORT).show()
            callback()
        }
    }

    private fun createAppDetailsIntent(context: Context) = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.parse("package:${context.packageName}")
    }

    private fun createRoundedBitmap(iconPath: String) = createRoundedBitmap(BitmapFactory.decodeFile(iconPath))

    private fun createRoundedBitmap(inBmp: Bitmap): Bitmap {
        // 将bitmap转换为mutable
        val outBmp = Bitmap.createBitmap(inBmp.width, inBmp.height, Bitmap.Config.ARGB_8888)
        Canvas(outBmp).apply {
            val radius = DP(16).toPx()
            val paint = Paint().apply {
                isAntiAlias = true
                color = Color.WHITE
            }
            drawRoundRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), radius, radius, paint)
            drawBitmap(inBmp, 0f, 0f, paint.apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN) })
        }
        return outBmp
    }

    private fun createRedirectIntent(context: Context, info: ShortcutInfoModel) = Intent(context, RedirectActivity::class.java)
            .setAction(ACTION)
            .putExtra(RedirectActivity.EXTRA_LAUNCH_PACKAGE, info.packageName)
}