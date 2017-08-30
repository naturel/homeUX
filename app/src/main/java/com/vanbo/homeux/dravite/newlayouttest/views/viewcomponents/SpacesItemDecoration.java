// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.viewcomponents;

import android.view.View;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration
{
    private int space;
    
    public SpacesItemDecoration(final int space) {
        this.space = space;
    }
    
    @Override
    public void getItemOffsets(final Rect rect, final View view, final RecyclerView recyclerView, final RecyclerView.State state) {
        rect.left = this.space;
        rect.right = this.space;
        rect.bottom = this.space * 2;
    }
}
