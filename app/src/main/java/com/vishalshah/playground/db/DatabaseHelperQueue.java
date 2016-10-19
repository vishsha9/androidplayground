package com.vishalshah.playground.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vishalshah on 19/10/16.
 */
public class DatabaseHelperQueue extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static DatabaseHelperQueue dbInstance;
    private final static String DATABASE_NAME = "db";
    private final static int DATABASE_VERSION = 1;

    private DatabaseHelperQueue(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelperQueue getInstance (Context context) {
        if (dbInstance == null) {
            dbInstance = new DatabaseHelperQueue(context);
        }
        return dbInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "QUEUE_DATA (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "data INTEGER NOT NULL );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
