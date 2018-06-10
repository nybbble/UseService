package com.gl.week11class;

import android.animation.IntArrayEvaluator;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class SomeService extends IntentService {
//public class SomeService extends Service{

    public final static String ACTION_DOWNLOAD ="Download";
    public final static String ACTION_TOAST="Toast";
    public final static String WAKE_RECEIVER="wakeitUp";

    public SomeService() {
        super("Dowloader");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    //public int onStartCommand(Intent intent, int flags, int startId) {
    protected void onHandleIntent(Intent intent){
        //return super.onStartCommand(intent, flags, startId);
        Log.d("ibrahim","Service Started");
        if(intent.getAction().equals(ACTION_TOAST)){
            //get URL
            String uri = intent.getStringExtra("url");
            Toast.makeText(this,uri,Toast.LENGTH_LONG).show();
        }
        else if(intent.getAction().equals(ACTION_DOWNLOAD)){
            try {
                Log.d("ibrahim", "Downloading something!");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(this,"UnknownAction",Toast.LENGTH_LONG).show();
        }

        Intent done  = new Intent();
        done.putExtra("info","Yes it is finished");
        done.setAction(WAKE_RECEIVER);
        sendBroadcast(done);

        //send notifications
        Intent in = new Intent(this,MainActivity.class);
        intent.putExtra("notif", "Not");
        PendingIntent pIntent = PendingIntent.getActivity(this,0,in,0);

        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("Dowlolader APP")
                .setContentText("Your download is finished")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true);

        Notification notification  = builder.build();
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(124,notification);


       //return START_STICKY;
    }
}
