package app.minervati.com.br.keepinmind.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.domain.InfoBasics;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by victorminerva on 18/08/2016.
 */
public class UpdateDadosActivityAbstract extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    protected Intent                    homeActivity;
    protected Toolbar                   toolbar;
    protected EditText                  inputData;

    protected Realm                     realm;
    protected RealmResults<InfoBasics>  realmInfoBasics;

    protected InfoBasics                infoBasics;
    protected Calendar                  calendar;
    protected SimpleDateFormat          dateFormat;

    protected DatePickerDialog          mDatePickerDialog;

    protected void init(){
        toolbar             = (Toolbar) findViewById(R.id.toolbar);
        homeActivity        = new Intent(this, HomeActivity.class);
        inputData           = (EditText) findViewById(R.id.input_data);

        realm           = Realm.getInstance(this);
        realmInfoBasics = realm.where(InfoBasics.class).findAll();
        infoBasics      = realmInfoBasics.where().equalTo("id", 1).findAll().get(0);
        calendar        = Calendar.getInstance();
        dateFormat      = new SimpleDateFormat("dd/MM/yyyy");
    }

    //Mostra um picker de data.
    protected DatePickerDialog dateDialog(Calendar cDefault) {
        mDatePickerDialog = DatePickerDialog.newInstance(
                this,
                cDefault.get(Calendar.YEAR),
                cDefault.get(Calendar.MONTH),
                cDefault.get(Calendar.DAY_OF_MONTH));
        return mDatePickerDialog;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        String valorData = dateFormat.format(calendar.getTime());
        inputData.setText(valorData);
    }
}
