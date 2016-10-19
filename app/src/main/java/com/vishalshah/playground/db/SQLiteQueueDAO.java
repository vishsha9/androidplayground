package com.vishalshah.playground.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * Created by vishalshah on 19/10/16.
 */
public class SQLiteQueueDAO {

    private DatabaseHelperQueue dbHelper;
    private static SQLiteQueueDAO sqLiteQueueDAO;
    private SQLiteDatabase database;

    private SQLiteQueueDAO(Context context) {
        dbHelper = DatabaseHelperQueue.getInstance(context);
        database = dbHelper.getWritableDatabase();
    }

    private static final String INSERT_DATA= "INSERT INTO QUEUE_DATA (data) VALUES (?) ";
    private static final String GET_DATA = "SELECT * FROM QUEUE_DATA ORDER BY id ASC LIMIT 1";
    private static final String DELETE_DATA = "DELETE FROM QUEUE_DATA WHERE id = ?";
    private static final String GET_COUNT = "SELECT count(*) FROM QUEUE_DATA";


    /***
     * Ensuring that this is a singleton and cannot have multiple instances of the same object
     *
     * @param context
     * @return
     */
    public static SQLiteQueueDAO getInstance(Context context) {
        if (sqLiteQueueDAO == null) {
            sqLiteQueueDAO = new SQLiteQueueDAO(context);
        }
        return sqLiteQueueDAO;
    }

    public void save(int item) {
        database.execSQL(INSERT_DATA, new Object [] { item });
    }

    public Integer get() {
        Cursor cursor = database.rawQuery(GET_DATA, null);
        cursor.moveToFirst();
        Integer result;
        try {
            int id = cursor.getInt(0);
            result = cursor.getInt(1);

            database.execSQL(DELETE_DATA, new Object[] {id});

        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public int getCount() {
        Cursor cursor = database.rawQuery(GET_COUNT, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }
}
