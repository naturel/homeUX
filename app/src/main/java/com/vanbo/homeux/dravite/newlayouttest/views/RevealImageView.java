// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views;

import android.graphics.Path.Direction;
import android.graphics.Canvas;
import android.graphics.Xfermode;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.Paint;
import android.view.View;

public class RevealImageView extends View
{
    Paint mBackPaint;
    Drawable mBackground;
    Path mClipPath;
    Drawable mForeground;
    Paint mMaskPaint;
    private float mProgress;
    PointF mRevealCenter;
    
    public RevealImageView(final Context context) {
        this(context, null);
    }
    
    public RevealImageView(final Context context, final AttributeSet set) {
        this(context, set, 0);
    }
    
    public RevealImageView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.mProgress = 0.5f;
        this.mRevealCenter = new PointF(0.0f, 0.0f);
        (this.mBackPaint = new Paint()).setAntiAlias(true);
        (this.mMaskPaint = new Paint()).setAntiAlias(true);
        this.mMaskPaint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        this.mClipPath = new Path();
    }
    
    double getCircleRadiusMax() {
        return Math.sqrt(Math.pow(Math.max(this.mRevealCenter.x, this.getMeasuredWidth() - this.mRevealCenter.x), 2.0) + Math.pow(Math.max(this.mRevealCenter.y, this.getMeasuredHeight() - this.mRevealCenter.y), 2.0));
    }
    
    void layoutDrawable(final Drawable drawable) {
        if (drawable != null) {
            final float min = Math.min(drawable.getIntrinsicWidth() / this.getMeasuredWidth(), drawable.getIntrinsicHeight() / this.getMeasuredHeight());
            final int n = (int)(drawable.getIntrinsicWidth() / min);
            final int n2 = (int)(drawable.getIntrinsicHeight() / min);
            drawable.setBounds(this.getMeasuredWidth() / 2 - n / 2, this.getMeasuredHeight() / 2 - n2 / 2, this.getMeasuredWidth() / 2 + n / 2, this.getMeasuredHeight() / 2 + n2 / 2);
        }
    }
    
    protected void onDraw(final Canvas canvas) {
        if (this.mBackground != null) {
            this.mBackground.draw(canvas);
        }
        canvas.clipPath(this.mClipPath);
        if (this.mForeground != null) {
            this.mForeground.draw(canvas);
        }
    }
    
    protected void onMeasure(final int n, final int n2) {
        super.onMeasure(n, n2);
        this.layoutDrawable(this.mForeground);
        this.layoutDrawable(this.mBackground);
        this.mClipPath.reset();
        this.mClipPath.addCircle(this.mRevealCenter.x, this.mRevealCenter.y, this.mProgress * (float)this.getCircleRadiusMax(), Path.Direction.CW);
    }
    
    public void setBackground(final Drawable mBackground) {
        this.layoutDrawable(this.mBackground = mBackground);
        this.invalidate();
    }
    
    public void setForeground(final Drawable mForeground) {
        this.layoutDrawable(this.mForeground = mForeground);
        this.invalidate();
    }
    
    public void setProgress(final float mProgress) {
        this.mProgress = mProgress;
        this.mClipPath.reset();
        this.mClipPath.addCircle(this.mRevealCenter.x, this.mRevealCenter.y, this.mProgress * (float)this.getCircleRadiusMax(), Path.Direction.CW);
        this.invalidate();
    }
    
    public void setRevealCenter(final float x, final float y) {
        this.mRevealCenter.x = x;
        this.mRevealCenter.y = y;
    }
}
