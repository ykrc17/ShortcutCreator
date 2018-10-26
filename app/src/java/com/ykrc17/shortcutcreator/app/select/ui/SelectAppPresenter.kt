package com.ykrc17.shortcutcreator.app.select.ui

import android.support.v7.app.AppCompatActivity
import com.ykrc17.shortcutcreator.arch.Presenter
import com.ykrc17.shortcutcreator.app.select.model.AppInfoModel

class SelectAppPresenter(activity: AppCompatActivity) : Presenter<SelectAppView>(activity) {
    init {
        view = SelectAppView(activity)
        view.presenter = this
    }

    fun loadAppList() {
        val result = arrayListOf<AppInfoModel>()
        activity.packageManager.apply {
            getInstalledApplications(0).forEach { appInfo ->
                result.add(AppInfoModel(appInfo.loadLabel(this), appInfo.loadIcon(this)))
            }
        }
        view.showAppList(result)
    }

}