// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.settings;

import android.content.res.Resources;
import android.os.Bundle;
import java.lang.ref.SoftReference;
import com.vanbo.homeux.dravite.newlayouttest.settings.settings_fragments.SettingsFragmentAdapter;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import com.vanbo.homeux.dravite.newlayouttest.welcome.WelcomeActivity;
import com.vanbo.homeux.dravite.newlayouttest.settings.backup_restore.BackupRestoreActivity;
import com.vanbo.homeux.dravite.newlayouttest.settings.hidden_apps.HiddenAppsActivity;
import com.vanbo.homeux.dravite.newlayouttest.folder_editor.SelectFolderIconActivity;
import com.vanbo.homeux.dravite.newlayouttest.settings.items.CaptionSettingsItem;
import com.vanbo.homeux.dravite.newlayouttest.settings.items.SwitchSettingsItem;
import com.vanbo.homeux.dravite.newlayouttest.settings.clock.ActivityClockSettings;
import java.util.List;

import android.widget.NumberPicker;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SeekBar;
import android.content.SharedPreferences.Editor;
import android.content.res.ColorStateList;
import com.vanbo.homeux.dravite.newlayouttest.general_dialogs.IconTextListDialog;
import android.content.pm.LauncherActivityInfo;
import com.vanbo.homeux.dravite.newlayouttest.general_adapters.AppSelectAdapter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ColorDrawable;
import android.app.AlertDialog;
import com.vanbo.homeux.dravite.newlayouttest.Const;
import android.app.Dialog;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.content.DialogInterface.OnDismissListener;
import android.widget.ListAdapter;
import com.vanbo.homeux.dravite.newlayouttest.settings.items.GenericSettingsItem;
import com.vanbo.homeux.dravite.newlayouttest.general_adapters.FontAdapter;
import android.content.Intent;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.FileManager;
import java.io.File;
import android.preference.PreferenceManager;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import android.content.DialogInterface;
//import android.content.DialogInterface.OnClickListener;
import android.content.Context;
import android.app.AlertDialog.Builder;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.content.SharedPreferences;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.FolderStructure;
import java.lang.ref.Reference;
import com.vanbo.homeux.dravite.newlayouttest.settings.items.BaseItem;
import android.content.pm.PackageManager;
import com.dravite.homeux.R;

public class SettingsActivity extends SettingsBaseActivity
{
    public static final int REQUEST_BACKUP_RESTORE = 340;
    public static final int REQUEST_PICK_QA_ICON = 840;
    public static final int REQUEST_SETTINGS = 425;
    BaseItem.ItemViewHolder.OnItemClickListener mClockFontListener;
    BaseItem.ItemViewHolder.OnItemClickListener mClockSizeListener;
    BaseItem.ItemViewHolder.OnItemClickListener mDefaultHomeListener;
    public Reference<FolderStructure> mFolderStructure;
    BaseItem.ItemViewHolder.OnItemClickListener mGridSizeListener;
    BaseItem.ItemViewHolder.OnItemClickListener mIconSizeListener;
    BaseItem.ItemViewHolder.OnItemClickListener mOpacityListener;
    BaseItem.ItemViewHolder.OnItemClickListener mPageTransitionListener;
    BaseItem.ItemViewHolder.OnItemClickListener mQuickAppListener;
    BaseItem.ItemViewHolder.OnItemClickListener mResetClickListener;
    private SharedPreferences preferences;
    
