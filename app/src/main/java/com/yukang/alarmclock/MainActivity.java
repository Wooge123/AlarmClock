package com.yukang.alarmclock;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    private TimePicker alarmTimePicker;
    private static MainActivity inst;
    private TextView alarmTextView;
    Spinner spinner;
    int rechard_quote = 0;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alarmTextView = (TextView) findViewById(R.id.alarmTextView);

        final Intent myIntent = new Intent(this, AlarmReceiver.class);

        // Get the alarm manager service
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // set the alarm to the time that you picked
        final Calendar calendar = Calendar.getInstance();
        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);

        // spinner creation
        Spinner spinner = (Spinner) findViewById(R.id.richard_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dawkins_sounds, android.R.layout.simple_spinner_dropdown_item);
        // Apple the adapter to the spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        Button startAlarm = (Button) findViewById(R.id.start_alarm);

        startAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour;
                int minute;

                int currentApiVersion = Build.VERSION.SDK_INT;
                if (currentApiVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    hour = alarmTimePicker.getHour();
                    minute = alarmTimePicker.getMinute();
                } else {
                    hour = alarmTimePicker.getCurrentHour();
                    minute = alarmTimePicker.getCurrentMinute();
                }

                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                if (minute < 10) minute_string = "0" + minute;
                if (hour > 12) hour_string = String.valueOf(hour - 12);

                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);

                myIntent.putExtra("extra", "yes");
                myIntent.putExtra("quote id", String.valueOf(rechard_quote));
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                setAlarmText("Alarm set to " + hour_string + ":" + minute_string);
            }
        });

        Button stopAlarm = (Button) findViewById(R.id.stop_alarm);
        stopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myIntent.putExtra("extra", "no");
                myIntent.putExtra("quote id", String.valueOf(rechard_quote));
                sendBroadcast(myIntent);

                alarmManager.cancel(pendingIntent);
                setAlarmText("Alarm canceled");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MyActivity", "on Destroy");
    }

    private void setAlarmText(String alarmText) {
        alarmTextView.setText(alarmText);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        rechard_quote = (int) id;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        
    }
}
