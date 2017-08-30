// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_helpers;

import com.vanbo.homeux.dravite.newlayouttest.iconpacks.LicensingObserver;
import com.vanbo.homeux.dravite.newlayouttest.LauncherLog;
import android.content.Intent;
import android.content.Context;
import android.content.BroadcastReceiver;

public class LicensingBroadcastReceiver extends BroadcastReceiver
{
    public void onReceive(final Context context, final Intent intent) {
        LauncherLog.d("LicensingReceiver", "Got a broadcast...");
        LicensingObserver.getInstance().updateValue(intent);
    }
}
