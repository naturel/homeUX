// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures;

import android.animation.TimeInterpolator;
import android.view.animation.DecelerateInterpolator;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.JsonHelper;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentStatePagerAdapter;

public class AppDrawerPagerAdapter extends FragmentStatePagerAdapter
{
    boolean doUpdate;
    int folderPos;
    public boolean mCanDragItems;
    private Context mContext;
    FragmentManager mFragmentManager;
    ViewPager mPager;
    private AdapterUpdateListener mUpdateListener;
    
    public AppDrawerPagerAdapter(final Context mContext, final FragmentManager mFragmentManager, final ViewPager mPager, final int folderPos) {
        super(mFragmentManager);
        this.doUpdate = false;
        this.mFragmentManager = mFragmentManager;
        this.mCanDragItems = true;
        this.mContext = mContext;
        this.mPager = mPager;
        this.folderPos = folderPos;
    }
    
    public void addPage() {
        (LauncherActivity.mFolderStructure.folders.get(this.folderPos)).add(new FolderStructure.Page());
        this.notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        return (LauncherActivity.mFolderStructure.folders.get(this.folderPos)).pages.size();
    }
    
    @Override
    public Fragment getItem(final int n) {
        return AppDrawerPageFragment.newInstance(this.folderPos, n);
    }
    
    @Override
    public int getItemPosition(final Object o) {
        if (((AppDrawerPageFragment)o).getRemovalTag() == -1 || this.doUpdate) {
            return -2;
        }
        return super.getItemPosition(o);
    }
    
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (this.mUpdateListener != null) {
            this.mUpdateListener.onUpdate();
        }
    }
    
    public void removeEmptyPages() {
        boolean b = false;
        boolean b2;
        for (int i = this.getCount() - 1; i >= 0; --i, b = b2) {
            b2 = b;
            if ((LauncherActivity.mFolderStructure.folders.get(this.folderPos)).pages.get(i).items.size() == 0) {
                b2 = b;
                if ((LauncherActivity.mFolderStructure.folders.get(this.folderPos)).pages.size() > 1) {
                    ((AppDrawerPageFragment)this.instantiateItem(this.mPager, i)).setRemovalTag(-1);
                    (LauncherActivity.mFolderStructure.folders.get(this.folderPos)).pages.remove(i);
                    boolean b3;
                    if (this.mPager.getCurrentItem() == i) {
                        b3 = true;
                    }
                    else {
                        b3 = false;
                    }
                    b2 = (b | b3);
                }
            }
        }
        JsonHelper.saveFolderStructure(this.mContext, LauncherActivity.mFolderStructure);
        if (b) {
            this.mPager.setScaleX(0.0f);
            this.mPager.setScaleY(0.0f);
            this.mPager.animate().scaleX(1.0f).scaleY(1.0f).setInterpolator((TimeInterpolator)new DecelerateInterpolator()).setDuration(150L).withStartAction((Runnable)new Runnable() {
                @Override
                public void run() {
                    AppDrawerPagerAdapter.this.notifyDataSetChanged();
                }
            });
            return;
        }
        this.notifyDataSetChanged();
    }
    
    public void removePage(final int n) {
        ((AppDrawerPageFragment)this.instantiateItem(this.mPager, n)).setRemovalTag(-1);
        LauncherActivity.mFolderStructure.removePageAssignments((FolderStructure.Page)((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(this.folderPos)).pages.get(n), ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(this.folderPos)).folderName);
        if (((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(this.folderPos)).pages.size() > 1) {
            ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(this.folderPos)).pages.remove(n);
        }
        else {
            ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(this.folderPos)).pages.get(n).items.clear();
        }
        JsonHelper.saveFolderStructure(this.mContext, LauncherActivity.mFolderStructure);
        ((LauncherActivity)this.mContext).refreshAllFolder(((LauncherActivity)this.mContext).mHolder.gridHeight, ((LauncherActivity)this.mContext).mHolder.gridWidth);
        this.notifyDataSetChanged();
    }
    
    public void setUpdateListener(final AdapterUpdateListener mUpdateListener) {
        this.mUpdateListener = mUpdateListener;
    }
    
    public void update() {
        this.doUpdate = true;
        while (true) {
            try {
                this.notifyDataSetChanged();
                this.doUpdate = false;
            }
            catch (IllegalStateException ex) {
                ex.printStackTrace();
                continue;
            }
            break;
        }
    }
    
    public interface AdapterUpdateListener
    {
        void onUpdate();
    }
}
