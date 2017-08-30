// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.animation.ValueAnimator;
import android.app.Service;
import android.util.Log;
import android.view.DragEvent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.appwidget.AppWidgetProviderInfo;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View.BaseSavedState;
import android.os.Parcelable;
import android.view.animation.DecelerateInterpolator;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.AppDrawerPagerAdapter;
import android.view.View.OnDragListener;
import com.vanbo.homeux.dravite.newlayouttest.views.helpers.CheckForLongPressHelper;
import java.lang.ref.SoftReference;
import android.view.View.MeasureSpec;
import java.util.Iterator;
import android.content.res.ColorStateList;
import com.vanbo.homeux.dravite.newlayouttest.LauncherLog;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import android.view.ViewPropertyAnimator;
import android.support.v4.widget.Space;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.JsonHelper;
import java.util.Random;
import android.graphics.Rect;
import android.widget.ImageView.ScaleType;
import android.widget.FrameLayout;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.FolderStructure;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.Widget;
import android.view.ViewGroup;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.Application;
import android.animation.TimeInterpolator;
import android.view.animation.OvershootInterpolator;
import android.support.annotation.Nullable;
import android.os.Bundle;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.ClickableAppWidgetHostView;
import android.view.ViewGroup.LayoutParams;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.Shortcut;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.animation.Interpolator;
import com.vanbo.homeux.dravite.newlayouttest.Const;
import android.preference.PreferenceManager;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Point;
import android.os.Vibrator;
import android.widget.RelativeLayout;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.os.Handler;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.DrawerObject;
import android.graphics.PointF;
import com.vanbo.homeux.dravite.newlayouttest.views.helpers.AppWidgetContainer;
import java.util.ArrayList;
import android.view.View.OnLongClickListener;
import android.support.v7.widget.GridLayout;
import com.dravite.homeux.R;


public class CustomGridLayout extends GridLayout implements View.OnLongClickListener
{
    private static final int DEFAULT_COLUMN_COUNT = 4;
    private static final int DEFAULT_ROW_COUNT = 5;
    private static final String TAG = "CustomGridLayout";
    public static final int VIBRATE_LIFT = 35;
    ArrayList<AppIconView> applicationViews;
    public int hLocX;
    public int hLocY;
    public int[] hoverPoint;
    private int mAppPage;
    public AppWidgetContainer mAppWidgetContainer;
    private boolean[] mCellUsed;
    private boolean[] mCellUsedTemp;
    public boolean mDragChanged;
    public PointF mDragClickPoint;
    public DrawerObject mDragData;
    public Handler mDragHoverHandler;
    private Bitmap mDragShadow;
    public ImageView mDragShadowView;
    public int mDragStartIndex;
    public int[] mDragStartPos;
    private DragSurfaceLayout mDragSurface;
    public View mDragView;
    private int mFolderPage;
    private int mGridType;
    public boolean mHasDragged;
    LayoutInflater mInflater;
    public List<View> mMovedViewList;
    private final List<CellSpan> mMovedViewSpans;
    private final List<Cell> mMovedViewTargetCells;
    public ViewPager mPager;
    public SharedPreferences mPreferences;
    private RelativeLayout mResizeGrips;
    private boolean mShadowIsAnimating;
    public Vibrator mVibrator;
    private boolean notificationsEnabled;
    public Point pos;
    public int[] size;
    
    public CustomGridLayout(final Context context) {
        this(context, null);
    }
    
    public CustomGridLayout(final Context context, final AttributeSet set) {
        this(context, set, 0);
    }
    
    public CustomGridLayout(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.mMovedViewList = new ArrayList<View>();
        this.mMovedViewSpans = new ArrayList<CellSpan>();
        this.mMovedViewTargetCells = new ArrayList<Cell>();
        this.size = new int[2];
        this.hLocX = Integer.MIN_VALUE;
        this.hLocY = Integer.MIN_VALUE;
        this.mGridType = 0;
        this.notificationsEnabled = false;
        this.applicationViews = new ArrayList<AppIconView>();
        this.mHasDragged = false;
        this.mDragStartPos = new int[2];
        this.hoverPoint = new int[] { -1, -1 };
        this.mDragHoverHandler = new Handler();
        this.mDragChanged = false;
        this.mShadowIsAnimating = false;
        this.setSaveEnabled(true);
        if (!this.isInEditMode()) {
            this.mVibrator = (Vibrator)context.getSystemService(Service.VIBRATOR_SERVICE);
        }
        this.mCellUsed = new boolean[this.getRowCount() * this.getColumnCount()];
        this.setLayerType(2, (Paint)null);
        this.mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.notificationsEnabled = this.mPreferences.getBoolean("notifications", Const.Defaults.getBoolean("notifications"));
        this.mInflater = LayoutInflater.from(context);
    }
    
    @Nullable
    private Point addView(@NonNull final View view, final DrawerObject drawerObject) {
        Point firstFreeCell = new Point(drawerObject.mGridPosition.row, drawerObject.mGridPosition.col);
        if (drawerObject.mGridPosition.row == Integer.MIN_VALUE || drawerObject.mGridPosition.col == Integer.MIN_VALUE) {
            firstFreeCell = this.findFirstFreeCell(drawerObject.mGridPosition.rowSpan, drawerObject.mGridPosition.colSpan);
            drawerObject.mGridPosition.row = firstFreeCell.x;
            drawerObject.mGridPosition.col = firstFreeCell.y;
        }
        int n = 0;
        for (int row = drawerObject.mGridPosition.row; row < drawerObject.mGridPosition.row + drawerObject.mGridPosition.rowSpan && row < this.getRowCount(); ++row) {
            for (int col = drawerObject.mGridPosition.col; col < drawerObject.mGridPosition.col + drawerObject.mGridPosition.colSpan && col < this.getColumnCount(); ++col) {
                if (n != 0 || this.mCellUsed[this.getColumnCount() * row + col]) {
                    n = 1;
                }
                else {
                    n = 0;
                }
            }
        }
        if (n == 0) {
            this.occupyCells(drawerObject.mGridPosition.row, drawerObject.mGridPosition.col, drawerObject.mGridPosition.rowSpan, drawerObject.mGridPosition.colSpan);
            final GridLayoutParams gridLayoutParams = new GridLayoutParams(drawerObject.mGridPosition.row, drawerObject.mGridPosition.col, drawerObject.mGridPosition.rowSpan, drawerObject.mGridPosition.colSpan, drawerObject);
            (gridLayoutParams).setGravity(119);
            gridLayoutParams.width = this.getColumnWidth() * drawerObject.mGridPosition.colSpan;
            gridLayoutParams.height = this.getRowHeight() * drawerObject.mGridPosition.rowSpan;
            view.setSaveEnabled(true);
            view.setSaveFromParentEnabled(true);
            view.setOnLongClickListener(this);

            boolean isShortcut = false;
            if (view instanceof AppIconView && view.getTag() != null && view.getTag() instanceof Intent) {
                Log.v(TAG, "method:addView app=" + ((AppIconView) view).getText() + " should be shortcut, set listener to shortcut.");
                if (drawerObject instanceof Shortcut) {
                    view.setOnClickListener(((LauncherActivity)this.getContext()).mShortcutClickListener);
                    isShortcut = true;
                }
            }
            if (view instanceof AppIconView) {
                LauncherUtils.colorAppIconView((AppIconView)view, this.getContext());
            }
            try {
                super.addView(view, gridLayoutParams);
                if (view instanceof ClickableAppWidgetHostView) {
                    ((ClickableAppWidgetHostView)view).updateAppWidgetSize(null, this.getColumnWidth(), this.getRowHeight(), this.getColumnWidth() * this.getColumnCount(), this.getRowHeight() * this.getRowCount());
                }

                if(!isShortcut) {
                    view.setOnClickListener(((LauncherActivity) this.getContext()).mAppClickListener);
                }
                return firstFreeCell;
            }
            catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return null;
    }
    
    private void addViewAnimated(@NonNull final View view, final DrawerObject drawerObject) {
        this.addViewAnimated(view, drawerObject, 0.0f, (Interpolator)new OvershootInterpolator(), 200);
    }
    
    private void addViewAnimated(@NonNull final View view, final DrawerObject drawerObject, final float n, final Interpolator interpolator, final int n2) {
        this.addViewAnimated(view, drawerObject, n, interpolator, n2, new Runnable() {
            @Override
            public void run() {
            }
        });
    }
    
    private void addViewAnimated(@NonNull final View view, final DrawerObject drawerObject, final float n, final Interpolator interpolator, int counterOverlay, final Runnable runnable) {
        runnable.run();
        this.addView(view, drawerObject);
        final GridLayoutParams layoutParams = (GridLayoutParams)view.getLayoutParams();
        if (layoutParams == null) {
            this.removeView(view);
        }
        else {
            view.setVisibility(View.INVISIBLE);
            layoutParams.height = this.getMeasuredHeight() / this.getRowCount() * drawerObject.mGridPosition.rowSpan;
            layoutParams.width = this.getMeasuredWidth() / this.getColumnCount() * drawerObject.mGridPosition.colSpan;
            view.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            view.setScaleX(n);
            view.setScaleY(n);
            view.setVisibility(View.VISIBLE);
            view.animate().scaleY(1.0f).scaleX(1.0f).setDuration((long)counterOverlay).setInterpolator((TimeInterpolator)interpolator);
            if (view instanceof AppIconView) {
                final boolean boolean1 = this.mPreferences.getBoolean("showLabels", Const.Defaults.getBoolean("showLabels"));
                if (drawerObject instanceof Application && this.notificationsEnabled && ((LauncherActivity)this.getContext()).mStatusBarNotifications.contains(((Application)drawerObject).info.getComponentName().getPackageName())) {
                    final int n2 = ((LauncherActivity)this.getContext()).mStatusBarNotificationCounts[((LauncherActivity)this.getContext()).mStatusBarNotifications.indexOf(((Application)drawerObject).info.getComponentName().getPackageName())];
                    if ((counterOverlay = n2) == 0) {
                        counterOverlay = n2 + 1;
                    }
                    ((AppIconView)view).setCounterOverlay(counterOverlay);
                }
                else {
                    ((AppIconView)view).removeOverlay();
                }
                ((AppIconView)view).setLabelVisibility(boolean1);
                LauncherUtils.colorAppIconView((AppIconView)view, this.getContext());
            }
        }
    }
    
    @Nullable
    private Point addViewFromViewData(final DrawerObject drawerObject, final boolean b) {
        if (this.mInflater == null) {
            this.mInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (drawerObject == null) {
            return null;
        }
        drawerObject.createView(this, this.mInflater, new DrawerObject.OnViewCreatedListener() {
            @Override
            public void onViewCreated(final View view) {
                if (view instanceof AppIconView) {
                    if (drawerObject instanceof Application && CustomGridLayout.this.notificationsEnabled && ((LauncherActivity)CustomGridLayout.this.getContext()).mStatusBarNotifications.contains(((Application)drawerObject).info.getComponentName().getPackageName())) {
                        final int n = ((LauncherActivity)CustomGridLayout.this.getContext()).mStatusBarNotificationCounts[((LauncherActivity)CustomGridLayout.this.getContext()).mStatusBarNotifications.indexOf(((Application)drawerObject).info.getComponentName().getPackageName())];
                        int counterOverlay;
                        if ((counterOverlay = n) == 0) {
                            counterOverlay = n + 1;
                        }
                        ((AppIconView)view).setCounterOverlay(counterOverlay);
                    }
                    else {
                        ((AppIconView)view).removeOverlay();
                    }
                    ((AppIconView)view).setLabelVisibility(b);
                    LauncherUtils.colorAppIconView((AppIconView)view, CustomGridLayout.this.getContext());
                }
                if (view != null) {
                    if (view.getParent() != null) {
                        ((ViewGroup)view.getParent()).removeView(view);
                    }
                    final Point access1400 = CustomGridLayout.this.addView(view, drawerObject);
                    view.setTranslationX(0.0f);
                    view.setTranslationY(0.0f);
                    if (drawerObject instanceof Widget && access1400 == null) {
                        ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(CustomGridLayout.this.mFolderPage)).pages.get(CustomGridLayout.this.mAppPage).items.remove(drawerObject);
                    }
                    return;
                }
                ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(CustomGridLayout.this.mFolderPage)).pages.get(CustomGridLayout.this.mAppPage).items.remove(drawerObject);
            }
        });
        return this.isPointValid(drawerObject);
    }
    
