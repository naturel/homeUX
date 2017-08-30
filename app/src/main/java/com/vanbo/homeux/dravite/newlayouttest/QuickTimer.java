// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest;

import android.util.Log;

public class QuickTimer
{
    static long startTime;
    static long time;
    
    static {
        QuickTimer.time = 0L;
        QuickTimer.startTime = 0L;
    }
    
    public static void beginTimer() {
        QuickTimer.time = System.nanoTime();
        QuickTimer.startTime = System.nanoTime();
    }
    
    public static void endTimer() {
        Log.d("Timer", "Ending after " + nsDelta(QuickTimer.startTime) + " ns (or " + msDelta(QuickTimer.startTime) + " ms)");
    }
    
    public static long msDelta(final long n) {
        return nsDelta(n) / 1000000L;
    }
    
    public static long nsDelta(final long n) {
        return System.nanoTime() - n;
    }
    
    public static void print(final String s) {
        Log.d("Timer", s + " ----- took " + nsDelta(QuickTimer.time) + " ns (or " + msDelta(QuickTimer.time) + " ms)");
    }
    
    public static void printAndRestart(final String s) {
        print(s);
        QuickTimer.time = System.nanoTime();
    }
}
