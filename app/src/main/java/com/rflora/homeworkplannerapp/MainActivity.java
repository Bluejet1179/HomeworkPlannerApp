package com.rflora.homeworkplannerapp;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AssignmentAdapter mAdapter;
    private ListView mAssignment_ListView;
    private DatabaseHandler databaseHandler;
    private List<Assignment> mAssignments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Activity", "Starting...");
        databaseHandler = new DatabaseHandler(getApplicationContext());
        mAssignment_ListView = (ListView) findViewById(R.id.assignment_ListView);
        Time time = new Time();
        //time.set(12,5,2017);
        time.setToNow();
        Log.d("SQL", "Date before SQL =" + (time.month+1) +  "/" + time.monthDay);
        databaseHandler.addAssignment(new Assignment("Read", "pages 123", time, 1.5, "English", 0));
        databaseHandler.addAssignment(new Assignment("TextBook", "pages 796", time, 0.5, "Math", 0));
//        Assignment test = databaseHandler.getAssignment(1);
  //      Assignment test2 = databaseHandler.getAssignment(2);
    //    Log.d("SQL", test.toString());
      //  Log.d("SQL", test2.toString());
        //databaseHandler.deleteAllAssignments();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter = new AssignmentAdapter(this, databaseHandler);
        mAssignment_ListView.setAdapter(mAdapter);
        mAssignments = databaseHandler.getAllAssignments();

/*
        int size = mAssignment_ListView.getCount();
        for(int i = 1; i < size; i++) {
            final Assignment assignment = mAssignments.get(i);
            View view = mAssignment_ListView.getChildAt(i);
            SeekBar seekBar1 = (SeekBar) view.findViewById(R.id.completeView);
            seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    assignment.setComplete(seekBar.getProgress());
                    Log.d("Seek", "Changed " + assignment.isComplete());
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
        */




        mAssignment_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Seek", "You clicked ");
                Assignment a = (Assignment) adapterView.getItemAtPosition(i);
            }
        });
    }


}
