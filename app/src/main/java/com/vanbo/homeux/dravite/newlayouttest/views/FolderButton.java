// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views;

import com.vanbo.homeux.dravite.newlayouttest.general_helpers.ColorUtils;
import android.graphics.Typeface;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.FolderStructure;
import android.text.TextUtils;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;
import com.dravite.homeux.R;

public class FolderButton extends android.support.v7.widget.AppCompatTextView
{
    private boolean isSelected;
    
    public FolderButton(final Context context) {
        this(context, null);
    }
    
    public FolderButton(final Context context, final AttributeSet set) {
        this(context, set, 2130772192);
    }
    
    public FolderButton(final Context context, final AttributeSet set, int dpToPx) {
        super(context, set, dpToPx);
        this.isSelected = false;
        dpToPx = LauncherUtils.dpToPx(2.0f, this.getContext());
        final int dpToPx2 = LauncherUtils.dpToPx(10.0f, this.getContext());
        this.setBackground(this.getContext().getDrawable(R.drawable.circle_background_folder));
        this.setEllipsize(TextUtils.TruncateAt.END);
        this.setElevation(0.0f);
        this.setTranslationZ(0.0f);
        this.setClipToOutline(true);
        this.setSingleLine(true);
        this.setPadding(dpToPx, dpToPx2, dpToPx, dpToPx2);
        this.setCompoundDrawablePadding(LauncherUtils.dpToPx(10.0f, this.getContext()));
    }
    
    public void assignFolder(final FolderStructure.Folder folder) {
        this.setText((CharSequence)folder.folderName);
        final Drawable drawable = new ScaleDrawable(this.getContext().getDrawable(this.getResources().getIdentifier(folder.folderIconRes, "drawable", this.getContext().getPackageName())), 0, (float)LauncherUtils.dpToPx(11.0f, this.getContext()), (float)LauncherUtils.dpToPx(11.0f, this.getContext())).getDrawable();
        drawable.setTint(-1);
        drawable.setBounds(0, 0, LauncherUtils.dpToPx(24.0f, this.getContext()), LauncherUtils.dpToPx(24.0f, this.getContext()));
        this.setCompoundDrawablesRelative((Drawable)null, drawable, (Drawable)null, (Drawable)null);
    }
    
    public boolean canScrollHorizontally(final int n) {
        return false;
    }
    
    public boolean canScrollVertically(final int n) {
        return false;
    }
    
    public void deselect() {
        this.setBackground((Drawable)null);
        if (this.getBackground() == null) {
            this.setBackground(this.getContext().getDrawable(R.drawable.circle_background_folder));
        }
        ((LayerDrawable)this.getBackground()).getDrawable(1).setTintMode(PorterDuff.Mode.MULTIPLY);
        ((LayerDrawable)this.getBackground()).getDrawable(1).setTint(0);
        this.setTypeface((Typeface)null, 0);
        this.setTextColor(-1);
        final Drawable drawable = this.getCompoundDrawablesRelative()[1];
        drawable.setTint(-1);
        drawable.setBounds(0, 0, LauncherUtils.dpToPx(24.0f, this.getContext()), LauncherUtils.dpToPx(24.0f, this.getContext()));
        this.setCompoundDrawablesRelative((Drawable)null, drawable, (Drawable)null, (Drawable)null);
        this.isSelected = false;
    }
    
    public boolean isSelected() {
        return this.isSelected;
    }
    
    protected void onMeasure(int n, int measuredHeight) {
        super.onMeasure(n, measuredHeight);
        if (this.getBackground() != null) {
            n = this.getMeasuredWidth() / 2 - LauncherUtils.dpToPx(22.0f, this.getContext());
            measuredHeight = this.getMeasuredHeight();
            ((LayerDrawable)this.getBackground()).setLayerInset(1, n, 0, n, measuredHeight - LauncherUtils.dpToPx(44.0f, this.getContext()));
        }
    }
    
    public void select(int n) {
        final Drawable drawable = this.getCompoundDrawablesRelative()[1];
        this.setBackground(this.getContext().getDrawable(R.drawable.circle_background_folder));
        ((LayerDrawable)this.getBackground()).getDrawable(1).setTintMode(PorterDuff.Mode.SRC_IN);
        ((LayerDrawable)this.getBackground()).getDrawable(1).setTint(n);
        if (ColorUtils.isBrightColor(n)) {
            n = 1476395008;
        }
        else {
            n = -1;
        }
        this.setTextColor(-1);
        drawable.setTint(n);
        this.setTypeface(this.getTypeface(), 1);
        this.isSelected = true;
    }
}
