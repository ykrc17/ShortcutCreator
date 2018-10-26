package com.ykrc17.shortcutcreator.arch

import android.support.v7.app.AppCompatActivity

abstract class Presenter<V : Any>(val activity: AppCompatActivity) {
    lateinit var view: V
}