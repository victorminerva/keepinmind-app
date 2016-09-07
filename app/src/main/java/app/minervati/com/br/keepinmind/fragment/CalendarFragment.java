package app.minervati.com.br.keepinmind.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.Date;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.domain.IconReminderEnum;
import app.minervati.com.br.keepinmind.util.CalendarView;

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
        setAlarme(calendar.getTime(), IconReminderEnum.DAY_START.getValue());
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
        setAlarme(calendar.getTime(), IconReminderEnum.DAY_END.getValue());

        addEventsDiasMenstruacao();

        for (int i = 0; i <= 12; i++) {
            /**
             * Add evento reinicio do anticoncepcional
             */
            if (infoBasics.getQtdeDiasMenstru() != 1 && infoBasics.getDuracaoCiclo() != 28)
                calendar.add(Calendar.DATE, 1);


            events.put(calendar.getTime(), IconReminderEnum.DAY_START.getValue());
            setAlarme(calendar.getTime(), IconReminderEnum.DAY_START.getValue());
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
            setAlarme(calendar.getTime(), IconReminderEnum.DAY_END.getValue());

            addEventsDiasMenstruacao();
        }

        calendarView.updateCalendar(events);
        calendarView.setEventHandler(new CalendarView.EventHandler(){

            @Override
            public void onDayLongPress(Date date) {
            }

            @Override
            public void onDayPress(Date date) {
                dataSelectedFormatada = sdf.format(date);
                value               = 0;
                titulo              = "";
                subtitulo           = "";
                descricao           = "";

                value = checkDateSelected(sdf, dataSelectedFormatada, value);
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
                    case 6:
                        titulo      = getResources().getString(R.string.periodo_menstru);
                        subtitulo   = getResources().getString(R.string.menstru);
                        descricao   = getResources().getString(R.string.msg_dia_menstru);
                        break;
                    default:
                        titulo      = getResources().getString(R.string.sem_evento_nesta_data);
                        subtitulo   = "";
                        descricao   = "";
                }

                if(!"".equals(titulo)) {
                    tvTitle.setText(titulo + " - " + dataSelectedFormatada.substring(0, 1).toUpperCase() + dataSelectedFormatada.substring(1));
                    tvSubtitle.setText(subtitulo);
                    tvDesc.setText(descricao);

                    tvTitle.setVisibility(View.VISIBLE);
                    tvSubtitle.setVisibility(View.VISIBLE);
                    tvDesc.setVisibility(View.VISIBLE);
                }
            }
        });

        return inflate;
    }

}
