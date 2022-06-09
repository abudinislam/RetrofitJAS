package kz.abudinislam.retrofitjas.presentation.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import kz.abudinislam.retrofitjas.R
import kz.abudinislam.retrofitjas.databinding.ActivityMainBinding
import kz.abudinislam.retrofitjas.presentation.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private  val  viewModel by viewModel<MainActivityViewModel>()

    private lateinit var navController: NavController

    private lateinit var navView: NavigationView
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var toolbar: MaterialToolbar
    private lateinit var prefSettings: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        appBarLayout = binding.includeContent.appBarLayout
        toolbar = binding.includeContent.Toolbar
        val view = binding.root
        setContentView(view)
        prefSettings = this.getSharedPreferences(
            LoginFragment.APP_SETTINGS, Context.MODE_PRIVATE
        ) as SharedPreferences
        editor = prefSettings.edit()

        firebaseAnalytics = Firebase.analytics




        navController = findNavController(R.id.nav_host_fragment)
        init()
        initBottomNavigation()
        sideBarInit()
        setVisibilityToolbar()

    }



    private fun init() {

//        viewModel = ViewModelProvider(
//            this,
//            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//        )[MainActivityViewModel::class.java]

        navController = findNavController(R.id.nav_host_fragment)
        bottomNavigation = binding.includeContent.bottomNavigation
        appBarLayout = binding.includeContent.appBarLayout
        toolbar = binding.includeContent.Toolbar
        drawerLayout = binding.drawerActivityMain
        navView = binding.navView
    }


    private fun setVisibilityToolbar() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.moviesFragment -> {
                    bottomNavigation.visibility = View.VISIBLE
                    appBarLayout.visibility = View.VISIBLE
                }
                R.id.favoritesFragment -> {
                    bottomNavigation.visibility = View.VISIBLE
                    appBarLayout.visibility = View.VISIBLE
                }
                R.id.detailFragment -> {
                    bottomNavigation.visibility = View.GONE
                    appBarLayout.visibility = View.GONE
                }
                R.id.loginFragment -> {
                    bottomNavigation.visibility = View.GONE
                    appBarLayout.visibility = View.GONE
                }
            }
        }
    }


    private fun sideBarInit() {
        navView.setupWithNavController(navController)

        navView.menu.findItem(R.id.navigation_movies).isCheckable = false
        navView.setNavigationItemSelectedListener {

            drawerLayout.closeDrawers()
            it.isCheckable = false




            when (it.itemId) {
                R.id.navigation_login -> {
                    this.let {
                        AlertDialog
                            .Builder(it)
                            .setMessage("Выйти?")
                            .setPositiveButton("Да") { dialogInterface, i ->
                                viewModel.deleteSession()
                                navController.popBackStack()
                            }
                            .setNegativeButton("Нет") { dialogInterface, i -> }
                            .create()
                            .show()
                    }
                }
                else -> throw RuntimeException("Wrong id")
            }

            return@setNavigationItemSelectedListener true
        }
    }


    private fun initBottomNavigation() {
        binding.includeContent.bottomNavigation.labelVisibilityMode =
            NavigationBarView.LABEL_VISIBILITY_LABELED
        binding.includeContent.bottomNavigation.setupWithNavController(navController)


    }
}

