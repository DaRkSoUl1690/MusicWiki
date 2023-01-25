package com.company.assignment.adapters

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.company.assignment.databinding.TablayoutviewholderBinding
import com.company.assignment.utils.glideLoad
import com.squareup.picasso.Picasso

class TabAdapterViewHolder(private val binding: TablayoutviewholderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun initializeUIComponent(imageUrl: String, title: String, artist: String) {
        binding.textView.text = title
        binding.textView2.text = artist
        Picasso.get().load(imageUrl).into(binding.imageView3);
    }
}