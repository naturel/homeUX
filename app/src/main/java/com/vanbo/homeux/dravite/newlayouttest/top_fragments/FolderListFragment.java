// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.top_fragments;

import android.view.View.OnDragListener;
import android.widget.FrameLayout;
import android.view.View.OnLongClickListener;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
import com.vanbo.homeux.dravite.newlayouttest.views.FolderButton;
import android.support.v7.widget.DefaultItemAnimator;
import com.vanbo.homeux.dravite.newlayouttest.views.viewcomponents.SpacesItemDecoration;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import com.vanbo.homeux.dravite.newlayouttest.LauncherLog;
import android.support.v7.widget.GridLayoutManager;
import android.graphics.Rect;
import android.content.Context;
import com.vanbo.homeux.dravite.newlayouttest.views.FolderRecyclerView;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import android.os.Handler;
import android.graphics.PointF;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import com.dravite.homeux.R;


import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.FolderStructure;
import android.view.View;
import android.support.v4.app.Fragment;

public class FolderListFragment extends Fragment
{
    private boolean canChange;
    public boolean hasChanged;
    public FolderListAdapter mAdapter;
    public String mCurrentlySelectedFolder;
    public View mDragButton;
    public int mDragCurrentIndex;
    public FolderStructure.Folder mDragFolder;
    public int mDragInitialScroll;
    public int mDragStartIndex;
    public int[] mDragStartPosition;
    public int[] mDragTargetPosition;
    public RecyclerView mFolderList;
    private QuickSettingsFragment mQuickSettingsFragment;
    public PointF mTouchPosition;
    private Handler returnHandler;
    
    public FolderListFragment() {
        this.returnHandler = new Handler();
        this.hasChanged = false;
        this.canChange = true;
        this.mTouchPosition = new PointF(0.0f, 0.0f);
        this.mDragTargetPosition = new int[2];
    }
    
    public int[] getRevealXCenter(final int n, final int[] array) {
        if (this.getView() != null) {
            final RecyclerView.ViewHolder viewHolderForAdapterPosition = ((RecyclerView)this.getView().findViewById(R.id.folder_list)).findViewHolderForAdapterPosition(n);
            final int[] array2 = new int[2];
            if (viewHolderForAdapterPosition != null) {
                viewHolderForAdapterPosition.itemView.getLocationInWindow(array2);
                array2[1] += LauncherUtils.dpToPx(22.0f, this.getContext());
                array2[0] += viewHolderForAdapterPosition.itemView.getMeasuredWidth() / 2;
                return array2;
            }
        }
        return array;
    }
    
