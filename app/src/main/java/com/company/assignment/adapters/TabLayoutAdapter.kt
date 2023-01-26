package com.company.assignment.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.assignment.databinding.TablayoutviewholderBinding
import com.company.assignment.models.MainModel

@SuppressLint("StaticFieldLeak")
private var context: Context? = null

class TabLayoutAdapter(
    private var arrayList: ArrayList<MainModel>,
    private var tab: Int
) : RecyclerView
.Adapter<TabAdapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        return TabAdapterViewHolder(
            TablayoutviewholderBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: TabAdapterViewHolder, position: Int) {
        if (tab == 0) {
            holder.initializeUIComponent(
                arrayList[position].imageUrl,
                arrayList[position].Name,
                arrayList[position].artistName,
                context!!,
                0
            )
        } else if (tab == 1) {
            holder.initializeUIComponent(
                arrayList[position].imageUrl,
                "",
                arrayList[position].artistName,
                context!!,
                1
            )
        } else if (tab == 2) {
            holder.initializeUIComponent(
                arrayList[position].imageUrl,
                arrayList[position].Name,
                arrayList[position].artistName,
                context!!,
                2
            )
        }
    }
}