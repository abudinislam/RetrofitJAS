package kz.abudinislam.retrofitjas.presentation.view.adapter.cast_adapter

import androidx.recyclerview.widget.DiffUtil
import kz.abudinislam.retrofitjas.data.cast.Cast

object CastDiffCallback : DiffUtil.ItemCallback<Cast>() {
    override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem == newItem
    }
}