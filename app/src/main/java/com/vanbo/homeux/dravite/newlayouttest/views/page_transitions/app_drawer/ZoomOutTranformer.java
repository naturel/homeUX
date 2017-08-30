// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer;

import android.view.View;

public class ZoomOutTranformer extends BaseTransformer
{
    @Override
    public void transformRaw(final View view, final float n) {
        final float n2 = 1.0f + Math.abs(n);
        view.setScaleX(n2);
        view.setScaleY(n2);
        view.setAlpha(1.0f - Math.abs(n));
        view.setTranslationX(view.getWidth() * -n);
    }
}
