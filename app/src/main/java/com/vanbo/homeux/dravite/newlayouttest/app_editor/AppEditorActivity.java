// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.app_editor;

import android.view.MenuItem;
import android.content.pm.LauncherActivityInfo;
import android.graphics.drawable.Drawable;
import android.graphics.PorterDuff.Mode;
import android.content.res.ColorStateList;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import android.os.Process;
import android.content.pm.LauncherApps;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.widget.ImageView;
import java.lang.ref.SoftReference;
import android.content.Intent;
import android.content.ComponentName;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import android.widget.RadioButton;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.DialogInterface;
//import android.content.DialogInterface.OnClickListener; //vanbo
import android.content.Context;
import android.app.AlertDialog.Builder;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.IconPackManager;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.Application;
import android.support.v7.app.AppCompatActivity;
import android.graphics.PorterDuff;
import com.dravite.homeux.R;


public class AppEditorActivity extends AppCompatActivity
{
    public static final int REQUEST_EDIT_APP = 4085;
    public static final int REQUEST_PASS_ICON = 4086;
    private static Application mCurrentApp;
    private String mAppLabel;
    IconPackManager.IconPack mCurrentIconPack;
    private Bitmap mCustomIcon;
    private Bitmap mDefaultIcon;
    private boolean mHasChanged;
    private Bitmap mThemedIcon;
    
    public AppEditorActivity() {
        this.mHasChanged = false;
    }
    
