package app.minervati.com.br.keepinmind.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.activity.ReminderActionActivity;

public class NotifyReceiver extends BroadcastReceiver {

    int MID = 0;

    @Override
    public void onReceive(Context context, Intent intent) {

        Long when = System.currentTimeMillis();

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, ReminderActionActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context);
        mNotifyBuilder.setSmallIcon(R.drawable.ic_tab_clock_alert);
        mNotifyBuilder.setTicker("Alerta diária do medicamento.");
        mNotifyBuilder.setContentTitle("Opa! Hora de lembrar sua parceira.");
        mNotifyBuilder.setContentText("Notifique-a, não deixe para depois!");
        mNotifyBuilder.setSound(alarmSound);
        mNotifyBuilder.setAutoCancel(true);
        mNotifyBuilder.setWhen(when);
        mNotifyBuilder.setContentIntent(pendingIntent);
        mNotifyBuilder.setVibrate(new long[]{150, 1000, 150, 1000});

        notificationManager.notify(MID, mNotifyBuilder.build());

        MID++;
    }
}