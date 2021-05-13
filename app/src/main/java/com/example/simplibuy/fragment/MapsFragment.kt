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
import com.example.simplibuy.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import java.lang.Exception

class MapsFragment : Fragment(),OnMapReadyCallback,LocationListener{


    private lateinit var mMap: GoogleMap
    private  var latlng: LatLng = LatLng(32.697501,74.868709)
    private var fusedLocationProviderClient:FusedLocationProviderClient? = null
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        // Add a marker in Sydney and move the camera
        val home = LatLng(32.697501,74.868709)
        val dummyOne = LatLng(32.699819,74.869414)
        val dummyTwo = LatLng(31.697501,74.868709)
        /*mMap.addMarker(MarkerOptions()
            .position(home)
            .title("Marker in Home"))*/
        mMap.addMarker(MarkerOptions()
            .position(dummyOne)
            .title("Marker in dummyOne"))
        mMap.addMarker(MarkerOptions()
            .position(dummyTwo)
            .title("Marker in dummyTwo"))
        mMap.addMarker(MarkerOptions()
            .position(latlng)
            .title("Marker in current location")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )
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

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(){
        fusedLocationProviderClient = activity?.let{
            LocationServices.getFusedLocationProviderClient(
                it
            )
        }
        try {
            val location = fusedLocationProviderClient!!.getLastLocation()

            location.addOnCompleteListener(object: OnCompleteListener<Location> {
                override fun onComplete(loc: Task<Location>) {
                    if (loc.isSuccessful){
                        val currentLocation = loc.result as Location?
                        if (currentLocation!=null)
                        {


                        }
                    }
                }

            })
        } catch (e:Exception)
        {

        }
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
}