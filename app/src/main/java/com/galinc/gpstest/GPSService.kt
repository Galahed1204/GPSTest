package com.galinc.gpstest

import android.Manifest
import android.app.*
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
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
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import javax.xml.datatype.DatatypeConstants.SECONDS
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.util.concurrent.TimeUnit
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat.PRIORITY_HIGH
import androidx.core.app.NotificationCompat.PRIORITY_MAX


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
                val msg = "Location changed: Lat: " + location.latitude + " Lng: " + location.longitude
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_HIGH)
        chan.lightColor = Color.CYAN
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    override fun onHandleIntent(intent: Intent?) {



        val path:File = Environment.getExternalStorageDirectory()
        file = File(path, "Download")



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
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_service", "My GPS Service")
            } else {
                ""
            }

        val notificationBuilder = NotificationCompat.Builder(this, channelId )
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(PRIORITY_MAX)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()

        startForeground(1333,notification)
        //intent.extras[]
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


