package com.example.vaccinestatuscheck;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class alarmService extends Service {

    MediaPlayer mediaPlayer;
    String channelId = "alarm_check";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int alarmId = intent.getExtras().getInt("alarmId");
        String friendId = intent.getExtras().getString("friendId");
        String getState = intent.getExtras().getString("state");
        Log.d("serviceID", String.valueOf(alarmId));
//        Intent alarmFunction = new Intent(this, AlarmFunctionActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent alarm = new Intent(this, AlarmFunctionActivity.class);
            alarm.putExtra("alarmId", alarmId);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntentWithParentStack(alarm);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(alarmId, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, channelId)
                    .setContentTitle("????????????")
                    .setContentIntent(pendingIntent)
                    .setContentText("???????????? ???????????????.")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();

            startForeground(alarmId, notification);

            mediaPlayer = MediaPlayer.create(this, R.raw.ouu);
            mediaPlayer.start();

            //30??? ????????? ??????

            //30??? ?????? ??? ?????? ?????? acitivty ?????? ??? ??? ????????? ??????
//            Intent push = new Intent(this, MessagePushActivity.class);
//            push.putExtra("friendId", friendId);
//            push.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(push);
            //Service??? ?????? ??????????????? ?????? ???????????? ?????? Service??? ????????? ?????? ????????? intent ?????? null??? ????????? ????????? ?????????

        };
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("onDestory() ??????", "????????? ??????");
        mediaPlayer.stop();
    }
}
