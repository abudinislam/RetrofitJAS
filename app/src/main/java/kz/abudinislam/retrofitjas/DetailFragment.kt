package kz.abudinislam.retrofitjas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kz.abudinislam.retrofitjas.databinding.FragmentDetailBinding
import java.lang.RuntimeException


class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    private lateinit var result: Result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parceArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

   Picasso.get().load(IMAGE_URL + result.posterPath).into(binding.ivDetailIcon)
    binding.tvDetailDesc.text = result.title
    }

    private fun parceArgs() {

        requireArguments().getParcelable<Result>(KEY)?.let {
            result = it
        }
    }

    companion object {

        private const val KEY = "result"
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"

        fun newInstance(result: Result):DetailFragment{

            return DetailFragment().apply {

                arguments = Bundle().apply {

                    putParcelable(KEY, result)
                }
            }
        }

    }
}


