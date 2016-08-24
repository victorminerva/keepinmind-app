package app.minervati.com.br.keepinmind.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import app.minervati.com.br.keepinmind.domain.InfoBasics;
import app.minervati.com.br.keepinmind.domain.Lembrete;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by victorminerva on 24/08/2016.
 */
public class ReceptorBoot extends BroadcastReceiver {

    protected static Lembrete                  lembrete;
    protected static InfoBasics                infoBasics;
    protected static Realm                     realm;
    protected static RealmResults<Lembrete>    realmLembretes;
    protected static RealmResults<InfoBasics>  realmInfoBasics;

    @Override
    public void onReceive(Context context, Intent intent) {
        init(context);

        configurarAlarme(context);
    }

    public static void configurarAlarme(Context context) {
        init(context);

        AlarmManager gerenciador = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, lembrete.getHora());
        cal.set(Calendar.MINUTE, lembrete.getMinuto());
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        if (cal.getTimeInMillis() < System.currentTimeMillis()) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }

        gerenciador.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, obterIntentPendente(context));
    }

    private static void init(Context context) {
        realm           = Realm.getInstance(context);
        realmLembretes  = realm.where(Lembrete.class).findAll();
        realmInfoBasics = realm.where(InfoBasics.class).findAll();
        infoBasics      = realmInfoBasics.where().equalTo("id", 1).findAll().get(0);

        lembrete        = new Lembrete();
        if (realmLembretes.size() > 0)
            lembrete        = realmLembretes.where().equalTo("id", 1).findAll().get(0);
    }

    public static void cancelarAlarme(Context contexto) {
        AlarmManager gerenciador = (AlarmManager) contexto.getSystemService(Context.ALARM_SERVICE);
        gerenciador.cancel(obterIntentPendente(contexto));
    }

    private static PendingIntent obterIntentPendente(Context contexto) {
        Intent i = new Intent(contexto, ReceptorAlarme.class);
        return PendingIntent.getBroadcast(contexto, 0, i, 0);
    }
}
