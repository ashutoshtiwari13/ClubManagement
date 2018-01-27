package com.ruuha.bmscms;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class SQLiteDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Main.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID =  "userid";
    public static final String COLUMN_USERNAME =  "username";
    public static final String COLUMN_BRANCH =  "branch";
    public static final String COLUMN_EMAIL =  "email";
    public static final String COLUMN_PASSWORD =  "password";
    public static final String COLUMN_MOBILE =  "mobile";
    //public static final String COLUMN_IMEI="imei";

    SQLiteDatabase db;
    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT NOT NULL, "+
                    COLUMN_BRANCH + " TEXT , "+
                    COLUMN_EMAIL + " TEXT NOT NULL, " +
                    COLUMN_PASSWORD + " TEXT NOT NULL, " +
                    COLUMN_MOBILE + " TEXT  "+")";
    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_QUERY);
        this.db=db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void insertContact(ContactRegister c){

        db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_USERNAME,c.getName());
        cv.put(COLUMN_BRANCH,c.getBranch());
        cv.put(COLUMN_EMAIL,c.getEmail());
        cv.put(COLUMN_PASSWORD,c.getPass());
        cv.put(COLUMN_MOBILE,c.getMobile());

        db.insert(TABLE_NAME,null,cv);
        db.close();
    }

    public String searchPass(String username){

        db=this.getReadableDatabase();
        String query="SELECT username ,password  FROM "+TABLE_NAME;
        Cursor cursor=db.rawQuery(query,null);
        String a,b;
        b="NOT FOUND IN DIRECTORY";
        if(cursor.moveToFirst()){

            do{
                a=cursor.getString(0);

                if(a.equals(username)){
                    b=cursor.getString(1);
                    break;
                }
            }
            while(cursor.moveToNext());
        }

           return b;
    }

}
