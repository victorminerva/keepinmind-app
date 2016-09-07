package app.minervati.com.br.keepinmind.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.domain.Alarme;
import app.minervati.com.br.keepinmind.domain.IconReminderEnum;
import app.minervati.com.br.keepinmind.domain.InfoBasics;
import app.minervati.com.br.keepinmind.util.CalendarView;
import io.realm.Realm;
import io.realm.RealmResults;

public class CalendarFragmentAbstract extends Fragment {

    protected CalendarView      calendarView;
    protected TextView          tvTitle;
    protected TextView          tvSubtitle;
    protected TextView          tvDesc;

    protected Realm                     realm;
    protected RealmResults<InfoBasics>  realmInfoBasics;
    protected RealmResults<Alarme>      realmAlarms;

    protected InfoBasics                infoBasics;
    protected Calendar                  calendar;
    protected HashMap<Date, Integer>    events;
    protected SimpleDateFormat          sdf;
    protected String                    dataSelectedFormatada;
    protected Integer                   value;
    protected String                    titulo;
    protected String                    subtitulo;
    protected String                    descricao;

    protected void init(View view){
        calendarView    = ((CalendarView) view.findViewById(R.id.calendar_view));
        tvTitle         = (TextView) view.findViewById(R.id.tv_title);
        tvSubtitle      = (TextView) view.findViewById(R.id.tv_subtitle);
        tvDesc          = (TextView) view.findViewById(R.id.tv_desc);

        events          = new HashMap<>();

        realm           = Realm.getInstance(getActivity());
        realmInfoBasics = realm.where(InfoBasics.class).findAll();
        realmAlarms     = realm.where(Alarme.class).findAll();

        if (realmAlarms.size() > 0){
            realm.beginTransaction();
            RealmResults<Alarme> all = realmAlarms.where().findAll();
            all.clear();
            realm.commitTransaction();
        }

        infoBasics      = realmInfoBasics.where().equalTo("id", 1).findAll().get(0);

        calendar        = Calendar.getInstance();

        sdf                 = new SimpleDateFormat("dd MMMM yyyy");
        value               = 0;
        titulo              = "";
        subtitulo           = "";
        descricao           = "";
    }

    protected void addEventsDiasBaixoRisco() {
        for (int i=1; i < infoBasics.getDuracaoCiclo()-11; i++ ){
            calendar.add(Calendar.DATE, 1);
            events.put(calendar.getTime(), IconReminderEnum.DAY_LOW_RISK.getValue());
        }
    }

    protected void addEventsDiasMedioRisco() {
        for (int i=1; i <= 8; i++ ){
            calendar.add(Calendar.DATE, 1);
            events.put(calendar.getTime(), IconReminderEnum.DAY_MID_RISK.getValue());
        }
    }

    protected void addEventsDiasTPM() {
        for (int i=1; i <= 2; i++ ) {
            calendar.add(Calendar.DATE, 1);
            events.put(calendar.getTime(), IconReminderEnum.DAY_TPM.getValue());
        }
    }

    protected void addEventsDiasMenstruacao() {
        for (int i=1; i <= infoBasics.getQtdeDiasMenstru(); i++ ) {
            calendar.add(Calendar.DATE, 1);
            events.put(calendar.getTime(), IconReminderEnum.DAY_MENST.getValue());
        }
    }

    protected Integer checkDateSelected(SimpleDateFormat sdf, String dataFormatada, Integer value) {
        for(Date data : events.keySet()){
            if (dataFormatada.equalsIgnoreCase(sdf.format(data))){
                value = events.get(data);
            }
        }
        return value;
    }

    protected Alarme setAlarme(Date data, Integer tipoLembrete) {
        Alarme alarm = new Alarme();
        try{
            realm.beginTransaction();
            realmAlarms.sort("id", RealmResults.SORT_ORDER_DESCENDING);
            long id = realmAlarms.size() == 0 ? 1 : realmAlarms.get(0).getId() + 1;
            alarm.setId(id);
            alarm.setData(data);
            alarm.setTipoLembrete(tipoLembrete);

            realm.copyToRealmOrUpdate(alarm);
            realm.commitTransaction();
        }catch (Exception e){
            System.out.println("Erro ao inserir alarme!" + e.getMessage());
        }
        return alarm;
    }
}
