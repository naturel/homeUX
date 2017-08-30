// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer;

import android.view.View;

public class CubeOutTransformer extends BaseTransformer
{
    @Override
    public void transformRaw(final View view, final float n) {
        float pivotX = 0.0f;
        if (n < 0.0f) {
            pivotX = view.getWidth();
        }
        view.setPivotX(pivotX);
        view.setPivotY(view.getHeight() * 0.5f);
        view.setRotationY(90.0f * n);
    }
}
