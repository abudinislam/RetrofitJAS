package kz.abudinislam.retrofitjas.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import kz.abudinislam.retrofitjas.databinding.FragmentDetailBinding
import kz.abudinislam.retrofitjas.model.Result


class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    private lateinit var result: Result
    private val args: DetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        result = args.result

        Picasso.get().load(IMAGE_URL + result.posterPath).into(binding.ivDetailIcon)
        binding.tvDetailDesc.text = result.overview
    }

    companion object {

        private const val KEY = "result"
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"

    }
}


