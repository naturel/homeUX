// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views;

import android.support.v4.os.ParcelableCompat;
import android.os.Parcel;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.os.Parcelable.Creator;
import android.view.View.BaseSavedState;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.content.res.Resources.NotFoundException;
import android.view.View.MeasureSpec;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.view.ViewConfiguration;
import android.support.v4.view.KeyEventCompat;
import android.os.Build.VERSION;
import android.support.v4.view.VelocityTrackerCompat;
import android.graphics.Canvas;
import android.view.accessibility.AccessibilityEvent;
import android.view.KeyEvent;
import android.os.SystemClock;
import android.view.SoundEffectConstants;
import android.view.FocusFinder;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import java.util.List;
import java.util.Collections;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.graphics.Paint;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.content.Context;
import android.view.VelocityTracker;
import android.graphics.Rect;
import java.lang.reflect.Method;
import android.widget.Scroller;
import android.os.Parcelable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.view.View;
import java.util.ArrayList;
import android.support.v4.widget.EdgeEffectCompat;
import android.support.v4.view.PagerAdapter;
import android.view.animation.Interpolator;
import java.util.Comparator;
import android.view.ViewGroup;
import android.os.Build;
import android.content.res.Resources;

public class VerticalViewPager extends ViewGroup
{
    private static final int CLOSE_ENOUGH = 2;
    private static final Comparator<ItemInfo> COMPARATOR;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_GUTTER_SIZE = 16;
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int DRAW_ORDER_DEFAULT = 0;
    private static final int DRAW_ORDER_FORWARD = 1;
    private static final int DRAW_ORDER_REVERSE = 2;
    private static final int INVALID_POINTER = -1;
    private static final int[] LAYOUT_ATTRS;
    private static final int MAX_SETTLE_DURATION = 400;
    private static final int MIN_DISTANCE_FOR_FLING = 8;
    private static final int MIN_FLING_VELOCITY = 100;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "ViewPager";
    private static final boolean USE_CACHE = true;
    private static final Interpolator sInterpolator;
    private static final ViewPositionComparator sPositionComparator;
    private int mActivePointerId;
    private PagerAdapter mAdapter;
    private OnAdapterChangeListener mAdapterChangeListener;
    private EdgeEffectCompat mBottomEdge;
    private boolean mCalledSuper;
    private int mChildHeightMeasureSpec;
    private int mChildWidthMeasureSpec;
    private int mCloseEnough;
    private int mCurItem;
    private int mDecorChildCount;
    private int mDefaultGutterSize;
    private int mDrawingOrder;
    private ArrayList<View> mDrawingOrderedChildren;
    private final Runnable mEndScrollRunnable;
    private int mExpectedAdapterCount;
    private long mFakeDragBeginTime;
    private boolean mFakeDragging;
    private boolean mFirstLayout;
    private float mFirstOffset;
    private int mFlingDistance;
    private int mGutterSize;
    private boolean mIgnoreGutter;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private ViewPager.OnPageChangeListener mInternalPageChangeListener;
    private boolean mIsBeingDragged;
    private boolean mIsUnableToDrag;
    private final ArrayList<ItemInfo> mItems;
    private float mLastMotionX;
    private float mLastMotionY;
    private float mLastOffset;
    private int mLeftPageBounds;
    private Drawable mMarginDrawable;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private boolean mNeedCalculatePageOffsets;
    private PagerObserver mObserver;
    private int mOffscreenPageLimit;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private int mPageMargin;
    private ViewPager.PageTransformer mPageTransformer;
    private boolean mPopulatePending;
    private Parcelable mRestoredAdapterState;
    private ClassLoader mRestoredClassLoader;
    private int mRestoredCurItem;
    private int mRightPageBounds;
    private int mScrollState;
    private Scroller mScroller;
    private boolean mScrollingCacheEnabled;
    private Method mSetChildrenDrawingOrderEnabled;
    private final ItemInfo mTempItem;
    private final Rect mTempRect;
    private EdgeEffectCompat mTopEdge;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    
    static {
        LAYOUT_ATTRS = new int[] { 16842931 };
        COMPARATOR = new Comparator<ItemInfo>() {
            @Override
            public int compare(final ItemInfo itemInfo, final ItemInfo itemInfo2) {
                return itemInfo.position - itemInfo2.position;
            }
        };
        sInterpolator = (Interpolator)new Interpolator() {
            public float getInterpolation(float n) {
                --n;
                return n * n * n * n * n + 1.0f;
            }
        };
        sPositionComparator = new ViewPositionComparator();
    }
    
    public VerticalViewPager(final Context context) {
        super(context);
        this.mEndScrollRunnable = new Runnable() {
            @Override
            public void run() {
                VerticalViewPager.this.setScrollState(0);
                VerticalViewPager.this.populate();
            }
        };
        this.mScrollState = 0;
        this.mOffscreenPageLimit = 1;
        this.mActivePointerId = -1;
        this.mItems = new ArrayList<ItemInfo>();
        this.mTempItem = new ItemInfo();
        this.mTempRect = new Rect();
        this.mRestoredCurItem = -1;
        this.mRestoredAdapterState = null;
        this.mRestoredClassLoader = null;
        this.mFirstOffset = -3.4028235E38f;
        this.mLastOffset = Float.MAX_VALUE;
        this.mFirstLayout = true;
        this.mNeedCalculatePageOffsets = false;
        this.initViewPager();
    }
    
    public VerticalViewPager(final Context context, final AttributeSet set) {
        super(context, set);
        this.mEndScrollRunnable = new Runnable() {
            @Override
            public void run() {
                VerticalViewPager.this.setScrollState(0);
                VerticalViewPager.this.populate();
            }
        };
        this.mScrollState = 0;
        this.mOffscreenPageLimit = 1;
        this.mActivePointerId = -1;
        this.mItems = new ArrayList<ItemInfo>();
        this.mTempItem = new ItemInfo();
        this.mTempRect = new Rect();
        this.mRestoredCurItem = -1;
        this.mRestoredAdapterState = null;
        this.mRestoredClassLoader = null;
        this.mFirstOffset = -3.4028235E38f;
        this.mLastOffset = Float.MAX_VALUE;
        this.mFirstLayout = true;
        this.mNeedCalculatePageOffsets = false;
        this.initViewPager();
    }
    
    private void calculatePageOffsets(ItemInfo itemInfo, int i, ItemInfo itemInfo2) {
        final int count = this.mAdapter.getCount();
        final int clientHeight = this.getClientHeight();
        float n;
        if (clientHeight > 0) {
            n = this.mPageMargin / clientHeight;
        }
        else {
            n = 0.0f;
        }
        if (itemInfo2 != null) {
            final int position = itemInfo2.position;
            if (position < itemInfo.position) {
                int n2 = 0;
                float n3 = itemInfo2.offset + itemInfo2.heightFactor + n;
                int j;
                for (int n4 = position + 1; n4 <= itemInfo.position && n2 < this.mItems.size(); n4 = j + 1) {
                    itemInfo2 = this.mItems.get(n2);
                    float offset;
                    while (true) {
                        offset = n3;
                        j = n4;
                        if (n4 <= itemInfo2.position) {
                            break;
                        }
                        offset = n3;
                        j = n4;
                        if (n2 >= this.mItems.size() - 1) {
                            break;
                        }
                        ++n2;
                        itemInfo2 = this.mItems.get(n2);
                    }
                    while (j < itemInfo2.position) {
                        offset += this.mAdapter.getPageWidth(j) + n;
                        ++j;
                    }
                    itemInfo2.offset = offset;
                    n3 = offset + (itemInfo2.heightFactor + n);
                }
            }
            else if (position > itemInfo.position) {
                int n5 = this.mItems.size() - 1;
                float offset2 = itemInfo2.offset;
                int k;
                for (int n6 = position - 1; n6 >= itemInfo.position && n5 >= 0; n6 = k - 1) {
                    itemInfo2 = this.mItems.get(n5);
                    float n7;
                    while (true) {
                        n7 = offset2;
                        k = n6;
                        if (n6 >= itemInfo2.position) {
                            break;
                        }
                        n7 = offset2;
                        k = n6;
                        if (n5 <= 0) {
                            break;
                        }
                        --n5;
                        itemInfo2 = this.mItems.get(n5);
                    }
                    while (k > itemInfo2.position) {
                        n7 -= this.mAdapter.getPageWidth(k) + n;
                        --k;
                    }
                    offset2 = n7 - (itemInfo2.heightFactor + n);
                    itemInfo2.offset = offset2;
                }
            }
        }
        final int size = this.mItems.size();
        final float offset3 = itemInfo.offset;
        int l = itemInfo.position - 1;
        float offset4;
        if (itemInfo.position == 0) {
            offset4 = itemInfo.offset;
        }
        else {
            offset4 = -3.4028235E38f;
        }
        this.mFirstOffset = offset4;
        float mLastOffset;
        if (itemInfo.position == count - 1) {
            mLastOffset = itemInfo.offset + itemInfo.heightFactor - 1.0f;
        }
        else {
            mLastOffset = Float.MAX_VALUE;
        }
        this.mLastOffset = mLastOffset;
        int n8 = i - 1;
        float n9 = offset3;
        while (n8 >= 0) {
            for (itemInfo2 = this.mItems.get(n8); l > itemInfo2.position; --l) {
                n9 -= this.mAdapter.getPageWidth(l) + n;
            }
            n9 -= itemInfo2.heightFactor + n;
            itemInfo2.offset = n9;
            if (itemInfo2.position == 0) {
                this.mFirstOffset = n9;
            }
            --n8;
            --l;
        }
        float offset5 = itemInfo.offset + itemInfo.heightFactor + n;
        final int n10 = itemInfo.position + 1;
        final int n11 = i + 1;
        i = n10;
        for (int n12 = n11; n12 < size; ++n12, ++i) {
            for (itemInfo = this.mItems.get(n12); i < itemInfo.position; ++i) {
                offset5 += this.mAdapter.getPageWidth(i) + n;
            }
            if (itemInfo.position == count - 1) {
                this.mLastOffset = itemInfo.heightFactor + offset5 - 1.0f;
            }
            itemInfo.offset = offset5;
            offset5 += itemInfo.heightFactor + n;
        }
        this.mNeedCalculatePageOffsets = false;
    }
    
