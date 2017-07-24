package com.rflora.homeworkplannerapp.Base;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.rflora.homeworkplannerapp.Assignments.Assignment;
import com.rflora.homeworkplannerapp.Assignments.AssignmentListFragment;
import com.rflora.homeworkplannerapp.R;

public class MainActivityTabs extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private CustomViewPager mViewPager;

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



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

/*
        AssignmentListFragment mAssignmentfragment = (AssignmentListFragment) mSectionsPagerAdapter.getItem(0);
        if(mAssignmentfragment.addAssignment();){
            Log.d("Fragment", "assignment frag is null ");
        }else {
            Log.d("Fragment", "not null");
        }
        */


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:Add add assignment screen
                AssignmentListFragment mAssignmentfragment = (AssignmentListFragment) getSupportFragmentManager().findFragmentByTag(getFragmentTag(0));

                Snackbar.make(view, "Added Assignment", Snackbar.LENGTH_SHORT)
                        .show();
                //TODO:TEMPPP
                try{
                    Time time = new Time();
                    time.setToNow();
                    mAssignmentfragment.addAssignment(new Assignment("Read", "pages 123", time, 1.5, "English", 0));

                }catch (NullPointerException n){
                    n.printStackTrace();
                }


                if(mAssignmentfragment == null){
                    Log.d("Fragment", "assignment frag is null ");
                }
                if(mAssignmentfragment.size() == 0){
                    mAssignmentfragment.no_HW.setVisibility(View.VISIBLE);
                }else {
                    mAssignmentfragment.no_HW.setVisibility(View.GONE);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //TODO:Add settings screen
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

private String getFragmentTag( int id )
{
    return "android:switcher:" + mViewPager.getId() + ":" + id;
}

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_activity_tabs, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText("Classes");
            return rootView;
        }
    }


}
