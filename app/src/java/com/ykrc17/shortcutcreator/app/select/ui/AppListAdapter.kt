package com.ykrc17.shortcutcreator.app.select.ui

import android.content.Context
import android.view.ViewGroup
import com.ykrc17.shortcutcreator.app.select.model.AppListModel
import com.ykrc17.shortcutcreator.widget.ArrayListAdapter

class AppListAdapter : ArrayListAdapter<AppListModel, AppListViewHolder> {
    var onItemClickListener: ((AppListModel) -> Unit)? = null

    constructor(context: Context) : super(context)

    override fun onCreateViewHolder(parent: ViewGroup, position: Int) = AppListViewHolder(context, parent)

    override fun onBindViewHolder(holder: AppListViewHolder, element: AppListModel) {
        holder.bindTo(element)
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(element)
        }
    }
}