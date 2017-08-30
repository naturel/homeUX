// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.settings.hidden_apps;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.content.res.ColorStateList;
import android.app.Activity;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import com.vanbo.homeux.dravite.newlayouttest.views.AppIconView;
import android.graphics.drawable.LayerDrawable;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import android.graphics.drawable.Drawable;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.JsonHelper;
import java.util.Comparator;
import java.util.Collections;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.helpers.AppLauncherActivityInfoComparator;
import android.os.Process;
import android.content.pm.LauncherApps;
import java.util.ArrayList;
import android.content.ComponentName;
import android.content.pm.LauncherActivityInfo;
import java.util.List;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.dravite.homeux.R;

public class HiddenAppsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.hidden_apps_activity);
        this.setSupportActionBar((Toolbar)this.findViewById(R.id.toolbar));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final RecyclerView recyclerView = (RecyclerView)this.findViewById(R.id.hiddenAppsList);
        recyclerView.setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager((Context)this, 4));
        recyclerView.setAdapter((RecyclerView.Adapter)new HiddenAppsAdapter((Context)this));
    }
    
    public static class HiddenAppsAdapter extends RecyclerView.Adapter<HiddenAppsAdapter.AppViewHolder>
    {
        private List<LauncherActivityInfo> mAppInfos;
        private Context mContext;
        private List<ComponentName> mHiddenComponents;
        
        public HiddenAppsAdapter(final Context mContext) {
            this.mHiddenComponents = new ArrayList<ComponentName>();
            this.mContext = mContext;
            Collections.sort(this.mAppInfos = (List<LauncherActivityInfo>)((LauncherApps)mContext.getSystemService(Context.LAUNCHER_APPS_SERVICE)).getActivityList((String)null, Process.myUserHandle()), new AppLauncherActivityInfoComparator(mContext));
            this.mHiddenComponents = JsonHelper.loadHiddenAppList(this.mContext);
        }
        
        Drawable generateHiddenDrawable(final Drawable drawable) {
            drawable.setTint(1895056182);
            final Drawable drawable2 = this.mContext.getDrawable(R.drawable.ic_hide);
            drawable2.setTint(1973791);
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
            final GridLayoutManager.LayoutParams layoutParams = new GridLayoutManager.LayoutParams(-1, LauncherUtils.dpToPx(80.0f, this.mContext));
            ((AppIconView)appViewHolder.itemView).overrideData(48);
            ((AppIconView)appViewHolder.itemView).setText(this.mAppInfos.get(n).getLabel());
            LauncherActivity.mStaticParallelThreadPool.enqueue(new Runnable() {
                @Override
                public void run() {
                    final Drawable icon = HiddenAppsAdapter.this.mAppInfos.get(n).getIcon(0);
                    icon.setBounds(0, 0, LauncherUtils.dpToPx(56.0f, HiddenAppsAdapter.this.mContext), LauncherUtils.dpToPx(56.0f, HiddenAppsAdapter.this.mContext));
                    ((Activity)HiddenAppsAdapter.this.mContext).runOnUiThread((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            if (((RecyclerView.ViewHolder)appViewHolder).getAdapterPosition() == n) {
                                if (!HiddenAppsAdapter.this.mHiddenComponents.contains(HiddenAppsAdapter.this.mAppInfos.get(n).getComponentName())) {
                                    icon.setTintList((ColorStateList)null);
                                    ((AppIconView)appViewHolder.itemView).setIcon(icon);
                                    ((AppIconView)appViewHolder.itemView).setTextColor(520093696);
                                    return;
                                }
                                ((AppIconView)appViewHolder.itemView).setIcon(HiddenAppsAdapter.this.generateHiddenDrawable(icon));
                                ((AppIconView)appViewHolder.itemView).setTextColor(1140850688);
                            }
                        }
                    });
                }
            });
            ((AppIconView)appViewHolder.itemView).setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            appViewHolder.itemView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    if (HiddenAppsAdapter.this.mHiddenComponents.contains(HiddenAppsAdapter.this.mAppInfos.get(n).getComponentName())) {
                        HiddenAppsAdapter.this.mHiddenComponents.remove(HiddenAppsAdapter.this.mAppInfos.get(n).getComponentName());
                    }
                    else {
                        HiddenAppsAdapter.this.mHiddenComponents.add(HiddenAppsAdapter.this.mAppInfos.get(n).getComponentName());
                    }
                    JsonHelper.saveHiddenAppList(HiddenAppsAdapter.this.mContext, HiddenAppsAdapter.this.mHiddenComponents);
                    HiddenAppsAdapter.this.notifyItemChanged(n);
                }
            });
        }
        
        public AppViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int n) {
            return new AppViewHolder(this.mContext);
        }
        
        public static class AppViewHolder extends RecyclerView.ViewHolder
        {
            public AppViewHolder(final Context context) {
                super(LayoutInflater.from(context).inflate(R.layout.icon, null));
            }
        }
    }
}
