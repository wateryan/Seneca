package com.wateryan.acropolis.seneca.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created on 7/31/2015.
 */
public class DbController {

    private static DbController instance = null;

    private DbHelper helper;
    private SQLiteDatabase db;

    private DbController(Context context) {
        this.helper = new DbHelper(context);
        this.db = this.helper.getWritableDatabase();
    }

    public void closeDb() {
        this.db.close();
        this.helper.close();
    }

    public static DbController getInstance(Context context) {
        if (instance == null) {
            instance = new DbController(context);
        }
        return instance;
    }

    private class DbHelper extends SQLiteOpenHelper {
        private static final int DB_VERSION = 1;
        private static final String DB_NAME = "sqldb.db";
        private static final String TABLE_MESSAGES = "messages";


        public DbHelper(Context context) {
            super(context, DB_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_MESSAGES + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, username text, type integer, body text)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
            onCreate(db);
        }
    }
}
