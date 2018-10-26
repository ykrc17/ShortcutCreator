package com.ykrc17.shortcutcreator.res

import android.content.res.Resources

class DP(private var dp: Float) {
    constructor(dp: Int) : this(dp.toFloat())

    fun toPx() = Resources.getSystem().displayMetrics.density * dp

    fun toPixelInt() = Math.round(toPx())
}