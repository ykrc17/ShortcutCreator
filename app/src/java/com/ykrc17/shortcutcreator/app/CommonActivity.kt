package com.ykrc17.shortcutcreator.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ykrc17.shortcutcreator.arch.View

class CommonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewClass = intent.getSerializableExtra(EXTRA_VIEW_CLASS) as Class<*>
        val view: View<*> = viewClass.getConstructor(AppCompatActivity::class.java).newInstance(this) as View<*>
        view.doCreatePresenter()
        super.onCreate(savedInstanceState)
    }

    companion object {
        private const val EXTRA_VIEW_CLASS = "extra.view.class"

        fun jump(context: Context, viewClass: Class<out View<*>>, extras: Bundle = Bundle()) {
            val intent = Intent(context, CommonActivity::class.java).apply {
                putExtra(EXTRA_VIEW_CLASS, viewClass)
                putExtras(extras)
            }
            context.startActivity(intent)
        }

        fun jump(activity: AppCompatActivity, viewClass: Class<out View<*>>, requestCode: Int, extras: Bundle = Bundle()) {
            val intent = Intent(activity, CommonActivity::class.java).apply {
                putExtra(EXTRA_VIEW_CLASS, viewClass)
                putExtras(extras)
            }
            activity.startActivityForResult(intent, requestCode)
        }
    }
}