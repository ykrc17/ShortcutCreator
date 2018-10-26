package com.ykrc17.shortcutcreator.app.select.model

import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable

class AppListModel(val packageName: String,
                   val label: CharSequence,
                   val icon: Drawable,
                   val appInfo: ApplicationInfo)