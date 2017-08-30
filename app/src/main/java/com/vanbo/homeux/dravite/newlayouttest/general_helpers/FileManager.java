// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_helpers;

import java.io.OutputStream;
import android.graphics.Bitmap.CompressFormat;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import android.graphics.BitmapFactory;
import java.io.FileInputStream;
import android.graphics.Bitmap;
import android.content.Context;
import java.io.File;

public class FileManager
{
    public static final String PATH_APP_CACHE = "/apps/";
    
    public static boolean deleteRecursive(final File file) {
        if (file.isDirectory()) {
            final File[] listFiles = file.listFiles();
            for (int length = listFiles.length, i = 0; i < length; ++i) {
                deleteRecursive(listFiles[i]);
            }
        }
        return file.delete();
    }
    
    public static boolean fileExists(final Context context, final String s, final String s2) {
        return context != null && new File(context.getCacheDir().getPath() + "/" + s, s2).exists();
    }
    
    public static Bitmap loadBitmap(Context context, final String s, final String s2) {
        final File file = new File(context.getCacheDir().getPath() + "/" + s, s2);
        InputStream bitmap = null;
        try {
            bitmap = new FileInputStream(file);
            return BitmapFactory.decodeStream(bitmap);
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
            if(bitmap != null){
                try {
                    bitmap.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
    
    public static Bitmap loadBitmapFromData(Context context, final String s) {
        final File file = new File(new File(context.getApplicationInfo().dataDir + "/folderImg/").getPath() + "/", s);
        InputStream bitmap = null;
        try {
            bitmap = new FileInputStream(file);
            return BitmapFactory.decodeStream(bitmap);
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
            if(bitmap != null){
                try {
                    bitmap.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
    
    public static String readTextFile(final Context context, final String s, String line) {
        final File file = new File(context.getCacheDir().getAbsolutePath() + s, line);
        final StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
                sb.append('\n');
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    
    public static boolean renameBitmapFromData(final Context context, final String s, final String s2) {
        final File file = new File(context.getApplicationInfo().dataDir + "/folderImg/");
        return new File(file.getPath() + "/", s).renameTo(new File(file.getPath() + "/", s2));
    }
    
    public static void saveBitmap(final Context context, final Bitmap bitmap, final String s, final String s2) {
        if (bitmap == null) {
            return;
        }
        final File cacheDir = context.getCacheDir();
        final File file = new File(cacheDir.getPath() + s);
        final File file2 = new File(cacheDir.getPath() + s, s2);
        file.mkdirs();
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex2) {
            ex2.printStackTrace();
        }
    }
    
    public static void saveBitmapToData(final Context context, final Bitmap bitmap, final String s, final boolean b) {
        if (bitmap != null) {
            final File file = new File(context.getApplicationInfo().dataDir + "/folderImg/");
            final File file2 = new File(file.getPath(), s);
            if (b || !file2.exists()) {
                file.mkdirs();
                try {
                    final FileOutputStream fileOutputStream = new FileOutputStream(file2);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
                catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                catch (IOException ex2) {
                    ex2.printStackTrace();
                }
            }
        }
    }
    
    public static void saveTextFile(final Context context, final String s, final String s2, final String s3) {
        final File file = new File(context.getCacheDir().getAbsolutePath() + s);
        file.mkdirs();
        final File file2 = new File(file, s2);
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(file2);
            fileOutputStream.write(s3.getBytes());
            fileOutputStream.close();
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex2) {
            ex2.printStackTrace();
        }
    }
}
