// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.settings.items;

import android.view.ViewGroup;
import android.content.Context;
import android.net.Uri;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.content.Intent;
import com.dravite.homeux.R;

public class GenericSettingsItem extends BaseItem<GenericSettingsItem.GeneralViewHolder>
{
    private String mDescription;
    int mDrawableRes;
    String mTitle;
    
    public GenericSettingsItem(final String title, final String description, final String s, final int res, final boolean b) {
        super(0, b, s);
        this.mTitle = title;
        this.mDescription = description;
        this.mDrawableRes = res;
    }
    
    public GenericSettingsItem(final String s, final String s2, final String s3, final int n, final boolean b, final Intent intent) {
        this(s, s2, s3, n, b);
        this.setAction(new ItemViewHolder.OnItemClickListener() {
            @Override
            public void onClick(final View view, final BaseItem baseItem, final RecyclerView.Adapter adapter, final int n) {
                view.getContext().startActivity(intent);
            }
        });
    }
    
    public GenericSettingsItem(final String s, final String s2, final String s3, final int n, final boolean b, final Intent intent, final int n2) {
        this(s, s2, s3, n, b);
        this.setAction(new ItemViewHolder.OnItemClickListener() {
            @Override
            public void onClick(final View view, final BaseItem baseItem, final RecyclerView.Adapter adapter, final int n) {
                ((Activity)view.getContext()).startActivityForResult(intent, n2);
            }
        });
    }
    
    public GenericSettingsItem(final String s, final String s2, final String s3, final int n, final boolean b, final Uri uri) {
        this(s, s2, s3, n, b);
        this.setAction(new ItemViewHolder.OnItemClickListener() {
            @Override
            public void onClick(final View view, final BaseItem baseItem, final RecyclerView.Adapter adapter, final int n) {
                final Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(uri);
                view.getContext().startActivity(intent);
            }
        });
    }
    
    public GenericSettingsItem(final String s, final String s2, final String s3, final int n, final boolean b, final ItemViewHolder.OnItemClickListener action) {
        this(s, s2, s3, n, b);
        this.setAction(action);
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
    
    public void setDescription(final String description) {
        this.mDescription = description;
    }
    
    public static class GeneralViewHolder extends BaseItem.ItemViewHolder
    {
        public GeneralViewHolder(final Context context, final ViewGroup viewGroup) {
            super(R.layout.setting_generic, context, viewGroup);
        }
    }
}
