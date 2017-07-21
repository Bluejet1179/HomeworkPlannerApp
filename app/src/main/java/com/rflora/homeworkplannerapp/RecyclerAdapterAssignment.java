package com.rflora.homeworkplannerapp;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;


public class RecyclerAdapterAssignment extends RecyclerView.Adapter<RecyclerAdapterAssignment.ViewHolder> {
    private List<Assignment> mAssignments;
    private AssignmentDatabaseHandler mDatabaseHandler;
    private AssignmentListFragment mAssignmentListFragment;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        //TODO:Add all views into constructor
        public TextView mName;
        public TextView mDateDue;
        public TextView mDescription;
        public TextView mSubject;
        public TextView mLength;
        public SeekBar mComplete;
        public ViewHolder(View v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.nameView);
            mDateDue = (TextView) v.findViewById(R.id.dateView);
            mDescription = (TextView) v.findViewById(R.id.descriptionView);
            mSubject = (TextView) v.findViewById(R.id.subjectView);
            mLength = (TextView) v.findViewById(R.id.lengthView);
            mComplete = (SeekBar) v.findViewById(R.id.completeView);
        }
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerAdapterAssignment(AssignmentDatabaseHandler db, AssignmentListFragment a) {
        mDatabaseHandler = db;
        mAssignments = db.getAllAssignments();
        setHasStableIds(true);
        mAssignmentListFragment = a;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerAdapterAssignment.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        //TODO: Link up views
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignment_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //TODO:Get data from list and set data to viewholder
        holder.mName.setText(mAssignments.get(position).getName());
        holder.mDescription.setText(mAssignments.get(position).getDescription());
        holder.mSubject.setText(mAssignments.get(position).getSubject());
        if (mAssignments.get(position).getLength() <= 1.0) {
            holder.mLength.setText(Double.toString(mAssignments.get(position).getLength()) + " hr");
        } else {
            holder.mLength.setText(Double.toString(mAssignments.get(position).getLength()) + " hrs");
        }
        Time time = mAssignments.get(position).getDateDue();

        Time currentTime = new Time();
        currentTime.setToNow();
        if(currentTime.month == time.month && currentTime.monthDay == time.monthDay){
            holder.mDateDue.setText("Due Today!");
            holder.mDateDue.setTextColor(Color.RED);
        }else{
            holder.mDateDue.setText(time.month + "/" + time.monthDay);
            holder.mDateDue.setTextColor(Color.BLACK);
        }
        holder.mComplete.setMax(100);
        holder.mComplete.setProgress(mAssignments.get(position).isComplete());

        holder.mComplete.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(holder.mComplete.getProgress() == 100){
                    mAssignments.remove(position);
                    updateDatabase();
                    if(getItemCount() == 0) {
                        mAssignmentListFragment.no_HW.setVisibility(View.VISIBLE);
                    }else {
                        mAssignmentListFragment.no_HW.setVisibility(View.GONE);
                    }
                    Snackbar.make(holder.itemView, "Removed Assignment", Snackbar.LENGTH_SHORT).show();


                }else {
                    mAssignments.get(position).setComplete(holder.mComplete.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateDatabase();


            }
        });

    }

    @Override
    public long getItemId(int position) {
        return mAssignments.get(position).getId();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mAssignments.size();
    }
    public void updateDatabase(){
        mDatabaseHandler.deleteAllAssignments();
        mDatabaseHandler.addAssignments(mAssignments);
        notifyDataSetChanged();
    }
    public void updateList(){
        mAssignments = mDatabaseHandler.getAllAssignments();
        notifyDataSetChanged();
    }
}