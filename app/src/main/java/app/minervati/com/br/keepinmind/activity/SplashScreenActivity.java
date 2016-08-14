package app.minervati.com.br.keepinmind.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.domain.InfoBasics;
import io.realm.Realm;
import io.realm.RealmResults;

public class SplashScreenActivity extends AppCompatActivity {

    private Realm realm;
    private RealmResults<InfoBasics> realmInfoBasics;

    private Intent intent;
    private Class activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();

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
