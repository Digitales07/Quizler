package com.assorttech.myquizler.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.provider.Settings;
import androidx.core.app.NotificationCompat;
import com.assorttech.myquizler.R;
import com.assorttech.myquizler.SplashScreen;

import java.util.Random;

public class Receiver extends BroadcastReceiver {
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    String userName = "Dear User";
    String title = "Don't give Up!";
    private String[] mMessages = new String[]{
            "Consistency is the key to get the most. Challenge your skills now!",
            "Stay true to yourself, yet always be open to learn. Come back and take a quiz today!",
            "There is no substitute for hard work. Never give up! We miss you today!",
            "Consistency leads to habits. Habits form the actions we take every day. Action leads to success. So take one quiz everyday!"
    };

    private int[] items = new int[]{0,1,2,3};

    private Random rand = new Random();

    public int getRandArrayElement(){
        return items[rand.nextInt(items.length)];
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        showNotification(context);
    }

    public void showNotification(Context context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = preferences.getString("Name", "");


        if (!name.equalsIgnoreCase("")) {
            userName = name;  /* Edit the value here*/
        } else {
            userName = "Dear User";
        }
        int messageid = getRandArrayElement();
// Create a notification and set the notification channel.
        Intent intent = new Intent(context, SplashScreen.class);
        PendingIntent pi = PendingIntent.getActivity(context, 271, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(title)
                .setContentText(userName+"! "+mMessages[messageid])
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(userName+"! "+mMessages[messageid]))
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(pi);
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "my_channel_07", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        } else {
            mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(userName+"! "+mMessages[messageid])
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(userName+"! "+mMessages[messageid]))
                    .setContentIntent(pi)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setAutoCancel(true);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(271, mBuilder.build());
    }
}
