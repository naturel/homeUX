// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.viewcomponents;

import android.graphics.Outline;
import android.view.View;
import android.graphics.Rect;
import android.annotation.TargetApi;
import android.view.ViewOutlineProvider;

@TargetApi(21)
public class RevealOutlineProvider extends ViewOutlineProvider
{
    private int mCenterX;
    private int mCenterY;
    private int mCurrentRadius;
    private final Rect mOval;
    private float mProgress;
    private float mRadius0;
    private float mRadius1;
    private View mView;
    
    public RevealOutlineProvider(final int mCenterX, final int mCenterY, final float mRadius0, final float mRadius2) {
        this.mCenterX = mCenterX;
        this.mCenterY = mCenterY;
        this.mRadius0 = mRadius0;
        this.mRadius1 = mRadius2;
        this.mOval = new Rect();
    }
    
    public void assignView(final View mView) {
        this.mView = mView;
    }
    
    public void getOutline(final View view, final Outline outline) {
        outline.setRoundRect(this.mOval, (float)this.mCurrentRadius);
    }
    
    public float getProgress() {
        return this.mProgress;
    }
    
    public void setProgress(final float mProgress) {
        this.mProgress = mProgress;
        this.mCurrentRadius = (int)((1.0f - mProgress) * this.mRadius0 + this.mRadius1 * mProgress);
        this.mOval.left = this.mCenterX - this.mCurrentRadius;
        this.mOval.top = this.mCenterY - this.mCurrentRadius;
        this.mOval.right = this.mCenterX + this.mCurrentRadius;
        this.mOval.bottom = this.mCenterY + this.mCurrentRadius;
        if (this.mView != null) {
            this.mView.invalidateOutline();
        }
    }
}
