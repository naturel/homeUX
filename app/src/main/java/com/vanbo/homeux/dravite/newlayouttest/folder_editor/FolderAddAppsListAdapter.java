// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.folder_editor;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.content.res.ColorStateList;
import android.app.Activity;
import com.vanbo.homeux.dravite.newlayouttest.views.AppIconView;
import android.support.v7.widget.GridLayoutManager;
import android.graphics.drawable.LayerDrawable;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import java.util.Iterator;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.Application;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.DrawerObject;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.FolderStructure;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.JsonHelper;
import java.util.Collection;
import java.util.Comparator;
import java.util.Collections;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.helpers.AppLauncherActivityInfoComparator;
import android.os.Process;
import android.content.pm.LauncherApps;
import android.content.Context;
import android.content.ComponentName;
import java.util.ArrayList;
import android.content.pm.LauncherActivityInfo;
import java.util.List;
import android.support.v7.widget.RecyclerView;
import android.graphics.PorterDuff;
import com.dravite.homeux.R;


public class FolderAddAppsListAdapter extends RecyclerView.Adapter<FolderAddAppsListAdapter.AppViewHolder>
{
    private List<LauncherActivityInfo> mAppInfos;
    private List<LauncherActivityInfo> mAppInfosWithOtherFolders;
    private List<LauncherActivityInfo> mAppInfosWithoutOtherFolders;
    public ArrayList<ComponentName> mContainsList;
    private Context mContext;
    public ArrayList<ComponentName> mSelectedAppsList;
    
    public FolderAddAppsListAdapter(final Context mContext, final ArrayList<ComponentName> mSelectedAppsList, final ArrayList<ComponentName> mContainsList) {
        this.mContext = mContext;
        Collections.sort(this.mAppInfos = (List<LauncherActivityInfo>)((LauncherApps)mContext.getSystemService(Context.LAUNCHER_APPS_SERVICE)).getActivityList((String)null, Process.myUserHandle()), new AppLauncherActivityInfoComparator(mContext));
        this.mSelectedAppsList = mSelectedAppsList;
        this.mContainsList = mContainsList;
        final ArrayList<LauncherActivityInfo> list = new ArrayList<LauncherActivityInfo>(this.mAppInfos);
        final List<ComponentName> loadHiddenAppList = JsonHelper.loadHiddenAppList(this.mContext);
        int n;
        for (int i = 0; i < list.size(); i = n + 1) {
            if (!loadHiddenAppList.contains(list.get(i).getComponentName())) {
                n = i;
                if (!this.mContainsList.contains(list.get(i).getComponentName())) {
                    continue;
                }
            }
            list.remove(i);
            n = i - 1;
        }
        this.mAppInfosWithoutOtherFolders = new ArrayList<LauncherActivityInfo>(list);
        this.mAppInfosWithOtherFolders = new ArrayList<LauncherActivityInfo>(list);
        for (final FolderStructure.Folder folder : LauncherActivity.mFolderStructure.folders) {
            final Iterator<FolderStructure.Page> iterator2 = folder.pages.iterator();
            while (iterator2.hasNext()) {
                for (final DrawerObject drawerObject : iterator2.next().items) {
                    int n2;
                    for (int j = 0; j < this.mAppInfosWithoutOtherFolders.size(); j = n2 + 1) {
                        n2 = j;
                        if (!folder.isAllFolder) {
                            n2 = j;
                            if (drawerObject instanceof Application) {
                                n2 = j;
                                if (this.mAppInfosWithoutOtherFolders.get(j).getComponentName().equals((Object)new ComponentName(((Application)drawerObject).packageName, ((Application)drawerObject).className))) {
                                    this.mAppInfosWithoutOtherFolders.remove(j);
                                    n2 = j - 1;
                                }
                            }
                        }
                    }
                }
            }
        }
        this.mAppInfos.clear();
        this.mAppInfos = this.mAppInfosWithOtherFolders;
    }
    
