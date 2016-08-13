package app.minervati.com.br.keepinmind.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.minervati.com.br.keepinmind.R;

public class BemVindoFragment extends Fragment {

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
        return inflate;
    }

}