    private void addViewOver(@NonNull final View view, int n, final int n2, final int n3, final int n4, final float n5) {
        final FrameLayout.LayoutParams frameLayoutLayoutParams = new FrameLayout.LayoutParams((int)(this.getColumnWidth() * n4 * n5), (int)(this.getRowHeight() * n3 * n5));
        this.mDragSurface.removeView(view);
        this.mDragSurface.addView(view, (ViewGroup.LayoutParams)frameLayoutLayoutParams);
        final int[] array = new int[2];
        this.getLocationInWindow(array);
        n = (int)(this.getRowHeight() * n * n5 + array[1]);
        view.setX((float)(int)(this.getColumnWidth() * n2 * n5 + array[0]));
        view.setY((float)n);
        view.setTranslationZ(0.0f);
    }
    
    private void addViewTmp(@NonNull final View view, final int n, final int n2, final int n3, final int n4, final DrawerObject drawerObject) {
        this.occupyCellsTemporarily(n, n2, n3, n4);
        final GridLayoutParams gridLayoutParams = new GridLayoutParams(n, n2, n3, n4, drawerObject);
        ((LayoutParams)gridLayoutParams).setGravity(119);
        gridLayoutParams.width = this.getColumnWidth() * n4;
        gridLayoutParams.height = this.getRowHeight() * n3;
        super.addView(view, (ViewGroup.LayoutParams)gridLayoutParams);
    }
    
    private void addViewsFromViewData(final FolderStructure.ViewDataArrayList list) {
        final boolean boolean1 = this.mPreferences.getBoolean("showLabels", Const.Defaults.getBoolean("showLabels"));
        for (int i = 0; i < list.size(); ++i) {
            this.addViewFromViewData(list.get(i), boolean1);
        }
    }
    
