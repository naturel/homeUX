// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_helpers;

import android.os.IBinder;
import android.graphics.PointF;
import android.content.Context;
import android.app.WallpaperManager;

public class CustomWallpaperManager
{
    WallpaperManager mWallpaperManager;
    float xOffset;
    float yOffset;
    
    public CustomWallpaperManager(final Context context) {
        this.mWallpaperManager = WallpaperManager.getInstance(context);
    }
    
    public PointF getWallpaperOffsets() {
        return new PointF(this.xOffset, this.yOffset);
    }
    
    public void setWallpaperOffsets(final IBinder binder, final float xOffset, final float yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        if (binder != null && binder.isBinderAlive()) {
            this.mWallpaperManager.setWallpaperOffsets(binder, xOffset, yOffset);
        }
    }
}
