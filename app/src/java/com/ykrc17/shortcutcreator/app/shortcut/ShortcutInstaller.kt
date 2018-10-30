package com.ykrc17.shortcutcreator.app.shortcut

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.ShortcutManager
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.content.pm.ShortcutInfoCompat
import android.support.v4.content.pm.ShortcutManagerCompat
import android.support.v4.graphics.drawable.IconCompat
import android.text.TextUtils
import android.widget.Toast
import com.ykrc17.shortcutcreator.app.RedirectActivity
import com.ykrc17.shortcutcreator.res.DP
import com.ykrc17.shortcutcreator.widget.toast

class ShortcutInstaller(private val callback: ShortcutInstallerCallback) {
    companion object CONSTANTS {
        private const val ACTION = "com.ykrc17.intent.action.REDIRECT"
        private val SHOULD_USE_SHORTCUT_MANAGER = Build.VERSION.SDK_INT >= 26
    }

    private var targetAppInfo: ApplicationInfo? = null
    private var shortcutLabel: String? = null
    private var shortcutBmp: Bitmap? = null

    fun install(context: Context) {
        if (isShortcutInfoValid(context)) {
            // 必须在应用存活的时候才能打开其他应用
            requestShortcutPermission(context) {
                val shortcutInfoCompat = ShortcutInfoCompat.Builder(context, targetAppInfo!!.packageName)
                        .setShortLabel(shortcutLabel!!)
                        .setIcon(IconCompat.createWithBitmap(shortcutBmp))
                        .setIntent(createRedirectIntent(context, targetAppInfo!!.packageName))
                        .build()
                if (tryUpdateShortcut(context, shortcutInfoCompat)) {
                    return@requestShortcutPermission
                }
                ShortcutManagerCompat.requestPinShortcut(context, shortcutInfoCompat, null)
            }
        }
    }

    private fun tryUpdateShortcut(context: Context, shortcutInfoCompat: ShortcutInfoCompat): Boolean {
        if (SHOULD_USE_SHORTCUT_MANAGER) {
            context.getSystemService(ShortcutManager::class.java).apply {
                if (pinnedShortcuts.any { it.id == shortcutInfoCompat.id }) {
                    if (updateShortcuts(listOf(shortcutInfoCompat.toShortcutInfo()))) {
                        context.toast("已更新快捷方式")
                        return true
                    }
                }
            }
        }
        return false
    }

    fun setTargetAppInfo(newTargetAppInfo: ApplicationInfo) {
        targetAppInfo = newTargetAppInfo
        callback.showTargetAppInfo(newTargetAppInfo)
    }

    fun setShortcutLabel(shortcutLabel: String) {
        this.shortcutLabel = shortcutLabel
    }

    fun setShortcutIcon(context: Context, uri: Uri) {
        setShortcutIcon(BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri)))
    }

    fun setShortcutIcon(bmp: Bitmap) {
        shortcutBmp?.recycle()
        shortcutBmp = createRoundedBitmap(createScaledBitmap(bmp))
        shortcutBmp?.also(callback::showShortcutIcon)
    }

    private fun isShortcutInfoValid(context: Context): Boolean {
        if (targetAppInfo == null) {
            context.toast("未选择目标应用")
            return false
        }
        if (TextUtils.isEmpty(shortcutLabel)) {
            context.toast("未输入快捷方式名")
            return false
        }
        if (shortcutBmp == null || shortcutBmp!!.isRecycled) {
            context.toast("未选择快捷方式图标")
            return false
        }
        return true
    }

    private fun requestShortcutPermission(context: Context, callback: () -> Unit) {
        if (SHOULD_USE_SHORTCUT_MANAGER) {
            AlertDialog.Builder(context).apply {
                setMessage("创建快捷方式可能需要权限")
                setNegativeButton("取消") { _: DialogInterface, _: Int -> }
                setNeutralButton("前往设置") { _: DialogInterface, _: Int -> context.startActivity(createPermissionIntent(context)) }
                setPositiveButton("直接创建") { _: DialogInterface, _: Int -> callback() }
                show()
            }
        } else {
            Toast.makeText(context, "已尝试创建快捷方式", Toast.LENGTH_SHORT).show()
            callback()
        }
    }

    private fun createPermissionIntent(context: Context): Intent {
        return if (Build.MANUFACTURER == "HUAWEI") {
            Intent("huawei.intent.action.HSM_PERMISSION_MANAGER")
        } else {
            Intent(Settings.ACTION_SETTINGS)
        }
    }

    private fun createScaledBitmap(inBmp: Bitmap): Bitmap {
        val size = DP(80).toPxInt()
        val outBmp = Bitmap.createScaledBitmap(inBmp, size, size, true)
        if (inBmp !== outBmp) {
            inBmp.recycle()
        }
        return outBmp
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

    private fun createRedirectIntent(context: Context, targetPackageName: String) = Intent(context, RedirectActivity::class.java)
            .setAction(ACTION)
            .putExtra(RedirectActivity.EXTRA_LAUNCH_PACKAGE, targetPackageName)
}