package app.minervati.com.br.keepinmind.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.riontech.calendar.CustomCalendar;
import com.riontech.calendar.dao.EventData;
import com.riontech.calendar.dao.dataAboutDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.domain.InfoBasics;
import app.minervati.com.br.keepinmind.util.KeepUtil;
import io.realm.Realm;
import io.realm.RealmResults;

public class CalendarFragmentAbstract extends Fragment {

    protected static final Integer  EVENT_COUNT_1 = 1;
    protected static final Integer  EVENT_COUNT_2 = 2;
    protected static final Integer  EVENT_COUNT_3 = 3;

    protected CustomCalendar  customCalendar;

    protected Realm           realm;
    protected RealmResults<InfoBasics> realmInfoBasics;

    protected InfoBasics      infoBasics;

    protected Calendar                    calendar;
    protected EventData                   dateData;
    protected dataAboutDate               dataAboutDate;
    protected ArrayList<EventData>        eventDataList;
    protected ArrayList<dataAboutDate>    dataAboutDates;

    protected List<String>  listSection;
    protected List<String>  listTitle;
    protected List<String>  listSubject;

    protected void init(View view){
        customCalendar  = (CustomCalendar) view.findViewById(R.id.customCalendar);
        realm           = Realm.getInstance(getActivity());
        realmInfoBasics = realm.where(InfoBasics.class).findAll();

        infoBasics      = realmInfoBasics.where().equalTo("id", 1).findAll().get(0);

        calendar        = Calendar.getInstance();

        listSection     = new ArrayList<>();
        listTitle       = new ArrayList<>();
        listSubject     = new ArrayList<>();

        listSection.add(KeepUtil.getStringFromResources(this, R.string.dia_anti_baixo_risco));
        listSection.add(KeepUtil.getStringFromResources(this, R.string.dia_anti_tpm));

        listTitle.add(KeepUtil.getStringFromResources(this, R.string.baixo_risco));
        listTitle.add(KeepUtil.getStringFromResources(this, R.string.tpm));

        listSubject.add(KeepUtil.getStringFromResources(this, R.string.msg_baixo_risco));
        listSubject.add(KeepUtil.getStringFromResources(this, R.string.msg_tpm));
    }

    protected void addOneEventIntoCalendar(Date dataInicial, Integer eventCount, String section,
                                           String title, String subject) {
        customCalendar.addAnEvent(
                KeepUtil.dateToStringFormat(dataInicial, "yyyy-MM-dd"),
                eventCount,
                addOneEventCalendarDay(section, title, subject));
    }

    protected void addTwoEventIntoCalendar(Date dataInicial, Integer eventCount, List<String> section,
                                           List<String> title, List<String> subject) {
        customCalendar.addAnEvent(
                KeepUtil.dateToStringFormat(dataInicial, "yyyy-MM-dd"),
                eventCount,
                addTwoEventCalendarDay(section, title, subject, eventCount));
    }

    protected ArrayList<EventData> addOneEventCalendarDay(String section, String title, String subject){
        dateData        = new EventData();
        dataAboutDate   = new dataAboutDate();
        eventDataList   = new ArrayList();
        dataAboutDates  = new ArrayList();

        dateData.setSection(section);
        dataAboutDate.setTitle(title);
        dataAboutDate.setSubject(subject);

        dataAboutDates.add(dataAboutDate);
        dateData.setData(dataAboutDates);
        eventDataList.add(dateData);

        return eventDataList;
    }

    protected ArrayList<EventData> addTwoEventCalendarDay(List<String> section, List<String> title,
                                                          List<String> subject, Integer size){
        eventDataList   = new ArrayList();

        for (int i = 0; i < size; i++){
            dateData        = new EventData();
            dataAboutDate   = new dataAboutDate();
            dataAboutDates  = new ArrayList();

            dateData.setSection(section.get(i));
            dataAboutDate.setTitle(title.get(i));
            dataAboutDate.setSubject(subject.get(i));

            dataAboutDates.add(dataAboutDate);
            dateData.setData(dataAboutDates);
            eventDataList.add(dateData);
        }

        return eventDataList;
    }

    protected Date formatDataFromAnoMesDiaToDate(Integer ano, Integer mes, Integer dia){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(ano, mes, dia);
        return calendar.getTime();
    }

}
