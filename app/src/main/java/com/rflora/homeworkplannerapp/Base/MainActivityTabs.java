package com.rflora.homeworkplannerapp.Base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.rflora.homeworkplannerapp.Assignments.AddAssignmentActivity;
import com.rflora.homeworkplannerapp.Assignments.Assignment;
import com.rflora.homeworkplannerapp.Assignments.AssignmentDatabaseHandler;
import com.rflora.homeworkplannerapp.Assignments.AssignmentListFragment;
import com.rflora.homeworkplannerapp.R;
import com.rflora.homeworkplannerapp.Subjects.Subject;

public class MainActivityTabs extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private CustomViewPager mViewPager;
    private Activity mActivity = this;
    private FloatingActionButton mActionButton;
    private AdView mAdView;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (CustomViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setPagingEnabled(false);
        mActionButton = (FloatingActionButton) findViewById(R.id.fab);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        MobileAds.initialize(this,"ca-app-pub-5488885107013405/1612346992");
        mAdView = (AdView) findViewById(R.id.bottom_adview);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //TODO:Change app unit id to one from admob





        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//TODO:Add button to add subjects

        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AddAssignmentActivity.class);

                startActivity(intent);
            }

            }
        );
    }





        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main_activity_tabs, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                //TODO:Add dialog for subjects
                //TODO:Get notification preferences stored in database
                //TODO:Change the subject field in assignment builder to have a dropdown list of subject names
                //TODO:Add settings screen with nightly hw reminders and in app purchase to remove ads
                //TODO:Get the in app purchasing working
                //TODO:Get notifications working(execute at end time and give yes and no option to open the add assignment activity)
                //TODO:Icon and app store page
                //TODO:Bug testing
                //TODO:Publish to play store!

                return true;
            }

            return super.onOptionsItemSelected(item);
        }

    private String getFragmentTag(int id) {
        return "android:switcher:" + mViewPager.getId() + ":" + id;
    }





}
