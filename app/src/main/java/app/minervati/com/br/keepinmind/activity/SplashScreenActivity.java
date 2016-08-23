package app.minervati.com.br.keepinmind.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.domain.InfoBasics;
import io.realm.Realm;
import io.realm.RealmResults;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int        WRITE_EXTERNAL_STORAGE_PERMISSION   = 1;

    private Realm realm;
    private RealmResults<InfoBasics> realmInfoBasics;

    private Intent      intent;
    private Class       activity;
    private String[]    permissoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

        permissoes = new String[]{Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.RECEIVE_BOOT_COMPLETED,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.SET_ALARM,
                Manifest.permission.SEND_SMS,
                Manifest.permission.CALL_PHONE
        };
        ActivityCompat.requestPermissions(this, permissoes, WRITE_EXTERNAL_STORAGE_PERMISSION);


        if (realmInfoBasics.size() == 0)
            activity = InfoBasicsActivity.class;

        intent = new Intent(this, activity);
        startActivity(intent);
        this.finish();

    }

    private void init() {
        realm           = Realm.getInstance(this);
        realmInfoBasics = realm.where(InfoBasics.class).findAll();
        activity        = HomeActivity.class;
    }
}