    private View createResizeHandle() {
        final ImageView imageView = new ImageView(this.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageDrawable(this.getContext().getDrawable(R.drawable.resize_handle));
        return (View)imageView;
    }
    
    private Cell findNearestEmptyCellForSpannedChild(final int n, final int n2, int n3, final int n4) {
        Cell cell;
        if (!this.isCellGridUsed(n, n2, n3, n4)) {
            cell = new Cell(n, n2);
        }
        else {
            final ArrayList<Cell> list = new ArrayList<Cell>();
            for (int i = 0; i < this.getRowCount(); ++i) {
                for (int j = 0; j < this.getColumnWidth(); ++j) {
                    if (!this.isCellGridUsed(i, j, n3, n4)) {
                        list.add(new Cell(i, j));
                    }
                }
            }
            if (list.size() == 0) {
                return new Cell(-1, -1);
            }
            float distanceBetweenPoints = this.getDistanceBetweenPoints(n, n2, list.get(0).row, list.get(0).column);
            Cell cell2 = list.get(0);
            n3 = 1;
            while (true) {
                cell = cell2;
                if (n3 >= list.size()) {
                    break;
                }
                final float distanceBetweenPoints2 = this.getDistanceBetweenPoints(n, n2, list.get(n3).row, list.get(n3).column);
                float n5 = distanceBetweenPoints;
                if (distanceBetweenPoints2 < distanceBetweenPoints) {
                    n5 = distanceBetweenPoints2;
                    cell2 = list.get(n3);
                }
                ++n3;
                distanceBetweenPoints = n5;
            }
        }
        return cell;
    }
    
    private void freeCellImpl(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (n >= 0 && n2 >= 0) {
            for (int i = n; i < Math.min(n + n3, n5); ++i) {
                for (int j = n2; j < Math.min(n2 + n4, n6); ++j) {
                    this.mCellUsed[this.getColumnCount() * i + j] = false;
                }
            }
        }
    }
    
    private boolean freeGrid(final int n, final int n2, final int n3, final int n4) {
        this.mCellUsedTemp = this.mCellUsed.clone();
        for (int i = n; i < Math.min(n + n3, this.getRowCount()); ++i) {
            int j = n2;
            while (j < Math.min(n2 + n4, this.getColumnCount())) {
                try {
                    final View child = this.getChildAt(i, j);
                    if (child != null && !this.mMovedViewList.contains(child)) {
                        this.occupyCellsTemporarily(n, n2, n3, n4);
                        final Cell moveChildToNextEmptyPlace = this.moveChildToNextEmptyPlace(i, j, n, n2, n3, n4);
                        this.occupyCellsTemporarily(n, n2, n3, n4);
                        final CellSpan childSpan = this.getChildSpan(child);
                        this.mMovedViewTargetCells.add(moveChildToNextEmptyPlace);
                        this.mMovedViewSpans.add(childSpan);
                        this.mMovedViewList.add(child);
                    }
                    ++j;
                    continue;
                }
                catch (ArrayIndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                    return false;
                }
                //break;
            }
        }
        return true;
    }
    
    private void freeTemporaryCells(final int n, final int n2, final int n3, final int n4) {
        if (this.mCellUsedTemp == null) {
            this.mCellUsedTemp = new boolean[this.mCellUsed.length];
        }
        else {
            for (int i = n; i < Math.min(n + n3, this.getRowCount()); ++i) {
                for (int j = n2; j < Math.min(n2 + n4, this.getColumnCount()); ++j) {
                    this.mCellUsedTemp[this.getColumnCount() * i + j] = false;
                }
            }
        }
    }
    
    private Point getCellCoordinates(final int n, final int n2) {
        return new Point(this.getColumnWidth() * n2, this.getRowHeight() * n);
    }
    
    @Nullable
    private View getChildAt(final int n, final int n2) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            final View child = this.getChildAt(i);
            final GridLayoutParams gridLayoutParams = (GridLayoutParams)child.getLayoutParams();
            if (new Rect(gridLayoutParams.col, gridLayoutParams.row, gridLayoutParams.col + gridLayoutParams.colSpan, gridLayoutParams.row + gridLayoutParams.rowSpan).contains(n2, n)) {
                return child;
            }
        }
        return null;
    }
    
    private CellSpan getChildSpan(final int n, final int n2) {
        return this.getChildSpan(this.getChildAt(n, n2));
    }
    
    private float getDistanceBetweenPoints(final int n, final int n2, final int n3, final int n4) {
        return (float)Math.sqrt((n3 - n) * (n3 - n) + (n4 - n2) * (n4 - n2));
    }
    
    private Point getLocationInWindow() {
        final int[] array = new int[2];
        super.getLocationInWindow(array);
        return new Point(array[0], array[1]);
    }
    
    private int getMaxItemCount() {
        return this.getColumnCount() * this.getRowCount();
    }
    
    private int getUniqueID() {
        final Random random = new Random(System.currentTimeMillis());
        int nextInt;
        do {
            nextInt = random.nextInt(Integer.MAX_VALUE);
        } while (this.getRootView().findViewById(nextInt) != null);
        return nextInt;
    }
    
    private void hideResizeGrips() {
        this.mResizeGrips.animate().alpha(0.0f).withEndAction((Runnable)new Runnable() {
            @Override
            public void run() {
                CustomGridLayout.this.mDragSurface.removeView((View)CustomGridLayout.this.mResizeGrips);
                CustomGridLayout.this.mResizeGrips = null;
                JsonHelper.saveFolderStructure(CustomGridLayout.this.getContext(), LauncherActivity.mFolderStructure);
            }
        });
    }
    
    private boolean isCellGridUsed(final int n, final int n2, final int n3, final int n4) {
        boolean b = false;
        for (int i = n; i < n + n3; ++i) {
            for (int j = n2; j < n2 + n4; ++j) {
                if (i >= this.getRowCount() || j >= this.getColumnCount()) {
                    return true;
                }
                b = (b || this.isTempCellUsed(i, j));
            }
        }
        return b;
    }
    
    private boolean isCellUsed(final int n, final int n2) {
        return this.mCellUsed[this.getColumnCount() * n + n2];
    }
    
    @Nullable
    private Point isPointValid(final DrawerObject drawerObject) {
        Point firstFreeCell = new Point(drawerObject.mGridPosition.row, drawerObject.mGridPosition.col);
        if (drawerObject.mGridPosition.row == Integer.MIN_VALUE || drawerObject.mGridPosition.col == Integer.MIN_VALUE) {
            firstFreeCell = this.findFirstFreeCell(drawerObject.mGridPosition.rowSpan, drawerObject.mGridPosition.colSpan);
            drawerObject.mGridPosition.row = firstFreeCell.x;
            drawerObject.mGridPosition.col = firstFreeCell.y;
        }
        int n = 0;
        for (int i = drawerObject.mGridPosition.row; i < drawerObject.mGridPosition.row + drawerObject.mGridPosition.rowSpan; ++i) {
            int j = drawerObject.mGridPosition.col;
        Label_0174_Outer:
            while (j < drawerObject.mGridPosition.col + drawerObject.mGridPosition.colSpan) {
                while (true) {
                    if (n == 0) {
                        try {
                            if (this.mCellUsed[this.getColumnCount() * i + j]) {
                                n = 1;
                            }
                            else {
                                n = 0;
                            }
                            ++j;
                            continue Label_0174_Outer;
                        }
                        catch (ArrayIndexOutOfBoundsException ex) {
                            ex.printStackTrace();
                            firstFreeCell = null;
                        }
                        return firstFreeCell;
                    }
                    continue;
                }
            }
        }
        if (n == 0) {
            final GridLayoutParams gridLayoutParams = new GridLayoutParams(drawerObject.mGridPosition.row, drawerObject.mGridPosition.col, drawerObject.mGridPosition.rowSpan, drawerObject.mGridPosition.colSpan, drawerObject);
            try {
                if (!this.checkLayoutParams((ViewGroup.LayoutParams)gridLayoutParams)) {
                    return null;
                }
                return firstFreeCell;
            }
            catch (IllegalArgumentException ex2) {
                ex2.printStackTrace();
                return null;
            }
        }
        return null;
    }
    
    private boolean isTempCellUsed(final int n, final int n2) {
        return this.mCellUsedTemp[this.getColumnCount() * n + n2];
    }
    
    @Nullable
    private Cell moveChildToNextEmptyPlace(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final View child = this.getChildAt(n, n2);
        if (child == null) {
            return null;
        }
        final GridLayoutParams gridLayoutParams = (GridLayoutParams)child.getLayoutParams();
        final CellSpan childSpan = this.getChildSpan(n, n2);
        final Cell childCell = this.getChildCell(child);
        this.freeTemporaryCells(childCell.row, childCell.column, childSpan.rowSpan, childSpan.columnSpan);
        this.occupyCellsTemporarily(n3, n4, n5, n6);
        final Cell nearestEmptyCellForSpannedChild = this.findNearestEmptyCellForSpannedChild(n, n2, childSpan.rowSpan, childSpan.columnSpan);
        final Point cellCoordinates = this.getCellCoordinates(nearestEmptyCellForSpannedChild.row, nearestEmptyCellForSpannedChild.column);
        final float n7 = gridLayoutParams.col * this.getColumnWidth();
        final float n8 = gridLayoutParams.row * this.getRowHeight();
        final float n9 = cellCoordinates.x;
        final float n10 = cellCoordinates.y;
        this.occupyCellsTemporarily(nearestEmptyCellForSpannedChild.row, nearestEmptyCellForSpannedChild.column, childSpan.rowSpan, childSpan.columnSpan);
        child.animate().translationX(n9 - n7).translationY(n10 - n8).setDuration(120L);
        return nearestEmptyCellForSpannedChild;
    }
    
    private void occupyCells(final View view) {
        final Cell childCell = this.getChildCell(view);
        final CellSpan childSpan = this.getChildSpan(view);
        final GridLayoutParams gridLayoutParams = (GridLayoutParams)view.getLayoutParams();
        if (gridLayoutParams.viewData != null) {
            gridLayoutParams.viewData.mGridPosition = new DrawerObject.GridPositioning(childCell.row, childCell.column, childSpan.rowSpan, childSpan.columnSpan);
            for (int i = childCell.row; i < childCell.row + childSpan.rowSpan; ++i) {
                for (int j = childCell.column; j < childCell.column + childSpan.columnSpan; ++j) {
                    this.mCellUsed[this.getColumnCount() * i + j] = true;
                }
            }
        }
    }
    
    private void occupyCellsTemporarily(final int n, final int n2, final int n3, final int n4) {
        for (int i = n; i < Math.min(n + n3, this.getRowCount()); ++i) {
            for (int j = n2; j < Math.min(n2 + n4, this.getColumnCount()); ++j) {
                this.mCellUsedTemp[this.getColumnCount() * i + j] = true;
            }
        }
    }
    
    private void redoFreeGrid(final Runnable runnable) {
        this.mCellUsedTemp = this.mCellUsed.clone();
        int n = 0;
        int n2;
        for (int i = 0; i < this.getChildCount(); ++i, n = n2) {
            n2 = n;
            if (!(this.getChildAt(i) instanceof Space)) {
                if (this.getChildAt(i).getTranslationX() == 0.0f) {
                    n2 = n;
                    if (this.getChildAt(i).getTranslationY() == 0.0f) {
                        continue;
                    }
                }
                n2 = 1;
                final ViewPropertyAnimator setDuration = this.getChildAt(i).animate().translationY(0.0f).translationX(0.0f).setDuration(200L);
                Runnable runnable2;
                if (i == 0) {
                    runnable2 = runnable;
                }
                else {
                    runnable2 = new Runnable() {
                        @Override
                        public void run() {
                        }
                    };
                }
                setDuration.withEndAction(runnable2);
            }
        }
        for (int j = 0; j < this.getChildCount(); ++j) {
            final View child = this.getChildAt(j);
            if (!(child instanceof Space)) {
                this.occupyCells(child);
            }
        }
        this.mMovedViewList.clear();
        this.mMovedViewSpans.clear();
        this.mMovedViewTargetCells.clear();
        if (n == 0) {
            runnable.run();
        }
    }
    
    private void removeViewAt(final int n, final int n2) {
        this.removeView(this.getChildAt(n, n2));
        this.requestLayout();
        this.invalidate();
    }
    
    private void transformObject(final DrawerObject drawerObject, final DrawerObject drawerObject2) {
        if (!drawerObject.equals(drawerObject2)) {
            final View child = this.getChildAt(drawerObject.mGridPosition.row, drawerObject.mGridPosition.col);
            if (child != null) {
                final Point cellCoordinates = this.getCellCoordinates(drawerObject.mGridPosition.row, drawerObject.mGridPosition.col);
                final Point cellCoordinates2 = this.getCellCoordinates(drawerObject2.mGridPosition.row, drawerObject2.mGridPosition.col);
                child.animate().translationX((float)(cellCoordinates2.x - cellCoordinates.x)).translationY((float)(cellCoordinates2.y - cellCoordinates.y)).setDuration(150L).withEndAction((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        final GridLayoutParams layoutParams = (GridLayoutParams)child.getLayoutParams();
                        layoutParams.viewData = drawerObject2;
                        layoutParams.row = drawerObject2.mGridPosition.row;
                        layoutParams.col = drawerObject2.mGridPosition.col;
                        layoutParams.rowSpec = GridLayout.spec(drawerObject2.mGridPosition.row, drawerObject2.mGridPosition.rowSpan);
                        layoutParams.columnSpec = GridLayout.spec(drawerObject2.mGridPosition.col, drawerObject2.mGridPosition.colSpan);
                        child.setTranslationX(0.0f);
                        child.setTranslationY(0.0f);
                        child.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
                    }
                });
            }
        }
    }
    
    public void addObject(@NonNull final View view, final DrawerObject drawerObject) {
        if (drawerObject.mGridPosition.row == Integer.MIN_VALUE || drawerObject.mGridPosition.col == Integer.MIN_VALUE) {
            final Point firstFreeCell = this.findFirstFreeCell(drawerObject.mGridPosition.rowSpan, drawerObject.mGridPosition.colSpan);
            drawerObject.mGridPosition.row = firstFreeCell.x;
            drawerObject.mGridPosition.col = firstFreeCell.y;
        }
        if (this.freeGrid(drawerObject.mGridPosition.row, drawerObject.mGridPosition.col, drawerObject.mGridPosition.rowSpan, drawerObject.mGridPosition.colSpan)) {
            this.fixFreeGrid();
            this.freeCells(drawerObject.mGridPosition.row, drawerObject.mGridPosition.col, drawerObject.mGridPosition.rowSpan, drawerObject.mGridPosition.colSpan);
            this.addViewAnimated(view, drawerObject);
            this.normalizeGrid();
            return;
        }
        this.redoFreeGrid();
    }
    
    public void addObject(final DrawerObject drawerObject, final float n, final Interpolator interpolator, final int n2, final Runnable runnable) {
        if (drawerObject.mGridPosition.row == Integer.MIN_VALUE || drawerObject.mGridPosition.col == Integer.MIN_VALUE) {
            final Point firstFreeCell = this.findFirstFreeCell(drawerObject.mGridPosition.rowSpan, drawerObject.mGridPosition.colSpan);
            drawerObject.mGridPosition.row = firstFreeCell.x;
            drawerObject.mGridPosition.col = firstFreeCell.y;
        }
        if (this.freeGrid(drawerObject.mGridPosition.row, drawerObject.mGridPosition.col, drawerObject.mGridPosition.rowSpan, drawerObject.mGridPosition.colSpan)) {
            this.fixFreeGrid();
            this.freeCells(drawerObject.mGridPosition.row, drawerObject.mGridPosition.col, drawerObject.mGridPosition.rowSpan, drawerObject.mGridPosition.colSpan);
            drawerObject.createView(this, this.mInflater, (DrawerObject.OnViewCreatedListener)new DrawerObject.OnViewCreatedListener() {
                @Override
                public void onViewCreated(final View view) {
                    if (drawerObject instanceof Application) {
                        ((AppIconView)view).setIcon((Drawable)new BitmapDrawable(CustomGridLayout.this.getResources(), ((Application)drawerObject).loadIcon(CustomGridLayout.this.getContext(), ((Application)drawerObject).info)));
                    }
                    view.setTranslationX(0.0f);
                    view.setTranslationY(0.0f);
                    CustomGridLayout.this.addViewAnimated(view, drawerObject, n, interpolator, n2, runnable);
                }
            });
            this.normalizeGrid();
            return;
        }
        this.redoFreeGrid();
    }
    
    public void addView(@Nullable View view, final int n) {
        if (n < this.mCellUsed.length) {
            if (view == null) {
                view = new Space(this.getContext());
            }
            else {
                view.setOnLongClickListener((View.OnLongClickListener)this);
            }
            view.setSaveEnabled(true);
            view.setSaveFromParentEnabled(true);
            view.setId(this.getUniqueID());
            super.addView(view, n);
        }
    }
    
    public void cancelLongPress() {
        super.cancelLongPress();
        for (int childCount = this.getChildCount(), i = 0; i < childCount; ++i) {
            this.getChildAt(i).cancelLongPress();
        }
    }
    
    public void checkRemoved() {
        for (int i = 0; i < this.getChildCount(); ++i) {
            final int index = i;
            LauncherActivity.mStaticParallelThreadPool.enqueue(new Runnable() {
                @Override
                public void run() {
                    CustomGridLayout.this.getMainActivity().runOnUiThread((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            final View child = CustomGridLayout.this.getChildAt(index);
                            if (child instanceof AppIconView) {
                                LauncherUtils.colorAppIconView((AppIconView)child, CustomGridLayout.this.getContext());
                            }
                            if (child != null && child.getTag() != null && child.getTag() instanceof Intent && !LauncherUtils.isAvailable(CustomGridLayout.this.getContext(), (Intent)child.getTag())) {
                                CustomGridLayout.this.removeView(child);
                            }
                        }
                    });
                }
            });
        }
    }
    
    public Point findFirstFreeCell(final int n, final int n2) {
        for (int i = 0; i < this.getRowCount(); ++i) {
            for (int j = 0; j < this.getColumnCount(); ++j) {
                if (!this.isCellGridUsedFull(i, j, n, n2)) {
                    return new Point(i, j);
                }
            }
        }
        return new Point(0, 0);
    }
    
    public void fixFreeGrid() {
        if (this.mCellUsedTemp == null) {
            this.mCellUsedTemp = new boolean[this.getColumnCount() * this.getRowCount()];
        }
        for (int i = 0; i < this.mCellUsedTemp.length; ++i) {
            this.mCellUsedTemp[i] = false;
        }
        this.mCellUsed = this.mCellUsedTemp.clone();
        for (int j = 0; j < this.mMovedViewList.size(); ++j) {
            final CellSpan cellSpan = this.mMovedViewSpans.get(j);
            final Cell cell = this.mMovedViewTargetCells.get(j);
            final FolderStructure.ViewDataArrayList items = ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(this.mFolderPage)).pages.get(this.mAppPage).items;
            final DrawerObject viewData = ((GridLayoutParams)this.mMovedViewList.get(j).getLayoutParams()).viewData;
            if (viewData.mGridPosition != null) {
                items.remove(viewData);
                viewData.mGridPosition.set(cell, cellSpan);
                ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(this.mFolderPage)).pages.get(this.mAppPage).items.add(viewData);
                final GridLayoutParams layoutParams = new GridLayoutParams(cell, cellSpan, viewData);
                layoutParams.height = this.getMeasuredHeight() / this.getRowCount() * cellSpan.rowSpan;
                layoutParams.width = this.getMeasuredWidth() / this.getColumnCount() * cellSpan.columnSpan;
                this.mMovedViewList.get(j).setLayoutParams((ViewGroup.LayoutParams)layoutParams);
                this.mMovedViewList.get(j).setTranslationX(0.0f);
                this.mMovedViewList.get(j).setTranslationY(0.0f);
            }
        }
        for (int k = 0; k < this.mMovedViewList.size(); ++k) {
            this.mMovedViewList.get(k).animate().translationY(0.0f).translationX(0.0f);
        }
        this.mMovedViewList.clear();
        this.mMovedViewSpans.clear();
        this.mMovedViewTargetCells.clear();
        for (int l = 0; l < this.getChildCount(); ++l) {
            final View child = this.getChildAt(l);
            if (!(child instanceof Space)) {
                this.occupyCells(child);
            }
        }
    }
    
    public void freeCells(final int n, final int n2, final int n3, final int n4) {
        this.freeCellImpl(n, n2, n3, n4, this.getRowCount(), this.getColumnCount());
    }
    
    public AppWidgetContainer getAppWidgetContainer() {
        return this.mAppWidgetContainer;
    }
    
    public Cell getChildCell(final View view) {
        if (view == null || !(view.getLayoutParams() instanceof GridLayoutParams)) {
            return new Cell(-1, -1);
        }
        final GridLayoutParams gridLayoutParams = (GridLayoutParams)view.getLayoutParams();
        return new Cell(gridLayoutParams.row, gridLayoutParams.col);
    }
    
    public CellSpan getChildSpan(final View view) {
        if (view == null || !(view.getLayoutParams() instanceof GridLayoutParams)) {
            return new CellSpan(-1, -1);
        }
        final GridLayoutParams gridLayoutParams = (GridLayoutParams)view.getLayoutParams();
        return new CellSpan(gridLayoutParams.rowSpan, gridLayoutParams.colSpan);
    }
    
    @Override
    public int getColumnCount() {
        int columnCount;
        if ((columnCount = super.getColumnCount()) <= 0) {
            columnCount = 4;
        }
        return columnCount;
    }
    
    public int getColumnWidth() {
        return this.getMeasuredWidth() / this.getColumnCount();
    }
    
    public int getGridType() {
        return this.mGridType;
    }
    
    public LauncherActivity getMainActivity() {
        if (this.getContext() instanceof LauncherActivity) {
            return (LauncherActivity)this.getContext();
        }
        throw new RuntimeException("This GridLayout can only exist attached to a LauncherActivity.");
    }
    
    @Override
    public int getRowCount() {
        int rowCount;
        if ((rowCount = super.getRowCount()) <= 0) {
            rowCount = 5;
        }
        return rowCount;
    }
    
    public int getRowHeight() {
        return this.getMeasuredHeight() / this.getRowCount();
    }
    
    public void hoverAt(final int n, final int n2, final int n3, final int n4) {
        int n5 = 0;
        if ((this.hoverPoint[0] == n3 && this.hoverPoint[1] == n4) || this.mGridType == 1) {
            return;
        }
        LauncherLog.d("CGridLayout", "Dragging at " + n3 + ", " + n4);
        this.mDragChanged = false;
        this.hoverPoint[0] = n3;
        this.hoverPoint[1] = n4;
        this.freeCells(n, n2, this.mDragData.mGridPosition.rowSpan, this.mDragData.mGridPosition.colSpan);
        this.mDragHoverHandler.removeCallbacksAndMessages((Object)null);
        this.redoFreeGrid();
        if (this.mDragShadowView == null) {
            this.mDragShadowView = new ImageView(this.getContext());
        }
        if (!this.mShadowIsAnimating) {
            this.mShadowIsAnimating = true;
            this.mDragShadowView.animate().scaleY(0.0f).scaleX(0.0f).alpha(0.0f).setDuration(100L).withEndAction((Runnable)new Runnable() {
                @Override
                public void run() {
                    CustomGridLayout.this.mDragSurface.removeView((View)CustomGridLayout.this.mDragShadowView);
                    CustomGridLayout.this.mShadowIsAnimating = false;
                }
            });
        }
        if (this.isCellGridUsedFull(n3, n4, this.mDragData.mGridPosition.rowSpan, this.mDragData.mGridPosition.colSpan)) {
            n5 = 300;
        }
        this.mDragHoverHandler.postDelayed((Runnable)new Runnable() {
            @Override
            public void run() {
                if (CustomGridLayout.this.mDragView != null) {
                    CustomGridLayout.this.mDragShadow = LauncherUtils.loadBitmapFromView(CustomGridLayout.this.mDragView);
                    CustomGridLayout.this.mDragShadowView.setImageDrawable((Drawable)new BitmapDrawable(CustomGridLayout.this.getResources(), CustomGridLayout.this.mDragShadow));
                    if (CustomGridLayout.this.mDragData == null || !CustomGridLayout.this.freeGrid(n3, n4, CustomGridLayout.this.mDragData.mGridPosition.rowSpan, CustomGridLayout.this.mDragData.mGridPosition.colSpan)) {
                        CustomGridLayout.this.hoverPoint[0] = n;
                        CustomGridLayout.this.hoverPoint[1] = n2;
                        return;
                    }
                    CustomGridLayout.this.mDragShadowView.setImageTintList(ColorStateList.valueOf(15108398));
                    CustomGridLayout.this.mDragChanged = true;
                    final DrawerObject mDragData = CustomGridLayout.this.mDragData;
                    CustomGridLayout.this.addViewOver((View)CustomGridLayout.this.mDragShadowView, n3, n4, mDragData.mGridPosition.rowSpan, mDragData.mGridPosition.colSpan, 0.0f);
                    if (CustomGridLayout.this.mDragShadowView != null) {
                        CustomGridLayout.this.mDragShadowView.post((Runnable)new Runnable() {
                            @Override
                            public void run() {
                                CustomGridLayout.this.mDragShadowView.animate().setDuration(100L).scaleY(0.9f).scaleX(0.9f).alpha(0.5f);
                            }
                        });
                    }
                }
            }
        }, (long)n5);
    }
    
    public AppIconView inflateIcon() {
        return (AppIconView)this.mInflater.inflate(R.layout.icon, (ViewGroup)this, false);
    }
    
    public boolean isCellGridUsedFull(final int n, final int n2, final int n3, final int n4) {
        boolean b = false;
        for (int i = n; i < n + n3; ++i) {
            for (int j = n2; j < n2 + n4; ++j) {
                if (i >= this.getRowCount() || j >= this.getColumnCount()) {
                    return true;
                }
                b = (b || this.isCellUsed(i, j));
            }
        }
        return b;
    }
    
    public void normalizeGrid() {
        for (int i = 0; i < this.getChildCount(); ++i) {
            final View child = this.getChildAt(i);
            if (!(child instanceof Space)) {
                this.occupyCells(child);
            }
        }
    }
    
    public void notifyDataSetChanged(final boolean b) {
        if (!b) {
            this.removeAllViews();
            this.addViewsFromViewData(((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(this.mFolderPage)).pages.get(this.mAppPage).items);
            return;
        }
        final FolderStructure.ViewDataArrayList list = new FolderStructure.ViewDataArrayList();
        final FolderStructure.ViewDataArrayList items = ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(Math.min(this.mFolderPage, LauncherActivity.mFolderStructure.folders.size() - 1))).pages.get(Math.min(this.mAppPage, ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(Math.min(this.mFolderPage, LauncherActivity.mFolderStructure.folders.size() - 1))).pages.size() - 1)).items;
        final ArrayList<View> list2 = new ArrayList<View>();
        for (int i = 0; i < this.getChildCount(); ++i) {
            list.add(((GridLayoutParams)this.getChildAt(i).getLayoutParams()).viewData);
            if (!items.containsNotPosition(((GridLayoutParams)this.getChildAt(i).getLayoutParams()).viewData)) {
                list2.add(this.getChildAt(i));
            }
        }
        for (int j = 0; j < list2.size(); ++j) {
            this.removeView((View)list2.get(j));
        }
        for (final DrawerObject drawerObject : items) {
            if (list.containsNotPosition(drawerObject)) {
                this.transformObject(list.get(list.indexOfNotPosition(drawerObject)), drawerObject);
            }
            else {
                this.addViewFromViewData(drawerObject, this.mPreferences.getBoolean("showLabels", Const.Defaults.getBoolean("showLabels")));
            }
        }
        this.normalizeGrid();
    }
    
    public void occupyCells(final int n, final int n2, final int n3, final int n4) {
        for (int i = n; i < n + n3; ++i) {
            for (int j = n2; j < n2 + n4; ++j) {
                this.mCellUsed[this.getColumnCount() * i + j] = true;
            }
        }
    }
    
    @Override
    protected void onLayout(final boolean b, int i, int childCount, int n, int n2) {
        View child;
        GridLayoutParams gridLayoutParams;
        int n3;
        int n4;
        for (childCount = this.getChildCount(), i = 0; i < childCount; ++i) {
            child = this.getChildAt(i);
            gridLayoutParams = (GridLayoutParams)child.getLayoutParams();
            n = gridLayoutParams.col * this.getColumnWidth();
            n2 = gridLayoutParams.row * this.getRowHeight();
            n3 = gridLayoutParams.colSpan * this.getColumnWidth();
            n4 = gridLayoutParams.rowSpan * this.getRowHeight();
            if (n3 != child.getMeasuredWidth() || n4 != child.getMeasuredHeight()) {
                child.measure(View.MeasureSpec.makeMeasureSpec(n3, MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(n4, MeasureSpec.EXACTLY));
            }
            child.layout(n, n2, n + n3, n2 + n4);
        }
    }
    
    public boolean onLongClick(final View mDragView) {
        LauncherLog.d("CGridLayout", "LongClicked " + (mDragView instanceof ClickableAppWidgetHostView && !((ClickableAppWidgetHostView)mDragView).mLongPressHelper.isCanLongPress()));
        if ((mDragView instanceof ClickableAppWidgetHostView && !((ClickableAppWidgetHostView)mDragView).mLongPressHelper.isCanLongPress()) || (mDragView instanceof AppIconView && !((AppIconView)mDragView).mLongPressHelper.isCanLongPress())) {
            return false;
        }
        CheckForLongPressHelper checkForLongPressHelper;
        if (mDragView instanceof ClickableAppWidgetHostView) {
            checkForLongPressHelper = ((ClickableAppWidgetHostView)mDragView).mLongPressHelper;
        }
        else {
            checkForLongPressHelper = ((AppIconView)mDragView).mLongPressHelper;
        }
        final SoftReference softReference = new SoftReference<CheckForLongPressHelper>(checkForLongPressHelper);
        this.mDragView = mDragView;
        this.mDragSurface.overrideDispatch(true);
        this.size = new int[] { this.getMeasuredWidth(), this.getMeasuredHeight() };
        this.mDragSurface.setOnDragListener((View.OnDragListener)this.mDragSurface);
        this.mDragSurface.setFocussedPagerGrid(this);
        this.mDragSurface.setInitialAppGrid(this);
        this.mDragShadow = LauncherUtils.loadBitmapFromView(mDragView);
        final Cell childCell = this.getChildCell(mDragView);
        final CellSpan childSpan = this.getChildSpan(mDragView);
        final Point cellCoordinates = this.getCellCoordinates(childCell.row, childCell.column);
        this.freeCells(childCell.row, childCell.column, childSpan.rowSpan, childSpan.columnSpan);
        final int[] array = new int[2];
        this.pos = this.getLocationInWindow();
        mDragView.getLocationInWindow(array);
        if (!(mDragView.getLayoutParams() instanceof GridLayoutParams)) {
            return false;
        }
        final GridLayoutParams gridLayoutParams = (GridLayoutParams)mDragView.getLayoutParams();
        if (gridLayoutParams.viewData != null) {
            this.mDragData = gridLayoutParams.viewData;
        }
        this.mDragStartIndex = this.indexOfChild(mDragView);
        this.removeView(mDragView);
        this.mDragView.measure(0, 0);
        final GridLayoutParams gridLayoutParams2 = (GridLayoutParams)this.mDragView.getLayoutParams();
        this.mDragSurface.addView(this.mDragView, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(gridLayoutParams2.colSpan * this.getColumnWidth(), gridLayoutParams2.rowSpan * this.getRowHeight()));
        this.mDragView.setTranslationX(0.0f);
        this.mDragView.setTranslationY(0.0f);
        CheckForLongPressHelper cfph = (CheckForLongPressHelper)softReference.get();
        this.mDragClickPoint = cfph.getLongPressPosition();
        final PointF mDragClickPoint = this.mDragClickPoint;
        mDragClickPoint.x -= 0.5f * gridLayoutParams2.colSpan * this.getColumnWidth();
        final PointF mDragClickPoint2 = this.mDragClickPoint;
        mDragClickPoint2.y -= 0.5f * gridLayoutParams2.rowSpan * this.getRowHeight();
        this.mDragView.setX((float)(childCell.x() * this.getColumnWidth() * 2 + this.pos.x));
        this.mDragView.setY((float)(childCell.y() * this.getRowHeight() * 2 + this.pos.y));
        this.mDragStartPos = new int[] { this.pos.x + cellCoordinates.x, this.pos.y + cellCoordinates.y };
        this.mHasDragged = true;
        this.mDragSurface.setPager(this.mPager);
        if (this.mDragView == null) {
            return false;
        }
        this.hLocY = childCell.y();
        this.hLocX = childCell.x();
        LauncherLog.d("CGridLayout", "Long pressed at: " + this.mDragClickPoint.toString());
        this.mDragView.post((Runnable)new Runnable() {
            @Override
            public void run() {
                CustomGridLayout.this.mDragSurface.startDrag(0);
                CustomGridLayout.this.mDragView.animate().setDuration(100L).scaleX(1.07f).scaleY(1.07f).translationZ(48.0f).alpha(1.0f).withEndAction((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        CustomGridLayout.this.hoverPoint[0] = Integer.MIN_VALUE;
                        CustomGridLayout.this.hoverPoint[1] = Integer.MIN_VALUE;
                        if (CustomGridLayout.this.mHasDragged || CustomGridLayout.this.mPager == null || CustomGridLayout.this.mPager.getAdapter() == null || !((AppDrawerPagerAdapter)CustomGridLayout.this.mPager.getAdapter()).mCanDragItems) {
                            CustomGridLayout.this.mDragSurface.removeAllViews();
                            CustomGridLayout.this.redoFreeGrid();
                            if (CustomGridLayout.this.mDragData != null) {
                                CustomGridLayout.this.addObject(CustomGridLayout.this.mDragData, 1.07f, (Interpolator)new DecelerateInterpolator(), 50, new Runnable() {
                                    @Override
                                    public void run() {
                                    }
                                });
                            }
                            CustomGridLayout.this.mDragView = null;
                            CustomGridLayout.this.hoverPoint[0] = -1;
                            CustomGridLayout.this.hoverPoint[1] = -1;
                            CustomGridLayout.this.mDragShadowView = null;
                        }
                    }
                });
            }
        });
        return false;
    }
    
    protected void onRestoreInstanceState(final Parcelable parcelable) {
        if (!(parcelable instanceof View.BaseSavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        if (!(parcelable instanceof GridState)) {
            super.onRestoreInstanceState(((View.BaseSavedState)parcelable).getSuperState());
            return;
        }
        this.notificationsEnabled = this.mPreferences.getBoolean("notifications", Const.Defaults.getBoolean("notifications"));
        final GridState gridState = (GridState)parcelable;
        super.onRestoreInstanceState(gridState.getSuperState());
        this.mCellUsed = new boolean[this.getRowCount() * this.getColumnCount()];
        this.mCellUsedTemp = new boolean[this.getRowCount() * this.getColumnCount()];
        this.mFolderPage = gridState.folderPage;
        this.mAppPage = gridState.appPage;
        this.removeAllViews();
        this.refresh();
    }
    
    protected Parcelable onSaveInstanceState() {
        return (Parcelable)new GridState(super.onSaveInstanceState(), this.mFolderPage, this.mAppPage);
    }
    
    public void populate(final List<? extends DrawerObject> list) {
        final boolean boolean1 = this.mPreferences.getBoolean("showLabels", Const.Defaults.getBoolean("showLabels"));
        for (int i = 0; i < Math.min(this.getRowCount() * this.getColumnCount(), list.size()); ++i) {
            final Point addViewFromViewData = this.addViewFromViewData((DrawerObject)list.get(i), boolean1);
            if (addViewFromViewData != null) {
                ((DrawerObject)list.get(i)).mGridPosition.row = addViewFromViewData.x;
                ((DrawerObject)list.get(i)).mGridPosition.col = addViewFromViewData.y;
            }
        }
    }
    
    public void redoFreeGrid() {
        this.redoFreeGrid(new Runnable() {
            @Override
            public void run() {
            }
        });
    }
    
    public void refresh() {
        FolderStructure.ViewDataArrayList items = new FolderStructure.ViewDataArrayList();
        if (LauncherActivity.mFolderStructure != null) {
            if (((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(this.mFolderPage)).pages.size() > this.mAppPage) {
                items = ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(this.mFolderPage)).pages.get(this.mAppPage).items;
            }
            final FolderStructure.ViewDataArrayList myItems = items;
            final int gridWidth = ((LauncherActivity)this.getContext()).mHolder.gridWidth;
            final int gridHeight = ((LauncherActivity)this.getContext()).mHolder.gridHeight;
            final boolean boolean1 = this.mPreferences.getBoolean("showLabels", true);
            for (int i = 0; i < items.size(); ++i) {
                final int index = i;
                LauncherActivity.mStaticParallelThreadPool.enqueue(new Runnable() {
                    final /* synthetic */ DrawerObject valobject = myItems.get(index);
                    
                    @Override
                    public void run() {
                        if (this.valobject.mGridPosition.row + this.valobject.mGridPosition.rowSpan <= gridHeight && this.valobject.mGridPosition.col + this.valobject.mGridPosition.colSpan <= gridWidth) {
                            CustomGridLayout.this.getMainActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CustomGridLayout.this.addViewFromViewData(valobject, boolean1);
                                }
                            });
                            return;
                        }
                        (LauncherActivity.mFolderStructure.folders.get(CustomGridLayout.this.mFolderPage)).pages.get(CustomGridLayout.this.mAppPage).items.remove(this.valobject);
                    }
                });
            }
        }
    }
    
    public void setAppWidgetContainer(final AppWidgetContainer mAppWidgetContainer) {
        this.mAppWidgetContainer = mAppWidgetContainer;
    }
    
    @Override
    public void setColumnCount(int i) {
        int columnCount = i;
        if (i <= 0) {
            columnCount = 4;
        }
        final boolean[] array = new boolean[this.getRowCount() * columnCount];
        if (this.mCellUsed == null) {
            this.mCellUsed = array;
        }
        else {
            System.arraycopy(this.mCellUsed, 0, array, 0, Math.min(array.length, this.mCellUsed.length));
        }
        this.mCellUsed = array;
        final int columnCount2 = this.getColumnCount();
        if (columnCount2 > columnCount) {
            int j;
            for (i = columnCount; i < columnCount2; ++i) {
                for (j = 0; j < this.getRowCount(); ++j) {
                    this.removeViewAt(j, i);
                }
            }
        }
        super.setColumnCount(columnCount);
    }
    
    public void setDragSurface(final DragSurfaceLayout mDragSurface) {
        this.mDragSurface = mDragSurface;
    }
    
    public void setGridType(final int mGridType) {
        this.mGridType = mGridType;
    }
    
    public void setPager(final ViewPager mPager) {
        this.mPager = mPager;
    }
    
    public void setPosition(final int mFolderPage, final int mAppPage) {
        this.mFolderPage = mFolderPage;
        this.mAppPage = mAppPage;
    }
    
    @Override
    public void setRowCount(int i) {
        int rowCount = i;
        if (i <= 0) {
            rowCount = 5;
        }
        final boolean[] array = new boolean[this.getColumnCount() * rowCount];
        if (this.mCellUsed == null) {
            this.mCellUsed = array;
        }
        else {
            System.arraycopy(this.mCellUsed, 0, array, 0, Math.min(array.length, this.mCellUsed.length));
        }
        this.mCellUsed = array;
        final int rowCount2 = this.getRowCount();
        if (rowCount2 > rowCount) {
            int j;
            for (i = rowCount; i < rowCount2; ++i) {
                for (j = 0; j < this.getColumnCount(); ++j) {
                    this.removeViewAt(i, j);
                }
            }
        }
        super.setRowCount(rowCount);
    }
    
    public void showResizeGrips(final DrawerObject drawerObject) {
        final AppWidgetProviderInfo appWidgetInfo = this.mAppWidgetContainer.mAppWidgetManager.getAppWidgetInfo(((Widget)drawerObject).widgetId);
        this.mDragSurface.removeView((View)this.mResizeGrips);
        if (appWidgetInfo == null || appWidgetInfo.resizeMode == 0) {
            return;
        }
        final int n = (int)this.getContext().getResources().getDimension(R.dimen.handle);
        final int n2 = (int)this.getContext().getResources().getDimension(R.dimen.handle_margin);
        (this.mResizeGrips = new RelativeLayout(this.getContext())).setBackground((Drawable)new InsetDrawable((Drawable)this.getContext().getDrawable(R.drawable.rectangle), LauncherUtils.dpToPx(20.0f, this.getContext())));
        final Cell cell = drawerObject.mGridPosition.getCell();
        final Point locationInWindow = this.getLocationInWindow();
        final int colSpan = drawerObject.mGridPosition.colSpan;
        final int columnWidth = this.getColumnWidth();
        final int rowSpan = drawerObject.mGridPosition.rowSpan;
        final int rowHeight = this.getRowHeight();
        final int dpToPx = LauncherUtils.dpToPx(48.0f, this.getContext());
        final FrameLayout.LayoutParams frameLayoutLayoutParams = new FrameLayout.LayoutParams(colSpan * columnWidth + n, rowSpan * rowHeight + n);
        this.mResizeGrips.setElevation(48.0f);
        this.mResizeGrips.setX((float)(locationInWindow.x + cell.x() * this.getColumnWidth() - n / 2));
        this.mResizeGrips.setY((float)(locationInWindow.y + cell.y() * this.getRowHeight() - n / 2));
        this.mResizeGrips.setScaleX(1.6f);
        this.mResizeGrips.setScaleY(1.6f);
        this.mResizeGrips.setAlpha(0.0f);
        this.mResizeGrips.setClipToPadding(false);
        this.mResizeGrips.setClipChildren(false);
        final RelativeLayout.LayoutParams relativeLayoutLayoutParams = new RelativeLayout.LayoutParams(dpToPx, dpToPx);
        relativeLayoutLayoutParams.addRule(10, 1);
        relativeLayoutLayoutParams.addRule(14, 1);
        relativeLayoutLayoutParams.setMargins(0, -n2, 0, 0);
        final RelativeLayout.LayoutParams relativeLayoutLayoutParams2 = new RelativeLayout.LayoutParams(dpToPx, dpToPx);
        relativeLayoutLayoutParams2.addRule(12, 1);
        relativeLayoutLayoutParams2.addRule(14, 1);
        relativeLayoutLayoutParams2.setMargins(0, 0, 0, -n2);
        final RelativeLayout.LayoutParams relativeLayoutLayoutParams3 = new RelativeLayout.LayoutParams(dpToPx, dpToPx);
        relativeLayoutLayoutParams3.addRule(9, 1);
        relativeLayoutLayoutParams3.addRule(15, 1);
        relativeLayoutLayoutParams3.setMargins(-n2, 0, 0, 0);
        final RelativeLayout.LayoutParams relativeLayoutLayoutParams4 = new RelativeLayout.LayoutParams(dpToPx, dpToPx);
        relativeLayoutLayoutParams4.addRule(11, 1);
        relativeLayoutLayoutParams4.addRule(15, 1);
        relativeLayoutLayoutParams4.setMargins(0, 0, -n2, 0);
        final View resizeHandle = this.createResizeHandle();
        resizeHandle.setRotation(180.0f);
        final View resizeHandle2 = this.createResizeHandle();
        final View resizeHandle3 = this.createResizeHandle();
        resizeHandle3.setRotation(90.0f);
        final View resizeHandle4 = this.createResizeHandle();
        resizeHandle4.setRotation(-90.0f);
        this.mResizeGrips.addView(resizeHandle3, (ViewGroup.LayoutParams)relativeLayoutLayoutParams3);
        this.mResizeGrips.addView(resizeHandle4, (ViewGroup.LayoutParams)relativeLayoutLayoutParams4);
        this.mResizeGrips.addView(resizeHandle, (ViewGroup.LayoutParams)relativeLayoutLayoutParams);
        this.mResizeGrips.addView(resizeHandle2, (ViewGroup.LayoutParams)relativeLayoutLayoutParams2);
        resizeHandle.setOnTouchListener((View.OnTouchListener)new View.OnTouchListener() {
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                CustomGridLayout.this.mDragSurface.overrideDispatch(false);
                CustomGridLayout.this.mDragSurface.setOnDragListener((View.OnDragListener)new GripDragListener(drawerObject, Direction.DIRECTION_TOP));
                CustomGridLayout.this.mDragSurface.startDrag(0);
                return false;
            }
        });
        resizeHandle2.setOnTouchListener((View.OnTouchListener)new View.OnTouchListener() {
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                CustomGridLayout.this.mDragSurface.overrideDispatch(false);
                CustomGridLayout.this.mDragSurface.setOnDragListener((View.OnDragListener)new GripDragListener(drawerObject, Direction.DIRECTION_BOTTOM));
                CustomGridLayout.this.mDragSurface.startDrag(0);
                return false;
            }
        });
        resizeHandle3.setOnTouchListener((View.OnTouchListener)new View.OnTouchListener() {
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                CustomGridLayout.this.mDragSurface.overrideDispatch(false);
                CustomGridLayout.this.mDragSurface.setOnDragListener((View.OnDragListener)new GripDragListener(drawerObject, Direction.DIRECTION_LEFT));
                CustomGridLayout.this.mDragSurface.startDrag(0);
                return false;
            }
        });
        resizeHandle4.setOnTouchListener((View.OnTouchListener)new View.OnTouchListener() {
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                CustomGridLayout.this.mDragSurface.overrideDispatch(false);
                CustomGridLayout.this.mDragSurface.setOnDragListener((View.OnDragListener)new GripDragListener(drawerObject, Direction.DIRECTION_RIGHT));
                CustomGridLayout.this.mDragSurface.startDrag(0);
                return false;
            }
        });
        this.mResizeGrips.invalidate();
        this.mDragSurface.addView((View)this.mResizeGrips, (ViewGroup.LayoutParams)frameLayoutLayoutParams);
        this.mResizeGrips.post((Runnable)new Runnable() {
            @Override
            public void run() {
                CustomGridLayout.this.mResizeGrips.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setInterpolator((TimeInterpolator)new OvershootInterpolator());
            }
        });
        this.mDragSurface.setClickable(true);
        this.mDragSurface.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                CustomGridLayout.this.mDragSurface.setOnDragListener(null);
                CustomGridLayout.this.mDragSurface.setOnClickListener((View.OnClickListener)null);
                CustomGridLayout.this.mDragSurface.setClickable(false);
                CustomGridLayout.this.hideResizeGrips();
            }
        });
    }
    
    public String toString() {
        if (this.mCellUsedTemp == null) {
            this.mCellUsedTemp = new boolean[this.getMaxItemCount()];
        }
        int n = 0;
        while (true) {
            String string = "";
            if (n >= this.getRowCount()) {
                break;
            }
            for (int i = 0; i < this.getColumnCount(); ++i) {
                final StringBuilder append = new StringBuilder().append(string);
                int n2;
                if (this.mCellUsed[this.getColumnCount() * n + i]) {
                    n2 = 1;
                }
                else {
                    n2 = 0;
                }
                string = append.append(n2).append(" ").toString();
            }
            ++n;
        }
        int n3 = 0;
        while (true) {
            String string2 = "";
            if (n3 >= this.getRowCount()) {
                break;
            }
            for (int j = 0; j < this.getColumnCount(); ++j) {
                final StringBuilder append2 = new StringBuilder().append(string2);
                int n4;
                if (this.mCellUsedTemp[this.getColumnCount() * n3 + j]) {
                    n4 = 1;
                }
                else {
                    n4 = 0;
                }
                string2 = append2.append(n4).append(" ").toString();
            }
            ++n3;
        }
        return super.toString();
    }
    
    public static class Cell
    {
        public final int column;
        public final int row;
        
        public Cell(final int row, final int column) {
            this.row = row;
            this.column = column;
        }
        
        public int x() {
            return this.column;
        }
        
        public int y() {
            return this.row;
        }
    }
    
    public static class CellSpan
    {
        public final int columnSpan;
        public final int rowSpan;
        
        public CellSpan(final int rowSpan, final int columnSpan) {
            this.rowSpan = rowSpan;
            this.columnSpan = columnSpan;
        }
        
        public int x() {
            return this.columnSpan;
        }
        
        public int y() {
            return this.rowSpan;
        }
    }
    
    private enum Direction
    {
        DIRECTION_BOTTOM, 
        DIRECTION_LEFT, 
        DIRECTION_RIGHT, 
        DIRECTION_TOP;
    }
    
    public class GridLayoutParams extends LayoutParams
    {
        public int col;
        public int colSpan;
        public int row;
        public int rowSpan;
        public DrawerObject viewData;
        
        public GridLayoutParams(final int row, final int col, final int rowSpan, final int colSpan, final DrawerObject viewData) {
            super(GridLayout.spec(row, rowSpan), GridLayout.spec(col, colSpan));
            this.row = row;
            this.col = col;
            this.rowSpan = rowSpan;
            this.colSpan = colSpan;
            this.viewData = viewData;
        }
        
        public GridLayoutParams(final Cell cell, final CellSpan cellSpan, final DrawerObject drawerObject) {
            this(cell.row, cell.column, cellSpan.rowSpan, cellSpan.columnSpan, drawerObject);
        }
    }
    
    public static class GridState extends View.BaseSavedState
    {
        public static final Parcelable.Creator<View.BaseSavedState> CREATOR;
        final int appPage;
        final int folderPage;
        
        static {
            CREATOR = new Parcelable.Creator<View.BaseSavedState>() {
                public View.BaseSavedState createFromParcel(final Parcel parcel) {
                    return new View.BaseSavedState(parcel);
                }
                
                public View.BaseSavedState[] newArray(final int n) {
                    return new View.BaseSavedState[n];
                }
            };
        }
        
        public GridState(final Parcel parcel) {
            super(parcel);
            this.folderPage = parcel.readInt();
            this.appPage = parcel.readInt();
        }
        
        public GridState(final Parcelable parcelable, final int folderPage, final int appPage) {
            super(parcelable);
            this.folderPage = folderPage;
            this.appPage = appPage;
        }
        
        public void writeToParcel(final Parcel parcel, final int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.folderPage);
            parcel.writeInt(this.appPage);
        }
    }
    
    public static class GridType
    {
        public static final int TYPE_APPS_ONLY = 1;
        public static final int TYPE_WIDGETS = 0;
    }
    
    public class GripDragListener implements View.OnDragListener
    {
        int mCellSizeDelta;
        float mCurrentPos;
        int mCurrentSize;
        final Direction mDirection;
        final DrawerObject mStartData;
        float mStartPos;
        float mStartSize;
        final AppWidgetProviderInfo mWidgetInfo;
        
        public GripDragListener(final DrawerObject mStartData, final Direction mDirection) {
            this.mStartData = mStartData;
            this.mWidgetInfo = CustomGridLayout.this.mAppWidgetContainer.mAppWidgetManager.getAppWidgetInfo(((Widget)mStartData).widgetId);
            this.mDirection = mDirection;
        }
        
        public boolean onDrag(View view, final DragEvent dragEvent) {
            final int n = (int)CustomGridLayout.this.getContext().getResources().getDimension(R.dimen.handle_diameter);
            if (this.mDirection == Direction.DIRECTION_TOP || this.mDirection == Direction.DIRECTION_BOTTOM) {
                switch (dragEvent.getAction()) {
                    case 1: {
                        this.mStartPos = CustomGridLayout.this.mResizeGrips.getY();
                        this.mStartSize = CustomGridLayout.this.mResizeGrips.getLayoutParams().height;
                        break;
                    }
                    case 2: {
                        if (CustomGridLayout.this.mResizeGrips == null) {
                            return false;
                        }
                        int mCurrentSize;
                        if (this.mDirection == Direction.DIRECTION_TOP) {
                            mCurrentSize = (int)(this.mStartSize + (this.mStartPos - dragEvent.getY()) + n / 2);
                        }
                        else {
                            mCurrentSize = (int)(dragEvent.getY() - this.mStartPos + n / 2);
                        }
                        this.mCurrentSize = mCurrentSize;
                        if (this.mCurrentSize - n > CustomGridLayout.this.getRowHeight()) {
                            this.mCurrentPos = dragEvent.getY() - n / 2;
                        }
                        else {
                            this.mCurrentSize = CustomGridLayout.this.getRowHeight() + n;
                            this.mCurrentPos = this.mStartPos + this.mStartSize - n - CustomGridLayout.this.getRowHeight();
                        }
                        if (this.mDirection == Direction.DIRECTION_TOP) {
                            CustomGridLayout.this.mResizeGrips.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(CustomGridLayout.this.mResizeGrips.getLayoutParams().width, this.mCurrentSize));
                            CustomGridLayout.this.mResizeGrips.setY(this.mCurrentPos);
                            final int round = Math.round((this.mStartPos - this.mCurrentPos) / CustomGridLayout.this.getRowHeight());
                            if (this.mStartData.mGridPosition.row - round > -1 && this.mStartSize + CustomGridLayout.this.getRowHeight() * round >= this.mWidgetInfo.minResizeHeight) {
                                if (round > this.mCellSizeDelta) {
                                    CustomGridLayout.this.freeCells(this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col, this.mStartData.mGridPosition.rowSpan, this.mStartData.mGridPosition.colSpan);
                                    if (!CustomGridLayout.this.isCellGridUsedFull(this.mStartData.mGridPosition.row - round, this.mStartData.mGridPosition.col, this.mStartData.mGridPosition.rowSpan + round, this.mStartData.mGridPosition.colSpan)) {
                                        this.mCellSizeDelta = round;
                                        view = CustomGridLayout.this.getChildAt(this.mStartData.mGridPosition.row + this.mStartData.mGridPosition.rowSpan - 1, this.mStartData.mGridPosition.col);
                                        CustomGridLayout.this.removeView(view);
                                        if (view != null) {
                                            CustomGridLayout.this.addViewTmp(view, this.mStartData.mGridPosition.row - round, this.mStartData.mGridPosition.col, this.mStartData.mGridPosition.rowSpan + round, this.mStartData.mGridPosition.colSpan, this.mStartData);
                                            if (view instanceof ClickableAppWidgetHostView) {
                                                ((ClickableAppWidgetHostView)view).updateAppWidgetSize((Bundle)null, CustomGridLayout.this.getColumnWidth(), CustomGridLayout.this.getRowHeight(), CustomGridLayout.this.getColumnCount() * CustomGridLayout.this.getColumnWidth(), CustomGridLayout.this.getRowCount() * CustomGridLayout.this.getRowHeight());
                                            }
                                        }
                                    }
                                }
                                else if (round < this.mCellSizeDelta) {
                                    CustomGridLayout.this.freeCells(this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col, this.mStartData.mGridPosition.rowSpan, this.mStartData.mGridPosition.colSpan);
                                    this.mCellSizeDelta = round;
                                    view = CustomGridLayout.this.getChildAt(this.mStartData.mGridPosition.row + this.mStartData.mGridPosition.rowSpan - 1, this.mStartData.mGridPosition.col);
                                    CustomGridLayout.this.removeView(view);
                                    if (view != null) {
                                        CustomGridLayout.this.addViewTmp(view, this.mStartData.mGridPosition.row - round, this.mStartData.mGridPosition.col, this.mStartData.mGridPosition.rowSpan + round, this.mStartData.mGridPosition.colSpan, this.mStartData);
                                        if (view instanceof ClickableAppWidgetHostView) {
                                            ((ClickableAppWidgetHostView)view).updateAppWidgetSize((Bundle)null, CustomGridLayout.this.getColumnWidth(), CustomGridLayout.this.getRowHeight(), CustomGridLayout.this.getColumnCount() * CustomGridLayout.this.getColumnWidth(), CustomGridLayout.this.getRowCount() * CustomGridLayout.this.getRowHeight());
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            CustomGridLayout.this.mResizeGrips.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(CustomGridLayout.this.mResizeGrips.getLayoutParams().width, this.mCurrentSize));
                            final int round2 = Math.round((this.mCurrentSize - this.mStartSize) / CustomGridLayout.this.getRowHeight());
                            if (this.mStartData.mGridPosition.row + this.mStartData.mGridPosition.rowSpan + round2 <= CustomGridLayout.this.getRowCount() && this.mStartSize + CustomGridLayout.this.getRowHeight() * round2 >= this.mWidgetInfo.minResizeHeight) {
                                if (round2 > this.mCellSizeDelta) {
                                    CustomGridLayout.this.freeCells(this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col, this.mStartData.mGridPosition.rowSpan, this.mStartData.mGridPosition.colSpan);
                                    if (!CustomGridLayout.this.isCellGridUsedFull(this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col, this.mStartData.mGridPosition.rowSpan + round2, this.mStartData.mGridPosition.colSpan)) {
                                        this.mCellSizeDelta = round2;
                                        view = CustomGridLayout.this.getChildAt(this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col);
                                        CustomGridLayout.this.removeView(view);
                                        if (view != null) {
                                            CustomGridLayout.this.addViewTmp(view, this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col, this.mStartData.mGridPosition.rowSpan + round2, this.mStartData.mGridPosition.colSpan, this.mStartData);
                                            if (view instanceof ClickableAppWidgetHostView) {
                                                ((ClickableAppWidgetHostView)view).updateAppWidgetSize((Bundle)null, CustomGridLayout.this.getColumnWidth(), CustomGridLayout.this.getRowHeight(), CustomGridLayout.this.getColumnCount() * CustomGridLayout.this.getColumnWidth(), CustomGridLayout.this.getRowCount() * CustomGridLayout.this.getRowHeight());
                                            }
                                        }
                                    }
                                }
                                else if (round2 < this.mCellSizeDelta) {
                                    CustomGridLayout.this.freeCells(this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col, this.mStartData.mGridPosition.rowSpan, this.mStartData.mGridPosition.colSpan);
                                    this.mCellSizeDelta = round2;
                                    view = CustomGridLayout.this.getChildAt(this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col);
                                    CustomGridLayout.this.removeView(view);
                                    if (view != null) {
                                        CustomGridLayout.this.addViewTmp(view, this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col, this.mStartData.mGridPosition.rowSpan + round2, this.mStartData.mGridPosition.colSpan, this.mStartData);
                                        if (view instanceof ClickableAppWidgetHostView) {
                                            ((ClickableAppWidgetHostView)view).updateAppWidgetSize((Bundle)null, CustomGridLayout.this.getColumnWidth(), CustomGridLayout.this.getRowHeight(), CustomGridLayout.this.getColumnCount() * CustomGridLayout.this.getColumnWidth(), CustomGridLayout.this.getRowCount() * CustomGridLayout.this.getRowHeight());
                                        }
                                    }
                                }
                            }
                        }
                        CustomGridLayout.this.mResizeGrips.requestLayout();
                        break;
                    }
                    case 4: {
                        final DrawerObject.GridPositioning mGridPosition = this.mStartData.mGridPosition;
                        mGridPosition.rowSpan += this.mCellSizeDelta;
                        final DrawerObject.GridPositioning mGridPosition2 = this.mStartData.mGridPosition;
                        final int row = mGridPosition2.row;
                        int mCellSizeDelta;
                        if (this.mDirection == Direction.DIRECTION_TOP) {
                            mCellSizeDelta = this.mCellSizeDelta;
                        }
                        else {
                            mCellSizeDelta = 0;
                        }
                        mGridPosition2.row = row - mCellSizeDelta;
                        final int n2 = this.mCellSizeDelta * CustomGridLayout.this.getRowHeight();
                        CustomGridLayout.this.fixFreeGrid();
                        view = CustomGridLayout.this.getChildAt(this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col);
                        int id;
                        if (view == null) {
                            id = -1;
                        }
                        else {
                            id = view.getId();
                        }
                        CustomGridLayout.this.mAppWidgetContainer.mAppWidgetManager.notifyAppWidgetViewDataChanged(((Widget)this.mStartData).widgetId, id);
                        final ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[] { this.mCurrentPos, this.mStartPos - n2 });
                        ofFloat.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                                if (GripDragListener.this.mDirection == Direction.DIRECTION_TOP) {
                                    GripDragListener.this.mCurrentPos = (float)valueAnimator.getAnimatedValue();
                                    CustomGridLayout.this.mResizeGrips.setY(GripDragListener.this.mCurrentPos);
                                    CustomGridLayout.this.mResizeGrips.requestLayout();
                                }
                            }
                        });
                        final ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[] { this.mCurrentSize, this.mStartSize + n2 });
                        ofFloat2.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                                if (CustomGridLayout.this.mResizeGrips == null || CustomGridLayout.this.mResizeGrips.getLayoutParams() == null) {
                                    return;
                                }
                                final int n = (int)(float)valueAnimator.getAnimatedValue();
                                if (GripDragListener.this.mDirection == Direction.DIRECTION_TOP) {
                                    CustomGridLayout.this.mResizeGrips.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(CustomGridLayout.this.mResizeGrips.getLayoutParams().width, n));
                                }
                                else {
                                    CustomGridLayout.this.mResizeGrips.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(CustomGridLayout.this.mResizeGrips.getLayoutParams().width, n));
                                }
                                CustomGridLayout.this.mResizeGrips.requestLayout();
                            }
                        });
                        final AnimatorSet set = new AnimatorSet();
                        set.playTogether(new Animator[] { ofFloat, ofFloat2 });
                        set.start();
                        break;
                    }
                }
            }
            else {
                switch (dragEvent.getAction()) {
                    case 1: {
                        this.mStartPos = CustomGridLayout.this.mResizeGrips.getX();
                        this.mStartSize = CustomGridLayout.this.mResizeGrips.getLayoutParams().width;
                        break;
                    }
                    case 2: {
                        int mCurrentSize2;
                        if (this.mDirection == Direction.DIRECTION_LEFT) {
                            mCurrentSize2 = (int)(this.mStartSize + (this.mStartPos - dragEvent.getX()) + n / 2);
                        }
                        else {
                            mCurrentSize2 = (int)(dragEvent.getX() - this.mStartPos + n / 2);
                        }
                        this.mCurrentSize = mCurrentSize2;
                        if (this.mCurrentSize - n > CustomGridLayout.this.getColumnWidth()) {
                            this.mCurrentPos = dragEvent.getX() - n / 2;
                        }
                        else {
                            this.mCurrentSize = CustomGridLayout.this.getColumnWidth() + n;
                            this.mCurrentPos = this.mStartPos + this.mStartSize - n - CustomGridLayout.this.getColumnWidth();
                        }
                        if (this.mDirection == Direction.DIRECTION_LEFT) {
                            CustomGridLayout.this.mResizeGrips.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(this.mCurrentSize, CustomGridLayout.this.mResizeGrips.getLayoutParams().height));
                            CustomGridLayout.this.mResizeGrips.setX(this.mCurrentPos);
                            final int round3 = Math.round((this.mStartPos - this.mCurrentPos) / CustomGridLayout.this.getColumnWidth());
                            if (this.mStartData.mGridPosition.col - round3 > -1 && this.mStartSize + CustomGridLayout.this.getColumnWidth() * round3 >= this.mWidgetInfo.minResizeWidth) {
                                if (round3 > this.mCellSizeDelta) {
                                    CustomGridLayout.this.freeCells(this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col, this.mStartData.mGridPosition.rowSpan, this.mStartData.mGridPosition.colSpan);
                                    if (!CustomGridLayout.this.isCellGridUsedFull(this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col - round3, this.mStartData.mGridPosition.rowSpan, this.mStartData.mGridPosition.colSpan + round3)) {
                                        this.mCellSizeDelta = round3;
                                        view = CustomGridLayout.this.getChildAt(this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col + this.mStartData.mGridPosition.colSpan - 1);
                                        CustomGridLayout.this.removeView(view);
                                        if (view != null) {
                                            CustomGridLayout.this.addViewTmp(view, this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col - round3, this.mStartData.mGridPosition.rowSpan, this.mStartData.mGridPosition.colSpan + round3, this.mStartData);
                                        }
                                    }
                                }
                                else if (round3 < this.mCellSizeDelta) {
                                    CustomGridLayout.this.freeCells(this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col, this.mStartData.mGridPosition.rowSpan, this.mStartData.mGridPosition.colSpan);
                                    this.mCellSizeDelta = round3;
                                    view = CustomGridLayout.this.getChildAt(this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col + this.mStartData.mGridPosition.colSpan - 1);
                                    CustomGridLayout.this.removeView(view);
                                    if (view != null) {
                                        CustomGridLayout.this.addViewTmp(view, this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col - round3, this.mStartData.mGridPosition.rowSpan, this.mStartData.mGridPosition.colSpan + round3, this.mStartData);
                                    }
                                }
                            }
                        }
                        else {
                            CustomGridLayout.this.mResizeGrips.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(this.mCurrentSize, CustomGridLayout.this.mResizeGrips.getLayoutParams().height));
                            final int round4 = Math.round((this.mCurrentSize - this.mStartSize) / CustomGridLayout.this.getColumnWidth());
                            if (this.mStartData.mGridPosition.col + this.mStartData.mGridPosition.colSpan + round4 <= CustomGridLayout.this.getColumnWidth() && this.mStartSize + CustomGridLayout.this.getColumnWidth() * round4 >= this.mWidgetInfo.minResizeWidth) {
                                if (round4 > this.mCellSizeDelta) {
                                    CustomGridLayout.this.freeCells(this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col, this.mStartData.mGridPosition.rowSpan, this.mStartData.mGridPosition.colSpan);
                                    if (!CustomGridLayout.this.isCellGridUsedFull(this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col, this.mStartData.mGridPosition.rowSpan, this.mStartData.mGridPosition.colSpan + round4)) {
                                        this.mCellSizeDelta = round4;
                                        view = CustomGridLayout.this.getChildAt(this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col);
                                        CustomGridLayout.this.removeView(view);
                                        if (view != null) {
                                            CustomGridLayout.this.addViewTmp(view, this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col, this.mStartData.mGridPosition.rowSpan, this.mStartData.mGridPosition.colSpan + round4, this.mStartData);
                                        }
                                    }
                                }
                                else if (round4 < this.mCellSizeDelta) {
                                    CustomGridLayout.this.freeCells(this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col, this.mStartData.mGridPosition.rowSpan, this.mStartData.mGridPosition.colSpan);
                                    this.mCellSizeDelta = round4;
                                    view = CustomGridLayout.this.getChildAt(this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col);
                                    CustomGridLayout.this.removeView(view);
                                    if (view != null) {
                                        CustomGridLayout.this.addViewTmp(view, this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col, this.mStartData.mGridPosition.rowSpan, this.mStartData.mGridPosition.colSpan + round4, this.mStartData);
                                    }
                                }
                            }
                        }
                        CustomGridLayout.this.mResizeGrips.requestLayout();
                        break;
                    }
                    case 4: {
                        final DrawerObject.GridPositioning mGridPosition3 = this.mStartData.mGridPosition;
                        mGridPosition3.colSpan += this.mCellSizeDelta;
                        final DrawerObject.GridPositioning mGridPosition4 = this.mStartData.mGridPosition;
                        final int col = mGridPosition4.col;
                        int mCellSizeDelta2;
                        if (this.mDirection == Direction.DIRECTION_LEFT) {
                            mCellSizeDelta2 = this.mCellSizeDelta;
                        }
                        else {
                            mCellSizeDelta2 = 0;
                        }
                        mGridPosition4.col = col - mCellSizeDelta2;
                        final int n3 = this.mCellSizeDelta * CustomGridLayout.this.getColumnWidth();
                        CustomGridLayout.this.fixFreeGrid();
                        view = CustomGridLayout.this.getChildAt(this.mStartData.mGridPosition.row, this.mStartData.mGridPosition.col);
                        int id2;
                        if (view == null) {
                            id2 = -1;
                        }
                        else {
                            id2 = view.getId();
                        }
                        CustomGridLayout.this.mAppWidgetContainer.mAppWidgetManager.notifyAppWidgetViewDataChanged(((Widget)this.mStartData).widgetId, id2);
                        final ValueAnimator ofFloat3 = ValueAnimator.ofFloat(new float[] { this.mCurrentPos, this.mStartPos - n3 });
                        ofFloat3.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                                if (GripDragListener.this.mDirection == Direction.DIRECTION_LEFT) {
                                    GripDragListener.this.mCurrentPos = (float)valueAnimator.getAnimatedValue();
                                    CustomGridLayout.this.mResizeGrips.setX(GripDragListener.this.mCurrentPos);
                                    CustomGridLayout.this.mResizeGrips.requestLayout();
                                }
                            }
                        });
                        final ValueAnimator ofFloat4 = ValueAnimator.ofFloat(new float[] { this.mCurrentSize, this.mStartSize + n3 });
                        ofFloat4.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                                final int n = (int)(float)valueAnimator.getAnimatedValue();
                                if (GripDragListener.this.mDirection == Direction.DIRECTION_LEFT) {
                                    CustomGridLayout.this.mResizeGrips.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(n, CustomGridLayout.this.mResizeGrips.getLayoutParams().height));
                                }
                                else {
                                    CustomGridLayout.this.mResizeGrips.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(n, CustomGridLayout.this.mResizeGrips.getLayoutParams().height));
                                }
                                CustomGridLayout.this.mResizeGrips.requestLayout();
                            }
                        });
                        final AnimatorSet set2 = new AnimatorSet();
                        set2.playTogether(new Animator[] { ofFloat3, ofFloat4 });
                        set2.start();
                        break;
                    }
                }
            }
            return true;
        }
    }
}
