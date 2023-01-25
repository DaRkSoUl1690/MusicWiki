package com.company.assignment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.assignment.databinding.TablayoutviewholderBinding
import com.company.assignment.models.Album.AlbumModel

class AlbumAdapter(
    private var arrayList: ArrayList<AlbumModel>,
    private var tab: Int 
) : RecyclerView
.Adapter<TabAdapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
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
        if(tab == 0 || tab==2) {
            holder.initializeUIComponent(
                arrayList[position].imageUrl,
                arrayList[position].albumName,
                arrayList[position].artistName
            )
        }
        else if(tab==1)
        {
            holder.initializeUIComponent(
                arrayList[position].imageUrl,
                "",
                arrayList[position].artistName
            )
        }
        else {
            
        }
    }
}