    public SettingsActivity() {
        this.mResetClickListener = new BaseItem.ItemViewHolder.OnItemClickListener() {
            @Override
            public void onClick(final View view, final BaseItem baseItem, final RecyclerView.Adapter adapter, final int n) {
                new AlertDialog.Builder(SettingsActivity.this).setTitle(SettingsActivity.this.getString(R.string.reset_desc)).setMessage((CharSequence)SettingsActivity.this.getString(R.string.reset_msg)).setNegativeButton(R.string.app_name, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialogInterface, final int n) {
                        dialogInterface.dismiss();
                    }
                }).setPositiveButton(R.string.app_name, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialogInterface, final int n) {
                        LauncherActivity.updateAfterSettings = true;
                        final boolean boolean1 = PreferenceManager.getDefaultSharedPreferences((Context)SettingsActivity.this).getBoolean("isLicensed", false);
                        FileManager.deleteRecursive(new File(SettingsActivity.this.getApplicationInfo().dataDir + "/cache/apps/"));
                        FileManager.deleteRecursive(new File(SettingsActivity.this.getApplicationInfo().dataDir + "/cache/Shortcuts/"));
                        FileManager.deleteRecursive(new File(SettingsActivity.this.getApplicationInfo().dataDir + "/folderImg/"));
                        FileManager.deleteRecursive(new File(SettingsActivity.this.getApplicationInfo().dataDir + "/hiddenApps.json"));
                        FileManager.deleteRecursive(new File(SettingsActivity.this.getApplicationInfo().dataDir + "/quickApps.json"));
                        FileManager.deleteRecursive(new File(SettingsActivity.this.getApplicationInfo().dataDir + "/somedata.json"));
                        PreferenceManager.getDefaultSharedPreferences((Context)SettingsActivity.this).edit().clear().putBoolean("isLicensed", boolean1).apply();
                        PreferenceManager.getDefaultSharedPreferences((Context)SettingsActivity.this).edit().clear().putBoolean("firstStart", false).apply();
                        SettingsActivity.this.startActivity(new Intent((Context)SettingsActivity.this, (Class)LauncherActivity.class));
                        SettingsActivity.this.finish();
                    }
                }).show();
            }
        };
        this.mClockFontListener = new BaseItem.ItemViewHolder.OnItemClickListener() {
            @Override
            public void onClick(final View view, final BaseItem baseItem, final RecyclerView.Adapter adapter, final int n) {
                final FontAdapter fontAdapter = new FontAdapter(SettingsActivity.this.getBaseContext());
                new AlertDialog.Builder((Context)SettingsActivity.this, R.style.DialogTheme).setTitle(R.string.clock_font).setAdapter((ListAdapter)fontAdapter, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialogInterface, final int n) {
                        final String description = (String)fontAdapter.getItem(n);
                        SettingsActivity.this.preferences.edit().putString("clock_font", description).apply();
                        ((GenericSettingsItem)baseItem).setDescription(description);
                        adapter.notifyItemChanged(n);
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton(R.string.app_name, (DialogInterface.OnClickListener)null).show();
            }
        };
        this.mClockSizeListener = new BaseItem.ItemViewHolder.OnItemClickListener() {
            @Override
            public void onClick(final View view, final BaseItem baseItem, final RecyclerView.Adapter adapter, final int n) {
                new AlertDialog.Builder((Context)SettingsActivity.this, R.style.DialogTheme).setTitle(R.string.clock_size).setItems(R.array.sizes, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialogInterface, final int n) {
                        final String description = SettingsActivity.this.getResources().getStringArray(R.array.sizes)[n];
                        SettingsActivity.this.preferences.edit().putString("clock_size", description).putInt("clock_sizeINT", n).apply();
                        ((GenericSettingsItem)baseItem).setDescription(description);
                        adapter.notifyItemChanged(n);
                    }
                }).setNegativeButton(R.string.app_name, (DialogInterface.OnClickListener)null).show();
            }
        };
        this.mPageTransitionListener = new BaseItem.ItemViewHolder.OnItemClickListener() {
            @Override
            public void onClick(final View view, final BaseItem baseItem, final RecyclerView.Adapter adapter, final int n) {
                new AlertDialog.Builder((Context)SettingsActivity.this, R.style.DialogTheme).setTitle(R.string.app_page_transition).setItems(R.array.page_transformer_names, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialogInterface, final int n) {
                        final String description = SettingsActivity.this.getResources().getStringArray(R.array.page_transformer_names)[n];
                        SettingsActivity.this.preferences.edit().putString("transformer", description).putInt("transformerINT", n).apply();
                        ((GenericSettingsItem)baseItem).setDescription(description);
                        adapter.notifyItemChanged(n);
                    }
                }).setNegativeButton(R.string.app_name, (DialogInterface.OnClickListener)null).show();
            }
        };

        mIconSizeListener = new BaseItem.ItemViewHolder.OnItemClickListener() {
            boolean hasClicked = false;
            
            @Override
            public void onClick(View viewById, final BaseItem baseItem, final RecyclerView.Adapter adapter, final int n) {
                final Dialog show = new AlertDialog.Builder(SettingsActivity.this, R.style.DialogTheme)
                        .setTitle(R.string.app_icon_size)
                        .setView(R.layout.app_icon_size_dialog)
                        .setNegativeButton(Resources.getSystem().getIdentifier("yes", "string", "android"), new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialogInterface, final int n) {
                                hasClicked = false;
                            }
                        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
                            public void onDismiss(final DialogInterface dialogInterface) {
                                hasClicked = false;
                            }
                        }).show();

                final View viewById1 = show.findViewById(R.id.layout24dp);
                final View viewById2 = show.findViewById(R.id.layout32dp);
                final View viewById3 = show.findViewById(R.id.layout44dp);
                final View viewById4 = show.findViewById(R.id.layout48dp);
                final View viewById5 = show.findViewById(R.id.layout56dp);
                final View viewById6 = show.findViewById(R.id.layout64dp);
                final View.OnClickListener viewOnClickListener = new View.OnClickListener() {
                    final /* synthetic */ TextView valtext24dp = (TextView)show.findViewById(R.id.tex24dp);
                    final /* synthetic */ TextView valtext32dp = (TextView)show.findViewById(R.id.tex32dp);
                    final /* synthetic */ TextView valtext44dp = (TextView)show.findViewById(R.id.tex44dp);
                    final /* synthetic */ TextView valtext48dp = (TextView)show.findViewById(R.id.tex48dp);
                    final /* synthetic */ TextView valtext56dp = (TextView)show.findViewById(R.id.tex56dp);
                    final /* synthetic */ TextView valtext64dp = (TextView)show.findViewById(R.id.tex64dp);
                    
                    public void onClick(final View view) {
                        int n2;
                        n2 = 0;
                        switch (view.getId()) {
                            case R.id.layout64dp: {
                                setChecked(viewById1, this.valtext24dp, false);
                                setChecked(viewById2, this.valtext32dp, false);
                                setChecked(viewById3, this.valtext44dp, false);
                                setChecked(viewById4, this.valtext48dp, false);
                                setChecked(viewById5, this.valtext56dp, false);
                                setChecked(viewById6, this.valtext64dp, true);
                                n2 = 4;
                                break ;
                            }
                            case R.id.layout56dp: {
                                setChecked(viewById1, this.valtext24dp, false);
                                setChecked(viewById2, this.valtext32dp, false);
                                setChecked(viewById3, this.valtext44dp, false);
                                setChecked(viewById4, this.valtext48dp, false);
                                setChecked(viewById5, this.valtext56dp, true);
                                setChecked(viewById6, this.valtext64dp, false);
                                n2 = 3;
                                break;
                            }
                            case R.id.layout48dp: {
                                setChecked(viewById1, this.valtext24dp, false);
                                setChecked(viewById2, this.valtext32dp, false);
                                setChecked(viewById3, this.valtext44dp, false);
                                setChecked(viewById4, this.valtext48dp, true);
                                setChecked(viewById5, this.valtext56dp, false);
                                setChecked(viewById6, this.valtext64dp, false);
                                n2 = 2;
                                break;
                            }
                            case R.id.layout44dp: {
                                setChecked(viewById1, this.valtext24dp, false);
                                setChecked(viewById2, this.valtext32dp, false);
                                setChecked(viewById3, this.valtext44dp, true);
                                setChecked(viewById4, this.valtext48dp, false);
                                setChecked(viewById5, this.valtext56dp, false);
                                setChecked(viewById6, this.valtext64dp, false);
                                n2 = 1;
                                break;
                            }
                            case R.id.layout32dp: {
                                setChecked(viewById1, this.valtext24dp, false);
                                setChecked(viewById2, this.valtext32dp, true);
                                setChecked(viewById3, this.valtext44dp, false);
                                setChecked(viewById4, this.valtext48dp, false);
                                setChecked(viewById5, this.valtext56dp, false);
                                setChecked(viewById6, this.valtext64dp, false);
                                n2 = 0;
                                break;
                            }
                            case R.id.layout24dp: {
                                setChecked(viewById1, this.valtext24dp, true);
                                setChecked(viewById2, this.valtext32dp, false);
                                setChecked(viewById3, this.valtext44dp, false);
                                setChecked(viewById4, this.valtext48dp, false);
                                setChecked(viewById5, this.valtext56dp, false);
                                setChecked(viewById6, this.valtext64dp, false);
                                n2 = -1;
                                break;
                            }
                            case R.id.tex24dp:
                            case R.id.tex32dp:
                            case R.id.tex44dp:
                            case R.id.tex48dp:
                            case R.id.tex56dp: {
                            }
                            default: {
                                n2 = n;
                                break;
                            }
                        }
                        SettingsActivity.this.preferences.edit().putInt("iconsize", n2).apply();
                        ((GenericSettingsItem)baseItem).setDescription(n2 * 8 + 32 + "dp");
                        adapter.notifyItemChanged(n2);
                        if (hasClicked) {
                            hasClicked = false;
                            show.dismiss();
                        }
                        //}
                    }
                };
                viewById1.setOnClickListener(viewOnClickListener);
                viewById2.setOnClickListener(viewOnClickListener);
                viewById3.setOnClickListener(viewOnClickListener);
                viewById4.setOnClickListener(viewOnClickListener);
                viewById5.setOnClickListener(viewOnClickListener);
                viewById6.setOnClickListener(viewOnClickListener);
                switch (SettingsActivity.this.preferences.getInt("iconsize", Const.Defaults.getInt("iconsize"))) {
                    case -1: {
                        viewById1.performClick();
                        break;
                    }
                    case 0: {
                        viewById2.performClick();
                        break;
                    }
                    case 1: {
                        viewById3.performClick();
                        break;
                    }
                    case 2: {
                        viewById4.performClick();
                        break;
                    }
                    case 3: {
                        viewById5.performClick();
                        break;
                    }
                    case 4: {
                        viewById6.performClick();
                        break;
                    }
                }
                this.hasClicked = true;
            }
            
            void setChecked(final View view, final TextView textView, final boolean b) {
                Object drawable;
                if (b) {
                    drawable = new ColorDrawable(12285185);
                }
                else {
                    drawable = SettingsActivity.this.getDrawable(R.drawable.ripple_colored_white_darkripple);
                }
                view.setBackground((Drawable)drawable);
                int textColor;
                if (b) {
                    textColor = -1;
                }
                else {
                    textColor = -1862270976;
                }
                textView.setTextColor(textColor);
            }
        };
        this.mQuickAppListener = new BaseItem.ItemViewHolder.OnItemClickListener() {
            @Override
            public void onClick(final View view, final BaseItem baseItem, final RecyclerView.Adapter adapter, final int n) {
                final AppSelectAdapter appSelectAdapter = new AppSelectAdapter((Context)SettingsActivity.this);
                new AlertDialog.Builder((Context)SettingsActivity.this, R.style.DialogTheme).setNegativeButton(R.string.app_name, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialogInterface, final int n) {
                    }
                }).setTitle(R.string.quick_app_floating).setAdapter((ListAdapter)appSelectAdapter, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialogInterface, final int n) {
                        SettingsActivity.this.preferences.edit().putString("qa_fab", appSelectAdapter.mInfos.get(n).getLabel().toString()).putString("qa_fab_pkg", appSelectAdapter.mInfos.get(n).getComponentName().getPackageName()).putString("qa_fab_cls", appSelectAdapter.mInfos.get(n).getComponentName().getClassName()).apply();
                        ((GenericSettingsItem)baseItem).setDescription(appSelectAdapter.mInfos.get(n).getLabel().toString());
                        adapter.notifyItemChanged(n);
                    }
                }).show();
            }
        };
        this.mDefaultHomeListener = new BaseItem.ItemViewHolder.OnItemClickListener() {
            @Override
            public void onClick(final View view, final BaseItem baseItem, final RecyclerView.Adapter adapter, final int n) {
                new IconTextListDialog((Context)SettingsActivity.this, R.style.DialogTheme, R.string.default_home).doOnSubmit((IconTextListDialog.OnDialogSubmitListener)new IconTextListDialog.OnDialogSubmitListener() {
                    @Override
                    public void onSubmit(final int n) {
                        final FolderStructure.Folder folder = SettingsActivity.this.mFolderStructure.get().folders.get(n);
                        SettingsActivity.this.preferences.edit().putString("defaultFolder", folder.folderName).apply();
                        ((GenericSettingsItem)baseItem).setDescription(folder.folderName);
                        adapter.notifyItemChanged(n);
                    }
                }).setItemModifier((IconTextListDialog.ItemModifier)new IconTextListDialog.ItemModifier() {
                    @Override
                    public int getCount() {
                        return SettingsActivity.this.mFolderStructure.get().folders.size();
                    }
                    
                    @Override
                    public Drawable getIcon(final int n) {
                        return SettingsActivity.this.getDrawable(SettingsActivity.this.getResources().getIdentifier(((FolderStructure.Folder)SettingsActivity.this.mFolderStructure.get().folders.get(n)).folderIconRes, "drawable", SettingsActivity.this.getPackageName()));
                    }
                    
                    @Override
                    public int[] getIconSize(final int n) {
                        return new int[] { 24, 24 };
                    }
                    
                    @Override
                    public String getText(final int n) {
                        return ((FolderStructure.Folder)SettingsActivity.this.mFolderStructure.get().folders.get(n)).folderName;
                    }
                    
                    @Override
                    public ColorStateList getTint(final int n) {
                        return ColorStateList.valueOf(1979711488);
                    }
                }).show();
            }
        };
        this.mOpacityListener = new BaseItem.ItemViewHolder.OnItemClickListener() {
            @Override
            public void onClick(final View view, final BaseItem baseItem, final RecyclerView.Adapter adapter, final int n) {
                final float[] array = { 0.0f };
                final AlertDialog show = new AlertDialog.Builder((Context)SettingsActivity.this, R.style.DialogTheme).setTitle(R.string.action_panel_transparency).setView(R.layout.action_panel_opacity).setPositiveButton(R.string.app_name, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialogInterface, final int n) {
                        final SharedPreferences.Editor edit = SettingsActivity.this.preferences.edit();
                        edit.putFloat("panelTransparency", array[0]);
                        edit.apply();
                        ((GenericSettingsItem)baseItem).setDescription((int)(array[0] * 100.0f) + "%");
                        adapter.notifyItemChanged(n);
                    }
                }).setNegativeButton(R.string.app_name, (DialogInterface.OnClickListener)null).show();
                final SeekBar seekBar = (SeekBar)((Dialog)show).findViewById(R.id.seek_opacity);
                final TextView textView = (TextView)((Dialog)show).findViewById(R.id.text_opactity);
                array[0] = SettingsActivity.this.preferences.getFloat("panelTransparency", 1.0f);
                seekBar.setMax(100);
                seekBar.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener)new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(final SeekBar seekBar, final int n, final boolean b) {
                        array[0] = n / 100.0f;
                        textView.setText((CharSequence)(n + "%"));
                    }
                    
                    public void onStartTrackingTouch(final SeekBar seekBar) {
                    }
                    
                    public void onStopTrackingTouch(final SeekBar seekBar) {
                    }
                });
                seekBar.setProgress((int)(array[0] * 100.0f));
            }
        };

        mGridSizeListener = new BaseItem.ItemViewHolder.OnItemClickListener() {
            @Override
            public void onClick(final View view, final BaseItem baseItem, final RecyclerView.Adapter adapter, int n) {
                final int[] array = new int[2];
                array[0] = SettingsActivity.this.preferences.getInt("appwidth", SettingsActivity.this.getResources().getInteger(R.integer.app_grid_width));
                array[1] = SettingsActivity.this.preferences.getInt("appheight", SettingsActivity.this.getResources().getInteger(R.integer.app_grid_height));

                Dialog gridSizeChangeDialog = new AlertDialog.Builder(SettingsActivity.this, R.style.DialogTheme)
                        .setTitle(R.string.app_grid_size)
                        .setView(R.layout.grid_size_dialog)
                        .setPositiveButton(Resources.getSystem().getIdentifier("yes", "string", "android"), new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialogInterface, final int n) {
                                final SharedPreferences.Editor edit = SettingsActivity.this.preferences.edit();
                                edit.putInt("appwidth", array[0]);
                                edit.putInt("appheight", array[1]);
                                edit.apply();
                                ((GenericSettingsItem)baseItem).setDescription(array[0] + " wide, " + array[1] + " high");
                                adapter.notifyItemChanged(n);
                            }
                        }).setNegativeButton(Resources.getSystem().getIdentifier("no", "string", "android"), null)
                        .show();

                NumberPicker pickerWidth = (NumberPicker)gridSizeChangeDialog.findViewById(R.id.width_picker);
                NumberPicker pickerHeight = (NumberPicker)gridSizeChangeDialog.findViewById(R.id.height_picker);

                pickerWidth.setWrapSelectorWheel(false);
                pickerWidth.setMaxValue(getResources().getInteger(R.integer.grid_picker_line_max));
                pickerWidth.setMinValue(getResources().getInteger(R.integer.grid_picker_line_min));
                pickerWidth.setValue(array[0]);

                pickerWidth.setWrapSelectorWheel(false);
                pickerHeight.setMaxValue(getResources().getInteger(R.integer.grid_picker_row_max));
                pickerHeight.setMinValue(getResources().getInteger(R.integer.grid_picker_row_min));
                pickerHeight.setValue(array[1]);

                pickerWidth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        array[0] = newVal;
                    }
                });

                pickerHeight.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        array[1] = newVal;
                    }
                });

            }
        };
    }
    
    private int getDrawableFromPreference(final String s, final String s2) {
        return this.getResources().getIdentifier(PreferenceManager.getDefaultSharedPreferences((Context)this).getString(s, s2), "drawable", this.getPackageName());
    }
    
    void initActionPage() {
        this.putPage(this.getString(R.string.page_action_panel), new AddItems() {
            final /* synthetic */ Context valmContext = SettingsActivity.this;
            
            @Override
            public void create(final List<BaseItem> list) {
                list.add(new GenericSettingsItem(this.valmContext.getString(R.string.category_clock_widget), null, "", R.drawable.ic_clock, SettingsActivity.this.isPremium(false), new Intent((Context)SettingsActivity.this, (Class)ActivityClockSettings.class)));
                list.add(new GenericSettingsItem(this.valmContext.getString(R.string.action_panel_transparency), ((int)(SettingsActivity.this.preferences.getFloat("panelTransparency", Const.Defaults.getFloat("panelTransparency")) * 100.0f)) + "%", "panelTransparency", R.drawable.ic_transparency, SettingsActivity.this.isPremium(false), SettingsActivity.this.mOpacityListener));
                list.add(new SwitchSettingsItem(this.valmContext.getString(R.string.transparent_status), null, "transpStatus", R.drawable.ic_space_bar_black_24dp, SettingsActivity.this.isPremium(false), true, Const.Defaults.getBoolean("transpStatus")));
                list.add(new SwitchSettingsItem(this.valmContext.getString(R.string.direct_reveal), this.valmContext.getString(R.string.direct_reveal_desc), "directReveal", R.drawable.ic_swap_vertical_circle_black_24dp, SettingsActivity.this.isPremium(false), true, Const.Defaults.getBoolean("directReveal")));
                list.add(new GenericSettingsItem(this.valmContext.getString(R.string.default_home), SettingsActivity.this.preferences.getString("defaultFolder", Const.Defaults.getString("defaultFolder")), "defaultFolder", R.drawable.ic_home_black_24dp, SettingsActivity.this.isPremium(false), SettingsActivity.this.mDefaultHomeListener));
                list.add(new CaptionSettingsItem(SettingsActivity.this.getString(R.string.category_quick_actions)));
                list.add(new GenericSettingsItem(this.valmContext.getString(R.string.quick_app_floating), SettingsActivity.this.preferences.getString("qa_fab", Const.Defaults.getString("qa_fab")), "qa_fab", R.drawable.ic_play_circle_filled_black_24dp, SettingsActivity.this.isPremium(false), SettingsActivity.this.mQuickAppListener));
                list.add(new GenericSettingsItem(this.valmContext.getString(R.string.quick_app_floating_icon), null, "", SettingsActivity.this.getDrawableFromPreference("qaIcon", Const.Defaults.getString("qaIcon")), SettingsActivity.this.isPremium(false), new Intent((Context)SettingsActivity.this, (Class)SelectFolderIconActivity.class), 840));
                list.add(new SwitchSettingsItem(this.valmContext.getString(R.string.switch_config), this.valmContext.getString(R.string.switch_config_desc), "switchConfig", R.drawable.ic_swap_horiz_black_24dp, SettingsActivity.this.isPremium(false), true, Const.Defaults.getBoolean("switchConfig")));
            }
        });
    }
    
    void initAppPage() {
        this.putPage(this.getString(R.string.page_apps), new AddItems() {
            final /* synthetic */ Context valmContext = SettingsActivity.this;
            
            @Override
            public void create(final List<BaseItem> list) {
                final int int1 = SettingsActivity.this.preferences.getInt("appwidth", SettingsActivity.this.getResources().getInteger(R.integer.app_grid_width));
                final int int2 = SettingsActivity.this.preferences.getInt("appheight", SettingsActivity.this.getResources().getInteger(R.integer.app_grid_height));

                list.add(new GenericSettingsItem(this.valmContext.getString(R.string.menu_action_hidden_apps),
                        null,
                        "",
                        R.drawable.ic_hide,
                        SettingsActivity.this.isPremium(false),
                        new Intent(SettingsActivity.this, HiddenAppsActivity.class)));

                list.add(new GenericSettingsItem(SettingsActivity.this.getString(R.string.app_grid_size),
                        int1 + " wide, " + int2 + " high",
                        "grid_size",
                        R.drawable.ic_grid_on_black_24dp,
                        SettingsActivity.this.isPremium(false),
                        SettingsActivity.this.mGridSizeListener));

                list.add(new GenericSettingsItem(this.valmContext.getString(R.string.app_icon_size),
                        SettingsActivity.this.preferences.getInt("iconsize", Const.Defaults.getInt("iconsize")) * 8 + 32 + "dp",
                        "iconSize5",
                        R.drawable.ic_icon_size,
                        SettingsActivity.this.isPremium(true),
                        SettingsActivity.this.mIconSizeListener));

                list.add(new SwitchSettingsItem(this.valmContext.getString(R.string.app_labels),
                        null,
                        "showLabels",
                        R.drawable.ic_show_labels,
                        SettingsActivity.this.isPremium(false),
                        true,
                        Const.Defaults.getBoolean("showLabels")));

                list.add(new SwitchSettingsItem(this.valmContext.getString(R.string.enable_notification_badges),
                        "\u5728\u56fe\u7247\u4e0a\u663e\u793a\u901a\u77e5\u8ba1\u6570",
                        "notifications",
                        R.drawable.ic_notifications_black_24dp,
                        SettingsActivity.this.isPremium(true),
                        true,
                        Const.Defaults.getBoolean("notifications")));

                list.add(new SwitchSettingsItem(this.valmContext.getString(R.string.hide_apps_from_all),
                        null,
                        "hideall",
                        R.drawable.ic_settings_applications_black_24dp,
                        false));

                list.add(new CaptionSettingsItem(SettingsActivity.this.getString(R.string.category_pages)));

                list.add(new SwitchSettingsItem(this.valmContext.getString(R.string.dis_wallpaper_scroll),
                        null,
                        "disablewallpaperscroll",
                        R.drawable.ic_wallpaper_black_24dp,
                        SettingsActivity.this.isPremium(false),
                        true,
                        Const.Defaults.getBoolean("disablewallpaperscroll")));

                list.add(new GenericSettingsItem(this.valmContext.getString(R.string.app_page_transition),
                        SettingsActivity.this.getResources().getStringArray(R.array.page_transformer_names)[SettingsActivity.this.preferences.getInt("transformerINT", Const.Defaults.getInt("transformerINT"))],
                        "transformer",
                        R.drawable.ic_page_transitions,
                        SettingsActivity.this.isPremium(true),
                        SettingsActivity.this.mPageTransitionListener));
            }
        });
    }
    
    void initUXPage() {
        this.putPage(this.getString(R.string.page_home_ux), new AddItems() {
            final /* synthetic */ Context valmContext = SettingsActivity.this;
            
            @Override
            public void create(final List<BaseItem> list) {
                list.add(new GenericSettingsItem(this.valmContext.getString(R.string.activity_backup_restore),
                        null,
                        "",
                        R.drawable.ic_settings_backup_restore_black_24dp,
                        false,
                        new Intent(SettingsActivity.this, BackupRestoreActivity.class),
                        340));

                list.add(new GenericSettingsItem(this.valmContext.getString(R.string.redo_welcome),
                        null,
                        "",
                        R.drawable.ic_info_outline_black_24dp,
                        false,
                        new Intent(SettingsActivity.this, (Class)WelcomeActivity.class)));

                list.add(new GenericSettingsItem(this.valmContext.getString(R.string.reset),
                        this.valmContext.getString(R.string.reset_desc),
                        "",
                        R.drawable.ic_clear_black_24dp,
                        false,
                        SettingsActivity.this.mResetClickListener));

                list.add(new CaptionSettingsItem(SettingsActivity.this.getString(R.string.category_about)));

                String versionName = "0";
                String value = "0";
                while (true) {
                    try {
                        versionName = SettingsActivity.this.getPackageManager().getPackageInfo(SettingsActivity.this.getPackageName(), 0).versionName;
                        value = String.valueOf(SettingsActivity.this.getPackageManager().getPackageInfo(SettingsActivity.this.getPackageName(), 0).versionCode);
                        versionName = versionName;
                        list.add(new GenericSettingsItem(this.valmContext.getString(R.string.app_version), versionName, "", R.drawable.ic_home_ux, false, Uri.parse(SettingsActivity.this.getString(R.string.app_version_url))));
                        list.add(new GenericSettingsItem(this.valmContext.getString(R.string.app_version_code), value, "", R.drawable.ic_home_ux, false, Uri.parse(SettingsActivity.this.getString(R.string.app_version_code_url))));
                        if (SettingsActivity.this.isPremium(true)) {
                            list.add(new GenericSettingsItem(this.valmContext.getString(R.string.donate), this.valmContext.getString(R.string.donate_desc), "", R.drawable.ic_attach_money_black_24dp, false, Uri.parse(SettingsActivity.this.getString(R.string.donate_link))));
                        }
                        list.add(new GenericSettingsItem(this.valmContext.getString(R.string.about_dravite), this.valmContext.getString(R.string.about_dravite_desc), "", R.drawable.ic_info_black_24dp, false, Uri.parse(SettingsActivity.this.getString(R.string.about_dravite_url))));
                    }
                    catch (PackageManager.NameNotFoundException ex) {
                        ex.printStackTrace();
                        continue;
                    }
                    break;
                }
            }
        });
    }
    
    @Override
    public void initiateFragments(final SettingsFragmentAdapter settingsFragmentAdapter) {
        this.mFolderStructure = new SoftReference<FolderStructure>(LauncherActivity.mFolderStructure);
        this.initAppPage();
        this.initActionPage();
        this.initUXPage();
    }
    
    boolean isPremium(final boolean b) {
        if (this.preferences.getBoolean("isLicensed", false) || b) {}
        return false;
    }
    
    @Override
    protected void onActivityResult(final int n, final int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 340) {
            intent = this.getIntent();
            intent.putExtra("page", 3);
            this.finish();
            this.startActivity(intent);
        }
        else if (n == 840 && n2 == -1) {
            PreferenceManager.getDefaultSharedPreferences((Context)this).edit().putString("qaIcon", intent.getStringExtra("iconRes")).apply();
            intent = this.getIntent();
            intent.putExtra("page", 1);
            this.finish();
            this.startActivity(intent);
        }
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences((Context)this);
        super.onCreate(bundle);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
    }
    
    @Override
    protected void onStop() {
        super.onStop();
    }
}
