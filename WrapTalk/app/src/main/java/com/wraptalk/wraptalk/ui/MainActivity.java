package com.wraptalk.wraptalk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.wraptalk.wraptalk.R;
import com.wraptalk.wraptalk.services.ChattingService;
import com.wraptalk.wraptalk.services.TaskWatchService;
import com.wraptalk.wraptalk.utils.BackPressCloseHandler;


public class MainActivity extends AppCompatActivity {

    private BackPressCloseHandler backPressCloseHandler;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backPressCloseHandler = new BackPressCloseHandler(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Game App");


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_applist);
        //tabLayout.getTabAt(1).setIcon(R.mipmap.ic_category);
        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_mychannel);
        tabLayout.getTabAt(2).setIcon(R.mipmap.ic_setting);

        Intent intent = getIntent();
        int setTab = intent.getIntExtra("Tab", 0);
        mViewPager.setCurrentItem(setTab);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        getSupportActionBar().setTitle("Game App");
                        break;
                    /*case 1:
                        getSupportActionBar().setTitle("Category");
                        break;*/
                    case 1:
                        getSupportActionBar().setTitle("My Channels");
                        break;
                    case 2:
                        getSupportActionBar().setTitle("AppSetting");
                        break;
                }
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        startService(new Intent(MainActivity.this, TaskWatchService.class));
        startService(new Intent(MainActivity.this, ChattingService.class));
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new TabGameListFragment();
                //case 1:
                //    return new TabCategoryFragment();
                case 1:
                    return new TabMyChannelFragment();
                case 2:
                    return new TabSettingFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

}
