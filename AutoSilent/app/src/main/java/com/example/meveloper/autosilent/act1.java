package com.example.meveloper.autosilent;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class act1 extends AppCompatActivity {

    Button mark,sho;
    dbhelper helper = new dbhelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act1);

        try {
            Cursor c = helper.GetLocations();
            if(c.getCount() == 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(act1.this);
                builder.setMessage("This application will turn your phone in silent mode whenever you are within a 20 meter radius of a location " +
                        "that you have marked. You will not be able to turn your phone's ringer on as long as you are in that location and the" +
                        " location is still in the marked list. You can Delete a marked location by the \"Show Marked Locations\" menu. If you" +
                        "agree to this, please continue using the application and if you disagree, you can press \"DISAGREE\" and this" +
                        "application will not be of any problem")
                        .setTitle("Disclaimer")
                        .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("DISAGREE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false);
                builder.create();
                builder.show();
            }
            else{

            }
        }
        catch (Exception e){
            Toast.makeText(act1.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

        LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE);
        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(act1.this, "Please turn on GPS for best use of services", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        mark = (Button) findViewById(R.id.curlock);
        sho = (Button) findViewById(R.id.show);

        mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act1.this,act2.class);
                startActivity(intent);
            }
        });

        sho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act1.this,act3.class);
                startActivity(intent);
            }
        });

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET
                },10);
                return;
            }
        }
        else{

            Intent in = new Intent(getApplicationContext(),autosilentservice.class);
            startService(in);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent inte = new Intent(getApplicationContext(),autosilentservice.class);
                    startService(inte);
                }
                return;
        }
    }
}
