package com.example.myfirebase.ui

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.myfirebase.Constants
import com.example.myfirebase.Permissions
import com.example.myfirebase.R
import com.example.myfirebase.databinding.FragmentLoginBinding
import com.example.myfirebase.databinding.FragmentMapsBinding
import com.example.myfirebase.firebase.FireStore
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.internal.OnConnectionFailedListener
import com.google.android.gms.location.*
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import java.util.concurrent.TimeUnit

class MapsFragment : Fragment(), EasyPermissions.PermissionCallbacks, OnConnectionFailedListener {

    private var mBinding: FragmentMapsBinding? = null
    private val binding get() = mBinding!!

    private lateinit var map: GoogleMap

    // Construct a FusedLocationProviderClient.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    override fun onStart() {
        super.onStart()
    }


    @SuppressLint("MissingPermission")
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
        map = googleMap ?: return@OnMapReadyCallback
        if (!::map.isInitialized) return@OnMapReadyCallback
        if (Permissions.hasLocationPermission(requireContext())) {

            map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(),
                    R.raw.uber_style
                )
            )
            //Enable my location
            map.isMyLocationEnabled = true
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

            locationUpdates()


        } else {
            Permissions.requestLocationPermission(this)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMapsBinding.inflate(inflater, container, false)
        binding.imageButton.setOnClickListener {
            transaction()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }


    /**
     * Begins transaction to next fragment
     */
    private fun transaction() {
        val fragment = UserHomeFragment()//Navigate to second
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.navHostFragment, fragment)?.commit()
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private fun getDeviceCurrentLocation() {
        try {
            fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Set the map's camera position to the current location of the device.
                    var lastKnownLocation = task.result
                    if (lastKnownLocation != null) {
                        map.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude
                                ), Constants.DEFAULT_ZOOM.toFloat()
                            )
                        )
                        val urlDirections =
                            "https://maps.googleapis.com/maps/api/geocode/json?latlng=${lastKnownLocation?.latitude},${lastKnownLocation?.longitude}&key=${Constants.APIKEY}"
                        FireStore().updateLocationUser(urlDirections)

                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    @SuppressLint("MissingPermission")
    private fun locationUpdates() {
        // LocationRequest - Requirements for the location updates, i.e., how often you
        // should receive updates, the priority, etc.
        var locationRequest: LocationRequest = LocationRequest.create().apply {
            // Sets the desired interval for active location updates. This interval is inexact. You
            // may not receive updates at all if no location sources are available, or you may
            // receive them less frequently than requested. You may also receive updates more
            // frequently than requested if other applications are requesting location at a more
            // frequent interval.
            interval = TimeUnit.SECONDS.toMillis(60)

            // Sets the fastest rate for active location updates. This interval is exact, and your
            // application will never receive updates more frequently than this value.
            fastestInterval = TimeUnit.SECONDS.toMillis(30)

            // Sets the maximum time when batched location updates are delivered. Updates may be
            // delivered sooner than this interval.
            maxWaitTime = TimeUnit.MINUTES.toMillis(2)

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        // LocationCallback - Called when FusedLocationProviderClient has a new Location.
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                getDeviceCurrentLocation()
                Toast.makeText(
                    requireContext(),
                    "Location Updated",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)


    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {

        if (EasyPermissions.permissionPermanentlyDenied(
                this,
                perms.first()
            ) || EasyPermissions.somePermissionDenied(this, perms.first())
        ) {
            SettingsDialog.Builder(requireActivity()).build().show()
        } else {
            Permissions.requestLocationPermission(this)
        }

    }

    @SuppressLint("MissingPermission")
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        if (Permissions.hasLocationPermission(requireContext())) {

            map.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(),
                    R.raw.uber_style
                )
            )
            //Enable my location
            map.isMyLocationEnabled = true
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
            getDeviceCurrentLocation()


        }
        Toast.makeText(requireContext(), "Permissions Granted", Toast.LENGTH_SHORT).show()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }


}