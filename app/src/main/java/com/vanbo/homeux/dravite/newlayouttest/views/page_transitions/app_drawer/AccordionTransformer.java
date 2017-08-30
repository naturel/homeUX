// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer;

import android.view.View;

public class AccordionTransformer extends BaseTransformer
{
    @Override
    public void transformRaw(final View view, float scaleX) {
        view.setTranslationZ(0.0f);
        float pivotX;
        if (scaleX >= 0.0f) {
            pivotX = 0.0f;
        }
        else {
            pivotX = view.getWidth();
        }
        view.setPivotX(pivotX);
        if (scaleX < 0.0f) {
            ++scaleX;
        }
        else {
            scaleX = 1.0f - scaleX;
        }
        view.setScaleX(scaleX);
    }
}
