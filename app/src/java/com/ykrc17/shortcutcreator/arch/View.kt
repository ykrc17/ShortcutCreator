package com.ykrc17.shortcutcreator.arch

import android.support.v7.app.AppCompatActivity

abstract class View<P : Any>(activity: AppCompatActivity) : ActivityDelegate(activity) {
    lateinit var presenter: P

    fun doCreatePresenter() {
        presenter = onCreatePresenter()
    }

    abstract fun onCreatePresenter(): P
}