// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views;

import android.util.TypedValue;
import android.view.ViewGroup.LayoutParams;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.util.AttributeSet;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.widget.HorizontalScrollView;

public class SlidingTabLayout extends HorizontalScrollView
{
    private static final int TAB_VIEW_PADDING_DIPS = 16;
    private static final int TAB_VIEW_TEXT_SIZE_SP = 12;
    private static final int TITLE_OFFSET_DIPS = 24;
    private SparseArray<String> mContentDescriptions;
    private boolean mDistributeEvenly;
    private final SlidingTabStrip mTabStrip;
    private int mTabViewLayoutId;
    private int mTabViewTextViewId;
    private int mTitleOffset;
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;
    
    public SlidingTabLayout(final Context context) {
        this(context, null);
    }
    
    public SlidingTabLayout(final Context context, final AttributeSet set) {
        this(context, set, 0);
    }
    
    public SlidingTabLayout(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.mContentDescriptions = (SparseArray<String>)new SparseArray();
        this.setHorizontalScrollBarEnabled(false);
        this.setFillViewport(true);
        this.mTitleOffset = (int)(24.0f * this.getResources().getDisplayMetrics().density);
        this.addView((View)(this.mTabStrip = new SlidingTabStrip(context)), -1, -2);
    }
    
    private void populateTabStrip() {
        final PagerAdapter adapter = this.mViewPager.getAdapter();
        final TabClickListener onClickListener = new TabClickListener();
        for (int i = 0; i < adapter.getCount(); ++i) {
            Object inflate = null;
            TextView textView = null;
            if (this.mTabViewLayoutId != 0) {
                inflate = LayoutInflater.from(this.getContext()).inflate(this.mTabViewLayoutId, (ViewGroup)this.mTabStrip, false);
                textView = (TextView)((View)inflate).findViewById(this.mTabViewTextViewId);
            }
            Object defaultTabView;
            if ((defaultTabView = inflate) == null) {
                defaultTabView = this.createDefaultTabView(this.getContext());
            }
            TextView textView2;
            if ((textView2 = textView) == null) {
                textView2 = textView;
                if (TextView.class.isInstance(defaultTabView)) {
                    textView2 = (TextView)defaultTabView;
                }
            }
            if (this.mDistributeEvenly) {
                final LinearLayout.LayoutParams linearLayoutLayoutParams = (LinearLayout.LayoutParams)((View)defaultTabView).getLayoutParams();
                linearLayoutLayoutParams.width = 0;
                linearLayoutLayoutParams.weight = 1.0f;
            }
            textView2.setText(adapter.getPageTitle(i));
            ((View)defaultTabView).setOnClickListener((View.OnClickListener)onClickListener);
            final String contentDescription = (String)this.mContentDescriptions.get(i, null);
            if (contentDescription != null) {
                ((View)defaultTabView).setContentDescription((CharSequence)contentDescription);
            }
            this.mTabStrip.addView((View)defaultTabView);
            if (i == this.mViewPager.getCurrentItem()) {
                ((View)defaultTabView).setSelected(true);
            }
            textView2.setTextColor(-1);
        }
    }
    
    private void scrollToTab(int n, final int n2) {
        final int childCount = this.mTabStrip.getChildCount();
        if (childCount != 0 && n >= 0 && n < childCount) {
            final View child = this.mTabStrip.getChildAt(n);
            if (child != null) {
                final int n3 = child.getLeft() + n2;
                Label_0062: {
                    if (n <= 0) {
                        n = n3;
                        if (n2 <= 0) {
                            break Label_0062;
                        }
                    }
                    n = n3 - this.mTitleOffset;
                }
                this.scrollTo(n, 0);
            }
        }
    }
    
