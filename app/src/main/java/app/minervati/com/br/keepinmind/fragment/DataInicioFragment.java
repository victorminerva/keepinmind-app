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
import android.widget.DatePicker;
import android.widget.ImageButton;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.util.KeepConstants;
import app.minervati.com.br.keepinmind.util.KeepUtil;

public class DataInicioFragment extends Fragment {

    private Button          buttonNext;
    private Button          buttonBack;
    private ImageButton     telaDadosDois;
    private DatePicker      dpDataInicioPil;

    private FragmentManager     fragManager;
    private FragmentTransaction fragmentTransaction;
    @NonNull
    private static DataInicioFragment   fragment;
    @NonNull
    private static Bundle               bundle;

    private Integer durCiclo        = 1;
    private Integer qtdeDiasMenstru = 0;

    public DataInicioFragment() {
        // Required empty public constructor
    }

    public static DataInicioFragment newInstance(Integer dia, Integer mes, Integer ano,
                                                 Integer durCiclo, Integer qtdeDiasMesntr) {
        fragment    = new DataInicioFragment();
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
        View inflate = inflater.inflate(R.layout.fragment_data_inicio, container, false);
        init(inflate);

        telaDadosDois.setPressed(Boolean.TRUE);
        if( bundle.getInt(KeepConstants.DIA) != 0 )
            dpDataInicioPil.updateDate(bundle.getInt(KeepConstants.ANO), bundle.getInt(KeepConstants.MES),
                bundle.getInt(KeepConstants.DIA));

        if ( bundle.getInt(KeepConstants.DUR_CICLO) != 0)
            durCiclo = bundle.getInt(KeepConstants.DUR_CICLO);

        if ( bundle.getInt(KeepConstants.QTD_DIAS_MENSTRU) != 0)
            qtdeDiasMenstru = bundle.getInt(KeepConstants.QTD_DIAS_MENSTRU);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragmentCurrent(BemVindoFragment.newInstance());
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragmentCurrent(CicloFragment.newInstance(dpDataInicioPil.getDayOfMonth(),
                        dpDataInicioPil.getMonth(),
                        dpDataInicioPil.getYear(), durCiclo, qtdeDiasMenstru));
            }
        });

        return inflate;
    }

    private void init(View view){
        buttonNext          = (Button) view.findViewById(R.id.btn_next);
        buttonBack          = (Button) view.findViewById(R.id.btn_back);
        telaDadosDois       = (ImageButton) view.findViewById(R.id.tela_dados_dois);
        dpDataInicioPil     = (DatePicker) view.findViewById(R.id.data_inicio_pil);

        fragManager         = getFragmentManager();
        fragmentTransaction = fragManager.beginTransaction();
    }

    private void showFragmentCurrent(Fragment fragment){
        fragmentTransaction.replace(R.id.container_frag, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
