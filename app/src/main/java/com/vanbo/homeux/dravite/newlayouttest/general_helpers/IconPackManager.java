// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_helpers;

import org.xml.sax.Attributes;

import android.content.ComponentName;
import android.content.res.AssetManager;
import android.os.Process;
import android.content.pm.LauncherApps;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import android.content.pm.LauncherActivityInfo;
import android.content.res.Resources.NotFoundException;
import android.graphics.Rect;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Bitmap.Config;
import android.graphics.Xfermode;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff.Mode;
import android.graphics.Bitmap;
import java.io.IOException;
import android.graphics.BitmapFactory.Options;
import android.content.pm.PackageManager.NameNotFoundException;
import java.util.Random;
import android.content.res.Resources;
import java.util.HashMap;
import android.graphics.Paint;
import org.xml.sax.helpers.DefaultHandler;
import android.graphics.drawable.Drawable;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import java.util.ArrayList;
import android.net.Uri;
import android.content.Intent;
import java.util.List;
import android.content.Context;
import android.graphics.PorterDuff;
import android.text.TextUtils;

import com.dravite.homeux.R;
import android.util.Log;

public class IconPackManager
{
    static final String TAG = "IconPackManager";
    public static List<Theme> getAllThemes(final Context context, final boolean b) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent("android.intent.action.MAIN", (Uri)null);
        intent.addCategory("com.anddoes.launcher.THEME");
        final List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 0);
        final ArrayList<Theme> list = new ArrayList<Theme>();
        final int dpToPx = LauncherUtils.dpToPx(40.0f, context);
        if (b) {
            final Drawable drawable = context.getDrawable(R.mipmap.ic_launcher);
            drawable.setBounds(0, 0, dpToPx, dpToPx);
            list.add(new Theme("", "\u9ed8\u8ba4\u56fe\u6807", drawable));
        }
        for (int i = 0; i < queryIntentActivities.size(); ++i) {
            final Drawable loadIcon = queryIntentActivities.get(i).activityInfo.loadIcon(packageManager);
            loadIcon.setBounds(0, 0, dpToPx, dpToPx);
            list.add(new Theme(queryIntentActivities.get(i).activityInfo.packageName, queryIntentActivities.get(i).activityInfo.loadLabel(packageManager).toString(), loadIcon));
        }
        return list;
    }
    
    public static class IconPack extends DefaultHandler
    {
        Paint defaultIconPaint;
        public boolean hasAlternateStructure;
        Paint iconMaskPaint;
        public boolean isLoaded;
        public Context mContext;
        public UpdateListener mCountListener;
        public int mCurrentItem;
        String mExtention;
        public List<Integer> mIconBackInts;
        public List<String> mIconBackNames;
        public HashMap<String, Integer> mIconMap;
        public HashMap<String, String> mIconMapStrings;
        public Integer mIconMaskInt;
        public String mIconMaskName;
        public Integer mIconUponInt;
        public String mIconUponName;
        public int mLineCount;
        public Resources mPackRes;
        public String mPackageName;
        private List<String> mPendingApps;
        private Random mRandom;
        public float mScale;
        
        public IconPack(final Context mContext, final String mPackageName) throws PackageManager.NameNotFoundException {
            this.mIconBackInts = new ArrayList<Integer>();
            this.mIconMaskInt = Integer.MIN_VALUE;
            this.mIconUponInt = Integer.MIN_VALUE;
            this.mIconBackNames = new ArrayList<String>();
            this.mIconMaskName = null;
            this.mIconUponName = null;
            this.mScale = 1.0f;
            this.mExtention = null;
            this.defaultIconPaint = new Paint(2);
            this.iconMaskPaint = new Paint(2);
            this.mPackageName = mPackageName;
            this.mContext = mContext;
            Log.v(TAG, "method:constructor mPackageName=" + mPackageName);
            if (!this.mPackageName.equals("")) {
                this.mPackRes = this.mContext.getPackageManager().getResourcesForApplication(this.mPackageName);
            }
            else{
                Log.v(TAG, "not package name specified, not icon pack selected, return the origin icons for apps.");
            }
            this.mIconMap = new HashMap<String, Integer>();
            this.mIconMapStrings = new HashMap<String, String>();
            this.mRandom = new Random(System.currentTimeMillis());
        }
        
        public static int calculateInSampleSize(final BitmapFactory.Options bitmapFactoryOptions, final int n, final int n2) {
            final int outHeight = bitmapFactoryOptions.outHeight;
            final int outWidth = bitmapFactoryOptions.outWidth;
            int n3 = 1;
            int n4 = 1;
            if (outHeight > n2 || outWidth > n) {
                final int n5 = outHeight / 2;
                final int n6 = outWidth / 2;
                while (true) {
                    n3 = n4;
                    if (n5 / n4 <= n2) {
                        break;
                    }
                    n3 = n4;
                    if (n6 / n4 <= n) {
                        break;
                    }
                    n4 *= 2;
                }
            }
            return n3;
        }
        
        boolean checkIfAssetExistsWithName(final int n, final String s) {
            try {
                this.mPackRes.getAssets().open("icons/res/drawable-" + this.getExtForDensity(n) + "/" + s + ".png");
                return true;
            }
            catch (IOException ex) {
                return false;
            }
        }
        
        Bitmap compileIcon(Bitmap o) {
            if(true){
                return o;
            }
            final int dpToPx = LauncherUtils.dpToPx(72.0f, this.mContext);
            this.defaultIconPaint.setAntiAlias(true);
            this.iconMaskPaint.setAntiAlias(true);
            this.iconMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            final Bitmap resizedBitmap = LauncherUtils.getResizedBitmap(o, (int)(dpToPx * this.mScale), (int)(dpToPx * this.mScale));
            Bitmap bitmap = null;
            Bitmap bitmap2 = null;
            final Bitmap bitmap3 = null;
            final Bitmap bitmap4 = Bitmap.createBitmap(dpToPx, dpToPx, Bitmap.Config.ARGB_8888);
            final Bitmap bitmap5 = Bitmap.createBitmap(dpToPx, dpToPx, Bitmap.Config.ARGB_8888);
        Label_0170_Outer:
            while (true) {
            Label_0195_Outer:
                while (true) {
                Block_11_Outer:
                    while (true) {
                        Block_8: {
                            if (this.mIconMaskInt != Integer.MIN_VALUE) {
                                o = this.getBitmap(this.mIconMaskInt);
                            }
                            else {
                                o = bitmap;
                                if (this.mIconMaskName != null) {
                                    o = bitmap;
                                    if (this.hasAlternateStructure) {
                                        break Block_8;
                                    }
                                }
                            }
                            if (this.mIconBackInts.size() == 0) {
                                break Label_0195_Outer;
                            }
                            bitmap = this.getBitmap(this.mIconBackInts.get(this.mRandom.nextInt(this.mIconBackInts.size())));
                            if (this.mIconUponInt == Integer.MIN_VALUE) {
                                break Block_11_Outer;
                            }
                            bitmap2 = this.getBitmap(this.mIconUponInt);
                            final Canvas canvas = new Canvas(bitmap5);
                            if (bitmap != null) {
                                canvas.drawBitmap(bitmap, LauncherUtils.getResizedMatrix(bitmap, dpToPx, dpToPx), this.defaultIconPaint);
                            }
                            final int n = (int)((dpToPx - dpToPx * this.mScale) / 2.0f);
                            final int n2 = (int)((dpToPx - dpToPx * this.mScale) / 2.0f);
                            final Canvas canvas2 = new Canvas(bitmap4);
                            canvas2.drawBitmap(resizedBitmap, (float)n, (float)n2, this.defaultIconPaint);
                            if (o != null) {
                                canvas2.drawBitmap((Bitmap)o, LauncherUtils.getResizedMatrix((Bitmap)o, dpToPx, dpToPx), this.iconMaskPaint);
                            }
                            canvas.drawBitmap(bitmap4, LauncherUtils.getResizedMatrix(bitmap4, dpToPx, dpToPx), this.defaultIconPaint);
                            if (bitmap2 != null) {
                                canvas.drawBitmap(bitmap2, LauncherUtils.getResizedMatrix(bitmap2, dpToPx, dpToPx), this.defaultIconPaint);
                            }
                            FileManager.saveBitmap(this.mContext, resizedBitmap, "/testbmp/", "default.png");
                            FileManager.saveBitmap(this.mContext, bitmap4, "/testbmp/", "defaultModified.png");
                            FileManager.saveBitmap(this.mContext, bitmap, "/testbmp/", "back.png");
                            FileManager.saveBitmap(this.mContext, (Bitmap)o, "/testbmp/", "mask.png");
                            FileManager.saveBitmap(this.mContext, bitmap2, "/testbmp/", "upon.png");
                            return bitmap5;
                        }
                        //try {
                        //    o = this.getBitmapForDensityWithName(this.mIconMaskName);
                        //    continue Label_0170_Outer;
                        //    bitmap = bitmap2;
                        //    // iftrue(Label_0170:, this.mIconBackNames.size() == 0)
                        //    while (true) {
                        //        Block_10: {
                        //            break Block_10;
                        //            try {
                        //                bitmap = this.getBitmapForDensityWithName(this.mIconBackNames.get(this.mRandom.nextInt(this.mIconBackNames.size())));
                        //                continue Label_0195_Outer;
                        //                try {
                        //                    bitmap2 = this.getBitmapForDensityWithName(this.mIconUponName);
                        //                }
                        //                catch (IOException io) {
                        //                    bitmap2 = bitmap3;
                        //                }
                        //                continue Block_11_Outer;
                        //                bitmap2 = bitmap3;
                        //                // iftrue(Label_0195:, this.mIconUponName == null)
                        //                bitmap2 = bitmap3;
                        //            }
                        //            // iftrue(Label_0195:, !this.hasAlternateStructure)
                        //            catch (IOException io2) {
                        //                bitmap = bitmap2;
                        //            }
                        //        }
                        //        bitmap = bitmap2;
                        //        continue;
                        //    }
                        //}
                        //// iftrue(Label_0170:, !this.hasAlternateStructure)
                        //catch (IOException ex) {
                        //    o = bitmap;
                        //    continue Label_0170_Outer;
                        //}
                        //break;
                    }
                    //break;
                }
                //break;
            }
        }
        
        public Bitmap decodeSampledBitmapFromResource(final String s, final String s2, final int n, final int n2) throws IOException {
            final BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
            bitmapFactoryOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(this.mPackRes.getAssets().open("icons/res/drawable-" + s2 + "/" + s + ".png"), (Rect)null, bitmapFactoryOptions);
            bitmapFactoryOptions.inSampleSize = calculateInSampleSize(bitmapFactoryOptions, n, n2);
            bitmapFactoryOptions.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(this.mPackRes.getAssets().open("icons/res/drawable-" + s2 + "/" + s + ".png"), (Rect)null, bitmapFactoryOptions);
        }
        
        Bitmap getBitmap(final int n) {
            return BitmapFactory.decodeResource(this.mPackRes, n);
        }
        
        Bitmap getBitmapForDensityWithComponent(final String s) throws IOException {
            return this.getBitmapForDensityWithName(this.mIconMapStrings.get(s));
        }
        
        Bitmap getBitmapForDensityWithName(final String s) throws IOException {
            if (this.mExtention == null) {
                this.mExtention = this.getHighestFittingExistingExtentionWithName(s);
            }
            return this.decodeSampledBitmapFromResource(s, this.mExtention, LauncherUtils.dpToPx(72.0f, this.mContext), LauncherUtils.dpToPx(72.0f, this.mContext));
        }
        
        String getExtForDensity(final int n) {
            if (n <= 120) {
                return "ldpi";
            }
            if (n <= 160) {
                return "mdpi";
            }
            if (n <= 240) {
                return "hdpi";
            }
            if (n <= 320) {
                return "xhdpi";
            }
            if (n <= 480) {
                return "xxhdpi";
            }
            return "xxxhdpi";
        }
        
        String getHighestFittingExistingExtentionWithName(final String s) {
            final int densityDpi = this.mContext.getResources().getDisplayMetrics().densityDpi;
            String s2 = "null";
            final int[] array2;
            final int[] array = array2 = new int[6];
            array2[0] = 120;
            array2[1] = 160;
            array2[2] = 240;
            array2[3] = 320;
            array2[4] = 480;
            array2[5] = 640;
            final boolean[] array3 = new boolean[array.length];
            String extForDensity;
            for (int i = 0; i < array3.length; ++i, s2 = extForDensity) {
                extForDensity = s2;
                if (this.checkIfAssetExistsWithName(array[i], s)) {
                    if (!s2.equals("null") && !s2.equals(this.getExtForDensity(densityDpi))) {
                        extForDensity = s2;
                        if (s2.equals(this.getExtForDensity(densityDpi))) {
                            continue;
                        }
                        extForDensity = s2;
                        if (array[i] <= densityDpi) {
                            continue;
                        }
                    }
                    extForDensity = this.getExtForDensity(array[i]);
                }
            }
            return s2;
        }
        
        public Bitmap getIconBitmap(final String s, final Bitmap bitmap) {
            if (this.mPackageName.equals("")) {
                return bitmap;
            }
            if ((!this.hasAlternateStructure && !this.mIconMap.containsKey(s)) || (this.hasAlternateStructure && !this.mIconMapStrings.containsKey(s))) {
                return this.compileIcon(bitmap);
            }
            if (this.hasAlternateStructure) {
                try {
                    return this.getBitmapForDensityWithComponent(s);
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                    return this.compileIcon(bitmap);
                }
            }
            try {
                final Bitmap decodeResource = BitmapFactory.decodeResource(this.mPackRes, (int)this.mIconMap.get(s));
                if (decodeResource == null) {
                    return this.compileIcon(bitmap);
                }
                return decodeResource;
            }
            catch (Resources.NotFoundException ex2) {
                return this.compileIcon(bitmap);
            }
        }
        
        public void loadAll(final UpdateListener updateListener) throws SAXException, PackageManager.NameNotFoundException, IOException, ParserConfigurationException {
            this.mIconMap.clear();
            this.mIconMapStrings.clear();
            this.loadSelected(updateListener, null);
        }
        
        public void loadAllInstalled(final UpdateListener updateListener) throws SAXException, PackageManager.NameNotFoundException, IOException, ParserConfigurationException {
            this.mIconMap.clear();
            this.mIconMapStrings.clear();
            this.loadSelectedPackage(updateListener, null);
        }
        
        public void loadSelected(final UpdateListener p0, final List<LauncherActivityInfo> p1) throws SAXException, PackageManager.NameNotFoundException, IOException, ParserConfigurationException {
            if(mPackRes == null){
                return;
            }
            if(p0 != null){
                mCountListener = p0;
            }

            java.io.InputStream raw = null;
            javax.xml.parsers.SAXParserFactory saxFactory = SAXParserFactory.newInstance();
            javax.xml.parsers.SAXParser saxParser = saxFactory.newSAXParser();
            AssetManager am = mPackRes.getAssets();

            //first time, get total line count
            try{
                raw = am.open("appfilter.xml");
                hasAlternateStructure = false;
                CountHandler ch = new CountHandler();
                saxParser.parse(raw, ch);
            }
            catch(java.io.FileNotFoundException fnfe){
                fnfe.printStackTrace();
            }finally{
                if(raw != null){
                    raw.close();
                }
            }

            try{
                raw = am.open("appfilter.xml");
                hasAlternateStructure = false;
                saxParser.parse(raw, IconPack.this);
            }
            catch(java.io.FileNotFoundException fnfe2){
                fnfe2.printStackTrace();
            }finally{
                if(raw != null){
                    raw.close();
                }
            }

            //java.io.InputStream isr = mPackRes.getAssets().open("icons/res/xml/appfilter.xml");


            if(p1 != null){
                android.util.Log.v(TAG, "method:loadSelected mPackageName = " + mPackageName);
                if(!TextUtils.isEmpty(mPackageName)){
                    mPendingApps = new ArrayList<String>();
                    java.util.Iterator<LauncherActivityInfo> itr = p1.iterator();
                    while(itr.hasNext()){
                        LauncherActivityInfo info = itr.next();
                        String name = info.getComponentName().toString();
                        mPendingApps.add(name);
                    }
                }
            }
            isLoaded = true;


            //
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     0: aload_2        
            //     1: ifnonnull       27
            //     4: aload_0        
            //     5: aconst_null    
            //     6: putfield        com/dravite/newlayouttest/general_helpers/IconPackManager.IconPack.mPendingApps:Ljava/util/List;
            //     9: aload_0        
            //    10: iconst_1       
            //    11: putfield        com/dravite/newlayouttest/general_helpers/IconPackManager.IconPack.isLoaded:Z
            //    14: aload_0        
            //    15: getfield        com/dravite/newlayouttest/general_helpers/IconPackManager.IconPack.mPackageName:Ljava/lang/String;
            //    18: ldc             ""
            //    20: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
            //    23: ifeq            84
            //    26: return         
            //    27: aload_0        
            //    28: new             Ljava/util/ArrayList;
            //    31: dup            
            //    32: invokespecial   java/util/ArrayList.<init>:()V
            //    35: putfield        com/dravite/newlayouttest/general_helpers/IconPackManager.IconPack.mPendingApps:Ljava/util/List;
            //    38: aload_2        
            //    39: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
            //    44: astore_2       
            //    45: aload_2        
            //    46: invokeinterface java/util/Iterator.hasNext:()Z
            //    51: ifeq            9
            //    54: aload_2        
            //    55: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
            //    60: checkcast       Landroid/content/pm/LauncherActivityInfo;
            //    63: astore_3       
            //    64: aload_0        
            //    65: getfield        com/dravite/newlayouttest/general_helpers/IconPackManager.IconPack.mPendingApps:Ljava/util/List;
            //    68: aload_3        
            //    69: invokevirtual   android/content/pm/LauncherActivityInfo.getComponentName:()Landroid/content/ComponentName;
            //    72: invokevirtual   android/content/ComponentName.toString:()Ljava/lang/String;
            //    75: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
            //    80: pop            
            //    81: goto            45
            //    84: aload_1        
            //    85: astore_2       
            //    86: aload_1        
            //    87: ifnonnull       99
            //    90: new             Lcom/dravite/newlayouttest/general_helpers/IconPackManager.IconPack.1;
            //    93: dup            
            //    94: aload_0        
            //    95: invokespecial   com/dravite/newlayouttest/general_helpers/IconPackManager.IconPack.1.<init>:(Lcom/dravite/newlayouttest/general_helpers/IconPackManager.IconPack;)V
            //    98: astore_2       
            //    99: aload_0        
            //   100: aload_2        
            //   101: putfield        com/dravite/newlayouttest/general_helpers/IconPackManager.IconPack.mCountListener:Lcom/dravite/newlayouttest/general_helpers/UpdateListener;
            //   104: aload_0        
            //   105: getfield        com/dravite/newlayouttest/general_helpers/IconPackManager.IconPack.mPackRes:Landroid/content/res/Resources;
            //   108: invokevirtual   android/content/res/Resources.getAssets:()Landroid/content/res/AssetManager;
            //   111: ldc_w           "appfilter.xml"
            //   114: invokevirtual   android/content/res/AssetManager.open:(Ljava/lang/String;)Ljava/io/InputStream;
            //   117: astore_1       
            //   118: aload_0        
            //   119: iconst_0       
            //   120: putfield        com/dravite/newlayouttest/general_helpers/IconPackManager.IconPack.hasAlternateStructure:Z
            //   123: invokestatic    javax/xml/parsers/SAXParserFactory.newInstance:()Ljavax/xml/parsers/SAXParserFactory;
            //   126: invokevirtual   javax/xml/parsers/SAXParserFactory.newSAXParser:()Ljavax/xml/parsers/SAXParser;
            //   129: astore_2       
            //   130: aload_2        
            //   131: aload_1        
            //   132: new             Lcom/dravite/newlayouttest/general_helpers/IconPackManager.IconPack.CountHandler;
            //   135: dup            
            //   136: aload_0        
            //   137: invokespecial   com/dravite/newlayouttest/general_helpers/IconPackManager.IconPack.CountHandler.<init>:(Lcom/dravite/newlayouttest/general_helpers/IconPackManager.IconPack;)V
            //   140: invokevirtual   javax/xml/parsers/SAXParser.parse:(Ljava/io/InputStream;Lorg/xml/sax/helpers/DefaultHandler;)V
            //   143: aload_0        
            //   144: getfield        com/dravite/newlayouttest/general_helpers/IconPackManager.IconPack.mPackRes:Landroid/content/res/Resources;
            //   147: invokevirtual   android/content/res/Resources.getAssets:()Landroid/content/res/AssetManager;
            //   150: ldc_w           "appfilter.xml"
            //   153: invokevirtual   android/content/res/AssetManager.open:(Ljava/lang/String;)Ljava/io/InputStream;
            //   156: astore_1       
            //   157: aload_0        
            //   158: iconst_0       
            //   159: putfield        com/dravite/newlayouttest/general_helpers/IconPackManager.IconPack.hasAlternateStructure:Z
            //   162: aload_0        
            //   163: iconst_0       
            //   164: putfield        com/dravite/newlayouttest/general_helpers/IconPackManager.IconPack.mCurrentItem:I
            //   167: aload_2        
            //   168: aload_1        
            //   169: aload_0        
            //   170: invokevirtual   javax/xml/parsers/SAXParser.parse:(Ljava/io/InputStream;Lorg/xml/sax/helpers/DefaultHandler;)V
            //   173: return         
            //   174: astore_1       
            //   175: aload_0        
            //   176: getfield        com/dravite/newlayouttest/general_helpers/IconPackManager.IconPack.mPackRes:Landroid/content/res/Resources;
            //   179: invokevirtual   android/content/res/Resources.getAssets:()Landroid/content/res/AssetManager;
            //   182: ldc_w           "icons/res/xml/appfilter.xml"
            //   185: invokevirtual   android/content/res/AssetManager.open:(Ljava/lang/String;)Ljava/io/InputStream;
            //   188: astore_1       
            //   189: aload_0        
            //   190: iconst_1       
            //   191: putfield        com/dravite/newlayouttest/general_helpers/IconPackManager.IconPack.hasAlternateStructure:Z
            //   194: goto            123
            //   197: astore_1       
            //   198: aload_0        
            //   199: getfield        com/dravite/newlayouttest/general_helpers/IconPackManager.IconPack.mPackRes:Landroid/content/res/Resources;
            //   202: invokevirtual   android/content/res/Resources.getAssets:()Landroid/content/res/AssetManager;
            //   205: ldc_w           "icons/res/xml/appfilter.xml"
            //   208: invokevirtual   android/content/res/AssetManager.open:(Ljava/lang/String;)Ljava/io/InputStream;
            //   211: astore_1       
            //   212: aload_0        
            //   213: iconst_1       
            //   214: putfield        com/dravite/newlayouttest/general_helpers/IconPackManager.IconPack.hasAlternateStructure:Z
            //   217: goto            162
            //    Exceptions:
            //  throws org.xml.sax.SAXException
            //  throws android.content.pm.PackageManager.NameNotFoundException
            //  throws java.io.IOException
            //  throws javax.xml.parsers.ParserConfigurationException
            //    Signature:
            //  (Lcom/dravite/newlayouttest/general_helpers/UpdateListener;Ljava/util/List<Landroid/content/pm/LauncherActivityInfo;>;)V
            //    Exceptions:
            //  Try           Handler
            //  Start  End    Start  End    Type                           
            //  -----  -----  -----  -----  -------------------------------
            //  104    123    174    197    Ljava/io/FileNotFoundException;
            //  143    162    197    220    Ljava/io/FileNotFoundException;
            // 
            // The error that occurred was:
            // 
            // java.lang.IllegalStateException: Expression is linked from several locations: Label_0162:
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
            //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:556)
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
            //throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        public void loadSelectedPackage(final UpdateListener updateListener, final String s) throws SAXException, PackageManager.NameNotFoundException, IOException, ParserConfigurationException {
            this.loadSelected(updateListener, ((LauncherApps)this.mContext.getSystemService(Context.LAUNCHER_APPS_SERVICE)).getActivityList(s, Process.myUserHandle()));
        }
        
        @Override
        public void startElement(String s, final String s2, final String s3, final Attributes attributes) throws SAXException {
            int n = -1;
            ++this.mCurrentItem;
            if (attributes.getValue(0) == null || !attributes.getValue(0).contains("ComponentInfo") || this.mPendingApps == null || this.mPendingApps.contains(attributes.getValue(0))) {
                if (this.mCountListener != null) {
                    this.mCountListener.update(this.mCurrentItem, this.mLineCount);
                }
                if (this.hasAlternateStructure) {
                    s = s3.toLowerCase();
                    switch (s.hashCode()) {
                        case -737518368: {
                            if (s.equals("iconback")) {
                                n = 0;
                                break;
                            }
                            break;
                        }
                        case -737190171: {
                            if (s.equals("iconmask")) {
                                n = 1;
                                break;
                            }
                            break;
                        }
                        case -736937549: {
                            if (s.equals("iconupon")) {
                                n = 2;
                                break;
                            }
                            break;
                        }
                        case 109250890: {
                            if (s.equals("scale")) {
                                n = 3;
                                break;
                            }
                            break;
                        }
                    }
                    switch (n) {
                        case 0: {
                            for (int i = 0; i < attributes.getLength(); ++i) {
                                this.mIconBackNames.add(attributes.getValue(i));
                            }
                            return;
                        }
                        case 1: {
                            this.mIconMaskName = attributes.getValue(0);
                            return;
                        }
                        case 2: {
                            this.mIconUponName = attributes.getValue(0);
                            return;
                        }
                        case 3: {
                            this.mScale = Float.parseFloat(attributes.getValue(0));
                            return;
                        }
                    }
                }
                else {
                    s = s3.toLowerCase();
                    switch (s.hashCode()) {
                        case -737518368: {
                            if (s.equals("iconback")) {
                                n = 0;
                                break;
                            }
                            break;
                        }
                        case -737190171: {
                            if (s.equals("iconmask")) {
                                n = 1;
                                break;
                            }
                            break;
                        }
                        case -736937549: {
                            if (s.equals("iconupon")) {
                                n = 2;
                                break;
                            }
                            break;
                        }
                        case 109250890: {
                            if (s.equals("scale")) {
                                n = 3;
                                break;
                            }
                            break;
                        }
                    }
                    switch (n) {
                        case 0: {
                            for (int j = 0; j < attributes.getLength(); ++j) {
                                this.mIconBackInts.add(this.mPackRes.getIdentifier(attributes.getValue(j), "drawable", this.mPackageName));
                            }
                            return;
                        }
                        case 1: {
                            this.mIconMaskInt = this.mPackRes.getIdentifier(attributes.getValue(0), "drawable", this.mPackageName);
                            return;
                        }
                        case 2: {
                            this.mIconUponInt = this.mPackRes.getIdentifier(attributes.getValue(0), "drawable", this.mPackageName);
                            return;
                        }
                        case 3: {
                            this.mScale = Float.parseFloat(attributes.getValue(0));
                            return;
                        }
                    }
                }
                if (attributes.getValue(0) != null && attributes.getValue(0).contains("ComponentInfo")) {
                    if (this.hasAlternateStructure) {
                        this.mIconMapStrings.put(attributes.getValue(0), attributes.getValue(1));
                        return;
                    }
                    this.mIconMap.put(attributes.getValue(0), this.mPackRes.getIdentifier(attributes.getValue(1), "drawable", this.mPackageName));
                }
            }
        }
        
        public class CountHandler extends DefaultHandler
        {

            IconPack mPack;

            public CountHandler() {
                mPack = IconPack.this;
                mPack.mLineCount = 0;
            }
            
            @Override
            public void startElement(final String s, final String s2, final String s3, final Attributes attributes) throws SAXException {
                if (attributes.getValue(0) != null && attributes.getValue(0).contains("ComponentInfo")) {
                    ++mPack.mLineCount;
                }
            }
        }
    }
    
    public static class Theme
    {
        public Drawable icon;
        public String label;
        public String packageName;
        
        public Theme(final String packageName, final String label, final Drawable icon) {
            this.packageName = packageName;
            this.label = label;
            this.icon = icon;
        }
    }
}
