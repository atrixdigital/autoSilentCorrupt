package com.example.meveloper.autosilent;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * Created by Meveloper on 8/22/2016.
 */
public class autosilentservice extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    dbhelper helper = new dbhelper(this);

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final dbhelper helper = new dbhelper(this);
        Thread thread = new Thread() {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {

                @Override
                public void onLocationChanged(Location location) {
                    try {
                        Cursor c = helper.GetLocations();
                        if (c != null) {
                            if (c.moveToFirst()) {
                                do {
                                    String slat = c.getString(c.getColumnIndex(dbhelper.latitude));
                                    String slonn = c.getString(c.getColumnIndex(dbhelper.longitude));
                                    double lat = Double.valueOf(slat);
                                    double lonn = Double.valueOf(slonn);
                                    Location loc = new Location("");
                                    loc.setLatitude(lat);
                                    loc.setLongitude(lonn);

                                    float dist = loc.distanceTo(location);
                                    if (dist <= 20) {
                                        AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                                        audio.setRingerMode(0);
                                    }

                                } while (c.moveToNext());
                            }
                        }
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            };

            {
                locationManager.requestLocationUpdates("gps", 3000, 0, locationListener);
            }

        };
        thread.start();



        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
