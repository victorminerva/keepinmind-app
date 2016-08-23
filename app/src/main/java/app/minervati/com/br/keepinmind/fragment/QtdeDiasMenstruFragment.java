package app.minervati.com.br.keepinmind.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.util.KeepConstants;

public class QtdeDiasMenstruFragment extends Fragment {

    private Button          buttonNext;
    private Button          buttonBack;
    private ImageButton     telaDadosQuatro;
    private NumberPicker    npQtdDiasMenstru;

    private FragmentManager     fragManager;
    private FragmentTransaction fragmentTransaction;

    private String  telefone        = "";
    private String  msgDefault      = "";

    public QtdeDiasMenstruFragment() {
        // Required empty public constructor
    }

    public static QtdeDiasMenstruFragment newInstance(Integer dia, Integer mes, Integer ano,
                                                      Integer durCiclo, Integer qtdeDiasMesntr,
                                                      String telefone, String msgDefault) {
        QtdeDiasMenstruFragment fragment    = new QtdeDiasMenstruFragment();
        Bundle                  bundle      = new Bundle();

        bundle.putInt(KeepConstants.DIA, dia);
        bundle.putInt(KeepConstants.MES, mes);
        bundle.putInt(KeepConstants.ANO, ano);
        bundle.putInt(KeepConstants.DUR_CICLO, durCiclo);
        bundle.putInt(KeepConstants.QTD_DIAS_MENSTRU, qtdeDiasMesntr);
        bundle.putString(KeepConstants.TELEFONE, telefone);
        bundle.putString(KeepConstants.MSG_DEFAULT, msgDefault);

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
        View inflate = inflater.inflate(R.layout.fragment_qtde_dias_menstru, container, false);
        init(inflate);
        telaDadosQuatro.setPressed(Boolean.TRUE);
        npQtdDiasMenstru.setMinValue(1);
        npQtdDiasMenstru.setMaxValue(8);

        if( getArguments().getInt(KeepConstants.QTD_DIAS_MENSTRU) != 0 )
            npQtdDiasMenstru.setValue(getArguments().getInt(KeepConstants.QTD_DIAS_MENSTRU));

        if ( getArguments().getString(KeepConstants.TELEFONE) != "")
            telefone = getArguments().getString(KeepConstants.TELEFONE);

        if ( getArguments().getString(KeepConstants.MSG_DEFAULT) != "")
            msgDefault = getArguments().getString(KeepConstants.MSG_DEFAULT);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragmentCurrent(CicloFragment.newInstance(getArguments().getInt(KeepConstants.DIA),
                        getArguments().getInt(KeepConstants.MES),
                        getArguments().getInt(KeepConstants.ANO),
                        getArguments().getInt(KeepConstants.DUR_CICLO),
                        npQtdDiasMenstru.getValue(),
                        getArguments().getString(KeepConstants.TELEFONE),
                        getArguments().getString(KeepConstants.MSG_DEFAULT)));
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragmentCurrent(DadosParceiraFragment.newInstance(getArguments().getInt(KeepConstants.DIA),
                        getArguments().getInt(KeepConstants.MES),
                        getArguments().getInt(KeepConstants.ANO),
                        getArguments().getInt(KeepConstants.DUR_CICLO),
                        npQtdDiasMenstru.getValue(),
                        getArguments().getString(KeepConstants.TELEFONE),
                        getArguments().getString(KeepConstants.MSG_DEFAULT)));
            }
        });
        return inflate;
    }

    private void init(View view){
        buttonNext          = (Button) view.findViewById(R.id.btn_next);
        buttonBack          = (Button) view.findViewById(R.id.btn_back);
        telaDadosQuatro     = (ImageButton) view.findViewById(R.id.tela_dados_quatro);
        npQtdDiasMenstru    = (NumberPicker) view.findViewById(R.id.np_qtd_dias_menstru);

        fragManager         = getFragmentManager();
        fragmentTransaction = fragManager.beginTransaction();
    }

    private void showFragmentCurrent(Fragment fragment){
        fragmentTransaction.replace(R.id.container_frag, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
