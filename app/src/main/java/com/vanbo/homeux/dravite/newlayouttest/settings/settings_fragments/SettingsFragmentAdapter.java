// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.settings.settings_fragments;

import android.support.v4.app.Fragment;
import java.util.ArrayList;
import android.support.v4.app.FragmentManager;
import java.util.List;
import android.support.v4.app.FragmentPagerAdapter;

public class SettingsFragmentAdapter extends FragmentPagerAdapter
{
    List<SettingsListFragment> fragments;
    
    public SettingsFragmentAdapter(final FragmentManager fragmentManager) {
        super(fragmentManager);
        this.fragments = new ArrayList<SettingsListFragment>();
    }
    
    public void addFragment(final SettingsListFragment settingsListFragment) {
        this.fragments.add(settingsListFragment);
        this.notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        return this.fragments.size();
    }
    
    @Override
    public Fragment getItem(final int n) {
        return this.fragments.get(n);
    }
    
    @Override
    public CharSequence getPageTitle(final int n) {
        return this.fragments.get(n).getCaption();
    }
}
