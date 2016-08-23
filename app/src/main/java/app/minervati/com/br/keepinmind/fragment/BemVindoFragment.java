package app.minervati.com.br.keepinmind.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import app.minervati.com.br.keepinmind.R;

public class BemVindoFragment extends Fragment {

    private Button                  btnConcordo;
    private ImageButton             telaDadosUm;

    private FragmentManager fragManager;
    private FragmentTransaction fragmentTransaction;

    public BemVindoFragment() {
        // Required empty public constructor
    }

    public static BemVindoFragment newInstance() {
        BemVindoFragment fragment = new BemVindoFragment();
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
        View inflate = inflater.inflate(R.layout.fragment_bem_vindo, container, false);
        init(inflate);

        telaDadosUm.setPressed(Boolean.TRUE);
        btnConcordo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,
                        R.anim.slide_out_left);*/
                fragmentTransaction.replace(R.id.container_frag,
                        DataInicioFragment.newInstance(0, 0, 0, 0, 0, "", ""));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return inflate;
    }

    private void init(View view){
        btnConcordo         = (Button) view.findViewById(R.id.btn_concordo);
        telaDadosUm         = (ImageButton) view.findViewById(R.id.tela_dados_um);
        fragManager         = getFragmentManager();
        fragmentTransaction = fragManager.beginTransaction();
    }

}
