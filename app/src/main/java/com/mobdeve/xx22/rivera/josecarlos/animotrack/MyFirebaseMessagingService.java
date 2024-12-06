package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        showNotification(title, body);
    }

    private void showNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "animo_notifications";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "AnimoTrack Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.notif_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        notificationManager.notify(0, builder.build());
    }
}

//package com.mobdeve.xx22.rivera.josecarlos.animotrack;
//
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import android.util.Log;
//import android.widget.Toast;
//
//public class MyFirebaseMessagingService extends FirebaseMessagingService {
//    private static final String TAG = "FCMService";
//
//    @Override
//    public void onNewToken(String token) {
//        super.onNewToken(token);
//        Log.d(TAG, "New token: " + token);
//        sendRegistrationToServer(token);
//    }
//
//    private void sendRegistrationToServer(String token) {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            db.collection("AnimoTrackUsers")
//                    .document(user.getUid())
//                    .update("fcm_token", token) // Store the token in Firestore
//                    .addOnSuccessListener(aVoid -> {
//                        Log.d(TAG, "FCM token saved");
//                        Toast.makeText(getApplicationContext(), "FCM token saved: " + token, Toast.LENGTH_SHORT).show();
//                    })
//                    .addOnFailureListener(e -> {
//                        Log.w(TAG, "Error saving FCM token", e);
//                        Toast.makeText(getApplicationContext(), "Error saving FCM token", Toast.LENGTH_SHORT).show();
//                    });
//        }
//    }
//}
