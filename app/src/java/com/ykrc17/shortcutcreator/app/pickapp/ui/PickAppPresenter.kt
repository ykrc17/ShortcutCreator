package com.ykrc17.shortcutcreator.app.pickapp.ui

import android.os.AsyncTask
import com.ykrc17.shortcutcreator.app.pickapp.model.AppListModel
import com.ykrc17.shortcutcreator.arch.Presenter

class PickAppPresenter(view: PickAppView) : Presenter<PickAppView>(view) {

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