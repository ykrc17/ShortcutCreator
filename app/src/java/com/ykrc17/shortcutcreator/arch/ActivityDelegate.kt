package com.ykrc17.shortcutcreator.arch

import android.arch.lifecycle.CompleteLifecycleObserver
import android.support.v7.app.AppCompatActivity

abstract class ActivityDelegate : CompleteLifecycleObserver {

    val activity: AppCompatActivity

    constructor(activity: AppCompatActivity) {
        this.activity = activity
        activity.lifecycle.addObserver(this)
    }
}