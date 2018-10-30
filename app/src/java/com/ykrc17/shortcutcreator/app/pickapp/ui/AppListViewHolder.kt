package com.ykrc17.shortcutcreator.app.pickapp.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ykrc17.shortcutcreator.R
import com.ykrc17.shortcutcreator.app.pickapp.model.AppListModel

class AppListViewHolder : RecyclerView.ViewHolder {
    private val views: PickAppItemBinding = PickAppItemBinding(itemView)

    constructor(context: Context, parent: ViewGroup) : super(LayoutInflater.from(context).inflate(R.layout.pick_app_item, parent, false))

    fun bindTo(model: AppListModel) = views.apply {
        tv_package_name.text = model.packageName
        tv_label.text = model.label
        iv_icon.setImageDrawable(model.icon)
    }
}