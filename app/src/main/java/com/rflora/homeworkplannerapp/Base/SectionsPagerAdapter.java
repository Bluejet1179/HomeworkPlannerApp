package com.rflora.homeworkplannerapp.Base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rflora.homeworkplannerapp.Assignments.AssignmentListFragment;
import com.rflora.homeworkplannerapp.Subjects.SubjectListFragment;


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
            //TODO:Subject fragment
            SubjectListFragment subjectListFragment = new SubjectListFragment();
           return  subjectListFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "ASSIGNMENTS";
            case 1:
                return "SUBJECTS";

        }
        return null;
    }
}
