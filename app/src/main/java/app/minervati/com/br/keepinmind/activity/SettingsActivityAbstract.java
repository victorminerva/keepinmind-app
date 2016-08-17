package app.minervati.com.br.keepinmind.activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.adapter.SobreAjudaListAdapter;

/**
 * Created by victorminerva on 17/08/2016.
 */
public class SettingsActivityAbstract extends AppCompatActivity {

    protected ListView              listItens;
    protected List<String>          itens       = new ArrayList<String>();
    protected List<String>          subTitles   = new ArrayList<String>();
    protected SobreAjudaListAdapter adapter;


    protected Toolbar toolbar;

    protected void init(){
        toolbar     = (Toolbar) findViewById(R.id.toolbar);
        listItens   = (ListView) findViewById(R.id.listItens);
        adapter     = new SobreAjudaListAdapter(this, itens, subTitles);

        itens.add(getResources().getString(R.string.sobre));
        subTitles.add("");
        itens.add(getResources().getString(R.string.definicoes));
        subTitles.add(getResources().getString(R.string.definicoes_subtitle));
    }

    protected void showOverLay() {
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.sobre_dialog);

        LinearLayout layout = (LinearLayout) dialog.findViewById(R.id.ll_sobre_dialog);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
