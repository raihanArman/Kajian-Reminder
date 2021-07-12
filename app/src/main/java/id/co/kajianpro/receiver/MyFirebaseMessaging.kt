package id.co.kajianpro.receiver

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessaging: FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val data = remoteMessage.data

        Log.d("Mantap", "onMessageReceived: ${ data["pesan"]}")

        val intent = Intent(this, TimeReceiver::class.java)
        intent.putExtra("pesan", data["pesan"])
        intent.action = "databaru"
        sendBroadcast(intent)
    }
}