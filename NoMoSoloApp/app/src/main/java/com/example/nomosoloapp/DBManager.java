package com.example.nomosoloapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBManager {
    private DBHelper dbHelper;
    private Context context;
    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insertNewMusician(String fn, String ln, String email, String password, String salt, String phone, String securityQuestion, String securityAnswer) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.FIRSTNAME, fn);
        contentValue.put(DBHelper.LASTNAME, ln);
        contentValue.put(DBHelper.EMAIL, email);
        contentValue.put(DBHelper.PASSWORD, password);
        contentValue.put(DBHelper.SALT, salt);
        contentValue.put(DBHelper.PHONE, phone);
        contentValue.put(DBHelper.SECURITY_QUESTION, securityQuestion);
        contentValue.put(DBHelper.SECURITY_ANSWER, securityAnswer);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        contentValue.put(DBHelper.CREATION_DATE, dateFormat.format(date));

        database.insert(DBHelper.MUSICIAN_REG_TABLE, null, contentValue);
    }

    public String checkEmail(String email) {
        String[] columns = new String[]{DBHelper.EMAIL};
        Cursor cursor = database.query(DBHelper.MUSICIAN_REG_TABLE, columns, DBHelper.EMAIL+" = ?", new String[]{email}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        return null;
    }

    public Cursor getSalt(String email) {
        String[] columns = new String[]{DBHelper.SALT, DBHelper.PASSWORD};
        Cursor cursor = database.query(DBHelper.MUSICIAN_REG_TABLE, columns, DBHelper.EMAIL+" = ?", new String[]{email}, null, null, null);

        return cursor;
    }

    // TEMPORARY, DELETE LATER
    public void deleteEverything() {
        dbHelper.onUpgrade(database, 1, 1);
    }

}
