package com.example.firebaserealtimeandmessageingandroid.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.firebaserealtimeandmessageingandroid.MainActivity
import com.example.firebaserealtimeandmessageingandroid.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
class Messaging : FirebaseMessagingService() {

    companion object {
        private const val CHANNEL_ID = "my_channel"
        private const val NOTIFICATION_REQUEST_CODE = 0

        private const val mTAG = "_MessagingService"

        var sharedPreferences: SharedPreferences? = null
        var mToken: String?
            set(value) {
                sharedPreferences?.edit()?.putString("", value)?.apply()
            }
            get() {
                return sharedPreferences?.getString("token", "")
            }
    }
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d(mTAG, "onMessageReceived: from => ${message.from}")
        Log.d(mTAG, "onMessageReceived: data => ${message.data}")

        if (message.notification != null) {
            Log.d(mTAG, "onMessageReceived: title => ${message.notification?.title}")
            Log.d(mTAG, "onMessageReceived: body => ${message.notification?.body}")

            val intent = Intent(this, MainActivity::class.java)
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationID = Random.nextInt()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createChannel(notificationManager = notificationManager)
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(
                this,
                NOTIFICATION_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(message.notification?.title)
                .setContentText(message.notification?.body)
                .setSmallIcon(R.drawable.ic_baseline_notifications)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

            notificationManager.notify(notificationID, notification)
        }


        if (message.data.isNotEmpty()) {
            val intent = Intent(this, MainActivity::class.java)
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationID = Random.nextInt()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createChannel(notificationManager = notificationManager)
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(
                this,
                NOTIFICATION_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(message.data["title"])
                .setContentText(message.data["body"])
                .setSmallIcon(R.drawable.ic_baseline_notifications)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

            notificationManager.notify(notificationID, notification)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(mTAG, "onNewToken: $token")

        mToken = token
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(notificationManager: NotificationManager) {
        val channelName = "Channel Name"
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            this.description = "My channel description"
            this.enableLights(true)
            this.lightColor = Color.GREEN
        }

        notificationManager.createNotificationChannel(notificationChannel)
    }
}