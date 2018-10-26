package com.ykrc17.shortcutcreator.app.select.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ykrc17.shortcutcreator.R
import com.ykrc17.shortcutcreator.app.select.model.AppInfoModel

class AppListViewHolder : RecyclerView.ViewHolder {
    private val views: SelectAppItemBinding = SelectAppItemBinding(itemView)

    constructor(context: Context, parent: ViewGroup) : super(LayoutInflater.from(context).inflate(R.layout.select_app_item, parent, false))

    fun bindTo(model: AppInfoModel) = views.apply {
        tv_package_name.text = model.packageName
        tv_label.text = model.label
        iv_icon.setImageDrawable(model.icon)
    }
}