package kz.abudinislam.retrofitjas.view.adapter

import androidx.recyclerview.widget.DiffUtil
import kz.abudinislam.retrofitjas.model.Result


object MovieDiffCallback : DiffUtil.ItemCallback<Result>() {

    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }
}