    void backPressed() {
        if (this.mHasChanged) {
            new AlertDialog.Builder((Context)this, 16974395).setTitle(R.string.dialog_exit_saving_title).setMessage(R.string.dialog_exit_saving_message).setPositiveButton(R.string.app_name, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialogInterface, final int n) {
                    AppEditorActivity.this.onBackPressed();
                }
            }).setNegativeButton(R.string.app_name, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialogInterface, final int n) {
                }
            }).show();
            return;
        }
        super.onBackPressed();
    }
    
    void initSaveButton() {
        ((Button)this.findViewById(R.id.save)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                final RadioButton radioButton = (RadioButton)AppEditorActivity.this.findViewById(R.id.checkDefault);
                final RadioButton radioButton2 = (RadioButton)AppEditorActivity.this.findViewById(R.id.checkOriginal);
                if (radioButton.isChecked()) {
                    AppEditorActivity.mCurrentApp.saveCustomIcon((Context)AppEditorActivity.this, null);
                }
                else if (radioButton2.isChecked()) {
                    AppEditorActivity.mCurrentApp.saveCustomIcon((Context)AppEditorActivity.this, AppEditorActivity.this.mDefaultIcon);
                }
                else {
                    AppEditorActivity.mCurrentApp.saveCustomIcon((Context)AppEditorActivity.this, AppEditorActivity.this.mCustomIcon);
                }
                LauncherActivity.mDrawerTree.changeLabel(AppEditorActivity.mCurrentApp.loadLabel((Context)AppEditorActivity.this), new ComponentName(AppEditorActivity.mCurrentApp.packageName, AppEditorActivity.mCurrentApp.className).toString(), AppEditorActivity.this.mAppLabel);
                if (AppEditorActivity.this.mAppLabel.equals(AppEditorActivity.mCurrentApp.label)) {
                    AppEditorActivity.mCurrentApp.saveLabel((Context)AppEditorActivity.this, null);
                }
                else {
                    AppEditorActivity.mCurrentApp.saveLabel((Context)AppEditorActivity.this, AppEditorActivity.this.mAppLabel);
                }
                AppEditorActivity.this.setResult(-1, new Intent());
                PassApp.softApp = new SoftReference<Application>(AppEditorActivity.mCurrentApp);
                AppEditorActivity.mCurrentApp = null;
                AppEditorActivity.this.finish();
            }
        });
    }
    
    @Override
    protected void onActivityResult(final int n, final int n2, final Intent intent) {
        if (n2 == -1 && n == 4086) {
            this.mCustomIcon = (Bitmap)intent.getParcelableExtra("icon");
            ((ImageView)this.findViewById(R.id.circleCustom)).setImageBitmap(this.mCustomIcon);
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
        this.setContentView(R.layout.activity_app_editor_new);
        final Toolbar supportActionBar = (Toolbar)this.findViewById(R.id.toolbar);
        this.setSupportActionBar(supportActionBar);
        supportActionBar.setBackgroundColor(ContextCompat.getColor((Context)this, R.color.colorPrimary));
        this.getWindow().setStatusBarColor(ContextCompat.getColor((Context)this, R.color.colorPrimaryDark));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable drawable = this.getDrawable(R.drawable.ic_clear_black_24dp);
        drawable.setTint(-1);
        this.getSupportActionBar().setHomeAsUpIndicator(drawable);
        this.initSaveButton();
        AppEditorActivity.mCurrentApp = PassApp.softApp.get();
        this.mCurrentIconPack = PassApp.iconPack.get();
        if (AppEditorActivity.mCurrentApp == null) {
            new AlertDialog.Builder((Context)this, R.style.DialogTheme).setCancelable(false).setPositiveButton(R.string.app_name, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialogInterface, final int n) {
                    AppEditorActivity.this.finish();
                }
            }).setTitle(R.string.error).setMessage(R.string.app_error_message).show();
        }
        final LauncherApps launcherApps = (LauncherApps)this.getSystemService(Context.LAUNCHER_APPS_SERVICE);
        final Intent intent = new Intent();
        intent.setComponent(new ComponentName(AppEditorActivity.mCurrentApp.packageName, AppEditorActivity.mCurrentApp.className));
        final LauncherActivityInfo resolveActivity = launcherApps.resolveActivity(intent, Process.myUserHandle());
        if (resolveActivity == null) {
            this.finish();
        }
        this.mDefaultIcon = LauncherUtils.drawableToBitmap(resolveActivity.getIcon(0));
        this.mThemedIcon = AppEditorActivity.mCurrentApp.loadThemedIcon(this.mCurrentIconPack, resolveActivity);
        this.mCustomIcon = AppEditorActivity.mCurrentApp.loadCustomIcon((Context)this);
        this.mAppLabel = AppEditorActivity.mCurrentApp.loadLabel((Context)this);
        final EditText editText = (EditText)this.findViewById(R.id.label);
        editText.setEnabled(true);
        editText.setText((CharSequence)this.mAppLabel.replace("\n", ""));
        editText.addTextChangedListener((TextWatcher)new TextWatcher() {
            public void afterTextChanged(final Editable editable) {
            }
            
            public void beforeTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
            }
            
            public void onTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
                AppEditorActivity.this.mAppLabel = charSequence.toString();
                AppEditorActivity.this.mHasChanged = true;
            }
        });
        ((ImageButton)this.findViewById(R.id.reset_label)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                editText.setText((CharSequence)AppEditorActivity.mCurrentApp.label);
                AppEditorActivity.this.mHasChanged = true;
            }
        });
        final Button button = (Button)this.findViewById(R.id.btn_select_icon);
        button.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                AppEditorActivity.this.startActivityForResult(new Intent((Context)AppEditorActivity.this, (Class)AppEditorIconPackActivity.class), 4086);
            }
        });
        final RadioButton radioButton = (RadioButton)this.findViewById(R.id.checkDefault);
        final RadioButton radioButton2 = (RadioButton)this.findViewById(R.id.checkOriginal);
        final RadioButton radioButton3 = (RadioButton)this.findViewById(R.id.checkCustom);
        boolean b;
        if (this.mCustomIcon != null && this.mDefaultIcon.sameAs(this.mCustomIcon)) {
            b = true;
        }
        else {
            b = false;
        }
        int n;
        if (!b && this.mCustomIcon != null) {
            n = 1;
        }
        else {
            n = 0;
        }
        if (this.mCustomIcon == null) {
            this.mCustomIcon = this.mThemedIcon;
        }
        final View viewById = this.findViewById(R.id.defaultSelector);
        final View viewById2 = this.findViewById(R.id.customSelector);
        final View viewById3 = this.findViewById(R.id.originalSelector);
        viewById.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                radioButton.setChecked(true);
            }
        });
        viewById2.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                radioButton3.setChecked(true);
            }
        });
        viewById3.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                radioButton2.setChecked(true);
            }
        });
        final ImageView imageView = (ImageView)this.findViewById(R.id.circleDefault);
        final ImageView imageView2 = (ImageView)this.findViewById(R.id.circleOriginal);
        final ImageView imageView3 = (ImageView)this.findViewById(R.id.circleCustom);
        imageView.setImageBitmap(AppEditorActivity.mCurrentApp.loadThemedIcon(this.mCurrentIconPack, resolveActivity));
        imageView2.setImageBitmap(this.mDefaultIcon);
        imageView3.setImageBitmap(this.mCustomIcon);
        final CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                AppEditorActivity.this.mHasChanged = true;
                ((InputMethodManager)AppEditorActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editText.getWindowToken(), 0);
                switch (compoundButton.getId()) {
                    case R.id.checkDefault: {
                        if (b) {
                            radioButton2.setChecked(false);
                            radioButton3.setChecked(false);
                            radioButton.setChecked(true);
                            imageView.setImageTintList((ColorStateList)null);
                            imageView2.setImageTintList(ColorStateList.valueOf(1140850689));
                            imageView2.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
                            imageView3.setImageTintList(ColorStateList.valueOf(1140850689));
                            imageView3.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
                            button.setEnabled(false);
                            button.animate().scaleX(0.0f).scaleY(0.0f).setDuration(150L);
                            return;
                        }
                        break;
                    }
                    case R.id.checkOriginal: {
                        if (b) {
                            radioButton.setChecked(false);
                            radioButton3.setChecked(false);
                            radioButton2.setChecked(true);
                            imageView2.setImageTintList((ColorStateList)null);
                            imageView.setImageTintList(ColorStateList.valueOf(1140850689));
                            imageView.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
                            imageView3.setImageTintList(ColorStateList.valueOf(1140850689));
                            imageView3.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
                            button.setEnabled(false);
                            button.animate().scaleX(0.0f).scaleY(0.0f).setDuration(150L);
                            return;
                        }
                        break;
                    }
                    case R.id.checkCustom: {
                        if (b) {
                            radioButton2.setChecked(false);
                            radioButton.setChecked(false);
                            radioButton3.setChecked(true);
                            imageView3.setImageTintList((ColorStateList)null);
                            imageView2.setImageTintList(ColorStateList.valueOf(1140850689));
                            imageView2.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
                            imageView.setImageTintList(ColorStateList.valueOf(1140850689));
                            imageView.setImageTintMode(PorterDuff.Mode.SRC_ATOP);
                            button.setEnabled(true);
                            button.animate().scaleX(1.0f).scaleY(1.0f).setDuration(150L);
                            return;
                        }
                        break;
                    }
                }
            }
        };
        radioButton.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)onCheckedChangeListener);
        radioButton2.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)onCheckedChangeListener);
        radioButton3.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)onCheckedChangeListener);
        if (n != 0) {
            radioButton3.setChecked(true);
            imageView3.setImageTintList((ColorStateList)null);
            return;
        }
        if (b) {
            radioButton2.setChecked(true);
            imageView2.setImageTintList((ColorStateList)null);
            return;
        }
        radioButton.setChecked(true);
        imageView.setImageTintList((ColorStateList)null);
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
    
    @Override
    protected void onStop() {
        PassApp.iconPack.clear();
        super.onStop();
    }
    
    public static class PassApp
    {
        public static SoftReference<IconPackManager.IconPack> iconPack;
        public static SoftReference<Application> softApp;
    }
}
