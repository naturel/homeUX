// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.drawerobjects;

import android.os.Parcelable;
import android.view.LayoutInflater;
import com.vanbo.homeux.dravite.newlayouttest.views.CustomGridLayout;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.FileManager;
import android.content.Intent.ShortcutIconResource;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import android.graphics.Bitmap;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Shortcut extends DrawerObject implements Serializable
{
    public static final Parcelable.Creator<Shortcut> CREATOR;
    @JsonIgnore
    public transient Bitmap icon;
    public SIconResource iconResource;
    public String shortcutIntentUri;
    public String shortcutLabel;
    
    static {
        CREATOR = (Parcelable.Creator)new Parcelable.Creator<Shortcut>() {
            public Shortcut createFromParcel(final Parcel parcel) {
                return new Shortcut(parcel);
            }
            
            public Shortcut[] newArray(final int n) {
                return new Shortcut[n];
            }
        };
    }
    
    public Shortcut() {
    }
    
    public Shortcut(final Intent intent, final Context context) {
        this.iconResource = new SIconResource((Intent.ShortcutIconResource)intent.getParcelableExtra("android.intent.extra.shortcut.ICON_RESOURCE"));
        this.icon = (Bitmap)intent.getParcelableExtra("android.intent.extra.shortcut.ICON");
        this.shortcutLabel = intent.getStringExtra("android.intent.extra.shortcut.NAME");
        this.shortcutIntentUri = ((Intent)intent.getParcelableExtra("android.intent.extra.shortcut.INTENT")).toUri(0);
        FileManager.saveBitmap(context, this.icon, "/Shortcuts", this.shortcutIntentUri.replaceAll("/", ""));
    }
    
    public Shortcut(final Parcel parcel) {
        super(parcel);
        this.shortcutIntentUri = parcel.readString();
        this.iconResource = (SIconResource)parcel.readSerializable();
        this.shortcutLabel = parcel.readString();
        this.icon = (Bitmap)parcel.readParcelable(Bitmap.class.getClassLoader());
    }
    
    @Override
    public DrawerObject copy() {
        final Shortcut shortcut = new Shortcut();
        shortcut.mGridPosition = this.mGridPosition;
        shortcut.icon = this.icon;
        shortcut.shortcutIntentUri = this.shortcutIntentUri;
        shortcut.iconResource = this.iconResource;
        shortcut.shortcutLabel = this.shortcutLabel;
        return shortcut;
    }
    
    @Override
    public void createView(final CustomGridLayout p0, final LayoutInflater p1, final OnViewCreatedListener p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: aconst_null    
        //     1: astore_2       
        //     2: aload_0        
        //     3: getfield        com/dravite/newlayouttest/drawerobjects/Shortcut.shortcutIntentUri:Ljava/lang/String;
        //     6: iconst_0       
        //     7: invokestatic    android/content/Intent.parseUri:(Ljava/lang/String;I)Landroid/content/Intent;
        //    10: astore          4
        //    12: aload           4
        //    14: astore_2       
        //    15: aload_0        
        //    16: getfield        com/dravite/newlayouttest/drawerobjects/Shortcut.icon:Landroid/graphics/Bitmap;
        //    19: ifnonnull       124
        //    22: aload_0        
        //    23: getfield        com/dravite/newlayouttest/drawerobjects/Shortcut.iconResource:Lcom/dravite/newlayouttest/drawerobjects/Shortcut.SIconResource;
        //    26: invokevirtual   com/dravite/newlayouttest/drawerobjects/Shortcut.SIconResource.createRes:()Landroid/content/Intent.ShortcutIconResource;
        //    29: astore          6
        //    31: aload           6
        //    33: ifnull          252
        //    36: aconst_null    
        //    37: astore          4
        //    39: aload_1        
        //    40: invokevirtual   com/dravite/newlayouttest/views/CustomGridLayout.getContext:()Landroid/content/Context;
        //    43: invokevirtual   android/content/Context.getPackageManager:()Landroid/content/pm/PackageManager;
        //    46: aload           6
        //    48: getfield        android/content/Intent.ShortcutIconResource.packageName:Ljava/lang/String;
        //    51: invokevirtual   android/content/pm/PackageManager.getResourcesForApplication:(Ljava/lang/String;)Landroid/content/res/Resources;
        //    54: astore          5
        //    56: aload           5
        //    58: astore          4
        //    60: aload           4
        //    62: ifnull          100
        //    65: aload_0        
        //    66: aload           4
        //    68: aload           4
        //    70: aload           6
        //    72: getfield        android/content/Intent.ShortcutIconResource.resourceName:Ljava/lang/String;
        //    75: aconst_null    
        //    76: aload           6
        //    78: getfield        android/content/Intent.ShortcutIconResource.packageName:Ljava/lang/String;
        //    81: invokevirtual   android/content/res/Resources.getIdentifier:(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
        //    84: aload_1        
        //    85: invokevirtual   com/dravite/newlayouttest/views/CustomGridLayout.getContext:()Landroid/content/Context;
        //    88: invokevirtual   android/content/Context.getTheme:()Landroid/content/res/Resources.Theme;
        //    91: invokevirtual   android/content/res/Resources.getDrawable:(ILandroid/content/res/Resources.Theme;)Landroid/graphics/drawable/Drawable;
        //    94: invokestatic    com/dravite/newlayouttest/LauncherUtils.drawableToBitmap:(Landroid/graphics/drawable/Drawable;)Landroid/graphics/Bitmap;
        //    97: putfield        com/dravite/newlayouttest/drawerobjects/Shortcut.icon:Landroid/graphics/Bitmap;
        //   100: aload_1        
        //   101: invokevirtual   com/dravite/newlayouttest/views/CustomGridLayout.getContext:()Landroid/content/Context;
        //   104: aload_0        
        //   105: getfield        com/dravite/newlayouttest/drawerobjects/Shortcut.icon:Landroid/graphics/Bitmap;
        //   108: ldc             "/Shortcuts"
        //   110: aload_0        
        //   111: getfield        com/dravite/newlayouttest/drawerobjects/Shortcut.shortcutIntentUri:Ljava/lang/String;
        //   114: ldc             "/"
        //   116: ldc             ""
        //   118: invokevirtual   java/lang/String.replaceAll:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   121: invokestatic    com/dravite/newlayouttest/general_helpers/FileManager.saveBitmap:(Landroid/content/Context;Landroid/graphics/Bitmap;Ljava/lang/String;Ljava/lang/String;)V
        //   124: aload_0        
        //   125: getfield        com/dravite/newlayouttest/drawerobjects/Shortcut.shortcutLabel:Ljava/lang/String;
        //   128: ifnull          279
        //   131: aload_2        
        //   132: ifnull          279
        //   135: aload_0        
        //   136: getfield        com/dravite/newlayouttest/drawerobjects/Shortcut.icon:Landroid/graphics/Bitmap;
        //   139: ifnull          279
        //   142: aload_1        
        //   143: invokevirtual   com/dravite/newlayouttest/views/CustomGridLayout.getContext:()Landroid/content/Context;
        //   146: ldc             R.layout.icon
        //   148: aconst_null    
        //   149: invokestatic    android/view/View.inflate:(Landroid/content/Context;ILandroid/view/ViewGroup;)Landroid/view/View;
        //   152: checkcast       Lcom/dravite/newlayouttest/views/AppIconView;
        //   155: astore          4
        //   157: aload           4
        //   159: new             Landroid/graphics/drawable/BitmapDrawable;
        //   162: dup            
        //   163: aload_1        
        //   164: invokevirtual   com/dravite/newlayouttest/views/CustomGridLayout.getContext:()Landroid/content/Context;
        //   167: invokevirtual   android/content/Context.getResources:()Landroid/content/res/Resources;
        //   170: aload_0        
        //   171: getfield        com/dravite/newlayouttest/drawerobjects/Shortcut.icon:Landroid/graphics/Bitmap;
        //   174: invokespecial   android/graphics/drawable/BitmapDrawable.<init>:(Landroid/content/res/Resources;Landroid/graphics/Bitmap;)V
        //   177: invokevirtual   com/dravite/newlayouttest/views/AppIconView.setIcon:(Landroid/graphics/drawable/Drawable;)V
        //   180: aload           4
        //   182: aload_0        
        //   183: getfield        com/dravite/newlayouttest/drawerobjects/Shortcut.shortcutLabel:Ljava/lang/String;
        //   186: invokevirtual   com/dravite/newlayouttest/views/AppIconView.setText:(Ljava/lang/CharSequence;)V
        //   189: aload           4
        //   191: aload_2        
        //   192: invokevirtual   com/dravite/newlayouttest/views/AppIconView.setTag:(Ljava/lang/Object;)V
        //   195: aload           4
        //   197: aload_1        
        //   198: invokevirtual   com/dravite/newlayouttest/views/AppIconView.setOnLongClickListener:(Landroid/view/View.OnLongClickListener;)V
        //   201: aload           4
        //   203: aload_1        
        //   204: invokevirtual   com/dravite/newlayouttest/views/CustomGridLayout.getContext:()Landroid/content/Context;
        //   207: checkcast       Lcom/dravite/newlayouttest/LauncherActivity;
        //   210: getfield        com/dravite/newlayouttest/LauncherActivity.mShortcutClickListener:Landroid/view/View.OnClickListener;
        //   213: invokevirtual   com/dravite/newlayouttest/views/AppIconView.setOnClickListener:(Landroid/view/View.OnClickListener;)V
        //   216: aload_3        
        //   217: aload           4
        //   219: invokeinterface com/dravite/newlayouttest/drawerobjects/DrawerObject.OnViewCreatedListener.onViewCreated:(Landroid/view/View;)V
        //   224: return         
        //   225: astore          4
        //   227: aload           4
        //   229: invokevirtual   java/net/URISyntaxException.printStackTrace:()V
        //   232: aload_3        
        //   233: aconst_null    
        //   234: invokeinterface com/dravite/newlayouttest/drawerobjects/DrawerObject.OnViewCreatedListener.onViewCreated:(Landroid/view/View;)V
        //   239: goto            15
        //   242: astore          5
        //   244: aload           5
        //   246: invokevirtual   android/content/pm/PackageManager.NameNotFoundException.printStackTrace:()V
        //   249: goto            60
        //   252: aload_0        
        //   253: aload_1        
        //   254: invokevirtual   com/dravite/newlayouttest/views/CustomGridLayout.getContext:()Landroid/content/Context;
        //   257: ldc             "/Shortcuts"
        //   259: aload_0        
        //   260: getfield        com/dravite/newlayouttest/drawerobjects/Shortcut.shortcutIntentUri:Ljava/lang/String;
        //   263: ldc             "/"
        //   265: ldc             ""
        //   267: invokevirtual   java/lang/String.replaceAll:(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   270: invokestatic    com/dravite/newlayouttest/general_helpers/FileManager.loadBitmap:(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Landroid/graphics/Bitmap;
        //   273: putfield        com/dravite/newlayouttest/drawerobjects/Shortcut.icon:Landroid/graphics/Bitmap;
        //   276: goto            124
        //   279: aload_3        
        //   280: aconst_null    
        //   281: invokeinterface com/dravite/newlayouttest/drawerobjects/DrawerObject.OnViewCreatedListener.onViewCreated:(Landroid/view/View;)V
        //   286: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                                     
        //  -----  -----  -----  -----  ---------------------------------------------------------
        //  2      12     225    242    Ljava/net/URISyntaxException;
        //  39     56     242    252    Landroid/content/pm/PackageManager.NameNotFoundException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0060:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2592)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:138)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public int describeContents() {
        return 0;
    }
    
    @Override
    public boolean equalType(final DrawerObject drawerObject) {
        if (drawerObject instanceof Shortcut) {
            if (this.shortcutIntentUri == null) {
                if (((Shortcut)drawerObject).shortcutIntentUri != null) {
                    return false;
                }
            }
            else if (!this.shortcutIntentUri.equals(((Shortcut)drawerObject).shortcutIntentUri)) {
                return false;
            }
            if (this.shortcutLabel == null) {
                if (((Shortcut)drawerObject).shortcutLabel != null) {
                    return false;
                }
            }
            else if (!this.shortcutLabel.equals(((Shortcut)drawerObject).shortcutLabel)) {
                return false;
            }
            if (this.icon == null) {
                if (((Shortcut)drawerObject).icon != null) {
                    return false;
                }
            }
            else if (!this.icon.equals(((Shortcut)drawerObject).icon)) {
                return false;
            }
            if (this.iconResource.equals(((Shortcut)drawerObject).iconResource)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int getObjectType() {
        return 1;
    }
    
    @Override
    public void writeToParcel(final Parcel parcel, final int n) {
        super.writeToParcel(parcel, n);
        parcel.writeString(this.shortcutIntentUri);
        parcel.writeSerializable((Serializable)this.iconResource);
        parcel.writeString(this.shortcutLabel);
        parcel.writeParcelable((Parcelable)this.icon, 0);
    }
    
    public static class SIconResource implements Serializable
    {
        public String packageName;
        public String resourceName;
        public boolean valid;
        
        public SIconResource() {
        }
        
        public SIconResource(final Intent.ShortcutIconResource intentShortcutIconResource) {
            if (intentShortcutIconResource == null) {
                this.valid = false;
                this.packageName = "";
                this.resourceName = "";
                return;
            }
            this.valid = true;
            this.packageName = intentShortcutIconResource.packageName;
            this.resourceName = intentShortcutIconResource.resourceName;
        }
        
        public Intent.ShortcutIconResource createRes() {
            if (!this.valid) {
                return null;
            }
            final Intent.ShortcutIconResource intentShortcutIconResource = new Intent.ShortcutIconResource();
            intentShortcutIconResource.packageName = this.packageName;
            intentShortcutIconResource.resourceName = this.resourceName;
            return intentShortcutIconResource;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o instanceof SIconResource && this.packageName == null) {
                return ((SIconResource)o).packageName == null;
            }
            if (this.packageName.equals(((SIconResource)o).packageName) && this.resourceName == null) {
                return ((SIconResource)o).resourceName == null;
            }
            return this.resourceName.equals(((SIconResource)o).resourceName) && this.valid == ((SIconResource)o).valid;
        }
    }
}
