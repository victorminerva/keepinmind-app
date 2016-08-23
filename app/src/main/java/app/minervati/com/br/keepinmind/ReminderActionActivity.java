package app.minervati.com.br.keepinmind;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import app.minervati.com.br.keepinmind.adapter.OpcoesListAdapter;

public class ReminderActionActivity extends AppCompatActivity {

    protected ListView      listOptions;
    protected List<String>  itensname = new ArrayList<String>();
    protected List<Integer> itemimage = new ArrayList<Integer>();

    protected OpcoesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_action);
        init();

        listOptions.setAdapter(adapter);
        listOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    default:
                        break;
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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
