// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.folder_editor;

import java.util.Collection;
import android.view.MenuItem;
import android.support.v7.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import android.widget.Switch;
import android.support.v7.widget.GridLayoutManager;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import com.vanbo.homeux.dravite.newlayouttest.general_dialogs.ColorDialog;
import android.graphics.Bitmap.Config;
import com.vanbo.homeux.dravite.newlayouttest.general_dialogs.helpers.ColorWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.content.res.ColorStateList;
import android.widget.ImageView;
import android.content.SharedPreferences.Editor;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.FileManager;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import com.vanbo.homeux.dravite.newlayouttest.Const;
import android.preference.PreferenceManager;
import java.io.Serializable;
import java.util.List;
import java.util.Collections;
import android.content.pm.PackageManager.NameNotFoundException;
import java.util.Comparator;
import android.content.Intent;
import java.lang.ref.WeakReference;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import java.io.FileNotFoundException;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.content.res.Resources;
import android.content.DialogInterface;
//import android.content.DialogInterface.OnClickListener;
import android.content.Context;
import android.app.AlertDialog.Builder;
import android.app.AlertDialog;
import android.graphics.BitmapFactory.Options;
import android.graphics.Bitmap;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.FolderStructure;
import android.content.ComponentName;
import java.util.ArrayList;
import android.support.v7.app.AppCompatActivity;
import android.content.pm.PackageManager;
import android.content.SharedPreferences;
import com.dravite.homeux.R;


public class FolderEditorActivity extends AppCompatActivity
{
    public static final int REQUEST_ADD_FOLDER = 4025;
    private static final int REQUEST_CROP_IMAGE = 928;
    public static final int REQUEST_EDIT_FOLDER = 4026;
    private static final int REQUEST_GET_ICON = 929;
    private int folderIndex;
    boolean hasImageChanged;
    private int mCurrentAccent;
    private ArrayList<ComponentName> mCurrentAppList;
    private ArrayList<ComponentName> mCurrentContainsList;
    private FolderStructure.Folder mCurrentFolder;
    private String mCurrentFolderName;
    private String mCurrentIconRes;
    private Bitmap mCurrentPanelImage;
    private FolderStructure mFolderStructure;
    private boolean mHasChanged;
    private String mOldFolderName;
    private int requestCode;
    
