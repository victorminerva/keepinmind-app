package app.minervati.com.br.keepinmind.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.riontech.calendar.CustomCalendar;
import com.riontech.calendar.dao.EventData;
import com.riontech.calendar.dao.dataAboutDate;
import com.riontech.calendar.utils.CalendarUtils;

import java.util.ArrayList;
import java.util.Random;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.util.KeepConstants;

public class CalendarFragment extends Fragment {

    private CustomCalendar customCalendar;

    public CalendarFragment() {
        // Required empty public constructor
    }

    public static CalendarFragment newInstance(Bundle args) {
        CalendarFragment fragment = new CalendarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Log.d("ARGS", "TEM ARGUS" + getArguments().getInt(KeepConstants.DIA));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_calendar, container, false);
        init(inflate);

        String[] arr = {"2016-08-10", "2016-08-11", "2016-08-15", "2016-08-16", "2016-08-25"};
        for (int i = 0; i < arr.length; i++) {
            int eventCount = 3;
            customCalendar.addAnEvent(arr[i], eventCount, getEventDataList(eventCount));
        }
        return inflate;
    }

    private void init(View view){
        customCalendar = (CustomCalendar) view.findViewById(R.id.customCalendar);
    }

    public ArrayList<EventData> getEventDataList(int count) {
        ArrayList<EventData> eventDataList = new ArrayList();

        for (int i = 0; i < count; i++) {
            EventData dateData = new EventData();
            ArrayList<dataAboutDate> dataAboutDates = new ArrayList();

            dateData.setSection(CalendarUtils.getNAMES()[new Random().nextInt(CalendarUtils.getNAMES().length)]);
            dataAboutDate dataAboutDate = new dataAboutDate();

            int index = new Random().nextInt(CalendarUtils.getEVENTS().length);

            dataAboutDate.setTitle(CalendarUtils.getEVENTS()[index]);
            dataAboutDate.setSubject(CalendarUtils.getEventsDescription()[index]);
            dataAboutDates.add(dataAboutDate);

            dateData.setData(dataAboutDates);
            eventDataList.add(dateData);
        }

        return eventDataList;
    }
}
