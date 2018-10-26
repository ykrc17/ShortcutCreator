package com.ykrc17.shortcutcreator.app.select.ui

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import com.ykrc17.shortcutcreator.app.select.model.AppListModel
import com.ykrc17.shortcutcreator.arch.Presenter

class SelectAppPresenter(activity: AppCompatActivity) : Presenter<SelectAppView>(activity) {
    init {
        view = SelectAppView(activity)
        view.presenter = this
    }

    fun asyncLoadAppList() {
        AsyncTask.THREAD_POOL_EXECUTOR.execute {
            activity.packageManager.also { pm ->
                pm.getInstalledApplications(0).forEach { appInfo ->
                    val appInfoModel = AppListModel(appInfo.packageName, appInfo.loadLabel(pm).toString(), appInfo.loadIcon(pm), appInfo)
                    activity.runOnUiThread {
                        view.appendAppList(appInfoModel)
                    }
                }
            }
        }
    }

}