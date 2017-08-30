// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views;

import android.view.View;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.TypedValue;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Paint;
import android.widget.LinearLayout;

class SlidingTabStrip extends LinearLayout
{
    private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 38;
    private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 0;
    private static final int DEFAULT_SELECTED_INDICATOR_COLOR = -13388315;
    private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 3;
    private final Paint mBottomBorderPaint;
    private final int mBottomBorderThickness;
    private SlidingTabLayout.TabColorizer mCustomTabColorizer;
    private final int mDefaultBottomBorderColor;
    private final SimpleTabColorizer mDefaultTabColorizer;
    private final Paint mSelectedIndicatorPaint;
    private final int mSelectedIndicatorThickness;
    private int mSelectedPosition;
    private float mSelectionOffset;
    
    SlidingTabStrip(final Context context) {
        this(context, null);
    }
    
    SlidingTabStrip(final Context context, final AttributeSet set) {
        super(context, set);
        this.setWillNotDraw(false);
        final float density = this.getResources().getDisplayMetrics().density;
        final TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16842800, typedValue, true);
        this.mDefaultBottomBorderColor = setColorAlpha(typedValue.data, (byte)38);
        (this.mDefaultTabColorizer = new SimpleTabColorizer()).setIndicatorColors(-13388315);
        this.mBottomBorderThickness = (int)(0.0f * density);
        (this.mBottomBorderPaint = new Paint()).setColor(this.mDefaultBottomBorderColor);
        this.mSelectedIndicatorThickness = (int)(3.0f * density);
        this.mSelectedIndicatorPaint = new Paint();
    }
    
    private static int blendColors(final int n, final int n2, final float n3) {
        final float n4 = 1.0f - n3;
        return Color.rgb((int)(Color.red(n) * n3 + Color.red(n2) * n4), (int)(Color.green(n) * n3 + Color.green(n2) * n4), (int)(Color.blue(n) * n3 + Color.blue(n2) * n4));
    }
    
    private static int setColorAlpha(final int n, final byte b) {
        return Color.argb((int)b, Color.red(n), Color.green(n), Color.blue(n));
    }
    
    protected void onDraw(final Canvas canvas) {
        final int height = this.getHeight();
        final int childCount = this.getChildCount();
        SlidingTabLayout.TabColorizer tabColorizer;
        if (this.mCustomTabColorizer != null) {
            tabColorizer = this.mCustomTabColorizer;
        }
        else {
            tabColorizer = this.mDefaultTabColorizer;
        }
        if (childCount > 0) {
            final View child = this.getChildAt(this.mSelectedPosition);
            final int left = child.getLeft();
            final int right = child.getRight();
            int color;
            final int n = color = tabColorizer.getIndicatorColor(this.mSelectedPosition);
            int n2 = left;
            int n3 = right;
            if (this.mSelectionOffset > 0.0f) {
                color = n;
                n2 = left;
                n3 = right;
                if (this.mSelectedPosition < this.getChildCount() - 1) {
                    final int indicatorColor = tabColorizer.getIndicatorColor(this.mSelectedPosition + 1);
                    if ((color = n) != indicatorColor) {
                        color = blendColors(indicatorColor, n, this.mSelectionOffset);
                    }
                    final View child2 = this.getChildAt(this.mSelectedPosition + 1);
                    n2 = (int)(this.mSelectionOffset * child2.getLeft() + (1.0f - this.mSelectionOffset) * left);
                    n3 = (int)(this.mSelectionOffset * child2.getRight() + (1.0f - this.mSelectionOffset) * right);
                }
            }
            this.mSelectedIndicatorPaint.setColor(color);
            canvas.drawRect((float)n2, (float)(height - this.mSelectedIndicatorThickness), (float)n3, (float)height, this.mSelectedIndicatorPaint);
        }
        canvas.drawRect(0.0f, (float)(height - this.mBottomBorderThickness), (float)this.getWidth(), (float)height, this.mBottomBorderPaint);
    }
    
    void onViewPagerPageChanged(final int mSelectedPosition, final float mSelectionOffset) {
        this.mSelectedPosition = mSelectedPosition;
        this.mSelectionOffset = mSelectionOffset;
        this.invalidate();
    }
    
    void setCustomTabColorizer(final SlidingTabLayout.TabColorizer mCustomTabColorizer) {
        this.mCustomTabColorizer = mCustomTabColorizer;
        this.invalidate();
    }
    
    void setSelectedIndicatorColors(final int... indicatorColors) {
        this.mCustomTabColorizer = null;
        this.mDefaultTabColorizer.setIndicatorColors(indicatorColors);
        this.invalidate();
    }
    
    private static class SimpleTabColorizer implements SlidingTabLayout.TabColorizer
    {
        private int[] mIndicatorColors;
        
        @Override
        public final int getIndicatorColor(final int n) {
            return this.mIndicatorColors[n % this.mIndicatorColors.length];
        }
        
        void setIndicatorColors(final int... mIndicatorColors) {
            this.mIndicatorColors = mIndicatorColors;
        }
    }
}
