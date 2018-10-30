package com.ykrc17.shortcutcreator.app.shortcut

import android.content.pm.ApplicationInfo
import android.graphics.Bitmap

interface ShortcutInstallerCallback {
    fun showTargetAppInfo(targetAppInfo: ApplicationInfo)
    fun showShortcutIcon(shortcutIcon: Bitmap)
}