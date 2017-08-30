// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_helpers;

import android.graphics.Color;
import android.support.annotation.Size;

public class ColorUtils
{
    public static int HSLtoColor(@Size(3L) final float[] array) {
        if (array.length != 3) {
            throw new RuntimeException("HSL needs to have 3 components.");
        }
        if (array[1] == 0.0f) {
            final int n = (int)(array[2] * 255.0f);
            return Color.rgb(n, n, n);
        }
        float n2;
        if (array[2] < 0.5f) {
            n2 = array[2] * (1.0f + array[1]);
        }
        else {
            n2 = array[2] + array[1] - array[2] * array[1];
        }
        final float n3 = 2.0f * array[2] - n2;
        final float n4 = array[0] / 360.0f;
        final float n5 = n4 + 0.3333f;
        final float n6 = n4;
        final float n7 = n4 - 0.3333f;
        float n8;
        if (n5 > 1.0f) {
            n8 = n5 - 1.0f;
        }
        else {
            n8 = n5;
            if (n5 < 0.0f) {
                n8 = n5 + 1.0f;
            }
        }
        float n9;
        if (n6 > 1.0f) {
            n9 = n6 - 1.0f;
        }
        else {
            n9 = n6;
            if (n6 < 0.0f) {
                n9 = n6 + 1.0f;
            }
        }
        float n10;
        if (n7 > 1.0f) {
            n10 = n7 - 1.0f;
        }
        else {
            n10 = n7;
            if (n7 < 0.0f) {
                n10 = n7 + 1.0f;
            }
        }
        return Color.rgb(getBitColor(testColor(n8, n2, n3)), getBitColor(testColor(n9, n2, n3)), getBitColor(testColor(n10, n2, n3)));
    }
    
    public static float[] colorToHSL(final int n) {
        final float[] array = new float[3];
        final float n2 = Color.red(n) / 255.0f;
        final float n3 = Color.green(n) / 255.0f;
        final float n4 = Color.blue(n) / 255.0f;
        final float min = Math.min(n2, Math.min(n3, n4));
        final float max = Math.max(n2, Math.max(n3, n4));
        array[2] = (min + max) / 2.0f;
        if (array[2] <= 0.5f) {
            array[1] = (max - min) / (max + min);
        }
        else {
            array[1] = (max - min) / (2.0f - max - min);
        }
        if (max == min) {
            array[0] = 0.0f;
        }
        else if (max == n2) {
            array[0] = (n3 - n4) / (max - min);
        }
        else if (max == n3) {
            array[0] = (n4 - n2) / (max - min) + 2.0f;
        }
        else if (max == n4) {
            array[0] = 4.0f + (n2 - n3) / (max - min);
        }
        array[0] *= 60.0f;
        if (array[0] < 0.0f) {
            array[0] += 360.0f;
        }
        return array;
    }
    
    private static int getBitColor(final float n) {
        return Math.round(255.0f * n);
    }
    
    public static int getDarkerColor(final int n) {
        final float[] array = new float[3];
        Color.RGBToHSV(Color.red(n), Color.green(n), Color.blue(n), array);
        array[2] *= 0.8f;
        return Color.HSVToColor(array);
    }
    
    public static boolean isBrightColor(final int n) {
        boolean b = true;
        if (17170445 == n) {
            return false;
        }
        final int[] array = { Color.red(n), Color.green(n), Color.blue(n) };
        if ((int)Math.sqrt(array[0] * array[0] * 0.241 + array[1] * array[1] * 0.691 + array[2] * array[2] * 0.068) < 200) {
            b = false;
        }
        return b;
    }
    
    private static float testColor(final float n, final float n2, final float n3) {
        float n4;
        if (6.0f * n < 1.0f) {
            n4 = n3 + (n2 - n3) * 6.0f * n;
        }
        else {
            n4 = n2;
            if (2.0f * n >= 1.0f) {
                if (3.0f * n < 2.0f) {
                    return n3 + (n2 - n3) * (0.6666f - n) * 6.0f;
                }
                return n3;
            }
        }
        return n4;
    }
}
