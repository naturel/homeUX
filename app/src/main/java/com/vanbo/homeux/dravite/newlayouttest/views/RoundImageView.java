// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views;

import android.graphics.Color;
import android.graphics.Path.Direction;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Path;
import android.widget.ImageView;

public class RoundImageView extends android.support.v7.widget.AppCompatImageView
{
    private final Path clipPath;
    Color mColor;
    
    public RoundImageView(final Context context) {
        super(context);
        this.clipPath = new Path();
        this.mColor = new Color(145, 0, 0, 0);
    }
    
    public RoundImageView(final Context context, @Nullable final AttributeSet set) {
        this(context, set, 0);
    }
    
    public RoundImageView(final Context context, @Nullable final AttributeSet set, final int n) {
        this(context, set, n, 0);
    }
    
    public RoundImageView(final Context context, @Nullable final AttributeSet set, final int n, final int n2) {
        super(context, set, n);
        this.clipPath = new Path();
        this.mColor = new Color(145, 0, 0, 0);
    }
    
    protected void onDraw(final Canvas canvas) {
        final float n = this.getWidth() / 2.0f;
        this.clipPath.reset();
        this.clipPath.addCircle(n, n, n, Path.Direction.CW);
        canvas.clipPath(this.clipPath);
        canvas.drawARGB(this.mColor.mAlpha, this.mColor.mRed, this.mColor.mGreen, this.mColor.mBlue);
        super.onDraw(canvas);
    }
    
    public void setBackgroundColor(final int n) {
        if (n == -1) {
            this.mColor.set(145, 0, 0, 0);
            return;
        }
        this.mColor.set(n);
    }
    
    static class Color
    {
        int mAlpha;
        int mBlue;
        int mGreen;
        int mRed;
        
        Color(final int mAlpha, final int mRed, final int mGreen, final int mBlue) {
            this.mRed = mRed;
            this.mAlpha = mAlpha;
            this.mGreen = mGreen;
            this.mBlue = mBlue;
        }
        
        void set(final int n) {
            this.mAlpha = android.graphics.Color.alpha(n);
            this.mRed = android.graphics.Color.red(n);
            this.mGreen = android.graphics.Color.green(n);
            this.mBlue = android.graphics.Color.blue(n);
        }
        
        void set(final int mAlpha, final int mRed, final int mGreen, final int mBlue) {
            this.mRed = mRed;
            this.mAlpha = mAlpha;
            this.mGreen = mGreen;
            this.mBlue = mBlue;
        }
    }
}
