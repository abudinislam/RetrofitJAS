package kz.abudinislam.retrofitjas.view.adapter.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import kz.abudinislam.retrofitjas.view.FavoritesFragment
import kz.abudinislam.retrofitjas.view.MoviesFragment

class PagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity)  {

    override fun getItemCount(): Int {
        return 2

    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> MoviesFragment()
            else -> FavoritesFragment()
        }

    }
}