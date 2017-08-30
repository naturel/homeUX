// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.settings.items;

import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import com.dravite.homeux.R;

public abstract class BaseItem<T extends BaseItem.ItemViewHolder>
{
    public static final int TYPE_CAPTION = 2;
    public static final int TYPE_GENERIC = 0;
    public static final int TYPE_NONE = -1;
    public static final int TYPE_SWITCH = 1;
    private boolean mEnabled;
    private ItemViewHolder.OnItemClickListener mListener;
    private boolean mPremium;
    private String mTag;
    private int mType;
    
    public BaseItem(final int mType, final boolean mPremium, final String mTag) {
        this.mEnabled = true;
        this.mType = mType;
        this.mPremium = mPremium;
        this.mTag = mTag;
    }
    
    public ItemViewHolder.OnItemClickListener getAction() {
        return this.mListener;
    }
    
    public String getTag() {
        return this.mTag;
    }
    
    public int getType() {
        return this.mType;
    }
    
    public boolean isEnabled() {
        return this.mEnabled;
    }
    
    public boolean isPremium() {
        return this.mPremium;
    }
    
    public void setAction(final ItemViewHolder.OnItemClickListener mListener) {
        this.mListener = mListener;
    }
    
    public void setEnabled(final boolean mEnabled) {
        this.mEnabled = mEnabled;
    }
    
    public abstract static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        public TextView mDescription;
        boolean mEnabled;
        public ImageView mIcon;
        LinearLayout.LayoutParams mParamsDefault;
        LinearLayout.LayoutParams mParamsMinimized;
        public TextView mTitle;
        
        public ItemViewHolder(final int n, final Context context, final ViewGroup viewGroup) {
            super(LayoutInflater.from(context).inflate(n, viewGroup, false));
            this.mParamsDefault = new LinearLayout.LayoutParams(-1, -2);
            this.mParamsMinimized = new LinearLayout.LayoutParams(-1, 0);
            this.mEnabled = true;
            this.mTitle = (TextView)this.itemView.findViewById(R.id.title_text);
            this.mDescription = (TextView)this.itemView.findViewById(R.id.desc_text);
            this.mIcon = (ImageView)this.itemView.findViewById(R.id.icon);
        }
        
        public boolean isEnabled() {
            return this.mEnabled;
        }
        
        public void setEnabled(final boolean mEnabled) {
            final float n = 0.57f;
            this.mEnabled = mEnabled;
            final TextView mTitle = this.mTitle;
            float alpha;
            if (mEnabled) {
                alpha = 0.89f;
            }
            else {
                alpha = 0.32f;
            }
            mTitle.setAlpha(alpha);
            if (this.mDescription != null) {
                final TextView mDescription = this.mDescription;
                float alpha2;
                if (mEnabled) {
                    alpha2 = 0.57f;
                }
                else {
                    alpha2 = 0.12f;
                }
                mDescription.setAlpha(alpha2);
            }
            if (this.mIcon != null) {
                final ImageView mIcon = this.mIcon;
                float alpha3;
                if (mEnabled) {
                    alpha3 = n;
                }
                else {
                    alpha3 = 0.12f;
                }
                mIcon.setAlpha(alpha3);
            }
        }
        
        public void setIcon(final int imageResource) {
            if (this.mIcon != null) {
                this.mIcon.setImageResource(imageResource);
            }
        }
        
        public void setOnItemClickListener(final OnItemClickListener onItemClickListener, final BaseItem baseItem, final RecyclerView.Adapter adapter) {
            this.itemView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    if (ItemViewHolder.this.mEnabled) {
                        onItemClickListener.onClick(view, baseItem, adapter, ((RecyclerView.ViewHolder)ItemViewHolder.this).getAdapterPosition());
                    }
                }
            });
        }
        
        public void setTexts(final String text, final String text2) {
            if (this.mDescription != null) {
                if (text2 == null || text2.equals("")) {
                    this.mDescription.setLayoutParams((ViewGroup.LayoutParams)this.mParamsMinimized);
                }
                else {
                    this.mDescription.setLayoutParams((ViewGroup.LayoutParams)this.mParamsDefault);
                    this.mDescription.setText((CharSequence)text2);
                }
            }
            this.mTitle.setText((CharSequence)text);
        }
        
        public interface OnItemClickListener
        {
            void onClick(final View p0, final BaseItem p1, final RecyclerView.Adapter p2, final int p3);
        }
    }
}
