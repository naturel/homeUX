// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures;

import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.View;
import android.content.Context;
import com.vanbo.homeux.dravite.newlayouttest.views.helpers.CheckForLongPressHelper;
import android.appwidget.AppWidgetHostView;

public class ClickableAppWidgetHostView extends AppWidgetHostView
{
    public CheckForLongPressHelper mLongPressHelper;
    private float mSlop;
    float[] pos;
    
    public ClickableAppWidgetHostView(final Context context) {
        super(context);
        this.pos = new float[2];
        (this.mLongPressHelper = new CheckForLongPressHelper((View)this)).setLongPressTimeout(500);
    }
    
    public void cancelLongPress() {
        super.cancelLongPress();
        this.mLongPressHelper.cancelLongPress();
    }
    
    public int getDescendantFocusability() {
        return FOCUS_BLOCK_DESCENDANTS;
    }
    
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mSlop = ViewConfiguration.get(this.getContext()).getScaledTouchSlop();
    }
    
    public boolean onInterceptTouchEvent(final MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.mLongPressHelper.cancelLongPress();
            this.pos = new float[] { motionEvent.getX(), motionEvent.getY() };
        }
        if (this.mLongPressHelper.hasPerformedLongPress()) {
            this.mLongPressHelper.cancelLongPress();
            return false;
        }
        switch (motionEvent.getAction()) {
            default: {
                return false;
            }
            case 0: {
                this.mLongPressHelper.postCheckForLongPress();
                this.mLongPressHelper.setLongPressPosition(motionEvent.getX(), motionEvent.getY());
                return false;
            }
            case 1:
            case 3:
            case 4: {
                this.mLongPressHelper.cancelLongPress();
                return false;
            }
            case 2:
            case 8: {
                if (Math.abs(motionEvent.getY() - this.pos[1]) > this.mSlop || Math.abs(motionEvent.getX() - this.pos[0]) > this.mSlop) {
                    this.mLongPressHelper.cancelLongPress();
                    return false;
                }
                this.mLongPressHelper.setLongPressPosition(motionEvent.getX(), motionEvent.getY());
                return false;
            }
        }
    }
    
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 1:
            case 3:
            case 4: {
                this.mLongPressHelper.cancelLongPress();
                break;
            }
            case 2:
            case 8: {
                if (!LauncherUtils.pointInView((View)this, motionEvent.getX(), motionEvent.getY(), this.mSlop)) {
                    this.mLongPressHelper.cancelLongPress();
                    break;
                }
                break;
            }
        }
        return true;
    }
}
