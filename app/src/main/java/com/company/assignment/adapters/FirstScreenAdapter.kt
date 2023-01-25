package com.company.assignment.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.assignment.activities.SecondScreen
import com.company.assignment.databinding.GenreViewHolderBinding

@SuppressLint("StaticFieldLeak")
private var context: Context? = null

class FirstScreenAdapter(
    private var arrayList: ArrayList<String>
) : RecyclerView
.Adapter<RecyclerView.ViewHolder>() {

    inner class FirstScreenViewHolder(private val binding: GenreViewHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun initializeUIComponent(name: String) {
            binding.genreicon.text = name

            binding.genreicon.setOnClickListener {
                val intent = Intent(context, SecondScreen::class.java)
                intent.putExtra("nameTag", name)
                context!!.startActivity(intent)
            }
            
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        return FirstScreenViewHolder(
            GenreViewHolderBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FirstScreenViewHolder).initializeUIComponent(arrayList[position])
    }
}

