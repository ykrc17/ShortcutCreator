package com.ykrc17.shortcutcreator.app.select.ui

import android.app.Activity
import android.arch.lifecycle.LifecycleOwner
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.ykrc17.shortcutcreator.app.select.model.AppListModel
import com.ykrc17.shortcutcreator.arch.View

class SelectAppView(activity: AppCompatActivity) : View<SelectAppPresenter>(activity) {
    private val views = SelectAppBinding()
    private val adapter = AppListAdapter(activity)

    override fun onCreate(owner: LifecycleOwner?) {
        activity.apply {
            setContentView(views.layoutRes)
            views.bind(window.decorView)
            supportActionBar?.title = "选择应用"
        }
        views.apply {
            rv_app_list.layoutManager = LinearLayoutManager(activity)
            rv_app_list.adapter = adapter
            adapter.onItemClickListener = {
                val intent = Intent()
                intent.putExtra(ApplicationInfo::class.java.canonicalName, it.appInfo)
                activity.setResult(Activity.RESULT_OK, intent)
                activity.finish()
            }
        }
        presenter.asyncLoadAppList()
    }

    fun appendAppList(result: AppListModel) {
        adapter.add(result)
    }

    override fun onStart(owner: LifecycleOwner?) {
    }

    override fun onResume(owner: LifecycleOwner?) {
    }

    override fun onPause(owner: LifecycleOwner?) {
    }

    override fun onStop(owner: LifecycleOwner?) {
    }

    override fun onDestroy(owner: LifecycleOwner?) {
    }
}