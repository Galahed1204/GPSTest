package com.galinc.gpstest

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_main.*
import android.location.LocationListener

import android.location.Location
import android.os.Build
import androidx.core.app.ActivityCompat

import android.util.Log
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE

import android.os.Handler
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var listener: LocationListener
    private lateinit var locationManager: LocationManager
    val LOG_TAG = "gps1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        applicationContext.filesDir
        requestPermissions(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET,
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE
        ), 5)

//        requestPermissions(arrayOf(READ_EXTERNAL_STORAGE), 2)
//        requestPermissions(arrayOf(WRITE_EXTERNAL_STORAGE), 2)


        listener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                text_coordinate.setText(
                    "Location changed: Lat: " + location.getLatitude() + " Lng: "
                            + location.getLongitude()

                )
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
        configureButton()
    }

    fun configureButton() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET
                    ), 10
                )
            }
            return
        }

        button.setOnClickListener {
//            locationManager.requestLocationUpdates(
//                LocationManager.GPS_PROVIDER,
//                5000,
//                1f,
//                listener
//            )
            startService(Intent(this,GPSService::class.java))
        }

        button2.setOnClickListener {
            val handler = Handler()
            val timertask = object : TimerTask() {
                override fun run() {
                    handler.post(){
                        startService(
                            Intent(
                                this@MainActivity,
                                Service::class.java
                            )
                        )
                    }
                }
            }
            val timer = Timer()
            timer.schedule(timertask, 0, 10000)
        }
    }
}


