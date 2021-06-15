package com.example.simplibuy.fragment

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.media.MediaPlayer
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.simplibuy.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import java.lang.Exception

class MapsFragment : Fragment(),
    OnMapReadyCallback,
    LocationListener,
    GoogleMap.OnInfoWindowClickListener,
    GoogleMap.OnMarkerClickListener
{


    private lateinit var mMap: GoogleMap
    private  var latlng: LatLng = LatLng(32.697501,74.868709)
    private var fusedLocationProviderClient:FusedLocationProviderClient? = null
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
with(mMap){
   // setOnMarkerClickListener(this@MapsFragment)
}



        // Add a marker in Sydney and move the camera
        val home = LatLng(32.697501,74.879044)
        val dummyOne = LatLng(32.699819,74.859414)
        val dummyTwo = LatLng(31.697501,74.868709)
        val jammu = mMap.addMarker(MarkerOptions()
            .position(home)
            .snippet("Tap to get direction")
            .title("Retailer"))
        val melbourne = mMap.addMarker(MarkerOptions()
            .position(dummyOne)
            .snippet("Tap to get direction")
            .title("Super Market"))
        mMap.addMarker(MarkerOptions()
            .position(dummyTwo)
            .title("Marker in dummyTwo"))
         val current = mMap.addMarker(MarkerOptions()
            .position(latlng)
            .title("Marker in current location")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )
        current.showInfoWindow()
        jammu.showInfoWindow()
        melbourne.showInfoWindow()
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng,14f))
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
        mapFragment?.getMapAsync(this)
    }

    override fun onLocationChanged(location: Location) {
        var loc  = location

        latlng = LatLng(location.latitude, location.longitude)

        val markerOptions = MarkerOptions()
        markerOptions.position(latlng)
        markerOptions.title("Current Position")
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        mMap.addMarker(markerOptions)

        //move map camera
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))
        //Map.animateCamera(CameraUpdateFactory.zoomTo(11f))
    }

   override fun onInfoWindowClick(p0: Marker?) {
        //view?.let { Navigation.findNavController(it).navigate(R.id.action_mapsFragment_to_superMarketFragment) }
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        mMap.setOnMarkerClickListener {
            view?.let { Navigation.findNavController(it).navigate(R.id.action_mapsFragment_to_infoFragment) }
            true
        }

        return true
    }

}
