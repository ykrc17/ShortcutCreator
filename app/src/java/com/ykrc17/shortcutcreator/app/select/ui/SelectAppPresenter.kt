package com.ykrc17.shortcutcreator.app.select.ui

import android.os.AsyncTask
import com.ykrc17.shortcutcreator.app.select.model.AppListModel
import com.ykrc17.shortcutcreator.arch.Presenter

class SelectAppPresenter(view: SelectAppView) : Presenter<SelectAppView>(view) {

    fun asyncLoadAppList() {
        AsyncTask.THREAD_POOL_EXECUTOR.execute {
            context.packageManager.also { pm ->
                pm.getInstalledApplications(0).forEach { appInfo ->
                    val appInfoModel = AppListModel(appInfo.packageName, appInfo.loadLabel(pm).toString(), appInfo.loadIcon(pm), appInfo)
                    handler.post {
                        view.appendAppList(appInfoModel)
                    }
                }
            }
        }
    }

}