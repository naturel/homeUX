// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views;

import android.graphics.drawable.Drawable;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.FolderStructure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.TextView;

public class FolderNameLabel extends android.support.v7.widget.AppCompatTextView
{
    public FolderNameLabel(final Context context) {
        this(context, null);
    }
    
    public FolderNameLabel(final Context context, @Nullable final AttributeSet set) {
        this(context, set, 16842884);
    }
    
    public FolderNameLabel(final Context context, @Nullable final AttributeSet set, final int n) {
        this(context, set, n, 0);
    }
    
    public FolderNameLabel(final Context context, @Nullable final AttributeSet set, final int n, final int n2) {
        super(context, set, n);
        this.setAllCaps(true);
    }
    
    public void assignFolder(final FolderStructure.Folder folder) {
        this.setText((CharSequence)folder.folderName);
        final Drawable drawable = this.getContext().getDrawable(this.getResources().getIdentifier(folder.folderIconRes, "drawable", this.getContext().getPackageName()));
        drawable.setTint(-1);
        this.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, (Drawable)null, (Drawable)null, (Drawable)null);
    }
}
