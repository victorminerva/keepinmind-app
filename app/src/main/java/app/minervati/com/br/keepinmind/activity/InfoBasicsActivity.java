package app.minervati.com.br.keepinmind.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.fragment.BemVindoFragment;

public class InfoBasicsActivity extends AppCompatActivity {

    private FragmentManager fragManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_basics);
        init();

        fragmentTransaction.replace(R.id.container_frag, BemVindoFragment.newInstance());
        fragmentTransaction.commit();
    }

    public void init(){
        fragManager         = getSupportFragmentManager();
        fragmentTransaction = fragManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
    }
}
