// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.iconpacks;

import java.util.Observable;

public class LicensingObserver extends Observable
{
    private static LicensingObserver instance;
    
    static {
        LicensingObserver.instance = new LicensingObserver();
    }
    
    public static LicensingObserver getInstance() {
        return LicensingObserver.instance;
    }
    
    public void updateValue(final Object o) {
        synchronized (this) {
            this.setChanged();
            this.notifyObservers(o);
        }
    }
}
