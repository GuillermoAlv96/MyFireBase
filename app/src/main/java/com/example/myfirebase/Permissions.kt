package com.example.myfirebase

import android.Manifest
import android.content.Context
import androidx.fragment.app.Fragment
import com.vmadalin.easypermissions.EasyPermissions

object Permissions {

    //FINELOCATION permission OK
    fun hasLocationPermission(context: Context) =
        EasyPermissions.hasPermissions(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    //FINELOCATION permissions ASK
    fun requestLocationPermission(fragment: Fragment){
        EasyPermissions.requestPermissions(
            fragment,
            "This application cannot work without Location Permission",
            1,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }


}
