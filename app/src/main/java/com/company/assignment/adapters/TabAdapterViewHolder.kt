package com.company.assignment.adapters

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.company.assignment.activities.AlbumDetails
import com.company.assignment.activities.ProfileScreen
import com.company.assignment.databinding.TablayoutviewholderBinding
import com.squareup.picasso.Picasso

class TabAdapterViewHolder(private val binding: TablayoutviewholderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun initializeUIComponent(
        imageUrl: String,
        title: String,
        artist: String,
        context: Context,
        tab: Int
    ) {
        binding.textView.text = title
        binding.textView2.text = artist
        Picasso.get().load(imageUrl).into(binding.imageView3)

        if (tab == 0) {
            binding.tabviewholder.setOnClickListener {
                val intent = Intent(context, AlbumDetails::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                intent.putExtra("artistName", artist)
                intent.putExtra("titleName", title)
                intent.putExtra("imageUrl", imageUrl)

                context.startActivity(intent)
            }
        } else if (tab == 1) {
            binding.tabviewholder.setOnClickListener {
                val intent = Intent(context, ProfileScreen::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                intent.putExtra("artistName", artist)

                context.startActivity(intent)
            }

        }
    }

}