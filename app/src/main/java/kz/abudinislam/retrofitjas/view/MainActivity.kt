package kz.abudinislam.retrofitjas.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import kz.abudinislam.retrofitjas.R
import kz.abudinislam.retrofitjas.databinding.ActivityMainBinding
import kz.abudinislam.retrofitjas.view.adapter.pager.PagerAdapter
import kz.abudinislam.retrofitjas.viewmodel.MainActivityViewModel
import kz.abudinislam.retrofitjas.viewmodel.MoviesViewModel
import kz.abudinislam.retrofitjas.viewmodel.ViewModelProviderFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    private lateinit var navController: NavController

    private lateinit var navView: NavigationView
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var toolbarLayout: AppBarLayout
    private lateinit var toolbar: MaterialToolbar
    private lateinit var prefSettings: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        toolbarLayout = binding.includeContent.toolbarLayout
        toolbar = binding.includeContent.topToolbar
        val view = binding.root
        setContentView(view)
        prefSettings = this.getSharedPreferences(
            LoginFragment.APP_SETTINGS, Context.MODE_PRIVATE
        ) as SharedPreferences
        editor = prefSettings.edit()


        navController = findNavController(R.id.nav_host_fragment)
        init()
        initBottomNav()
        sideBarInit()



    }
    private fun init() {

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MainActivityViewModel::class.java]

        navController = findNavController(R.id.nav_host_fragment)
        bottomNavigation = binding.includeContent.bottomNavigation
        toolbarLayout = binding.includeContent.toolbarLayout
        toolbar = binding.includeContent.topToolbar
        drawerLayout = binding.drawerActivityMain
        navView = binding.navView
    }


    private fun sideBarInit() {

        setSupportActionBar(toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_movies,
                R.id.navigation_favourites,
                R.id.navigation_login,
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.menu.findItem(R.id.navigation_movies).isCheckable = false
        navView.setNavigationItemSelectedListener {

            drawerLayout.closeDrawers()
            it.isCheckable = false

            when (it.itemId) {
//                R.id.navigation_movies -> {
//                    val current = findCurrentFragmentId()
//                    if (current != R.id.moviesFragment) {
//                        //navController.popBackStack(R.id.loginFragment, false)
//                        navController.navigate(R.id.navigation_movies)
//                    }
//                }
//                R.id.navigation_favourites -> {
//                    val current = findCurrentFragmentId()
//                    if (current != R.id.favoritesFragment) {
//                        //navController.popBackStack(R.id.loginFragment, false)
//                        navController.navigate(R.id.navigation_favourites)
//                    }
//                }

                R.id.navigation_login -> {
                    this.let {
                        AlertDialog
                            .Builder(it)
                            .setMessage("Выйти?")
                            .setPositiveButton("Да") { dialogInterface, i ->
                                viewModel.deleteSession()
                                //navController.popBackStack(R.id.loginFragment, false)
                                navController.navigate(R.id.navigation_login)
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

    private fun findCurrentFragmentId(): Int {
        return navController.currentDestination?.id as Int
    }

    private fun initBottomNav() {
        binding.includeContent.bottomNavigation.labelVisibilityMode =
            NavigationBarView.LABEL_VISIBILITY_LABELED
        binding.includeContent.bottomNavigation.setupWithNavController(navController)
//        bottomNavigation.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.navigation_movies -> {
//                    val current = findCurrentFragmentId()
//                    if (current != R.id.moviesFragment) {
//                        navController.popBackStack(R.id.loginFragment, false)
//                        navController.navigate(R.id.navigation_movies)
//                    }
//                }
//                R.id.navigation_favourites -> {
//                    val current = findCurrentFragmentId()
//                    if (current != R.id.favoritesFragment) {
//                        navController.popBackStack(R.id.loginFragment, false)
//                        navController.navigate(R.id.navigation_favourites)
//                    }
//                }
//            }
//            return@setOnItemSelectedListener true
//        }
//    }

    }
}
