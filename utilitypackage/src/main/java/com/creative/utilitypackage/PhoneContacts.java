package com.creative.utilitypackage;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import com.creative.utilitypackage.model.ContactItem;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class PhoneContacts {

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
                contactItem.setIsChecked(true);
                contactList.add(contactItem);
            }

        }
        return contactList;
    }
}
