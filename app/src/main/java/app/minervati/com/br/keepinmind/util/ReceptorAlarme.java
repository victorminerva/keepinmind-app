package app.minervati.com.br.keepinmind.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.activity.ReminderActionActivity;
import app.minervati.com.br.keepinmind.domain.Alarme;
import app.minervati.com.br.keepinmind.domain.IconReminderEnum;
import app.minervati.com.br.keepinmind.domain.InfoBasics;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by victorminerva on 24/08/2016.
 */
public class ReceptorAlarme extends BroadcastReceiver {

    int MID = 0;

    protected InfoBasics                infoBasics;
    protected Realm                     realm;
    protected RealmResults<InfoBasics>  realmInfoBasics;
    protected RealmResults<Alarme>      realmAlarms;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(KeepConstants.DEBUG, "Enter into method onReceive to ReceptorAlarme");

        init(context);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        Boolean hasAlarm = false;
        hasAlarm = hasAlarmForTodayDate(calendar, hasAlarm);

        if(hasAlarm) {
            Long when = System.currentTimeMillis();

            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            Intent notificationIntent = new Intent(context, ReminderActionActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(context);
            mNotifyBuilder.setSmallIcon(R.drawable.ic_tab_clock_alert);
            mNotifyBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_cartela_pill));
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
        Log.d(KeepConstants.DEBUG, "Exiting into method onReceive to ReceptorAlarme");
    }

    private Boolean hasAlarmForTodayDate(Calendar calendar, Boolean hasAlarm) {
        Date min = new Date();
        Date max = new Date();
        for (Alarme alarme : realmAlarms){
            if (alarme.getTipoLembrete().equals(IconReminderEnum.DAY_START.getValue())){
                min = alarme.getData();
                continue;
            }
            if (alarme.getTipoLembrete().equals(IconReminderEnum.DAY_END.getValue())){
                max = alarme.getData();
            }
            hasAlarm = calendar.getTime().after(min) && calendar.getTime().before(max);
            if (hasAlarm)
                break;
        }
        return hasAlarm;
    }

    private void init(Context context) {
        realm           = Realm.getInstance(context);
        realmInfoBasics = realm.where(InfoBasics.class).findAll();
        realmAlarms     = realm.where(Alarme.class).findAll();

        infoBasics      = realmInfoBasics.where().equalTo("id", 1).findAll().get(0);
    }
}
