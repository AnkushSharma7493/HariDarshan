package com.example.haridarshan.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.example.haridarshan.R;
import com.example.haridarshan.activities.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseNotificationService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "hd_channel_id";

    private Context appContext;

    public FirebaseNotificationService(){}

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d("FCM Token", "Device Token: " + token);
        // Send token to your server if needed
    }

    public FirebaseNotificationService(Context context) {
        this.appContext = context.getApplicationContext(); // Avoid leaking Activity context
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();

        sendNotification(title, message);
    }


    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("NotificationService","*********************************************");
            Log.d("NotificationService","context : "+appContext==null?"null":appContext.NOTIFICATION_SERVICE);
            Log.d("NotificationService","*********************************************");
            NotificationManager notificationManager = (NotificationManager) getSystemService(appContext.NOTIFICATION_SERVICE);
            Log.d("NotificationService","*********************************************111111111");
            // Check if the channel already exists
            NotificationChannel existingChannel = notificationManager.getNotificationChannel(CHANNEL_ID);
            if (existingChannel != null) {
                Log.d("NotificationChannel", "Channel already exists: " + CHANNEL_ID);
                return; // Exit if already exists
            }

            // Create a new notification channel
            CharSequence name = "General Notifications";
            String description = "Includes all general notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            notificationManager.createNotificationChannel(channel);
            Log.d("NotificationChannel", "Channel created: " + CHANNEL_ID);
        }
    }




    private void sendNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class); // The activity to open
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT // Reuse if it already exists
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID) // Use CHANNEL_ID
                .setSmallIcon(R.drawable.ic_book)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        notificationManager.notify(0, builder.build());
    }

}

