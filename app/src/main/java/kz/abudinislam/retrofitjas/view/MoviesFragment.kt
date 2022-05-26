package kz.abudinislam.retrofitjas.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.abudinislam.retrofitjas.databinding.FragmentMoviesBinding
import kz.abudinislam.retrofitjas.model.Result
import kz.abudinislam.retrofitjas.view.adapter.MoviesAdapter
import kz.abudinislam.retrofitjas.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext


class MoviesFragment : Fragment(), CoroutineScope {

    lateinit var binding: FragmentMoviesBinding
    private  val  viewModel by viewModel<MoviesViewModel>()
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
        logOut()
    }

    private fun getClick() {
        launch {
            binding.rvMovies.adapter = adapter
            adapter.onMovieClickListener = object : MoviesAdapter.OnMovieClickListener {
                override fun onMovieClick(result: Result) {
                    val action =
                        MoviesFragmentDirections.actionMoviesFragmentToDetailFragment(result.id)
                    findNavController().navigate(action)
                }
            }
        }

    }

    private fun initAndObserveViewModel() {
//        val viewModelProviderFactory = ViewModelProviderFactory(requireActivity().application)
//        viewModel = ViewModelProvider(this, viewModelProviderFactory)[MoviesViewModel::class.java]


        viewModel.loadingState.observe(viewLifecycleOwner) {
            when (it) {
                is MoviesViewModel.State.ShowLoading -> binding.progressBar.visibility =
                    View.VISIBLE
                is MoviesViewModel.State.HideLoading -> binding.progressBar.visibility = View.GONE
                is MoviesViewModel.State.Finish -> viewModel.movies.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                    binding.rvMovies.adapter = adapter
                }
            }
        }

    }

    private fun logOut() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                requireContext().let {
                    AlertDialog
                        .Builder(it)
                        .setMessage("Выйти?")
                        .setPositiveButton("Да") { dialogInterface, i ->
                            requireActivity().finish()
                        }
                        .setNegativeButton("Нет") { dialogInterface, i -> }
                        .create()
                        .show()
                }

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}