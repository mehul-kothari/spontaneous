package com.example.mehulkothari.spontaneous1;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by mehulkothari on 4/9/2017.
 */
public class MyServices extends Service {
    Handler handler;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),"service running in background",Toast.LENGTH_SHORT).show();
                handler.postDelayed(this,10000);
            }
        };
        //Toast.makeText(getApplicationContext(),"goingon1",Toast.LENGTH_SHORT).show();
        handler.postDelayed(runnable,5000);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed user logged out", Toast.LENGTH_LONG).show();
    }
}
