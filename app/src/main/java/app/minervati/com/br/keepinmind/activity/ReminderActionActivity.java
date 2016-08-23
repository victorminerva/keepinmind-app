package app.minervati.com.br.keepinmind.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
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
                            message = "Redirecionando para o whatsapp..";
                            enviaMsgToWhatsApp();
                            break;
                        case 1:
                            message = "SMS enviado com sucesso!";
                            smsManager.sendTextMessage(infoBasics.getTelefone(), null, infoBasics.getMsgDefault(), null, null);
                            finish();
                            break;
                        case 2:
                            message = "Redirecionando para ligação..";
                            if (realizaLigacao()) return;
                            break;
                        default:
                            break;
                    }
                    Toast.makeText(ReminderActionActivity.this,
                            message,
                            Toast.LENGTH_SHORT).show();
                    finish();
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

        realm           = Realm.getInstance(this);
        realmInfoBasics = realm.where(InfoBasics.class).findAll();
        infoBasics      = realmInfoBasics.where().equalTo("id", 1).findAll().get(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private boolean realizaLigacao() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + infoBasics.getTelefone()));
        if (ActivityCompat.checkSelfPermission(ReminderActionActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        startActivity(callIntent);
        return false;
    }

    private void enviaMsgToWhatsApp() {
        StringBuffer sb = new StringBuffer(infoBasics.getTelefone());
        sb.setCharAt(2, ' ');
        Uri uri = Uri.parse("smsto:" + sb.toString());
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(i, infoBasics.getTelefone()));
    }

}