    public FolderEditorActivity() {
        this.mCurrentAccent = -1;
        this.mCurrentIconRes = "";
        this.requestCode = 4026;
        this.folderIndex = 0;
        this.mHasChanged = false;
        this.hasImageChanged = false;
        this.mCurrentAppList = new ArrayList<ComponentName>();
        this.mCurrentContainsList = new ArrayList<ComponentName>();
        this.mCurrentFolderName = "";
        this.mOldFolderName = "";
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
    
    void backPressed() {
        if (this.mHasChanged) {
            new AlertDialog.Builder((Context)this, 16974395).setTitle(R.string.dialog_exit_saving_title).setMessage(R.string.dialog_exit_saving_message).setPositiveButton(R.string.app_name, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialogInterface, final int n) {
                    FolderEditorActivity.this.onBackPressed();
                }
            }).setNegativeButton(R.string.app_name, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialogInterface, final int n) {
                }
            }).show();
            return;
        }
        super.onBackPressed();
    }
    
    public Bitmap decodeSampledBitmapFromResource(final Resources resources, final Uri uri, final int n, final int n2) throws FileNotFoundException {
        final BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
        bitmapFactoryOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri), new Rect(0, 0, 0, 0), bitmapFactoryOptions);
        bitmapFactoryOptions.inSampleSize = calculateInSampleSize(bitmapFactoryOptions, n, n2);
        bitmapFactoryOptions.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri), new Rect(0, 0, 0, 0), bitmapFactoryOptions);
    }
    
    void initSaveButton() {
        (this.findViewById(R.id.save)).setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {

                //check if name is empty
                String folderName = ((EditText)FolderEditorActivity.this.findViewById(R.id.folderName)).getText().toString();
                if (folderName.equals("")) {
                    ((TextInputLayout)FolderEditorActivity.this.findViewById(R.id.nameInput)).setError(FolderEditorActivity.this.getString(R.string.folder_no_name_error));
                    return;
                }

                //check if name exist
                if (isFolderNameExisting()) {
                    ((TextInputLayout)FolderEditorActivity.this.findViewById(R.id.nameInput)).setError(FolderEditorActivity.this.getString(R.string.folder_name_already_taken_error));
                    return;
                }

                if (requestCode == REQUEST_ADD_FOLDER) {
                    mCurrentFolder.headerImage = mCurrentPanelImage;
                    mCurrentFolder.accentColor = mCurrentAccent;
                    mCurrentFolder.folderName = mCurrentFolderName;
                    mCurrentFolder.folderIconRes = mCurrentIconRes;

                    FolderPasser.passFolder = new WeakReference<FolderStructure.Folder>(mCurrentFolder);
                    final Intent intent = new Intent();
                    Collections.sort(mCurrentAppList, new Comparator<ComponentName>() {
                        PackageManager pm = getPackageManager();
                        @Override
                        public int compare(final ComponentName componentName, final ComponentName componentName2) {
                            try {
                                String appLabel = pm.getActivityInfo(componentName, 0).loadLabel(pm).toString().toLowerCase();
                                String appLabel2 = pm.getActivityInfo(componentName2, 0).loadLabel(pm).toString().toLowerCase();
                                return appLabel.compareTo(appLabel2);
                            }
                            catch (PackageManager.NameNotFoundException ex) {
                                return componentName.compareTo(componentName2);
                            }
                        }
                    });

                    intent.putExtra("appList", mCurrentAppList);
                    setResult(-1, intent);
                    finish();
                    return;
                }

                if (mOldFolderName.equals(PreferenceManager.getDefaultSharedPreferences(FolderEditorActivity.this).getString("defaultFolder", Const.Defaults.getString("defaultFolder")))) {
                    final SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(FolderEditorActivity.this).edit();
                    edit.putString("defaultFolder", mCurrentFolderName);
                    edit.apply();
                }
                new AsyncTask<Void, Void, Void>() {
                    ProgressDialog dialog = new ProgressDialog(FolderEditorActivity.this, R.style.DialogTheme);
                    
                    protected Void doInBackground(final Void... array) {
                        if (hasImageChanged) {
                            if (!mCurrentPanelImage.isRecycled()) {
                                FileManager.saveBitmapToData(FolderEditorActivity.this, mCurrentPanelImage, mCurrentFolderName, true);
                            }
                            hasImageChanged = false;
                        }
                        return null;
                    }
                    
                    protected void onPostExecute(final Void void1) {
                        this.dialog.cancel();
                        final Intent intent = new Intent();
                        intent.putExtra("iconRes", mCurrentIconRes);
                        intent.putExtra("folderName", mCurrentFolderName);
                        intent.putExtra("accent", mCurrentAccent);
                        intent.putExtra("folderIndex", folderIndex);
                        Collections.sort(mCurrentAppList, new Comparator<ComponentName>() {
                            PackageManager pm = getPackageManager();
                            @Override
                            public int compare(final ComponentName componentName, final ComponentName componentName2) {
                                try {
                                    String appLabel = pm.getActivityInfo(componentName, 0).loadLabel(pm).toString().toLowerCase();
                                    String appLabel2 = pm.getActivityInfo(componentName2, 0).loadLabel(pm).toString().toLowerCase();
                                    return appLabel.compareTo(appLabel2);
                                }
                                catch (PackageManager.NameNotFoundException ex) {
                                    return componentName.compareTo(componentName2);
                                }
                            }
                        });
                        intent.putExtra("appList", mCurrentAppList);
                        setResult(-1, intent);
                        finish();
                    }
                    
                    protected void onPreExecute() {
                        this.dialog.setTitle("Saving");
                        this.dialog.setMessage("Please wait while the settings are being saved...");
                        this.dialog.show();
                    }
                }.execute(new Void[0]);
            }
        });
    }
    
    boolean isFolderNameExisting() {
        if (this.requestCode == 4025) {
            return this.mFolderStructure.getFolderWithName(((EditText)this.findViewById(R.id.folderName)).getText().toString()) != null;
        }
        return this.mFolderStructure.getFolderWithName(((EditText)this.findViewById(R.id.folderName)).getText().toString()) != null && !this.mCurrentFolder.folderName.equals(((EditText)this.findViewById(R.id.folderName)).getText().toString());
    }
    
    @Override
    protected void onActivityResult(final int n, final int n2, final Intent intent) {
        if (n2 == -1) {
            switch (n) {
                case 928: {
                    if (n2 == -1) {
                        this.hasImageChanged = true;
                        try {
                            final Uri data = intent.getData();
                            ((ImageView)this.findViewById(R.id.circlePrimary)).setImageBitmap((Bitmap)null);
                            this.mCurrentPanelImage = this.decodeSampledBitmapFromResource(this.getResources(), data, this.findViewById(android.R.id.content).getMeasuredWidth(), this.findViewById(android.R.id.content).getMeasuredWidth());
                            ((ImageView)this.findViewById(R.id.circlePrimary)).setImageBitmap(this.mCurrentPanelImage);
                        }
                        catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    }
                    break;
                }
                case 929: {
                    if (n2 == -1) {
                        this.mCurrentIconRes = intent.getStringExtra("iconRes");
                        final ImageView imageView = (ImageView)this.findViewById(R.id.circleIcon);
                        imageView.setImageResource(this.getResources().getIdentifier(this.mCurrentIconRes, "drawable", this.getPackageName()));
                        imageView.setImageTintList(ColorStateList.valueOf(16777216));
                        imageView.setAlpha(0.57f);
                        break;
                    }
                    break;
                }
            }
        }
        super.onActivityResult(n, n2, intent);
    }
    
    @Override
    public void onBackPressed() {
        this.backPressed();
    }
    
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_folder_editor_new);
        this.mHasChanged = false;
        this.setSupportActionBar((Toolbar)this.findViewById(R.id.toolbar));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable drawable = this.getDrawable(R.drawable.ic_clear_black_24dp);
        drawable.setTint(-1);
        this.getSupportActionBar().setHomeAsUpIndicator(drawable);
        this.initSaveButton();
        this.getWindow().setStatusBarColor(15108398);
        this.mCurrentContainsList = AppListPasser.receiveContainedList();
        ((TextView)this.findViewById(R.id.chooseApps)).setText((CharSequence)("Contains " + this.mCurrentContainsList.size() + " apps."));
        this.requestCode = this.getIntent().getIntExtra("requestCode", 4026);
        this.folderIndex = this.getIntent().getIntExtra("folderIndex", 0);
        if (FolderPasser.passFolder == null || FolderPasser.passFolder.get() == null) {
            this.mCurrentFolder = new FolderStructure.Folder();
            this.mCurrentFolder.headerImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.welcome_header_small);
            this.mCurrentFolder.isAllFolder = false;
            this.mCurrentFolder.accentColor = -13615201;
            this.mCurrentFolder.folderName = "";
            this.mCurrentFolder.folderIconRes = "ic_folder";
            this.mCurrentFolder.pages.add(new FolderStructure.Page());
            this.mCurrentFolder.mFolderType = 0;
        }
        else {
            this.mCurrentFolder = FolderPasser.passFolder.get();
        }
        if (this.mCurrentFolder.folderName == null) {
            this.mCurrentFolder.folderName = "";
        }
        if (FolderPasser.passFolder != null) {
            FolderPasser.passFolder.clear();
        }
        this.mCurrentFolder.loadImage((Context)this);
        this.mCurrentPanelImage = this.mCurrentFolder.headerImage;
        if (this.mCurrentPanelImage == null) {
            this.mCurrentPanelImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.welcome_header_small);
        }
        ((ImageView)this.findViewById(R.id.circlePrimary)).setImageBitmap(this.mCurrentPanelImage);
        this.mCurrentAccent = this.mCurrentFolder.accentColor;
        this.mCurrentIconRes = this.mCurrentFolder.folderIconRes;
        String mCurrentIconRes;
        if (this.mCurrentIconRes == null) {
            mCurrentIconRes = "ic_folder";
        }
        else {
            mCurrentIconRes = this.mCurrentIconRes;
        }
        this.mCurrentIconRes = mCurrentIconRes;
        this.mCurrentFolderName = this.mCurrentFolder.folderName;
        this.mOldFolderName = this.mCurrentFolder.folderName;
        final boolean isAllFolder = this.mCurrentFolder.isAllFolder;
        if (this.mCurrentFolder.folderName.equals("All")) {
            this.findViewById(R.id.folderName).setEnabled(false);
        }
        else {
            this.findViewById(R.id.folderName).setEnabled(true);
        }
        this.mFolderStructure = LauncherActivity.mFolderStructure;
        final ActionBar supportActionBar = this.getSupportActionBar();
        String title;
        if (this.mCurrentFolderName.equals("")) {
            title = this.getString(R.string.activity_folder_editor_new);
        }
        else {
            title = this.getString(R.string.activity_folder_editor_edit);
        }
        supportActionBar.setTitle(title);
        this.setColor(this.mCurrentAccent);
        final EditText editText = (EditText)this.findViewById(R.id.folderName);
        editText.setText((CharSequence)this.mCurrentFolderName);
        editText.addTextChangedListener((TextWatcher)new TextWatcher() {
            public void afterTextChanged(final Editable editable) {
            }
            
            public void beforeTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
            }
            
            public void onTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
                FolderEditorActivity.this.mCurrentFolderName = charSequence.toString();
                editText.setBackgroundColor(0);
                FolderEditorActivity.this.mHasChanged = true;
            }
        });
        editText.setEnabled(!isAllFolder);
        final ImageView imageView = (ImageView)this.findViewById(R.id.circleIcon);
        imageView.setImageDrawable(this.getDrawable(this.getResources().getIdentifier(this.mCurrentIconRes, "drawable", this.getPackageName())));
        imageView.setImageTintList(ColorStateList.valueOf(16777216));
        this.findViewById(R.id.circleIcon).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                FolderEditorActivity.this.startActivityForResult(new Intent((Context)FolderEditorActivity.this, (Class)SelectFolderIconActivity.class), 929);
            }
        });
        this.findViewById(R.id.primarySelector).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                FolderEditorActivity.this.mHasChanged = true;
                new AlertDialog.Builder((Context)FolderEditorActivity.this, R.style.DialogTheme).setTitle((CharSequence)"\u9009\u62e9\u7c7b\u578b").setItems(new CharSequence[] { "\u989c\u8272", "\u56fe\u7247" }, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialogInterface, final int n) {
                        switch (n) {
                            default: {}
                            case 0: {
                                new ColorDialog((Context)FolderEditorActivity.this, FolderEditorActivity.this.getString(R.string.dialog_primary_color), -769226, new ColorWatcher() {
                                    @Override
                                    public void onColorSubmitted(final int n) {
                                        FolderEditorActivity.this.hasImageChanged = true;
                                        FolderEditorActivity.this.mCurrentPanelImage = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
                                        FolderEditorActivity.this.mCurrentPanelImage.eraseColor(n);
                                        ((ImageView)FolderEditorActivity.this.findViewById(R.id.circlePrimary)).setImageBitmap(FolderEditorActivity.this.mCurrentPanelImage);
                                    }
                                }).show();
                            }
                            case 1: {
                                final Intent intent = new Intent("android.intent.action.PICK");
                                intent.setType("image/*");
                                intent.putExtra("return-data", true);
                                intent.setAction("android.intent.action.GET_CONTENT");
                                FolderEditorActivity.this.startActivityForResult(intent, 928);
                            }
                        }
                    }
                }).show();
            }
        });
        this.findViewById(R.id.accentSelector).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                FolderEditorActivity.this.mHasChanged = true;
                new ColorDialog((Context)FolderEditorActivity.this, FolderEditorActivity.this.getString(R.string.dialog_accent_color), FolderEditorActivity.this.mCurrentAccent, new ColorWatcher() {
                    @Override
                    public void onColorSubmitted(final int color) {
                        FolderEditorActivity.this.setColor(color);
                        FolderEditorActivity.this.mCurrentAccent = color;
                    }
                }).show();
            }
        });
        final RecyclerView recyclerView = (RecyclerView)this.findViewById(R.id.appList);
        if (isAllFolder) {
            ((ViewGroup)recyclerView.getParent()).setVisibility(View.GONE);
        }
        else {
            ((ViewGroup)recyclerView.getParent()).setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager((Context)this, 4));
            recyclerView.setAdapter((RecyclerView.Adapter)new FolderAddAppsListAdapter((Context)this, this.mCurrentAppList, this.mCurrentContainsList));
        }
        final Switch switch1 = (Switch)this.findViewById(R.id.appSwitch);
        recyclerView.addOnScrollListener((RecyclerView.OnScrollListener)new RecyclerView.OnScrollListener() {
            boolean downAnimRunning;
            boolean upAnimRunning;
            
            @Override
            public void onScrolled(final RecyclerView recyclerView, final int n, final int n2) {
                if (n2 > LauncherUtils.dpToPx(2.0f, (Context)FolderEditorActivity.this) && !this.upAnimRunning) {
                    this.upAnimRunning = true;
                    this.downAnimRunning = false;
                    switch1.animate().translationY((float)LauncherUtils.dpToPx(48.0f, (Context)FolderEditorActivity.this));
                }
                else if (n2 < LauncherUtils.dpToPx(-2.0f, (Context)FolderEditorActivity.this) && !this.downAnimRunning) {
                    this.upAnimRunning = false;
                    this.downAnimRunning = true;
                    switch1.animate().translationY(0.0f);
                }
            }
        });
        switch1.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                ((FolderAddAppsListAdapter)recyclerView.getAdapter()).setShowInOthers(!b);
            }
        });
    }
    
    public boolean onOptionsItemSelected(final MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                return super.onOptionsItemSelected(menuItem);
            }
            case 16908332: {
                this.backPressed();
                return true;
            }
        }
    }
    
    void setColor(final int n) {
        ((ImageView)this.findViewById(R.id.circleAccent)).setImageTintList(ColorStateList.valueOf(n));
    }
    
    public static class AppListPasser
    {
        static ArrayList<ComponentName> alreadyContainedList;
        static ArrayList<ComponentName> appList;
        
        static {
            AppListPasser.appList = new ArrayList<ComponentName>();
            AppListPasser.alreadyContainedList = new ArrayList<ComponentName>();
        }
        
        public static void passAlreadyContainedList(final ArrayList<ComponentName> alreadyContainedList) {
            AppListPasser.alreadyContainedList = alreadyContainedList;
        }
        
        public static void passAppList(final ArrayList<ComponentName> appList) {
            AppListPasser.appList = appList;
        }
        
        public static ArrayList<ComponentName> receiveAppList() {
            final ArrayList<ComponentName> list = new ArrayList<ComponentName>(AppListPasser.appList);
            AppListPasser.appList.clear();
            return list;
        }
        
        public static ArrayList<ComponentName> receiveContainedList() {
            final ArrayList<ComponentName> list = new ArrayList<ComponentName>(AppListPasser.alreadyContainedList);
            AppListPasser.alreadyContainedList.clear();
            return list;
        }
    }
    
    public static class FolderPasser
    {
        public static WeakReference<FolderStructure.Folder> passFolder;
    }
}
