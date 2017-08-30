// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.drawerobjects;

import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.IconPackManager;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import android.view.View.OnLongClickListener;
import com.vanbo.homeux.dravite.newlayouttest.Const;
import com.vanbo.homeux.dravite.newlayouttest.LauncherLog;
import com.vanbo.homeux.dravite.newlayouttest.views.CustomGridLayout;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.DrawerTree;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import com.vanbo.homeux.dravite.newlayouttest.views.AppIconView;
import android.os.Process;
import android.content.ComponentName;
import android.content.pm.LauncherApps;
import android.view.View;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.FileManager;
import android.content.Context;
import com.fasterxml.jackson.annotation.JsonIgnore;
import android.os.Parcel;
import android.content.pm.LauncherActivityInfo;
import android.graphics.Bitmap;
import android.content.Intent;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import com.dravite.homeux.R;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Application extends DrawerObject implements Serializable
{
    public static final Parcelable.Creator<Application> CREATOR;
    private static final String TAG = "Application";
    public transient Intent appIntent;
    public String className;
    public transient Bitmap icon;
    public transient LauncherActivityInfo info;
    public String label;
    public String packageName;
    
    static {
        CREATOR = new Parcelable.Creator<Application>() {
            public Application createFromParcel(final Parcel parcel) {
                return new Application(parcel);
            }
            
            public Application[] newArray(final int n) {
                return new Application[n];
            }
        };
    }
    
    private Application() {
    }
    
    public Application(final LauncherActivityInfo info) {
        super(new GridPositioning(Integer.MIN_VALUE, Integer.MIN_VALUE, 1, 1));
        this.info = info;
        this.packageName = info.getComponentName().getPackageName();
        this.className = info.getComponentName().getClassName();
        this.label = info.getLabel().toString();
        (this.appIntent = new Intent()).setComponent(info.getComponentName());
    }
    
    private Application(final Parcel parcel) {
        super(parcel);
        this.icon = (Bitmap)parcel.readParcelable(Bitmap.class.getClassLoader());
        this.appIntent = (Intent)parcel.readParcelable(Intent.class.getClassLoader());
        this.label = parcel.readString();
        this.packageName = parcel.readString();
        this.className = parcel.readString();
    }
    
    @JsonIgnore
    private String getAppPathExt() {
        return "/apps/" + this.info.getComponentName().getPackageName();
    }
    
    @JsonIgnore
    private String getCustomIconFileName() {
        return this.info.getComponentName().getClassName() + "_t";
    }
    
    @JsonIgnore
    private String getLabelFileName() {
        return this.info.getComponentName().getClassName() + "_l";
    }
    
    private boolean hasCustomIcon(final Context context) {
        return FileManager.fileExists(context, this.getAppPathExt(), this.getCustomIconFileName());
    }
    
    @Override
    public DrawerObject copy() {
        final Application application = new Application();
        application.mGridPosition = this.mGridPosition.copy();
        application.icon = this.icon;
        application.info = this.info;
        application.packageName = this.packageName;
        application.className = this.className;
        application.label = this.label;
        application.appIntent = this.appIntent;
        return application;
    }
    
    public View createDefaultView(final Context context) {
        if (this.info == null) {
            final LauncherApps launcherApps = (LauncherApps)context.getSystemService(Context.LAUNCHER_APPS_SERVICE);
            final Intent intent = new Intent();
            intent.setComponent(new ComponentName(this.packageName, this.className));
            this.info = launcherApps.resolveActivity(intent, Process.myUserHandle());
        }
        final AppIconView appIconView = (AppIconView)LayoutInflater.from(context).inflate(R.layout.icon, (ViewGroup)null);
        appIconView.setText((CharSequence)this.loadLabel(context));
        if (this.appIntent == null) {
            (this.appIntent = new Intent()).setComponent(new ComponentName(this.packageName, this.className));
        }
        appIconView.setTag((Object)this.appIntent);
        appIconView.setTag(2131558401, (Object)true);
        appIconView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ((LauncherActivity)context).mAppClickListener.onClick(view);
                ((LauncherActivity)context).hideSearchMode();
            }
        });
        LauncherActivity.mDrawerTree.doWithApplication(this.info, (DrawerTree.LoadedListener)new DrawerTree.LoadedListener() {
            @Override
            public void onApplicationLoadingFinished(final Application application) {
                if (application == null) {
                    appIconView.setIcon(null);
                    return;
                }
                appIconView.setIcon((Drawable)new BitmapDrawable(context.getResources(), application.icon));
            }
        });
        return (View)appIconView;
    }
    
    @Override
    public void createView(final CustomGridLayout onLongClickListener, final LayoutInflater layoutInflater, final OnViewCreatedListener onViewCreatedListener) {
        if (this.info == null || this.appIntent == null) {
            final LauncherApps launcherApps = (LauncherApps)onLongClickListener.getContext().getSystemService(Context.LAUNCHER_APPS_SERVICE);
            (this.appIntent = new Intent()).setComponent(new ComponentName(this.packageName, this.className));
            this.info = launcherApps.resolveActivity(this.appIntent, Process.myUserHandle());
        }
        if (this.info == null) {
            final StringBuilder append = new StringBuilder().append("Application's info is null: ");
            String label;
            if (this.label == null) {
                label = "n/a";
            }
            else {
                label = this.label;
            }
            LauncherLog.w("Application", append.append(label).toString());
            return;
        }
        final AppIconView inflateIcon = onLongClickListener.inflateIcon();
        String loadLabel;
        if (onLongClickListener.mPreferences.getBoolean("showLabels", Const.Defaults.getBoolean("showLabels"))) {
            loadLabel = this.loadLabel(onLongClickListener.getContext());
        }
        else {
            loadLabel = "";
        }
        inflateIcon.setText((CharSequence)loadLabel);
        inflateIcon.setTag((Object)this.appIntent);
        inflateIcon.setTag(2131558401, (Object)true);
        inflateIcon.setOnLongClickListener((View.OnLongClickListener)onLongClickListener);
        inflateIcon.setOnClickListener(onLongClickListener.getMainActivity().mAppClickListener);
        LauncherActivity.mDrawerTree.doWithApplicatioByLabel(this.loadLabel((Context)onLongClickListener.getMainActivity()), this.info, (DrawerTree.LoadedListener)new DrawerTree.LoadedListener() {
            @Override
            public void onApplicationLoadingFinished(final Application application) {
                if (application != null) {
                    inflateIcon.setIcon((Drawable)new BitmapDrawable(onLongClickListener.getResources(), application.icon));
                }
            }
        });
        onViewCreatedListener.onViewCreated((View)inflateIcon);
    }
    
    public int describeContents() {
        return 0;
    }
    
    @Override
    public boolean equalType(final DrawerObject drawerObject) {
        if (drawerObject instanceof Application && this.label.equals(((Application)drawerObject).label)) {
            if (this.appIntent == null) {
                if (((Application)drawerObject).appIntent != null) {
                    return false;
                }
            }
            else if (!this.appIntent.equals(((Application)drawerObject).appIntent)) {
                return false;
            }
            if (this.icon == null) {
                if (((Application)drawerObject).icon != null) {
                    return false;
                }
            }
            else if (!this.icon.equals(((Application)drawerObject).icon)) {
                return false;
            }
            if (this.info == null) {
                if (((Application)drawerObject).info != null) {
                    return false;
                }
            }
            else if (!this.info.equals(((Application)drawerObject).info)) {
                return false;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public int getObjectType() {
        return 0;
    }
    
    public Bitmap loadCustomIcon(final Context context) {
        if (this.hasCustomIcon(context)) {
            return FileManager.loadBitmap(context, this.getAppPathExt(), this.getCustomIconFileName());
        }
        return null;
    }
    
    public Bitmap loadIcon(final Context context, final LauncherActivityInfo launcherActivityInfo) {
        if (this.hasCustomIcon(context)) {
            return this.loadCustomIcon(context);
        }
        if (((LauncherActivity)context).mCurrentIconPack != null && ((LauncherActivity)context).mCurrentIconPack.isLoaded) {
            return ((LauncherActivity)context).mCurrentIconPack.getIconBitmap(launcherActivityInfo.getComponentName().toString(), LauncherUtils.drawableToBitmap(launcherActivityInfo.getIcon(0)));
        }
        return LauncherUtils.drawableToBitmap(launcherActivityInfo.getIcon(0));
    }
    
    @JsonIgnore
    public String loadLabel(final Context context) {
        if (FileManager.fileExists(context, this.getAppPathExt(), this.getLabelFileName())) {
            return FileManager.readTextFile(context, this.getAppPathExt(), this.getLabelFileName());
        }
        return this.label;
    }
    
    public Bitmap loadThemedIcon(@Nullable final IconPackManager.IconPack iconPack, final LauncherActivityInfo launcherActivityInfo) {
        if (iconPack != null && iconPack.isLoaded) {
            return iconPack.getIconBitmap(launcherActivityInfo.getComponentName().toString(), LauncherUtils.drawableToBitmap(launcherActivityInfo.getIcon(0)));
        }
        return LauncherUtils.drawableToBitmap(launcherActivityInfo.getIcon(0));
    }
    
    public void saveCustomIcon(final Context context, @Nullable final Bitmap bitmap) {
        if (bitmap != null) {
            FileManager.saveBitmap(context, bitmap, this.getAppPathExt(), this.getCustomIconFileName());
            return;
        }
        FileManager.deleteRecursive(this.getCacheFile(context, this.getAppPathExt(), this.getCustomIconFileName()));
    }
    
    public void saveLabel(final Context context, @Nullable final String s) {
        if (s == null) {
            FileManager.deleteRecursive(this.getCacheFile(context, this.getAppPathExt(), this.getLabelFileName()));
            return;
        }
        FileManager.saveTextFile(context, this.getAppPathExt(), this.getLabelFileName(), s);
    }
    
    @Override
    public void writeToParcel(final Parcel parcel, final int n) {
        super.writeToParcel(parcel, n);
        parcel.writeParcelable((Parcelable)this.icon, 0);
        parcel.writeParcelable((Parcelable)this.appIntent, 0);
        parcel.writeString(this.label);
        parcel.writeString(this.packageName);
        parcel.writeString(this.className);
    }
}
