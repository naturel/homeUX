// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer;

import android.view.View;
import android.support.v4.view.ViewPager;

public class CubeInTransformer implements ViewPager.PageTransformer
{
    @Override
    public void transformPage(final View view, final float n) {
        if (n < -1.0f) {
            view.setAlpha(0.0f);
            view.setTranslationZ(-1.0f);
            return;
        }
        if (n <= 1.0f) {
            view.setAlpha(1.0f);
            view.setTranslationZ(0.0f);
            float pivotX;
            if (n > 0.0f) {
                pivotX = 0.0f;
            }
            else {
                pivotX = view.getWidth();
            }
            view.setPivotX(pivotX);
            view.setPivotY(0.0f);
            view.setRotationY(-90.0f * n);
            return;
        }
        view.setTranslationZ(-1.0f);
        view.setAlpha(0.0f);
    }
}
