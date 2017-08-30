// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.drawerobjects;

import java.io.Serializable;

public class QuickAction implements Serializable
{
    public String iconRes;
    public String intentClass;
    public String intentPackage;
    public int qaIndex;
    
    public QuickAction() {
    }
    
    public QuickAction(final String iconRes, final String intentPackage, final String intentClass, final int qaIndex) {
        this.iconRes = iconRes;
        this.intentPackage = intentPackage;
        this.intentClass = intentClass;
        this.qaIndex = qaIndex;
    }
}
