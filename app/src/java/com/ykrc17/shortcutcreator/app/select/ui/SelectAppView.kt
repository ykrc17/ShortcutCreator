package com.ykrc17.shortcutcreator.app.select.ui

import android.arch.lifecycle.LifecycleOwner
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.ykrc17.shortcutcreator.app.select.model.AppInfoModel
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
        }
        presenter.loadAppList()
    }

    fun showAppList(result: ArrayList<AppInfoModel>) {
        adapter.addAll(result)
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