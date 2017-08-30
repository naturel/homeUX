// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer;

import android.view.View;
import android.graphics.Matrix;
import android.graphics.Camera;

public class TabletTransformer extends BaseTransformer
{
    private static final Camera OFFSET_CAMERA;
    private static final Matrix OFFSET_MATRIX;
    private static final float[] OFFSET_TEMP_FLOAT;
    
    static {
        OFFSET_MATRIX = new Matrix();
        OFFSET_CAMERA = new Camera();
        OFFSET_TEMP_FLOAT = new float[2];
    }
    
    protected static final float getOffsetXForRotation(float n, final int n2, final int n3) {
        TabletTransformer.OFFSET_MATRIX.reset();
        TabletTransformer.OFFSET_CAMERA.save();
        TabletTransformer.OFFSET_CAMERA.rotateY(Math.abs(n));
        TabletTransformer.OFFSET_CAMERA.getMatrix(TabletTransformer.OFFSET_MATRIX);
        TabletTransformer.OFFSET_CAMERA.restore();
        TabletTransformer.OFFSET_MATRIX.preTranslate(-n2 * 0.5f, -n3 * 0.5f);
        TabletTransformer.OFFSET_MATRIX.postTranslate(n2 * 0.5f, n3 * 0.5f);
        TabletTransformer.OFFSET_TEMP_FLOAT[0] = n2;
        TabletTransformer.OFFSET_TEMP_FLOAT[1] = n3;
        TabletTransformer.OFFSET_MATRIX.mapPoints(TabletTransformer.OFFSET_TEMP_FLOAT);
        final float n4 = n2;
        final float n5 = TabletTransformer.OFFSET_TEMP_FLOAT[0];
        if (n > 0.0f) {
            n = 1.0f;
        }
        else {
            n = -1.0f;
        }
        return n * (n4 - n5);
    }
    
    @Override
    public void transformRaw(final View view, float rotationY) {
        float n;
        if (rotationY < 0.0f) {
            n = 30.0f;
        }
        else {
            n = -30.0f;
        }
        rotationY = n * Math.abs(rotationY);
        view.setTranslationX(getOffsetXForRotation(rotationY, view.getWidth(), view.getHeight()));
        view.setPivotX(view.getWidth() * 0.5f);
        view.setPivotY(0.0f);
        view.setRotationY(rotationY);
    }
}
