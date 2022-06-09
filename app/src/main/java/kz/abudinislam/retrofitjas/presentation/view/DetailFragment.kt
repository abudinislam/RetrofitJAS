package kz.abudinislam.retrofitjas.presentation.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kz.abudinislam.retrofitjas.R
import kz.abudinislam.retrofitjas.databinding.FragmentDetailBinding
import kz.abudinislam.retrofitjas.domain.model.Result
import kz.abudinislam.retrofitjas.presentation.viewmodel.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext


class DetailFragment : Fragment(), CoroutineScope {

    lateinit var binding: FragmentDetailBinding
    private lateinit var result: Result
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var prefSettings: SharedPreferences

    private  val  viewModel by viewModel<DetailViewModel>()

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefSettings = context?.getSharedPreferences(
            LoginFragment.APP_SETTINGS, Context.MODE_PRIVATE
        ) as SharedPreferences
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSessionId()
       // initViewModel()
        getMovieDetails()

        setOnClickFavorites()
        setLike()
    }

    private fun getSessionId() {
        try {
            sessionId = prefSettings.getString(LoginFragment.SESSION_ID_KEY, null) as String
        } catch (e: Exception) {

        }
    }

//    private fun initViewModel() {
//        val viewModelProviderFactory = ViewModelProviderFactory(requireActivity().application)
//        viewModel = ViewModelProvider(this, viewModelProviderFactory)[DetailViewModel::class.java]
//    }

    private fun setOnClickFavorites() {
        binding.ivFavIcon.setOnClickListener {
            viewModel.addOrDeleteFavorite(args.result, sessionId)
        }
    }

    private fun setLike() {
        viewModel.liveDataDetail.observe(viewLifecycleOwner) {

            if (it.favoritesState == true) {
                binding.ivFavIcon.setImageResource(R.drawable.ic_baseline_like)
            } else {
                binding.ivFavIcon.setImageResource(R.drawable.ic_baseline_not_like)
            }
        }
    }


    private fun getMovieDetails() {
        viewModel.getMovieDetails(args.result, sessionId)
        viewModel.loadingState.observe(viewLifecycleOwner) {
            when (it) {
                is DetailViewModel.StateDetail.ShowLoading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is DetailViewModel.StateDetail.HideLoading -> {
                    binding.progressBar.visibility = View.GONE
                }
                is DetailViewModel.StateDetail.Finish -> {
                    viewModel.liveDataDetail.observe(viewLifecycleOwner) {
                        Picasso.get().load(IMAGE_URL + it.posterPath).into(binding.ivDetailIcon)
                        binding.tvMovieDesc.text = it.title
                        binding.tvMovieOverview.text = it.overview
                        binding.tvDateRelease.text = it.releaseDate
                        binding.tvRating.text = it.voteAverage.toString()



                    }
                }
            }
        }

    }

    companion object {

        private const val KEY = "result"
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
        private var sessionId: String = ""

    }


}


