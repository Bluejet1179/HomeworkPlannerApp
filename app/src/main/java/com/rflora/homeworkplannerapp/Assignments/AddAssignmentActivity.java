package com.rflora.homeworkplannerapp.Assignments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.rflora.homeworkplannerapp.Base.MainActivityTabs;
import com.rflora.homeworkplannerapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.reflect.Array.set;

public class AddAssignmentActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private TextView mDateDue;
    private ImageButton mImageButton;
    private EditText mName;
    private EditText mSubject;
    private EditText mLength;
    private EditText mDescription;
    private Time dateDue;
    private boolean flag = false;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        setContentView(R.layout.activity_add_assignment2);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dateDue = new Time();

        mDateDue = (TextView) findViewById(R.id.dateTextView);
        mDateDue.setOnClickListener(this);
        mImageButton = (ImageButton) findViewById(R.id.doneButton);
        mName = (EditText) findViewById(R.id.edit_name);
        mSubject = (EditText) findViewById(R.id.edit_subject);
        mLength = (EditText) findViewById(R.id.edit_Length);
        mDescription = (EditText) findViewById(R.id.edit_description);

        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Assignment assignment = new Assignment(mName.getEditableText().toString(), mDescription.getText().toString(), dateDue, Double.parseDouble(mLength.getText().toString()), mSubject.getText().toString(), 0 );
                if(checkValues()){
                    AssignmentDatabaseHandler databaseHandler = new AssignmentDatabaseHandler(getApplicationContext());
                    databaseHandler.addAssignment(assignment);
                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, mName.getText().toString());
                    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "Assignment");
                    mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                    Intent intent = new Intent(getApplicationContext(), MainActivityTabs.class);
                    startActivity(intent);
                }
            }
        });



    }
    private boolean checkValues(){
        if(mName.getText() != null && mSubject.getText() != null && flag && mDescription.getText() != null && mLength.getText() != null ){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_down_back, R.anim.no_animation);


    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dateDue.set(dayOfMonth,month,year);
        Date date = new Date(year,month,dayOfMonth);
        SimpleDateFormat s = new SimpleDateFormat("MMM d, ''yy");
        mDateDue.setText(s.format(date));
        flag = true;
    }

    @Override
    public void onClick(View v) {
        Time t = new Time();
        t.setToNow();

        DatePickerDialog dialog = new DatePickerDialog(this, this, t.year,t.month,t.monthDay);
        dialog.show();
    }
}
