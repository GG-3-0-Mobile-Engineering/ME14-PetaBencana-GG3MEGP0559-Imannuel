package com.imannuel.petabencana.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imannuel.petabencana.R
import com.imannuel.petabencana.data.model.Geometries
import com.imannuel.petabencana.databinding.DisasterItemBinding
import com.imannuel.petabencana.utils.DiffUtil
import com.imannuel.petabencana.utils.TimeUtils

class LatestDisasterAdapter(private val listener: (Geometries) -> Unit) :
    RecyclerView.Adapter<LatestDisasterAdapter.latestDisasterViewHolder>() {
    private var items = emptyList<Geometries>()

    class latestDisasterViewHolder(binding: DisasterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val image = binding.disasterItemImageView
        private val title = binding.disasterItemTitleTv
        private val time = binding.disasterItemTimeTv
        private val desc = binding.disasterItemDescTv

        fun bind(item: Geometries) {
            if (item.properties?.imageUrl == null) {
                Glide.with(itemView).load(R.drawable.noimg).into(image)
            } else {
                Glide.with(itemView).load(item.properties?.imageUrl).into(image)
            }

            if (item.properties?.title == null) {
                title.text = item.properties?.disasterType
            } else {
                title.text = item.properties?.title
            }

            time.text = TimeUtils.getTimeAgo(item.properties?.createdAt.toString())

            desc.text = item.properties?.text

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): latestDisasterViewHolder {
        return latestDisasterViewHolder(
            DisasterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
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

    fun setData(data: List<Geometries>) {
        val diffUtil = DiffUtil(items, data)
        val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffUtil)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }
}