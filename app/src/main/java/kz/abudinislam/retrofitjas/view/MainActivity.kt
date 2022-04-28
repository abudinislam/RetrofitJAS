package kz.abudinislam.retrofitjas.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView
import kz.abudinislam.retrofitjas.R
import kz.abudinislam.retrofitjas.databinding.ActivityMainBinding
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        navController = findNavController(R.id.nav_host_fragment)
        initBottomNav()
        //initOnDestinationChangedListener()
    }

//    private fun initOnDestinationChangedListener() {
//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            when (destination.id) {
//                R.id.firstFragment,
//                R.id.secondFragment -> {
//                    binding.bottomNavigation.visibility = View.VISIBLE
//                }
//                R.id.blankFragment -> {
//                    binding.bottomNavigation.visibility = View.GONE
//                }
//            }
//        }
//    }

    private fun initBottomNav() {
        binding.bottomNavigation.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
        binding.bottomNavigation.setupWithNavController(navController)
    }


}