    private void completeScroll(final boolean b) {
        boolean b2;
        if (this.mScrollState == 2) {
            b2 = true;
        }
        else {
            b2 = false;
        }
        if (b2) {
            this.setScrollingCacheEnabled(false);
            this.mScroller.abortAnimation();
            final int scrollX = this.getScrollX();
            final int scrollY = this.getScrollY();
            final int currX = this.mScroller.getCurrX();
            final int currY = this.mScroller.getCurrY();
            if (scrollX != currX || scrollY != currY) {
                this.scrollTo(currX, currY);
            }
        }
        this.mPopulatePending = false;
        final int n = 0;
        int n2 = b2 ? 1 : 0;
        for (int i = n; i < this.mItems.size(); ++i) {
            final ItemInfo itemInfo = this.mItems.get(i);
            if (itemInfo.scrolling) {
                n2 = 1;
                itemInfo.scrolling = false;
            }
        }
        if (n2 != 0) {
            if (!b) {
                this.mEndScrollRunnable.run();
                return;
            }
            ViewCompat.postOnAnimation((View)this, this.mEndScrollRunnable);
        }
    }
    
    private int determineTargetPage(int n, final float n2, int max, final int n3) {
        if (Math.abs(n3) > this.mFlingDistance && Math.abs(max) > this.mMinimumVelocity) {
            if (max <= 0) {
                ++n;
            }
        }
        else {
            float n4;
            if (n >= this.mCurItem) {
                n4 = 0.4f;
            }
            else {
                n4 = 0.6f;
            }
            n = (int)(n + n2 + n4);
        }
        max = n;
        if (this.mItems.size() > 0) {
            max = Math.max(this.mItems.get(0).position, Math.min(n, this.mItems.get(this.mItems.size() - 1).position));
        }
        return max;
    }
    
    private void enableLayers(final boolean b) {
        for (int childCount = this.getChildCount(), i = 0; i < childCount; ++i) {
            int n;
            if (b) {
                n = 2;
            }
            else {
                n = 0;
            }
            ViewCompat.setLayerType(this.getChildAt(i), n, null);
        }
    }
    
    private void endDrag() {
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }
    
    private Rect getChildRectInPagerCoordinates(final Rect rect, final View view) {
        Rect rect2 = rect;
        if (rect == null) {
            rect2 = new Rect();
        }
        if (view == null) {
            rect2.set(0, 0, 0, 0);
        }
        else {
            rect2.left = view.getLeft();
            rect2.right = view.getRight();
            rect2.top = view.getTop();
            rect2.bottom = view.getBottom();
            VerticalViewPager verticalViewPager;
            for (ViewParent viewParent = view.getParent(); viewParent instanceof ViewGroup && viewParent != this; viewParent = verticalViewPager.getParent()) {
                verticalViewPager = (VerticalViewPager)viewParent;
                rect2.left += verticalViewPager.getLeft();
                rect2.right += verticalViewPager.getRight();
                rect2.top += verticalViewPager.getTop();
                rect2.bottom += verticalViewPager.getBottom();
            }
        }
        return rect2;
    }
    
    private int getClientHeight() {
        return this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom();
    }
    
    private ItemInfo infoForCurrentScrollPosition() {
        float n = 0.0f;
        final int clientHeight = this.getClientHeight();
        float n2;
        if (clientHeight > 0) {
            n2 = this.getScrollY() / clientHeight;
        }
        else {
            n2 = 0.0f;
        }
        if (clientHeight > 0) {
            n = this.mPageMargin / clientHeight;
        }
        int position = -1;
        float offset = 0.0f;
        float heightFactor = 0.0f;
        int n3 = 1;
        ItemInfo itemInfo = null;
        int n4 = 0;
        ItemInfo itemInfo2;
        while (true) {
            itemInfo2 = itemInfo;
            if (n4 >= this.mItems.size()) {
                break;
            }
            final ItemInfo itemInfo3 = this.mItems.get(n4);
            int n5 = n4;
            ItemInfo mTempItem = itemInfo3;
            if (n3 == 0) {
                n5 = n4;
                mTempItem = itemInfo3;
                if (itemInfo3.position != position + 1) {
                    mTempItem = this.mTempItem;
                    mTempItem.offset = offset + heightFactor + n;
                    mTempItem.position = position + 1;
                    mTempItem.heightFactor = this.mAdapter.getPageWidth(mTempItem.position);
                    n5 = n4 - 1;
                }
            }
            offset = mTempItem.offset;
            final float heightFactor2 = mTempItem.heightFactor;
            if (n3 == 0) {
                itemInfo2 = itemInfo;
                if (n2 < offset) {
                    break;
                }
            }
            if (n2 < heightFactor2 + offset + n || n5 == this.mItems.size() - 1) {
                itemInfo2 = mTempItem;
                break;
            }
            n3 = 0;
            position = mTempItem.position;
            heightFactor = mTempItem.heightFactor;
            n4 = n5 + 1;
            itemInfo = mTempItem;
        }
        return itemInfo2;
    }
    
    private boolean isGutterDrag(final float n, final float n2) {
        return (n < this.mGutterSize && n2 > 0.0f) || (n > this.getHeight() - this.mGutterSize && n2 < 0.0f);
    }
    
    private void onSecondaryPointerUp(final MotionEvent motionEvent) {
        final int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
        if (MotionEventCompat.getPointerId(motionEvent, actionIndex) == this.mActivePointerId) {
            int n;
            if (actionIndex == 0) {
                n = 1;
            }
            else {
                n = 0;
            }
            this.mLastMotionY = MotionEventCompat.getY(motionEvent, n);
            this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, n);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }
    
    private boolean pageScrolled(int n) {
        boolean b = false;
        if (this.mItems.size() == 0) {
            this.mCalledSuper = false;
            this.onPageScrolled(0, 0.0f, 0);
            if (!this.mCalledSuper) {
                throw new IllegalStateException("onPageScrolled did not call superclass implementation");
            }
        }
        else {
            final ItemInfo infoForCurrentScrollPosition = this.infoForCurrentScrollPosition();
            final int clientHeight = this.getClientHeight();
            final int mPageMargin = this.mPageMargin;
            final float n2 = this.mPageMargin / clientHeight;
            final int position = infoForCurrentScrollPosition.position;
            final float n3 = (n / clientHeight - infoForCurrentScrollPosition.offset) / (infoForCurrentScrollPosition.heightFactor + n2);
            n = (int)((clientHeight + mPageMargin) * n3);
            this.mCalledSuper = false;
            this.onPageScrolled(position, n3, n);
            if (!this.mCalledSuper) {
                throw new IllegalStateException("onPageScrolled did not call superclass implementation");
            }
            b = true;
        }
        return b;
    }
    
    private boolean performDrag(float mLastMotionY) {
        final boolean b = false;
        final boolean b2 = false;
        boolean b3 = false;
        final float mLastMotionY2 = this.mLastMotionY;
        this.mLastMotionY = mLastMotionY;
        final float n = this.getScrollY() + (mLastMotionY2 - mLastMotionY);
        final int clientHeight = this.getClientHeight();
        mLastMotionY = clientHeight * this.mFirstOffset;
        float n2 = clientHeight * this.mLastOffset;
        boolean b4 = true;
        boolean b5 = true;
        final ItemInfo itemInfo = this.mItems.get(0);
        final ItemInfo itemInfo2 = this.mItems.get(this.mItems.size() - 1);
        if (itemInfo.position != 0) {
            b4 = false;
            mLastMotionY = itemInfo.offset * clientHeight;
        }
        if (itemInfo2.position != this.mAdapter.getCount() - 1) {
            b5 = false;
            n2 = itemInfo2.offset * clientHeight;
        }
        if (n < mLastMotionY) {
            if (b4) {
                b3 = this.mTopEdge.onPull(Math.abs(mLastMotionY - n) / clientHeight);
            }
        }
        else {
            b3 = b;
            mLastMotionY = n;
            if (n > n2) {
                b3 = b2;
                if (b5) {
                    b3 = this.mBottomEdge.onPull(Math.abs(n - n2) / clientHeight);
                }
                mLastMotionY = n2;
            }
        }
        this.mLastMotionX += mLastMotionY - (int)mLastMotionY;
        this.scrollTo(this.getScrollX(), (int)mLastMotionY);
        this.pageScrolled((int)mLastMotionY);
        return b3;
    }
    
    private void recomputeScrollPosition(int n, int n2, int duration, int timePassed) {
        if (n2 > 0 && !this.mItems.isEmpty()) {
            n2 = (n - this.getPaddingTop() - this.getPaddingBottom() + duration) * (this.getScrollY() / (n2 - this.getPaddingTop() - this.getPaddingBottom() + timePassed));
            this.scrollTo(this.getScrollX(), n2);
            if (!this.mScroller.isFinished()) {
                duration = this.mScroller.getDuration();
                timePassed = this.mScroller.timePassed();
                this.mScroller.startScroll(0, n2, 0, (int)(this.infoForPosition(this.mCurItem).offset * n), duration - timePassed);
            }
        }
        else {
            final ItemInfo infoForPosition = this.infoForPosition(this.mCurItem);
            float min;
            if (infoForPosition != null) {
                min = Math.min(infoForPosition.offset, this.mLastOffset);
            }
            else {
                min = 0.0f;
            }
            n = (int)((n - this.getPaddingTop() - this.getPaddingBottom()) * min);
            if (n != this.getScrollY()) {
                this.completeScroll(false);
                this.scrollTo(this.getScrollX(), n);
            }
        }
    }
    
    private void removeNonDecorViews() {
        int n;
        for (int i = 0; i < this.getChildCount(); i = n + 1) {
            n = i;
            if (!((LayoutParams)this.getChildAt(i).getLayoutParams()).isDecor) {
                this.removeViewAt(i);
                n = i - 1;
            }
        }
    }
    
