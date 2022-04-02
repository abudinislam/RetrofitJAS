package kz.abudinislam.retrofitjas

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kz.abudinislam.retrofitjas.databinding.FragmentMoviesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MoviesFragment : Fragment() {

    lateinit var binding: FragmentMoviesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getPosts()


    }

    private fun getPosts() {
        RetrofitService.getPostApi().getMoviesList().enqueue(object : Callback<Movie> {
            override fun onFailure(call: Call<Movie>, t: Throwable) {
                println("ERROR")
            }

            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                Log.d("My_post_list", response.body().toString())
                if (response.isSuccessful) {
                    val list = response.body()
                    list?.results?.let {
                        val adapter = MoviesAdapter(list = it)
                        binding.rvMovies.adapter = adapter
                        adapter.onMovieClickListener = object : MoviesAdapter.OnMovieClickListener {
                            override fun onMovieClick(result: Result) {
                               detailFragment(result)

                            }
                        }
                    }
                }
            }
        })
    }

    private fun detailFragment(result: Result) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.activity_main, DetailFragment.newInstance(result))
            .addToBackStack(null)
            .commit()
    }

}