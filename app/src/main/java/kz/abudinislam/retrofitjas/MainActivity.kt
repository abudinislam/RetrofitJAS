package kz.abudinislam.retrofitjas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kz.abudinislam.retrofitjas.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
                    }
                }
            }
        })
    }

}