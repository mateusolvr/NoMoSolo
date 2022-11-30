package com.example.nomosoloapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

    public void setNewProfile(int id, String bio, String instrument, String skillLevel, String genre1, String genre2, String seekingInstrument, String seekingSkill, String seekingGenre){
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.ID, id);
        contentValue.put(DBHelper.BIO, bio);
        contentValue.put(DBHelper.INSTRUMENT, instrument);
        contentValue.put(DBHelper.PHOTO, "");
        contentValue.put(DBHelper.SKILL_LEVEL, skillLevel);
        contentValue.put(DBHelper.GENRE1, genre1);
        contentValue.put(DBHelper.GENRE2, genre2);
        contentValue.put(DBHelper.INSTRUMENT_DESIRED, seekingInstrument);
        contentValue.put(DBHelper.SKILL_DESIRED, seekingSkill);
        contentValue.put(DBHelper.GENRE_DESIRED, seekingGenre);

        database.insert(DBHelper.MUSICIAN_INFO_TABLE, null, contentValue);
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

    public Cursor getQuestionAndAnswer(String email) {
        String[] columns = new String[]{DBHelper.SECURITY_QUESTION, DBHelper.SECURITY_ANSWER};
        Cursor cursor = database.query(DBHelper.MUSICIAN_REG_TABLE, columns, DBHelper.EMAIL+" = ?", new String[]{email}, null, null, null);

        return cursor;
    }

    public Cursor getSalt(String email) {
        String[] columns = new String[]{DBHelper.SALT, DBHelper.PASSWORD};
        Cursor cursor = database.query(DBHelper.MUSICIAN_REG_TABLE, columns, DBHelper.EMAIL+" = ?", new String[]{email}, null, null, null);

        return cursor;
    }

    public String getUserId(String email){
        String[] columns = new String[]{DBHelper.ID};
        Cursor cursor = database.query(DBHelper.MUSICIAN_REG_TABLE, columns, DBHelper.EMAIL+" = ?", new String[]{email}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        return null;
    }

    public void updatePassword(String email, String newPassword){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.PASSWORD,newPassword);
        database.update(DBHelper.MUSICIAN_REG_TABLE, cv, DBHelper.EMAIL+" = ?", new String[]{email});
    }

    public User getUserProfile(String userID){
        String[] columns = new String[]{DBHelper.BIO, DBHelper.INSTRUMENT, DBHelper.SKILL_LEVEL, DBHelper.GENRE1, DBHelper.GENRE2, DBHelper.INSTRUMENT_DESIRED, DBHelper.SKILL_DESIRED, DBHelper.GENRE_DESIRED};
        Cursor cursor = database.query(DBHelper.MUSICIAN_INFO_TABLE, columns, DBHelper.ID+" = ?", new String[]{userID}, null, null, null);

        columns = new String[]{DBHelper.FIRSTNAME, DBHelper.LASTNAME};
        Cursor cursor2 = database.query(DBHelper.MUSICIAN_REG_TABLE, columns, DBHelper.ID+" = ?", new String[]{userID}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            String bio = cursor.getString(0);
            String instrument = cursor.getString(1);
            String skillLevel = cursor.getString(2);
            String genre1 = cursor.getString(3);
            String genre2 = cursor.getString(4);
            String seekingInstrument = cursor.getString(5);
            String seekingLevel = cursor.getString(6);
            String seekingGenre = cursor.getString(7);

            User user = new User(bio, instrument, skillLevel, genre1, genre2, seekingInstrument, seekingLevel, seekingGenre);
            if (cursor2 != null && cursor.getCount() > 0) {
                cursor2.moveToFirst();
                String fn = cursor2.getString(0);
                String ln = cursor2.getString(1);

                user.setFn(fn.trim().toUpperCase());
                user.setLn(ln.trim().toUpperCase());
            }
            return user;
        }

        return null;
    }

    // TEMPORARY, DELETE LATER
    public void deleteEverything() {
        dbHelper.onUpgrade(database, 1, 1);
    }

}
