package kz.abudinislam.retrofitjas.presentation.view.adapter.cast_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import kz.abudinislam.retrofitjas.data.cast.Cast
import kz.abudinislam.retrofitjas.databinding.ItemCastBinding

class CastAdapter : ListAdapter<Cast, CastViewHolder>(CastDiffCallback) {

//    var onFilmClickListener: OnFilmClickListener? = null
    private val IMAGE_URL = "https://image.tmdb.org/t/p/w500"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        return CastViewHolder(
            ItemCastBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val cast = getItem(position)

        with(holder.binding) {
//            Picasso.get().load(IMAGE_URL + cast.profile_path).into(iv)
//            movieItemID.setOnClickListener {
//
//                onFilmClickListener?.onFilmClick(movie)
//            }
            tvCastName.text = cast.name
            tvCastRole.text = cast.character
        }
    }
//
//    interface OnFilmClickListener {
//
//        fun onFilmClick(movie: Movie)
//    }
}