// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.settings.backup_restore;

import android.view.View.OnLongClickListener;
import android.view.animation.DecelerateInterpolator;
import android.content.res.ColorStateList;
import android.animation.TimeInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CheckBox;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import java.util.Iterator;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.FileManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog;
import android.view.View;
//import android.view.View.OnClickListener;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import java.util.Date;
import java.text.DateFormat;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.vanbo.homeux.dravite.newlayouttest.LauncherLog;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.Context;
import android.os.Environment;
import java.text.DecimalFormat;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.File;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.content.pm.PackageManager;
import com.dravite.homeux.R;

public class BackupRestoreActivity extends AppCompatActivity
{
    private static final int PERM_REQUEST_READ_STORAGE = 102;
    private FloatingActionButton floatingActionButton;
    public boolean isInDeleteMode;
    private BackupAdapter mAdapter;
    
    public BackupRestoreActivity() {
        this.isInDeleteMode = false;
    }
    
    public static void copyDirectoryOneLocationToAnotherLocation(final File file, final File file2) throws IOException {
        if (file.isDirectory()) {
            if (!file2.exists()) {
                file2.mkdir();
            }
            final String[] list = file.list();
            for (int i = 0; i < file.listFiles().length; ++i) {
                copyDirectoryOneLocationToAnotherLocation(new File(file, list[i]), new File(file2, list[i]));
            }
        }
        else {
            final FileInputStream fileInputStream = new FileInputStream(file);
            final FileOutputStream fileOutputStream = new FileOutputStream(file2);
            final byte[] array = new byte[1024];
            while (true) {
                final int read = fileInputStream.read(array);
                if (read <= 0) {
                    break;
                }
                fileOutputStream.write(array, 0, read);
            }
            fileInputStream.close();
            fileOutputStream.close();
        }
    }
    
    public static String formatFileSize(final long n) {
        final double n2 = n;
        final double n3 = n / 1024.0;
        final double n4 = n / 1024.0 / 1024.0;
        final double n5 = n / 1024.0 / 1024.0 / 1024.0;
        final double n6 = n / 1024.0 / 1024.0 / 1024.0 / 1024.0;
        final DecimalFormat decimalFormat = new DecimalFormat("0.00");
        if (n6 > 1.0) {
            return decimalFormat.format(n6).concat(" TB");
        }
        if (n5 > 1.0) {
            return decimalFormat.format(n5).concat(" GB");
        }
        if (n4 > 1.0) {
            return decimalFormat.format(n4).concat(" MB");
        }
        if (n3 > 1.0) {
            return decimalFormat.format(n3).concat(" KB");
        }
        return decimalFormat.format(n2).concat(" Bytes");
    }
    
