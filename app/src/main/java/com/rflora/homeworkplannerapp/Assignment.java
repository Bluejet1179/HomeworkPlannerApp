package com.rflora.homeworkplannerapp;


import android.text.format.Time;



/**
 * Created by flora on 7/8/2017.
 */

public class Assignment {
    //Member Variables
    String mName;
    String mDescription;
    Time mDateDue;
    double mLength;
    String mSubject;
    int mIsComplete;
    int id = 0;

    public Assignment() {

    }

    public Assignment(int id, String name, String description, Time dateDue, double lengthHours, String subject, int isComplete) {
        this.id = id;
        setName(name);
        setDescription(description);
        setDateDue(dateDue);
        setLength(lengthHours);
        setSubject(subject);
        mIsComplete = isComplete;
    }

    public Assignment(String name, String description, Time dateDue, double lengthHours, String subject, int isComplete) {
        setName(name);
        setDescription(description);
        setDateDue(dateDue);
        setLength(lengthHours);
        setSubject(subject);
        mIsComplete = isComplete;
    }

    public boolean isDue() {
        Time current = new Time();
        current.setToNow();
        if (mDateDue.after(current)) {
            return true;
        } else {
            return false;
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Time getDateDue() {
        return mDateDue;
    }

    public void setDateDue(Time dateDue) {
        mDateDue = dateDue;
    }


    public double getLength() {
        return mLength;
    }

    public void setLength(double length) {
        mLength = length;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public int isComplete() {
        return mIsComplete;
    }

    public void setComplete(int complete) {
        mIsComplete = complete;
    }


    @Override
    public String toString() {
        return "ID = " + Integer.toString(id) + "\n" +
                "Name = " + mName + "\n" +
                "Description = " + mDescription + "\n" +
                "DateDue = " + Integer.toString(mDateDue.month + 1) + "/" + Integer.toString(mDateDue.monthDay) + "/" + Integer.toString(mDateDue.year) + "\n" +
                "Length = " + Double.toString(mLength) + "\n" +
                "Subject = " + mSubject + "\n";
    }
}