    private void requestParentDisallowInterceptTouchEvent(final boolean b) {
        final ViewParent parent = this.getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(b);
        }
    }
    
    private void scrollToItem(final int n, final boolean b, final int n2, final boolean b2) {
        final ItemInfo infoForPosition = this.infoForPosition(n);
        int n3 = 0;
        if (infoForPosition != null) {
            n3 = (int)(this.getClientHeight() * Math.max(this.mFirstOffset, Math.min(infoForPosition.offset, this.mLastOffset)));
        }
        if (b) {
            this.smoothScrollTo(0, n3, n2);
            if (b2 && this.mOnPageChangeListener != null) {
                this.mOnPageChangeListener.onPageSelected(n);
            }
            if (b2 && this.mInternalPageChangeListener != null) {
                this.mInternalPageChangeListener.onPageSelected(n);
            }
            return;
        }
        if (b2 && this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageSelected(n);
        }
        if (b2 && this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageSelected(n);
        }
        this.completeScroll(false);
        this.scrollTo(0, n3);
        this.pageScrolled(n3);
    }
    
    private void setScrollState(final int mScrollState) {
        if (this.mScrollState != mScrollState) {
            this.mScrollState = mScrollState;
            if (this.mPageTransformer != null) {
                this.enableLayers(mScrollState != 0);
            }
            if (this.mOnPageChangeListener != null) {
                this.mOnPageChangeListener.onPageScrollStateChanged(mScrollState);
            }
        }
    }
    
    private void setScrollingCacheEnabled(final boolean b) {
        if (this.mScrollingCacheEnabled != b) {
            this.mScrollingCacheEnabled = b;
            for (int childCount = this.getChildCount(), i = 0; i < childCount; ++i) {
                final View child = this.getChildAt(i);
                if (child.getVisibility() != View.GONE) {
                    child.setDrawingCacheEnabled(b);
                }
            }
        }
    }
    
    private void sortChildDrawingOrder() {
        if (this.mDrawingOrder != 0) {
            if (this.mDrawingOrderedChildren == null) {
                this.mDrawingOrderedChildren = new ArrayList<View>();
            }
            else {
                this.mDrawingOrderedChildren.clear();
            }
            for (int childCount = this.getChildCount(), i = 0; i < childCount; ++i) {
                this.mDrawingOrderedChildren.add(this.getChildAt(i));
            }
            Collections.sort(this.mDrawingOrderedChildren, VerticalViewPager.sPositionComparator);
        }
    }
    
    public void addFocusables(final ArrayList<View> list, final int n, final int n2) {
        final int size = list.size();
        final int descendantFocusability = this.getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i = 0; i < this.getChildCount(); ++i) {
                final View child = this.getChildAt(i);
                if (child.getVisibility() == View.VISIBLE) {
                    final ItemInfo infoForChild = this.infoForChild(child);
                    if (infoForChild != null && infoForChild.position == this.mCurItem) {
                        child.addFocusables((ArrayList)list, n, n2);
                    }
                }
            }
        }
        if ((descendantFocusability != 262144 || size == list.size()) && this.isFocusable() && ((n2 & 0x1) != 0x1 || !this.isInTouchMode() || this.isFocusableInTouchMode()) && list != null) {
            list.add((View)this);
        }
    }
    
    ItemInfo addNewItem(final int position, final int n) {
        final ItemInfo itemInfo = new ItemInfo();
        itemInfo.position = position;
        itemInfo.object = this.mAdapter.instantiateItem(this, position);
        itemInfo.heightFactor = this.mAdapter.getPageWidth(position);
        if (n < 0 || n >= this.mItems.size()) {
            this.mItems.add(itemInfo);
            return itemInfo;
        }
        this.mItems.add(n, itemInfo);
        return itemInfo;
    }
    
    public void addTouchables(final ArrayList<View> list) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            final View child = this.getChildAt(i);
            if (child.getVisibility() == View.VISIBLE) {
                final ItemInfo infoForChild = this.infoForChild(child);
                if (infoForChild != null && infoForChild.position == this.mCurItem) {
                    child.addTouchables((ArrayList)list);
                }
            }
        }
    }
    
    public void addView(final View view, final int n, final ViewGroup.LayoutParams viewGroupLayoutParams) {
        ViewGroup.LayoutParams generateLayoutParams = viewGroupLayoutParams;
        if (!this.checkLayoutParams(viewGroupLayoutParams)) {
            generateLayoutParams = this.generateLayoutParams(viewGroupLayoutParams);
        }
        final LayoutParams layoutParams = (LayoutParams)generateLayoutParams;
        layoutParams.isDecor |= (view instanceof Decor);
        if (this.mInLayout) {
            if (layoutParams != null && layoutParams.isDecor) {
                throw new IllegalStateException("Cannot add pager decor view during layout");
            }
            layoutParams.needsMeasure = true;
            this.addViewInLayout(view, n, generateLayoutParams);
        }
        else {
            super.addView(view, n, generateLayoutParams);
        }
        if (view.getVisibility() != View.GONE) {
            view.setDrawingCacheEnabled(this.mScrollingCacheEnabled);
            return;
        }
        view.setDrawingCacheEnabled(false);
    }
    
    public boolean arrowScroll(final int n) {
        final View focus = this.findFocus();
        View view;
        if (focus == this) {
            view = null;
        }
        else if ((view = focus) != null) {
            final boolean b = false;
            ViewParent viewParent = focus.getParent();
            boolean b2;
            while (true) {
                b2 = b;
                if (!(viewParent instanceof ViewGroup)) {
                    break;
                }
                if (viewParent == this) {
                    b2 = true;
                    break;
                }
                viewParent = viewParent.getParent();
            }
            view = focus;
            if (!b2) {
                final StringBuilder sb = new StringBuilder();
                sb.append(focus.getClass().getSimpleName());
                for (ViewParent viewParent2 = focus.getParent(); viewParent2 instanceof ViewGroup; viewParent2 = viewParent2.getParent()) {
                    sb.append(" => ").append(viewParent2.getClass().getSimpleName());
                }
                Log.e("ViewPager", "arrowScroll tried to find focus based on non-child current focused view " + sb.toString());
                view = null;
            }
        }
        boolean b3 = false;
        final View nextFocus = FocusFinder.getInstance().findNextFocus((ViewGroup)this, view, n);
        if (nextFocus != null && nextFocus != view) {
            if (n == 33) {
                final int top = this.getChildRectInPagerCoordinates(this.mTempRect, nextFocus).top;
                final int top2 = this.getChildRectInPagerCoordinates(this.mTempRect, view).top;
                if (view != null && top >= top2) {
                    b3 = this.pageUp();
                }
                else {
                    b3 = nextFocus.requestFocus();
                }
            }
            else if (n == 130) {
                final int bottom = this.getChildRectInPagerCoordinates(this.mTempRect, nextFocus).bottom;
                final int bottom2 = this.getChildRectInPagerCoordinates(this.mTempRect, view).bottom;
                if (view != null && bottom <= bottom2) {
                    b3 = this.pageDown();
                }
                else {
                    b3 = nextFocus.requestFocus();
                }
            }
        }
        else if (n == 33 || n == 1) {
            b3 = this.pageUp();
        }
        else if (n == 130 || n == 2) {
            b3 = this.pageDown();
        }
        if (b3) {
            this.playSoundEffect(SoundEffectConstants.getContantForFocusDirection(n));
        }
        return b3;
    }
    
    public boolean beginFakeDrag() {
        if (this.mIsBeingDragged) {
            return false;
        }
        this.mFakeDragging = true;
        this.setScrollState(1);
        this.mLastMotionY = 0.0f;
        this.mInitialMotionY = 0.0f;
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        else {
            this.mVelocityTracker.clear();
        }
        final long uptimeMillis = SystemClock.uptimeMillis();
        final MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 0, 0.0f, 0.0f, 0);
        this.mVelocityTracker.addMovement(obtain);
        obtain.recycle();
        this.mFakeDragBeginTime = uptimeMillis;
        return true;
    }
    
    protected boolean canScroll(final View view, final boolean b, final int n, final int n2, final int n3) {
        if (view instanceof ViewGroup) {
            final ViewGroup viewGroup = (ViewGroup)view;
            final int scrollX = view.getScrollX();
            final int scrollY = view.getScrollY();
            for (int i = viewGroup.getChildCount() - 1; i >= 0; --i) {
                final View child = viewGroup.getChildAt(i);
                if (n3 + scrollY >= child.getTop() && n3 + scrollY < child.getBottom() && n2 + scrollX >= child.getLeft() && n2 + scrollX < child.getRight() && this.canScroll(child, true, n, n2 + scrollX - child.getLeft(), n3 + scrollY - child.getTop())) {
                    return true;
                }
            }
        }
        return b && ViewCompat.canScrollVertically(view, -n);
    }
    
    protected boolean checkLayoutParams(final ViewGroup.LayoutParams viewGroupLayoutParams) {
        return viewGroupLayoutParams instanceof LayoutParams && super.checkLayoutParams(viewGroupLayoutParams);
    }
    
    public void computeScroll() {
        if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
            final int scrollX = this.getScrollX();
            final int scrollY = this.getScrollY();
            final int currX = this.mScroller.getCurrX();
            final int currY = this.mScroller.getCurrY();
            if (scrollX != currX || scrollY != currY) {
                this.scrollTo(currX, currY);
                if (!this.pageScrolled(currY)) {
                    this.mScroller.abortAnimation();
                    this.scrollTo(currX, 0);
                }
            }
            ViewCompat.postInvalidateOnAnimation((View)this);
            return;
        }
        this.completeScroll(true);
    }
    
    void dataSetChanged() {
        final int count = this.mAdapter.getCount();
        this.mExpectedAdapterCount = count;
        boolean b = this.mItems.size() < this.mOffscreenPageLimit * 2 + 1 && this.mItems.size() < count;
        int mCurItem = this.mCurItem;
        int n = 0;
        int max;
        int n2;
        int n3;
        for (int i = 0; i < this.mItems.size(); i = n3 + 1, n = n2, mCurItem = max) {
            final ItemInfo itemInfo = this.mItems.get(i);
            final int itemPosition = this.mAdapter.getItemPosition(itemInfo.object);
            if (itemPosition == -1) {
                max = mCurItem;
                n2 = n;
                n3 = i;
            }
            else if (itemPosition == -2) {
                this.mItems.remove(i);
                final int n4 = i - 1;
                int n5;
                if ((n5 = n) == 0) {
                    this.mAdapter.startUpdate(this);
                    n5 = 1;
                }
                this.mAdapter.destroyItem(this, itemInfo.position, itemInfo.object);
                b = true;
                n3 = n4;
                n2 = n5;
                max = mCurItem;
                if (this.mCurItem == itemInfo.position) {
                    max = Math.max(0, Math.min(this.mCurItem, count - 1));
                    b = true;
                    n3 = n4;
                    n2 = n5;
                }
            }
            else {
                n3 = i;
                n2 = n;
                max = mCurItem;
                if (itemInfo.position != itemPosition) {
                    if (itemInfo.position == this.mCurItem) {
                        mCurItem = itemPosition;
                    }
                    itemInfo.position = itemPosition;
                    b = true;
                    n3 = i;
                    n2 = n;
                    max = mCurItem;
                }
            }
        }
        if (n != 0) {
            this.mAdapter.finishUpdate(this);
        }
        Collections.sort(this.mItems, VerticalViewPager.COMPARATOR);
        if (b) {
            for (int childCount = this.getChildCount(), j = 0; j < childCount; ++j) {
                final LayoutParams layoutParams = (LayoutParams)this.getChildAt(j).getLayoutParams();
                if (!layoutParams.isDecor) {
                    layoutParams.heightFactor = 0.0f;
                }
            }
            this.setCurrentItemInternal(mCurItem, false, true);
            this.requestLayout();
        }
    }
    
    public boolean dispatchKeyEvent(final KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || this.executeKeyEvent(keyEvent);
    }
    
    public boolean dispatchPopulateAccessibilityEvent(final AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        for (int childCount = this.getChildCount(), i = 0; i < childCount; ++i) {
            final View child = this.getChildAt(i);
            if (child.getVisibility() == View.VISIBLE) {
                final ItemInfo infoForChild = this.infoForChild(child);
                if (infoForChild != null && infoForChild.position == this.mCurItem && child.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    float distanceInfluenceForSnapDuration(final float n) {
        return (float)Math.sin((float)((n - 0.5f) * 0.4712389167638204));
    }
    
    public void draw(final Canvas canvas) {
        super.draw(canvas);
        boolean b = false;
        boolean b2 = false;
        final int overScrollMode = ViewCompat.getOverScrollMode((View)this);
        if (overScrollMode == 0 || (overScrollMode == 1 && this.mAdapter != null && this.mAdapter.getCount() > 1)) {
            if (!this.mTopEdge.isFinished()) {
                final int save = canvas.save();
                final int height = this.getHeight();
                final int width = this.getWidth();
                final int paddingLeft = this.getPaddingLeft();
                final int paddingRight = this.getPaddingRight();
                canvas.translate((float)this.getPaddingLeft(), this.mFirstOffset * height);
                this.mTopEdge.setSize(width - paddingLeft - paddingRight, height);
                b2 = (false | this.mTopEdge.draw(canvas));
                canvas.restoreToCount(save);
            }
            b = b2;
            if (!this.mBottomEdge.isFinished()) {
                final int save2 = canvas.save();
                final int height2 = this.getHeight();
                final int n = this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
                canvas.rotate(180.0f);
                canvas.translate((float)(-n - this.getPaddingLeft()), -(this.mLastOffset + 1.0f) * height2);
                this.mBottomEdge.setSize(n, height2);
                b = (b2 | this.mBottomEdge.draw(canvas));
                canvas.restoreToCount(save2);
            }
        }
        else {
            this.mTopEdge.finish();
            this.mBottomEdge.finish();
        }
        if (b) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }
    
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        final Drawable mMarginDrawable = this.mMarginDrawable;
        if (mMarginDrawable != null && mMarginDrawable.isStateful()) {
            mMarginDrawable.setState(this.getDrawableState());
        }
    }
    
    public void endFakeDrag() {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        final VelocityTracker mVelocityTracker = this.mVelocityTracker;
        mVelocityTracker.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
        final int n = (int)VelocityTrackerCompat.getYVelocity(mVelocityTracker, this.mActivePointerId);
        this.mPopulatePending = true;
        final int clientHeight = this.getClientHeight();
        final int scrollY = this.getScrollY();
        final ItemInfo infoForCurrentScrollPosition = this.infoForCurrentScrollPosition();
        this.setCurrentItemInternal(this.determineTargetPage(infoForCurrentScrollPosition.position, (scrollY / clientHeight - infoForCurrentScrollPosition.offset) / infoForCurrentScrollPosition.heightFactor, n, (int)(this.mLastMotionY - this.mInitialMotionY)), true, true, n);
        this.endDrag();
        this.mFakeDragging = false;
    }
    
    public boolean executeKeyEvent(final KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            switch (keyEvent.getKeyCode()) {
                case 21: {
                    return this.arrowScroll(17);
                }
                case 22: {
                    return this.arrowScroll(66);
                }
                case 61: {
                    if (Build.VERSION.SDK_INT < 11) {
                        break;
                    }
                    if (KeyEventCompat.hasNoModifiers(keyEvent)) {
                        return this.arrowScroll(2);
                    }
                    if (KeyEventCompat.hasModifiers(keyEvent, 1)) {
                        return this.arrowScroll(1);
                    }
                    break;
                }
            }
        }
        return false;
    }
    
    public void fakeDragBy(float n) {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        this.mLastMotionY += n;
        final float n2 = this.getScrollY() - n;
        final int clientHeight = this.getClientHeight();
        n = clientHeight * this.mFirstOffset;
        float n3 = clientHeight * this.mLastOffset;
        final ItemInfo itemInfo = this.mItems.get(0);
        final ItemInfo itemInfo2 = this.mItems.get(this.mItems.size() - 1);
        if (itemInfo.position != 0) {
            n = itemInfo.offset * clientHeight;
        }
        if (itemInfo2.position != this.mAdapter.getCount() - 1) {
            n3 = itemInfo2.offset * clientHeight;
        }
        if (n2 >= n) {
            n = n2;
            if (n2 > n3) {
                n = n3;
            }
        }
        this.mLastMotionY += n - (int)n;
        this.scrollTo(this.getScrollX(), (int)n);
        this.pageScrolled((int)n);
        final MotionEvent obtain = MotionEvent.obtain(this.mFakeDragBeginTime, SystemClock.uptimeMillis(), 2, 0.0f, this.mLastMotionY, 0);
        this.mVelocityTracker.addMovement(obtain);
        obtain.recycle();
    }
    
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }
    
    public ViewGroup.LayoutParams generateLayoutParams(final AttributeSet set) {
        return new LayoutParams(this.getContext(), set);
    }
    
    protected ViewGroup.LayoutParams generateLayoutParams(final ViewGroup.LayoutParams viewGroupLayoutParams) {
        return this.generateDefaultLayoutParams();
    }
    
    public PagerAdapter getAdapter() {
        return this.mAdapter;
    }
    
    protected int getChildDrawingOrder(int n, final int n2) {
        if (this.mDrawingOrder == 2) {
            n = n - 1 - n2;
        }
        else {
            n = n2;
        }
        return ((LayoutParams)this.mDrawingOrderedChildren.get(n).getLayoutParams()).childIndex;
    }
    
    public int getCurrentItem() {
        return this.mCurItem;
    }
    
    public int getOffscreenPageLimit() {
        return this.mOffscreenPageLimit;
    }
    
    public int getPageMargin() {
        return this.mPageMargin;
    }
    
    ItemInfo infoForAnyChild(View view) {
        while (true) {
            final ViewParent parent = view.getParent();
            if (parent == this) {
                return this.infoForChild(view);
            }
            if (parent == null || !(parent instanceof View)) {
                return null;
            }
            view = (View)parent;
        }
    }
    
    ItemInfo infoForChild(final View view) {
        for (int i = 0; i < this.mItems.size(); ++i) {
            final ItemInfo itemInfo = this.mItems.get(i);
            if (this.mAdapter.isViewFromObject(view, itemInfo.object)) {
                return itemInfo;
            }
        }
        return null;
    }
    
    ItemInfo infoForPosition(final int n) {
        for (int i = 0; i < this.mItems.size(); ++i) {
            final ItemInfo itemInfo = this.mItems.get(i);
            if (itemInfo.position == n) {
                return itemInfo;
            }
        }
        return null;
    }
    
    void initViewPager() {
        this.setWillNotDraw(false);
        this.setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
        this.setFocusable(true);
        final Context context = this.getContext();
        this.mScroller = new Scroller(context, VerticalViewPager.sInterpolator);
        final ViewConfiguration value = ViewConfiguration.get(context);
        final float density = context.getResources().getDisplayMetrics().density;
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(value);
        this.mMinimumVelocity = (int)(100.0f * density);
        this.mMaximumVelocity = value.getScaledMaximumFlingVelocity();
        this.mTopEdge = new EdgeEffectCompat(context);
        this.mBottomEdge = new EdgeEffectCompat(context);
        this.mFlingDistance = (int)(8.0f * density);
        this.mCloseEnough = (int)(2.0f * density);
        this.mDefaultGutterSize = (int)(16.0f * density);
        ViewCompat.setAccessibilityDelegate((View)this, new MyAccessibilityDelegate());
        if (ViewCompat.getImportantForAccessibility((View)this) == 0) {
            ViewCompat.setImportantForAccessibility((View)this, 1);
        }
    }
    
    public boolean internalCanScrollVertically(final int n) {
        final boolean b = true;
        boolean b2 = true;
        if (this.mAdapter != null) {
            final int clientHeight = this.getClientHeight();
            final int scrollY = this.getScrollY();
            if (n < 0) {
                if (scrollY <= (int)(clientHeight * this.mFirstOffset)) {
                    b2 = false;
                }
                return b2;
            }
            if (n > 0) {
                return scrollY < (int)(clientHeight * this.mLastOffset) && b;
            }
        }
        return false;
    }
    
    public boolean isFakeDragging() {
        return this.mFakeDragging;
    }
    
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }
    
    protected void onDetachedFromWindow() {
        this.removeCallbacks(this.mEndScrollRunnable);
        super.onDetachedFromWindow();
    }
    
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPageMargin > 0 && this.mMarginDrawable != null && this.mItems.size() > 0 && this.mAdapter != null) {
            final int scrollY = this.getScrollY();
            final int height = this.getHeight();
            final float n = this.mPageMargin / height;
            int n2 = 0;
            ItemInfo itemInfo = this.mItems.get(0);
            float offset = itemInfo.offset;
            for (int size = this.mItems.size(), i = itemInfo.position; i < this.mItems.get(size - 1).position; ++i) {
                while (i > itemInfo.position && n2 < size) {
                    final ArrayList<ItemInfo> mItems = this.mItems;
                    ++n2;
                    itemInfo = mItems.get(n2);
                }
                float n3;
                if (i == itemInfo.position) {
                    n3 = (itemInfo.offset + itemInfo.heightFactor) * height;
                    offset = itemInfo.offset + itemInfo.heightFactor + n;
                }
                else {
                    final float pageWidth = this.mAdapter.getPageWidth(i);
                    n3 = (offset + pageWidth) * height;
                    offset += pageWidth + n;
                }
                if (this.mPageMargin + n3 > scrollY) {
                    this.mMarginDrawable.setBounds(this.mLeftPageBounds, (int)n3, this.mRightPageBounds, (int)(this.mPageMargin + n3 + 0.5f));
                    this.mMarginDrawable.draw(canvas);
                }
                if (n3 > scrollY + height) {
                    break;
                }
            }
        }
    }
    
    public boolean onInterceptTouchEvent(final MotionEvent motionEvent) {
        final int n = motionEvent.getAction() & 0xFF;
        if (n == 3 || n == 1) {
            this.mIsBeingDragged = false;
            this.mIsUnableToDrag = false;
            this.mActivePointerId = -1;
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
            }
            return false;
        }
        if (n != 0) {
            if (this.mIsBeingDragged) {
                return true;
            }
            if (this.mIsUnableToDrag) {
                return false;
            }
        }
        switch (n) {
            case 2: {
                final int mActivePointerId = this.mActivePointerId;
                if (mActivePointerId == -1) {
                    break;
                }
                final int pointerIndex = MotionEventCompat.findPointerIndex(motionEvent, mActivePointerId);
                final float y = MotionEventCompat.getY(motionEvent, pointerIndex);
                final float n2 = y - this.mLastMotionY;
                final float abs = Math.abs(n2);
                final float x = MotionEventCompat.getX(motionEvent, pointerIndex);
                final float abs2 = Math.abs(x - this.mInitialMotionX);
                if (n2 != 0.0f && !this.isGutterDrag(this.mLastMotionY, n2) && this.canScroll((View)this, false, (int)n2, (int)x, (int)y)) {
                    this.mLastMotionX = x;
                    this.mLastMotionY = y;
                    this.mIsUnableToDrag = true;
                    return false;
                }
                if (abs > this.mTouchSlop && 0.5f * abs > abs2) {
                    this.requestParentDisallowInterceptTouchEvent(this.mIsBeingDragged = true);
                    this.setScrollState(1);
                    float mLastMotionY;
                    if (n2 > 0.0f) {
                        mLastMotionY = this.mInitialMotionY + this.mTouchSlop;
                    }
                    else {
                        mLastMotionY = this.mInitialMotionY - this.mTouchSlop;
                    }
                    this.mLastMotionY = mLastMotionY;
                    this.mLastMotionX = x;
                    this.setScrollingCacheEnabled(true);
                }
                else if (abs2 > this.mTouchSlop) {
                    this.mIsUnableToDrag = true;
                }
                if (this.mIsBeingDragged && this.performDrag(y)) {
                    ViewCompat.postInvalidateOnAnimation((View)this);
                    break;
                }
                break;
            }
            case 0: {
                final float x2 = motionEvent.getX();
                this.mInitialMotionX = x2;
                this.mLastMotionX = x2;
                final float y2 = motionEvent.getY();
                this.mInitialMotionY = y2;
                this.mLastMotionY = y2;
                this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                this.mIsUnableToDrag = false;
                this.mScroller.computeScrollOffset();
                if (this.mScrollState == 2 && Math.abs(this.mScroller.getFinalY() - this.mScroller.getCurrY()) > this.mCloseEnough) {
                    this.mScroller.abortAnimation();
                    this.mPopulatePending = false;
                    this.populate();
                    this.requestParentDisallowInterceptTouchEvent(this.mIsBeingDragged = true);
                    this.setScrollState(1);
                    break;
                }
                this.completeScroll(false);
                this.mIsBeingDragged = false;
                break;
            }
            case 6: {
                this.onSecondaryPointerUp(motionEvent);
                break;
            }
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        return this.mIsBeingDragged;
    }
    
    protected void onLayout(final boolean b, int paddingTop, int mLeftPageBounds, int i, int paddingBottom) {
        final int childCount = this.getChildCount();
        final int n = i - paddingTop;
        final int n2 = paddingBottom - mLeftPageBounds;
        mLeftPageBounds = this.getPaddingLeft();
        paddingTop = this.getPaddingTop();
        int paddingRight = this.getPaddingRight();
        paddingBottom = this.getPaddingBottom();
        final int scrollY = this.getScrollY();
        int mDecorChildCount = 0;
        int n3;
        int n4;
        int n5;
        int n6;
        for (int j = 0; j < childCount; ++j, mDecorChildCount = n3, paddingBottom = n4, mLeftPageBounds = n5, paddingRight = n6, paddingTop = i) {
            final View child = this.getChildAt(j);
            n3 = mDecorChildCount;
            n4 = paddingBottom;
            n5 = mLeftPageBounds;
            n6 = paddingRight;
            i = paddingTop;
            if (child.getVisibility() != View.GONE) {
                final LayoutParams layoutParams = (LayoutParams)child.getLayoutParams();
                n3 = mDecorChildCount;
                n4 = paddingBottom;
                n5 = mLeftPageBounds;
                n6 = paddingRight;
                i = paddingTop;
                if (layoutParams.isDecor) {
                    i = layoutParams.gravity;
                    final int gravity = layoutParams.gravity;
                    switch (i & 0x7) {
                        default: {
                            i = mLeftPageBounds;
                            n5 = mLeftPageBounds;
                            break;
                        }
                        case 3: {
                            i = mLeftPageBounds;
                            n5 = mLeftPageBounds + child.getMeasuredWidth();
                            break;
                        }
                        case 1: {
                            i = Math.max((n - child.getMeasuredWidth()) / 2, mLeftPageBounds);
                            n5 = mLeftPageBounds;
                            break;
                        }
                        case 5: {
                            i = n - paddingRight - child.getMeasuredWidth();
                            paddingRight += child.getMeasuredWidth();
                            n5 = mLeftPageBounds;
                            break;
                        }
                    }
                    switch (gravity & 0x70) {
                        default: {
                            mLeftPageBounds = paddingTop;
                            break;
                        }
                        case 48: {
                            mLeftPageBounds = paddingTop;
                            paddingTop += child.getMeasuredHeight();
                            break;
                        }
                        case 16: {
                            mLeftPageBounds = Math.max((n2 - child.getMeasuredHeight()) / 2, paddingTop);
                            break;
                        }
                        case 80: {
                            mLeftPageBounds = n2 - paddingBottom - child.getMeasuredHeight();
                            paddingBottom += child.getMeasuredHeight();
                            break;
                        }
                    }
                    mLeftPageBounds += scrollY;
                    child.layout(i, mLeftPageBounds, child.getMeasuredWidth() + i, child.getMeasuredHeight() + mLeftPageBounds);
                    n3 = mDecorChildCount + 1;
                    i = paddingTop;
                    n6 = paddingRight;
                    n4 = paddingBottom;
                }
            }
        }
        paddingBottom = n2 - paddingTop - paddingBottom;
        View child2;
        LayoutParams layoutParams2;
        ItemInfo infoForChild;
        int n7;
        for (i = 0; i < childCount; ++i) {
            child2 = this.getChildAt(i);
            if (child2.getVisibility() != View.GONE) {
                layoutParams2 = (LayoutParams)child2.getLayoutParams();
                if (!layoutParams2.isDecor) {
                    infoForChild = this.infoForChild(child2);
                    if (infoForChild != null) {
                        n7 = paddingTop + (int)(paddingBottom * infoForChild.offset);
                        if (layoutParams2.needsMeasure) {
                            layoutParams2.needsMeasure = false;
                            child2.measure(View.MeasureSpec.makeMeasureSpec(n - mLeftPageBounds - paddingRight, MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec((int)(paddingBottom * layoutParams2.heightFactor), MeasureSpec.EXACTLY));
                        }
                        child2.layout(mLeftPageBounds, n7, child2.getMeasuredWidth() + mLeftPageBounds, child2.getMeasuredHeight() + n7);
                    }
                }
            }
        }
        this.mLeftPageBounds = mLeftPageBounds;
        this.mRightPageBounds = n - paddingRight;
        this.mDecorChildCount = mDecorChildCount;
        if (this.mFirstLayout) {
            this.scrollToItem(this.mCurItem, false, 0, false);
        }
        this.mFirstLayout = false;
    }
    
    protected void onMeasure(int measuredHeight, int i) {
        this.setMeasuredDimension(getDefaultSize(0, measuredHeight), getDefaultSize(0, i));
        measuredHeight = this.getMeasuredHeight();
        this.mGutterSize = Math.min(measuredHeight / 10, this.mDefaultGutterSize);
        i = this.getMeasuredWidth() - this.getPaddingLeft() - this.getPaddingRight();
        measuredHeight = measuredHeight - this.getPaddingTop() - this.getPaddingBottom();
        int n;
        int n2;
        for (int childCount = this.getChildCount(), j = 0; j < childCount; ++j, measuredHeight = n, i = n2) {
            final View child = this.getChildAt(j);
            n = measuredHeight;
            n2 = i;
            if (child.getVisibility() != View.GONE) {
                final LayoutParams layoutParams = (LayoutParams)child.getLayoutParams();
                n = measuredHeight;
                n2 = i;
                if (layoutParams != null) {
                    n = measuredHeight;
                    n2 = i;
                    if (layoutParams.isDecor) {
                        final int n3 = layoutParams.gravity & 0x7;
                        final int n4 = layoutParams.gravity & 0x70;
                        final int n5 = Integer.MIN_VALUE;
                        int n6 = Integer.MIN_VALUE;
                        boolean b;
                        if (n4 == 48 || n4 == 80) {
                            b = true;
                        }
                        else {
                            b = false;
                        }
                        final boolean b2 = n3 == 3 || n3 == 5;
                        int n7;
                        if (b) {
                            n7 = MeasureSpec.EXACTLY;
                        }
                        else {
                            n7 = n5;
                            if (b2) {
                                n6 = MeasureSpec.EXACTLY;
                                n7 = n5;
                            }
                        }
                        final int n8 = i;
                        final int n9 = measuredHeight;
                        int width = n8;
                        if (layoutParams.width != -2) {
                            n7 = MeasureSpec.EXACTLY;
                            width = n8;
                            if (layoutParams.width != -1) {
                                width = layoutParams.width;
                                n7 = n7;
                            }
                        }
                        int height = n9;
                        if (layoutParams.height != -2) {
                            n6 = MeasureSpec.EXACTLY;
                            height = n9;
                            if (layoutParams.height != -1) {
                                height = layoutParams.height;
                                n6 = n6;
                            }
                        }
                        child.measure(View.MeasureSpec.makeMeasureSpec(width, (n7 == MeasureSpec.EXACTLY)?MeasureSpec.EXACTLY:MeasureSpec.AT_MOST ), View.MeasureSpec.makeMeasureSpec(height, (n6 == MeasureSpec.EXACTLY)?MeasureSpec.EXACTLY:MeasureSpec.AT_MOST ));
                        if (b) {
                            n = measuredHeight - child.getMeasuredHeight();
                            n2 = i;
                        }
                        else {
                            n = measuredHeight;
                            n2 = i;
                            if (b2) {
                                n2 = i - child.getMeasuredWidth();
                                n = measuredHeight;
                            }
                        }
                    }
                }
            }
        }
        this.mChildWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(i, MeasureSpec.EXACTLY);
        this.mChildHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY);
        this.mInLayout = true;
        this.populate();
        this.mInLayout = false;
        int childCount2;
        View child2;
        LayoutParams layoutParams2;
        for (childCount2 = this.getChildCount(), i = 0; i < childCount2; ++i) {
            child2 = this.getChildAt(i);
            if (child2.getVisibility() != View.GONE) {
                layoutParams2 = (LayoutParams)child2.getLayoutParams();
                if (layoutParams2 == null || !layoutParams2.isDecor) {
                    child2.measure(this.mChildWidthMeasureSpec, View.MeasureSpec.makeMeasureSpec((int)(measuredHeight * layoutParams2.heightFactor), MeasureSpec.EXACTLY));
                }
            }
        }
    }
    
    protected void onPageScrolled(int i, float n, int scrollY) {
        if (this.mDecorChildCount > 0) {
            final int scrollY2 = this.getScrollY();
            int paddingTop = this.getPaddingTop();
            int paddingBottom = this.getPaddingBottom();
            final int height = this.getHeight();
            int n2;
            int n3;
            for (int childCount = this.getChildCount(), j = 0; j < childCount; ++j, paddingBottom = n3, paddingTop = n2) {
                final View child = this.getChildAt(j);
                final LayoutParams layoutParams = (LayoutParams)child.getLayoutParams();
                if (!layoutParams.isDecor) {
                    n2 = paddingTop;
                    n3 = paddingBottom;
                }
                else {
                    int max = 0;
                    switch (layoutParams.gravity & 0x70) {
                        default: {
                            max = paddingTop;
                            break;
                        }
                        case 48: {
                            max = paddingTop;
                            paddingTop += child.getHeight();
                            break;
                        }
                        case 16: {
                            max = Math.max((height - child.getMeasuredHeight()) / 2, paddingTop);
                            break;
                        }
                        case 80: {
                            max = height - paddingBottom - child.getMeasuredHeight();
                            paddingBottom += child.getMeasuredHeight();
                            break;
                        }
                    }
                    final int n4 = max + scrollY2 - child.getTop();
                    n3 = paddingBottom;
                    n2 = paddingTop;
                    if (n4 != 0) {
                        child.offsetTopAndBottom(n4);
                        n3 = paddingBottom;
                        n2 = paddingTop;
                    }
                }
            }
        }
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrolled(i, n, scrollY);
        }
        if (this.mInternalPageChangeListener != null) {
            this.mInternalPageChangeListener.onPageScrolled(i, n, scrollY);
        }
        if (this.mPageTransformer != null) {
            scrollY = this.getScrollY();
            int childCount2;
            View child2;
            for (childCount2 = this.getChildCount(), i = 0; i < childCount2; ++i) {
                child2 = this.getChildAt(i);
                if (!((LayoutParams)child2.getLayoutParams()).isDecor) {
                    n = (child2.getTop() - scrollY) / this.getClientHeight();
                    this.mPageTransformer.transformPage(child2, n);
                }
            }
        }
        this.mCalledSuper = true;
    }
    
    protected boolean onRequestFocusInDescendants(final int n, final Rect rect) {
        int childCount = this.getChildCount();
        int i;
        int n2;
        if ((n & 0x2) != 0x0) {
            i = 0;
            n2 = 1;
        }
        else {
            i = childCount - 1;
            n2 = -1;
            childCount = -1;
        }
        while (i != childCount) {
            final View child = this.getChildAt(i);
            if (child.getVisibility() == View.VISIBLE) {
                final ItemInfo infoForChild = this.infoForChild(child);
                if (infoForChild != null && infoForChild.position == this.mCurItem && child.requestFocus(n, rect)) {
                    return true;
                }
            }
            i += n2;
        }
        return false;
    }
    
    public void onRestoreInstanceState(final Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        final SavedState savedState = (SavedState)parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (this.mAdapter != null) {
            this.mAdapter.restoreState(savedState.adapterState, savedState.loader);
            this.setCurrentItemInternal(savedState.position, false, true);
            return;
        }
        this.mRestoredCurItem = savedState.position;
        this.mRestoredAdapterState = savedState.adapterState;
        this.mRestoredClassLoader = savedState.loader;
    }
    
    public Parcelable onSaveInstanceState() {
        final SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.position = this.mCurItem;
        if (this.mAdapter == null) {
            return (Parcelable)savedState;
        }
        try {
            savedState.adapterState = this.mAdapter.saveState();
            return (Parcelable)savedState;
        }
        catch (IllegalStateException ex) {
            ex.printStackTrace();
            return (Parcelable)savedState;
        }
    }
    
    protected void onSizeChanged(final int n, final int n2, final int n3, final int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        if (n2 != n4) {
            this.recomputeScrollPosition(n2, n4, this.mPageMargin, this.mPageMargin);
        }
    }
    
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        if (this.mFakeDragging) {
            return true;
        }
        if (motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) {
            return false;
        }
        if (this.mAdapter == null || this.mAdapter.getCount() == 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        final int action = motionEvent.getAction();
        boolean b2;
        final boolean b = b2 = false;
        while (true) {
            switch (action & 0xFF) {
                default: {
                    b2 = b;
                    break;
                }
                case 6: {
                    this.onSecondaryPointerUp(motionEvent);
                    this.mLastMotionY = MotionEventCompat.getY(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId));
                    b2 = b;
                    break;
                }
                case 5: {
                    final int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
                    this.mLastMotionY = MotionEventCompat.getY(motionEvent, actionIndex);
                    this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, actionIndex);
                    b2 = b;
                    break;
                }
                case 0: {
                    this.mScroller.abortAnimation();
                    this.mPopulatePending = false;
                    this.populate();
                    final float x = motionEvent.getX();
                    this.mInitialMotionX = x;
                    this.mLastMotionX = x;
                    final float y = motionEvent.getY();
                    this.mInitialMotionY = y;
                    this.mLastMotionY = y;
                    this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                    b2 = b;
                }
                case 4: {
                    if (b2) {
                        ViewCompat.postInvalidateOnAnimation((View)this);
                    }
                    return true;
                }
                case 2: {
                    if (!this.mIsBeingDragged) {
                        final int pointerIndex = MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId);
                        final float y2 = MotionEventCompat.getY(motionEvent, pointerIndex);
                        final float abs = Math.abs(y2 - this.mLastMotionY);
                        final float x2 = MotionEventCompat.getX(motionEvent, pointerIndex);
                        final float abs2 = Math.abs(x2 - this.mLastMotionX);
                        if (abs > this.mTouchSlop && abs > abs2) {
                            this.requestParentDisallowInterceptTouchEvent(this.mIsBeingDragged = true);
                            float mLastMotionY;
                            if (y2 - this.mInitialMotionY > 0.0f) {
                                mLastMotionY = this.mInitialMotionY + this.mTouchSlop;
                            }
                            else {
                                mLastMotionY = this.mInitialMotionY - this.mTouchSlop;
                            }
                            this.mLastMotionY = mLastMotionY;
                            this.mLastMotionX = x2;
                            this.setScrollState(1);
                            this.setScrollingCacheEnabled(true);
                            final ViewParent parent = this.getParent();
                            if (parent != null) {
                                parent.requestDisallowInterceptTouchEvent(true);
                            }
                        }
                    }
                    b2 = b;
                    if (this.mIsBeingDragged) {
                        b2 = (false | this.performDrag(MotionEventCompat.getY(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId))));
                    }
                    continue;
                }
                case 1: {
                    b2 = b;
                    if (this.mIsBeingDragged) {
                        final VelocityTracker mVelocityTracker = this.mVelocityTracker;
                        mVelocityTracker.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
                        final int n = (int)VelocityTrackerCompat.getYVelocity(mVelocityTracker, this.mActivePointerId);
                        this.mPopulatePending = true;
                        final int clientHeight = this.getClientHeight();
                        final int scrollY = this.getScrollY();
                        final ItemInfo infoForCurrentScrollPosition = this.infoForCurrentScrollPosition();
                        this.setCurrentItemInternal(this.determineTargetPage(infoForCurrentScrollPosition.position, (scrollY / clientHeight - infoForCurrentScrollPosition.offset) / infoForCurrentScrollPosition.heightFactor, n, (int)(MotionEventCompat.getY(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId)) - this.mInitialMotionY)), true, true, n);
                        this.mActivePointerId = -1;
                        this.endDrag();
                        b2 = (this.mTopEdge.onRelease() | this.mBottomEdge.onRelease());
                    }
                    continue;
                }
                case 3: {
                    b2 = b;
                    if (this.mIsBeingDragged) {
                        this.scrollToItem(this.mCurItem, true, 0, false);
                        this.mActivePointerId = -1;
                        this.endDrag();
                        b2 = (this.mTopEdge.onRelease() | this.mBottomEdge.onRelease());
                    }
                    continue;
                }
            }
            break;
        }
        return false;
    }
    
    boolean pageDown() {
        if (this.mAdapter != null && this.mCurItem < this.mAdapter.getCount() - 1) {
            this.setCurrentItem(this.mCurItem + 1, true);
            return true;
        }
        return false;
    }
    
    boolean pageUp() {
        if (this.mCurItem > 0) {
            this.setCurrentItem(this.mCurItem - 1, true);
            return true;
        }
        return false;
    }
    
    void populate() {
        this.populate(this.mCurItem);
    }
    
    void populate(int i) {
        ItemInfo infoForPosition = null;
        int n = 2;
        if (this.mCurItem != i) {
            int n2;
            if (this.mCurItem < i) {
                n2 = 130;
            }
            else {
                n2 = 33;
            }
            infoForPosition = this.infoForPosition(this.mCurItem);
            this.mCurItem = i;
            n = n2;
        }
        if (this.mAdapter == null) {
            this.sortChildDrawingOrder();
        }
        else {
            if (this.mPopulatePending) {
                this.sortChildDrawingOrder();
                return;
            }
            if (this.getWindowToken() != null) {
                this.mAdapter.startUpdate(this);
                i = this.mOffscreenPageLimit;
                final int max = Math.max(0, this.mCurItem - i);
                final int count = this.mAdapter.getCount();
                final int min = Math.min(count - 1, this.mCurItem + i);
                if (count != this.mExpectedAdapterCount) {
                    try {
                        final String s = this.getResources().getResourceName(this.getId());
                        throw new IllegalStateException("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.mExpectedAdapterCount + ", found: " + count + " Pager id: " + s + " Pager class: " + this.getClass() + " Problematic adapter: " + this.mAdapter.getClass());
                    }
                    catch (Resources.NotFoundException ex) {
                        final String s = Integer.toHexString(this.getId());
                        throw new IllegalStateException("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.mExpectedAdapterCount + ", found: " + count + " Pager id: " + s + " Pager class: " + this.getClass() + " Problematic adapter: " + this.mAdapter.getClass());
                    }
                }
                final ItemInfo itemInfo = null;
                i = 0;
                ItemInfo itemInfo2;
                while (true) {
                    itemInfo2 = itemInfo;
                    if (i >= this.mItems.size()) {
                        break;
                    }
                    final ItemInfo itemInfo3 = this.mItems.get(i);
                    if (itemInfo3.position >= this.mCurItem) {
                        itemInfo2 = itemInfo;
                        if (itemInfo3.position == this.mCurItem) {
                            itemInfo2 = itemInfo3;
                            break;
                        }
                        break;
                    }
                    else {
                        ++i;
                    }
                }
                ItemInfo addNewItem;
                if ((addNewItem = itemInfo2) == null) {
                    addNewItem = itemInfo2;
                    if (count > 0) {
                        addNewItem = this.addNewItem(this.mCurItem, i);
                    }
                }
                if (addNewItem != null) {
                    float n3 = 0.0f;
                    int n4 = i - 1;
                    ItemInfo itemInfo4;
                    if (n4 >= 0) {
                        itemInfo4 = this.mItems.get(n4);
                    }
                    else {
                        itemInfo4 = null;
                    }
                    final int clientHeight = this.getClientHeight();
                    float n5;
                    if (clientHeight <= 0) {
                        n5 = 0.0f;
                    }
                    else {
                        n5 = 2.0f - addNewItem.heightFactor + this.getPaddingLeft() / clientHeight;
                    }
                    int j = this.mCurItem - 1;
                    ItemInfo itemInfo5 = itemInfo4;
                    int n6 = i;
                    while (j >= 0) {
                        float n7;
                        ItemInfo itemInfo6;
                        int n8;
                        if (n3 >= n5 && j < max) {
                            if (itemInfo5 == null) {
                                break;
                            }
                            i = n6;
                            n7 = n3;
                            itemInfo6 = itemInfo5;
                            n8 = n4;
                            if (j == itemInfo5.position) {
                                i = n6;
                                n7 = n3;
                                itemInfo6 = itemInfo5;
                                n8 = n4;
                                if (!itemInfo5.scrolling) {
                                    this.mItems.remove(n4);
                                    this.mAdapter.destroyItem(this, j, itemInfo5.object);
                                    n8 = n4 - 1;
                                    i = n6 - 1;
                                    if (n8 >= 0) {
                                        itemInfo6 = this.mItems.get(n8);
                                        n7 = n3;
                                    }
                                    else {
                                        itemInfo6 = null;
                                        n7 = n3;
                                    }
                                }
                            }
                        }
                        else if (itemInfo5 != null && j == itemInfo5.position) {
                            n7 = n3 + itemInfo5.heightFactor;
                            n8 = n4 - 1;
                            if (n8 >= 0) {
                                itemInfo6 = this.mItems.get(n8);
                            }
                            else {
                                itemInfo6 = null;
                            }
                            i = n6;
                        }
                        else {
                            n7 = n3 + this.addNewItem(j, n4 + 1).heightFactor;
                            i = n6 + 1;
                            if (n4 >= 0) {
                                itemInfo6 = this.mItems.get(n4);
                            }
                            else {
                                itemInfo6 = null;
                            }
                            n8 = n4;
                        }
                        --j;
                        n6 = i;
                        n3 = n7;
                        itemInfo5 = itemInfo6;
                        n4 = n8;
                    }
                    float heightFactor = addNewItem.heightFactor;
                    int n9 = n6 + 1;
                    if (heightFactor < 2.0f) {
                        ItemInfo itemInfo7;
                        if (n9 < this.mItems.size()) {
                            itemInfo7 = this.mItems.get(n9);
                        }
                        else {
                            itemInfo7 = null;
                        }
                        float n10;
                        if (clientHeight <= 0) {
                            n10 = 0.0f;
                        }
                        else {
                            n10 = this.getPaddingRight() / clientHeight + 2.0f;
                        }
                        int k = this.mCurItem + 1;
                        ItemInfo itemInfo8 = itemInfo7;
                        while (k < count) {
                            float n11;
                            ItemInfo itemInfo9;
                            if (heightFactor >= n10 && k > min) {
                                if (itemInfo8 == null) {
                                    break;
                                }
                                n11 = heightFactor;
                                itemInfo9 = itemInfo8;
                                i = n9;
                                if (k == itemInfo8.position) {
                                    n11 = heightFactor;
                                    itemInfo9 = itemInfo8;
                                    i = n9;
                                    if (!itemInfo8.scrolling) {
                                        this.mItems.remove(n9);
                                        this.mAdapter.destroyItem(this, k, itemInfo8.object);
                                        if (n9 < this.mItems.size()) {
                                            itemInfo9 = this.mItems.get(n9);
                                            i = n9;
                                            n11 = heightFactor;
                                        }
                                        else {
                                            itemInfo9 = null;
                                            n11 = heightFactor;
                                            i = n9;
                                        }
                                    }
                                }
                            }
                            else if (itemInfo8 != null && k == itemInfo8.position) {
                                n11 = heightFactor + itemInfo8.heightFactor;
                                i = n9 + 1;
                                if (i < this.mItems.size()) {
                                    itemInfo9 = this.mItems.get(i);
                                }
                                else {
                                    itemInfo9 = null;
                                }
                            }
                            else {
                                final ItemInfo addNewItem2 = this.addNewItem(k, n9);
                                i = n9 + 1;
                                n11 = heightFactor + addNewItem2.heightFactor;
                                if (i < this.mItems.size()) {
                                    itemInfo9 = this.mItems.get(i);
                                }
                                else {
                                    itemInfo9 = null;
                                }
                            }
                            ++k;
                            heightFactor = n11;
                            itemInfo8 = itemInfo9;
                            n9 = i;
                        }
                    }
                    this.calculatePageOffsets(addNewItem, n6, infoForPosition);
                }
                final PagerAdapter mAdapter = this.mAdapter;
                i = this.mCurItem;
                Object object;
                if (addNewItem != null) {
                    object = addNewItem.object;
                }
                else {
                    object = null;
                }
                mAdapter.setPrimaryItem(this, i, object);
                this.mAdapter.finishUpdate(this);
                int childCount;
                View child;
                LayoutParams layoutParams;
                ItemInfo infoForChild;
                for (childCount = this.getChildCount(), i = 0; i < childCount; ++i) {
                    child = this.getChildAt(i);
                    layoutParams = (LayoutParams)child.getLayoutParams();
                    layoutParams.childIndex = i;
                    if (!layoutParams.isDecor && layoutParams.heightFactor == 0.0f) {
                        infoForChild = this.infoForChild(child);
                        if (infoForChild != null) {
                            layoutParams.heightFactor = infoForChild.heightFactor;
                            layoutParams.position = infoForChild.position;
                        }
                    }
                }
                this.sortChildDrawingOrder();
                if (this.hasFocus()) {
                    final View focus = this.findFocus();
                    ItemInfo infoForAnyChild;
                    if (focus != null) {
                        infoForAnyChild = this.infoForAnyChild(focus);
                    }
                    else {
                        infoForAnyChild = null;
                    }
                    if (infoForAnyChild == null || infoForAnyChild.position != this.mCurItem) {
                        View child2;
                        ItemInfo infoForChild2;
                        for (i = 0; i < this.getChildCount(); ++i) {
                            child2 = this.getChildAt(i);
                            infoForChild2 = this.infoForChild(child2);
                            if (infoForChild2 != null && infoForChild2.position == this.mCurItem && child2.requestFocus(n)) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void removeView(final View view) {
        if (this.mInLayout) {
            this.removeViewInLayout(view);
            return;
        }
        super.removeView(view);
    }
    
    public void setAdapter(final PagerAdapter mAdapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver);
            this.mAdapter.startUpdate(this);
            for (int i = 0; i < this.mItems.size(); ++i) {
                final ItemInfo itemInfo = this.mItems.get(i);
                this.mAdapter.destroyItem(this, itemInfo.position, itemInfo.object);
            }
            this.mAdapter.finishUpdate(this);
            this.mItems.clear();
            this.removeNonDecorViews();
            this.scrollTo(this.mCurItem = 0, 0);
        }
        final PagerAdapter mAdapter2 = this.mAdapter;
        this.mAdapter = mAdapter;
        this.mExpectedAdapterCount = 0;
        if (this.mAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new PagerObserver();
            }
            this.mAdapter.registerDataSetObserver(this.mObserver);
            this.mPopulatePending = false;
            final boolean mFirstLayout = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
                this.setCurrentItemInternal(this.mRestoredCurItem, false, true);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
                this.mRestoredClassLoader = null;
            }
            else if (!mFirstLayout) {
                this.populate();
            }
            else {
                this.requestLayout();
            }
        }
        if (this.mAdapterChangeListener != null && mAdapter2 != mAdapter) {
            this.mAdapterChangeListener.onAdapterChanged(mAdapter2, mAdapter);
        }
    }
    
    void setChildrenDrawingOrderEnabledCompat(final boolean p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: bipush          7
        //     5: if_icmplt       57
        //     8: aload_0        
        //     9: getfield        com/dravite/newlayouttest/views/VerticalViewPager.mSetChildrenDrawingOrderEnabled:Ljava/lang/reflect/Method;
        //    12: ifnonnull       37
        //    15: aload_0        
        //    16: ldc             Landroid/view/ViewGroup;.class
        //    18: ldc_w           "setChildrenDrawingOrderEnabled"
        //    21: iconst_1       
        //    22: anewarray       Ljava/lang/Class;
        //    25: dup            
        //    26: iconst_0       
        //    27: getstatic       java/lang/Boolean.TYPE:Ljava/lang/Class;
        //    30: aastore        
        //    31: invokevirtual   java/lang/Class.getDeclaredMethod:(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
        //    34: putfield        com/dravite/newlayouttest/views/VerticalViewPager.mSetChildrenDrawingOrderEnabled:Ljava/lang/reflect/Method;
        //    37: aload_0        
        //    38: getfield        com/dravite/newlayouttest/views/VerticalViewPager.mSetChildrenDrawingOrderEnabled:Ljava/lang/reflect/Method;
        //    41: aload_0        
        //    42: iconst_1       
        //    43: anewarray       Ljava/lang/Object;
        //    46: dup            
        //    47: iconst_0       
        //    48: iload_1        
        //    49: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    52: aastore        
        //    53: invokevirtual   java/lang/reflect/Method.invoke:(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
        //    56: pop            
        //    57: return         
        //    58: astore_2       
        //    59: ldc             "ViewPager"
        //    61: ldc_w           "Can't find setChildrenDrawingOrderEnabled"
        //    64: aload_2        
        //    65: invokestatic    android/util/Log.e:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
        //    68: pop            
        //    69: goto            37
        //    72: astore_2       
        //    73: ldc             "ViewPager"
        //    75: ldc_w           "Error changing children drawing order"
        //    78: aload_2        
        //    79: invokestatic    android/util/Log.e:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
        //    82: pop            
        //    83: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                             
        //  -----  -----  -----  -----  ---------------------------------
        //  15     37     58     72     Ljava/lang/NoSuchMethodException;
        //  37     57     72     84     Ljava/lang/Exception;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0037:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2592)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:138)
        // 
        //throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void setCurrentItem(final int n) {
        this.mPopulatePending = false;
        this.setCurrentItemInternal(n, !this.mFirstLayout, false);
    }
    
    public void setCurrentItem(final int n, final boolean b) {
        this.setCurrentItemInternal(n, b, this.mPopulatePending = false);
    }
    
    void setCurrentItemInternal(final int n, final boolean b, final boolean b2) {
        this.setCurrentItemInternal(n, b, b2, 800);
    }
    
    void setCurrentItemInternal(int i, final boolean b, final boolean b2, final int n) {
        final boolean b3 = true;
        if (this.mAdapter == null || this.mAdapter.getCount() <= 0) {
            this.setScrollingCacheEnabled(false);
            return;
        }
        if (!b2 && this.mCurItem == i && this.mItems.size() != 0) {
            this.setScrollingCacheEnabled(false);
            return;
        }
        int mCurItem;
        if (i < 0) {
            mCurItem = 0;
        }
        else if ((mCurItem = i) >= this.mAdapter.getCount()) {
            mCurItem = this.mAdapter.getCount() - 1;
        }
        i = this.mOffscreenPageLimit;
        if (mCurItem > this.mCurItem + i || mCurItem < this.mCurItem - i) {
            for (i = 0; i < this.mItems.size(); ++i) {
                this.mItems.get(i).scrolling = true;
            }
        }
        final boolean b4 = this.mCurItem != mCurItem && b3;
        if (this.mFirstLayout) {
            this.mCurItem = mCurItem;
            if (b4 && this.mOnPageChangeListener != null) {
                this.mOnPageChangeListener.onPageSelected(mCurItem);
            }
            if (b4 && this.mInternalPageChangeListener != null) {
                this.mInternalPageChangeListener.onPageSelected(mCurItem);
            }
            this.requestLayout();
            return;
        }
        this.populate(mCurItem);
        this.scrollToItem(mCurItem, b, n, b4);
    }
    
    ViewPager.OnPageChangeListener setInternalPageChangeListener(final ViewPager.OnPageChangeListener mInternalPageChangeListener) {
        final ViewPager.OnPageChangeListener mInternalPageChangeListener2 = this.mInternalPageChangeListener;
        this.mInternalPageChangeListener = mInternalPageChangeListener;
        return mInternalPageChangeListener2;
    }
    
    public void setOffscreenPageLimit(final int n) {
        int mOffscreenPageLimit = n;
        if (n < 1) {
            Log.w("ViewPager", "Requested offscreen page limit " + n + " too small; defaulting to " + 1);
            mOffscreenPageLimit = 1;
        }
        if (mOffscreenPageLimit != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = mOffscreenPageLimit;
            this.populate();
        }
    }
    
    void setOnAdapterChangeListener(final OnAdapterChangeListener mAdapterChangeListener) {
        this.mAdapterChangeListener = mAdapterChangeListener;
    }
    
    public void setOnPageChangeListener(final ViewPager.OnPageChangeListener mOnPageChangeListener) {
        this.mOnPageChangeListener = mOnPageChangeListener;
    }
    
    public void setPageMargin(final int mPageMargin) {
        final int mPageMargin2 = this.mPageMargin;
        this.mPageMargin = mPageMargin;
        final int height = this.getHeight();
        this.recomputeScrollPosition(height, height, mPageMargin, mPageMargin2);
        this.requestLayout();
    }
    
    public void setPageMarginDrawable(final int n) {
        this.setPageMarginDrawable(this.getContext().getResources().getDrawable(n));
    }
    
    public void setPageMarginDrawable(final Drawable mMarginDrawable) {
        this.mMarginDrawable = mMarginDrawable;
        if (mMarginDrawable != null) {
            this.refreshDrawableState();
        }
        this.setWillNotDraw(mMarginDrawable == null);
        this.invalidate();
    }
    
    public void setPageTransformer(final boolean b, final ViewPager.PageTransformer mPageTransformer) {
        int mDrawingOrder = 1;
        if (Build.VERSION.SDK_INT >= 11) {
            final boolean childrenDrawingOrderEnabledCompat = mPageTransformer != null;
            int n;
            if (childrenDrawingOrderEnabledCompat != (this.mPageTransformer != null)) {
                n = 1;
            }
            else {
                n = 0;
            }
            this.mPageTransformer = mPageTransformer;
            this.setChildrenDrawingOrderEnabledCompat(childrenDrawingOrderEnabledCompat);
            if (childrenDrawingOrderEnabledCompat) {
                if (b) {
                    mDrawingOrder = 2;
                }
                this.mDrawingOrder = mDrawingOrder;
            }
            else {
                this.mDrawingOrder = 0;
            }
            if (n != 0) {
                this.populate();
            }
        }
    }
    
    void smoothScrollTo(final int n, final int n2) {
        this.smoothScrollTo(n, n2, 0);
    }
    
    void smoothScrollTo(int n, int n2, int abs) {
        if (this.getChildCount() == 0) {
            this.setScrollingCacheEnabled(false);
            return;
        }
        final int scrollX = this.getScrollX();
        final int scrollY = this.getScrollY();
        final int n3 = n - scrollX;
        n2 -= scrollY;
        if (n3 == 0 && n2 == 0) {
            this.completeScroll(false);
            this.populate();
            this.setScrollState(0);
            return;
        }
        this.setScrollingCacheEnabled(true);
        this.setScrollState(2);
        n = this.getClientHeight();
        final int n4 = n / 2;
        final float min = Math.min(1.0f, 1.0f * Math.abs(n3) / n);
        final float n5 = n4;
        final float n6 = n4;
        final float distanceInfluenceForSnapDuration = this.distanceInfluenceForSnapDuration(min);
        abs = Math.abs(abs);
        if (abs > 0) {
            n = Math.round(1000.0f * Math.abs((n5 + n6 * distanceInfluenceForSnapDuration) / abs)) * 4;
        }
        else {
            n = (int)((1.0f + Math.abs(n3) / (this.mPageMargin + n * this.mAdapter.getPageWidth(this.mCurItem))) * 100.0f);
        }
        n = Math.min(n, 400);
        this.mScroller.startScroll(scrollX, scrollY, n3, n2, n);
        ViewCompat.postInvalidateOnAnimation((View)this);
    }
    
    protected boolean verifyDrawable(final Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mMarginDrawable;
    }
    
    interface Decor
    {
    }
    
    static class ItemInfo
    {
        float heightFactor;
        Object object;
        float offset;
        int position;
        boolean scrolling;
    }
    
    public static class LayoutParams extends ViewGroup.LayoutParams
    {
        int childIndex;
        public int gravity;
        float heightFactor;
        public boolean isDecor;
        boolean needsMeasure;
        int position;
        
        public LayoutParams() {
            super(-1, -1);
            this.heightFactor = 0.0f;
        }
        
        public LayoutParams(final Context context, final AttributeSet set) {
            super(context, set);
            this.heightFactor = 0.0f;
            final TypedArray obtainStyledAttributes = context.obtainStyledAttributes(set, VerticalViewPager.LAYOUT_ATTRS);
            this.gravity = obtainStyledAttributes.getInteger(0, 48);
            obtainStyledAttributes.recycle();
        }
    }
    
    class MyAccessibilityDelegate extends AccessibilityDelegateCompat
    {
        private boolean canScroll() {
            return VerticalViewPager.this.mAdapter != null && VerticalViewPager.this.mAdapter.getCount() > 1;
        }
        
        @Override
        public void onInitializeAccessibilityEvent(final View view, final AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName((CharSequence)ViewPager.class.getName());
            final AccessibilityRecordCompat obtain = AccessibilityRecordCompat.obtain();
            obtain.setScrollable(this.canScroll());
            if (accessibilityEvent.getEventType() == 4096 && VerticalViewPager.this.mAdapter != null) {
                obtain.setItemCount(VerticalViewPager.this.mAdapter.getCount());
                obtain.setFromIndex(VerticalViewPager.this.mCurItem);
                obtain.setToIndex(VerticalViewPager.this.mCurItem);
            }
        }
        
        @Override
        public void onInitializeAccessibilityNodeInfo(final View view, final AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClassName(ViewPager.class.getName());
            accessibilityNodeInfoCompat.setScrollable(this.canScroll());
            if (VerticalViewPager.this.internalCanScrollVertically(1)) {
                accessibilityNodeInfoCompat.addAction(4096);
            }
            if (VerticalViewPager.this.internalCanScrollVertically(-1)) {
                accessibilityNodeInfoCompat.addAction(8192);
            }
        }
        
        @Override
        public boolean performAccessibilityAction(final View view, final int n, final Bundle bundle) {
            if (super.performAccessibilityAction(view, n, bundle)) {
                return true;
            }
            switch (n) {
                default: {
                    return false;
                }
                case 4096: {
                    if (VerticalViewPager.this.internalCanScrollVertically(1)) {
                        VerticalViewPager.this.setCurrentItem(VerticalViewPager.this.mCurItem + 1);
                        return true;
                    }
                    return false;
                }
                case 8192: {
                    if (VerticalViewPager.this.internalCanScrollVertically(-1)) {
                        VerticalViewPager.this.setCurrentItem(VerticalViewPager.this.mCurItem - 1);
                        return true;
                    }
                    return false;
                }
            }
        }
    }
    
    interface OnAdapterChangeListener
    {
        void onAdapterChanged(final PagerAdapter p0, final PagerAdapter p1);
    }
    
    private class PagerObserver extends DataSetObserver
    {
        public void onChanged() {
            VerticalViewPager.this.dataSetChanged();
        }
        
        public void onInvalidated() {
            VerticalViewPager.this.dataSetChanged();
        }
    }
    
    public static class SavedState extends View.BaseSavedState
    {
        public static final Parcelable.Creator<SavedState> CREATOR;
        Parcelable adapterState;
        ClassLoader loader;
        int position;
        
        static {
            CREATOR = ParcelableCompat.newCreator((ParcelableCompatCreatorCallbacks<SavedState>)new ParcelableCompatCreatorCallbacks<SavedState>() {
                @Override
                public SavedState createFromParcel(final Parcel parcel, final ClassLoader classLoader) {
                    return new SavedState(parcel, classLoader);
                }
                
                @Override
                public SavedState[] newArray(final int n) {
                    return new SavedState[n];
                }
            });
        }
        
        SavedState(final Parcel parcel, final ClassLoader classLoader) {
            super(parcel);
            ClassLoader classLoader2 = classLoader;
            if (classLoader == null) {
                classLoader2 = this.getClass().getClassLoader();
            }
            this.position = parcel.readInt();
            this.adapterState = parcel.readParcelable(classLoader2);
            this.loader = classLoader2;
        }
        
        public SavedState(final Parcelable parcelable) {
            super(parcelable);
        }
        
        public String toString() {
            return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.position + "}";
        }
        
        public void writeToParcel(final Parcel parcel, final int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.position);
            parcel.writeParcelable(this.adapterState, n);
        }
    }
    
    static class ViewPositionComparator implements Comparator<View>
    {
        @Override
        public int compare(final View view, final View view2) {
            final LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
            final LayoutParams layoutParams2 = (LayoutParams)view2.getLayoutParams();
            if (layoutParams.isDecor == layoutParams2.isDecor) {
                return layoutParams.position - layoutParams2.position;
            }
            if (layoutParams.isDecor) {
                return 1;
            }
            return -1;
        }
    }
}
