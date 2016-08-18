package app.minervati.com.br.keepinmind.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import app.minervati.com.br.keepinmind.R;

public class UpdateDadosActivity extends UpdateDadosActivityAbstract {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_dados);
        init();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        calendar.set(infoBasics.getAno(), infoBasics.getMes(), infoBasics.getDia());

        inputData.setText(dateFormat.format(calendar.getTime()));
        inputData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateDialog(calendar).show(getFragmentManager(), "DatePicker");
            }
        });

        duracaoCiclo.setSelectedItem(infoBasics.getDuracaoCiclo()-21);
        duracaoMenstrual.setSelectedItem(infoBasics.getQtdeDiasMenstru()-1);
    }

    @Override
    public void onBackPressed() {
        startActivity(homeActivity);
        this.finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_update:
                try {
                    updateDados(infoBasics);
                    Toast.makeText(this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }catch (Exception e){
                    Toast.makeText(this, "Falha ao atualizar!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_update_dados, menu);
        return true;
    }


}
