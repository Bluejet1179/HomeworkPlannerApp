package com.rflora.homeworkplannerapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.name;

/**
 * Created by flora on 7/17/2017.
 */

public class AssignmentCursorAdapter extends CursorAdapter {

    private Context mContext;
    DatabaseHandler db;
    private LayoutInflater inflater;
    private Cursor mCursor;
    private Activity mActivity;
    private ListView mListView;


    public AssignmentCursorAdapter(Context context, Cursor c, DatabaseHandler DB, Activity activity, ListView listView) {
        super(context, c, 0);
        mContext = context;
        db = DB;
        mCursor = c;
        mActivity = activity;
        mListView = listView;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.d("SQL", "CursorAdapter Constructed" + c.getCount());
    }

    @Override
    public Assignment getItem(int position) {
        return db.getAssignment(position);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.d("SQL", "newView");
        return inflater.inflate(R.layout.assignment_row, parent, false);
        //return LayoutInflater.from(context).inflate(R.layout.assignment_row, parent, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.d("SQL", "bindview");
        TextView mName = (TextView) view.findViewById(R.id.nameView);
        TextView mDateDue = (TextView) view.findViewById(R.id.dateView);
        TextView mDescription = (TextView) view.findViewById(R.id.descriptionView);
        TextView mSubject = (TextView) view.findViewById(R.id.subjectView);
        TextView mLength = (TextView) view.findViewById(R.id.lengthView);
        SeekBar mComplete = (SeekBar) view.findViewById(R.id.completeView);
        Time dateDue = new Time();
        dateDue.set(cursor.getInt(3), cursor.getInt(4), cursor.getInt(5));
        Assignment assignment = new Assignment(cursor.getInt(0),
                cursor.getString(1),//name
                cursor.getString(2),//des
                dateDue,//dateDue
                cursor.getDouble(6),//length
                cursor.getString(7),//subject
                cursor.getInt(8));//isComplete

        mName.setText(assignment.getName());
        mDescription.setText(assignment.getDescription());
        mSubject.setText(assignment.getSubject());

        if (assignment.getLength() <= 1.0) {
            mLength.setText(Double.toString(assignment.getLength()) + " hr");
        } else {
            mLength.setText(Double.toString(assignment.getLength()) + " hrs");
        }
        mDateDue.setText(assignment.getDateDue().month + "/" + assignment.getDateDue().monthDay);
        mComplete.setMax(100);
        attachProgressUpdatedListener(mComplete, assignment);
        mComplete.setProgress(assignment.isComplete());

    }

    private void attachProgressUpdatedListener(SeekBar seekBar, final Assignment assignment) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(final SeekBar seekBar, int i, boolean b) {

                Log.d("Seek", Integer.toString(seekBar.getProgress()) + " ID: " + assignment.getId());
                assignment.setComplete(seekBar.getProgress());
//                db.updateAssignment(assignment);
                if (seekBar.getProgress() == 100) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Are you sure you want to delete " + assignment.getName() + "?");
                    builder.setTitle("Warning!");
                    builder.setIcon(R.drawable.ic_delete_forever_black_24dp);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String name = assignment.getName();

                            db.deleteAssignment(assignment);
                            mCursor = db.getCursor();
                            mActivity.recreate();


                            Toast.makeText(mContext.getApplicationContext(), "Deleted " + name, Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
