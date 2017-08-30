// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.helpers;

import android.view.View;
import android.graphics.PointF;
import android.view.View.OnLongClickListener;

public class CheckForLongPressHelper
{
    boolean canLongPress;
    boolean mHasPerformedLongPress;
    View.OnLongClickListener mListener;
    private PointF mLongPressPosition;
    private int mLongPressTimeout;
    private CheckForLongPress mPendingCheckForLongPress;
    View mView;
    
    public CheckForLongPressHelper(final View mView) {
        this.mLongPressTimeout = 400;
        this.mLongPressPosition = new PointF();
        this.mView = mView;
    }
    
    public void cancelLongPress() {
        this.mHasPerformedLongPress = false;
        this.canLongPress = false;
        if (this.mPendingCheckForLongPress != null) {
            this.mView.removeCallbacks((Runnable)this.mPendingCheckForLongPress);
            this.mPendingCheckForLongPress = null;
        }
    }
    
    public PointF getLongPressPosition() {
        return this.mLongPressPosition;
    }
    
    public boolean hasPerformedLongPress() {
        return this.mHasPerformedLongPress;
    }
    
    public boolean isCanLongPress() {
        return this.canLongPress;
    }
    
    public void postCheckForLongPress() {
        this.mHasPerformedLongPress = false;
        if (this.mPendingCheckForLongPress == null) {
            this.mPendingCheckForLongPress = new CheckForLongPress();
        }
        this.mView.postDelayed((Runnable)this.mPendingCheckForLongPress, (long)this.mLongPressTimeout);
    }
    
    public void setLongPressPosition(final float x, final float y) {
        this.mLongPressPosition.x = x;
        this.mLongPressPosition.y = y;
    }
    
    public void setLongPressTimeout(final int mLongPressTimeout) {
        this.mLongPressTimeout = mLongPressTimeout;
    }
    
    class CheckForLongPress implements Runnable
    {
        @Override
        public void run() {
            if (CheckForLongPressHelper.this.mView.getParent() != null && CheckForLongPressHelper.this.mView.hasWindowFocus() && !CheckForLongPressHelper.this.mHasPerformedLongPress) {
                CheckForLongPressHelper.this.canLongPress = true;
                boolean b;
                if (CheckForLongPressHelper.this.mListener != null) {
                    b = CheckForLongPressHelper.this.mListener.onLongClick(CheckForLongPressHelper.this.mView);
                }
                else {
                    b = CheckForLongPressHelper.this.mView.performLongClick();
                }
                if (b) {
                    CheckForLongPressHelper.this.mView.setPressed(false);
                    CheckForLongPressHelper.this.mHasPerformedLongPress = true;
                }
            }
        }
    }
}
