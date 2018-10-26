package com.ykrc17.shortcutcreator.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ykrc17.shortcutcreator.arch.Presenter

class CommonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val presenterClass = intent.getSerializableExtra(EXTRA_PRESENTER_CLASS) as Class<*>
        val presenter: Presenter<*> = presenterClass.getConstructor(AppCompatActivity::class.java).newInstance(this) as Presenter<*>
        super.onCreate(savedInstanceState)
    }

    companion object {
        private const val EXTRA_PRESENTER_CLASS = "extra.persenter.class"

        fun jump(context: Context, presenterClass: Class<out Presenter<*>>, extras: Bundle = Bundle()) {
            val intent = Intent(context, CommonActivity::class.java).apply {
                putExtra(EXTRA_PRESENTER_CLASS, presenterClass)
                putExtras(extras)
            }
            context.startActivity(intent)
        }
    }
}