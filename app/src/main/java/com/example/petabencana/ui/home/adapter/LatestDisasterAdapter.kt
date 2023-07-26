package com.example.petabencana.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petabencana.R
import com.example.petabencana.data.model.Geometries
import com.example.petabencana.databinding.DisasterItemBinding
import com.example.petabencana.utils.DiffUtil

class LatestDisasterAdapter( private val listener: (Geometries) -> Unit): RecyclerView.Adapter<LatestDisasterAdapter.latestDisasterViewHolder>() {
    private var items = emptyList<Geometries>()

    class latestDisasterViewHolder(binding: DisasterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val image = binding.disasterItemImageView
        private val title = binding.disasterItemTitleTv
        private val desc = binding.disasterItemDescTv

        fun bind(item : Geometries){
            if(item.properties?.imageUrl == null){
                Glide.with(itemView).load(R.drawable.noimg).into(image)
            }else{
                Glide.with(itemView).load(item.properties?.imageUrl).into(image)
            }

            if(item.properties?.title==null){
                title.setText(item.properties?.disasterType)
            }else{
                title.setText(item.properties?.title)
            }

            desc.text = item.properties?.text

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): latestDisasterViewHolder {
        return latestDisasterViewHolder(DisasterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: latestDisasterViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }

    fun setData(data : List<Geometries>){
        val diffUtil = DiffUtil(items, data)
        val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffUtil)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }
}