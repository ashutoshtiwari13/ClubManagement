package com.ruuha.bmscms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "StudentManager.db";

    public static final String TABLE_NAME = "student";
    public static final String _USN = "_id";
    public static final String COLUMN_NAME = "student_name";
    public static final String COLUMN_EMAIL = "student_email";
    public static final String COLUMN_BRANCH = "student_branch";
    public static final String COLUMN_CLUB ="student_club";
    public static final String COLUMN_CONTACT = "student_contact";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                _USN+ " TEXT NOT NULL," +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_EMAIL + " TEXT NOT NULL, " +
                COLUMN_BRANCH + " TEXT NOT NULL, " +
                COLUMN_CLUB + " TEXT NOT NULL, " +
                COLUMN_CONTACT + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }
    //drop beneficiary table
    private String DROP_BENEFICIARY_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //---opens the database---
    public DatabaseHelper open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }


    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db1, int oldVersion, int newVersion) {

        //Drop User Table if exist

        db1.execSQL(DROP_BENEFICIARY_TABLE);

        // Create tables again
        onCreate(db1);

    }


    //Method to create beneficiary records

    public void addBeneficiary(StudentGetterSetter studentGetterSetter) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_USN, studentGetterSetter.getUsn());
        values.put(COLUMN_NAME, studentGetterSetter.getName());
        values.put(COLUMN_EMAIL, studentGetterSetter.getEmail());
        values.put(COLUMN_BRANCH, studentGetterSetter.getBranch());
        values.put(COLUMN_CLUB, studentGetterSetter.getClub());
        values.put(COLUMN_CONTACT, studentGetterSetter.getContact());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                _USN
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }





    public List<StudentGetterSetter> getAllBeneficiary() {
        // array of columns to fetch
        String[] columns = {
                _USN,
                COLUMN_NAME,
                COLUMN_EMAIL,
                COLUMN_BRANCH,
                COLUMN_CLUB,
                COLUMN_CONTACT
        };
        // sorting orders
        String sortOrder =
                COLUMN_NAME + " ASC";
        List<StudentGetterSetter> studentGetterSetterList = new ArrayList<StudentGetterSetter>();

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                StudentGetterSetter studentGetterSetter = new StudentGetterSetter();
                studentGetterSetter.setUsn(cursor.getString(cursor.getColumnIndex(_USN)));
                studentGetterSetter.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                studentGetterSetter.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                studentGetterSetter.setBranch(cursor.getString(cursor.getColumnIndex(COLUMN_BRANCH)));
                studentGetterSetter.setClub(cursor.getString(cursor.getColumnIndex(COLUMN_CLUB)));
                studentGetterSetter.setContact(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACT)));
                studentGetterSetterList.add(studentGetterSetter);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return studentGetterSetterList;
    }

}
