package app.minervati.com.br.keepinmind.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.minervati.com.br.keepinmind.R;
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
        View inflate = inflater.inflate(R.layout.fragment_calendar, container, false);
        init(inflate);

        /**
         * Retorna a data de inicio da pilula atraves do ano, mes e dia
         * informados pelo usuario no cadastro das informacoes
         * no formato 'yyyy-MM-dd'
         */
        Date dataInicial = formatDataFromAnoMesDiaToDate(infoBasics.getAno(), infoBasics.getMes(),
                infoBasics.getDia());
        calendar.setTime(dataInicial);

        /**
         * Add evento 1º dia do anticoncepcional
         */
        addOneEventIntoCalendar(calendar.getTime(), EVENT_COUNT_3,
                KeepUtil.getStringFromResources(this, R.string.inicio_anti),
                KeepUtil.getStringFromResources(this, R.string.primeir_dia),
                KeepUtil.getStringFromResources(this, R.string.msg_primeiro_dia_anti));
        /**
         * Add evento durante o anticoncepcional
         */
        addEventsDiaMedioRisco();
        addEventsDiasBaixoRisco();
        addEventsDiasBaixoRiscoAndTPM();

        /**
         * Add evento Ultimo dia do anticoncepcional
         */
        calendar.add(Calendar.DATE, 1);
        addOneEventIntoCalendar(calendar.getTime(), EVENT_COUNT_3,
                KeepUtil.getStringFromResources(this, R.string.fim_anti),
                KeepUtil.getStringFromResources(this, R.string.ultimo_dia),
                KeepUtil.getStringFromResources(this, R.string.msg_ultimo_dia_anti));
        for (int i = 0; i <= 12; i++) {
            /**
             * Add evento reinicio do anticoncepcional
             */
            calendar.add(Calendar.DATE, infoBasics.getQtdeDiasMenstru() + 1);
            addOneEventIntoCalendar(calendar.getTime(), EVENT_COUNT_3,
                    KeepUtil.getStringFromResources(this, R.string.reinicio_anti),
                    KeepUtil.getStringFromResources(this, R.string.reinicio_dia),
                    KeepUtil.getStringFromResources(this, R.string.msg_reinicio_anti));
            /**
             * Add evento durante o anticoncepcional
             */
            addEventsDiaMedioRisco();
            addEventsDiasBaixoRisco();
            addEventsDiasBaixoRiscoAndTPM();

            /**
             * Add evento Ultimo dia do anticoncepcional
             */
            calendar.add(Calendar.DATE, 1);
            addOneEventIntoCalendar(calendar.getTime(), EVENT_COUNT_3,
                    KeepUtil.getStringFromResources(this, R.string.fim_anti),
                    KeepUtil.getStringFromResources(this, R.string.ultimo_dia),
                    KeepUtil.getStringFromResources(this, R.string.msg_ultimo_dia_anti));
        }
        return inflate;
    }

    private void addEventsDiasBaixoRiscoAndTPM() {
        for (int i=1; i < infoBasics.getDuracaoCiclo()-21; i++ ) {
            calendar.add(Calendar.DATE, 1);
            addTwoEventIntoCalendar(calendar.getTime(), EVENT_COUNT_2,
                    listSection,
                    listTitle,
                    listSubject);
        }
    }

    private void addEventsDiasBaixoRisco() {
        for (int i=1; i < infoBasics.getDuracaoCiclo()-11; i++ ){
            calendar.add(Calendar.DATE, 1);
            addOneEventIntoCalendar(calendar.getTime(), EVENT_COUNT_1,
                    KeepUtil.getStringFromResources(this, R.string.dia_anti_baixo_risco),
                    KeepUtil.getStringFromResources(this, R.string.baixo_risco),
                    KeepUtil.getStringFromResources(this, R.string.msg_baixo_risco));
        }
    }

    private void addEventsDiaMedioRisco() {
        for (int i=1; i < infoBasics.getDuracaoCiclo()-15; i++ ){
            calendar.add(Calendar.DATE, 1);
            addOneEventIntoCalendar(calendar.getTime(), EVENT_COUNT_2,
                    KeepUtil.getStringFromResources(this, R.string.dia_anti_medio_risco),
                    KeepUtil.getStringFromResources(this, R.string.medio_risco),
                    KeepUtil.getStringFromResources(this, R.string.msg_medio_risco));
        }
    }
}
