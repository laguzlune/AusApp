package com.example.maist.ausapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    public FloatingActionButton fab;
    private Animation animSlideUp;
    private Animation animSlideDown;
    private ViewPagerAdapter adapter;

    boolean enterEdited = false;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        fab = (FloatingActionButton) findViewById(R.id.floating_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    AddItemDialog.AddDialogListener frag = (AddItemDialog.AddDialogListener)adapter.getItem(0);
                    showAddItemDialog(frag);
                }
            }
        });


        animSlideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);

        //currentFragment = this.getFragmentManager().findFragmentById()
        Log.d("MainActivity","onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_edit) {
            if (!enterEdited) {
                fab.setVisibility(View.VISIBLE);
                fab.startAnimation(animSlideUp);
                enterEdited = true;
            } else {
                fab.startAnimation(animSlideDown);
                fab.setVisibility(View.GONE);
                enterEdited = false;
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CommuMain(), "การสื่อสาร");
        adapter.addFragment(new QuizList(), "แบบฝึกหัด");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void showAddItemDialog(AddItemDialog.AddDialogListener target_frag) {
        FragmentManager fm = getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putString("title", "เลือกรูปภาพ");
        AddItemDialog alertDialog = new AddItemDialog();
        alertDialog.setArguments(args);
        alertDialog.setAddDialogListener(target_frag);
        alertDialog.show(fm , "ssss");
    }

}
