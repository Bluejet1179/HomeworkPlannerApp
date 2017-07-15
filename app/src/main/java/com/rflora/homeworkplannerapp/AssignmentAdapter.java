package com.rflora.homeworkplannerapp;

import android.app.Activity;
import android.content.Context;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * Created by flora on 7/11/2017.
 */

public class AssignmentAdapter extends BaseAdapter {
    private Activity mActivity;

    DatabaseHandler db;

    public AssignmentAdapter(Activity activity, DatabaseHandler db){
        mActivity = activity;
        this.db = db;
    }

    static class ViewHolder{
        TextView mName;
        LinearLayout.LayoutParams params;
        TextView mDescription;
        TextView mDateDue;
        TextView mLength;
        TextView mSubject;
        SeekBar mComplete;

    }

    @Override
    public int getCount() {
        return db.getAssignmentsCount();
    }

    @Override
    public Assignment getItem(int i) {
        return db.getAssignment(i+1);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.assignment_row, viewGroup, false);
            final  ViewHolder holder = new ViewHolder();
            holder.mName = (TextView) view.findViewById(R.id.nameView);
            holder.params = (LinearLayout.LayoutParams) holder.mName.getLayoutParams();
            holder.mDateDue = (TextView) view.findViewById(R.id.dateView);
            holder.mDescription = (TextView) view.findViewById(R.id.descriptionView);
            holder.mSubject = (TextView) view.findViewById(R.id.subjectView);
            holder.mLength = (TextView) view.findViewById(R.id.lengthView);
            holder.mComplete = (SeekBar) view.findViewById(R.id.completeView);
            view.setTag(holder);
        }
        final Assignment assignment = getItem(i);
        final ViewHolder holder = (ViewHolder) view.getTag();
        String name = assignment.getName();
        holder.mName.setText(name);
        holder.mDescription.setText(assignment.getDescription());
        holder.mSubject.setText(assignment.getSubject());
        holder.mLength.setText(Double.toString(assignment.getLength()) + "hrs");
        holder.mDateDue.setText(assignment.getDateDue().month + "/" + assignment.getDateDue().monthDay);
        holder.mComplete.setMax(100);
        assignment.setComplete(holder.mComplete.getProgress());



        return view;
    }

}
