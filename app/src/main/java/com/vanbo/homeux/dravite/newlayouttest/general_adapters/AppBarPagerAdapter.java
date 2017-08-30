// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_adapters;

import com.vanbo.homeux.dravite.newlayouttest.top_fragments.FolderListFragment;
import com.vanbo.homeux.dravite.newlayouttest.top_fragments.QuickSettingsFragment;
import com.vanbo.homeux.dravite.newlayouttest.top_fragments.ClockFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class AppBarPagerAdapter extends FragmentStatePagerAdapter
{
    public AppBarPagerAdapter(final FragmentManager fragmentManager) {
        super(fragmentManager);
    }
    
    @Override
    public int getCount() {
        return 3;
    }
    
    @Override
    public Fragment getItem(final int n) {
        switch (n) {
            default: {
                return new ClockFragment();
            }
            case 0: {
                return new QuickSettingsFragment();
            }
            case 1: {
                return new ClockFragment();
            }
            case 2: {
                return new FolderListFragment();
            }
        }
    }
}
