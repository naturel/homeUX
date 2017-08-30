// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_helpers;

import android.content.ComponentName;

public class PreferenceHolder
{
    public ComponentName fabComponent;
    public int gridHeight;
    public int gridWidth;
    public boolean isFirstStart;
    public int pagerTransition;
    public boolean showCard;
    public boolean useDirectReveal;
    
    public PreferenceHolder() {
        this.showCard = true;
        this.pagerTransition = 4;
        this.gridHeight = 5;
        this.gridWidth = 4;
        this.isFirstStart = true;
        this.fabComponent = null;
        this.useDirectReveal = true;
    }
    
    public int gridSize() {
        return this.gridHeight * this.gridWidth;
    }
}
