// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.drawerobjects.helpers;

import android.database.Cursor;
import android.content.ContentResolver;
import android.support.v4.app.ActivityCompat;
import android.app.Activity;
import android.support.v4.content.ContextCompat;
import java.util.ArrayList;
import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.net.Uri;
import android.provider.ContactsContract;

public class ContactUtil
{
    private static final String CONTACT_IMG_URI = "photo_thumb_uri";
    private static final String CONTACT_LOOKUP_KEY = "lookup";
    private static final String CONTACT_NAME = "display_name";
    private static final Uri CONTENT_URI;
    private static final int REQUEST_READ_CONTACTS = 37;
    private static final String TAG = "ContactUtil";
    
    static {
        CONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    }
    
    public static ArrayList<Contact> getContactList(final Context context, final String s) {
        ArrayList<Contact> list;
        if (s == null || s.equals("")) {
            list = new ArrayList<Contact>();
        }
        else {
            if (ContextCompat.checkSelfPermission(context, "android.permission.READ_CONTACTS") != 0) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale((Activity)context, "android.permission.READ_CONTACTS")) {
                    ActivityCompat.requestPermissions((Activity)context, new String[] { "android.permission.READ_CONTACTS" }, 37);
                }
                return new ArrayList<Contact>();
            }
            final ContentResolver contentResolver = context.getContentResolver();
            final ArrayList<Contact> list2 = new ArrayList<Contact>();
            final Cursor query = contentResolver.query(ContactUtil.CONTENT_URI, (String[])null, "display_name LIKE '%" + s + "%' AND " + "in_visible_group" + "=1", (String[])null, "display_name ASC");
            if (query != null && query.moveToFirst()) {
                do {
                    final String string = query.getString(query.getColumnIndex("lookup"));
                    final String string2 = query.getString(query.getColumnIndex("display_name"));
                    final String string3 = query.getString(query.getColumnIndex("photo_thumb_uri"));
                    Uri parse;
                    if (string3 == null) {
                        parse = null;
                    }
                    else {
                        parse = Uri.parse(string3);
                    }
                    final Contact contact = new Contact(string, string2, parse);
                    if (!list2.contains(contact)) {
                        list2.add(contact);
                    }
                } while (query.moveToNext());
            }
            list = list2;
            if (query != null) {
                query.close();
                return list2;
            }
        }
        return list;
    }
    
    public static class Contact
    {
        public final String mLookupKey;
        public final String mName;
        public final Uri mThumbnailUri;
        
        Contact(final String mLookupKey, final String mName, final Uri mThumbnailUri) {
            this.mName = mName;
            this.mLookupKey = mLookupKey;
            this.mThumbnailUri = mThumbnailUri;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o != null && o instanceof Contact && this.mLookupKey.equals(((Contact)o).mLookupKey);
        }
    }
}
