package com.wateryan.acropolis.seneca.core;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.wateryan.acropolis.seneca.model.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 7/31/2015.
 */
public class DbController {

    private static DbController instance = null;

    private DbHelper helper;
    private SQLiteDatabase db;

    // TODO Swap all queries with query builder
    private DbController(Context context) {
        this.helper = new DbHelper(context);
        this.db = this.helper.getWritableDatabase();
    }

    public static DbController getInstance(Context context) {
        if (instance == null) {
            instance = new DbController(context);
        }
        return instance;
    }

    public List<Account> getUsersAccounts() {
        List<Account> accounts = new ArrayList<>();
        try (Cursor cursor = db.rawQuery("SELECT * FROM account", null)) {
            if (cursor.moveToFirst()) {
                do {
                    accounts.add(new Account(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4)));
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            Log.d("SQL Error", e.getLocalizedMessage());
        }
        return accounts;
    }

    public void addUsersAccount(Account account) {
        db.execSQL("INSERT INTO account " + account.getUsername() + "," + account.getPassword() + "," + account.getServiceName() + "," + account.getHost() + "," + account.getPort());
    }

    public void closeDb() {
        this.db.close();
        this.helper.close();
    }

    private class DbHelper extends SQLiteOpenHelper {
        private static final int DB_VERSION = 1;
        private static final String DB_NAME = "sqldb";
        private static final String TABLE_MESSAGES = "messages";


        public DbHelper(Context context) {
            super(context, DB_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // messages table
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_MESSAGES + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, username text, type integer, body text)");
            // uac table
            db.execSQL("CREATE TABLE IF NOT EXISTS account (_id INTEGER PRIMARY KEY AUTOINCREMENT, username text, password text, serviceName text, host text, port integer)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
            db.execSQL("DROP TABLE IF EXISTS account");
            onCreate(db);
        }
    }
}
