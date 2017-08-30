// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer;

import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import android.view.View;

public class StackTransformer extends BaseTransformer
{
    @Override
    public void transformRaw(final View view, float min) {
        float translationX;
        if (min < 0.0f) {
            translationX = 0.0f;
        }
        else {
            translationX = -view.getWidth() * min;
        }
        view.setTranslationX(translationX);
        view.setElevation(LauncherUtils.dpToPx(10.0f, view.getContext()) * -(min - 2.0f));
        float alpha;
        if (min > 0.0f) {
            alpha = 1.0f - min;
        }
        else {
            alpha = 1.0f;
        }
        view.setAlpha(alpha);
        view.setPivotY((float)((view.getTop() + view.getBottom()) / 2));
        view.setPivotX((float)((view.getLeft() + view.getRight()) / 2));
        if (min < 0.0f) {
            min = Math.min(-(min - 1.0f), 1.1f);
            view.setScaleX(min);
            view.setScaleY(min);
        }
    }
}
