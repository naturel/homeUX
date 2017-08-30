// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer;

import android.view.View;
import android.support.v4.view.ViewPager;

public abstract class BaseTransformer implements ViewPager.PageTransformer
{
    @Override
    public void transformPage(final View view, final float n) {
        if (Math.abs(n) > 1.0f) {
            view.setVisibility(View.INVISIBLE);
            return;
        }
        view.setVisibility(View.VISIBLE);
        this.transformRaw(view, n);
    }
    
    public abstract void transformRaw(final View p0, final float p1);
}
