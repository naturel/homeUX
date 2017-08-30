// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views;

import android.animation.ObjectAnimator;
import com.vanbo.homeux.dravite.newlayouttest.LauncherLog;
import android.view.View.MeasureSpec;
import android.graphics.Canvas;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import android.util.AttributeSet;
import android.content.Context;
import java.util.List;
import android.support.v4.view.ViewPager;
import android.graphics.Paint;
import android.view.View;
import com.dravite.homeux.R;

public class IndicatorView extends View
{
    private static final float MAX_SIZE_DIFFERENCE = 8.0f;
    private int mCurrentSelected;
    private int mDotColor;
    private int mDotCount;
    private Paint mDotPaint;
    private ViewPager mPager;
    private Paint mShadowPaint;
    private List<Float> mSizeDifferences;
    
    public IndicatorView(final Context context) {
        this(context, null);
    }
    
    public IndicatorView(final Context context, final AttributeSet set) {
        this(context, set, 0);
    }
    
    public IndicatorView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.mSizeDifferences = new ArrayList<Float>();
        this.mDotCount = 0;
        this.mCurrentSelected = 2;
        this.init(set);
    }
    
    private void init(@Nullable final AttributeSet set) {
        this.mDotPaint = new Paint();
        this.mShadowPaint = new Paint();
        if (set == null) {
            this.mDotColor = -1;
            this.setDotCount(1);
        }
        else {
            final TypedArray obtainStyledAttributes = this.getContext().obtainStyledAttributes(set, new int[] { R.attr.dotColor });
            this.mDotColor = obtainStyledAttributes.getColor(0, -1);
            obtainStyledAttributes.recycle();
            final TypedArray obtainStyledAttributes2 = this.getContext().obtainStyledAttributes(set, new int[] { R.attr.dotCount });
            this.setDotCount(obtainStyledAttributes2.getInt(0, 1));
            obtainStyledAttributes2.recycle();
        }
        this.mDotPaint.setAntiAlias(true);
        this.mDotPaint.setColor(this.mDotColor);
        this.mShadowPaint.setAntiAlias(true);
        this.mShadowPaint.setColor(1426063360);
    }
    
    private ValueAnimator.AnimatorUpdateListener makeAnimUpdateListener(final int n, final int n2) {
        return (ValueAnimator.AnimatorUpdateListener)new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                if (n < IndicatorView.this.mSizeDifferences.size()) {
                    IndicatorView.this.mSizeDifferences.set(n, (float)valueAnimator.getAnimatedValue());
                }
                IndicatorView.this.mSizeDifferences.set(n2, 8.0f - (float)valueAnimator.getAnimatedValue());
                IndicatorView.this.invalidate();
            }
        };
    }
    
    public void next() {
        if (this.mCurrentSelected + 1 < this.mDotCount) {
            this.setCurrentSelectedAnimated(this.mCurrentSelected + 1);
        }
    }
    
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDotCount > 1) {
            for (int i = 0; i < this.mDotCount; ++i) {
                canvas.drawCircle((float)(this.getWidth() / 2 + (i - this.mDotCount / 2) * 40 + (1 - this.mDotCount % 2) * 20), (float)(this.getHeight() / 2 + 2), this.mSizeDifferences.get(i) + 11.0f, this.mShadowPaint);
                canvas.drawCircle((float)(this.getWidth() / 2 + (i - this.mDotCount / 2) * 40 + (1 - this.mDotCount % 2) * 20), (float)(this.getHeight() / 2), this.mSizeDifferences.get(i) + 10.0f, this.mDotPaint);
            }
        }
    }
    
    protected void onMeasure(final int n, final int n2) {
        this.setMeasuredDimension(View.MeasureSpec.getSize(n), 80);
    }
    
    public void previous() {
        if (this.mCurrentSelected - 1 >= 0) {
            this.setCurrentSelectedAnimated(this.mCurrentSelected - 1);
        }
    }
    
    public void setCurrentSelectedAnimated(final int mCurrentSelected) {
        LauncherLog.d(this.getClass().getName(), mCurrentSelected + " is new selected");
        final int mCurrentSelected2 = this.mCurrentSelected;
        this.mCurrentSelected = mCurrentSelected;
        final ValueAnimator ofFloat = ObjectAnimator.ofFloat(new float[] { 8.0f, 0.0f });
        ofFloat.addUpdateListener(this.makeAnimUpdateListener(mCurrentSelected2, this.mCurrentSelected));
        ofFloat.setDuration(200L);
        ofFloat.start();
    }
    
    public void setCurrentSelectedInstant(final int mCurrentSelected) {
        for (int i = 0; i < this.mDotCount; ++i) {
            final List<Float> mSizeDifferences = this.mSizeDifferences;
            float n;
            if (i == mCurrentSelected) {
                n = 5.0f;
            }
            else {
                n = 0.0f;
            }
            mSizeDifferences.set(i, n);
        }
        this.mCurrentSelected = mCurrentSelected;
        this.invalidate();
    }
    
    public void setDotCount(final int mDotCount) {
        this.mDotCount = mDotCount;
        this.mSizeDifferences.clear();
        for (int i = 0; i < mDotCount; ++i) {
            final List<Float> mSizeDifferences = this.mSizeDifferences;
            float n;
            if (this.mCurrentSelected == i) {
                n = 8.0f;
            }
            else {
                n = 0.0f;
            }
            mSizeDifferences.add(n);
        }
        this.invalidate();
    }
    
    public void setPager(final ViewPager mPager) {
        this.mPager = mPager;
        this.setDotCount(this.mPager.getAdapter().getCount());
        this.mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(final int n) {
            }
            
            @Override
            public void onPageScrolled(final int n, final float n2, final int n3) {
            }
            
            @Override
            public void onPageSelected(final int currentSelectedAnimated) {
                IndicatorView.this.setCurrentSelectedAnimated(currentSelectedAnimated);
            }
        });
    }
    
    public void update() {
        this.setDotCount(this.mPager.getAdapter().getCount());
    }
}
