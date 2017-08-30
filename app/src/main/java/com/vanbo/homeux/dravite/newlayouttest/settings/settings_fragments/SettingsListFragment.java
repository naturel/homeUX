// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.settings.settings_fragments;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import com.vanbo.homeux.dravite.newlayouttest.settings.items.BaseItem;
import java.util.List;
import android.support.v4.app.Fragment;
import com.dravite.homeux.R;

public class SettingsListFragment extends Fragment
{
    public SettingsItemAdapter adapter;
    private String mCaption;
    
    public SettingsListFragment() {
        this.mCaption = "";
        this.adapter = new SettingsItemAdapter(this);
    }
    
    public static SettingsListFragment create(final String caption, final List<BaseItem> list) {
        final SettingsListFragment settingsListFragment = new SettingsListFragment();
        settingsListFragment.setCaption(caption);
        settingsListFragment.addItems(list);
        return settingsListFragment;
    }
    
    public void addItems(final List<BaseItem> list) {
        this.adapter.addItems(list);
    }
    
    public String getCaption() {
        return this.mCaption;
    }
    
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, @Nullable final ViewGroup viewGroup, @Nullable final Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_settings, viewGroup, false);
    }
    
    @Override
    public void onViewCreated(final View view, @Nullable final Bundle bundle) {
        super.onViewCreated(view, bundle);
        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.settingList);
        recyclerView.setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager(view.getContext(), 1));
        recyclerView.setAdapter((RecyclerView.Adapter)this.adapter);
    }
    
    public void setCaption(final String mCaption) {
        this.mCaption = mCaption;
    }
}
