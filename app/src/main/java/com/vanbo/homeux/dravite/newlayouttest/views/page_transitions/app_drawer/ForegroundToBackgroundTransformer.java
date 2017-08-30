// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer;

import android.view.View;

public class ForegroundToBackgroundTransformer extends BaseTransformer
{
    @Override
    public void transformRaw(final View view, float translationX) {
        final float n = view.getHeight();
        final float n2 = view.getWidth();
        float abs;
        if (translationX > 0.0f) {
            abs = 1.0f;
        }
        else {
            abs = Math.abs(1.0f + translationX);
        }
        final float min = Math.min(abs, 1.0f);
        view.setScaleX(min);
        view.setScaleY(min);
        view.setPivotX(n2 * 0.5f);
        view.setPivotY(n * 0.5f);
        if (translationX > 0.0f) {
            translationX *= n2;
        }
        else {
            translationX = -n2 * translationX * 0.25f;
        }
        view.setTranslationX(translationX);
    }
}
