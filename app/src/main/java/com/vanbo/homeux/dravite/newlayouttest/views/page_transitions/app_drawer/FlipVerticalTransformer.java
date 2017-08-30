// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer;

import android.view.View;

public class FlipVerticalTransformer extends BaseTransformer
{
    @Override
    public void transformRaw(final View view, float alpha) {
        final float rotationX = -180.0f * alpha;
        view.setTranslationX(-view.getWidth() * alpha);
        if (rotationX > 90.0f || rotationX < -90.0f) {
            alpha = 0.0f;
        }
        else {
            alpha = 1.0f;
        }
        view.setAlpha(alpha);
        view.setPivotX(view.getWidth() * 0.5f);
        view.setPivotY(view.getHeight() * 0.5f);
        view.setRotationX(rotationX);
    }
}
