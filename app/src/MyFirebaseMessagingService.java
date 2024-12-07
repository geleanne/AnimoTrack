package com.mobdeve.xx22.rivera.josecarlos.animotrack;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingService";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        // Check if the message contains a notification payload
        if (remoteMessage.getNotification() != null) {
            // Handle FCM notification
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            // Show Toast for debugging
            Toast.makeText(getApplicationContext(), "Message Received: " + title, Toast.LENGTH_SHORT).show();

            // Display the notification
            sendNotification(title, body);
        }

        // Handle data payload (if any)
        if (remoteMessage.getData().size() > 0) {
            // You can parse the data payload if needed
            String dataMessage = remoteMessage.getData().get("message");
            Log.d(TAG, "Data Payload: " + dataMessage);
        }
    }

    private void sendNotification(String title, String body) {
        // Create a Notification and display it
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "default_channel";
            CharSequence channelName = "Default Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(this, "default_channel")
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.default_poster_squared)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(0, notification);
    }
    private void showNotification(String title, String body) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.default_poster_squared)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void saveNotificationToFirestore(String title, String body) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Create a new notification document
            Map<String, Object> notification = new HashMap<>();
            notification.put("title", title);
            notification.put("body", body);
            notification.put("timestamp", FieldValue.serverTimestamp());

            db.collection("notifications")
                    .document(userId)
                    .collection("userNotifications")
                    .add(notification)
                    .addOnSuccessListener(documentReference -> {
                        Log.d(TAG, "Notification saved to Firestore");
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error saving notification", e);
                    });
        }
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
