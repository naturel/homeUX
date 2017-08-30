// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_adapters;

import android.graphics.Typeface;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.View;
import android.content.Context;
import android.widget.BaseAdapter;

public class FontAdapter extends BaseAdapter
{
    public static String[] fonts = new String[] { "sans-serif-thin", "sans-serif-light", "sans-serif-condensed", "sans-serif", "sans-serif-medium", "sans-serif-black" };
    private Context mContext;
    
    static {
    }
    
    public FontAdapter(final Context mContext) {
        this.mContext = mContext;
    }
    
    public int getCount() {
        return FontAdapter.fonts.length;
    }
    
    public Object getItem(final int n) {
        return FontAdapter.fonts[n];
    }
    
    public long getItemId(final int n) {
        return 0L;
    }
    
    public View getView(final int n, View inflate, final ViewGroup viewGroup) {
        inflate = View.inflate(this.mContext, android.R.layout.simple_list_item_1, (ViewGroup)null);
        final TextView textView = (TextView)inflate.findViewById(android.R.id.text1);
        textView.setText((CharSequence)FontAdapter.fonts[n]);
        textView.setTypeface(Typeface.create(FontAdapter.fonts[n], 0));
        return inflate;
    }
}
