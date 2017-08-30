// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views;

import com.vanbo.homeux.dravite.newlayouttest.Const;
import com.vanbo.homeux.dravite.newlayouttest.views.viewcomponents.TextDrawable;
import android.view.MotionEvent;
import android.graphics.Canvas;
import android.view.ViewConfiguration;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import android.view.View;
import android.util.TypedValue;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.vanbo.homeux.dravite.newlayouttest.views.helpers.CheckForLongPressHelper;
import android.widget.Button;
import com.dravite.homeux.R;


public class AppIconView extends android.support.v7.widget.AppCompatButton
{
    int mCounterValue;
    boolean mDoOverrideData;
    int mIconSizeDP;
    int mIconSizePixels;
    boolean mLabelVisibility;
    public CheckForLongPressHelper mLongPressHelper;
    Drawable mOverlay;
    Rect mRect;
    private float mSlop;
    Rect mTextBounds;
    
    public AppIconView(final Context context) {
        this(context, null);
    }
    
    public AppIconView(final Context context, final AttributeSet set) {
        this(context, set, 0);
    }
    
    public AppIconView(final Context context, final AttributeSet set, final int n) {
        this(context, set, n, 0);
    }
    
    public AppIconView(final Context context, final AttributeSet set, final int n, final int n2) {
        super(context, set, n);
        this.mTextBounds = new Rect();
        this.mIconSizeDP = 56;
        this.mIconSizePixels = (int)TypedValue.applyDimension(1, (float)this.mIconSizeDP, this.getResources().getDisplayMetrics());
        this.mLabelVisibility = true;
        this.mDoOverrideData = false;
        this.mCounterValue = 0;
        this.mRect = new Rect();
        (this.mLongPressHelper = new CheckForLongPressHelper((View)this)).setLongPressTimeout(200);
        this.setWillNotDraw(true);
        this.setCompoundDrawablePadding(LauncherUtils.dpToPx(-1.0f, this.getContext()));
    }
    
    public boolean canScrollHorizontally(final int n) {
        return false;
    }
    
    public boolean canScrollVertically(final int n) {
        return false;
    }
    
    public void cancelLongPress() {
        super.cancelLongPress();
        this.mLongPressHelper.cancelLongPress();
    }
    
    public Drawable getIcon() {
        return this.getCompoundDrawables()[1];
    }
    
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mSlop = ViewConfiguration.get(this.getContext()).getScaledTouchSlop();
    }
    
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if (this.mOverlay != null) {
            canvas.getClipBounds(this.mRect);
            this.mOverlay.setBounds(this.mRect);
            this.mOverlay.draw(canvas);
        }
    }
    
    protected void onMeasure(int compoundDrawablePadding, int dpToPx) {
        super.onMeasure(compoundDrawablePadding, dpToPx);
        final int mIconSizePixels = this.mIconSizePixels;
        compoundDrawablePadding = this.getCompoundDrawablePadding();
        this.getPaint().getTextBounds(this.getText().toString(), 0, this.getText().length(), this.mTextBounds);
        if (this.mLabelVisibility) {
            compoundDrawablePadding += this.mTextBounds.height();
        }
        else {
            compoundDrawablePadding = 0;
        }
        if (this.mLabelVisibility) {
            dpToPx = LauncherUtils.dpToPx(4.0f, this.getContext());
        }
        else {
            dpToPx = 0;
        }
        this.setPadding(0, (int)(-((mIconSizePixels + compoundDrawablePadding - this.getMeasuredHeight()) / 4 - dpToPx) - this.getResources().getDimension(R.dimen.app_icon_text_padding_delta)), 0, 0);
    }
    
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        final boolean onTouchEvent = super.onTouchEvent(motionEvent);
        switch (motionEvent.getAction()) {
            default: {
                return onTouchEvent;
            }
            case 0: {
                this.mLongPressHelper.postCheckForLongPress();
                this.mLongPressHelper.setLongPressPosition(motionEvent.getX(), motionEvent.getY());
                return onTouchEvent;
            }
            case 1:
            case 3: {
                this.mLongPressHelper.cancelLongPress();
                return onTouchEvent;
            }
            case 2: {
                if (!LauncherUtils.pointInView((View)this, motionEvent.getX(), motionEvent.getY(), this.mSlop)) {
                    this.mLongPressHelper.cancelLongPress();
                    return onTouchEvent;
                }
                this.mLongPressHelper.setLongPressPosition(motionEvent.getX(), motionEvent.getY());
                return onTouchEvent;
            }
        }
    }
    
    public void overrideData(final int mIconSizeDP) {
        this.mDoOverrideData = true;
        this.mIconSizeDP = mIconSizeDP;
        this.mIconSizePixels = (int)TypedValue.applyDimension(1, (float)this.mIconSizeDP, this.getResources().getDisplayMetrics());
    }
    
    public void removeOverlay() {
        this.mOverlay = null;
        this.invalidate();
    }
    
    public void setCounterOverlay(final int mCounterValue) {
        if (mCounterValue == 0) {
            this.removeOverlay();
            return;
        }
        this.mCounterValue = mCounterValue;
        this.mOverlay = new TextDrawable(String.valueOf(mCounterValue), LauncherUtils.dpToPx(4.0f, this.getContext()), LauncherUtils.dpToPx(8.0f, this.getContext()));
        this.invalidate();
    }
    
    public void setIcon(final Drawable drawable) {
        if (drawable != null) {
            int n;
            if (this.mDoOverrideData) {
                n = this.mIconSizePixels;
            }
            else {
                n = LauncherUtils.dpToPx(Const.ICON_SIZE, this.getContext());
            }
            int n2;
            if (this.mDoOverrideData) {
                n2 = this.mIconSizePixels;
            }
            else {
                n2 = LauncherUtils.dpToPx(Const.ICON_SIZE, this.getContext());
            }
            drawable.setBounds(0, 0, n, n2);
        }
        this.setCompoundDrawables((Drawable)null, drawable, (Drawable)null, (Drawable)null);
    }
    
    public void setIconSizeInDP(final int mIconSizeDP) {
        this.mIconSizePixels = (int)TypedValue.applyDimension(1, (float)mIconSizeDP, this.getResources().getDisplayMetrics());
        this.mIconSizeDP = mIconSizeDP;
    }
    
    public void setLabelVisibility(final boolean mLabelVisibility) {
        this.mLabelVisibility = mLabelVisibility;
        this.invalidate();
        this.measure(0, 0);
    }
    
    public void setShadowLayer(final float n, final float n2, final float n3, final int n4) {
        if (this.mDoOverrideData || this.mLabelVisibility) {
            super.setShadowLayer(n, n2, n3, n4);
            return;
        }
        super.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
    }
    
    public void setTextColor(final int textColor) {
        if (this.mDoOverrideData || this.mLabelVisibility) {
            super.setTextColor(textColor);
            return;
        }
        super.setTextColor(0);
    }
}
