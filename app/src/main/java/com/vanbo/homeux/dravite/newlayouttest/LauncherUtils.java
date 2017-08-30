// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest;

import android.os.Build;
import android.os.Bundle;
import android.app.ActivityOptions;
import android.os.Build.VERSION;
import android.app.Activity;
import android.view.View;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.content.res.Resources;
import android.util.TypedValue;
import com.vanbo.homeux.dravite.newlayouttest.views.AppIconView;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.Context;

public class LauncherUtils
{
    public static boolean canBeUninstalled(final String s, final Context context) {
        try {
            return isUserApp(context.getPackageManager().getApplicationInfo(s, 0), true);
        }
        catch (PackageManager.NameNotFoundException ex) {
            return false;
        }
    }
    
    public static void colorAppIconView(final AppIconView appIconView, final Context context) {
        if (!((LauncherActivity)context).mHolder.showCard) {
            final Resources resources = context.getResources();
            appIconView.setTextColor(-1);
            appIconView.setShadowLayer(TypedValue.applyDimension(1, 1.0f, resources.getDisplayMetrics()), TypedValue.applyDimension(1, 0.5f, resources.getDisplayMetrics()), TypedValue.applyDimension(1, 0.5f, resources.getDisplayMetrics()), -1728053248);
            return;
        }
        appIconView.setTextColor(1979711488);
        appIconView.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
    }
    
    public static int dpToPx(final float n, final Context context) {
        return (int)TypedValue.applyDimension(1, n, context.getResources().getDisplayMetrics());
    }
    
    public static Bitmap drawableToBitmap(final Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        if (intrinsicWidth <= 0) {
            intrinsicWidth = 1;
        }
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicHeight <= 0) {
            intrinsicHeight = 1;
        }
        final Bitmap bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    
    public static Bitmap getResizedBitmap(final Bitmap bitmap, final int n, final int n2) {
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        final float n3 = n2 / width;
        final float n4 = n / height;
        final Matrix matrix = new Matrix();
        matrix.postScale(n3, n4);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
    
    public static Matrix getResizedMatrix(final Bitmap bitmap, final int n, final int n2) {
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        final float n3 = n2 / width;
        final float n4 = n / height;
        final Matrix matrix = new Matrix();
        matrix.postScale(n3, n4);
        return matrix;
    }
    
    public static boolean isAvailable(final Context context, final Intent intent) {
        return context.getPackageManager().queryIntentActivities(intent, 0).size() > 0;
    }
    
    public static boolean isPackageInstalled(final String s, final Context context) {
        final PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(s, PackageManager.GET_ACTIVITIES);
            return true;
        }
        catch (PackageManager.NameNotFoundException ex) {
            return false;
        }
    }
    
    public static boolean isUserApp(final ApplicationInfo applicationInfo, final boolean b) {
        if ((applicationInfo.flags & 0x1) != 0x0) {
            if (!b) {
                return false;
            }
            if ((applicationInfo.flags & 0x80) == 0x0) {
                return false;
            }
        }
        return true;
    }
    
    public static Bitmap loadBitmapFromView(final View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return view.getDrawingCache();
    }
    
    public static Intent makeLaunchIntent(final Intent intent) {
        return intent.setAction("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setFlags(270532608);
    }
    
    public static boolean pointInView(final View view, final float n, final float n2, final float n3) {
        return n >= -n3 && n2 >= -n3 && n < view.getWidth() + n3 && n2 < view.getHeight() + n3;
    }
    
    public static void startActivity(final Activity activity, final View view, final Intent intent) {
        startActivityForResult(activity, view, intent, -1);
    }
    
    public static void startActivityForResult(final Activity activity, final View view, final Intent intent, final int n) {
        if (view != null) {
            view.getLocationInWindow(new int[] { 0, 0 });
        }
        ActivityOptions activityOptions;
        Bundle bundle = null;

        if (view != null) {
            if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activityOptions = ActivityOptions.makeClipRevealAnimation(view, 0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            }
            else{
                activityOptions = ActivityOptions.makeScaleUpAnimation(view, 0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            }
            bundle = activityOptions.toBundle();
        }

        activity.startActivityForResult(intent, n, bundle);
    }
}
