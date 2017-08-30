// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer;

import android.view.View;

public class ZoomOutSlideTransformer extends BaseTransformer
{
    private static final float MIN_ALPHA = 0.5f;
    private static final float MIN_SCALE = 0.85f;
    
    @Override
    public void transformRaw(final View view, final float n) {
        final float n2 = view.getHeight();
        final float max = Math.max(0.85f, 1.0f - Math.abs(n));
        final float n3 = (1.0f - max) * n2 / 2.0f;
        final float n4 = view.getWidth() * (1.0f - max) / 2.0f;
        view.setPivotY(0.5f * n2);
        if (n < 0.0f) {
            view.setTranslationX(n4 - n3 / 2.0f);
        }
        else {
            view.setTranslationX(-n4 + n3 / 2.0f);
        }
        view.setScaleX(max);
        view.setScaleY(max);
        view.setAlpha((max - 0.85f) / 0.14999998f * 0.5f + 0.5f);
    }
}
