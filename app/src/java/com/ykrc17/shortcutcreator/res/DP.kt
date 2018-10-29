package com.ykrc17.shortcutcreator.res

import android.content.res.Resources

class DP(private var dp: Float) {
    constructor(dp: Int) : this(dp.toFloat())

    fun toPx() = density * dp

    fun toPxInt() = Math.round(toPx())

    companion object {
        private val density = Resources.getSystem().displayMetrics.density
    }
}