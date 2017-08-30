// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures;

import com.vanbo.homeux.dravite.newlayouttest.views.IndicatorView;
import android.support.v4.view.PagerAdapter;
import com.vanbo.homeux.dravite.newlayouttest.Const;
import android.content.SharedPreferences;
import android.content.Context;
import android.preference.PreferenceManager;
import com.vanbo.homeux.dravite.newlayouttest.views.FolderNameLabel;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.PageTransitionManager;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import com.dravite.homeux.R;


public class FolderDrawerPageFragment extends Fragment
{
    public ViewPager mPager;
    int pos;
    
    public static FolderDrawerPageFragment newInstance(final int n) {
        final FolderDrawerPageFragment folderDrawerPageFragment = new FolderDrawerPageFragment();
        final Bundle arguments = new Bundle();
        arguments.putInt("pos", n);
        folderDrawerPageFragment.setArguments(arguments);
        return folderDrawerPageFragment;
    }
    
    public AppDrawerPageFragment getCurrentPagerCard() {
        return this.getPagerCard(this.mPager.getCurrentItem());
    }
    
    public AppDrawerPageFragment getPagerCard(final int n) {
        if (this.mPager == null) {
            return null;
        }
        return (AppDrawerPageFragment)this.mPager.getAdapter().instantiateItem(this.mPager, n);
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.pos = this.getArguments().getInt("pos", 0);
    }
    
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        return layoutInflater.inflate(R.layout.folder_fragment, viewGroup, false);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        final View view = this.getView();
        if (this.mPager != null) {
            this.mPager.setPageTransformer(false, PageTransitionManager.getTransformer(((LauncherActivity)this.getActivity()).mHolder.pagerTransition));
        }
        if (view != null) {
            ((FolderNameLabel)view.findViewById(R.id.name)).assignFolder(LauncherActivity.mFolderStructure.folders.get(this.pos));
        }
    }
    
    @Override
    public void onViewCreated(final View view, @Nullable final Bundle bundle) {
        super.onViewCreated(view, bundle);
        final LauncherActivity launcherActivity = (LauncherActivity)this.getActivity();
        if (mPager == null || mPager.getAdapter() == null) {
            mPager = (ViewPager)view.findViewById(R.id.folder_pager);
            mPager.setPageTransformer(false, PageTransitionManager.getTransformer(launcherActivity.mHolder.pagerTransition));
            mPager.setOffscreenPageLimit(100);
            mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                float oldxOffset = 0.0f;
                final /* synthetic */ SharedPreferences valpreferences = PreferenceManager.getDefaultSharedPreferences((Context)getActivity());
                
                @Override
                public void onPageScrollStateChanged(final int n) {
                    if (n != 0) {
                        ((AppDrawerPagerAdapter)FolderDrawerPageFragment.this.mPager.getAdapter()).mCanDragItems = true;
                    }
                }
                
                @Override
                public void onPageScrolled(final int n, float max, int count) {
                    if (max == 0.0f) {
                        ((AppDrawerPagerAdapter)FolderDrawerPageFragment.this.mPager.getAdapter()).mCanDragItems = true;
                    }
                    else {
                        ((AppDrawerPagerAdapter)FolderDrawerPageFragment.this.mPager.getAdapter()).mCanDragItems = false;
                    }
                    if (!this.valpreferences.getBoolean("disablewallpaperscroll", Const.Defaults.getBoolean("disablewallpaperscroll"))) {
                        count = FolderDrawerPageFragment.this.mPager.getAdapter().getCount();
                        max = Math.max(0.0f, Math.min((n + max) / count, 1.0f));
                        this.oldxOffset = max;
                        launcherActivity.mWallpaperManager.setWallpaperOffsets(FolderDrawerPageFragment.this.mPager.getWindowToken(), max, launcherActivity.mWallpaperManager.getWallpaperOffsets().y);
                    }
                }
                
                @Override
                public void onPageSelected(final int n) {
                    launcherActivity.mIndicator.animate().scaleX(1.0f / FolderDrawerPageFragment.this.mPager.getAdapter().getCount());
                    launcherActivity.mIndicator.animate().translationX((float)(launcherActivity.mAppBarLayout.getMeasuredWidth() / FolderDrawerPageFragment.this.mPager.getAdapter().getCount() * n));
                    ((AppDrawerPagerAdapter)FolderDrawerPageFragment.this.mPager.getAdapter()).mCanDragItems = true;
                }
            });
            mPager.setAdapter(new AppDrawerPagerAdapter(this.getActivity(), this.getChildFragmentManager(), this.mPager, this.pos));
            final IndicatorView indicatorView = (IndicatorView)view.findViewById(R.id.indicatorView);
            indicatorView.setPager(this.mPager);
            indicatorView.setCurrentSelectedInstant(0);
            ((AppDrawerPagerAdapter)this.mPager.getAdapter()).setUpdateListener(new AppDrawerPagerAdapter.AdapterUpdateListener() {
                @Override
                public void onUpdate() {
                    indicatorView.update();
                }
            });
        }
    }
}
