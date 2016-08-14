package app.minervati.com.br.keepinmind.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.activity.HomeActivity;
import app.minervati.com.br.keepinmind.domain.InfoBasics;
import app.minervati.com.br.keepinmind.util.KeepConstants;
import app.minervati.com.br.keepinmind.util.KeepUtil;
import io.realm.Realm;
import io.realm.RealmResults;

public class ResumoFragment extends Fragment {

    private Button          buttonConcluir;
    private Button          buttonBack;
    private ImageButton     telaDadosCinco;
    private TextView        valueData;
    private TextView        valueCiclo;
    private TextView        valueCicloMenst;

    private FragmentManager     fragManager;
    private FragmentTransaction fragmentTransaction;

    private SimpleDateFormat    sdf         = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy");
    private Calendar            calendar    = Calendar.getInstance();
    private Integer             ano;
    private Integer             mes;
    private Integer             dia;

    @NonNull
    private Activity        activity;
    private InfoBasics      infoBasics;

    private Realm                       realm;
    private RealmResults<InfoBasics>    realmInfoBasics;

/*    private SharedPreferences           preferences;
    private SharedPreferences.Editor    editor;*/

    public ResumoFragment() {
        // Required empty public constructor
    }

    public static ResumoFragment newInstance(Integer dia, Integer mes, Integer ano,
                                             Integer durCiclo, Integer qtdeDiasMesntr) {
        ResumoFragment  fragment    = new ResumoFragment();
        Bundle          bundle      = new Bundle();

        bundle.putInt(KeepConstants.DIA, dia);
        bundle.putInt(KeepConstants.MES, mes);
        bundle.putInt(KeepConstants.ANO, ano);
        bundle.putInt(KeepConstants.DUR_CICLO, durCiclo);
        bundle.putInt(KeepConstants.QTD_DIAS_MENSTRU, qtdeDiasMesntr);

        fragment.setArguments(bundle);

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
        final View inflate = inflater.inflate(R.layout.fragment_resumo, container, false);
        init(inflate);

        telaDadosCinco.setPressed(Boolean.TRUE);

        valueData.setText(formatToDate(getArguments().getInt(KeepConstants.DIA),
                getArguments().getInt(KeepConstants.MES),
                getArguments().getInt(KeepConstants.ANO)));
        valueCiclo.setText(String.valueOf(getArguments().getInt(KeepConstants.DUR_CICLO)));
        valueCicloMenst.setText(String.valueOf(getArguments().getInt(KeepConstants.QTD_DIAS_MENSTRU)));


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragmentCurrent(QtdeDiasMenstruFragment.newInstance(getArguments().getInt(KeepConstants.DIA),
                        getArguments().getInt(KeepConstants.MES),
                        getArguments().getInt(KeepConstants.ANO),
                        getArguments().getInt(KeepConstants.DUR_CICLO),
                        getArguments().getInt(KeepConstants.QTD_DIAS_MENSTRU)));
            }
        });

        buttonConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    mantemInfoBasics();
                    Intent home = new Intent(activity, HomeActivity.class);
                    startActivity(home);
                    // close InfoBasics activity
                    activity.finish();
                } catch (Exception e) {
                    Toast.makeText(activity, R.string.erro_ao_salvar, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return inflate;
    }

    private void init(View view){
        buttonConcluir      = (Button) view.findViewById(R.id.btn_next);
        buttonBack          = (Button) view.findViewById(R.id.btn_back);
        telaDadosCinco      = (ImageButton) view.findViewById(R.id.tela_dados_cinco);
        valueData           = (TextView) view.findViewById(R.id.valueData);
        valueCiclo          = (TextView) view.findViewById(R.id.valueCiclo);
        valueCicloMenst     = (TextView) view.findViewById(R.id.valueMens);

        fragManager         = getFragmentManager();
        fragmentTransaction = fragManager.beginTransaction();

        activity            = getActivity();
        infoBasics          = new InfoBasics();

        realm               = Realm.getInstance(activity);
        realmInfoBasics     = realm.where( InfoBasics.class ).findAll();
        /*preferences         = getActivity().getSharedPreferences(KeepConstants.SP_KEEP_DADOS, Context.MODE_PRIVATE);
        editor              = preferences.edit();*/

        buttonConcluir.setText(R.string.concluir);
        buttonConcluir.setTextColor(KeepUtil.getColor(R.color.white, getActivity()));
        buttonConcluir.setBackgroundColor(KeepUtil.getColor(R.color.colorPrimary, getActivity()));
    }

    private void mantemInfoBasics() throws Exception {
        realm.beginTransaction();
        realmInfoBasics.sort("id", RealmResults.SORT_ORDER_DESCENDING);
        long id = realmInfoBasics.size() == 0 ? 1 : realmInfoBasics.get(0).getId() + 1;
        infoBasics.setId(id);
        infoBasics.setDia(getArguments().getInt(KeepConstants.DIA));
        infoBasics.setMes(getArguments().getInt(KeepConstants.MES));
        infoBasics.setAno(getArguments().getInt(KeepConstants.ANO));
        infoBasics.setDuracaoCiclo(getArguments().getInt(KeepConstants.DUR_CICLO));
        infoBasics.setQtdeDiasMenstru(getArguments().getInt(KeepConstants.QTD_DIAS_MENSTRU));
        realm.copyToRealmOrUpdate(infoBasics);
        realm.commitTransaction();
    }

    private String formatToDate(Integer dia, Integer mes, Integer ano) {
        calendar.set(ano, mes, dia);
        return sdf.format(calendar.getTime());
    }

    private void showFragmentCurrent(Fragment fragment){
        fragmentTransaction.replace(R.id.container_frag, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
