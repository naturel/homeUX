// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.settings.items;

import android.view.ViewGroup;
import android.content.Context;
import com.dravite.homeux.R;

public class CaptionSettingsItem extends BaseItem<CaptionSettingsItem.CaptionItemHolder>
{
    String mTitle;
    
    public CaptionSettingsItem(final String mTitle) {
        super(2, false, "");
        this.mTitle = mTitle;
    }
    
    public String getTitle() {
        return this.mTitle;
    }
    
    public static class CaptionItemHolder extends BaseItem.ItemViewHolder
    {
        public CaptionItemHolder(final Context context, final ViewGroup viewGroup) {
            super(R.layout.setting_caption, context, viewGroup);
        }
        
        @Override
        public void setEnabled(final boolean b) {
        }
    }
}
