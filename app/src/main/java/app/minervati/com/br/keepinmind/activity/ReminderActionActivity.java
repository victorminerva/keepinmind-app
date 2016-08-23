package app.minervati.com.br.keepinmind.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.adapter.OpcoesListAdapter;
import app.minervati.com.br.keepinmind.domain.InfoBasics;
import io.realm.Realm;
import io.realm.RealmResults;

public class ReminderActionActivity extends AppCompatActivity {

    protected ListView      listOptions;
    protected List<String>  itensname = new ArrayList<String>();
    protected List<Integer> itemimage = new ArrayList<Integer>();

    protected OpcoesListAdapter adapter;
    protected InfoBasics        infoBasics;
    protected SmsManager        smsManager;
    protected TelephonyManager  tm;
    protected String            countryCode;

    protected Intent            whatsappIntent;

    protected Realm                     realm;
    protected RealmResults<InfoBasics>  realmInfoBasics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_action);
        init();

        listOptions.setAdapter(adapter);
        listOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String message = "";
                try {
                    switch (position) {
                        case 0:
                            Uri uri = Uri.parse("smsto:" + infoBasics.getTelefone());
                            whatsappIntent = new Intent(Intent.ACTION_SENDTO, uri);
                            whatsappIntent.setPackage("com.whatsapp");
                            startActivity(Intent.createChooser(whatsappIntent, infoBasics.getMsgDefault()));
                            message = "Redirecionando para o whatsapp..";
                            break;
                        case 1:
                            smsManager.sendTextMessage(infoBasics.getTelefone(), null, infoBasics.getMsgDefault(), null, null);
                            message = "SMS enviado com sucesso!";
                            finish();
                            break;
                        case 2:
                            break;
                        default:
                            break;
                    }
                    Toast.makeText(ReminderActionActivity.this,
                            message,
                            Toast.LENGTH_SHORT).show();
                } catch(Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ReminderActionActivity.this,
                            "Erro, não foi possível realizar a operação!",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void init(){
        listOptions = (ListView) findViewById(R.id.list_of_options);

        itensname.add(getResources().getString(R.string.enviar_msg_wpp));
        itensname.add(getResources().getString(R.string.enviar_sms));
        itensname.add(getResources().getString(R.string.ligar));
        itemimage.add(R.drawable.ic_whatsapp);
        itemimage.add(R.drawable.ic_sms);
        itemimage.add(R.drawable.ic_call_phone);

        adapter     = new OpcoesListAdapter(this, itensname, itemimage);

        smsManager  = SmsManager.getDefault();
        tm          = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        countryCode = tm.getSimCountryIso();

        realm           = Realm.getInstance(this);
        realmInfoBasics = realm.where(InfoBasics.class).findAll();
        infoBasics      = realmInfoBasics.where().equalTo("id", 1).findAll().get(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
