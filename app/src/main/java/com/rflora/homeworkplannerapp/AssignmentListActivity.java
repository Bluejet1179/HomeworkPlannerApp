package com.rflora.homeworkplannerapp;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.R.attr.fragment;

public class AssignmentListActivity extends AppCompatActivity {

   // private AssignmentAdapter mAdapter;
    private AssignmentDatabaseHandler databaseHandler;
    private RecyclerView mRecyclerView;
    private RecyclerAdapterAssignment mAdapterAssignment;
    private FloatingActionButton addButton;
    private AssignmentListFragment mAssignmentfragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        addButton = (FloatingActionButton) findViewById(R.id.add_assignment_button);
        setSupportActionBar(myToolbar);
        Log.d("Activity", "Starting...");
        databaseHandler = new AssignmentDatabaseHandler(getApplicationContext());
        mAssignmentfragment = new AssignmentListFragment();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Time time = new Time();
                time.setToNow();
                mAssignmentfragment.addAssignment(new Assignment("Read", "pages 123", time, 1.5, "English", 0));
                Snackbar.make(v,"Added Assignment", Snackbar.LENGTH_SHORT).show();
                if(mAssignmentfragment.size() == 0){
                    mAssignmentfragment.no_HW.setVisibility(View.VISIBLE);
                }else {
                    mAssignmentfragment.no_HW.setVisibility(View.GONE);
                }
            }
        });





        //databaseHandler.addAssignment(new Assignment("TextBook", "pages 796", time, 0.5, "Math", 0));
        //databaseHandler.deleteAllAssignments();



    }

    @Override
    protected void onStart() {
        super.onStart();




        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_rel_container, mAssignmentfragment)
                .commit();



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;



            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
