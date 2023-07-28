package com.imannuel.petabencana.ui.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imannuel.petabencana.R
import com.imannuel.petabencana.data.model.Properties
import com.imannuel.petabencana.databinding.DisasterItemBinding
import com.imannuel.petabencana.utils.DiffUtil
import com.imannuel.petabencana.utils.TimeUtils

class SavedDisasterAdapter(
    private val listener: (Properties) -> Unit,
    private val listener2: (Properties) -> Unit
) : RecyclerView.Adapter<SavedDisasterAdapter.SavedDisasterViewHolder>() {

    private var items = emptyList<Properties>()

    class SavedDisasterViewHolder(binding: DisasterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val image = binding.disasterItemImageView
        private val title = binding.disasterItemTitleTv
        private val time = binding.disasterItemTimeTv
        private val desc = binding.disasterItemDescTv
        val favBtn = binding.favButton

        fun bind(item: Properties) {
            if (item.imageUrl == null) {
                Glide.with(itemView).load(R.drawable.noimg).into(image)
            } else {
                Glide.with(itemView).load(item.imageUrl).into(image)
            }

            if (item.title == null) {
                title.text = item.disasterType
            } else {
                title.text = item.title
            }

            time.text = TimeUtils.getTimeAgo(item.createdAt.toString())

            desc.text = item.text

            if (item.isSaved) {
                favBtn.setIconResource(R.drawable.baseline_bookmark_24)
            } else {
                favBtn.setIconResource(R.drawable.baseline_bookmark_border_24)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedDisasterViewHolder {
        return SavedDisasterViewHolder(
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

    override fun onBindViewHolder(holder: SavedDisasterViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)

        holder.favBtn.setOnClickListener {
            listener(item)
        }
        holder.itemView.setOnClickListener {
            listener2(item)
        }
    }

    fun setData(data: List<Properties>) {
        val diffUtil = DiffUtil(items, data)
        val diffResult = androidx.recyclerview.widget.DiffUtil.calculateDiff(diffUtil)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }
}