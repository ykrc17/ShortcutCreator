package com.ykrc17.shortcutmaker.pm

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v4.content.pm.ShortcutInfoCompat
import android.support.v4.content.pm.ShortcutManagerCompat
import android.support.v4.graphics.drawable.IconCompat
import com.ykrc17.shortcutmaker.BuildConfig
import com.ykrc17.shortcutmaker.RedirectActivity
import com.ykrc17.shortcutmaker.model.ShortcutInfoModel

object ShortcutCreator {
    private const val ID = BuildConfig.APPLICATION_ID
    private const val ACTION = "com.ykrc17.intent.action.REDIRECT"

    fun create(context: Context, model: ShortcutInfoModel) {
        // 必须在应用存活的时候才能打开其他应用
        // TODO 尝试Activity以外的方式
        val creatorIntent = Intent(context, RedirectActivity::class.java)
                .setAction(ACTION)
                .putExtra(RedirectActivity.EXTRA_LAUNCH_PACKAGE, model.packageName)
        context.packageManager.getLaunchIntentForPackage(model.packageName)?.also {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val shortcutInfoCompat = ShortcutInfoCompat.Builder(context, ID)
                    .setShortLabel(model.label)
                    .setIcon(IconCompat.createWithBitmap(BitmapFactory.decodeFile(model.iconPath)))
                    .setIntent(creatorIntent)
//                    .setIntents(arrayOf(creatorIntent, it))
                    .build()
            ShortcutManagerCompat.requestPinShortcut(context, shortcutInfoCompat, null)
        } ?: println("${model.packageName} has no launcher activity")
    }
}