    Drawable generateSelectedDrawable(final Drawable drawable) {
        drawable.setTint(12303292);
        drawable.setTintMode(PorterDuff.Mode.MULTIPLY);
        final Drawable drawable2 = this.mContext.getDrawable(R.drawable.ic_okay);
        drawable2.setTint(11751600);
        drawable2.setBounds(0, 0, LauncherUtils.dpToPx(24.0f, this.mContext), LauncherUtils.dpToPx(24.0f, this.mContext));
        final LayerDrawable layerDrawable = new LayerDrawable(new Drawable[] { drawable, drawable2 });
        final int dpToPx = LauncherUtils.dpToPx(12.0f, this.mContext);
        layerDrawable.setLayerInset(1, dpToPx, dpToPx, dpToPx, dpToPx);
        layerDrawable.setBounds(0, 0, LauncherUtils.dpToPx(56.0f, this.mContext), LauncherUtils.dpToPx(56.0f, this.mContext));
        return (Drawable)layerDrawable;
    }
    
    @Override
    public int getItemCount() {
        return this.mAppInfos.size();
    }
    
    public void onBindViewHolder(final AppViewHolder appViewHolder, final int n) {
        final GridLayoutManager.LayoutParams layoutParams = new GridLayoutManager.LayoutParams(-1, LauncherUtils.dpToPx(96.0f, this.mContext));
        ((AppIconView)appViewHolder.itemView).overrideData(56);
        ((AppIconView)appViewHolder.itemView).setText(this.mAppInfos.get(n).getLabel());
        LauncherActivity.mStaticParallelThreadPool.enqueue(new Runnable() {
            @Override
            public void run() {
                final Drawable icon = FolderAddAppsListAdapter.this.mAppInfos.get(n).getIcon(0);
                icon.setBounds(0, 0, LauncherUtils.dpToPx(64.0f, FolderAddAppsListAdapter.this.mContext), LauncherUtils.dpToPx(64.0f, FolderAddAppsListAdapter.this.mContext));
                ((Activity)FolderAddAppsListAdapter.this.mContext).runOnUiThread((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        if (((RecyclerView.ViewHolder)appViewHolder).getAdapterPosition() == n) {
                            if (!FolderAddAppsListAdapter.this.mSelectedAppsList.contains(FolderAddAppsListAdapter.this.mAppInfos.get(n).getComponentName())) {
                                icon.setTintList((ColorStateList)null);
                                ((AppIconView)appViewHolder.itemView).setIcon(icon);
                                return;
                            }
                            ((AppIconView)appViewHolder.itemView).setIcon(FolderAddAppsListAdapter.this.generateSelectedDrawable(icon));
                        }
                    }
                });
            }
        });
        ((AppIconView)appViewHolder.itemView).setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        appViewHolder.itemView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (FolderAddAppsListAdapter.this.mSelectedAppsList.contains(FolderAddAppsListAdapter.this.mAppInfos.get(((RecyclerView.ViewHolder)appViewHolder).getAdapterPosition()).getComponentName())) {
                    FolderAddAppsListAdapter.this.mSelectedAppsList.remove(FolderAddAppsListAdapter.this.mAppInfos.get(((RecyclerView.ViewHolder)appViewHolder).getAdapterPosition()).getComponentName());
                }
                else {
                    FolderAddAppsListAdapter.this.mSelectedAppsList.add(FolderAddAppsListAdapter.this.mAppInfos.get(((RecyclerView.ViewHolder)appViewHolder).getAdapterPosition()).getComponentName());
                }
                ((RecyclerView.Adapter)FolderAddAppsListAdapter.this).notifyItemChanged(((RecyclerView.ViewHolder)appViewHolder).getAdapterPosition());
            }
        });
    }
    
    public AppViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int n) {
        return new AppViewHolder(this.mContext, viewGroup);
    }
    
    public void onViewRecycled(final AppViewHolder appViewHolder) {
        ((AppIconView)appViewHolder.itemView).setIcon(null);
        super.onViewRecycled(appViewHolder);
    }
    
    public void setShowInOthers(final boolean b) {
        List<LauncherActivityInfo> mAppInfos;
        if (b) {
            mAppInfos = this.mAppInfosWithOtherFolders;
        }
        else {
            mAppInfos = this.mAppInfosWithoutOtherFolders;
        }
        this.mAppInfos = mAppInfos;
        ((RecyclerView.Adapter)this).notifyDataSetChanged();
    }
    
    public static class AppViewHolder extends RecyclerView.ViewHolder
    {
        public AppViewHolder(final Context context, final ViewGroup viewGroup) {
            super(LayoutInflater.from(context).inflate(R.layout.icon, viewGroup, false));
        }
    }
}
