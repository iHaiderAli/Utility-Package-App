package com.creative.utilitypackage;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class AppPermissons {

    public static void REQUEST_STORAGE(Activity act, int code) {
        ActivityCompat.requestPermissions(act, new
                String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},code);
    }
    public static void REQUEST_CAMERA(Activity act,int code) {
        ActivityCompat.requestPermissions(act, new
                String[]{Manifest.permission.CAMERA},code);
    }
    public static void REQUEST_FINE_LOCATION(Activity act,int code) {
        ActivityCompat.requestPermissions(act, new
                String[]{Manifest.permission.ACCESS_FINE_LOCATION},code);
    }
    public static void REQUEST_READ_SMS(Activity act,int code) {
        ActivityCompat.requestPermissions(act, new
                String[]{Manifest.permission.READ_SMS},code);
    }
    public static void REQUEST_READ_CONTACTS(Activity act,int code) {
        ActivityCompat.requestPermissions(act, new
                String[]{Manifest.permission.READ_CONTACTS},code);
    }
    public static void REQUEST_READ_CALENDAR(Activity act,int code) {
        ActivityCompat.requestPermissions(act, new
                String[]{Manifest.permission.READ_CALENDAR},code);
    }
    public static void REQUEST_RECORD_AUDIO(Activity act,int code) {
        ActivityCompat.requestPermissions(act, new
                String[]{Manifest.permission.RECORD_AUDIO},code);
    }
    public static void REQUEST_READ_STORAGE(Activity act,int code) {
        ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},code);
    }

    public static boolean CHECK_READ_STORAGE(Activity act) {
        int result = ContextCompat.checkSelfPermission(act, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean CHECK_STORAGE(Activity act) {
        int result = ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    public static boolean CHECK_CAMERA(Activity act) {
        int result = ContextCompat.checkSelfPermission(act, Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    public static boolean CHECK_FINE_LOCATION(Activity act) {
        int result = ContextCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    public static boolean CHECK_READ_SMS(Activity act) {
        int result = ContextCompat.checkSelfPermission(act, Manifest.permission.READ_SMS);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    public static boolean CHECK_READ_CONTACTS(Activity act) {
        int result = ContextCompat.checkSelfPermission(act, Manifest.permission.READ_CONTACTS);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    public static boolean CHECK_READ_CALENDAR(Activity act) {
        int result = ContextCompat.checkSelfPermission(act, Manifest.permission.READ_CALENDAR);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    public static boolean CHECK_RECORD_AUDIO(Activity act) {
        int result = ContextCompat.checkSelfPermission(act, Manifest.permission.RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED;
    }

}

