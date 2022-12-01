package com.example.nomosoloapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public void setNewProfile(int id, String bio, String instrument, String skillLevel, String genre1, String genre2, String seekingInstrument, String seekingSkill, String seekingGenre) {
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
        Cursor cursor = database.query(DBHelper.MUSICIAN_REG_TABLE, columns, DBHelper.EMAIL + " = ?", new String[]{email}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        return null;
    }

    public Cursor getQuestionAndAnswer(String email) {
        String[] columns = new String[]{DBHelper.SECURITY_QUESTION, DBHelper.SECURITY_ANSWER};
        Cursor cursor = database.query(DBHelper.MUSICIAN_REG_TABLE, columns, DBHelper.EMAIL + " = ?", new String[]{email}, null, null, null);

        return cursor;
    }

    public Cursor getSalt(String email) {
        String[] columns = new String[]{DBHelper.SALT, DBHelper.PASSWORD};
        Cursor cursor = database.query(DBHelper.MUSICIAN_REG_TABLE, columns, DBHelper.EMAIL + " = ?", new String[]{email}, null, null, null);

        return cursor;
    }

    public String getUserId(String email) {
        String[] columns = new String[]{DBHelper.ID};
        Cursor cursor = database.query(DBHelper.MUSICIAN_REG_TABLE, columns, DBHelper.EMAIL + " = ?", new String[]{email}, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        return null;
    }

    public void updatePassword(String email, String newPassword) {
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.PASSWORD, newPassword);
        database.update(DBHelper.MUSICIAN_REG_TABLE, cv, DBHelper.EMAIL + " = ?", new String[]{email});
    }

    public User getUserProfile(String userID) {
        String[] columns = new String[]{DBHelper.BIO, DBHelper.INSTRUMENT, DBHelper.SKILL_LEVEL, DBHelper.GENRE1, DBHelper.GENRE2, DBHelper.INSTRUMENT_DESIRED, DBHelper.SKILL_DESIRED, DBHelper.GENRE_DESIRED};
        Cursor cursor = database.query(DBHelper.MUSICIAN_INFO_TABLE, columns, DBHelper.ID + " = ?", new String[]{userID}, null, null, null);

        columns = new String[]{DBHelper.FIRSTNAME, DBHelper.LASTNAME};
        Cursor cursor2 = database.query(DBHelper.MUSICIAN_REG_TABLE, columns, DBHelper.ID + " = ?", new String[]{userID}, null, null, null);

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

            User user = new User(userID, bio, instrument, skillLevel, genre1, genre2, seekingInstrument, seekingLevel, seekingGenre);
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

    public ArrayList<User> getMatches(String userID) {
        String[] columns = new String[]{DBHelper.INSTRUMENT_DESIRED, DBHelper.SKILL_DESIRED, DBHelper.GENRE_DESIRED};
        Cursor cursor = database.query(DBHelper.MUSICIAN_INFO_TABLE, columns, DBHelper.ID + " = ?", new String[]{userID}, null, null, null);

        cursor.moveToFirst();
        String seekingInstrument = cursor.getString(0);
        String seekingLevel = cursor.getString(1);
        String seekingGenre = cursor.getString(2);

        String[] columns2 = new String[]{DBHelper.ID};
        Cursor cursor2 = database.query(DBHelper.MUSICIAN_INFO_TABLE, columns2, DBHelper.INSTRUMENT + " = ? AND " + DBHelper.SKILL_LEVEL + " = ? AND (" + DBHelper.GENRE1  + " = ? OR " + DBHelper.GENRE2 + " = ? )", new String[]{seekingInstrument, seekingLevel, seekingGenre, seekingGenre}, null, null, null);

        ArrayList<User> usersMatched = new ArrayList<>();

        if (cursor2 != null && cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {
                usersMatched.add(getUserProfile(cursor2.getString(0)));
            }
        }
        return usersMatched;
    }

    public void updateProfile(String id, String bio, String instrument, String skillLevel, String genre1, String genre2, String seekingInstrument, String seekingSkill, String seekingGenre) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.BIO, bio);
        contentValue.put(DBHelper.INSTRUMENT, instrument);
        contentValue.put(DBHelper.PHOTO, "");
        contentValue.put(DBHelper.SKILL_LEVEL, skillLevel);
        contentValue.put(DBHelper.GENRE1, genre1);
        contentValue.put(DBHelper.GENRE2, genre2);
        contentValue.put(DBHelper.INSTRUMENT_DESIRED, seekingInstrument);
        contentValue.put(DBHelper.SKILL_DESIRED, seekingSkill);
        contentValue.put(DBHelper.GENRE_DESIRED, seekingGenre);

        database.update(DBHelper.MUSICIAN_INFO_TABLE, contentValue, DBHelper.ID + " = ?", new String[]{id});
    }

    // TEMPORARY, DELETE LATER
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void populateEmptyDB() {
        String[] columns = new String[]{DBHelper.ID};
        Cursor cursor = database.query(DBHelper.MUSICIAN_REG_TABLE, columns, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            return;
        }
        PasswordHelper myPasswordHelper = new PasswordHelper(this);

        ContentValues contentValue = new ContentValues();
        contentValue.put(DBHelper.FIRSTNAME, "Mateus");
        contentValue.put(DBHelper.LASTNAME, "de Oliveira");
        contentValue.put(DBHelper.EMAIL, "mateus@gmail.com");
        String salt = myPasswordHelper.getNewSalt();
        String encryptedPassword = myPasswordHelper.getEncryptedPassword("m", salt);
        contentValue.put(DBHelper.PASSWORD, encryptedPassword);
        contentValue.put(DBHelper.SALT, salt);
        contentValue.put(DBHelper.PHONE, "6043072803");
        contentValue.put(DBHelper.SECURITY_QUESTION, "m");
        contentValue.put(DBHelper.SECURITY_ANSWER, "m");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        contentValue.put(DBHelper.CREATION_DATE, dateFormat.format(date));
        database.insert(DBHelper.MUSICIAN_REG_TABLE, null, contentValue);

        contentValue.clear();
        contentValue.put(DBHelper.FIRSTNAME, "Bill");
        contentValue.put(DBHelper.LASTNAME, "Nguyen");
        contentValue.put(DBHelper.EMAIL, "bill@gmail.com");
        salt = myPasswordHelper.getNewSalt();
        encryptedPassword = myPasswordHelper.getEncryptedPassword("b", salt);
        contentValue.put(DBHelper.PASSWORD, encryptedPassword);
        contentValue.put(DBHelper.SALT, salt);
        contentValue.put(DBHelper.PHONE, "1234567890");
        contentValue.put(DBHelper.SECURITY_QUESTION, "b");
        contentValue.put(DBHelper.SECURITY_ANSWER, "b");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new Date();
        contentValue.put(DBHelper.CREATION_DATE, dateFormat.format(date));
        database.insert(DBHelper.MUSICIAN_REG_TABLE, null, contentValue);

        contentValue.clear();
        contentValue.put(DBHelper.FIRSTNAME, "Umar");
        contentValue.put(DBHelper.LASTNAME, "Salih");
        contentValue.put(DBHelper.EMAIL, "umar@gmail.com");
        salt = myPasswordHelper.getNewSalt();
        encryptedPassword = myPasswordHelper.getEncryptedPassword("u", salt);
        contentValue.put(DBHelper.PASSWORD, encryptedPassword);
        contentValue.put(DBHelper.SALT, salt);
        contentValue.put(DBHelper.PHONE, "1234567890");
        contentValue.put(DBHelper.SECURITY_QUESTION, "u");
        contentValue.put(DBHelper.SECURITY_ANSWER, "u");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new Date();
        contentValue.put(DBHelper.CREATION_DATE, dateFormat.format(date));
        database.insert(DBHelper.MUSICIAN_REG_TABLE, null, contentValue);

        contentValue.clear();
        contentValue.put(DBHelper.FIRSTNAME, "Eustace");
        contentValue.put(DBHelper.LASTNAME, "Hortense");
        contentValue.put(DBHelper.EMAIL, "eustace@gmail.com");
        salt = myPasswordHelper.getNewSalt();
        encryptedPassword = myPasswordHelper.getEncryptedPassword("e", salt);
        contentValue.put(DBHelper.PASSWORD, encryptedPassword);
        contentValue.put(DBHelper.SALT, salt);
        contentValue.put(DBHelper.PHONE, "1234567890");
        contentValue.put(DBHelper.SECURITY_QUESTION, "e");
        contentValue.put(DBHelper.SECURITY_ANSWER, "e");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new Date();
        contentValue.put(DBHelper.CREATION_DATE, dateFormat.format(date));
        database.insert(DBHelper.MUSICIAN_REG_TABLE, null, contentValue);

        contentValue.clear();
        contentValue.put(DBHelper.FIRSTNAME, "Sibyl");
        contentValue.put(DBHelper.LASTNAME, "Veva");
        contentValue.put(DBHelper.EMAIL, "sibyl@gmail.com");
        salt = myPasswordHelper.getNewSalt();
        encryptedPassword = myPasswordHelper.getEncryptedPassword("s", salt);
        contentValue.put(DBHelper.PASSWORD, encryptedPassword);
        contentValue.put(DBHelper.SALT, salt);
        contentValue.put(DBHelper.PHONE, "1234567890");
        contentValue.put(DBHelper.SECURITY_QUESTION, "s");
        contentValue.put(DBHelper.SECURITY_ANSWER, "s");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new Date();
        contentValue.put(DBHelper.CREATION_DATE, dateFormat.format(date));
        database.insert(DBHelper.MUSICIAN_REG_TABLE, null, contentValue);

        contentValue.clear();
        contentValue.put(DBHelper.FIRSTNAME, "Sanford");
        contentValue.put(DBHelper.LASTNAME, "Lou");
        contentValue.put(DBHelper.EMAIL, "sanford@gmail.com");
        salt = myPasswordHelper.getNewSalt();
        encryptedPassword = myPasswordHelper.getEncryptedPassword("s", salt);
        contentValue.put(DBHelper.PASSWORD, encryptedPassword);
        contentValue.put(DBHelper.SALT, salt);
        contentValue.put(DBHelper.PHONE, "1234567890");
        contentValue.put(DBHelper.SECURITY_QUESTION, "s");
        contentValue.put(DBHelper.SECURITY_ANSWER, "s");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new Date();
        contentValue.put(DBHelper.CREATION_DATE, dateFormat.format(date));
        database.insert(DBHelper.MUSICIAN_REG_TABLE, null, contentValue);

        contentValue.clear();
        contentValue.put(DBHelper.FIRSTNAME, "Martha");
        contentValue.put(DBHelper.LASTNAME, "Dora");
        contentValue.put(DBHelper.EMAIL, "martha@gmail.com");
        salt = myPasswordHelper.getNewSalt();
        encryptedPassword = myPasswordHelper.getEncryptedPassword("m", salt);
        contentValue.put(DBHelper.PASSWORD, encryptedPassword);
        contentValue.put(DBHelper.SALT, salt);
        contentValue.put(DBHelper.PHONE, "1234567890");
        contentValue.put(DBHelper.SECURITY_QUESTION, "m");
        contentValue.put(DBHelper.SECURITY_ANSWER, "m");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new Date();
        contentValue.put(DBHelper.CREATION_DATE, dateFormat.format(date));
        database.insert(DBHelper.MUSICIAN_REG_TABLE, null, contentValue);

        contentValue.clear();
        contentValue.put(DBHelper.FIRSTNAME, "Harley");
        contentValue.put(DBHelper.LASTNAME, "Nate");
        contentValue.put(DBHelper.EMAIL, "harley@gmail.com");
        salt = myPasswordHelper.getNewSalt();
        encryptedPassword = myPasswordHelper.getEncryptedPassword("h", salt);
        contentValue.put(DBHelper.PASSWORD, encryptedPassword);
        contentValue.put(DBHelper.SALT, salt);
        contentValue.put(DBHelper.PHONE, "1234567890");
        contentValue.put(DBHelper.SECURITY_QUESTION, "h");
        contentValue.put(DBHelper.SECURITY_ANSWER, "h");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new Date();
        contentValue.put(DBHelper.CREATION_DATE, dateFormat.format(date));
        database.insert(DBHelper.MUSICIAN_REG_TABLE, null, contentValue);

        contentValue.clear();
        contentValue.put(DBHelper.FIRSTNAME, "Bernetta");
        contentValue.put(DBHelper.LASTNAME, "Derick");
        contentValue.put(DBHelper.EMAIL, "bernetta@gmail.com");
        salt = myPasswordHelper.getNewSalt();
        encryptedPassword = myPasswordHelper.getEncryptedPassword("b", salt);
        contentValue.put(DBHelper.PASSWORD, encryptedPassword);
        contentValue.put(DBHelper.SALT, salt);
        contentValue.put(DBHelper.PHONE, "1234567890");
        contentValue.put(DBHelper.SECURITY_QUESTION, "b");
        contentValue.put(DBHelper.SECURITY_ANSWER, "b");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new Date();
        contentValue.put(DBHelper.CREATION_DATE, dateFormat.format(date));
        database.insert(DBHelper.MUSICIAN_REG_TABLE, null, contentValue);

        contentValue.clear();
        contentValue.put(DBHelper.FIRSTNAME, "Chuckie");
        contentValue.put(DBHelper.LASTNAME, "Shirley");
        contentValue.put(DBHelper.EMAIL, "chuckie@gmail.com");
        salt = myPasswordHelper.getNewSalt();
        encryptedPassword = myPasswordHelper.getEncryptedPassword("c", salt);
        contentValue.put(DBHelper.PASSWORD, encryptedPassword);
        contentValue.put(DBHelper.SALT, salt);
        contentValue.put(DBHelper.PHONE, "1234567890");
        contentValue.put(DBHelper.SECURITY_QUESTION, "c");
        contentValue.put(DBHelper.SECURITY_ANSWER, "c");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = new Date();
        contentValue.put(DBHelper.CREATION_DATE, dateFormat.format(date));
        database.insert(DBHelper.MUSICIAN_REG_TABLE, null, contentValue);


        contentValue.clear();
        contentValue = new ContentValues();
        contentValue.put(DBHelper.ID, 1);
        contentValue.put(DBHelper.BIO, "Hey, Mateus");
        contentValue.put(DBHelper.INSTRUMENT, "Keyboard");
        contentValue.put(DBHelper.PHOTO, "");
        contentValue.put(DBHelper.SKILL_LEVEL, "Beginner Enthusiast");
        contentValue.put(DBHelper.GENRE1, "Pop");
        contentValue.put(DBHelper.GENRE2, "Rock");
        contentValue.put(DBHelper.INSTRUMENT_DESIRED, "Vocal");
        contentValue.put(DBHelper.SKILL_DESIRED, "Intermediate Enthusiast");
        contentValue.put(DBHelper.GENRE_DESIRED, "Pop");
        database.insert(DBHelper.MUSICIAN_INFO_TABLE, null, contentValue);

        contentValue.clear();
        contentValue = new ContentValues();
        contentValue.put(DBHelper.ID, 2);
        contentValue.put(DBHelper.BIO, "Hey, Bill");
        contentValue.put(DBHelper.INSTRUMENT, "Guitar");
        contentValue.put(DBHelper.PHOTO, "");
        contentValue.put(DBHelper.SKILL_LEVEL, "Intermediate Enthusiast");
        contentValue.put(DBHelper.GENRE1, "Blues");
        contentValue.put(DBHelper.GENRE2, "Rock");
        contentValue.put(DBHelper.INSTRUMENT_DESIRED, "Keyboard");
        contentValue.put(DBHelper.SKILL_DESIRED, "Beginner Enthusiast");
        contentValue.put(DBHelper.GENRE_DESIRED, "Pop");
        database.insert(DBHelper.MUSICIAN_INFO_TABLE, null, contentValue);

        contentValue.clear();
        contentValue = new ContentValues();
        contentValue.put(DBHelper.ID, 3);
        contentValue.put(DBHelper.BIO, "Hey, Umar");
        contentValue.put(DBHelper.INSTRUMENT, "Violin");
        contentValue.put(DBHelper.PHOTO, "");
        contentValue.put(DBHelper.SKILL_LEVEL, "Intermediate Enthusiast");
        contentValue.put(DBHelper.GENRE1, "RnB");
        contentValue.put(DBHelper.GENRE2, "Rock");
        contentValue.put(DBHelper.INSTRUMENT_DESIRED, "Keyboard");
        contentValue.put(DBHelper.SKILL_DESIRED, "Beginner Enthusiast");
        contentValue.put(DBHelper.GENRE_DESIRED, "Pop");
        database.insert(DBHelper.MUSICIAN_INFO_TABLE, null, contentValue);


        contentValue.clear();
        contentValue = new ContentValues();
        contentValue.put(DBHelper.ID, 4);
        contentValue.put(DBHelper.BIO, "Hey, there");
        contentValue.put(DBHelper.INSTRUMENT, "Vocal");
        contentValue.put(DBHelper.PHOTO, "");
        contentValue.put(DBHelper.SKILL_LEVEL, "Intermediate Enthusiast");
        contentValue.put(DBHelper.GENRE1, "Pop");
        contentValue.put(DBHelper.GENRE2, "Classical");
        contentValue.put(DBHelper.INSTRUMENT_DESIRED, "Saxophone");
        contentValue.put(DBHelper.SKILL_DESIRED, "Intermediate Enthusiast");
        contentValue.put(DBHelper.GENRE_DESIRED, "Soul");
        database.insert(DBHelper.MUSICIAN_INFO_TABLE, null, contentValue);

        contentValue.clear();
        contentValue = new ContentValues();
        contentValue.put(DBHelper.ID, 5);
        contentValue.put(DBHelper.BIO, "Hey, there");
        contentValue.put(DBHelper.INSTRUMENT, "Vocal");
        contentValue.put(DBHelper.PHOTO, "");
        contentValue.put(DBHelper.SKILL_LEVEL, "Intermediate Enthusiast");
        contentValue.put(DBHelper.GENRE1, "Pop");
        contentValue.put(DBHelper.GENRE2, "Jazz");
        contentValue.put(DBHelper.INSTRUMENT_DESIRED, "Violin");
        contentValue.put(DBHelper.SKILL_DESIRED, "Intermediate Enthusiast");
        contentValue.put(DBHelper.GENRE_DESIRED, "Soul");
        database.insert(DBHelper.MUSICIAN_INFO_TABLE, null, contentValue);

        contentValue.clear();
        contentValue = new ContentValues();
        contentValue.put(DBHelper.ID, 6);
        contentValue.put(DBHelper.BIO, "Hey, there");
        contentValue.put(DBHelper.INSTRUMENT, "Vocal");
        contentValue.put(DBHelper.PHOTO, "");
        contentValue.put(DBHelper.SKILL_LEVEL, "Intermediate Enthusiast");
        contentValue.put(DBHelper.GENRE1, "RnB");
        contentValue.put(DBHelper.GENRE2, "Pop");
        contentValue.put(DBHelper.INSTRUMENT_DESIRED, "Drums/Percussive");
        contentValue.put(DBHelper.SKILL_DESIRED, "Beginner Enthusiast");
        contentValue.put(DBHelper.GENRE_DESIRED, "Country");
        database.insert(DBHelper.MUSICIAN_INFO_TABLE, null, contentValue);

        contentValue.clear();
        contentValue = new ContentValues();
        contentValue.put(DBHelper.ID, 7);
        contentValue.put(DBHelper.BIO, "Hey, there");
        contentValue.put(DBHelper.INSTRUMENT, "Bass Guitar");
        contentValue.put(DBHelper.PHOTO, "");
        contentValue.put(DBHelper.SKILL_LEVEL, "Intermediate Enthusiast");
        contentValue.put(DBHelper.GENRE1, "RnB");
        contentValue.put(DBHelper.GENRE2, "Pop");
        contentValue.put(DBHelper.INSTRUMENT_DESIRED, "Drums/Percussive");
        contentValue.put(DBHelper.SKILL_DESIRED, "Beginner Enthusiast");
        contentValue.put(DBHelper.GENRE_DESIRED, "Country");
        database.insert(DBHelper.MUSICIAN_INFO_TABLE, null, contentValue);

        contentValue.clear();
        contentValue = new ContentValues();
        contentValue.put(DBHelper.ID, 8);
        contentValue.put(DBHelper.BIO, "Hey, there");
        contentValue.put(DBHelper.INSTRUMENT, "Keyboard");
        contentValue.put(DBHelper.PHOTO, "");
        contentValue.put(DBHelper.SKILL_LEVEL, "Beginner Enthusiast");
        contentValue.put(DBHelper.GENRE1, "RnB");
        contentValue.put(DBHelper.GENRE2, "Pop");
        contentValue.put(DBHelper.INSTRUMENT_DESIRED, "Drums/Percussive");
        contentValue.put(DBHelper.SKILL_DESIRED, "Beginner Enthusiast");
        contentValue.put(DBHelper.GENRE_DESIRED, "Country");
        database.insert(DBHelper.MUSICIAN_INFO_TABLE, null, contentValue);

        contentValue.clear();
        contentValue = new ContentValues();
        contentValue.put(DBHelper.ID, 9);
        contentValue.put(DBHelper.BIO, "Hey, there");
        contentValue.put(DBHelper.INSTRUMENT, "Keyboard");
        contentValue.put(DBHelper.PHOTO, "");
        contentValue.put(DBHelper.SKILL_LEVEL, "Beginner Enthusiast");
        contentValue.put(DBHelper.GENRE1, "RnB");
        contentValue.put(DBHelper.GENRE2, "Pop");
        contentValue.put(DBHelper.INSTRUMENT_DESIRED, "Drums/Percussive");
        contentValue.put(DBHelper.SKILL_DESIRED, "Beginner Enthusiast");
        contentValue.put(DBHelper.GENRE_DESIRED, "Country");
        database.insert(DBHelper.MUSICIAN_INFO_TABLE, null, contentValue);

        contentValue.clear();
        contentValue = new ContentValues();
        contentValue.put(DBHelper.ID, 10);
        contentValue.put(DBHelper.BIO, "Hey, there");
        contentValue.put(DBHelper.INSTRUMENT, "Saxophone");
        contentValue.put(DBHelper.PHOTO, "");
        contentValue.put(DBHelper.SKILL_LEVEL, "Beginner Enthusiast");
        contentValue.put(DBHelper.GENRE1, "RnB");
        contentValue.put(DBHelper.GENRE2, "Pop");
        contentValue.put(DBHelper.INSTRUMENT_DESIRED, "Drums/Percussive");
        contentValue.put(DBHelper.SKILL_DESIRED, "Beginner Enthusiast");
        contentValue.put(DBHelper.GENRE_DESIRED, "Country");
        database.insert(DBHelper.MUSICIAN_INFO_TABLE, null, contentValue);
    }

    // TEMPORARY, DELETE LATER
    public void deleteEverything() {
        dbHelper.onUpgrade(database, 1, 1);
    }

}
