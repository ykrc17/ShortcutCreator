package com.ykrc17.shortcutcreator.arch

import android.arch.lifecycle.CompleteLifecycleObserver
import android.support.v7.app.AppCompatActivity

abstract class View<P : Any>(val activity: AppCompatActivity) : CompleteLifecycleObserver {

    lateinit var presenter: P

    init {
        activity.lifecycle.addObserver(this)
    }
}