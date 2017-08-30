// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views;

import android.view.View;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

public class FolderRecyclerView extends RecyclerView
{
    public FolderRecyclerView(final Context context) {
        this(context, null);
    }
    
    public FolderRecyclerView(final Context context, @Nullable final AttributeSet set) {
        this(context, set, 0);
    }
    
    public FolderRecyclerView(final Context context, @Nullable final AttributeSet set, final int n) {
        super(context, set, n);
    }
    
    @Override
    public View findChildViewUnder(final float n, final float n2) {
        for (int i = this.getChildCount() - 1; i >= 0; --i) {
            final View child = this.getChildAt(i);
            if (n >= child.getLeft() && n <= child.getRight() && n2 >= child.getTop() && n2 <= child.getBottom()) {
                return child;
            }
        }
        return null;
    }
}
