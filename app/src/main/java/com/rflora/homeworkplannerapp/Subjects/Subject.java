package com.rflora.homeworkplannerapp.Subjects;

import android.text.format.Time;


/**
 * Created by flora on 7/25/2017.
 */

public class Subject {

    private String mName;
    private Time mEndTime;
    private int mID = 0;

    public Subject(String name, int minutes, int id) {
        mEndTime = new Time();
        mEndTime.set(0,0,0,0,0,0);
        mEndTime.hour = minutes / 60;
        mEndTime.minute = minutes % 60;
        mID = id;
        mName = name;
    }

    public Subject(String name, int minutes) {
        mEndTime = new Time();
        mEndTime.set(0,0,0,0,0,0);
        mEndTime.hour = minutes / 60;
        mEndTime.minute = minutes % 60;
        mName = name;
    }


    public String getName() {
        return mName;
    }

    public Time getEndTime() {
        return mEndTime;
    }

    public int getEndTimeMinutes(){
        return mEndTime.hour * 60 + mEndTime.minute;
    }

    public int getID(){
        return mID;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "mName='" + mName + '\'' +
                ", mEndTime=" + mEndTime +
                ", mID=" + mID +
                '}';
    }
}
