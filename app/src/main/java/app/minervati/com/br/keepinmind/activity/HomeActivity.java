package app.minervati.com.br.keepinmind.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import app.minervati.com.br.keepinmind.R;

public class HomeActivity extends HomeActivityAbstract {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();

        setSupportActionBar(mToolbar);

        setupViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                startActivity(settingsActivity);
                this.finish();
                break;
            case R.id.action_edit:
                startActivity(updateActivity);
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
