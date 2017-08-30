// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer;

import android.view.View;

public class RotateUpTransformer extends BaseTransformer
{
    private static final float ROT_MOD = -15.0f;
    
    @Override
    public void transformRaw(final View view, final float n) {
        view.setPivotX(0.5f * view.getWidth());
        view.setPivotY(0.0f);
        view.setTranslationX(0.0f);
        view.setRotation(-15.0f * n);
    }
}
