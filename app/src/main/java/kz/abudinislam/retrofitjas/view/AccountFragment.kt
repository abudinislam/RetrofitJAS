package kz.abudinislam.retrofitjas.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kz.abudinislam.retrofitjas.R
import kz.abudinislam.retrofitjas.databinding.FragmentAccountBinding
import kz.abudinislam.retrofitjas.viewmodel.AccountViewModel
import kz.abudinislam.retrofitjas.viewmodel.DetailViewModel
import kotlin.coroutines.CoroutineContext

class AccountFragment : Fragment(),CoroutineScope {

   lateinit var binding:FragmentAccountBinding
    private lateinit var viewModel: AccountViewModel
    private lateinit var prefSettings: SharedPreferences

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
        binding = FragmentAccountBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

}