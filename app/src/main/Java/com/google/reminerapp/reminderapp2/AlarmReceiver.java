package com.google.reminderapp.reminderapp2;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

import Model.ReminderApp2;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneNumberReceiver=intent.getStringExtra("PHONENUMBER");
        if(phoneNumberReceiver == null) {
            phoneNumberReceiver = "6464929531"; // default: send to myself
        }
        String message="YOUR ASSIGNMENT IS DUE IN 12 HOURS!";
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumberReceiver,null,message,null,null);
        Toast.makeText(context,"Reminder sent",Toast.LENGTH_LONG);

        // Notification
        PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(), 0);
        NotificationCompat.Builder mBuilder =
                //new NotificationCompat.Builder(this)
                new NotificationCompat.Builder(context)
                        .setContentTitle("ReminderApp")
                        .setContentText(message)
                        .setContentIntent(pi)
                        .setSound(Uri.parse(ReminderApp2.getRingtone()))
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.build();
    }
}
