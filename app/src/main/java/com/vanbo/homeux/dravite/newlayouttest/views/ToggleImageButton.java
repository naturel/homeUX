// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;

import android.util.AttributeSet;
import android.content.Context;
import android.widget.Checkable;
import android.widget.ImageButton;
import com.dravite.homeux.R;

public class ToggleImageButton extends android.support.v7.widget.AppCompatImageButton implements Checkable
{
    private boolean mOnlyCheck;
    private OnCheckedChangeListener onCheckedChangeListener;
    
    public ToggleImageButton(final Context context) {
        super(context);
    }
    
    public ToggleImageButton(final Context context, final AttributeSet set) {
        super(context, set);
        this.init(set);
    }
    
    public ToggleImageButton(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.init(set);
    }
    
    private void init(final AttributeSet set) {
        //final TypedArray obtainStyledAttributes = this.getContext().obtainStyledAttributes(set, R.styleable.ToggleImageButton);
        //this.setChecked(obtainStyledAttributes.getBoolean(0, false));
        //this.mOnlyCheck = obtainStyledAttributes.getBoolean(1, false);
        //obtainStyledAttributes.recycle();
    }
    
    public OnCheckedChangeListener getOnCheckedChangeListener() {
        return this.onCheckedChangeListener;
    }
    
    public boolean isChecked() {
        return this.isSelected();
    }
    
    public boolean performClick() {
        if (this.mOnlyCheck) {
            this.setChecked(true);
        }
        else {
            this.toggle();
        }
        return super.performClick();
    }
    
    public void setChecked(final boolean selected) {
        this.setSelected(selected);
        if (this.onCheckedChangeListener != null) {
            this.onCheckedChangeListener.onCheckedChanged(this, selected);
        }
    }
    
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            this.setImageTintList(ColorStateList.valueOf(13421773));
            return;
        }
        this.setImageTintList(ColorStateList.valueOf(7303024));
    }
    
    public void setOnCheckedChangeListener(final OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }
    
    public void toggle() {
        this.setChecked(!this.isChecked());
    }
    
    public interface OnCheckedChangeListener
    {
        void onCheckedChanged(final ToggleImageButton p0, final boolean p1);
    }
}
