package com.rflora.homeworkplannerapp.Subjects;

import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.rflora.homeworkplannerapp.Assignments.AssignmentDatabaseHandler;
import com.rflora.homeworkplannerapp.Assignments.RecyclerAdapterAssignment;
import com.rflora.homeworkplannerapp.R;

import java.util.List;

/**
 * Created by flora on 8/16/2017.
 */

public class RecyclerAdapterSubjects extends RecyclerView.Adapter<RecyclerAdapterSubjects.ViewHolder> {

    private AssignmentDatabaseHandler mDatabaseHandler;
    private List<Subject> mSubjectList;
    private SubjectListFragment mSubjectListFragment;


    public RecyclerAdapterSubjects(AssignmentDatabaseHandler db, SubjectListFragment fragment) {
        mDatabaseHandler = db;
        mSubjectList = mDatabaseHandler.getAllSubjects();
        setHasStableIds(true);
        mSubjectListFragment = fragment;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mEndTime;
        public Switch mNotifications;

        public ViewHolder(View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.text_subject_name);
            mEndTime = (TextView) v.findViewById(R.id.text_end_time);
            mNotifications = (Switch) v.findViewById(R.id.notification_option);
        }

    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapterSubjects.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        //TODO: Link up views
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mName.setText(mSubjectList.get(position).getName());
        int hours = mSubjectList.get(position).getEndTimeMinutes() / 60;
        int minutes = mSubjectList.get(position).getEndTimeMinutes() % 60;
        if(minutes >= 10) {
            holder.mEndTime.setText(hours + ":" + minutes);
        }else {
            holder.mEndTime.setText(hours + ":" + "0" + minutes);
        }
        holder.mNotifications.setChecked(mSubjectList.get(position).notificationBool());
        holder.mNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSubjectList.get(position).setNotifications(holder.mNotifications.isChecked());
                updateDatabase();
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return mSubjectList.get(position).getID();
    }

    @Override
    public int getItemCount() {
        return mSubjectList.size();
    }

    public void updateDatabase() {
        mDatabaseHandler.deleteAllSubjects();
        mDatabaseHandler.addSubjects(mSubjectList);
        notifyDataSetChanged();
    }

    public void updateList() {
        mSubjectList = mDatabaseHandler.getAllSubjects();
        notifyDataSetChanged();
    }
}