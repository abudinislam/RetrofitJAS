package kz.abudinislam.retrofitjas.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.abudinislam.retrofitjas.R
import kz.abudinislam.retrofitjas.databinding.FragmentFavoritesBinding
import kz.abudinislam.retrofitjas.model.Result
import kz.abudinislam.retrofitjas.view.adapter.MoviesAdapter
import kz.abudinislam.retrofitjas.viewmodel.FavoritesViewModel
import kz.abudinislam.retrofitjas.viewmodel.MoviesViewModel
import kz.abudinislam.retrofitjas.viewmodel.ViewModelProviderFactory
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext


class FavoritesFragment : Fragment(), CoroutineScope {

    private lateinit var binding: FragmentFavoritesBinding
    override val coroutineContext: CoroutineContext = Dispatchers.Main
    private lateinit var viewModel: FavoritesViewModel

    private val adapter = MoviesAdapter()
    private lateinit var prefSettings: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        prefSettings = context?.getSharedPreferences(
            LoginFragment.APP_SETTINGS, Context.MODE_PRIVATE
        ) as SharedPreferences
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getSessionId()
        initAndObserveViewModel()
        viewModel.getPosts(sessionId)
        getClick()


    }

    private fun getSessionId() {
        try {
            sessionId = prefSettings.getString(LoginFragment.SESSION_ID_KEY, null) as String
        } catch (e: Exception) {
        }
    }

    private fun initAndObserveViewModel() {
        val viewModelProviderFactory = ViewModelProviderFactory(requireActivity())
        viewModel = ViewModelProvider(
            this, viewModelProviderFactory
        )[FavoritesViewModel::class.java]

        viewModel.loadingState.observe(viewLifecycleOwner) {
            when (it) {
                is MoviesViewModel.State.ShowLoading -> binding.pbFavorites.visibility =
                    View.VISIBLE
                is MoviesViewModel.State.HideLoading -> binding.pbFavorites.visibility = View.GONE
                is MoviesViewModel.State.Finish -> viewModel.movies.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                    binding.rvFavorites.adapter = adapter
                }
            }
        }

    }

    private fun getClick() {
        launch {
            binding.rvFavorites.adapter = adapter
            adapter.onMovieClickListener = object : MoviesAdapter.OnMovieClickListener {
                override fun onMovieClick(result: Result) {
                    val action =
                        FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(result.id)
                    findNavController().navigate(action)
                }

            }
        }
    }


    companion object {

        private var sessionId: String = ""
    }


}