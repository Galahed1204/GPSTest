package com.galinc.gpstest

import android.Manifest
import android.app.IntentService
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.Looper
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat.requestPermissions
import kotlinx.android.synthetic.main.activity_main.*


class GPSService:IntentService("HelloIntentService"){
    val LOG_TAG = "gps1"
    private lateinit var listener: LocationListener
    private lateinit var locationManager: LocationManager

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG,"Сервис запущен")
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        listener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
//                text_coordinate.setText(
//                    "Location changed: Lat: " + location.getLatitude() + " Lng: "
//                            + location.getLongitude()
//                )
                Log.d(LOG_TAG,"Location changed: Lat: " + location.getLatitude() + " Lng: "
                        + location.getLongitude())
            }

            override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {

            }

            override fun onProviderEnabled(s: String) {

            }

            override fun onProviderDisabled(s: String) {

            }
        }
    }

    override fun onHandleIntent(intent: Intent?) {


//        if (checkSelfPermission("ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED)
//        requestPermissions(applicationContext,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 2)
//        locationManager.requestLocationUpdates(
//            LocationManager.GPS_PROVIDER,
//            1000,
//            0f,
//            listener)
    }

//    override fun onBind(intent: Intent?): IBinder? {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//
//
//    }



}


