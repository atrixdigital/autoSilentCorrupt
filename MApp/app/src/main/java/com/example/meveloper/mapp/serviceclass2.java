package com.example.meveloper.mapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Meveloper on 8/5/2016.
 */
public class serviceclass2 extends Service{

    MediaRecorder mediaRecorder;
    String outputfile;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC_ELD);
        mediaRecorder.setOutputFile(Environment.getExternalStorageDirectory()+"asd.mp3");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaRecorder.start();
        Toast.makeText(serviceclass2.this, "Recording Started", Toast.LENGTH_SHORT).show();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mediaRecorder.stop();
    }
}
