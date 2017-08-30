// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views;

import android.os.Parcelable;
import com.vanbo.homeux.dravite.newlayouttest.add_quick_action.AddQuickActionActivity;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.DrawerObject;
import android.view.View.OnClickListener;
import android.support.design.widget.Snackbar;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import android.view.MotionEvent;
import android.view.View.OnDragListener;
import android.widget.FrameLayout;
import android.graphics.Rect;
import com.vanbo.homeux.dravite.newlayouttest.LauncherLog;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.JsonHelper;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.Application;
import android.view.ViewGroup;
import android.content.ComponentName;
import android.content.Intent;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.QuickAction;
import android.util.AttributeSet;
import android.content.Context;
import android.os.Handler;
import android.graphics.PointF;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import com.dravite.homeux.R;


public class QuickAppBar extends ViewGroup implements View.OnLongClickListener, View.OnTouchListener
{
    public static final int REQUEST_ADD_QA = 378;
    public static final int REQUEST_EDIT_QA = 379;
    private DragSurfaceLayout mDragSurfaceLayout;
    public boolean mHasChanged;
    public int mHoverIndex;
    private boolean mIsDragging;
    public boolean mIsHovering;
    public View mQaDragView;
    public int mRealChildCount;
    public int mStartDragIndex;
    public PointF mTouchPosition;
    public Handler moveHandler;
    
    public QuickAppBar(final Context context) {
        this(context, null);
    }
    
    public QuickAppBar(final Context context, final AttributeSet set) {
        this(context, set, 0);
    }
    
    public QuickAppBar(final Context context, final AttributeSet set, final int n) {
        this(context, set, n, 0);
    }
    
    public QuickAppBar(final Context context, final AttributeSet set, final int n, final int n2) {
        super(context, set, n, n2);
        this.mTouchPosition = new PointF(0.0f, 0.0f);
        this.mIsDragging = false;
        this.mHoverIndex = -1;
        this.mIsHovering = false;
        this.mHasChanged = false;
        this.moveHandler = new Handler();
        this.setBackgroundColor(0);
        this.setClipChildren(false);
        this.setClipToPadding(false);
    }
    
    public boolean add(final QuickAction quickAction) {
        if (this.getChildCount() >= 5 || quickAction.qaIndex < 0 || quickAction.qaIndex > 4) {
            return false;
        }
        final QuickAppIcon quickAppIcon = new QuickAppIcon(this.getContext());
        quickAppIcon.setIconRes(this.getResources().getIdentifier(quickAction.iconRes, "drawable", this.getContext().getPackageName()));
        final Intent tag = new Intent();
        tag.setComponent(new ComponentName(quickAction.intentPackage, quickAction.intentClass));
        quickAppIcon.setTag(tag);
        quickAppIcon.setBackground(this.getContext().getDrawable(R.drawable.ripple));
        quickAppIcon.setOnLongClickListener((View.OnLongClickListener)this);
        quickAppIcon.setOnTouchListener((View.OnTouchListener)this);
        this.addView((View)quickAppIcon, (ViewGroup.LayoutParams)new QALayoutParams(0, 0, quickAction.qaIndex));
        ++this.mRealChildCount;
        return true;
    }
    
    public boolean addAnimated(final int iconRes, final Application application, final int n) {
        if (this.getChildCount() >= 5 || n < 0 || n > 4) {
            return false;
        }
        final QuickAppIcon quickAppIcon = new QuickAppIcon(this.getContext());
        quickAppIcon.setScaleX(0.0f);
        quickAppIcon.setScaleY(0.0f);
        quickAppIcon.setIconRes(iconRes);
        final Intent tag = new Intent();
        tag.setComponent(new ComponentName(application.packageName, application.className));
        quickAppIcon.setTag(tag);
        quickAppIcon.setBackground(this.getContext().getDrawable(R.drawable.ripple));
        quickAppIcon.setOnLongClickListener((View.OnLongClickListener)this);
        this.addView((View)quickAppIcon, (ViewGroup.LayoutParams)new QALayoutParams(45, 45, n));
        ++this.mRealChildCount;
        quickAppIcon.post((Runnable)new Runnable() {
            @Override
            public void run() {
                quickAppIcon.animate().scaleX(1.0f).scaleY(1.0f).setDuration(150L);
            }
        });
        JsonHelper.saveQuickApps(this.getContext(), this);
        this.endDrag();
        return true;
    }
    
    public boolean addAnimated(final View view, final int n) {
        if (this.getChildCount() >= 5 || n < 0 || n > 4) {
            return false;
        }
        this.addView(view, (ViewGroup.LayoutParams)new QALayoutParams(555, 555, n));
        view.setX(0.0f);
        view.setY(0.0f);
        ++this.mRealChildCount;
        view.post((Runnable)new Runnable() {
            @Override
            public void run() {
                view.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(150L);
            }
        });
        JsonHelper.saveQuickApps(this.getContext(), this);
        return true;
    }
    
    public void endDrag() {
        this.mIsDragging = false;
        this.setBackgroundColor(0);
    }
    
