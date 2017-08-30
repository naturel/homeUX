// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures;

import android.view.ViewGroup;
import com.vanbo.homeux.dravite.newlayouttest.LauncherLog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FolderPagerAdapter extends FragmentStatePagerAdapter
{
    private static final String TAG = "FolderPagerAdapter";
    boolean doUpdate;
    private boolean isInNullState;
    LauncherActivity mMainActivity;
    
    public FolderPagerAdapter(final LauncherActivity mainActivity, final FragmentManager fragmentManager) {
        super(fragmentManager);
        doUpdate = false;
        mMainActivity = mainActivity;
    }
    
    @Override
    public int getCount() {
        if (this.isInNullState) {
            return 0;
        }
        return LauncherActivity.mFolderStructure.folders.size();
    }
    
    @Override
    public Fragment getItem(final int n) {
        return FolderDrawerPageFragment.newInstance(n);
    }
    
    @Override
    public int getItemPosition(final Object o) {
        if (this.doUpdate) {
            return -2;
        }
        return super.getItemPosition(o);
    }
    
    public void notifyPagesChanged() {
        this.doUpdate = true;
        while (true) {
            try {
                this.notifyDataSetChanged();
                this.doUpdate = false;
            }
            catch (IllegalStateException ex) {
                LauncherLog.d("FolderPagerAdapter", "notifyPagesChanged: " + ex.toString());
                continue;
            }
            break;
        }
    }
    
    public void switchFromNullState() {
        this.isInNullState = false;
        this.notifyPagesChanged();
    }
    
    public void switchToNullState() {
        this.isInNullState = true;
        this.notifyDataSetChanged();
    }
    
    public void update() {
        this.doUpdate = true;
        while (true) {
            try {
                this.notifyDataSetChanged();
                for (int i = Math.max(0, this.mMainActivity.mPager.getCurrentItem() - 1); i < Math.min(this.getCount(), this.mMainActivity.mPager.getCurrentItem() + 1); ++i) {
                    final FolderDrawerPageFragment folderDrawerPageFragment = (FolderDrawerPageFragment)this.instantiateItem(this.mMainActivity.mPager, i);
                    if (folderDrawerPageFragment.mPager != null) {
                        ((AppDrawerPagerAdapter)folderDrawerPageFragment.mPager.getAdapter()).update();
                    }
                }
            }
            catch (IllegalStateException ex) {
                LauncherLog.w("FolderPagerAdapter", ex.getMessage());
                continue;
            }
            break;
        }
        this.doUpdate = false;
    }
}
