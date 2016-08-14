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

import java.text.SimpleDateFormat;
import java.util.Calendar;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.activity.HomeActivity;
import app.minervati.com.br.keepinmind.util.KeepConstants;
import app.minervati.com.br.keepinmind.util.KeepUtil;

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
    private static ResumoFragment   fragment;
    @NonNull
    private static Bundle           bundle;


    @NonNull
    private Activity                    activity;

    private SharedPreferences           preferences;
    private SharedPreferences.Editor    editor;

    public ResumoFragment() {
        // Required empty public constructor
    }

    public static ResumoFragment newInstance(Integer dia, Integer mes, Integer ano,
                                             Integer durCiclo, Integer qtdeDiasMesntr) {
        fragment    = new ResumoFragment();
        bundle      = new Bundle();

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
        View inflate = inflater.inflate(R.layout.fragment_resumo, container, false);
        init(inflate);

        telaDadosCinco.setPressed(Boolean.TRUE);

        valueData.setText(formatToDate(bundle.getInt(KeepConstants.DIA),
                bundle.getInt(KeepConstants.MES),
                bundle.getInt(KeepConstants.ANO)));
        valueCiclo.setText(String.valueOf(bundle.getInt(KeepConstants.DUR_CICLO)));
        valueCicloMenst.setText(String.valueOf(bundle.getInt(KeepConstants.QTD_DIAS_MENSTRU)));


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragmentCurrent(QtdeDiasMenstruFragment.newInstance(bundle.getInt(KeepConstants.DIA),
                        bundle.getInt(KeepConstants.MES),
                        bundle.getInt(KeepConstants.ANO),
                        bundle.getInt(KeepConstants.DUR_CICLO),
                        bundle.getInt(KeepConstants.QTD_DIAS_MENSTRU)));
            }
        });

        buttonConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(activity, HomeActivity.class);
                home.putExtras(bundle);
                startActivity(home);
                // close InfoBasics activity
                activity.finish();
            }
        });
        return inflate;
    }

    private String formatToDate(Integer dia, Integer mes, Integer ano) {
        calendar.set(ano, mes, dia);
        return sdf.format(calendar.getTime());
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
        preferences         = getActivity().getSharedPreferences(KeepConstants.SP_KEEP_DADOS, Context.MODE_PRIVATE);
        editor              = preferences.edit();

        buttonConcluir.setText(R.string.concluir);
        buttonConcluir.setTextColor(KeepUtil.getColor(R.color.white, getActivity()));
        buttonConcluir.setBackgroundColor(KeepUtil.getColor(R.color.colorPrimary, getActivity()));
    }

    private void showFragmentCurrent(Fragment fragment){
        fragmentTransaction.replace(R.id.container_frag, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
