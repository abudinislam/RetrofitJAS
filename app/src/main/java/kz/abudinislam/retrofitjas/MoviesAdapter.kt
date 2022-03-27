package kz.abudinislam.retrofitjas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kz.abudinislam.retrofitjas.databinding.ItemMoviesBinding

class MoviesAdapter(list: List<Result>) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {
    class MovieViewHolder(val binding: ItemMoviesBinding) : RecyclerView.ViewHolder(binding.root)

    var listMovies = list
    var onMovieClickListener: OnMovieClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            ItemMoviesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        with(holder.binding) {
            tvMovieName.text = listMovies[position].title
            Picasso.get().load(IMAGE_URL + listMovies[position].posterPath).into(ivMovie)

            root.setOnClickListener {
                onMovieClickListener?.onMovieClick(listMovies[position])
                true
            }
        }


    }

    override fun getItemCount(): Int {
        return listMovies.size
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }

    interface OnMovieClickListener {
        fun onMovieClick(result: Result)
    }

}