    void backup(final boolean b, final boolean b2, final boolean b3, final boolean b4, final String s) {
        final File file = new File(Environment.getExternalStorageDirectory().getPath() + "/HomeUX/" + s + "/");
        file.mkdirs();
        if (b) {
            this.backupTo(new File(file.getPath() + "/folders/"), this.getFileFromInternal("/cache/Shortcuts/"), this.getFileFromInternal("/cache/apps/"), this.getFileFromInternal("/folderImg/"), this.getFileFromInternal("/somedata.json"));
        }
        if (b2) {
            this.backupTo(new File(file.getPath() + "/quickApps/"), this.getFileFromInternal("/quickApps.json"));
        }
        if (b4) {
            SharedPreferenceHelper.saveSharedPreferencesToFile((Context)this, new File(file.getPath() + "/general/", "/com.vanbo.homeux.dravite.homeux_preferences.xml"));
        }
        if (b3) {
            this.backupTo(new File(file.getPath() + "/hiddenApps/"), this.getFileFromInternal("/hiddenApps.json"));
        }
        try {
            this.createDataFile(file, this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName);
        }
        catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    void backupTo(final File file, final File... array) {
        final int length = array.length;
        int i = 0;
    Label_0061_Outer:
        while (i < length) {
            final File file2 = array[i];
            //while (true) {
                try {
                    final File file3 = new File(file.getPath(), file2.getName());
                    if (file3.exists()) {
                        file3.delete();
                    }
                    file.mkdirs();
                    copyDirectoryOneLocationToAnotherLocation(file2, file3);
                    ++i;
                    continue Label_0061_Outer;
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                    continue;
                }
                //break;
            //}
            //break;
        }
    }
    
    void createDataFile(File file, final String s) {
        try {
            file = new File(file.getPath(), "/data");
            file.createNewFile();
            final FileOutputStream fileOutputStream = new FileOutputStream(file);
            final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(s);
            outputStreamWriter.close();
            fileOutputStream.close();
        }
        catch (IOException ex) {
            LauncherLog.w("Exception", "File write failed: " + ex.toString());
        }
    }
    
    void getBackups(final List<BackupObject> list) {
        list.clear();
        final File file = new File(Environment.getExternalStorageDirectory().getPath() + "/HomeUX/");
        if (!file.exists()) {
            file.mkdirs();
        }
        final File[] listFiles = file.listFiles();
        for (int length = listFiles.length, i = 0; i < length; ++i) {
            final File file2 = listFiles[i];
            if (file2.isDirectory() && new File(file2.getPath() + "/data").exists()) {
                list.add(new BackupObject(this.getFolderDate(file2), "1.0", file2.getName(), formatFileSize(this.getFolderSize(file2)), new ArrayList<String>()));
            }
        }
    }
    
    File getFileFromInternal(final String s) {
        return new File(new File(this.getApplicationInfo().dataDir).getPath() + "/" + s);
    }
    
    String getFolderDate(final File file) {
        return ((SimpleDateFormat)DateFormat.getDateInstance(3, Locale.getDefault())).format(new Date(file.lastModified()));
    }
    
    long getFolderSize(final File file) {
        long n = 0L;
        long length2;
        if (file.isDirectory()) {
            final File[] listFiles = file.listFiles();
            final int length = listFiles.length;
            int n2 = 0;
            while (true) {
                length2 = n;
                if (n2 >= length) {
                    break;
                }
                n += this.getFolderSize(listFiles[n2]);
                ++n2;
            }
        }
        else {
            length2 = file.length();
        }
        return length2;
    }
    
    @Override
    public void onBackPressed() {
        if (this.isInDeleteMode) {
            this.toggleDeleteMode(false);
            ((RecyclerView.Adapter)this.mAdapter).notifyDataSetChanged();
            return;
        }
        super.onBackPressed();
    }
    
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.backup_restore_activity);
        this.setSupportActionBar((Toolbar)this.findViewById(R.id.toolbar));
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        final RecyclerView recyclerView = (RecyclerView)this.findViewById(R.id.backupList);
        recyclerView.setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager((Context)this, 1));
        if (ContextCompat.checkSelfPermission((Context)this, "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission((Context)this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            final ArrayList<BackupObject> list = new ArrayList<BackupObject>();
            this.getBackups(list);
            recyclerView.setAdapter((RecyclerView.Adapter)(this.mAdapter = new BackupAdapter((Context)this, list)));
            (this.floatingActionButton = (FloatingActionButton)this.findViewById(R.id.floatingActionButton)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    if (BackupRestoreActivity.this.isInDeleteMode) {
                        new AlertDialog.Builder((Context)BackupRestoreActivity.this, R.style.DialogTheme).setTitle("\u5220\u9664").setMessage("\u786e\u8ba4\u8981\u5220\u9664\u8fd9\u4e9b\u5907\u4efd\u5417?").setNegativeButton(R.string.app_name, null).setPositiveButton(R.string.app_name, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialogInterface, int i) {
                                for (final BackupObject backupObject : BackupRestoreActivity.this.mAdapter.mSelected) {
                                    FileManager.deleteRecursive(new File(Environment.getExternalStorageDirectory().getPath() + "/HomeUX/" + backupObject.backupName));
                                    i = BackupRestoreActivity.this.mAdapter.mAllBackups.indexOf(backupObject);
                                    BackupRestoreActivity.this.mAdapter.mAllBackups.remove(i);
                                    ((RecyclerView.Adapter)BackupRestoreActivity.this.mAdapter).notifyItemRemoved(i);
                                }
                                for (i = 0; i < BackupRestoreActivity.this.mAdapter.mAllBackups.size(); ++i) {
                                    ((RecyclerView.Adapter)BackupRestoreActivity.this.mAdapter).notifyItemChanged(i);
                                }
                                BackupRestoreActivity.this.toggleDeleteMode(false);
                            }
                        }).show();
                        return;
                    }
                    final View inflate = LayoutInflater.from((Context)BackupRestoreActivity.this).inflate(R.layout.restore_view, (ViewGroup)null);
                    ((TextView)inflate.findViewById(R.id.description)).setText((CharSequence)"\u8bf7\u9009\u62e9\u4f60\u8981\u5907\u4efd\u7684\u7ec4\u4ef6.");
                    final CheckBox checkBox = (CheckBox)inflate.findViewById(R.id.folders);
                    final CheckBox checkBox2 = (CheckBox)inflate.findViewById(R.id.quickapps);
                    final CheckBox checkBox3 = (CheckBox)inflate.findViewById(R.id.hiddenapps);
                    final CheckBox checkBox4 = (CheckBox)inflate.findViewById(R.id.general);
                    final CompoundButton.OnCheckedChangeListener compoundButtonOnCheckedChangeListener = (CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
                        final /* synthetic */ AlertDialog valdialog = new AlertDialog.Builder((Context)BackupRestoreActivity.this, R.style.DialogTheme).setTitle(R.string.dialog_restore_title).setView(inflate).setNegativeButton(R.string.app_name, null).setPositiveButton(R.string.dialog_restore_title, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                            final /* synthetic */ CheckBox valcbFolders = checkBox;
                            final /* synthetic */ CheckBox valcbGeneral = checkBox4;
                            final /* synthetic */ CheckBox valcbHidden = checkBox3;
                            final /* synthetic */ CheckBox valcbQA = checkBox2;
                            
                            public void onClick(final DialogInterface dialogInterface, final int n) {
                                BackupRestoreActivity.this.backup(this.valcbFolders.isChecked(), this.valcbQA.isChecked(), this.valcbHidden.isChecked(), this.valcbGeneral.isChecked(), "Backup-" + System.currentTimeMillis());
                                BackupRestoreActivity.this.getBackups(BackupRestoreActivity.this.mAdapter.mAllBackups);
                                ((RecyclerView.Adapter)BackupRestoreActivity.this.mAdapter).notifyDataSetChanged();
                            }
                        }).show();
                        
                        public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                            this.valdialog.getButton(-1).setEnabled(checkBox.isChecked() || checkBox4.isChecked() || checkBox3.isChecked() || checkBox2.isChecked());
                        }
                    };
                    checkBox.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)compoundButtonOnCheckedChangeListener);
                    checkBox2.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)compoundButtonOnCheckedChangeListener);
                    checkBox3.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)compoundButtonOnCheckedChangeListener);
                    checkBox4.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)compoundButtonOnCheckedChangeListener);
                }
            });
            return;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_EXTERNAL_STORAGE") || ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            return;
        }
        ActivityCompat.requestPermissions(this, new String[] { "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE" }, 102);
    }
    
    @Override
    public void onRequestPermissionsResult(final int n, final String[] array, final int[] array2) {
        switch (n) {
            case 102: {
                if (array2.length > 0 && array2[0] == 0) {
                    final ArrayList<BackupObject> list = new ArrayList<BackupObject>();
                    this.getBackups(list);
                    ((RecyclerView)this.findViewById(R.id.backupList)).setAdapter((RecyclerView.Adapter)(this.mAdapter = new BackupAdapter((Context)this, list)));
                    (this.floatingActionButton = (FloatingActionButton)this.findViewById(R.id.floatingActionButton)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                        public void onClick(final View view) {
                            if (BackupRestoreActivity.this.isInDeleteMode) {
                                new AlertDialog.Builder((Context)BackupRestoreActivity.this, R.style.DialogTheme).setTitle("\u5220\u9664").setMessage("\u786e\u8ba4\u8981\u5220\u9664\u90a3\u4e9b\u5907\u4efd\u5417?").setNegativeButton(R.string.app_name, null).setPositiveButton(R.string.app_name, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialogInterface, int i) {
                                        for (final BackupObject backupObject : BackupRestoreActivity.this.mAdapter.mSelected) {
                                            FileManager.deleteRecursive(new File(Environment.getExternalStorageDirectory().getPath() + "/HomeUX/" + backupObject.backupName));
                                            i = BackupRestoreActivity.this.mAdapter.mAllBackups.indexOf(backupObject);
                                            BackupRestoreActivity.this.mAdapter.mAllBackups.remove(i);
                                            ((RecyclerView.Adapter)BackupRestoreActivity.this.mAdapter).notifyItemRemoved(i);
                                        }
                                        for (i = 0; i < BackupRestoreActivity.this.mAdapter.mAllBackups.size(); ++i) {
                                            ((RecyclerView.Adapter)BackupRestoreActivity.this.mAdapter).notifyItemChanged(i);
                                        }
                                        BackupRestoreActivity.this.toggleDeleteMode(false);
                                    }
                                }).show();
                                return;
                            }
                            final View inflate = LayoutInflater.from((Context)BackupRestoreActivity.this).inflate(R.layout.restore_view, (ViewGroup)null);
                            ((TextView)inflate.findViewById(R.id.description)).setText((CharSequence)"\u8bf7\u9009\u62e9\u8981\u5907\u4efd\u7684\u5185\u5bb9.");
                            final CheckBox checkBox = (CheckBox)inflate.findViewById(R.id.folders);
                            final CheckBox checkBox2 = (CheckBox)inflate.findViewById(R.id.quickapps);
                            final CheckBox checkBox3 = (CheckBox)inflate.findViewById(R.id.hiddenapps);
                            final CheckBox checkBox4 = (CheckBox)inflate.findViewById(R.id.general);
                            final CompoundButton.OnCheckedChangeListener compoundButtonOnCheckedChangeListener = (CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
                                final /* synthetic */ AlertDialog valdialog = new AlertDialog.Builder((Context)BackupRestoreActivity.this, R.style.DialogTheme).setTitle(R.string.dialog_restore_title).setView(inflate).setNegativeButton(R.string.app_name, null).setPositiveButton(R.string.dialog_restore_title, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                                    final /* synthetic */ CheckBox valcbFolders = checkBox;
                                    final /* synthetic */ CheckBox valcbGeneral = checkBox4;
                                    final /* synthetic */ CheckBox valcbHidden =checkBox3;
                                    final /* synthetic */ CheckBox valcbQA = checkBox2;
                                    
                                    public void onClick(final DialogInterface dialogInterface, final int n) {
                                        BackupRestoreActivity.this.backup(this.valcbFolders.isChecked(), this.valcbQA.isChecked(), this.valcbHidden.isChecked(), this.valcbGeneral.isChecked(), "Backup-" + System.currentTimeMillis());
                                        BackupRestoreActivity.this.getBackups(BackupRestoreActivity.this.mAdapter.mAllBackups);
                                        ((RecyclerView.Adapter)BackupRestoreActivity.this.mAdapter).notifyDataSetChanged();
                                    }
                                }).show();
                                
                                public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                                    this.valdialog.getButton(-1).setEnabled(checkBox.isChecked() || checkBox4.isChecked() || checkBox3.isChecked() || checkBox2.isChecked());
                                }
                            };
                            checkBox.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)compoundButtonOnCheckedChangeListener);
                            checkBox2.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)compoundButtonOnCheckedChangeListener);
                            checkBox3.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)compoundButtonOnCheckedChangeListener);
                            checkBox4.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)compoundButtonOnCheckedChangeListener);
                        }
                    });
                    return;
                }
                break;
            }
        }
    }
    
    void restore(final boolean p0, final boolean p1, final boolean p2, final boolean p3, final String p4) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: putstatic       com/dravite/newlayouttest/LauncherActivity.updateAfterSettings:Z
        //     4: new             Ljava/io/File;
        //     7: dup            
        //     8: new             Ljava/lang/StringBuilder;
        //    11: dup            
        //    12: invokespecial   java/lang/StringBuilder.<init>:()V
        //    15: invokestatic    android/os/Environment.getExternalStorageDirectory:()Ljava/io/File;
        //    18: invokevirtual   java/io/File.getPath:()Ljava/lang/String;
        //    21: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    24: ldc             "/HomeUX/"
        //    26: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    29: aload           5
        //    31: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    34: ldc             "/"
        //    36: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    39: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    42: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //    45: astore          5
        //    47: new             Ljava/io/File;
        //    50: dup            
        //    51: aload_0        
        //    52: invokevirtual   com/dravite/newlayouttest/settings/backup_restore/BackupRestoreActivity.getApplicationInfo:()Landroid/content/pm/ApplicationInfo;
        //    55: getfield        android/content/pm/ApplicationInfo.dataDir:Ljava/lang/String;
        //    58: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //    61: astore          6
        //    63: aload_0        
        //    64: invokevirtual   com/dravite/newlayouttest/settings/backup_restore/BackupRestoreActivity.getCacheDir:()Ljava/io/File;
        //    67: invokestatic    com/dravite/newlayouttest/general_helpers/FileManager.deleteRecursive:(Ljava/io/File;)Z
        //    70: pop            
        //    71: iload_1        
        //    72: ifeq            371
        //    75: aload_0        
        //    76: aload           6
        //    78: iconst_2       
        //    79: anewarray       Ljava/io/File;
        //    82: dup            
        //    83: iconst_0       
        //    84: new             Ljava/io/File;
        //    87: dup            
        //    88: new             Ljava/lang/StringBuilder;
        //    91: dup            
        //    92: invokespecial   java/lang/StringBuilder.<init>:()V
        //    95: aload           5
        //    97: invokevirtual   java/io/File.getPath:()Ljava/lang/String;
        //   100: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   103: ldc_w           "/folders"
        //   106: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   109: ldc             "/folderImg/"
        //   111: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   114: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   117: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //   120: aastore        
        //   121: dup            
        //   122: iconst_1       
        //   123: new             Ljava/io/File;
        //   126: dup            
        //   127: new             Ljava/lang/StringBuilder;
        //   130: dup            
        //   131: invokespecial   java/lang/StringBuilder.<init>:()V
        //   134: aload           5
        //   136: invokevirtual   java/io/File.getPath:()Ljava/lang/String;
        //   139: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   142: ldc_w           "/folders"
        //   145: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   148: ldc             "/somedata.json"
        //   150: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   153: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   156: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //   159: aastore        
        //   160: invokevirtual   com/dravite/newlayouttest/settings/backup_restore/BackupRestoreActivity.backupTo:(Ljava/io/File;[Ljava/io/File;)V
        //   163: new             Ljava/io/File;
        //   166: dup            
        //   167: new             Ljava/lang/StringBuilder;
        //   170: dup            
        //   171: invokespecial   java/lang/StringBuilder.<init>:()V
        //   174: aload_0        
        //   175: invokevirtual   com/dravite/newlayouttest/settings/backup_restore/BackupRestoreActivity.getCacheDir:()Ljava/io/File;
        //   178: invokevirtual   java/io/File.getPath:()Ljava/lang/String;
        //   181: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   184: ldc_w           "/Shortcuts/"
        //   187: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   190: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   193: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //   196: invokevirtual   java/io/File.mkdirs:()Z
        //   199: pop            
        //   200: new             Ljava/io/File;
        //   203: dup            
        //   204: new             Ljava/lang/StringBuilder;
        //   207: dup            
        //   208: invokespecial   java/lang/StringBuilder.<init>:()V
        //   211: aload           5
        //   213: invokevirtual   java/io/File.getPath:()Ljava/lang/String;
        //   216: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   219: ldc_w           "/folders/Shortcuts/"
        //   222: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   225: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   228: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //   231: new             Ljava/io/File;
        //   234: dup            
        //   235: new             Ljava/lang/StringBuilder;
        //   238: dup            
        //   239: invokespecial   java/lang/StringBuilder.<init>:()V
        //   242: aload_0        
        //   243: invokevirtual   com/dravite/newlayouttest/settings/backup_restore/BackupRestoreActivity.getCacheDir:()Ljava/io/File;
        //   246: invokevirtual   java/io/File.getPath:()Ljava/lang/String;
        //   249: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   252: ldc_w           "/Shortcuts/"
        //   255: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   258: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   261: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //   264: invokestatic    com/dravite/newlayouttest/settings/backup_restore/BackupRestoreActivity.copyDirectoryOneLocationToAnotherLocation:(Ljava/io/File;Ljava/io/File;)V
        //   267: new             Ljava/io/File;
        //   270: dup            
        //   271: new             Ljava/lang/StringBuilder;
        //   274: dup            
        //   275: invokespecial   java/lang/StringBuilder.<init>:()V
        //   278: aload_0        
        //   279: invokevirtual   com/dravite/newlayouttest/settings/backup_restore/BackupRestoreActivity.getCacheDir:()Ljava/io/File;
        //   282: invokevirtual   java/io/File.getPath:()Ljava/lang/String;
        //   285: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   288: ldc_w           "/apps/"
        //   291: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   294: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   297: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //   300: invokevirtual   java/io/File.mkdirs:()Z
        //   303: pop            
        //   304: new             Ljava/io/File;
        //   307: dup            
        //   308: new             Ljava/lang/StringBuilder;
        //   311: dup            
        //   312: invokespecial   java/lang/StringBuilder.<init>:()V
        //   315: aload           5
        //   317: invokevirtual   java/io/File.getPath:()Ljava/lang/String;
        //   320: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   323: ldc_w           "/folders/apps/"
        //   326: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   329: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   332: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //   335: new             Ljava/io/File;
        //   338: dup            
        //   339: new             Ljava/lang/StringBuilder;
        //   342: dup            
        //   343: invokespecial   java/lang/StringBuilder.<init>:()V
        //   346: aload_0        
        //   347: invokevirtual   com/dravite/newlayouttest/settings/backup_restore/BackupRestoreActivity.getCacheDir:()Ljava/io/File;
        //   350: invokevirtual   java/io/File.getPath:()Ljava/lang/String;
        //   353: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   356: ldc_w           "/apps/"
        //   359: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   362: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   365: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //   368: invokestatic    com/dravite/newlayouttest/settings/backup_restore/BackupRestoreActivity.copyDirectoryOneLocationToAnotherLocation:(Ljava/io/File;Ljava/io/File;)V
        //   371: iload_2        
        //   372: ifeq            424
        //   375: aload_0        
        //   376: aload           6
        //   378: iconst_1       
        //   379: anewarray       Ljava/io/File;
        //   382: dup            
        //   383: iconst_0       
        //   384: new             Ljava/io/File;
        //   387: dup            
        //   388: new             Ljava/lang/StringBuilder;
        //   391: dup            
        //   392: invokespecial   java/lang/StringBuilder.<init>:()V
        //   395: aload           5
        //   397: invokevirtual   java/io/File.getPath:()Ljava/lang/String;
        //   400: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   403: ldc_w           "/quickApps"
        //   406: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   409: ldc             "/quickApps.json"
        //   411: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   414: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   417: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //   420: aastore        
        //   421: invokevirtual   com/dravite/newlayouttest/settings/backup_restore/BackupRestoreActivity.backupTo:(Ljava/io/File;[Ljava/io/File;)V
        //   424: iload           4
        //   426: ifeq            508
        //   429: aload_0        
        //   430: invokestatic    android/preference/PreferenceManager.getDefaultSharedPreferences:(Landroid/content/Context;)Landroid/content/SharedPreferences;
        //   433: ldc_w           "isLicensed"
        //   436: ldc_w           "isLicensed"
        //   439: invokestatic    com/dravite/newlayouttest/Const.Defaults.getBoolean:(Ljava/lang/String;)Z
        //   442: invokeinterface android/content/SharedPreferences.getBoolean:(Ljava/lang/String;Z)Z
        //   447: istore_1       
        //   448: aload_0        
        //   449: new             Ljava/io/File;
        //   452: dup            
        //   453: new             Ljava/lang/StringBuilder;
        //   456: dup            
        //   457: invokespecial   java/lang/StringBuilder.<init>:()V
        //   460: aload           5
        //   462: invokevirtual   java/io/File.getPath:()Ljava/lang/String;
        //   465: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   468: ldc             "/general/"
        //   470: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   473: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   476: ldc             "/com.vanbo.homeux.dravite.homeux_preferences.xml"
        //   478: invokespecial   java/io/File.<init>:(Ljava/lang/String;Ljava/lang/String;)V
        //   481: invokestatic    com/dravite/newlayouttest/settings/backup_restore/SharedPreferenceHelper.loadSharedPreferencesFromFile:(Landroid/content/Context;Ljava/io/File;)Z
        //   484: pop            
        //   485: aload_0        
        //   486: invokestatic    android/preference/PreferenceManager.getDefaultSharedPreferences:(Landroid/content/Context;)Landroid/content/SharedPreferences;
        //   489: invokeinterface android/content/SharedPreferences.edit:()Landroid/content/SharedPreferences.Editor;
        //   494: ldc_w           "isLicensed"
        //   497: iload_1        
        //   498: invokeinterface android/content/SharedPreferences.Editor.putBoolean:(Ljava/lang/String;Z)Landroid/content/SharedPreferences.Editor;
        //   503: invokeinterface android/content/SharedPreferences.Editor.apply:()V
        //   508: iload_3        
        //   509: ifeq            561
        //   512: aload_0        
        //   513: aload           6
        //   515: iconst_1       
        //   516: anewarray       Ljava/io/File;
        //   519: dup            
        //   520: iconst_0       
        //   521: new             Ljava/io/File;
        //   524: dup            
        //   525: new             Ljava/lang/StringBuilder;
        //   528: dup            
        //   529: invokespecial   java/lang/StringBuilder.<init>:()V
        //   532: aload           5
        //   534: invokevirtual   java/io/File.getPath:()Ljava/lang/String;
        //   537: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   540: ldc_w           "/hiddenApps"
        //   543: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   546: ldc             "/hiddenApps.json"
        //   548: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   551: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   554: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //   557: aastore        
        //   558: invokevirtual   com/dravite/newlayouttest/settings/backup_restore/BackupRestoreActivity.backupTo:(Ljava/io/File;[Ljava/io/File;)V
        //   561: return         
        //   562: astore          7
        //   564: aload           7
        //   566: invokevirtual   java/io/IOException.printStackTrace:()V
        //   569: goto            267
        //   572: astore          7
        //   574: aload           7
        //   576: invokevirtual   java/io/IOException.printStackTrace:()V
        //   579: goto            371
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  163    267    562    572    Ljava/io/IOException;
        //  267    371    572    582    Ljava/io/IOException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0267:
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
    
    void toggleDeleteMode(final boolean isInDeleteMode) {
        this.isInDeleteMode = isInDeleteMode;
        if (isInDeleteMode) {
            this.floatingActionButton.animate().scaleX(0.0f).scaleY(0.0f).setDuration(100L).setInterpolator((TimeInterpolator)new AccelerateInterpolator()).withEndAction((Runnable)new Runnable() {
                @Override
                public void run() {
                    BackupRestoreActivity.this.floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(769226));
                    BackupRestoreActivity.this.floatingActionButton.setImageDrawable(BackupRestoreActivity.this.getDrawable(R.drawable.ic_delete_black_24dp));
                    BackupRestoreActivity.this.floatingActionButton.animate().scaleY(1.0f).scaleX(1.0f).setDuration(100L).setInterpolator((TimeInterpolator)new DecelerateInterpolator());
                }
            });
            return;
        }
        this.floatingActionButton.animate().scaleX(0.0f).scaleY(0.0f).setDuration(100L).setInterpolator((TimeInterpolator)new AccelerateInterpolator()).withEndAction((Runnable)new Runnable() {
            @Override
            public void run() {
                BackupRestoreActivity.this.floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(11751600));
                BackupRestoreActivity.this.floatingActionButton.setImageDrawable(BackupRestoreActivity.this.getDrawable(R.drawable.ic_playlist_add_black_24dp));
                BackupRestoreActivity.this.floatingActionButton.animate().scaleY(1.0f).scaleX(1.0f).setDuration(100L).setInterpolator((TimeInterpolator)new DecelerateInterpolator());
            }
        });
    }
    
    public class BackupAdapter extends RecyclerView.Adapter<BackupAdapter.BackupHolder>
    {
        List<BackupObject> mAllBackups;
        Context mContext;
        List<BackupObject> mSelected;
        
        public BackupAdapter(final Context mContext, final List<BackupObject> mAllBackups) {
            this.mAllBackups = new ArrayList<BackupObject>();
            this.mSelected = new ArrayList<BackupObject>();
            this.mContext = mContext;
            this.mAllBackups = mAllBackups;
        }
        
        public String createDataString(final BackupObject backupObject) {
            return backupObject.backupSize;
        }
        
        @Override
        public int getItemCount() {
            return this.mAllBackups.size();
        }
        
        public void onBindViewHolder(final BackupHolder backupHolder, final int n) {
            backupHolder.titleView.setText((CharSequence)("Backup from " + this.mAllBackups.get(n).backupDate));
            backupHolder.dataView.setText((CharSequence)this.createDataString(this.mAllBackups.get(n)));
            backupHolder.clickView.setOnLongClickListener((View.OnLongClickListener)new View.OnLongClickListener() {
                public boolean onLongClick(final View view) {
                    if (!BackupRestoreActivity.this.isInDeleteMode) {
                        BackupAdapter.this.mSelected.clear();
                        BackupAdapter.this.mSelected.add(BackupAdapter.this.mAllBackups.get(n));
                        ((RecyclerView.Adapter)BackupAdapter.this).notifyItemChanged(n);
                        BackupRestoreActivity.this.toggleDeleteMode(true);
                    }
                    return true;
                }
            });
            if (this.mSelected.contains(this.mAllBackups.get(n)) && BackupRestoreActivity.this.isInDeleteMode) {
                backupHolder.clickView.setBackgroundTintList(ColorStateList.valueOf(769226));
            }
            else {
                backupHolder.clickView.setBackgroundTintList((ColorStateList)null);
                backupHolder.titleView.setTextColor(16777216);
                backupHolder.dataView.setTextColor(16777216);
            }
            backupHolder.clickView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    if (BackupRestoreActivity.this.isInDeleteMode) {
                        if (BackupAdapter.this.mSelected.contains(BackupAdapter.this.mAllBackups.get(n)) && BackupRestoreActivity.this.isInDeleteMode) {
                            BackupAdapter.this.mSelected.remove(BackupAdapter.this.mAllBackups.get(n));
                            if (BackupAdapter.this.mSelected.size() == 0) {
                                BackupRestoreActivity.this.toggleDeleteMode(false);
                            }
                        }
                        else {
                            BackupAdapter.this.mSelected.add(BackupAdapter.this.mAllBackups.get(n));
                        }
                        ((RecyclerView.Adapter)BackupAdapter.this).notifyItemChanged(n);
                        return;
                    }
                    final View inflate = LayoutInflater.from(BackupAdapter.this.mContext).inflate(R.layout.restore_view, (ViewGroup)null);
                    final CheckBox checkBox = (CheckBox)inflate.findViewById(R.id.folders);
                    final CheckBox checkBox2 = (CheckBox)inflate.findViewById(R.id.quickapps);
                    final CheckBox checkBox3 = (CheckBox)inflate.findViewById(R.id.hiddenapps);
                    final CheckBox checkBox4 = (CheckBox)inflate.findViewById(R.id.general);
                    final BackupObject backupObject = BackupAdapter.this.mAllBackups.get(n);
                    final File file = new File(Environment.getExternalStorageDirectory().getPath() + "/HomeUX/" + BackupAdapter.this.mAllBackups.get(n).backupName);
                    checkBox.setChecked(new File(file.getPath() + "/folders").exists());
                    checkBox2.setChecked(new File(file.getPath() + "/quickApps").exists());
                    checkBox4.setChecked(new File(file.getPath() + "/general").exists());
                    checkBox3.setChecked(new File(file.getPath() + "/hiddenApps").exists());
                    if (!checkBox.isChecked()) {
                        checkBox.setVisibility(View.GONE);
                    }
                    if (!checkBox2.isChecked()) {
                        checkBox2.setVisibility(View.GONE);
                    }
                    if (!checkBox4.isChecked()) {
                        checkBox4.setVisibility(View.GONE);
                    }
                    if (!checkBox3.isChecked()) {
                        checkBox3.setVisibility(View.GONE);
                    }
                    final CompoundButton.OnCheckedChangeListener compoundButtonOnCheckedChangeListener = (CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
                        final /* synthetic */ AlertDialog valdialog = new AlertDialog.Builder(BackupAdapter.this.mContext, R.style.DialogTheme).setTitle("Restore (" + BackupAdapter.this.mAllBackups.get(n).backupDate + ")").setView(inflate).setNegativeButton(R.string.app_name, null).setPositiveButton(R.string.dialog_restore, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                            final /* synthetic */ CheckBox valcbFolders = checkBox;
                            final /* synthetic */ CheckBox valcbGeneral = checkBox4;
                            final /* synthetic */ CheckBox valcbHidden = checkBox3;
                            final /* synthetic */ CheckBox valcbQA = checkBox2;
                            final /* synthetic */ BackupObject valobject = backupObject;
                            
                            public void onClick(final DialogInterface dialogInterface, final int n) {
                                BackupRestoreActivity.this.restore(this.valcbFolders.isChecked(), this.valcbQA.isChecked(), this.valcbHidden.isChecked(), this.valcbGeneral.isChecked(), this.valobject.backupName);
                            }
                        }).show();
                        
                        public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                            this.valdialog.getButton(-1).setEnabled(checkBox.isChecked() || checkBox4.isChecked() || checkBox3.isChecked() || checkBox2.isChecked());
                        }
                    };
                    checkBox.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)compoundButtonOnCheckedChangeListener);
                    checkBox2.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)compoundButtonOnCheckedChangeListener);
                    checkBox3.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)compoundButtonOnCheckedChangeListener);
                    checkBox4.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)compoundButtonOnCheckedChangeListener);
                }
            });
        }
        
        public BackupHolder onCreateViewHolder(final ViewGroup viewGroup, final int n) {
            return new BackupHolder(LayoutInflater.from(this.mContext).inflate(R.layout.backup_item, viewGroup, false));
        }
        
        public class BackupHolder extends RecyclerView.ViewHolder
        {
            View clickView;
            TextView dataView;
            TextView titleView;
            
            public BackupHolder(final View view) {
                super(view);
                this.clickView = view.findViewById(R.id.clickView);
                this.titleView = (TextView)view.findViewById(R.id.titleView);
                this.dataView = (TextView)view.findViewById(R.id.infoText);
            }
        }
    }
}
