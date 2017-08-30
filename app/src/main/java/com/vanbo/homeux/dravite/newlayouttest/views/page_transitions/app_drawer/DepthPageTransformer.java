// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer;

import android.view.View;

public class DepthPageTransformer extends BaseTransformer
{
    private static final float MIN_SCALE = 0.75f;
    
    @Override
    public void transformRaw(final View view, final float n) {
        if (n <= 0.0f) {
            view.setAlpha(1.0f);
            view.setTranslationX(0.0f);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
            view.setTranslationZ(0.0f);
            return;
        }
        final float n2 = 0.75f + 0.25f * (1.0f - Math.abs(n));
        view.setAlpha(1.0f - Math.abs(n));
        view.setTranslationX(view.getWidth() * -n);
        view.setScaleX(n2);
        view.setScaleY(n2);
        view.setTranslationZ(-1.0f);
    }
}
