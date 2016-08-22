package app.minervati.com.br.keepinmind.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import app.minervati.com.br.keepinmind.R;
import app.minervati.com.br.keepinmind.adapter.ViewPagerAdapter;
import app.minervati.com.br.keepinmind.fragment.AlertsFragment;
import app.minervati.com.br.keepinmind.fragment.CalendarFragment;

/**
 * Created by victorminerva on 12/08/2016.
 */
public class HomeActivityAbstract extends AppCompatActivity {

    protected Toolbar       mToolbar;
    protected TabLayout     mTabLayout;
    protected TabItem       mTabItemCalendar;
    protected TabItem       mTabItemAlerts;
    protected ViewPager     mViewPager;

    protected Integer[]     tabIcon = {
            R.drawable.ic_tab_calendar,
            R.drawable.ic_tab_clock_alert
    };

    protected ViewPagerAdapter mViewPagerAdapter;

    protected Intent           settingsActivity;
    protected Intent           updateActivity;

    protected void init(){
        mToolbar            = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout          = (TabLayout) findViewById(R.id.tablayout);
        mTabItemCalendar    = (TabItem) findViewById(R.id.tabitem_calendar);
        mTabItemAlerts      = (TabItem) findViewById(R.id.tabitem_alerts);
        mViewPager          = (ViewPager) findViewById(R.id.viewpage);

        mTabLayout.setupWithViewPager(mViewPager);

        Bundle extras = getIntent().getExtras();

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPagerAdapter.addFrag(CalendarFragment.newInstance(extras), "CALENDÁRIO");
        mViewPagerAdapter.addFrag(AlertsFragment.newInstance(), "LEMBRETES");

        settingsActivity            = new Intent(this, SettingsActivity.class);
        updateActivity              = new Intent(this, UpdateDadosActivity.class);
    }

    protected void setupViewPager(ViewPager viewPager) {
        viewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.getTabAt(0).setIcon(tabIcon[0]);
        mTabLayout.getTabAt(1).setIcon(tabIcon[1]);
    }

}
