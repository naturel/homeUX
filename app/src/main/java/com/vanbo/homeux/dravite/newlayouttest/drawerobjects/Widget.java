// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.drawerobjects;

import android.view.View.OnLongClickListener;
import android.view.View;
import com.vanbo.homeux.dravite.newlayouttest.views.helpers.AppWidgetContainer;
import android.view.LayoutInflater;
import com.vanbo.homeux.dravite.newlayouttest.views.CustomGridLayout;
import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.Parcelable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Widget extends DrawerObject implements Serializable
{
    public static final Parcelable.Creator<Widget> CREATOR;
    public String className;
    public String packageName;
    public int widgetId;
    
    static {
        CREATOR = new Parcelable.Creator<Widget>() {
            public Widget createFromParcel(final Parcel parcel) {
                return new Widget(parcel);
            }
            
            public Widget[] newArray(final int n) {
                return new Widget[n];
            }
        };
    }
    
    public Widget() {
    }
    
    public Widget(final Parcel parcel) {
        super(parcel);
        this.widgetId = parcel.readInt();
        this.packageName = parcel.readString();
        this.className = parcel.readString();
    }
    
    public Widget(final GridPositioning gridPositioning, final int widgetId, final ComponentName componentName) {
        super(gridPositioning);
        this.widgetId = widgetId;
        this.packageName = componentName.getPackageName();
        this.className = componentName.getClassName();
    }
    
    @Override
    public DrawerObject copy() {
        return new Widget(this.mGridPosition.copy(), this.widgetId, new ComponentName(this.packageName, this.className));
    }
    
    @Override
    public void createView(final CustomGridLayout customGridLayout, final LayoutInflater layoutInflater, final OnViewCreatedListener onViewCreatedListener) {
        customGridLayout.mAppWidgetContainer.restoreWidget(this, (AppWidgetContainer.OnWidgetCreatedListener)new AppWidgetContainer.OnWidgetCreatedListener() {
            @Override
            public void onShortcutCreated(final Shortcut shortcut) {
            }
            
            @Override
            public void onWidgetCreated(final View view, final int n) {
                view.setOnLongClickListener((View.OnLongClickListener)customGridLayout);
                onViewCreatedListener.onViewCreated(view);
            }
        });
    }
    
    public int describeContents() {
        return 0;
    }
    
    @Override
    public boolean equalType(final DrawerObject drawerObject) {
        return drawerObject instanceof Widget && this.widgetId == ((Widget)drawerObject).widgetId && this.packageName.equals(((Widget)drawerObject).packageName) && this.className.equals(((Widget)drawerObject).className);
    }
    
    @Override
    public int getObjectType() {
        return 2;
    }
    
    public ComponentName provider() {
        if (this.packageName == null || this.className == null) {
            return null;
        }
        return new ComponentName(this.packageName, this.className);
    }
    
    @Override
    public void writeToParcel(final Parcel parcel, final int n) {
        super.writeToParcel(parcel, n);
        parcel.writeInt(this.widgetId);
        parcel.writeString(this.packageName);
        parcel.writeString(this.className);
    }
}
