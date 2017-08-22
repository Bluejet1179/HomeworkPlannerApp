package com.rflora.homeworkplannerapp.Base;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.rflora.homeworkplannerapp.Assignments.AddAssignmentActivity;
import com.rflora.homeworkplannerapp.Assignments.AssignmentDatabaseHandler;
import com.rflora.homeworkplannerapp.R;
import com.rflora.homeworkplannerapp.Subjects.Subject;
import com.rflora.homeworkplannerapp.Subjects.SubjectListFragment;

import static android.R.attr.animationDuration;
import static android.R.attr.id;

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





        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tabLayout.getSelectedTabPosition() == 0) {
                    Intent intent = new Intent(getApplicationContext(), AddAssignmentActivity.class);
                    startActivity(intent);
                }else {
                    LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
                    View mView = layoutInflater.inflate(R.layout.add_subject_dialog, null);
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivityTabs.this);
                    dialog.setView(mView);
                    final EditText name = (EditText)mView.findViewById(R.id.dialog_name);
                    final TimePicker timePicker = (TimePicker)mView.findViewById(R.id.dialog_timepicker);
                    dialog.setCancelable(false)
                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Build and add class
                                   int minutes = timePicker.getHour() * 60 + timePicker.getMinute();
                                    Subject subject = new Subject(name.getText().toString(), minutes, false);
                                    AssignmentDatabaseHandler db = new AssignmentDatabaseHandler(getApplicationContext());
                                    db.addSubject(subject);
                                    SubjectListFragment subjectListFragment =(SubjectListFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.container + ":" + mViewPager.getCurrentItem());
                                    subjectListFragment.update();

                                    Log.d("Dialog", "Added subject" + name.getText().toString() + minutes);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setTitle("Add a Class");
                    AlertDialog subjectDialog = dialog.create();
                    subjectDialog.show();
                }
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
