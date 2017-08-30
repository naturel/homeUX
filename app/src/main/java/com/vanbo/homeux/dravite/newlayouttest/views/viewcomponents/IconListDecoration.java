// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.viewcomponents;

import android.view.View;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;

public class IconListDecoration extends RecyclerView.ItemDecoration
{
    int mSpace;
    
    public IconListDecoration(final int mSpace) {
        this.mSpace = mSpace;
    }
    
    @Override
    public void getItemOffsets(final Rect rect, final View view, final RecyclerView recyclerView, final RecyclerView.State state) {
        rect.left = this.mSpace;
        rect.right = this.mSpace;
        rect.bottom = this.mSpace;
        rect.top = this.mSpace;
    }
}
