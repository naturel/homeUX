// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.viewcomponents;

import android.graphics.ColorFilter;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

public class ProgressFadeDrawable extends Drawable
{
    int mAlpha;
    Drawable mDrawableEnd;
    Drawable mDrawableStart;
    float mProgress;
    
    public ProgressFadeDrawable(final Drawable mDrawableStart, final Drawable mDrawableEnd) {
        this.mAlpha = 255;
        this.mProgress = 0.0f;
        this.mDrawableStart = mDrawableStart;
        this.mDrawableEnd = mDrawableEnd;
    }
    
    public void draw(final Canvas canvas) {
        final int alpha = (int)(this.mAlpha * this.mProgress);
        final int alpha2 = (int)(this.mAlpha * (1.0f - this.mProgress));
        this.mDrawableStart.setBounds(this.getBounds());
        this.mDrawableEnd.setBounds(this.getBounds());
        this.mDrawableStart.setAlpha(alpha2);
        this.mDrawableEnd.setAlpha(alpha);
        this.mDrawableStart.draw(canvas);
        this.mDrawableEnd.draw(canvas);
    }
    
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
    
    public void setAlpha(final int mAlpha) {
        this.mAlpha = mAlpha;
    }
    
    public void setColorFilter(final ColorFilter colorFilter) {
        this.mDrawableStart.setColorFilter(colorFilter);
        this.mDrawableEnd.setColorFilter(colorFilter);
    }
    
    public void setDrawableEnd(final Drawable mDrawableEnd) {
        this.mDrawableEnd = mDrawableEnd;
        this.invalidateSelf();
    }
    
    public void setDrawableStart(final Drawable mDrawableStart) {
        this.mDrawableStart = mDrawableStart;
        this.invalidateSelf();
    }
    
    public void setProgress(final float mProgress) {
        this.mProgress = mProgress;
        this.invalidateSelf();
    }
    
    public void setTint(final int n) {
        this.mDrawableEnd.setTint(n);
        this.mDrawableStart.setTint(n);
        this.invalidateSelf();
    }
}
