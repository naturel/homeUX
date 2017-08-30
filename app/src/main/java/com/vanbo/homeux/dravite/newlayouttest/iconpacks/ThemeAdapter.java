// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.iconpacks;

import android.widget.TextView;
import android.view.ViewGroup;
import android.view.View;
import java.util.List;
import android.content.Context;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.IconPackManager;
import android.widget.ArrayAdapter;
import com.dravite.homeux.R;

public class ThemeAdapter extends ArrayAdapter<IconPackManager.Theme>
{
    public ThemeAdapter(final Context context, final List<IconPackManager.Theme> list) {
        super(context, 0, (List)list);
    }
    
    public View getView(final int n, View inflate, final ViewGroup viewGroup) {
        ThemeHolder tag;
        if (inflate == null) {
            inflate = View.inflate(this.getContext(), R.layout.folder_drop_icon, null);
            tag = new ThemeHolder();
            tag.itemView = (TextView)inflate;
            inflate.setTag(tag);
        }
        else {
            tag = (ThemeHolder)inflate.getTag();
        }
        final IconPackManager.Theme theme = this.getItem(n);
        tag.itemView.setText(theme.label);
        tag.itemView.setCompoundDrawablesRelative(theme.icon, null, null, null);
        return inflate;
    }
    
    static class ThemeHolder
    {
        TextView itemView;
    }
}