    public void freeSpace(final int n) {
        if (this.getChildCount() < 5) {
            final View childAtPosition = this.getChildAtPosition(n);
            if (childAtPosition != null) {
                this.moveViewToNextFree(childAtPosition);
            }
        }
    }
    
    public int[] getAbsolutePositionCoords(int n) {
        n = (this.getWidth() - LauncherUtils.dpToPx(88.0f, this.getContext())) * n / 5;
        final int height = this.getHeight();
        final int dpToPx = LauncherUtils.dpToPx(48.0f, this.getContext());
        final int[] array = new int[2];
        this.getLocationInWindow(array);
        return new int[] { array[0] + n, array[1] + (height - dpToPx) };
    }
    
    public View getChildAtPosition(final int n) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            if (((QALayoutParams)this.getChildAt(i).getLayoutParams()).position == n) {
                return this.getChildAt(i);
            }
        }
        return null;
    }
    
    public int getNextFreePos(final int n) {
        int n2 = Integer.MAX_VALUE;
        int n3 = -1;
        int abs;
        int n4;
        for (int i = 0; i < 5; ++i, n2 = abs, n3 = n4) {
            abs = n2;
            n4 = n3;
            if (this.getChildAtPosition(i) == null) {
                abs = n2;
                n4 = n3;
                if (n2 > Math.abs(i - n)) {
                    abs = Math.abs(i - n);
                    n4 = i;
                }
            }
        }
        return n3;
    }
    
    public int getRealChildCount() {
        return this.mRealChildCount;
    }
    
    public void hoverAt(final float n, final float n2) {
        int n3 = 0;
        final int[] array = new int[2];
        this.getLocationInWindow(array);
        final int n4 = array[1] + LauncherUtils.dpToPx(80.0f, this.getContext());
        if (n2 < n4 && n2 > n4 - LauncherUtils.dpToPx(48.0f, this.getContext()) && this.getRealChildCount() < 5) {
            this.setBackgroundColor(587202559);
            final int mHoverIndex = -(array[0] - (int)n) / ((this.getWidth() - LauncherUtils.dpToPx(88.0f, this.getContext())) / 5);
            if (mHoverIndex != this.mHoverIndex && mHoverIndex >= 0 && mHoverIndex <= 4) {
                this.moveHandler.removeCallbacksAndMessages((Object)null);
                this.redoFreeSpace();
                this.mHoverIndex = mHoverIndex;
                if (this.getChildAtPosition(mHoverIndex) != null) {
                    n3 = 300;
                }
                this.moveHandler.postDelayed((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        QuickAppBar.this.freeSpace(mHoverIndex);
                        QuickAppBar.this.mIsHovering = true;
                        QuickAppBar.this.mHasChanged = true;
                    }
                }, (long)n3);
            }
            else if (mHoverIndex >= 0 && mHoverIndex > 4) {
                return;
            }
            return;
        }
        if (this.mIsHovering) {
            this.redoFreeSpace();
        }
        LauncherLog.d("QuickActionBar", "Outside hovering.");
        this.mIsHovering = false;
        this.setBackgroundColor(16777215);
        this.mHoverIndex = -1;
    }
    
    public void moveViewToNextFree(final View view) {
        final int position = ((QALayoutParams)view.getLayoutParams()).position;
        view.animate().translationX((float)((this.getNextFreePos(position) - position) * ((this.getWidth() - LauncherUtils.dpToPx(88.0f, this.getContext())) / 5))).withEndAction((Runnable)new Runnable() {
            @Override
            public void run() {
            }
        });
    }
    
    protected void onLayout(final boolean b, int i, int n, int position, int n2) {
        View child;
        QALayoutParams qaLayoutParams;
        int height;
        for (i = 0; i < this.getChildCount(); ++i) {
            child = this.getChildAt(i);
            qaLayoutParams = (QALayoutParams)child.getLayoutParams();
            position = qaLayoutParams.position;
            n = (this.getWidth() - LauncherUtils.dpToPx(88.0f, this.getContext())) * position / 5;
            position = (position + 1) * (this.getWidth() - LauncherUtils.dpToPx(88.0f, this.getContext())) / 5;
            n2 = this.getHeight() - LauncherUtils.dpToPx(48.0f, this.getContext());
            height = this.getHeight();
            qaLayoutParams.top = n2;
            qaLayoutParams.left = n;
            child.layout(n, n2, position, height);
        }
    }
    
    public boolean onLongClick(final View mQaDragView) {
        if (this.mDragSurfaceLayout != null) {
            this.mIsDragging = true;
            mQaDragView.measure(0, 0);
            final Rect rect = new Rect();
            mQaDragView.getHitRect(rect);
            final QALayoutParams qaLayoutParams = (QALayoutParams)mQaDragView.getLayoutParams();
            final int[] absolutePositionCoords = this.getAbsolutePositionCoords(qaLayoutParams.position);
            this.mStartDragIndex = qaLayoutParams.position;
            final PointF mTouchPosition = this.mTouchPosition;
            mTouchPosition.x -= rect.width() / 2;
            final PointF mTouchPosition2 = this.mTouchPosition;
            mTouchPosition2.y -= rect.height() / 2;
            final FrameLayout.LayoutParams frameLayoutLayoutParams = new FrameLayout.LayoutParams(rect.width(), rect.height());
            this.removeView(mQaDragView);
            --this.mRealChildCount;
            this.mQaDragView = mQaDragView;
            this.mDragSurfaceLayout.addView(this.mQaDragView, (ViewGroup.LayoutParams)frameLayoutLayoutParams);
            this.mQaDragView.setX((float)absolutePositionCoords[0]);
            this.mQaDragView.setY((float)absolutePositionCoords[1]);
            this.mQaDragView.setScaleY(1.2f);
            this.mQaDragView.setScaleX(1.2f);
            this.mQaDragView.setAlpha(0.0f);
            this.mDragSurfaceLayout.setOnDragListener((View.OnDragListener)this.mDragSurfaceLayout);
            this.mDragSurfaceLayout.startDrag(1);
        }
        return true;
    }
    
    protected void onMeasure(final int n, final int n2) {
        super.onMeasure(n, n2);
        this.measureChildren(n, n2);
    }
    
    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        if (!this.mIsDragging) {
            this.mTouchPosition.x = motionEvent.getX();
            this.mTouchPosition.y = motionEvent.getY();
        }
        return false;
    }
    
    public void redoFreeSpace() {
        for (int i = 0; i < this.getChildCount(); ++i) {
            this.getChildAt(i).animate().translationX(0.0f).setDuration(150L);
        }
    }
    
    public void replaceIconDelayed(final int n, final int n2, final int n3) {
        new Handler().postDelayed((Runnable)new Runnable() {
            @Override
            public void run() {
                final QuickAppIcon quickAppIcon = (QuickAppIcon)QuickAppBar.this.getChildAtPosition(n2);
                quickAppIcon.animate().scaleX(0.0f).scaleY(0.0f).setDuration(150L).withEndAction((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        quickAppIcon.setIconRes(n);
                        quickAppIcon.animate().scaleX(1.0f).scaleY(1.0f).setDuration(150L);
                        JsonHelper.saveQuickApps(QuickAppBar.this.getContext(), QuickAppBar.this);
                    }
                });
            }
        }, (long)n3);
    }
    
    public void setDragSurfaceLayout(final DragSurfaceLayout mDragSurfaceLayout) {
        this.mDragSurfaceLayout = mDragSurfaceLayout;
    }
    
    public void stopHovering(final View view) {
        this.moveHandler.removeCallbacksAndMessages((Object)null);
        this.mHasChanged = false;
        if (this.mHoverIndex == -1) {
            this.redoFreeSpace();
            Snackbar.make((View)((LauncherActivity)this.getContext()).mCoordinatorLayout, "QuickApp removed.", -1).setAction("Undo", (View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    QuickAppBar.this.addAnimated(view, QuickAppBar.this.mStartDragIndex);
                }
            }).show();
            return;
        }
        if (this.mIsHovering && this.getChildCount() < 5) {
            this.mIsHovering = false;
            final View childAtPosition = this.getChildAtPosition(this.mHoverIndex);
            if (childAtPosition != null) {
                final QALayoutParams layoutParams = (QALayoutParams)childAtPosition.getLayoutParams();
                layoutParams.position = this.getNextFreePos(layoutParams.position);
                childAtPosition.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
                childAtPosition.setTranslationX(0.0f);
            }
            this.addAnimated(view, this.mHoverIndex);
            this.mHoverIndex = -1;
        }
        JsonHelper.saveQuickApps(this.getContext(), this);
    }
    
    public boolean stopHovering(final DrawerObject drawerObject) {
        if (drawerObject instanceof Application && this.mIsHovering && this.getChildCount() < 5) {
            this.mIsHovering = false;
            final View childAtPosition = this.getChildAtPosition(this.mHoverIndex);
            if (childAtPosition != null) {
                final QALayoutParams layoutParams = (QALayoutParams)childAtPosition.getLayoutParams();
                layoutParams.position = this.getNextFreePos(layoutParams.position);
                childAtPosition.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
                childAtPosition.setTranslationX(0.0f);
            }
            final Intent intent = new Intent(this.getContext(), (Class)AddQuickActionActivity.class);
            intent.putExtra("data", (Parcelable)drawerObject);
            intent.putExtra("index", this.mHoverIndex);
            new Handler().postDelayed((Runnable)new Runnable() {
                @Override
                public void run() {
                    QuickAppBar.this.endDrag();
                    ((LauncherActivity)QuickAppBar.this.getContext()).startActivityForResult(intent, 378);
                }
            }, 240L);
            this.mHoverIndex = -1;
            return true;
        }
        return false;
    }
    
    public static class QALayoutParams extends ViewGroup.LayoutParams
    {
        public float left;
        public int position;
        public float top;
        
        public QALayoutParams(final int n, final int n2, final int position) {
            super(n, n2);
            this.position = position;
        }
    }
}
