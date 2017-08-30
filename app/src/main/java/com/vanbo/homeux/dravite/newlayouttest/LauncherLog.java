// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest;

public class LauncherLog
{
    public static final boolean DEBUG = true;
    
    public static void d(final Class clazz, final String s) {
        d(clazz.getName(), s);
    }
    
    public static void d(final String s, final String s2) {
        android.util.Log.v(s, s2);
    }
    
    public static void w(final Class clazz, final String s) {
        w(clazz.getName(), s);
    }
    
    public static void w(final String s, final String s2) {
        android.util.Log.v(s, s2);
    }
}
