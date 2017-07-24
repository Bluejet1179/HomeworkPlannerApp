package com.rflora.homeworkplannerapp.Base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.format.Time;

import com.rflora.homeworkplannerapp.Assignments.Assignment;
import com.rflora.homeworkplannerapp.Assignments.AssignmentListFragment;
import com.rflora.homeworkplannerapp.Base.MainActivityTabs.PlaceholderFragment;
import com.rflora.homeworkplannerapp.R;

/**
 * Created by flora on 7/23/2017.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {
private FragmentManager mFragmentManager;
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position == 0) {
            AssignmentListFragment assignmentListFragment = new AssignmentListFragment();

            return assignmentListFragment;
        }else {
            //TODO:Classes fragment
           return  PlaceholderFragment.newInstance(position + 1);
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "ASSIGNMENTS";
            case 1:
                return "CLASSES";

        }
        return null;
    }
}
