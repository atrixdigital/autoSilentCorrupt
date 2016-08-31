package com.example.meveloper.mapp;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.io.ObjectStreamField;

/**
 * Created by Meveloper on 8/5/2016.
 */
public class sms_service extends Service {

    BroadcastReceiver receiver;
    IntentFilter filter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                GetMsg(intent);
            }
        };

    }


    public void GetMsg(Intent intent)
    {
        Bundle bundle = intent.getExtras();

        SmsMessage smsMessage;

        if(bundle!=null)
        {
            if(Build.VERSION.SDK_INT>19)
            {
                SmsMessage[] sms = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                smsMessage = sms[0];
            }
            else
            {
                Object[] obj = (Object[])bundle.get("pdus");
                smsMessage = SmsMessage.createFromPdu((byte[])obj[0]);
            }

            String msg = smsMessage.getDisplayMessageBody();
            String num = smsMessage.getDisplayOriginatingAddress();
            ShowNotification(num,msg);
        }
    }


    public void ShowNotification(String phone,String msg)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Msg from"+phone);
        builder.setContentText(msg);
        builder.setTicker(msg);
        builder.setSmallIcon(R.drawable.icon1);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,builder.build());
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        registerReceiver(receiver,filter);
        return START_STICKY;

    }

    @Override
    public void onDestroy() {

        try
        {
            unregisterReceiver(receiver);
        }
        catch (Exception e)
        {

        }

    }




}
