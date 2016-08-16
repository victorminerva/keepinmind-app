package app.minervati.com.br.keepinmind.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.domain.IconReminderEnum;
import app.minervati.com.br.keepinmind.domain.InfoBasics;
import app.minervati.com.br.keepinmind.util.CalendarView;
import io.realm.Realm;
import io.realm.RealmResults;

public class CalendarFragmentAbstract extends Fragment {

    protected CalendarView      calendarView;

    protected Realm                     realm;
    protected RealmResults<InfoBasics>  realmInfoBasics;

    protected InfoBasics                infoBasics;
    protected Calendar                  calendar;
    protected HashMap<Date, Integer>    events;

    protected void init(View view){
        calendarView    = ((CalendarView) view.findViewById(R.id.calendar_view));
        events          = new HashMap<>();

        realm           = Realm.getInstance(getActivity());
        realmInfoBasics = realm.where(InfoBasics.class).findAll();

        infoBasics      = realmInfoBasics.where().equalTo("id", 1).findAll().get(0);

        calendar        = Calendar.getInstance();
    }

    protected void addEventsDiasBaixoRisco() {
        for (int i=1; i < infoBasics.getDuracaoCiclo()-11; i++ ){
            calendar.add(Calendar.DATE, 1);
            events.put(calendar.getTime(), IconReminderEnum.DAY_LOW_RISK.getValue());
        }
    }

    protected void addEventsDiasMedioRisco() {
        for (int i=1; i < infoBasics.getDuracaoCiclo()-15; i++ ){
            calendar.add(Calendar.DATE, 1);
            events.put(calendar.getTime(), IconReminderEnum.DAY_MID_RISK.getValue());
        }
    }

    protected void addEventsDiasTPM() {
        for (int i=1; i < infoBasics.getDuracaoCiclo()-21; i++ ) {
            calendar.add(Calendar.DATE, 1);
            events.put(calendar.getTime(), IconReminderEnum.DAY_TPM.getValue());
        }
    }
}
