// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer;

import android.view.View;

public class ScaleInOutTransformer extends BaseTransformer
{
    @Override
    public void transformRaw(final View view, float n) {
        float pivotX;
        if (n > 0.0f) {
            pivotX = 0.0f;
        }
        else {
            pivotX = view.getWidth();
        }
        view.setPivotX(pivotX);
        view.setPivotY(view.getHeight() / 2.0f);
        if (n < 0.0f) {
            ++n;
        }
        else {
            n = 1.0f - n;
        }
        view.setScaleX(n);
        view.setScaleY(n);
    }
}
