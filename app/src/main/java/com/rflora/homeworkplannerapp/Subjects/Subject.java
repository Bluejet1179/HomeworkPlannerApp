package com.rflora.homeworkplannerapp.Subjects;

import android.text.format.Time;


/**
 * Created by flora on 7/25/2017.
 */

public class Subject {

    private String mName;
    private Time mEndTime;
    private int mID = 0;
    private boolean mNotifications;

    public int isNotifications() {
        if(mNotifications){
            return 1;
        }else {
            return 0;
        }
    }

    public void setNotifications(boolean notifications) {
        mNotifications = notifications;
    }

    public Subject(String name, int minutes, int id, boolean notifications) {
        mEndTime = new Time();
        mEndTime.set(0,0,0,0,0,0);
        mEndTime.hour = minutes / 60;
        mEndTime.minute = minutes % 60;
        mID = id;
        mName = name;
        mNotifications = notifications;
    }

    public Subject(String name, int minutes, boolean notifications) {
        mEndTime = new Time();
        mEndTime.set(0,0,0,0,0,0);
        mEndTime.hour = minutes / 60;
        mEndTime.minute = minutes % 60;
        mName = name;
        mNotifications = notifications;
    }
    public boolean notificationBool(){
        return mNotifications;
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
