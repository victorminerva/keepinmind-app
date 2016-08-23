package app.minervati.com.br.keepinmind.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.util.KeepConstants;

public class DadosParceiraFragment extends Fragment {

    private Button      buttonNext;
    private Button      buttonBack;
    private ImageButton telaDadosCinco;
    private EditText    mTelefone;
    private EditText    mMsgDefault;

    private FragmentManager     fragManager;
    private FragmentTransaction fragmentTransaction;

    public DadosParceiraFragment() {
        // Required empty public constructor
    }

    public static DadosParceiraFragment newInstance(Integer dia, Integer mes, Integer ano,
                                                    Integer durCiclo, Integer qtdeDiasMesntr,
                                                    String telefone, String msgDefault) {
        DadosParceiraFragment fragment = new DadosParceiraFragment();
        Bundle          bundle      = new Bundle();

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
        View inflate = inflater.inflate(R.layout.fragment_dados_parceira, container, false);
        init(inflate);
        telaDadosCinco.setPressed(Boolean.TRUE);

        if ( getArguments().getString(KeepConstants.TELEFONE) != "")
            mTelefone.setText(getArguments().getString(KeepConstants.TELEFONE));

        if ( getArguments().getString(KeepConstants.MSG_DEFAULT) != "")
            mMsgDefault.setText(getArguments().getString(KeepConstants.MSG_DEFAULT));

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragmentCurrent(QtdeDiasMenstruFragment.newInstance(getArguments().getInt(KeepConstants.DIA),
                        getArguments().getInt(KeepConstants.MES),
                        getArguments().getInt(KeepConstants.ANO),
                        getArguments().getInt(KeepConstants.DUR_CICLO),
                        getArguments().getInt(KeepConstants.QTD_DIAS_MENSTRU),
                        String.valueOf(mTelefone.getText()),
                        String.valueOf(mMsgDefault.getText())));
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragmentCurrent(ResumoFragment.newInstance(getArguments().getInt(KeepConstants.DIA),
                        getArguments().getInt(KeepConstants.MES),
                        getArguments().getInt(KeepConstants.ANO),
                        getArguments().getInt(KeepConstants.DUR_CICLO),
                        getArguments().getInt(KeepConstants.QTD_DIAS_MENSTRU), String.valueOf(mTelefone.getText()),
                        String.valueOf(mMsgDefault.getText())));
            }
        });

        return inflate;
    }

    private void init(View inflate) {
        buttonNext      = (Button) inflate.findViewById(R.id.btn_next);
        buttonBack      = (Button) inflate.findViewById(R.id.btn_back);
        telaDadosCinco  = (ImageButton) inflate.findViewById(R.id.tela_dados_cinco);
        mTelefone       = (EditText) inflate.findViewById(R.id.input_tel);
        mMsgDefault     = (EditText) inflate.findViewById(R.id.input_msg_default);

        //mTelefone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        fragManager         = getFragmentManager();
        fragmentTransaction = fragManager.beginTransaction();
    }

    private void showFragmentCurrent(Fragment fragment){
        fragmentTransaction.replace(R.id.container_frag, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
