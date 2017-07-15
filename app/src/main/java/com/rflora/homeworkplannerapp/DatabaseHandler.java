package com.rflora.homeworkplannerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.Time;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.provider.Contacts.SettingsColumns.KEY;

/**
 * Created by flora on 7/8/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "assignmentBook";

    // Contacts table name
    private static final String TABLE_ASSIGNMENTS = "Assignments";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESCRIPTION = "descrption";
    //private static final String KEY_DAYDUE = "dayDue";

    private static final String KEY_DAYDUE = "dayDue";
    private static final String KEY_MONTHDUE = "monthDue";
    private static final String KEY_YEARDUE = "yearDue";

    private static final String KEY_LENGTH = "length";
    private static final String KEY_SUBJECT = "subject";
    private static final String KEY_ISCOMPLETE = "isComplete";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ASSIGNMENTS_TABLE = "CREATE TABLE " + TABLE_ASSIGNMENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_DAYDUE + " INTEGER,"
                + KEY_MONTHDUE + " INTEGER,"
                + KEY_YEARDUE + " INTEGER,"
                + KEY_LENGTH + " REAL,"
                + KEY_SUBJECT + " TEXT,"
                + KEY_ISCOMPLETE + " INTEGER" + ")";
        db.execSQL(CREATE_ASSIGNMENTS_TABLE);
        Log.d("SQL", "Created Database(onCreate)");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSIGNMENTS);

        // Create tables again
        onCreate(db);
    }

    // Adding new assignment
    public void addAssignment(Assignment assignment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, assignment.getName());
        values.put(KEY_DESCRIPTION, assignment.getDescription());
        values.put(KEY_DAYDUE, assignment.getDateDue().monthDay);
        values.put(KEY_MONTHDUE, assignment.getDateDue().month);
        values.put(KEY_YEARDUE, assignment.getDateDue().year);
        values.put(KEY_LENGTH, assignment.getLength());
        values.put(KEY_SUBJECT, assignment.getSubject());
        values.put(KEY_ISCOMPLETE, assignment.isComplete());

        // Inserting Row
        db.insert(TABLE_ASSIGNMENTS, null, values);
        db.close(); // Closing database connection
        Log.d("SQL", "Added Assignment");
    }


    // Getting single assignment
    public Assignment getAssignment(int id) {
        Log.d("SQL", "Attempting to get assignment");
        SQLiteDatabase db = this.getReadableDatabase();
        Time dateDue = new Time();

        Cursor cursor = db.query(TABLE_ASSIGNMENTS,
                new String[] { KEY_ID, KEY_NAME, KEY_DESCRIPTION, KEY_DAYDUE, KEY_MONTHDUE, KEY_YEARDUE, KEY_LENGTH, KEY_SUBJECT, KEY_ISCOMPLETE },
                KEY_ID + "=?",
                new String[] { String.valueOf(id) },
                null, null, null, null);
        Log.d("SQL", "Made cursor");
        if (cursor != null) {
            cursor.moveToFirst();
        }
        dateDue.set(cursor.getInt(3), cursor.getInt(4), cursor.getInt(5));
        Assignment assignment = new Assignment(cursor.getInt(0),
                    cursor.getString(1),//name
                    cursor.getString(2),//des
                    dateDue,//dateDue
                    cursor.getDouble(6),//length
                    cursor.getString(7),//subject
                    cursor.getInt(8));//isComplete
            // return assignment
            Log.d("SQL", "Got assignment");
            cursor.close();
            return assignment;
    }

    // Getting All Assignments
    public List<Assignment> getAllAssignments() {
        List<Assignment> contactList = new ArrayList<Assignment>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ASSIGNMENTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding assignment to list
                contactList.add(getAssignment(cursor.getInt(0))
                );
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }



    // Getting assignments Count
    public int getAssignmentsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ASSIGNMENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
       // cursor.close();

        // return count
        return cursor.getCount();
    }

    // Deleting single assignment
    public void deleteAssignment(Assignment assignment) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ASSIGNMENTS, KEY_ID + " = ?",
                new String[] { String.valueOf(assignment.getId()) });
        db.close();
    }

    //Updating a single assignment
    public int updateAssignment(Assignment assignment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, assignment.getName());
        values.put(KEY_DESCRIPTION, assignment.getDescription());
        values.put(KEY_DAYDUE, assignment.getDateDue().monthDay);
        values.put(KEY_MONTHDUE, assignment.getDateDue().month);
        values.put(KEY_YEARDUE, assignment.getDateDue().year);
        values.put(KEY_LENGTH, assignment.getLength());
        values.put(KEY_SUBJECT, assignment.getSubject());
        values.put(KEY_ISCOMPLETE, assignment.isComplete());

        // updating row
        return db.update(TABLE_ASSIGNMENTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(assignment.getId()) });
    }

    public void deleteAllAssignments(){
        List<Assignment> assignmentList;
        assignmentList = getAllAssignments();
        for(Assignment assignment: assignmentList){
            deleteAssignment(assignment);
        }
    }













}
