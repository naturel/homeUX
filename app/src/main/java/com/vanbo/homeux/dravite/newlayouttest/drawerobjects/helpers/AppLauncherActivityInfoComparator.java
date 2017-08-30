// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.drawerobjects.helpers;

import java.io.File;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.FileManager;
import android.content.ComponentName;
import java.util.HashMap;
import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import java.util.Comparator;

public class AppLauncherActivityInfoComparator implements Comparator<LauncherActivityInfo>
{
    String loadedLabel;
    String loadedLabel2;
    private Context mContext;
    private HashMap<ComponentName, String> mCustomLabelMap;
    
    public AppLauncherActivityInfoComparator(final Context mContext) {
        this.mCustomLabelMap = new HashMap<ComponentName, String>();
        this.mContext = mContext;
    }
    
    @Override
    public int compare(final LauncherActivityInfo launcherActivityInfo, final LauncherActivityInfo launcherActivityInfo2) {
        this.loadedLabel = this.mCustomLabelMap.get(launcherActivityInfo.getComponentName());
        this.loadedLabel2 = this.mCustomLabelMap.get(launcherActivityInfo2.getComponentName());
        String s;
        if (this.loadedLabel == null) {
            s = launcherActivityInfo.getLabel().toString();
        }
        else {
            s = this.loadedLabel;
        }
        final String lowerCase = s.toLowerCase();
        String s2;
        if (this.loadedLabel2 == null) {
            s2 = launcherActivityInfo2.getLabel().toString();
        }
        else {
            s2 = this.loadedLabel2;
        }
        return lowerCase.compareTo(s2.toLowerCase());
    }
    
    String readLabelFile(final ComponentName componentName) {
        return FileManager.readTextFile(this.mContext, "/apps/" + componentName.getPackageName(), componentName.getClassName() + "_l");
    }
    
    public void refresh() {
        this.mCustomLabelMap.clear();
        new File(this.mContext.getCacheDir().getPath() + "/" + "/apps/");
    }
}
