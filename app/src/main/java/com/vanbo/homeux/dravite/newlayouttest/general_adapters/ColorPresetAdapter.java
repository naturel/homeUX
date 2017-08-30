// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_adapters;

import android.widget.LinearLayout;
import android.view.ViewGroup;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.res.ColorStateList;
import android.view.ViewGroup.LayoutParams;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import com.dravite.homeux.R;

public class ColorPresetAdapter extends RecyclerView.Adapter<ColorPresetAdapter.Holder>
{
    int[] colors;
    Context mContext;
    ColorListener mListener;
    
    public ColorPresetAdapter(final Context mContext, final ColorListener mListener) {
        this.colors = new int[] { -769226, -1499549, -6543440, -10011977, -12627531, -14575885, -16537100, -16728876, -16738680, -11751600, -7617718, -3285959, -5317, -16121, -26624, -43230, -8825528, -6381922, -10453621 };
        this.mContext = mContext;
        this.mListener = mListener;
    }
    
    @Override
    public int getItemCount() {
        return this.colors.length;
    }
    
    public void onBindViewHolder(final Holder holder, final int n) {
        holder.t.setLayoutParams(new ViewGroup.LayoutParams(144, 144));
        holder.t.setBackgroundTintList(ColorStateList.valueOf(this.colors[n]));
        holder.t.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ColorPresetAdapter.this.mListener.onSelected(ColorPresetAdapter.this.colors[n]);
            }
        });
    }
    
    public Holder onCreateViewHolder(final ViewGroup viewGroup, final int n) {
        return new Holder(View.inflate(this.mContext, R.layout.color_preset_item, (ViewGroup)null));
    }
    
    public interface ColorListener
    {
        void onSelected(final int p0);
    }
    
    public static class Holder extends RecyclerView.ViewHolder
    {
        LinearLayout t;
        
        public Holder(final View view) {
            super(view);
            this.t = (LinearLayout)view.findViewById(R.id.color_field);
        }
    }
}
