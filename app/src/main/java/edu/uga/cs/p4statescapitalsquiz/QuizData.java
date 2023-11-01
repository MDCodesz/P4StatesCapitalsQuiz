package edu.uga.cs.p4statescapitalsquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuizData {
    public static final String DEBUG_TAG = "QuizData";

    // this is a reference to our database; it is used later to run SQL commands
    private SQLiteDatabase db;
    private SQLiteOpenHelper stateCapitalsQuizDBHelper;
    private static final String[] allColumns = {
            StateCapitalsQuizDBHelper.QUIZZES_COLUMN_ID,
            StateCapitalsQuizDBHelper.QUIZZES_COLUMN_DATE,
            StateCapitalsQuizDBHelper.QUIZZES_COLUMN_TIME,
            StateCapitalsQuizDBHelper.QUIZZES_COLUMN_RESULT
    };

    public QuizData( Context context ) {
        this.stateCapitalsQuizDBHelper = StateCapitalsQuizDBHelper.getInstance( context );
    }

    // Open the database
    public void open() {
        db = stateCapitalsQuizDBHelper.getWritableDatabase();
        Log.d( DEBUG_TAG, "QuizData: db open" );
    }

    // Close the database
    public void close() {
        if( stateCapitalsQuizDBHelper != null ) {
            stateCapitalsQuizDBHelper.close();
            Log.d(DEBUG_TAG, "QuizData: db closed");
        }
    }

    public boolean isDBOpen()
    {
        return db.isOpen();
    }

    // Retrieve all job leads and return them as a List.
    // This is how we restore persistent objects stored as rows in the job leads table in the database.
    // For each retrieved row, we create a new JobLead (Java POJO object) instance and add it to the list.
    public List<Quiz> retrieveAllQuizzes() {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            // Execute the select query and get the Cursor to iterate over the retrieved rows
            cursor = db.query( StateCapitalsQuizDBHelper.TABLE_QUIZZES, allColumns,
                    null, null, null, null, null );

            // collect all job leads into a List
            if( cursor != null && cursor.getCount() > 0 ) {

                while( cursor.moveToNext() ) {

                    if( cursor.getColumnCount() >= 5) {

                        // get all attribute values of this job lead
                        columnIndex = cursor.getColumnIndex( StateCapitalsQuizDBHelper.QUIZZES_COLUMN_ID );
                        long id = cursor.getLong( columnIndex );
                        columnIndex = cursor.getColumnIndex( StateCapitalsQuizDBHelper.QUIZZES_COLUMN_DATE );
                        long longDate = cursor.getLong( columnIndex ); // Retrieve date as a long value
                        Date date = new Date(longDate); // Convert the long value to a Date object
                        columnIndex = cursor.getColumnIndex( StateCapitalsQuizDBHelper.QUIZZES_COLUMN_TIME );
                        String timeStr = cursor.getString( columnIndex ); // Retrieve time as a string
                        Time time = Time.valueOf(timeStr); // Convert the string to a Time object
                        columnIndex = cursor.getColumnIndex( StateCapitalsQuizDBHelper.QUIZZES_COLUMN_RESULT );
                        Integer result = cursor.getInt( columnIndex );

                        // create a new Quiz object and set its date to the retrieved values
                        Quiz quiz = new Quiz( date, time, result );
                        quiz.setId(id); // set the id (the primary key) of this object
                        // add it to the list
                        quizzes.add( quiz );
                        Log.d(DEBUG_TAG, "Retrieved Quiz: " + quiz);
                    }
                }
            }
            if( cursor != null )
                Log.d( DEBUG_TAG, "Number of records from DB: " + cursor.getCount() );
            else
                Log.d( DEBUG_TAG, "Number of records from DB: 0" );
        }
        catch( Exception e ){
            Log.d( DEBUG_TAG, "Exception caught: " + e );
        }
        finally{
            // we should close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        // return a list of retrieved job leads
        return quizzes;
    }

    // Store a new job lead in the database.
    public Quiz storeQuiz( Quiz quiz ) {

        // Prepare the values for all of the necessary columns in the table
        // and set their values to the variables of the Quiz argument.
        // This is how we are providing persistence to a Quiz (Java object) instance
        // by storing it as a new row in the database table representing job leads.
        ContentValues values = new ContentValues();
        values.put(StateCapitalsQuizDBHelper.QUIZZES_COLUMN_DATE, quiz.getQuizDate().getTime());  // Store date as a long value
        values.put(StateCapitalsQuizDBHelper.QUIZZES_COLUMN_TIME, quiz.getQuizTime().toString());  // Store time as a string
        values.put(StateCapitalsQuizDBHelper.QUIZZES_COLUMN_RESULT, quiz.getResult());

        // Insert the new row into the database table;
        // The id (primary key) is automatically generated by the database system
        // and returned as from the insert method call.
        long id = db.insert( StateCapitalsQuizDBHelper.TABLE_QUIZZES, null, values );

        // store the id (the primary key) in the Quiz instance, as it is now persistent
        quiz.setId( id );

        Log.d( DEBUG_TAG, "Stored new job lead with id: " + String.valueOf( quiz.getId() ) );

        return quiz;
    }
    
}
