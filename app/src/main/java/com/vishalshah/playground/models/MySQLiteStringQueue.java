package com.vishalshah.playground.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vishalshah.playground.db.SQLiteQueueDAO;

import java.io.Serializable;

/**
 * Created by vishalshah on 19/10/16.
 */
public class MySQLiteStringQueue {

    private SQLiteQueueDAO sqLiteQueueDAO;

    public MySQLiteStringQueue(Context context) {
        sqLiteQueueDAO = SQLiteQueueDAO.getInstance(context);
    }

    public Integer get() {
        return sqLiteQueueDAO.get();
    }

    public void add(Integer item) {
        sqLiteQueueDAO.save(item);
    }

    public int getSize() {
        return sqLiteQueueDAO.getCount();
    }

}
