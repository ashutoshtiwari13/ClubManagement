package com.ruuha.bmscms;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.version;

/**
 * Created by ruuha on 9/23/2017.
 */

public class DBHelperClub extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Club.db";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME="CLUB_TABLE";

    public DBHelperClub(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }

    public void queryData(String sql){

        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);


    }

    public void insertData(String name,String C_manager, String contact,byte[] image){

        SQLiteDatabase database=getWritableDatabase();
        String sql="INSERT INTO "+TABLE_NAME+" VALUES (NULL,?, ? , ?, ?)";

        SQLiteStatement statement=database.compileStatement(sql);
        statement.clearBindings();


        statement.bindString(1,name);
        statement.bindString(2,C_manager);
        statement.bindString(3,contact);
        statement.bindBlob(4,image);

        statement.executeInsert();



    }


    public void updateData(int id,String name,String C_manager,String contact,byte[] image){
        SQLiteDatabase database=getWritableDatabase();
        String sql="UPDATE CLUB_TABLE SET name = ? , C_manager = ? , contact  = ?,image =? WHERE Id = ?";
        SQLiteStatement statement=database.compileStatement(sql);

        statement.bindString(1,name);
        statement.bindString(2,C_manager);
        statement.bindString(3,contact);
        statement.bindBlob(4,image);
        statement.bindDouble(5,(double)id);

        statement.execute();
        database.close();


    }

    public void deleteData(int id){

        SQLiteDatabase database=getWritableDatabase();
        String sql="DELETE FROM CLUB_TABLE WHERE Id = ?";
        SQLiteStatement statement=database.compileStatement(sql);

        statement.clearBindings();
        statement.bindDouble(1,(double)id);

        statement.execute();
        database.close();



    }
    public Cursor getData(String sql){

        SQLiteDatabase database=getReadableDatabase();
         return  database.rawQuery(sql,null);
    }

    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();

        String selectQuery = "SELECT * FROM CLUB_TABLE";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
