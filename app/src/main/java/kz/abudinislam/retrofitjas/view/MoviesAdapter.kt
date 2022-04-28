package kz.abudinislam.retrofitjas.view

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kz.abudinislam.retrofitjas.databinding.ItemMoviesBinding
import kz.abudinislam.retrofitjas.model.Result

class MoviesAdapter: ListAdapter<Result, MoviesViewHolder>(MovieDiffCallback){

private val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    var onMovieClickListener: OnMovieClickListener? = null
    var onReachEndListener: OnReachEndListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
    return MoviesViewHolder(
        ItemMoviesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )
}


    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = getItem(position)
        if (position >= (itemCount - 3) && onReachEndListener != null) {
            onReachEndListener?.onReachEnd()
        }
        with(holder.binding) {
            tvMovieName.text = movie.title
            Picasso.get().load(IMAGE_URL+movie.posterPath).into(ivMovie)

            root.setOnClickListener {
                onMovieClickListener?.onMovieClick(movie)// передаём все сведения о фильме при клике на постер
                true // нужно для перехода в детали фильма при клике на постер с фильмом
            }
        }
    }

    interface OnMovieClickListener {
        fun onMovieClick(result: Result)
    }

    interface OnReachEndListener {
        fun onReachEnd()
    }

}