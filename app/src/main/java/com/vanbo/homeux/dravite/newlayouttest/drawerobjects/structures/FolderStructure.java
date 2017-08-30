// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures;

import java.io.File;
import android.graphics.Bitmap;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.FileManager;
import com.vanbo.homeux.dravite.newlayouttest.LauncherLog;
import com.vanbo.homeux.dravite.newlayouttest.Const;
import android.preference.PreferenceManager;
import android.content.Context;
import java.util.Iterator;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.Application;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.DrawerObject;
import java.util.ArrayList;
import android.os.Parcel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.LinkedList;
import android.content.ComponentName;
import java.util.TreeMap;
import java.util.List;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import android.os.Parcelable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FolderStructure implements Parcelable, Serializable
{
    public static final Parcelable.Creator<FolderStructure> CREATOR;
    public List<Folder> folders;
    @JsonIgnore
    public TreeMap<ComponentName, LinkedList<String>> mApplicationToFolderMapper;
    
    static {
        CREATOR = (Parcelable.Creator)new Parcelable.Creator<FolderStructure>() {
            public FolderStructure createFromParcel(final Parcel parcel) {
                return new FolderStructure(parcel);
            }
            
            public FolderStructure[] newArray(final int n) {
                return new FolderStructure[n];
            }
        };
    }
    
    public FolderStructure() {
        this.mApplicationToFolderMapper = new TreeMap<ComponentName, LinkedList<String>>();
        this.folders = new ArrayList<Folder>();
    }
    
    public FolderStructure(final Parcel parcel) {
        this.mApplicationToFolderMapper = new TreeMap<ComponentName, LinkedList<String>>();
        this.folders = new ArrayList<Folder>();
        final Parcelable[] parcelableArray = parcel.readParcelableArray(Folder[].class.getClassLoader());
        this.folders = new ArrayList<Folder>();
        for (int length = parcelableArray.length, i = 0; i < length; ++i) {
            this.add((Folder)parcelableArray[i]);
        }
    }
    
    public void add(final Folder folder) {
        this.folders.add(folder);
        if (!folder.folderName.equals("All")) {
            this.addFolderAssignments(folder);
        }
    }
    
    public void addFolderAssignment(final ComponentName componentName, final String s) {
        LinkedList<String> list;
        if ((list = this.mApplicationToFolderMapper.get(componentName)) == null) {
            list = new LinkedList<String>();
        }
        if (!list.contains(s)) {
            list.add(s);
            this.mApplicationToFolderMapper.put(componentName, list);
        }
    }
    
    public void addFolderAssignments(final Folder folder) {
        final Iterator<Page> iterator = folder.pages.iterator();
        while (iterator.hasNext()) {
            for (DrawerObject drawerObject : (iterator.next()).items) {
                if (drawerObject instanceof Application) {
                    ComponentName componentName = new ComponentName(((Application)drawerObject).packageName, ((Application)drawerObject).className);
                    LinkedList<String> list = mApplicationToFolderMapper.get(componentName);
                    if (list == null) {
                        LinkedList<String> list2 = new LinkedList<String>();
                        list2.add(folder.folderName);
                        mApplicationToFolderMapper.put(componentName, list2);
                    }
                    else {
                        if (list.contains(folder.folderName)) {
                            continue;
                        }
                        list.add(folder.folderName);
                        mApplicationToFolderMapper.put(componentName, list);
                    }
                }
            }
        }
    }
    
    public int describeContents() {
        return 0;
    }
    
    public int getDefaultFolderIndex(final Context context) {
        return this.getFolderIndexOfName(this.getDefaultFolderName(context));
    }
    
    public String getDefaultFolderName(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("defaultFolder", Const.Defaults.getString("defaultFolder"));
    }
    
    public int getFolderIndexOfName(final String s) {
        for (int i = 0; i < this.folders.size(); ++i) {
            if (this.folders.get(i).folderName.equals(s)) {
                return i;
            }
        }
        return 0;
    }
    
    public Folder getFolderWithName(final String s) {
        for (int i = 0; i < this.folders.size(); ++i) {
            if (this.folders.get(i).folderName.equals(s)) {
                return this.folders.get(i);
            }
        }
        return null;
    }
    
    @JsonIgnore
    public int getItemCount() {
        int n = 0;
        for (int i = 0; i < this.folders.size(); ++i) {
            for (int j = 0; j < this.folders.get(i).pages.size(); ++j) {
                n += ((Page)this.folders.get(i).pages.get(j)).items.size();
            }
        }
        return n;
    }
    
    public void iterateThrough(final boolean b, final IteratorHelper iteratorHelper) {
        for (int i = 0; i < this.folders.size(); ++i) {
            if (b || !this.folders.get(i).folderName.equals("All")) {
                for (int j = 0; j < this.folders.get(i).pages.size(); ++j) {
                    for (int k = 0; k < ((Page)this.folders.get(i).pages.get(j)).items.size(); ++k) {
                        iteratorHelper.getObject(i, (Folder)this.folders.get(i), j, (Page)this.folders.get(i).pages.get(j), k, ((Page)this.folders.get(i).pages.get(j)).items.get(k));
                    }
                }
            }
        }
    }
    
    public void remove(final int n) {
        this.remove(this.folders.get(n));
    }
    
    public void remove(final Folder folder) {
        if (folder.folderName.equals("All")) {
            return;
        }
        this.removeFolderAssignments(folder);
        this.folders.remove(folder);
    }
    
    public void removeAppPackage(final ComponentName componentName) {
        this.mApplicationToFolderMapper.remove(componentName);
    }
    
    public void removeFolderAssignment(final ComponentName componentName, final String s) {
        final LinkedList<String> list = this.mApplicationToFolderMapper.get(componentName);
        LinkedList<String> list2;
        if (list == null) {
            list2 = new LinkedList<String>();
        }
        else {
            list.remove(s);
            list2 = list;
        }
        this.mApplicationToFolderMapper.put(componentName, list2);
    }
    
    public void removeFolderAssignments(final Folder folder) {
        final Iterator<Page> iterator = folder.pages.iterator();
        while (iterator.hasNext()) {
            this.removePageAssignments(iterator.next(), folder.folderName);
        }
    }
    
    public void removePageAssignments(final Page page, final String s) {
        for (final DrawerObject drawerObject : page.items) {
            if (drawerObject instanceof Application) {
                final ComponentName componentName = new ComponentName(((Application)drawerObject).packageName, ((Application)drawerObject).className);
                final LinkedList<String> list = this.mApplicationToFolderMapper.get(componentName);
                if (list == null) {
                    this.mApplicationToFolderMapper.put(componentName, new LinkedList<String>());
                }
                else {
                    if (!list.contains(s)) {
                        continue;
                    }
                    list.remove(s);
                    this.mApplicationToFolderMapper.put(componentName, list);
                }
            }
        }
    }
    
    public void setFolderName(final Context context, final int n, final String folderName) {
        final String folderName2 = this.folders.get(n).folderName;
        LauncherLog.d("FolderStructure", "Changed a relation Folder: " + folderName2 + " -> " + folderName);
        for (final ComponentName componentName : this.mApplicationToFolderMapper.keySet()) {
            final LinkedList<String> list = this.mApplicationToFolderMapper.get(componentName);
            if (list != null && list.contains(folderName2)) {
                list.remove(folderName2);
                list.add(folderName);
                this.mApplicationToFolderMapper.put(componentName, list);
            }
        }
        FileManager.renameBitmapFromData(context, folderName2, folderName);
        this.folders.get(n).folderName = folderName;
    }
    
    public void writeToParcel(final Parcel parcel, int i) {
        Folder[] array;
        for (array = new Folder[this.folders.size()], i = 0; i < array.length; ++i) {
            array[i] = this.folders.get(i);
        }
        parcel.writeParcelableArray((Parcelable[])array, 0);
    }
    
    public static class Folder implements Parcelable, Serializable
    {
        public static final Parcelable.Creator<Folder> CREATOR;
        public int accentColor;
        public String folderIconRes;
        public String folderName;
        //@JsonIgnore
        public transient Bitmap headerImage;
        public boolean isAllFolder;
        public int mFolderType;
        public List<Page> pages;
        
        static {
            CREATOR = (Parcelable.Creator)new Parcelable.Creator<Folder>() {
                public Folder createFromParcel(final Parcel parcel) {
                    return new Folder(parcel);
                }
                
                public Folder[] newArray(final int n) {
                    return new Folder[n];
                }
            };
        }
        
        public Folder() {
            this.isAllFolder = false;
            this.accentColor = -1;
            this.mFolderType = 0;
            this.pages = new ArrayList<Page>();
        }
        
        public Folder(final Parcel parcel) {
            this.isAllFolder = false;
            this.accentColor = -1;
            this.mFolderType = 0;
            this.pages = new ArrayList<Page>();
            this.folderName = parcel.readString();
            this.isAllFolder = (Boolean)parcel.readValue(Boolean.class.getClassLoader());
            this.accentColor = parcel.readInt();
            this.mFolderType = parcel.readInt();
            final Parcelable[] parcelableArray = parcel.readParcelableArray(Page[].class.getClassLoader());
            this.pages = new ArrayList<Page>();
            for (int i = 0; i < parcelableArray.length; ++i) {
                this.pages.add((Page)parcelableArray[i]);
            }
        }
        
        public void add(final Page page) {
            pages.add(page);
        }
        
        public void deleteImage(final Context context) {
            FileManager.deleteRecursive(new File(new File(context.getApplicationInfo().dataDir + "/folderImg/").getPath(), this.folderName));
        }
        
        public int describeContents() {
            return 0;
        }
        
        public void loadImage(final Context context) {
            this.headerImage = FileManager.loadBitmapFromData(context, this.folderName);
        }
        
        public void saveImage(final Context context, final boolean b) {
            FileManager.saveBitmapToData(context, this.headerImage, this.folderName, b);
        }
        
        public void writeToParcel(final Parcel parcel, int i) {
            parcel.writeString(this.folderName);
            parcel.writeValue((Object)this.isAllFolder);
            parcel.writeInt(this.accentColor);
            parcel.writeInt(this.mFolderType);
            Page[] array;
            for (array = new Page[this.pages.size()], i = 0; i < array.length; ++i) {
                array[i] = this.pages.get(i);
            }
            parcel.writeParcelableArray((Parcelable[])array, 0);
        }
        
        public static class FolderType
        {
            public static final int TYPE_APPS_ONLY = 1;
            public static final int TYPE_WIDGETS = 0;
        }
    }
    
    public interface IteratorHelper
    {
        void getObject(final int p0, final Folder p1, final int p2, final Page p3, final int p4, final DrawerObject p5);
    }
    
    public static class Page implements Parcelable, Serializable
    {
        public static final Parcelable.Creator<Page> CREATOR;
        public ViewDataArrayList items;
        
        static {
            CREATOR = (Parcelable.Creator)new Parcelable.Creator<Page>() {
                public Page createFromParcel(final Parcel parcel) {
                    return new Page(parcel);
                }
                
                public Page[] newArray(final int n) {
                    return new Page[n];
                }
            };
        }
        
        public Page() {
            this.items = new ViewDataArrayList();
        }
        
        public Page(final Parcel parcel) {
            this.items = new ViewDataArrayList();
        }
        
        public void add(final DrawerObject drawerObject) {
            this.items.add(drawerObject);
        }
        
        public int describeContents() {
            return 0;
        }
        
        public void writeToParcel(final Parcel parcel, final int n) {
        }
    }
    
    public static class ViewDataArrayList extends ArrayList<DrawerObject> implements Serializable
    {
        public boolean containsNotPosition(final DrawerObject drawerObject) {
            final Object[] array = this.toArray();
            final int size = this.size();
            if (drawerObject != null) {
                for (int i = 0; i < size; ++i) {
                    if (drawerObject.equalType((DrawerObject)array[i])) {
                        return true;
                    }
                }
            }
            else {
                for (int j = 0; j < size; ++j) {
                    if (array[j] == null) {
                        return true;
                    }
                }
            }
            return false;
        }
        
        @Override
        public DrawerObject get(final int n) {
            return super.get(n);
        }
        
        @Override
        public int indexOf(final Object o) {
            final DrawerObject drawerObject = (DrawerObject)o;
            if (drawerObject != null) {
                for (int i = 0; i < this.size(); ++i) {
                    if (drawerObject.equals(this.get(i))) {
                        return i;
                    }
                }
            }
            else {
                for (int j = 0; j < this.size(); ++j) {
                    if (this.get(j) == null) {
                        return j;
                    }
                }
            }
            return -1;
        }
        
        public int indexOfNotPosition(final DrawerObject drawerObject) {
            if (drawerObject != null) {
                for (int i = 0; i < this.size(); ++i) {
                    if (drawerObject.equalType(this.get(i))) {
                        return i;
                    }
                }
            }
            else {
                for (int j = 0; j < this.size(); ++j) {
                    if (this.get(j) == null) {
                        return j;
                    }
                }
            }
            return -1;
        }
        
        public boolean isFull(final int n) {
            int n2 = 0;
            for (int i = 0; i < this.size(); ++i) {
                n2 += this.get(i).mGridPosition.colSpan * this.get(i).mGridPosition.rowSpan;
            }
            return n2 >= n;
        }
        
        @Override
        public boolean remove(final Object o) {
            final int size = this.size();
            if (o != null) {
                for (int i = 0; i < size; ++i) {
                    if (((DrawerObject)o).equalType(this.get(i))) {
                        this.remove(i);
                        return true;
                    }
                }
            }
            else {
                for (int j = 0; j < size; ++j) {
                    if (this.get(j) == null) {
                        this.remove(j);
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
