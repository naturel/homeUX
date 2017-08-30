// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views;

import android.support.v7.widget.RecyclerView;
import android.view.View.DragShadowBuilder;
import android.content.ClipData;
import android.content.Intent;
import com.vanbo.homeux.dravite.newlayouttest.add_quick_action.AddQuickActionActivity;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.FolderPagerAdapter;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.DrawerObject;
import android.graphics.PointF;
import android.util.TypedValue;
import android.view.ViewGroup;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.ClickableAppWidgetHostView;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import android.view.animation.AccelerateInterpolator;
import com.vanbo.homeux.dravite.newlayouttest.LauncherLog;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.JsonHelper;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.Application;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.AppDrawerPagerAdapter;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.FolderStructure;
import android.view.DragEvent;
import android.graphics.Bitmap;
import android.animation.TimeInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.view.View;
import android.util.AttributeSet;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.os.Handler;
import com.vanbo.homeux.dravite.newlayouttest.top_fragments.FolderListFragment;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.AppDrawerPageFragment;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import android.view.View.OnDragListener;
import android.widget.FrameLayout;
import com.dravite.homeux.R;


public class DragSurfaceLayout extends FrameLayout implements View.OnDragListener
{
    private static final int PAGE_CHANGE_HOVER_MARGIN = 36;
    private static final String TAG;
    boolean doDispatchDragEventOverride;
    public boolean isDragging;
    LauncherActivity mActivity;
    CustomGridLayout mAppGrid;
    boolean mCanChangePage;
    boolean mCanRedoFreeGrid;
    boolean mCanRemoveDragShadow;
    float mDragDeltaY;
    private int mDragType;
    AppDrawerPageFragment mFocussedPage;
    FolderListFragment mFolderList;
    CustomGridLayout mInitialAppGrid;
    private boolean mIsOnAppPage;
    private DragDropListenerAppDrawer mListener;
    private DragDropListenerFolder mListenerFolder;
    private DragDropListenerQuickApp mListenerQA;
    ObjectDropButtonStrip mObjectDropButtonStrip;
    float mOldY;
    int mPageChangeDelta;
    Handler mPageChangeHandler;
    Runnable mPageChangeRunnable;
    ViewPager mPager;
    QuickAppBar mQuickAppBar;
    boolean mRemoveAnimationHasStarted;
    boolean mShowNextIndicator;
    boolean mShowPrevIndicator;
    int mStartPage;
    String mWidgetRemoveAreaHoveredButton;
    View.OnDragListener tmpOnDragListener;
    
    static {
        TAG = DragSurfaceLayout.class.getName();
    }
    
    public DragSurfaceLayout(final Context context) {
        this(context, null);
    }
    
    public DragSurfaceLayout(final Context context, final AttributeSet set) {
        this(context, set, 0);
    }
    
    public DragSurfaceLayout(final Context context, final AttributeSet set, final int n) {
        this(context, set, n, 0);
    }
    
    public DragSurfaceLayout(final Context context, final AttributeSet set, final int n, final int n2) {
        super(context, set, n, n2);
        this.mPageChangeHandler = new Handler();
        this.mPageChangeRunnable = new Runnable() {
            @Override
            public void run() {
                DragSurfaceLayout.this.changePage();
            }
        };
        this.mDragType = 0;
        this.mOldY = 0.0f;
        this.mDragDeltaY = 0.0f;
        this.mWidgetRemoveAreaHoveredButton = "nothing";
        this.mCanChangePage = true;
        this.mCanRemoveDragShadow = true;
        this.mRemoveAnimationHasStarted = false;
        this.mCanRedoFreeGrid = true;
        this.doDispatchDragEventOverride = true;
        this.mIsOnAppPage = true;
        this.isDragging = false;
        this.setOnDragListener((View.OnDragListener)this);
        if (!this.isInEditMode()) {
            this.mActivity = (LauncherActivity)context;
        }
    }
    
    private void changePage() {
        if (this.mPager.getCurrentItem() + this.mPageChangeDelta >= 0 && this.mPager.getCurrentItem() + this.mPageChangeDelta < this.mPager.getAdapter().getCount()) {
            this.mAppGrid.redoFreeGrid();
            this.mPager.setCurrentItem(this.mPager.getCurrentItem() + this.mPageChangeDelta, true);
            this.mFocussedPage = this.getFocussedPage();
            this.removeView((View)this.mAppGrid.mDragShadowView);
            this.mFocussedPage.mAppGrid.mDragHoverHandler = this.mAppGrid.mDragHoverHandler;
            this.mFocussedPage.mAppGrid.mDragShadowView = this.mAppGrid.mDragShadowView;
            this.mFocussedPage.mAppGrid.hoverPoint = new int[] { Integer.MIN_VALUE, Integer.MIN_VALUE };
            this.mFocussedPage.mAppGrid.mDragData = this.mAppGrid.mDragData;
            this.mFocussedPage.mAppGrid.mDragChanged = this.mAppGrid.mDragChanged;
            this.mFocussedPage.mAppGrid.mDragView = this.mAppGrid.mDragView;
            this.mFocussedPage.mAppGrid.mDragClickPoint = this.mAppGrid.mDragClickPoint;
            this.mFocussedPage.mAppGrid.mDragStartPos = this.mAppGrid.mDragStartPos;
            this.mFocussedPage.mAppGrid.hLocY = this.mAppGrid.hLocY;
            this.mFocussedPage.mAppGrid.hLocX = this.mAppGrid.hLocX;
            this.mFocussedPage.mAppGrid.size = this.mAppGrid.size;
            this.mFocussedPage.mAppGrid.pos = this.mAppGrid.pos;
            this.mFocussedPage.mAppGrid.mHasDragged = this.mAppGrid.mHasDragged;
            this.mFocussedPage.mAppGrid.mDragStartIndex = this.mAppGrid.mDragStartIndex;
            this.setFocussedPagerGrid(this.mFocussedPage.mAppGrid);
            this.mCanChangePage = true;
        }
        if (this.mPager.getCurrentItem() == 0) {
            this.hidePrevPageIndicator();
        }
        else {
            this.showPrevPageIndicator();
        }
        if (this.mPager.getCurrentItem() == this.mPager.getAdapter().getCount() - 1) {
            this.hideNextPageIndicator();
            return;
        }
        this.showNextPageIndicator();
    }
    
