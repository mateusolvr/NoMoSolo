package com.example.nomosoloapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class MainActivity extends AppCompatActivity {

    private DBManager dbManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_foreground);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        dbManager = new DBManager(this);
        dbManager.open();

//        dbManager.deleteEverything();
        createMusician();
        Log.d("AUTHENTICATION ---->", String.valueOf(authenticateUser("mateus.olvr@gmail.com", "12345")));

    }


    // ADD THESE CODES BELOW TO REGISTRATION ACTIVITY

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createMusician() {
        String fName = "Mateus";
        String lName = "de Oliveira";
        String email = "mateus.olvr@gmail.com";
        String phone = "6043072803";
        String password = "12345";
        String securityQuestion = "What's my Age?";
        String securityAnswer = "27";

        if (isEmailAvailable(email)) {
            String salt = getNewSalt();
            String encryptedPassword = getEncryptedPassword(password, salt);

            dbManager.insertNewMusician(fName, lName, email, encryptedPassword, salt, phone, securityQuestion, securityAnswer);
        } else {
            Log.d("--->", "Sorry, this email is already taken, please choose another one.");
        }
    }

    private boolean isEmailAvailable(String email) {

        ArrayList<String> dbEmails = new ArrayList<>();

        String dbEmail = dbManager.checkEmail(email);
        if (email.equalsIgnoreCase(dbEmail)) {
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean authenticateUser(String email, String inputPass) {
        Cursor c = dbManager.getSalt(email);
        String salt = null;
        String userEncryptedPassword = null;
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            salt = c.getString(0);
            userEncryptedPassword = c.getString(1);
        }
        String calculatedHash = getEncryptedPassword(inputPass, salt);
        if (calculatedHash.equals(userEncryptedPassword)) {
            return true;
        } else {
            return false;
        }

    }


    // Get a encrypted password using PBKDF2 hash algorithm
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getEncryptedPassword(String password, String salt){
        String algorithm = "PBKDF2WithHmacSHA1";
        int derivedKeyLength = 160; // for SHA1
        int iterations = 20000; // NIST specifies 10000

        byte[] saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, derivedKeyLength);
        SecretKeyFactory f = null;
        try {
            f = SecretKeyFactory.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] encBytes = new byte[0];
        try {
            encBytes = f.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(encBytes);
    }

    // Returns base64 encoded salt
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getNewSalt() {
        // Don't use Random!
        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // NIST recommends minimum 4 bytes. We use 8.
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

}