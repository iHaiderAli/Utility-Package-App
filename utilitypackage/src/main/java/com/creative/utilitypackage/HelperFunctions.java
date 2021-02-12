package com.creative.utilitypackage;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.creative.utilitypackage.model.ContactItem;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class HelperFunctions {

    public boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase(Constants.WIFI))
                if (ni.isConnected()) {
                    haveConnectedWifi = true;
                    return haveConnectedWifi;
                }
            if (ni.getTypeName().equalsIgnoreCase(Constants.MOBILE))
                if (ni.isConnected()) {
                    haveConnectedMobile = true;
                    return haveConnectedMobile;
                }
        }
        return false;
    }

    public static void hideSoftKeyboard(View view, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static void showSoftKeyboard(View view, Context context) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static void showConfirmationDialog(Context context, String title, String message, DialogInterface.OnClickListener positiveClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(Constants.YES,positiveClickListener);
        builder.setNegativeButton(Constants.NO, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static void showAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton(Constants.NO, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static int convertDpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int convertPxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLog(String key, String msg) {
        Log.d(key, msg);
    }

    public static void showErrorLog(String key, String msg) {
        Log.d(key, msg);
    }

    public static ArrayList<ContactItem> getReadContacts(Context context) {

        final ArrayList<ContactItem> contactList = new ArrayList<>();
        final ContentResolver cr = context.getContentResolver();
        try (final Cursor mainCursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)) {
            if (mainCursor == null) {
                return null;
            }

            while (mainCursor.moveToNext()) {
                final ContactItem contactItem = new ContactItem();
                final String id = mainCursor.getString(mainCursor.getColumnIndex(ContactsContract.Contacts._ID));
                final String displayName = mainCursor.getString(mainCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(mainCursor.getString(mainCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

                    contactItem.setDisplayName(displayName);
                    //ADD PHOTO DATA...
                    final Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(id));
                    final Uri photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
                    try (final Cursor cursor = cr.query(photoUri,
                            new String[]{ContactsContract.Contacts.Photo.PHOTO}, null, null, null)) {
                        if (cursor == null) {
                            return null;
                        }
                        if (cursor.moveToFirst()) {
                            final byte[] data = cursor.getBlob(0);
                            if (data != null) {
                                final Bitmap bmp = BitmapFactory.decodeStream(new ByteArrayInputStream(data));
                                contactItem.setPhotoUrl(bmp);
                            }
                        }
                    }
                }

                //ADD PHONE DATA...
                try (final Cursor phoneCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{
                        id
                }, null)) {
                    if (phoneCursor != null) {
                        while (phoneCursor.moveToNext()) {
                            final String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            contactItem.setPhone(phone);
                        }
                    }
                }
                contactList.add(contactItem);
            }

        }
        return contactList;
    }

}
