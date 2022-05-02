package kz.abudinislam.retrofitjas.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.abudinislam.retrofitjas.databinding.FragmentMoviesBinding
import kz.abudinislam.retrofitjas.model.Result
import kz.abudinislam.retrofitjas.viewmodel.MoviesViewModel
import kotlin.coroutines.CoroutineContext


class MoviesFragment : Fragment(),CoroutineScope {

    lateinit var binding: FragmentMoviesBinding
    private lateinit var viewModel: MoviesViewModel
    private val adapter = MoviesAdapter()

    override val coroutineContext: CoroutineContext = Dispatchers.Main



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAndObserveViewModel()

        getClick()

    }

    private fun getClick(){
        launch {
        binding.rvMovies.adapter = adapter
        adapter.onMovieClickListener = object: MoviesAdapter.OnMovieClickListener{
            override fun onMovieClick(result: Result) {
            val action = MoviesFragmentDirections.actionMoviesFragmentToDetailFragment(result)
            findNavController().navigate(action)
            }
        }
        }

    }

    private fun initAndObserveViewModel(){
        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        viewModel.loadingState.observe(viewLifecycleOwner){
        when(it){
        is MoviesViewModel.State.ShowLoading ->binding.progressBar.visibility = View.VISIBLE
        is MoviesViewModel.State.HideLoading ->binding.progressBar.visibility = View.GONE
        is MoviesViewModel.State.Finish ->viewModel.movies.observe(viewLifecycleOwner){
            adapter.submitList(it)
            binding.rvMovies.adapter = adapter

        }
        }
        }
    }
}