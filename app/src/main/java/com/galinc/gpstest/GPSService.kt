package com.galinc.gpstest

import android.Manifest
import android.app.IntentService
import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.io.File
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.*
import java.io.FileOutputStream
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.io.FileWriter
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.io.OutputStreamWriter
import android.app.PendingIntent
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import javax.xml.datatype.DatatypeConstants.SECONDS
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.util.concurrent.TimeUnit
import android.os.IBinder


val LOG_TAG = "gps1"

class GPSService:IntentService("HelloIntentService"){
    val LOG_TAG = "gps1"
    val ONGOING_NOTIFICATION_ID = 1338
    private lateinit var listener: LocationListener
    private lateinit var locationManager: LocationManager
    private lateinit var file: File
    val handler = Handler()

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
                val msg = "Location changed: Lat: " + location.getLatitude() + " Lng: " + location.getLongitude()
                Log.d(LOG_TAG,msg)
                if (file.exists()){
                    val gpxfile = File(file, "gpslogs.txt")
                    val myOutWriter = OutputStreamWriter(FileOutputStream(gpxfile,true))
                    myOutWriter.use { myOutWriter.append(msg + "\n") }

                }
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


//        ActivityCompat.requestPermissions(,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 2)
//        if (checkSelfPermission("ACCESS_FINE_LOCATION") == PackageManager.PERMISSION_GRANTED)
        val path:File = Environment.getExternalStorageDirectory()
        file = File(path, "Download")
//        file.mkdir()
        Notification()
        //startForeground(ONGOING_NOTIFICATION_ID,Notification())

//        for (i in 1..100)
//        handler.postDelayed({
//            try {
//
//                locationManager.requestLocationUpdates(
//                    LocationManager.GPS_PROVIDER,
//                    1000,
//                    0f,
//                    listener)
//            }catch (e:SecurityException){}
//        }, 10000)


    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG,"Сервис остановлен")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        super.onStartCommand(intent, flags, startId)
        val run = NewRun()
        Thread(run).start()
        return Service.START_STICKY
    }

    //    override fun onBind(intent: Intent?): IBinder? {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//
//
//    }

    internal inner class NewRun:Runnable{

        override fun run() {
            val path:File = Environment.getExternalStorageDirectory()
            file = File(path, "Download")
//        file.mkdir()

            //startForeground(ONGOING_NOTIFICATION_ID,Notification())

            while (true){
                TimeUnit.SECONDS.sleep(10)
                handler.postDelayed({
                    Log.d(LOG_TAG, "Прошел цикл запроса")
                    try {

                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            100,
                            0f,
                            listener
                        )
                    } catch (e: SecurityException) {
                    }
                }, 2000)
            }
//                handler.postDelayed({
//                    Log.d(LOG_TAG,"Прошел цикл запроса")
//                    try {
//
//                        locationManager.requestLocationUpdates(
//                            LocationManager.GPS_PROVIDER,
//                            100,
//                            0f,
//                            listener)
//                    }catch (e:SecurityException){}
//                }, 10000)
        }

    }




}

class Service : IntentService(Service::class.java.simpleName) {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onHandleIntent(intent: Intent?) {
//        val intent1 = Intent("com.example.andy.myapplication")
//        intent1.putExtra("someName", "Tutorialspoint.com")
//        LocalBroadcastManager.getInstance(this).sendBroadcast(intent1)

        val file = File(Environment.getExternalStorageDirectory(), "Download")
        if (file.exists()){
            val gpxfile = File(file, "gpslogs1.txt")
            val myOutWriter = OutputStreamWriter(FileOutputStream(gpxfile,true))
            myOutWriter.use { myOutWriter.append("Сработал таймер" + "\n") }

        }
        Log.d(LOG_TAG, "Сработал таймер")
        if (shouldStop) {
            stopSelf()
            return
        }
    }

    companion object {
        @Volatile
        var shouldStop = false
    }
}


