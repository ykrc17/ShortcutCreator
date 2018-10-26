package com.ykrc17.shortcutcreator.app.select.ui

import android.content.Context
import android.view.ViewGroup
import com.ykrc17.shortcutcreator.app.select.model.AppInfoModel
import com.ykrc17.shortcutcreator.widget.ArrayListAdapter

class AppListAdapter : ArrayListAdapter<AppInfoModel, AppListViewHolder> {
    constructor(context: Context) : super(context)

    override fun onCreateViewHolder(parent: ViewGroup, position: Int) = AppListViewHolder(context, parent)

    override fun onBindViewHolder(holder: AppListViewHolder, element: AppInfoModel) {
        holder.bindTo(element)
    }
}