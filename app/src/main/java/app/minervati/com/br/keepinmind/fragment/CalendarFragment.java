package app.minervati.com.br.keepinmind.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.domain.IconReminderEnum;
import app.minervati.com.br.keepinmind.util.CalendarView;
import app.minervati.com.br.keepinmind.util.KeepUtil;

public class CalendarFragment extends CalendarFragmentAbstract {

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View inflate = inflater.inflate(R.layout.fragment_calendar, container, false);
        init(inflate);

        /**
         * Add evento 1º dia do anticoncepcional
         */
        calendar.set(infoBasics.getAno(), infoBasics.getMes(), infoBasics.getDia());
        events.put(calendar.getTime(), IconReminderEnum.DAY_START.getValue());

        /**
         * Add evento durante o anticoncepcional
         */
        addEventsDiasBaixoRisco();
        addEventsDiasMedioRisco();
        addEventsDiasTPM();

        /**
         * Add evento Ultimo dia do anticoncepcional
         */
        calendar.add(Calendar.DATE, 1);
        events.put(calendar.getTime(), IconReminderEnum.DAY_END.getValue());

        for (int i = 0; i <= 12; i++) {
            /**
             * Add evento reinicio do anticoncepcional
             */
            calendar.add(Calendar.DATE, infoBasics.getQtdeDiasMenstru() + 1);
            events.put(calendar.getTime(), IconReminderEnum.DAY_START.getValue());

            /**
             * Add evento durante o anticoncepcional
             */
            addEventsDiasMedioRisco();
            addEventsDiasBaixoRisco();
            addEventsDiasTPM();

            /**
             * Add evento Ultimo dia do anticoncepcional
             */
            calendar.add(Calendar.DATE, 1);
            events.put(calendar.getTime(), IconReminderEnum.DAY_END.getValue());
        }

        calendarView.updateCalendar(events);
        calendarView.setEventHandler(new CalendarView.EventHandler(){

            @Override
            public void onDayLongPress(Date date) {
            }

            @Override
            public void onDayPress(Date date) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
                String dataFormatada = sdf.format(date);
                Integer value    = 0;
                String titulo    = "";
                String subtitulo = "";
                String descricao = "";

                for(Date data : events.keySet()){
                    if (dataFormatada.equalsIgnoreCase(sdf.format(data))){
                        value = events.get(data);
                    }
                }
                switch (value){
                    case 1:
                        titulo      = getResources().getString(R.string.inicio_anti);
                        subtitulo   = getResources().getString(R.string.primeir_dia);
                        descricao   = getResources().getString(R.string.msg_primeiro_dia_anti);
                        break;
                    case 2:
                        titulo      = getResources().getString(R.string.dia_anti_baixo_risco);
                        subtitulo   = getResources().getString(R.string.baixo_risco);
                        descricao   = getResources().getString(R.string.msg_baixo_risco);
                        break;
                    case 3:
                        titulo      = getResources().getString(R.string.dia_anti_medio_risco);
                        subtitulo   = getResources().getString(R.string.medio_risco);
                        descricao   = getResources().getString(R.string.msg_medio_risco);
                        break;
                    case 4:
                        titulo      = getResources().getString(R.string.dia_anti_tpm);
                        subtitulo   = getResources().getString(R.string.tpm);
                        descricao   = getResources().getString(R.string.msg_tpm);
                        break;
                    case 5:
                        titulo      = getResources().getString(R.string.fim_anti);
                        subtitulo   = getResources().getString(R.string.ultimo_dia);
                        descricao   = getResources().getString(R.string.msg_ultimo_dia_anti);
                        break;
                }
                tvTitle.setText(titulo+" - "+dataFormatada.substring(0,1).toUpperCase() + dataFormatada.substring(1));
                tvSubtitle.setText(subtitulo);
                tvDesc.setText(descricao);

                tvTitle.setVisibility(View.VISIBLE);
                tvSubtitle.setVisibility(View.VISIBLE);
                tvDesc.setVisibility(View.VISIBLE);

            }
        });

        return inflate;
    }

}
