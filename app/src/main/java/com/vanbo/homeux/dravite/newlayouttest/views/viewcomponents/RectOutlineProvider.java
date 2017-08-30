// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.viewcomponents;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

public class RectOutlineProvider extends ViewOutlineProvider
{
    public void getOutline(final View view, final Outline outline) {
        outline.setRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }
}
