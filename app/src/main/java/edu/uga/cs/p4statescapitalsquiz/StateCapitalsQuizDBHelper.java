package edu.uga.cs.p4statescapitalsquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * This is a SQLiteOpenHelper class, which Android uses to create, upgrade, delete an SQLite database
 * in an app.
 *
 * This class is a singleton, following the Singleton Design Pattern.
 * Only one instance of this class will exist.  To make sure, the
 * only constructor is private.
 * Access to the only instance is via the getInstance method.
 */
public class StateCapitalsQuizDBHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "StateCapitalsQuizDBHelper";

    private static final String DB_NAME = "statecapitalsquiz.db";
    private static final int DB_VERSION = 1;

    // Define all names (strings) for table and column names for questions table
    public static final String TABLE_QUESTIONS = "questions";
    public static final String QUESTIONS_COLUMN_ID = "questionId";
    public static final String QUESTIONS_COLUMN_STATE = "state";
    public static final String QUESTIONS_COLUMN_CAPITAL = "capital";
    public static final String QUESTIONS_COLUMN_CITYONE = "cityOne";
    public static final String QUESTIONS_COLUMN_CITYTWO = "cityTwo";

    // Define all names (strings) for table and column names for quizzes table
    public static final String TABLE_QUIZZES = "quizzes";
    public static final String QUIZZES_COLUMN_ID = "quizId";
    public static final String QUIZZES_COLUMN_DATE = "quizDate";
    public static final String QUIZZES_COLUMN_TIME = "quizTime";
    public static final String QUIZZES_COLUMN_RESULT = "quizResult";

    // Define all names (strings) for table and column names for relationship table
    public static final String TABLE_RELATIONSHIP = "question_quiz_relationship";
    public static final String RELATIONSHIP_COLUMN_ID = "relationshipId";
    public static final String RELATIONSHIP_COLUMN_QUESTION_ID = "questionId";
    public static final String RELATIONSHIP_COLUMN_QUIZ_ID = "quizId";
    public static final String RELATIONSHIP_COLUMN_USER_ANSWER = "userAnswer";

    // This is a reference to the only instance for the helper.
    private static StateCapitalsQuizDBHelper helperInstance;

    // A Create table SQL statement to create a table for job leads.
    // Note that _id is an auto increment primary key, i.e. the database will
    // automatically generate unique id values as keys.
    private static final String CREATE_QUESTIONS =
        "create table " + TABLE_QUESTIONS + " ("
                + QUESTIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QUESTIONS_COLUMN_STATE + " TEXT, "
                + QUESTIONS_COLUMN_CAPITAL + " TEXT, "
                + QUESTIONS_COLUMN_CITYONE + " TEXT, "
                + QUESTIONS_COLUMN_CITYTWO + " TEXT"
                + ")";

    private static final String CREATE_QUIZZES =
        "CREATE TABLE " + TABLE_QUIZZES + " (" +
                QUIZZES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QUIZZES_COLUMN_DATE + " DATE, " +
                QUIZZES_COLUMN_TIME + " TIME, " +
                QUIZZES_COLUMN_RESULT + " INTEGER" +
                ")";

    private static final String CREATE_RELATIONSHIP =
        "CREATE TABLE " + TABLE_RELATIONSHIP + " (" +
                RELATIONSHIP_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RELATIONSHIP_COLUMN_QUESTION_ID + " INTEGER, " +
                RELATIONSHIP_COLUMN_QUIZ_ID + " INTEGER, " +
                RELATIONSHIP_COLUMN_USER_ANSWER + " TEXT" +
                ")";


    // Note that the constructor is private!
    // So, it can be called only from
    // this class, in the getInstance method.
    private StateCapitalsQuizDBHelper( Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
    }

    // Access method to the single instance of the class.
    // It is synchronized, so that only one thread can executes this method, at a time.
    public static synchronized StateCapitalsQuizDBHelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new StateCapitalsQuizDBHelper( context.getApplicationContext() );
        }
        return helperInstance;
    }

    // We must override onCreate method, which will be used to create the database if
    // it does not exist yet.
    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL(CREATE_QUESTIONS);
        db.execSQL(CREATE_QUIZZES);
        db.execSQL(CREATE_RELATIONSHIP);
        Log.d( DEBUG_TAG, "Table " + TABLE_QUESTIONS + " created" );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZZES + " created" );
        Log.d( DEBUG_TAG, "Table " + TABLE_RELATIONSHIP + " created" );
    }

    // We should override onUpgrade method, which will be used to upgrade the database if
    // its version (DB_VERSION) has changed.  This will be done automatically by Android
    // if the version will be bumped up, as we modify the database schema.
    // ******* May need to handle table upgrade separately ****
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "drop table if exists " + TABLE_QUESTIONS );
        db.execSQL( "drop table if exists " + TABLE_QUIZZES );
        db.execSQL( "drop table if exists " + TABLE_RELATIONSHIP );
        onCreate( db );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUESTIONS + " upgraded" );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZZES + " upgraded" );
        Log.d( DEBUG_TAG, "Table " + TABLE_RELATIONSHIP + " upgraded" );
    }
}
