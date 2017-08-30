// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_helpers;

import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface;
import android.app.AlertDialog.Builder;
import android.app.AlertDialog;
import android.content.Context;
import com.vanbo.homeux.dravite.newlayouttest.LauncherLog;
import com.dravite.homeux.R;

public class ExceptionLog
{
    private static final String TAG = "HomeUX Exception";
    
    public static void e(final Exception ex) {
        LauncherLog.w("HomeUX Exception", ex.getMessage());
    }
    
    public static void throwErrorMsg(final Context context, final String s) {
        new AlertDialog.Builder(context, R.style.DialogTheme).setTitle((CharSequence)"HomeUX Exception").setMessage((CharSequence)("An error occurred:\n\n" + s)).setPositiveButton(R.string.app_name, (DialogInterface.OnClickListener)null).show();
    }
    
    public static void w(final Exception ex) {
        LauncherLog.d("HomeUX Exception", ex.getMessage());
    }
}
