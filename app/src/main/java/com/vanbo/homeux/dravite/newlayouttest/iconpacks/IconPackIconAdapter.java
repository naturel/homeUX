// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.iconpacks;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.net.Uri;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.res.ColorStateList;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import com.squareup.picasso.Cache;
import java.util.Collection;
import java.util.TreeSet;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.IconPackManager;
import android.content.Context;
import android.content.pm.PackageManager;
import com.squareup.picasso.LruCache;
import android.support.v7.widget.RecyclerView;
import com.dravite.homeux.R;

public class IconPackIconAdapter extends RecyclerView.Adapter<IconPackIconAdapter.AppViewHolder>
{
    LruCache mCache;
    Context mContext;
    IconPackManager.IconPack mIconPack;
    ArrayList<Integer> mIconsInt;
    ArrayList<String> mIconsString;
    Picasso mPicasso;
    OnAppSelectedInterface onAppSelectedInterface;
    
    public IconPackIconAdapter(final Context mContext, final IconPackManager.IconPack mIconPack, final TreeSet<Integer> set, final TreeSet<String> set2, final OnAppSelectedInterface onAppSelectedInterface) {
        this.onAppSelectedInterface = onAppSelectedInterface;
        this.mContext = mContext;
        this.mIconPack = mIconPack;
        this.mIconsInt = new ArrayList<Integer>(set);
        if (this.mIconsInt.size() != 0) {
            this.mIconsInt.remove(0);
        }
        this.mIconsString = new ArrayList<String>(set2);
        if (this.mIconsString.size() != 0) {
            this.mIconsString.remove(0);
        }
        this.mCache = new LruCache(this.mContext);
        this.mPicasso = new Picasso.Builder(this.mContext).memoryCache(this.mCache).build();
    }
    
    @Override
    public int getItemCount() {
        return Math.max(this.mIconsInt.size(), this.mIconsString.size());
    }
    
    public void onBindViewHolder(final AppViewHolder appViewHolder, int dpToPx) {
        while (true) {
            try {
                appViewHolder.icon.setImageDrawable(this.mContext.getPackageManager().getApplicationIcon(this.mIconPack.mPackageName));
                final int dpToPx2 = LauncherUtils.dpToPx(8.0f, this.mContext);
                appViewHolder.icon.setPadding(dpToPx2, dpToPx2, dpToPx2, dpToPx2);
                appViewHolder.icon.setImageTintList((ColorStateList)null);
                appViewHolder.icon.setAlpha(1.0f);
                appViewHolder.itemView.setAlpha(1.0f);
                Uri uri;
                if (this.mIconsInt.size() == 0) {
                    uri = this.resourceToUri(this.mIconsString.get(dpToPx));
                }
                else {
                    uri = this.resourceToUri(this.mIconsInt.get(dpToPx));
                }
                this.mPicasso.load(uri).into(appViewHolder.icon);
                dpToPx = LauncherUtils.dpToPx(8.0f, this.mContext);
                appViewHolder.icon.setPadding(dpToPx, dpToPx, dpToPx, dpToPx);
                appViewHolder.setClickListener((View.OnClickListener)new View.OnClickListener() {
                    public void onClick(final View view) {
                        IconPackIconAdapter.this.mCache.clear();
                        IconPackIconAdapter.this.mCache = null;
                        IconPackIconAdapter.this.mPicasso.shutdown();
                        IconPackIconAdapter.this.onAppSelectedInterface.onAppSelected(LauncherUtils.drawableToBitmap(appViewHolder.icon.getDrawable()));
                    }
                });
                appViewHolder.itemView.setElevation(0.0f);
                appViewHolder.icon.setImageTintList((ColorStateList)null);
            }
            catch (PackageManager.NameNotFoundException ex) {
                continue;
            }
            break;
        }
    }
    
    public AppViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int n) {
        return new AppViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.simple_icon, (ViewGroup)null));
    }
    
    public Uri resourceToUri(final int n) {
        return this.resourceToUri(this.mIconPack.mPackRes.getResourceEntryName(n));
    }
    
    public Uri resourceToUri(final String s) {
        return Uri.parse("android.resource://" + this.mIconPack.mPackageName + '/' + "drawable" + "/" + s);
    }
    
    public static class AppViewHolder extends RecyclerView.ViewHolder
    {
        ImageView icon;
        
        public AppViewHolder(final View view) {
            super(view);
            this.icon = (ImageView)view.findViewById(R.id.icon);
        }
        
        public void setClickListener(final View.OnClickListener onClickListener) {
            this.icon.setOnClickListener(onClickListener);
        }
    }
    
    public interface OnAppSelectedInterface
    {
        void onAppSelected(final Bitmap p0);
    }
}
