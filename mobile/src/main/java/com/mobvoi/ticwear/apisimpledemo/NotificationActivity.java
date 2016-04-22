package com.mobvoi.ticwear.apisimpledemo;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.Button;

/**
 * Created by pd on 16-4-20.
 */
public class NotificationActivity extends Activity implements View.OnClickListener{

    private String EXTRA_EVENT_ID = "EXTRA_EVENT_ID";
    private int eventId = 1;
    private String eventTitle = "eventTitle";
    private String eventLocation = "eventLocation";

    private Button notify = null;

    private int notificationId = 001;
    private NotificationCompat.Builder notificationBuilder = null;
    private NotificationManagerCompat notificationManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notify = (Button) findViewById(R.id.notify);
        notify.setOnClickListener(this);

        Intent viewIntent = new Intent(this,DataTransferActivity.class);
        viewIntent.putExtra(EXTRA_EVENT_ID,eventId);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(this,0,viewIntent,0);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(eventTitle)
                .setContentText(eventLocation)
                .setContentIntent(viewPendingIntent);

        notificationManager = NotificationManagerCompat.from(this);
    }


    @Override
    public void onClick(View v){
        notificationManager.notify(notificationId,notificationBuilder.build());
    }
}
