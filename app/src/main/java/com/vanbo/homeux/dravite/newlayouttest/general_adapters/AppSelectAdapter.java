// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_adapters;

import android.content.res.ColorStateList;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import java.util.Comparator;
import java.util.Collections;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.helpers.AppLauncherActivityInfoComparator;
import android.os.Process;
import android.content.pm.LauncherApps;
import android.content.pm.LauncherActivityInfo;
import java.util.List;
import android.content.Context;
import android.widget.BaseAdapter;
import com.dravite.homeux.R;


public class AppSelectAdapter extends BaseAdapter
{
    private final Context mContext;
    public List<LauncherActivityInfo> mInfos;
    
    public AppSelectAdapter(final Context mContext) {
        this.mContext = mContext;
        Collections.sort(this.mInfos = (List<LauncherActivityInfo>)((LauncherApps)mContext.getSystemService(Context.LAUNCHER_APPS_SERVICE)).getActivityList((String)null, Process.myUserHandle()), new AppLauncherActivityInfoComparator(mContext));
    }
    
    public int getCount() {
        return this.mInfos.size();
    }
    
    public Object getItem(final int n) {
        return null;
    }
    
    public long getItemId(final int n) {
        return 0L;
    }
    
    public View getView(final int n, View inflate, final ViewGroup viewGroup) {
        AppHolder tag;
        if (inflate == null) {
            inflate = LayoutInflater.from(this.mContext).inflate(R.layout.folder_app_list_item, viewGroup, false);
            tag = new AppHolder();
            tag.name = (TextView)inflate.findViewById(R.id.name);
            tag.icon = (ImageView)inflate.findViewById(R.id.appSelector);
            inflate.setTag((Object)tag);
        }
        else {
            tag = (AppHolder)inflate.getTag();
        }
        tag.name.setText(this.mInfos.get(n).getLabel());
        tag.icon.setImageDrawable(this.mInfos.get(n).getIcon(0));
        tag.icon.setImageTintList((ColorStateList)null);
        return inflate;
    }
    
    public static class AppHolder
    {
        ImageView icon;
        TextView name;
    }
}
