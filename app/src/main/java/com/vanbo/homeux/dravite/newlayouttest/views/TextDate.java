// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Date;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.content.Context;
import java.text.SimpleDateFormat;
import android.widget.TextView;

public class TextDate extends android.support.v7.widget.AppCompatTextView
{
    private SimpleDateFormat mDateFormat;
    
    public TextDate(final Context context) {
        this(context, null);
    }
    
    public TextDate(final Context context, @Nullable final AttributeSet set) {
        this(context, set, 16842884);
    }
    
    public TextDate(final Context context, @Nullable final AttributeSet set, final int n) {
        this(context, set, n, 0);
    }
    
    public TextDate(final Context context, @Nullable final AttributeSet set, final int n, final int n2) {
        super(context, set, n);
        this.updateDate();
    }
    
    public void onRestoreInstanceState(final Parcelable parcelable) {
        super.onRestoreInstanceState(parcelable);
        this.updateDate();
    }
    
    public void updateDate() {
        final Date date = new Date(System.currentTimeMillis());
        final String format = new SimpleDateFormat("yyy", Locale.getDefault()).format(date);
        this.mDateFormat = (SimpleDateFormat)DateFormat.getDateInstance(0, Locale.getDefault());
        final StringBuilder sb = new StringBuilder(this.mDateFormat.format(date).replace(format, ""));
        while (sb.toString().endsWith(" ") || sb.toString().endsWith(",") || sb.toString().endsWith(".")) {
            sb.replace(sb.length() - 1, sb.length(), "");
        }
        this.setText((CharSequence)sb.toString());
    }
}