    public void hover(int indexOfChild, final int n) {
        if (this.getView() != null) {
            final FolderRecyclerView folderRecyclerView = (FolderRecyclerView)this.getView().findViewById(R.id.folder_list);
            final int[] array = new int[2];
            final int dpToPx = LauncherUtils.dpToPx(24.0f, (Context)this.getActivity());
            folderRecyclerView.getLocationInWindow(array);
            final Rect rect = new Rect(array[0], array[1], array[0] + folderRecyclerView.getMeasuredWidth(), array[1] + dpToPx * 2);
            final Rect rect2 = new Rect(array[0], array[1] + folderRecyclerView.getMeasuredHeight() - dpToPx, array[0] + folderRecyclerView.getMeasuredWidth(), array[1] + folderRecyclerView.getMeasuredHeight());
            final GridLayoutManager gridLayoutManager = (GridLayoutManager)folderRecyclerView.getLayoutManager();
            if (rect.contains(indexOfChild, n)) {
                folderRecyclerView.scrollBy(0, -LauncherUtils.dpToPx(8.0f, (Context)this.getActivity()));
                return;
            }
            if (rect2.contains(indexOfChild, n) && gridLayoutManager.findLastCompletelyVisibleItemPosition() != folderRecyclerView.getAdapter().getItemCount() - 1) {
                folderRecyclerView.scrollBy(0, LauncherUtils.dpToPx(8.0f, (Context)this.getActivity()));
                return;
            }
            indexOfChild = folderRecyclerView.indexOfChild(folderRecyclerView.findChildViewUnder(indexOfChild, n));
            LauncherLog.d("FLF", indexOfChild + "  ...  " + this.mDragCurrentIndex);
            if (this.canChange && indexOfChild != -1 && indexOfChild != this.mDragCurrentIndex && indexOfChild < LauncherActivity.mFolderStructure.folders.size()) {
                this.canChange = false;
                this.hasChanged = true;
                this.returnHandler.removeCallbacksAndMessages((Object)null);
                LauncherLog.d("FLF", "Change...");
                final FolderStructure.Folder folder = LauncherActivity.mFolderStructure.folders.get(this.mDragCurrentIndex);
                LauncherActivity.mFolderStructure.folders.remove(this.mDragCurrentIndex);
                LauncherActivity.mFolderStructure.folders.add(indexOfChild, folder);
                ((RecyclerView.Adapter)this.mAdapter).notifyItemMoved(this.mDragCurrentIndex, indexOfChild);
                folderRecyclerView.getChildAt(indexOfChild).getLocationInWindow(this.mDragTargetPosition);
                this.mDragCurrentIndex = indexOfChild;
                new Handler().postDelayed((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        FolderListFragment.this.canChange = true;
                    }
                }, 150L);
            }
            if (this.hasChanged && rect2.bottom < n && this.mDragCurrentIndex != this.mDragStartIndex) {
                this.hasChanged = false;
                final FolderStructure.Folder folder2 = LauncherActivity.mFolderStructure.folders.get(this.mDragCurrentIndex);
                LauncherActivity.mFolderStructure.folders.remove(this.mDragCurrentIndex);
                LauncherActivity.mFolderStructure.folders.add(this.mDragStartIndex, folder2);
                ((RecyclerView.Adapter)this.mAdapter).notifyItemMoved(this.mDragCurrentIndex, this.mDragStartIndex);
                this.mDragTargetPosition = this.mDragStartPosition.clone();
                this.mDragCurrentIndex = this.mDragStartIndex;
            }
        }
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
    }
    
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_folder_list, viewGroup, false);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (this.mQuickSettingsFragment == null) {
            this.mQuickSettingsFragment = (QuickSettingsFragment)((LauncherActivity)this.getActivity()).mAppBarPager.getAdapter().instantiateItem(((LauncherActivity)this.getActivity()).mAppBarPager, 0);
        }
    }
    
    @Override
    public void onViewCreated(final View view, @Nullable final Bundle bundle) {
        super.onViewCreated(view, bundle);
        final LauncherActivity launcherActivity = (LauncherActivity)this.getActivity();
        launcherActivity.mDragView.setFolderListFragment(this);
        (this.mFolderList = (RecyclerView)view.findViewById(R.id.folder_list)).setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager((Context)launcherActivity, 4));
        this.mAdapter = new FolderListAdapter((Context)launcherActivity);
        this.mFolderList.setAdapter((RecyclerView.Adapter)this.mAdapter);
        this.mFolderList.addItemDecoration((RecyclerView.ItemDecoration)new SpacesItemDecoration(LauncherUtils.dpToPx(2.0f, (Context)launcherActivity)));
        this.mFolderList.setEnabled(true);
        this.mFolderList.setFocusable(true);
        this.mFolderList.setFocusableInTouchMode(true);
        this.mFolderList.setNestedScrollingEnabled(true);
        final DefaultItemAnimator itemAnimator = new DefaultItemAnimator();
        ((RecyclerView.ItemAnimator)itemAnimator).setChangeDuration(80L);
        ((RecyclerView.ItemAnimator)itemAnimator).setMoveDuration(200L);
        ((RecyclerView.ItemAnimator)itemAnimator).setRemoveDuration(100L);
        ((RecyclerView.ItemAnimator)itemAnimator).setAddDuration(100L);
        this.mFolderList.setItemAnimator((RecyclerView.ItemAnimator)itemAnimator);
    }
    
    public class FolderListAdapter extends Adapter<FolderListAdapter.FolderHolder>
    {
        boolean isDragging;
        Context mContext;
        
        public FolderListAdapter(final Context mContext) {
            this.isDragging = false;
            this.mContext = mContext;
        }
        
        public void cancelDrag() {
            this.isDragging = false;
        }
        
        @Override
        public int getItemCount() {
            return LauncherActivity.mFolderStructure.folders.size();
        }
        
        public void onBindViewHolder(final FolderHolder folderHolder, final int n) {
            final LauncherActivity launcherActivity = (LauncherActivity)FolderListFragment.this.getActivity();
            final int currentItem = launcherActivity.mPager.getCurrentItem();
            final FolderButton folderButton = (FolderButton)folderHolder.itemView;
            folderButton.setLayoutParams((ViewGroup.LayoutParams)new RecyclerView.LayoutParams(-2, -2));
            folderButton.assignFolder(LauncherActivity.mFolderStructure.folders.get(n));
            folderButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                }
            });
            if (n == currentItem && !this.isDragging) {
                folderButton.select(((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(n)).accentColor);
            }
            else {
                folderButton.deselect();
            }
            folderButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    ((LauncherActivity)FolderListFragment.this.getActivity()).mPager.setCurrentItem(n, true);
                    FolderListAdapter.this.select(n);
                }
            });
            if (((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(n)).equals(FolderListFragment.this.mDragFolder)) {
                folderButton.setVisibility(View.INVISIBLE);
            }
            else {
                folderButton.setVisibility(View.VISIBLE);
            }
            folderButton.setOnTouchListener((View.OnTouchListener)new View.OnTouchListener() {
                public boolean onTouch(final View view, final MotionEvent motionEvent) {
                    if (!FolderListAdapter.this.isDragging) {
                        FolderListFragment.this.mTouchPosition.x = motionEvent.getX();
                        FolderListFragment.this.mTouchPosition.y = motionEvent.getY();
                    }
                    return false;
                }
            });
            folderButton.setOnLongClickListener((View.OnLongClickListener)new View.OnLongClickListener() {
                public boolean onLongClick(final View view) {
                    FolderListAdapter.this.isDragging = true;
                    FolderListFragment.this.mDragInitialScroll = FolderListFragment.this.mFolderList.computeVerticalScrollRange();
                    LauncherLog.d(this.getClass(), FolderListFragment.this.mDragInitialScroll + " scrolled");
                    FolderListFragment.this.mCurrentlySelectedFolder = ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(launcherActivity.mPager.getCurrentItem())).folderName;
                    final int measuredWidth = view.getMeasuredWidth();
                    final int measuredHeight = view.getMeasuredHeight();
                    if (FolderListFragment.this.mDragStartPosition == null) {
                        FolderListFragment.this.mDragStartPosition = new int[2];
                    }
                    view.getLocationInWindow(FolderListFragment.this.mDragStartPosition);
                    final FolderButton mDragButton = new FolderButton(FolderListFragment.this.getContext());
                    mDragButton.assignFolder(LauncherActivity.mFolderStructure.folders.get(n));
                    mDragButton.deselect();
                    FolderListFragment.this.mDragFolder = (FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(n);
                    ((LauncherActivity)FolderListFragment.this.getActivity()).mDragView.addView((View)mDragButton, (ViewGroup.LayoutParams)new FrameLayout.LayoutParams(measuredWidth, measuredHeight));
                    final PointF mTouchPosition = FolderListFragment.this.mTouchPosition;
                    mTouchPosition.x -= measuredWidth / 2;
                    final PointF mTouchPosition2 = FolderListFragment.this.mTouchPosition;
                    mTouchPosition2.y -= measuredHeight / 2;
                    mDragButton.setTextColor(-1);
                    mDragButton.setX((float)FolderListFragment.this.mDragStartPosition[0]);
                    mDragButton.setY((float)FolderListFragment.this.mDragStartPosition[1]);
                    FolderListFragment.this.mDragTargetPosition = FolderListFragment.this.mDragStartPosition.clone();
                    FolderListFragment.this.mDragButton = (View)mDragButton;
                    FolderListFragment.this.mDragCurrentIndex = n;
                    FolderListFragment.this.mDragStartIndex = n;
                    ((RecyclerView.Adapter)FolderListAdapter.this).notifyDataSetChanged();
                    launcherActivity.mDragView.setOnDragListener((View.OnDragListener)launcherActivity.mDragView);
                    launcherActivity.mDragView.startDrag(2);
                    return true;
                }
            });
            folderButton.setTranslationZ(0.0f);
            folderButton.setElevation(0.0f);
        }
        
        public FolderHolder onCreateViewHolder(final ViewGroup viewGroup, final int n) {
            return new FolderHolder((View)new FolderButton(this.mContext));
        }
        
        public void select(final int n) {
            ((RecyclerView.Adapter)this).notifyDataSetChanged();
        }
        
        public class FolderHolder extends RecyclerView.ViewHolder
        {
            public FolderHolder(final View view) {
                super(view);
            }
        }
    }
}