    protected TextView createDefaultTabView(final Context context) {
        final TextView textView = new TextView(context);
        textView.setGravity(17);
        textView.setTextSize(2, 12.0f);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-2, -2));
        final TypedValue typedValue = new TypedValue();
        this.getContext().getTheme().resolveAttribute(16843534, typedValue, true);
        textView.setBackgroundResource(typedValue.resourceId);
        textView.setAllCaps(true);
        final int n = (int)(16.0f * this.getResources().getDisplayMetrics().density);
        textView.setPadding(n, n, n, n);
        return textView;
    }
    
    public SlidingTabStrip getSlidingTabStrip() {
        return this.mTabStrip;
    }
    
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mViewPager != null) {
            this.scrollToTab(this.mViewPager.getCurrentItem(), 0);
        }
    }
    
    public void setContentDescription(final int n, final String s) {
        this.mContentDescriptions.put(n, s);
    }
    
    public void setCustomTabColorizer(final TabColorizer customTabColorizer) {
        this.mTabStrip.setCustomTabColorizer(customTabColorizer);
    }
    
    public void setCustomTabView(final int mTabViewLayoutId, final int mTabViewTextViewId) {
        this.mTabViewLayoutId = mTabViewLayoutId;
        this.mTabViewTextViewId = mTabViewTextViewId;
    }
    
    public void setDistributeEvenly(final boolean mDistributeEvenly) {
        this.mDistributeEvenly = mDistributeEvenly;
    }
    
    public void setOnPageChangeListener(final ViewPager.OnPageChangeListener mViewPagerPageChangeListener) {
        this.mViewPagerPageChangeListener = mViewPagerPageChangeListener;
    }
    
    public void setSelectedIndicatorColors(final int... selectedIndicatorColors) {
        this.mTabStrip.setSelectedIndicatorColors(selectedIndicatorColors);
    }
    
    public void setViewPager(final ViewPager mViewPager) {
        this.mTabStrip.removeAllViews();
        this.mViewPager = mViewPager;
        if (mViewPager != null) {
            mViewPager.setOnPageChangeListener(new InternalViewPagerListener());
            this.populateTabStrip();
        }
    }
    
    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener
    {
        private int mScrollState;
        
        @Override
        public void onPageScrollStateChanged(final int mScrollState) {
            this.mScrollState = mScrollState;
            if (SlidingTabLayout.this.mViewPagerPageChangeListener != null) {
                SlidingTabLayout.this.mViewPagerPageChangeListener.onPageScrollStateChanged(mScrollState);
            }
        }
        
        @Override
        public void onPageScrolled(final int n, final float n2, final int n3) {
            final int childCount = SlidingTabLayout.this.mTabStrip.getChildCount();
            if (childCount != 0 && n >= 0 && n < childCount) {
                SlidingTabLayout.this.mTabStrip.onViewPagerPageChanged(n, n2);
                final View child = SlidingTabLayout.this.mTabStrip.getChildAt(n);
                int n4;
                if (child != null) {
                    n4 = (int)(child.getWidth() * n2);
                }
                else {
                    n4 = 0;
                }
                SlidingTabLayout.this.scrollToTab(n, n4);
                if (SlidingTabLayout.this.mViewPagerPageChangeListener != null) {
                    SlidingTabLayout.this.mViewPagerPageChangeListener.onPageScrolled(n, n2, n3);
                }
            }
        }
        
        @Override
        public void onPageSelected(final int n) {
            if (this.mScrollState == 0) {
                SlidingTabLayout.this.mTabStrip.onViewPagerPageChanged(n, 0.0f);
                SlidingTabLayout.this.scrollToTab(n, 0);
            }
            for (int i = 0; i < SlidingTabLayout.this.mTabStrip.getChildCount(); ++i) {
                SlidingTabLayout.this.mTabStrip.getChildAt(i).setSelected(n == i);
            }
            if (SlidingTabLayout.this.mViewPagerPageChangeListener != null) {
                SlidingTabLayout.this.mViewPagerPageChangeListener.onPageSelected(n);
            }
        }
    }
    
    private class TabClickListener implements View.OnClickListener
    {
        public void onClick(final View view) {
            for (int i = 0; i < SlidingTabLayout.this.mTabStrip.getChildCount(); ++i) {
                if (view == SlidingTabLayout.this.mTabStrip.getChildAt(i)) {
                    SlidingTabLayout.this.mViewPager.setCurrentItem(i);
                    break;
                }
            }
        }
    }
    
    public interface TabColorizer
    {
        int getIndicatorColor(final int p0);
    }
}
