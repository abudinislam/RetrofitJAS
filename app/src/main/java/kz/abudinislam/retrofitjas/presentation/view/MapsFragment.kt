package kz.abudinislam.retrofitjas.presentation.view

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kz.abudinislam.retrofitjas.R

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        val cinemas = mutableMapOf<String,LatLng>()

        cinemas ["KinoPark 16 Forum"] = LatLng(43.234286124171255, 76.93583294745744)
        cinemas ["KinoPark Atakent"] = LatLng(43.225516969069226, 76.90908059972396)

            for (cinema in cinemas.keys){
                googleMap.addMarker(MarkerOptions().position(cinemas[cinema]!!).title(cinema))
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(cinemas[cinema]!!))
            }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cinemas["KinoPark 16 Forum"]!!,15f))
    }
    

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}