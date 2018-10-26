package com.ykrc17.shortcutcreator.widget

import android.content.Context
import android.support.v7.widget.RecyclerView

abstract class ArrayListAdapter<E, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH> {
    private val list = arrayListOf<E>()
    protected val context: Context

    constructor(context: Context) {
        this.context = context
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        onBindViewHolder(holder, list[position])
    }

    abstract fun onBindViewHolder(holder: VH, element: E)

    fun add(item: E) {
        val startIndex = list.size
        list.add(item)
        notifyItemInserted(startIndex)
    }

    fun addAll(elements: Collection<E>) {
        val startIndex = list.size
        list.addAll(elements)
        notifyItemRangeInserted(startIndex, elements.size)
    }

    fun resetAll(elements: Collection<E>) {
        list.clear()
        list.addAll(elements)
        notifyDataSetChanged()
    }
}