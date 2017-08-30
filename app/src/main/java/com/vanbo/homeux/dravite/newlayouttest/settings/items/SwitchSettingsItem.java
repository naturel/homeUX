// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.settings.items;

import android.view.ViewGroup;
import android.content.Context;
import android.widget.Switch;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.dravite.homeux.R;

public class SwitchSettingsItem extends BaseItem<SwitchSettingsItem.SwitchItemHolder>
{
    String mDescription;
    int mDrawableRes;
    boolean mIsChecked;
    String mTitle;
    
    public SwitchSettingsItem(final String mTitle, final String mDescription, final String s, final int mDrawableRes, final boolean b) {
        super(1, b, s);
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mDrawableRes = mDrawableRes;
        this.setAction(new ItemViewHolder.OnItemClickListener() {
            @Override
            public void onClick(final View view, final BaseItem baseItem, final RecyclerView.Adapter adapter, final int n) {
            }
        });
    }
    
    public SwitchSettingsItem(final String s, final String s2, final String s3, final int n, final boolean b, final boolean enabled) {
        this(s, s2, s3, n, b);
        this.setEnabled(enabled);
    }
    
    public SwitchSettingsItem(final String s, final String s2, final String s3, final int n, final boolean b, final boolean b2, final boolean mIsChecked) {
        this(s, s2, s3, n, b, b2);
        this.mIsChecked = mIsChecked;
    }
    
    public String getDescription() {
        return this.mDescription;
    }
    
    public int getDrawableRes() {
        return this.mDrawableRes;
    }
    
    public String getTitle() {
        return this.mTitle;
    }
    
    public boolean isChecked() {
        return this.mIsChecked;
    }
    
    public static class SwitchItemHolder extends BaseItem.ItemViewHolder
    {
        public Switch mSwitch;
        
        public SwitchItemHolder(final Context context, final ViewGroup viewGroup) {
            super(R.layout.setting_switch, context, viewGroup);
            this.mSwitch = (Switch)this.itemView.findViewById(R.id.pswitch);
        }
        
        @Override
        public void setEnabled(final boolean b) {
            super.setEnabled(b);
            this.mSwitch.setEnabled(b);
        }
        
        @Override
        public void setOnItemClickListener(final OnItemClickListener onItemClickListener, final BaseItem baseItem, final RecyclerView.Adapter adapter) {
            super.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onClick(final View view, final BaseItem baseItem, final RecyclerView.Adapter adapter, final int n) {
                    SwitchItemHolder.this.mSwitch.setChecked(!SwitchItemHolder.this.mSwitch.isChecked());
                    onItemClickListener.onClick(view, baseItem, adapter, n);
                }
            }, baseItem, adapter);
        }
    }
}
