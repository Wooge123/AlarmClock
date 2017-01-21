package com.yukang.alarmclock;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

/**
 * Created by yukang on 17-1-21.
 */
public class RingtonePlayingService extends Service {

    private boolean isRunning;
    private Context context;
    MediaPlayer mediaPlayer;
    private int startId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("MyActivity", "In the Richard service");
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        Notification notification = new Notification.Builder(this).setContentTitle("Richard Dawkins is talking" + "!").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).setAutoCancel(true).build();

        String state = intent.getExtras().getString("extra");

        Log.e("what is going on here ", state);

        assert state != null;
        switch (state) {
            case "no":
                startId = 0;
                break;
            case "yes":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }

        // get richard's thing
        String richard_id = intent.getExtras().getString("quote id");
        Log.e("Service: richard id is ", richard_id);

        if (!isRunning && startId == 1) {
            Log.e("if there was not sound", " and you want start");

            if (richard_id.equals("0")) {
                int min = 1;
                int max = 9;

                Random r = new Random();
                int random_number = r.nextInt(max - min + 1) + min;
                Log.e("random number is ", String.valueOf(random_number));

                if (random_number == 1)
                    mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_1);
                else if (random_number == 2)
                    mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_2);
                else if (random_number == 3)
                    mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_3);
                else if (random_number == 4)
                    mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_4);
                else if (random_number == 5)
                    mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_5);
                else if (random_number == 6)
                    mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_6);
                else if (random_number == 7)
                    mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_7);
                else if (random_number == 8)
                    mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_8);
                else if (random_number == 9)
                    mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_9);
                else
                    mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_1);
            } else if (richard_id.equals("1"))
                mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_1);
            else if (richard_id.equals("2"))
                mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_2);
            else if (richard_id.equals("3"))
                mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_3);
            else if (richard_id.equals("4"))
                mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_4);
            else if (richard_id.equals("5"))
                mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_5);
            else if (richard_id.equals("6"))
                mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_6);
            else if (richard_id.equals("7"))
                mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_7);
            else if (richard_id.equals("8"))
                mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_8);
            else if (richard_id.equals("9"))
                mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_9);
            else
                mediaPlayer = MediaPlayer.create(this, R.raw.richard_dawkins_1);
            
            mediaPlayer.start();
            mediaPlayer.setLooping(true);

            manager.notify(0, notification);

            this.isRunning = true;
            this.startId = 0;
        } else if (!isRunning && startId == 0) {
            Log.e("if there is not sound ", "and you want end");
            this.isRunning = false;
            this.startId = 0;

        } else if (isRunning && startId == 1) {
            Log.e("if there is sound ", " and you want start");

            this.isRunning = true;
            this.startId = 0;
        } else {
            Log.e("if there is sound ", " and you want end");

            mediaPlayer.stop();
            mediaPlayer.reset();

            this.isRunning = false;
            this.startId = 0;
        }

        Log.e("MyActivity", "In the service");

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e("JSLog", "on destroy called");
        super.onDestroy();

        this.isRunning = false;
    }
}
