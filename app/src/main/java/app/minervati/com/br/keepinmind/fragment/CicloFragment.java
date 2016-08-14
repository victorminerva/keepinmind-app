package app.minervati.com.br.keepinmind.fragment;

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
import android.widget.NumberPicker;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.util.KeepConstants;

public class CicloFragment extends Fragment {

    private Button          buttonNext;
    private Button          buttonBack;
    private ImageButton     telaDadosTres;
    private NumberPicker    npDurCiclo;

    private FragmentManager     fragManager;
    private FragmentTransaction fragmentTransaction;

    @NonNull
    private static CicloFragment    fragment;
    @NonNull
    private static Bundle           bundle;

    private Integer qtdeDiasMenstru = 0;

    public CicloFragment() {
        // Required empty public constructor
    }

    public static CicloFragment newInstance(Integer dia, Integer mes, Integer ano,
                                            Integer durCiclo, Integer qtdeDiasMesntr) {
        fragment    = new CicloFragment();
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
        View inflate = inflater.inflate(R.layout.fragment_ciclo, container, false);
        init(inflate);

        telaDadosTres.setPressed(Boolean.TRUE);
        npDurCiclo.setMinValue(21);
        npDurCiclo.setMaxValue(28);

        if( bundle.getInt(KeepConstants.DUR_CICLO) != 1 )
            npDurCiclo.setValue( bundle.getInt(KeepConstants.DUR_CICLO) );

        if ( bundle.getInt(KeepConstants.QTD_DIAS_MENSTRU) != 0)
            qtdeDiasMenstru = bundle.getInt(KeepConstants.QTD_DIAS_MENSTRU);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragmentCurrent(DataInicioFragment.newInstance(bundle.getInt(KeepConstants.DIA),
                        bundle.getInt(KeepConstants.MES),
                        bundle.getInt(KeepConstants.ANO),
                        npDurCiclo.getValue(),
                        qtdeDiasMenstru));
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragmentCurrent(QtdeDiasMenstruFragment.newInstance(bundle.getInt(KeepConstants.DIA),
                        bundle.getInt(KeepConstants.MES),
                        bundle.getInt(KeepConstants.ANO),
                        npDurCiclo.getValue(),
                        qtdeDiasMenstru));
            }
        });

        return inflate;
    }

    private void init(View view){
        buttonNext          = (Button) view.findViewById(R.id.btn_next);
        buttonBack          = (Button) view.findViewById(R.id.btn_back);
        telaDadosTres       = (ImageButton) view.findViewById(R.id.tela_dados_tres);
        npDurCiclo          = (NumberPicker) view.findViewById(R.id.np_dur_ciclo);

        fragManager         = getFragmentManager();
        fragmentTransaction = fragManager.beginTransaction();
    }

    private void showFragmentCurrent(Fragment fragment){
        fragmentTransaction.replace(R.id.container_frag, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
