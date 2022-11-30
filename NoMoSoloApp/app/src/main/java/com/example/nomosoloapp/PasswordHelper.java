package com.example.nomosoloapp;

import android.database.Cursor;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHelper {

    private DBManager dbManager;

    public PasswordHelper(DBManager dbManager){
        this.dbManager = dbManager;
    }

//    dbManager = new DBManager(this);
//    dbManager.open();

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean authenticateUser(String email, String inputPass) {
        Cursor c = dbManager.getSalt(email);
        String salt = null;
        String userEncryptedPassword = null;
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            salt = c.getString(0);
            userEncryptedPassword = c.getString(1);
        } else{
            return false;
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
}
