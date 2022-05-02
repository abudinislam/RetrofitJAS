package kz.abudinislam.retrofitjas.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kz.abudinislam.retrofitjas.databinding.FragmentDetailBinding
import kz.abudinislam.retrofitjas.model.Result
import kz.abudinislam.retrofitjas.viewmodel.DetailViewModel
import kz.abudinislam.retrofitjas.viewmodel.ViewModelProviderFactory
import kotlin.coroutines.CoroutineContext


class DetailFragment : Fragment(),CoroutineScope {

    lateinit var binding: FragmentDetailBinding
    private lateinit var result: Result
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var viewModel: DetailViewModel

    override val coroutineContext: CoroutineContext = Dispatchers.Main


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        getMovieDetails()
    }


    private fun initViewModel(){
        val viewModelProviderFactory = ViewModelProviderFactory(requireActivity())
        viewModel = ViewModelProvider(this, viewModelProviderFactory)[DetailViewModel::class.java]
    }

    private fun getMovieDetails(){
        result = args.result
        viewModel.getMovieDetails(result.id)
        viewModel.loadingState.observe(viewLifecycleOwner){
            when(it){
                is DetailViewModel.StateDetail.ShowLoading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is DetailViewModel.StateDetail.HideLoading ->{
                    binding.progressBar.visibility = View.GONE
                }
                is DetailViewModel.StateDetail.Finish ->{
                    viewModel.liveDataDetail.observe(viewLifecycleOwner){
                        Picasso.get().load(IMAGE_URL + result.posterPath).into(binding.ivDetailIcon)
                        binding.tvMovieDesc.text = it.title
                        binding.tvMovieOverview.text = it.overview

                    }
                }
            }
        }

    }

    companion object {

        private const val KEY = "result"
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"

    }


}


