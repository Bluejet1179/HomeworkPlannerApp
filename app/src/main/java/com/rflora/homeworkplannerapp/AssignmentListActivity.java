package com.rflora.homeworkplannerapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class AssignmentListActivity extends AppCompatActivity {

   // private AssignmentAdapter mAdapter;
    private ListView mAssignment_ListView;
    private DatabaseHandler databaseHandler;

    private AssignmentCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Log.d("Activity", "Starting...");
        databaseHandler = new DatabaseHandler(getApplicationContext());
        mAssignment_ListView = (ListView) findViewById(R.id.assignment_ListView);
        Time time = new Time();
        time.setToNow();
        databaseHandler.addAssignment(new Assignment("Read", "pages 123", time, 1.5, "English", 0));
        databaseHandler.addAssignment(new Assignment("TextBook", "pages 796", time, 0.5, "Math", 0));
       // Assignment test = databaseHandler.getAssignment(1);
  //      Assignment test2 = databaseHandler.getAssignment(2);
    //  Log.d("SQL", test.toString());
      //  Log.d("SQL", test2.toString());
        //databaseHandler.deleteAllAssignments();

    }

    @Override
    protected void onStart() {
        super.onStart();
       // mAdapter = new AssignmentAdapter(this, databaseHandler);
        //mAssignment_ListView.setAdapter(mAdapter);

        mCursorAdapter = new AssignmentCursorAdapter(AssignmentListActivity.this, databaseHandler.getCursor(), databaseHandler, this, mAssignment_ListView);
        mAssignment_ListView.setAdapter(mCursorAdapter);

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

            case R.id.action_add_assignment:
                // User chose the "add" action, mark the current item
                Intent addIntent = new Intent(AssignmentListActivity.this, AddAssignmentActivity.class);
                startActivity(addIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
