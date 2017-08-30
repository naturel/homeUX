// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.drawerobjects;

import android.view.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.File;
import android.content.Context;
import android.view.LayoutInflater;
import com.vanbo.homeux.dravite.newlayouttest.views.CustomGridLayout;
import android.os.Parcel;
import java.io.Serializable;
import android.os.Parcelable;

public abstract class DrawerObject implements Parcelable, Serializable
{
    public static final int TYPE_APP = 0;
    public static final int TYPE_SHORTCUT = 1;
    public static final int TYPE_WIDGET = 2;
    public GridPositioning mGridPosition;
    
    public DrawerObject() {
    }
    
    public DrawerObject(final Parcel parcel) {
        this.mGridPosition = (GridPositioning)parcel.readSerializable();
    }
    
    public DrawerObject(final GridPositioning mGridPosition) {
        this.mGridPosition = mGridPosition;
    }
    
    public abstract DrawerObject copy();
    
    public abstract void createView(final CustomGridLayout p0, final LayoutInflater p1, final OnViewCreatedListener p2);
    
    public abstract boolean equalType(final DrawerObject p0);
    
    @JsonIgnore
    public File getCacheFile(final Context context, final String s, final String s2) {
        return new File(context.getCacheDir().getPath() + s, s2);
    }
    
    public abstract int getObjectType();
    
    public void writeToParcel(final Parcel parcel, final int n) {
        parcel.writeSerializable((Serializable)this.mGridPosition);
    }
    
    public static class GridPositioning implements Serializable
    {
        public int col;
        public int colSpan;
        public int row;
        public int rowSpan;
        
        public GridPositioning() {
        }
        
        public GridPositioning(final int row, final int col, final int rowSpan, final int colSpan) {
            this.row = row;
            this.col = col;
            this.rowSpan = rowSpan;
            this.colSpan = colSpan;
        }
        
        public GridPositioning copy() {
            return new GridPositioning(this.row, this.col, this.rowSpan, this.colSpan);
        }
        
        @JsonIgnore
        public CustomGridLayout.Cell getCell() {
            return new CustomGridLayout.Cell(this.row, this.col);
        }
        
        @JsonIgnore
        public void set(final CustomGridLayout.Cell cell, final CustomGridLayout.CellSpan cellSpan) {
            this.row = cell.row;
            this.col = cell.column;
            this.rowSpan = cellSpan.rowSpan;
            this.colSpan = cellSpan.columnSpan;
        }
    }
    
    public interface OnViewCreatedListener
    {
        void onViewCreated(final View p0);
    }
}
