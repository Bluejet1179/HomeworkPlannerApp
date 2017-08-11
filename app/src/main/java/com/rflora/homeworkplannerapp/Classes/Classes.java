package com.rflora.homeworkplannerapp.Classes;

import android.text.format.Time;

/**
 * Created by flora on 7/25/2017.
 */

public class Classes {

    private String mName;
    private Time mEndTime;

    public Classes(String name, Time endTime){
        mName = name;
        mEndTime = endTime;
    }

    public Classes(String name, int hours, int minutes){
        Time time = new Time();
        time.set(0,minutes,hours,0,0,0);
        mName = name;
        mEndTime = time;
    }

    public String getName() {
        return mName;
    }

    public Time getEndTime() {
        return mEndTime;
    }


}
