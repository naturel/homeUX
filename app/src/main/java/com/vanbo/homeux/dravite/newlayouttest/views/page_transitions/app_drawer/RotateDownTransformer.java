// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer;

import android.view.View;
import android.support.v4.view.ViewPager;

public class RotateDownTransformer implements ViewPager.PageTransformer
{
    private static final float ROT_MOD = -15.0f;
    
    @Override
    public void transformPage(final View view, final float n) {
        if (n < -1.0f) {
            view.setAlpha(0.0f);
            view.setTranslationZ(-1.0f);
            return;
        }
        if (n <= 1.0f) {
            final float n2 = view.getWidth();
            final float pivotY = view.getHeight();
            view.setAlpha(1.0f);
            view.setTranslationZ(0.0f);
            view.setPivotX(0.5f * n2);
            view.setPivotY(pivotY);
            view.setRotation(-15.0f * n * -1.25f);
            return;
        }
        view.setAlpha(0.0f);
        view.setTranslationZ(-1.0f);
    }
}
