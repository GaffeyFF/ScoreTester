package com.myapplication.gaffey.scoretes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by 72356 on 2017/10/21.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String paperId = "paperId";

    public static String CREATE_PAPER = "create table paper" +
            "(" +
            "id integer primary key autoincrement," +
            "paperName string unique," +
            "paperImage integer)";


    private Context mContext;





    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PAPER);

//        CREATE_ANSWER="create table answer "+
//                paperId+
//                "("+
//                "id integer,"+
//                "string select";
//        db.execSQL(CREATE_ANSWER);
        Log.d("DatabaseHelper", "Create Successful");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // db.execSQL("drop table if exists answer" + paperId);
        db.execSQL("drop table if exists paper");
        onCreate(db);
    }

}