    void animateDragViewDrop(final int n, final int n2) {
        final ImageView imageView = new ImageView(this.getContext());
        final Bitmap drawingCache = this.mAppGrid.mDragView.getDrawingCache();
        imageView.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(drawingCache.getWidth(), drawingCache.getHeight()));
        imageView.setImageBitmap(drawingCache);
        this.addView((View)imageView);
        imageView.setTranslationX(this.mAppGrid.mDragView.getTranslationX());
        imageView.setTranslationY(this.mAppGrid.mDragView.getTranslationY());
        imageView.post((Runnable)new Runnable() {
            @Override
            public void run() {
                imageView.animate().scaleX((float)n2).scaleY((float)n2).alpha(0.0f).setDuration((long)n).setInterpolator((TimeInterpolator)new DecelerateInterpolator()).withEndAction((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        if (imageView != null && imageView.getParent() != null) {
                            DragSurfaceLayout.this.removeView((View)imageView);
                        }
                    }
                });
            }
        });
    }
    
    public boolean dispatchDragEvent(final DragEvent dragEvent) {
        if (this.doDispatchDragEventOverride && this.mDragType == 0) {
            switch (dragEvent.getAction()) {
                case 1: {
                    if (this.mAppGrid == null) {
                        return false;
                    }
                    this.dispatchStartAppOrWidget();
                    break;
                }
                case 4: {
                    this.dispatchEndAppOrWidget();
                    break;
                }
            }
        }
        else if (!this.doDispatchDragEventOverride && this.tmpOnDragListener instanceof CustomGridLayout.GripDragListener) {
            switch (dragEvent.getAction()) {
                case 1: {
                    this.tmpOnDragListener.onDrag((View)null, dragEvent);
                    break;
                }
                case 4: {
                    this.tmpOnDragListener.onDrag((View)null, dragEvent);
                    break;
                }
            }
        }
        return super.dispatchDragEvent(dragEvent);
    }
    
    void dispatchEndAppOrWidget() {
        boolean b = true;
        this.dropResetViews();
        if (!this.dropCheckFolderDropAdapter() && !this.dropCheckRemoval()) {
            ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(this.mActivity.mPager.getCurrentItem())).pages.get(this.mPager.getCurrentItem()).items.add(this.mAppGrid.mDragData);
            if (this.mAppGrid.hoverPoint[0] != this.mAppGrid.mDragData.mGridPosition.row || this.mAppGrid.hoverPoint[1] != this.mAppGrid.mDragData.mGridPosition.col || this.mPager.getCurrentItem() != this.mStartPage) {
                b = false;
            }
            if (b || !this.mAppGrid.mDragChanged) {
                this.dropDispatchUnchanged();
            }
            else {
                this.dropDispatchChanged();
            }
            this.mListener.onEndDrag();
            this.postDelayed((Runnable)new Runnable() {
                @Override
                public void run() {
                    ((AppDrawerPagerAdapter)DragSurfaceLayout.this.mPager.getAdapter()).removeEmptyPages();
                }
            }, 200L);
        }
    }
    
    void dispatchStartAppOrWidget() {
        this.mAppGrid.mHasDragged = false;
        this.setPager(this.mAppGrid.mPager);
        this.mStartPage = this.mPager.getCurrentItem();
        this.mAppGrid.mVibrator.vibrate(35L);
        this.isDragging = true;
        this.mPageChangeDelta = 0;
        if (this.mAppGrid.getGridType() == 0) {
            ((AppDrawerPagerAdapter)this.mPager.getAdapter()).addPage();
        }
        this.mPageChangeRunnable.run();
        this.mOldY = this.mAppGrid.mDragStartPos[1];
        this.mListener.onStartDrag(this.mAppGrid.mDragView, this.mAppGrid.mDragData, this.mPager);
        this.mActivity.mIndicator.animate().scaleX(1.0f / this.mPager.getAdapter().getCount()).translationX((float)(this.mPager.getCurrentItem() * (this.mActivity.mAppBarLayout.getMeasuredWidth() / this.mPager.getAdapter().getCount())));
        ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(this.mActivity.mPager.getCurrentItem())).pages.get(this.mStartPage).items.remove(this.mAppGrid.mDragData);
    }
    
    boolean dropCheckFolderDropAdapter() {
        if (this.mActivity.mFolderDropAdapter != null && this.mActivity.mFolderDropAdapter.getHovered() != -1 && this.mAppGrid.mDragData instanceof Application) {
            this.mActivity.addAppToFolder((Application)this.mAppGrid.mDragData, this.mActivity.mFolderDropAdapter.getHovered());
            LauncherActivity.mFolderStructure.addFolderAssignments((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(this.mActivity.mFolderDropAdapter.getHovered()));
            this.mActivity.mFolderDropAdapter.resetHovered();
            if (this.mAppGrid.mDragView != null) {
                this.mAppGrid.mDragView.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(100L).withEndAction((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        JsonHelper.saveFolderStructure((Context)DragSurfaceLayout.this.mActivity, LauncherActivity.mFolderStructure);
                        DragSurfaceLayout.this.mActivity.refreshAllFolder(DragSurfaceLayout.this.mActivity.mHolder.gridHeight, DragSurfaceLayout.this.mActivity.mHolder.gridWidth);
                        DragSurfaceLayout.this.removeAllViews();
                        DragSurfaceLayout.this.mListener.onEndDrag();
                    }
                });
            }
            return true;
        }
        return false;
    }
    
    boolean dropCheckRemoval() {
        boolean b = false;
        if (this.mDragDeltaY > LauncherUtils.dpToPx(25.0f, (Context)this.mActivity)) {
            b = b;
            if (!this.mActivity.isInAllFolder()) {
                LauncherLog.d(DragSurfaceLayout.TAG, "Flung away an object, because delta is " + this.mDragDeltaY + ". Deleting now.");
                this.mWidgetRemoveAreaHoveredButton = "remove";
                b = true;
            }
        }
        this.mOldY = 0.0f;
        this.mDragDeltaY = 0.0f;
        return this.mObjectDropButtonStrip.doRemove(this.mAppGrid.mDragData, this.mAppGrid.mDragView, this.mWidgetRemoveAreaHoveredButton, this.mAppGrid, this.mPager, this.mActivity.mPager.getCurrentItem(), this.mPager.getCurrentItem(), true, new Runnable() {
            @Override
            public void run() {
                DragSurfaceLayout.this.mAppGrid.normalizeGrid();
                DragSurfaceLayout.this.mAppGrid.redoFreeGrid();
                ((AppDrawerPagerAdapter)DragSurfaceLayout.this.mPager.getAdapter()).removeEmptyPages();
                DragSurfaceLayout.this.mListener.onEndDrag();
                DragSurfaceLayout.this.removeAllViews();
                JsonHelper.saveFolderStructure((Context)DragSurfaceLayout.this.mActivity, LauncherActivity.mFolderStructure);
            }
        }, b);
    }
    
    void dropDispatchChanged() {
        float n = 1.0f;
        final int hLocX = this.mAppGrid.hLocX;
        final int hLocY = this.mAppGrid.hLocY;
        final int[] array = { this.mAppGrid.hLocY * (this.mAppGrid.size[1] / this.mAppGrid.getRowCount()), this.mAppGrid.hLocX * (this.mAppGrid.size[0] / this.mAppGrid.getColumnCount()) };
        this.mInitialAppGrid.fixFreeGrid();
        this.mAppGrid.fixFreeGrid();
        if (this.mAppGrid.mDragView == null) {
            return;
        }
        final ViewPropertyAnimator translationZ = this.mAppGrid.mDragView.animate().x((float)(array[1] + this.mAppGrid.pos.x)).y((float)(array[0] + this.mAppGrid.pos.y)).scaleX(1.0f).scaleY(1.0f).setDuration(200L).setInterpolator((TimeInterpolator)new DecelerateInterpolator()).translationZ(0.0f);
        if (!this.mIsOnAppPage) {
            n = 0.0f;
        }
        translationZ.alpha(n).withEndAction((Runnable)new Runnable() {
            @Override
            public void run() {
                ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(DragSurfaceLayout.this.mActivity.mPager.getCurrentItem())).pages.get(DragSurfaceLayout.this.mPager.getCurrentItem()).items.remove(DragSurfaceLayout.this.mAppGrid.mDragData);
                DragSurfaceLayout.this.mAppGrid.freeCells(hLocY, hLocX, DragSurfaceLayout.this.mAppGrid.mDragData.mGridPosition.rowSpan, DragSurfaceLayout.this.mAppGrid.mDragData.mGridPosition.colSpan);
                DragSurfaceLayout.this.mAppGrid.mDragData.mGridPosition.row = hLocY;
                DragSurfaceLayout.this.mAppGrid.mDragData.mGridPosition.col = hLocX;
                ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(DragSurfaceLayout.this.mActivity.mPager.getCurrentItem())).pages.get(DragSurfaceLayout.this.mPager.getCurrentItem()).items.add(DragSurfaceLayout.this.mAppGrid.mDragData);
                DragSurfaceLayout.this.mAppGrid.addObject(DragSurfaceLayout.this.mAppGrid.mDragData, 1.0f, (Interpolator)new AccelerateInterpolator(), 0, new Runnable() {
                    @Override
                    public void run() {
                        DragSurfaceLayout.this.removeAllViews();
                    }
                });
                DragSurfaceLayout.this.mAppGrid.mDragView = null;
                DragSurfaceLayout.this.mAppGrid.hoverPoint[0] = -1;
                DragSurfaceLayout.this.mAppGrid.hoverPoint[1] = -1;
                DragSurfaceLayout.this.mAppGrid.mDragShadowView = null;
                JsonHelper.saveFolderStructure((Context)DragSurfaceLayout.this.mActivity, LauncherActivity.mFolderStructure);
            }
        });
    }
    
    void dropDispatchUnchanged() {
        float n = 1.0f;
        this.resetPager();
        if (this.mAppGrid.mDragView == null) {
            return;
        }
        final ViewPropertyAnimator setInterpolator = this.mAppGrid.mDragView.animate().x((float)this.mAppGrid.mDragStartPos[0]).y((float)this.mAppGrid.mDragStartPos[1]).scaleX(1.0f).scaleY(1.0f).setDuration(200L).setInterpolator((TimeInterpolator)new DecelerateInterpolator());
        if (!this.mIsOnAppPage) {
            n = 0.0f;
        }
        setInterpolator.alpha(n).withEndAction((Runnable)new Runnable() {
            @Override
            public void run() {
                DragSurfaceLayout.this.mAppGrid.addObject(DragSurfaceLayout.this.mAppGrid.mDragData, 1.0f, (Interpolator)new AccelerateInterpolator(), 0, new Runnable() {
                    @Override
                    public void run() {
                        DragSurfaceLayout.this.removeAllViews();
                    }
                });
                if (DragSurfaceLayout.this.mAppGrid.mDragView instanceof ClickableAppWidgetHostView) {
                    DragSurfaceLayout.this.mAppGrid.showResizeGrips(DragSurfaceLayout.this.mAppGrid.mDragData);
                }
                DragSurfaceLayout.this.mAppGrid.mDragView = null;
                DragSurfaceLayout.this.mAppGrid.hoverPoint[0] = -1;
                DragSurfaceLayout.this.mAppGrid.hoverPoint[1] = -1;
                DragSurfaceLayout.this.mAppGrid.mDragShadowView = null;
            }
        });
    }
    
    void dropResetViews() {
        this.isDragging = false;
        this.hideNextPageIndicator();
        this.hidePrevPageIndicator();
        if (this.mQuickAppBar.stopHovering(this.mAppGrid.mDragData)) {
            this.animateDragViewDrop(130, 8);
        }
        this.hideNextPageIndicator();
        this.hidePrevPageIndicator();
        this.mObjectDropButtonStrip.exitHover();
        this.mAppGrid.mDragHoverHandler.removeCallbacksAndMessages((Object)null);
        this.mActivity.mIndicator.animate().scaleX(1.0f / this.mPager.getAdapter().getCount()).translationX((float)(this.mPager.getCurrentItem() * (this.mActivity.mAppBarLayout.getMeasuredWidth() / this.mPager.getAdapter().getCount())));
        if (this.mAppGrid.mDragShadowView != null) {
            this.mAppGrid.mDragShadowView.animate().scaleY(0.0f).scaleX(0.0f).alpha(0.0f).setDuration(100L).withEndAction((Runnable)new Runnable() {
                @Override
                public void run() {
                    DragSurfaceLayout.this.removeView((View)DragSurfaceLayout.this.mAppGrid.mDragShadowView);
                }
            });
        }
    }
    
    public AppDrawerPageFragment getFocussedPage() {
        return (AppDrawerPageFragment)this.mPager.getAdapter().instantiateItem(this.mPager, this.mPager.getCurrentItem());
    }
    
    public void hideNextPageIndicator() {
        final View viewWithTag = this.findViewWithTag((Object)"next");
        if (viewWithTag != null) {
            this.mShowNextIndicator = false;
            viewWithTag.animate().translationX((float)(int)TypedValue.applyDimension(1, 28.0f, this.getResources().getDisplayMetrics())).withEndAction((Runnable)new Runnable() {
                @Override
                public void run() {
                    DragSurfaceLayout.this.removeView(viewWithTag);
                }
            });
        }
    }
    
    public void hidePrevPageIndicator() {
        final View viewWithTag = this.findViewWithTag((Object)"prev");
        if (viewWithTag != null) {
            this.mShowPrevIndicator = false;
            viewWithTag.animate().translationX((float)(-(int)TypedValue.applyDimension(1, 28.0f, this.getResources().getDisplayMetrics()))).withEndAction((Runnable)new Runnable() {
                @Override
                public void run() {
                    DragSurfaceLayout.this.removeView(viewWithTag);
                }
            });
        }
    }
    
    public boolean onDrag(final View view, final DragEvent dragEvent) {
        switch (this.mDragType) {
            default: {
                return false;
            }
            case 0: {
                return this.onDragAppOrWidget(dragEvent);
            }
            case 1: {
                return this.onDragQuickAction(dragEvent);
            }
            case 2: {
                return this.onDragFolder(dragEvent);
            }
        }
    }
    
    public boolean onDragAppOrWidget(final DragEvent dragEvent) {
        switch (dragEvent.getAction()) {
            case 2: {
                if (this.mAppGrid.mDragView == null || this.mAppGrid.mDragData == null) {
                    return false;
                }
                this.mDragDeltaY = this.mOldY - dragEvent.getY();
                LauncherLog.d(DragSurfaceLayout.TAG, "Difference: " + this.mDragDeltaY);
                this.mOldY = dragEvent.getY();
                this.mWidgetRemoveAreaHoveredButton = this.mObjectDropButtonStrip.doHover(this.mAppGrid.mDragData, (int)dragEvent.getX(), (int)dragEvent.getY());
                if (this.mAppGrid.mDragView instanceof AppIconView && this.mAppGrid.mDragData.getObjectType() == 0) {
                    if (this.mQuickAppBar != null) {
                        this.mQuickAppBar.hoverAt(dragEvent.getX(), dragEvent.getY());
                    }
                    if (this.mActivity.isInFolderDropLocation(dragEvent.getX(), dragEvent.getY(), this.mQuickAppBar.mIsHovering || !this.mWidgetRemoveAreaHoveredButton.equals("nothing"))) {
                        this.mActivity.switchToFolderView();
                    }
                    else {
                        this.mActivity.switchBackFromFolderView();
                    }
                }
                final int[] array = new int[2];
                this.mAppGrid.getLocationInWindow(array);
                if (dragEvent.getY() >= array[1] && dragEvent.getY() <= array[1] + this.mAppGrid.size[1] - TypedValue.applyDimension(1, 48.0f, this.getResources().getDisplayMetrics())) {
                    this.mCanRemoveDragShadow = true;
                }
                if (this.mAppGrid.mDragClickPoint == null) {
                    this.mAppGrid.mDragClickPoint = new PointF(0.0f, 0.0f);
                }
                this.mAppGrid.mDragView.setX(dragEvent.getX() - this.mAppGrid.mDragView.getWidth() / 2.0f - this.mAppGrid.mDragClickPoint.x);
                this.mAppGrid.mDragView.setY(dragEvent.getY() - this.mAppGrid.mDragView.getHeight() / 2.0f - this.mAppGrid.mDragClickPoint.y);
                if (this.mAppGrid.hLocX == Integer.MIN_VALUE || this.mAppGrid.hLocY == Integer.MIN_VALUE) {
                    this.mAppGrid.hLocY = this.mAppGrid.mDragData.mGridPosition.row;
                    this.mAppGrid.hLocX = this.mAppGrid.mDragData.mGridPosition.col;
                }
                final int hLocX = this.mAppGrid.hLocX;
                final int hLocY = this.mAppGrid.hLocY;
                final int n = (int)(dragEvent.getY() - array[1] + TypedValue.applyDimension(1, 8.0f, this.getResources().getDisplayMetrics()));
                final int n2 = this.mAppGrid.mDragView.getMeasuredHeight() / 2;
                this.mAppGrid.hLocX = Math.max(0, (int)Math.min(((int)(dragEvent.getX() - array[0]) - this.mAppGrid.mDragView.getMeasuredWidth() / 2 + 0.5f * this.mAppGrid.getColumnWidth() * 0.94f - this.mAppGrid.mDragClickPoint.x) / (this.mAppGrid.getColumnWidth() * 0.94f), this.mAppGrid.getColumnCount() - this.mAppGrid.mDragData.mGridPosition.colSpan));
                this.mAppGrid.hLocY = Math.max(0, (int)Math.min((n - n2 + 0.5f * this.mAppGrid.getRowHeight() * 0.94f - this.mAppGrid.mDragClickPoint.y) / (this.mAppGrid.getRowHeight() * 0.94f), this.mAppGrid.getRowCount() - this.mAppGrid.mDragData.mGridPosition.rowSpan));
                this.mCanRemoveDragShadow = true;
                this.mCanRemoveDragShadow = ((dragEvent.getY() < array[1] || dragEvent.getY() > array[1] + this.mAppGrid.size[1] - TypedValue.applyDimension(1, 48.0f, this.getResources().getDisplayMetrics())) && this.mCanRemoveDragShadow);
                if (!this.mCanRemoveDragShadow && this.mAppGrid.hLocY + this.mAppGrid.mDragData.mGridPosition.rowSpan - 1 < this.mAppGrid.getRowCount() && this.mAppGrid.hLocX >= 0 && this.mAppGrid.hLocX + this.mAppGrid.mDragData.mGridPosition.colSpan - 1 < this.mAppGrid.getColumnCount() && this.mAppGrid.hLocY >= 0) {
                    this.mRemoveAnimationHasStarted = false;
                    if (this.mIsOnAppPage) {
                        this.mAppGrid.hoverAt(hLocY, hLocX, this.mAppGrid.hLocY, this.mAppGrid.hLocX);
                    }
                    else {
                        this.removeView((View)this.mAppGrid.mDragShadowView);
                    }
                    this.mCanRedoFreeGrid = true;
                }
                else {
                    if (this.mCanRedoFreeGrid) {
                        this.mAppGrid.redoFreeGrid();
                        this.mCanRedoFreeGrid = false;
                    }
                    this.mAppGrid.mDragHoverHandler.removeCallbacksAndMessages((Object)null);
                    this.mAppGrid.hoverPoint[0] = Integer.MIN_VALUE;
                    this.mAppGrid.hoverPoint[1] = Integer.MIN_VALUE;
                    this.mAppGrid.hoverPoint[0] = this.mAppGrid.mDragData.mGridPosition.row;
                    this.mAppGrid.hoverPoint[1] = this.mAppGrid.mDragData.mGridPosition.col;
                    this.removeView((View)this.mAppGrid.mDragShadowView);
                    this.mAppGrid.mDragChanged = false;
                    if (dragEvent.getY() < array[1] || dragEvent.getY() > array[1] + this.mAppGrid.getMeasuredHeight()) {
                        this.resetPager();
                    }
                }
                if (this.mIsOnAppPage && this.mAppGrid.getGridType() != 1) {
                    if (dragEvent.getX() >= this.getRight() - TypedValue.applyDimension(1, 36.0f, this.getResources().getDisplayMetrics())) {
                        if (this.mCanChangePage) {
                            this.mCanChangePage = false;
                            this.mPageChangeDelta = 1;
                            this.mPageChangeHandler.postDelayed(this.mPageChangeRunnable, 600L);
                        }
                    }
                    else if (dragEvent.getX() <= TypedValue.applyDimension(1, 36.0f, this.getResources().getDisplayMetrics())) {
                        if (this.mCanChangePage) {
                            this.mCanChangePage = false;
                            this.mPageChangeDelta = -1;
                            this.mPageChangeHandler.postDelayed(this.mPageChangeRunnable, 600L);
                        }
                    }
                    else {
                        this.mPageChangeHandler.removeCallbacksAndMessages((Object)null);
                        this.mCanChangePage = true;
                    }
                }
                if (dragEvent.getY() < array[1] && !this.mRemoveAnimationHasStarted) {
                    this.mRemoveAnimationHasStarted = true;
                    break;
                }
                break;
            }
        }
        return true;
    }
    
    public boolean onDragFolder(final DragEvent dragEvent) {
        switch (dragEvent.getAction()) {
            case 1: {
                if (this.mFolderList.mDragButton == null) {
                    return false;
                }
                this.mFolderList.mDragButton.animate().translationZ((float)LauncherUtils.dpToPx(4.0f, (Context)this.mActivity)).alpha(0.8f).scaleY(1.1f).scaleX(1.1f).setDuration(150L);
                this.mListenerFolder.onStartDrag(this.mFolderList.mDragButton, this.mFolderList.mDragFolder);
                break;
            }
            case 2: {
                this.mWidgetRemoveAreaHoveredButton = this.mObjectDropButtonStrip.doHover(null, (int)dragEvent.getX(), (int)dragEvent.getY());
                this.mFolderList.hover((int)(dragEvent.getX() - this.mFolderList.mTouchPosition.x), (int)(dragEvent.getY() - this.mFolderList.mTouchPosition.y));
                this.mFolderList.mDragButton.setX(dragEvent.getX() - this.mFolderList.mDragButton.getMeasuredWidth() / 2 - this.mFolderList.mTouchPosition.x);
                this.mFolderList.mDragButton.setY(dragEvent.getY() - this.mFolderList.mDragButton.getMeasuredHeight() / 2 - this.mFolderList.mTouchPosition.y);
                break;
            }
            case 4: {
                if (this.mObjectDropButtonStrip.doRemoveFolder(this.mFolderList.mDragButton, this.mFolderList.mDragFolder, this.mWidgetRemoveAreaHoveredButton)) {
                    this.mFolderList.mAdapter.cancelDrag();
                    ((RecyclerView.Adapter)this.mFolderList.mAdapter).notifyItemChanged(LauncherActivity.mFolderStructure.folders.indexOf(this.mFolderList.mDragFolder));
                    this.mFolderList.mDragButton.animate().alpha(0.0f).scaleY(0.0f).scaleX(0.0f).setDuration(150L).withEndAction((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            DragSurfaceLayout.this.removeAllViews();
                        }
                    });
                    this.mFolderList.mDragFolder = null;
                    this.mListenerFolder.onEndDrag();
                    return true;
                }
                ((FolderPagerAdapter)this.mActivity.mPager.getAdapter()).notifyPagesChanged();
                JsonHelper.saveFolderStructure((Context)this.mActivity, LauncherActivity.mFolderStructure);
                this.mFolderList.mDragFolder = null;
                this.mActivity.mPager.setCurrentItem(LauncherActivity.mFolderStructure.folders.indexOf(LauncherActivity.mFolderStructure.getFolderWithName(this.mFolderList.mCurrentlySelectedFolder)), false);
                this.mFolderList.mAdapter.cancelDrag();
                if (this.mFolderList.mDragStartIndex == this.mFolderList.mDragCurrentIndex) {
                    this.mFolderList.mFolderList.scrollTo(0, this.mFolderList.mDragInitialScroll);
                }
                this.mFolderList.mDragButton.animate().alpha(1.0f).scaleY(1.0f).scaleX(1.0f).x((float)this.mFolderList.mDragTargetPosition[0]).y((float)this.mFolderList.mDragTargetPosition[1]).translationZ((float)LauncherUtils.dpToPx(0.0f, (Context)this.mActivity)).setDuration(150L).withEndAction((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        DragSurfaceLayout.this.removeAllViews();
                        DragSurfaceLayout.this.mFolderList.mAdapter.select(DragSurfaceLayout.this.mActivity.mPager.getCurrentItem());
                        DragSurfaceLayout.this.mListenerFolder.onEndDrag();
                        DragSurfaceLayout.this.mActivity.revealColor(((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(DragSurfaceLayout.this.mActivity.mPager.getCurrentItem())).headerImage);
                    }
                });
                break;
            }
        }
        return true;
    }
    
    public boolean onDragQuickAction(final DragEvent dragEvent) {
        if (this.mQuickAppBar.mQaDragView != null) {
            switch (dragEvent.getAction()) {
                default: {
                    this.mQuickAppBar.mQaDragView.setAlpha(1.0f);
                    this.mWidgetRemoveAreaHoveredButton = this.mObjectDropButtonStrip.doHover(null, (int)dragEvent.getX(), (int)dragEvent.getY());
                    final float x = dragEvent.getX();
                    final float n = this.mQuickAppBar.mQaDragView.getMeasuredWidth() / 2.0f;
                    final float x2 = this.mQuickAppBar.mTouchPosition.x;
                    final float y = dragEvent.getY();
                    final float n2 = this.mQuickAppBar.mQaDragView.getMeasuredHeight() / 2.0f;
                    final float y2 = this.mQuickAppBar.mTouchPosition.y;
                    this.mQuickAppBar.mQaDragView.setX(x - n - x2);
                    this.mQuickAppBar.mQaDragView.setY(y - n2 - y2);
                    this.mQuickAppBar.hoverAt(dragEvent.getX(), dragEvent.getY());
                    break;
                }
                case 3: {
                    break;
                }
                case 1: {
                    this.mListenerQA.onStartDrag(this.mQuickAppBar.mQaDragView);
                    return true;
                }
                case 4: {
                    this.mQuickAppBar.endDrag();
                    final int[] absolutePositionCoords = this.mQuickAppBar.getAbsolutePositionCoords(this.mQuickAppBar.mHoverIndex);
                    if (this.mWidgetRemoveAreaHoveredButton.equals("remove")) {
                        this.mQuickAppBar.mQaDragView.animate().scaleX(0.0f).scaleY(0.0f).withEndAction((Runnable)new Runnable() {
                            @Override
                            public void run() {
                                DragSurfaceLayout.this.mQuickAppBar.mHoverIndex = -1;
                                DragSurfaceLayout.this.removeView(DragSurfaceLayout.this.mQuickAppBar.mQaDragView);
                                JsonHelper.saveQuickApps(DragSurfaceLayout.this.getContext(), DragSurfaceLayout.this.mQuickAppBar);
                                DragSurfaceLayout.this.mQuickAppBar.stopHovering(DragSurfaceLayout.this.mQuickAppBar.mQaDragView);
                            }
                        });
                    }
                    else if (this.mWidgetRemoveAreaHoveredButton.equals("editQa")) {
                        final int[] absolutePositionCoords2 = this.mQuickAppBar.getAbsolutePositionCoords(this.mQuickAppBar.mStartDragIndex);
                        this.mQuickAppBar.mHoverIndex = this.mQuickAppBar.mStartDragIndex;
                        this.mQuickAppBar.moveHandler.removeCallbacksAndMessages((Object)null);
                        this.mQuickAppBar.redoFreeSpace();
                        this.mQuickAppBar.mQaDragView.animate().x((float)absolutePositionCoords2[0]).y((float)absolutePositionCoords2[1]).scaleX(1.0f).scaleY(1.0f).withEndAction((Runnable)new Runnable() {
                            @Override
                            public void run() {
                                DragSurfaceLayout.this.removeView(DragSurfaceLayout.this.mQuickAppBar.mQaDragView);
                                DragSurfaceLayout.this.mQuickAppBar.mIsHovering = true;
                                DragSurfaceLayout.this.mQuickAppBar.stopHovering(DragSurfaceLayout.this.mQuickAppBar.mQaDragView);
                                final Intent intent = new Intent(DragSurfaceLayout.this.getContext(), (Class)AddQuickActionActivity.class);
                                intent.putExtra("index", DragSurfaceLayout.this.mQuickAppBar.mStartDragIndex);
                                DragSurfaceLayout.this.mActivity.startActivityForResult(intent, 379);
                            }
                        });
                    }
                    else if (absolutePositionCoords[0] < 0 || !this.mQuickAppBar.mHasChanged) {
                        final int[] absolutePositionCoords3 = this.mQuickAppBar.getAbsolutePositionCoords(this.mQuickAppBar.mStartDragIndex);
                        this.mQuickAppBar.moveHandler.removeCallbacksAndMessages((Object)null);
                        this.mQuickAppBar.redoFreeSpace();
                        this.mQuickAppBar.mQaDragView.animate().x((float)absolutePositionCoords3[0]).y((float)absolutePositionCoords3[1]).scaleX(1.0f).scaleY(1.0f).withEndAction((Runnable)new Runnable() {
                            @Override
                            public void run() {
                                DragSurfaceLayout.this.mQuickAppBar.mHoverIndex = DragSurfaceLayout.this.mQuickAppBar.mStartDragIndex;
                                DragSurfaceLayout.this.removeView(DragSurfaceLayout.this.mQuickAppBar.mQaDragView);
                                DragSurfaceLayout.this.mQuickAppBar.mIsHovering = true;
                                DragSurfaceLayout.this.mQuickAppBar.stopHovering(DragSurfaceLayout.this.mQuickAppBar.mQaDragView);
                            }
                        });
                    }
                    else {
                        this.mQuickAppBar.mQaDragView.animate().x((float)absolutePositionCoords[0]).y((float)absolutePositionCoords[1]).scaleX(1.0f).scaleY(1.0f).withEndAction((Runnable)new Runnable() {
                            @Override
                            public void run() {
                                DragSurfaceLayout.this.removeView(DragSurfaceLayout.this.mQuickAppBar.mQaDragView);
                                DragSurfaceLayout.this.mQuickAppBar.stopHovering(DragSurfaceLayout.this.mQuickAppBar.mQaDragView);
                            }
                        });
                    }
                    this.mListenerQA.onEndDrag();
                    return true;
                }
            }
        }
        return true;
    }
    
    public void overrideDispatch(final boolean doDispatchDragEventOverride) {
        this.doDispatchDragEventOverride = doDispatchDragEventOverride;
    }
    
    public void resetPager() {
        this.mPager.setCurrentItem(this.mStartPage, true);
        this.mFocussedPage = this.getFocussedPage();
        this.mFocussedPage.mAppGrid.mDragHoverHandler = this.mAppGrid.mDragHoverHandler;
        this.mFocussedPage.mAppGrid.mDragShadowView = this.mAppGrid.mDragShadowView;
        this.mFocussedPage.mAppGrid.hoverPoint = this.mAppGrid.hoverPoint;
        this.mFocussedPage.mAppGrid.mDragData = this.mAppGrid.mDragData;
        this.mFocussedPage.mAppGrid.mDragChanged = this.mAppGrid.mDragChanged;
        this.mFocussedPage.mAppGrid.mDragView = this.mAppGrid.mDragView;
        this.mFocussedPage.mAppGrid.mDragStartPos = this.mAppGrid.mDragStartPos;
        this.mFocussedPage.mAppGrid.hLocY = this.mAppGrid.hLocY;
        this.mFocussedPage.mAppGrid.hLocX = this.mAppGrid.hLocX;
        this.mFocussedPage.mAppGrid.size = this.mAppGrid.size;
        this.mFocussedPage.mAppGrid.pos = this.mAppGrid.pos;
        this.mFocussedPage.mAppGrid.mHasDragged = this.mAppGrid.mHasDragged;
        this.mFocussedPage.mAppGrid.mDragStartIndex = this.mAppGrid.mDragStartIndex;
        this.setFocussedPagerGrid(this.mFocussedPage.mAppGrid);
        this.mCanChangePage = true;
    }
    
    public void setDragDropListenerAppdrawer(final DragDropListenerAppDrawer mListener) {
        this.mListener = mListener;
    }
    
    public void setDragDropListenerFolder(final DragDropListenerFolder mListenerFolder) {
        this.mListenerFolder = mListenerFolder;
    }
    
    public void setDragDropListenerQuickApp(final DragDropListenerQuickApp mListenerQA) {
        this.mListenerQA = mListenerQA;
    }
    
    public void setFocussedPagerGrid(final CustomGridLayout mAppGrid) {
        this.mAppGrid = mAppGrid;
    }
    
    public void setFolderListFragment(final FolderListFragment mFolderList) {
        this.mFolderList = mFolderList;
    }
    
    public void setInitialAppGrid(final CustomGridLayout mInitialAppGrid) {
        this.mInitialAppGrid = mInitialAppGrid;
    }
    
    public void setIsOnAppPage(final boolean mIsOnAppPage) {
        this.mIsOnAppPage = mIsOnAppPage;
    }
    
    public void setObjectDropButtonStrip(final ObjectDropButtonStrip mObjectDropButtonStrip) {
        this.mObjectDropButtonStrip = mObjectDropButtonStrip;
    }
    
    public void setOnDragListener(final View.OnDragListener tmpOnDragListener) {
        super.setOnDragListener(this.tmpOnDragListener = tmpOnDragListener);
    }
    
    public void setPager(final ViewPager mPager) {
        this.mPager = mPager;
    }
    
    public void setQuickActionBar(final QuickAppBar mQuickAppBar) {
        this.mQuickAppBar = mQuickAppBar;
    }
    
    public void showNextPageIndicator() {
        this.mShowNextIndicator = true;
        if (this.findViewWithTag((Object)"next") != null || this.mAppGrid.getGridType() == 1) {
            return;
        }
        final View view = new View(this.getContext());
        view.setTag((Object)"next");
        view.setBackground(this.getContext().getDrawable(R.drawable.next_page_indicator));
        view.setElevation((float)(int)TypedValue.applyDimension(1, 4.0f, this.getResources().getDisplayMetrics()));
        final FrameLayout.LayoutParams frameLayoutLayoutParams = new FrameLayout.LayoutParams((int)TypedValue.applyDimension(1, 24.0f, this.getResources().getDisplayMetrics()), this.mAppGrid.getMeasuredHeight() - (int)TypedValue.applyDimension(1, 24.0f, this.getResources().getDisplayMetrics()));
        frameLayoutLayoutParams.gravity = 8388693;
        frameLayoutLayoutParams.bottomMargin = (int)TypedValue.applyDimension(1, 24.0f, this.getResources().getDisplayMetrics());
        view.setTranslationX((float)(int)TypedValue.applyDimension(1, 28.0f, this.getResources().getDisplayMetrics()));
        this.addView(view, (ViewGroup.LayoutParams)frameLayoutLayoutParams);
        view.post((Runnable)new Runnable() {
            @Override
            public void run() {
                view.animate().translationX((float)LauncherUtils.dpToPx(14.0f, DragSurfaceLayout.this.getContext()));
            }
        });
    }
    
    public void showPrevPageIndicator() {
        this.mShowPrevIndicator = true;
        if (this.findViewWithTag((Object)"prev") != null || this.mAppGrid.getGridType() == 1) {
            return;
        }
        final View view = new View(this.getContext());
        view.setTag((Object)"prev");
        view.setBackground(this.getContext().getDrawable(R.drawable.prev_page_indicator));
        view.setElevation((float)(int)TypedValue.applyDimension(1, 4.0f, this.getResources().getDisplayMetrics()));
        final FrameLayout.LayoutParams frameLayoutLayoutParams = new FrameLayout.LayoutParams((int)TypedValue.applyDimension(1, 24.0f, this.getResources().getDisplayMetrics()), this.mAppGrid.getMeasuredHeight() - (int)TypedValue.applyDimension(1, 24.0f, this.getResources().getDisplayMetrics()));
        frameLayoutLayoutParams.gravity = 8388691;
        frameLayoutLayoutParams.bottomMargin = (int)TypedValue.applyDimension(1, 24.0f, this.getResources().getDisplayMetrics());
        view.setTranslationX((float)(-(int)TypedValue.applyDimension(1, 28.0f, this.getResources().getDisplayMetrics())));
        this.addView(view, (ViewGroup.LayoutParams)frameLayoutLayoutParams);
        view.post((Runnable)new Runnable() {
            @Override
            public void run() {
                view.animate().translationX((float)(-LauncherUtils.dpToPx(14.0f, DragSurfaceLayout.this.getContext())));
            }
        });
    }
    
    public void startDrag(final int mDragType) {
        this.mDragType = mDragType;
        this.startDrag(ClipData.newPlainText((CharSequence)"dt", (CharSequence)("" + mDragType)), new View.DragShadowBuilder(), (Object)null, 0);
    }
    
    public interface DragDropListenerAppDrawer
    {
        void onEndDrag();
        
        void onStartDrag(final View p0, final DrawerObject p1, final ViewPager p2);
    }
    
    public interface DragDropListenerFolder
    {
        void onEndDrag();
        
        void onStartDrag(final View p0, final FolderStructure.Folder p1);
    }
    
    public interface DragDropListenerQuickApp
    {
        void onEndDrag();
        
        void onStartDrag(final View p0);
    }
    
    public static class DragType
    {
        public static final int TYPE_APP_PAGE = 0;
        public static final int TYPE_FOLDER = 2;
        public static final int TYPE_QA = 1;
    }
}
