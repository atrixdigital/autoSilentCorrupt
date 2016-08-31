package com.example.meveloper.autosilent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;

public class act2 extends AppCompatActivity {

    EditText locname;
    Button done;
    TextView lat;
    TextView lon;

    LocationManager locationManager;
    LocationListener locationListener;

    dbhelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act2);

        locname = (EditText) findViewById(R.id.location);
        done = (Button) findViewById(R.id.saveLocation);
        lat = (TextView) findViewById(R.id.latitude);
        lon = (TextView) findViewById(R.id.longitude);

        helper = new dbhelper(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        locationListener = new LocationListener() {

            String lti;
            String lngi;
            @Override
            public void onLocationChanged(Location location) {
                lti = location.getLatitude()+"";
                lngi = location.getLongitude()+"";
                if(lat.getText().toString().isEmpty()){
                    lat.append(lti);
                }
                if(lon.getText().toString().isEmpty()){
                    lon.append(lngi);
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
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        //locationManager.requestLocationUpdates("gps", 3000, 0, locationListener);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if (locname.getText().toString().isEmpty() || lat.getText().toString().isEmpty() || lon.getText().toString().isEmpty()) {
                        if(locname.getText().toString().isEmpty()) {
                            Toast.makeText(act2.this, "Please enter location name.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(act2.this, "Please wait while GPS gets location coordinates.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        String name = locname.getText().toString();
                        String latitu = lat.getText().toString();
                        String longitu =  lon.getText().toString();

                        boolean b = helper.insert(name,latitu,longitu);
                        if (!b) {
                            Toast.makeText(act2.this, "There was some problem saving the locations", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(act2.this, "Location saved successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(act2.this,act3.class);
                            finish();
                            startActivity(intent);
                        }
                    }
                }
                catch (Exception e){
                    Toast.makeText(act2.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
