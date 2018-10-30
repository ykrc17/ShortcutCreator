package com.ykrc17.shortcutcreator.arch

import android.content.Context
import android.os.Handler
import android.os.Looper

abstract class Presenter<V : ActivityDelegate>(protected val view: V) {
    protected val handler: Handler = Handler(Looper.getMainLooper())
    protected val context: Context
        get() = view.activity
}