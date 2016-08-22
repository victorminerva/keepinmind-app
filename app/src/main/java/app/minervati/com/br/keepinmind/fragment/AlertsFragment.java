package app.minervati.com.br.keepinmind.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.domain.Lembrete;
import app.minervati.com.br.keepinmind.util.KeepUtil;
import io.realm.Realm;
import io.realm.RealmResults;

public class AlertsFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {

    protected TextView                  mHoraAnti;
    protected ToggleButton              mToggleOnOff;
    protected RecyclerView              listReminder;
    protected FloatingActionButton      fabAddLembrete;

    protected TimePickerDialog          mTimePickerDialog;

    protected SimpleDateFormat          sdf;
    protected Calendar                  calendar;

    protected Activity                  activity;
    protected Lembrete                  lembrete;
    protected Realm                     realm;
    protected RealmResults<Lembrete>    realmLembretes;

    public AlertsFragment() {
        // Required empty public constructor
    }

    public static AlertsFragment newInstance() {
        AlertsFragment fragment = new AlertsFragment();
        Bundle args = new Bundle();
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
        View inflate = inflater.inflate(R.layout.fragment_alerts, container, false);
        init(inflate);

        if (!KeepUtil.isNull(lembrete) && !KeepUtil.isNull(lembrete.getHora())){
            calendar.set(0,0,0,lembrete.getHora(), lembrete.getMinuto(), 0);
            mToggleOnOff.setChecked(lembrete.getStatusLembrete());
        }

        mHoraAnti.setText(sdf.format(calendar.getTime()));

        mToggleOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    timeDialog(calendar).show(activity.getFragmentManager(), "TimePicker");
                } else {
                    alteraStatusMedicamento(lembrete.getHora(), lembrete.getMinuto(), false);
                }
            }
        });

        return inflate;
    }

    protected void init(View view){
        mHoraAnti       = (TextView) view.findViewById(R.id.tv_hora_anti);
        mToggleOnOff    = (ToggleButton) view.findViewById(R.id.toggle_sim_nao);
        listReminder    = (RecyclerView) view.findViewById(R.id.list_item_reminder);
        fabAddLembrete  = (FloatingActionButton) view.findViewById(R.id.fab_add_reminder);

        sdf             = new SimpleDateFormat("HH:mm");
        calendar        = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        activity        = getActivity();
        realm           = Realm.getInstance(activity);
        realmLembretes  = realm.where(Lembrete.class).findAll();
        lembrete        = realmLembretes.where().equalTo("id", 1).findAll().get(0);
    }

    //Mostra um picker de data.
    protected TimePickerDialog timeDialog(Calendar cDefault) {
        mTimePickerDialog = TimePickerDialog.newInstance(
                this,
                cDefault.get(Calendar.HOUR_OF_DAY),
                cDefault.get(Calendar.MINUTE),
                cDefault.get(Calendar.SECOND),
                false);

        mTimePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                mToggleOnOff.setChecked(false);
            }
        });
        return mTimePickerDialog;
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        calendar.set(0,0,0,hourOfDay, minute, second);
        mHoraAnti.setText( sdf.format(calendar.getTime()) );

        salvaHorarioMedicamento(hourOfDay, minute);
    }

    private void salvaHorarioMedicamento(Integer hourOfDay, Integer minute) {
        preencheDadosLembrete(hourOfDay, minute, true);
    }

    private void alteraStatusMedicamento(Integer hourOfDay, Integer minute, Boolean status) {
        preencheDadosLembrete(hourOfDay, minute, status);
    }

    private void preencheDadosLembrete(Integer hourOfDay, Integer minute, Boolean status) {
        realm.beginTransaction();
        lembrete.setId(1L);
        lembrete.setHora(hourOfDay);
        lembrete.setMinuto(minute);
        lembrete.setStatusLembrete(status);
        lembrete.setTituloLembrete(getResources().getString(R.string.hora_do_anti));
        lembrete.setDataLembrete(new Date());
        realm.copyToRealmOrUpdate(lembrete);
        realm.commitTransaction();
    }
}
