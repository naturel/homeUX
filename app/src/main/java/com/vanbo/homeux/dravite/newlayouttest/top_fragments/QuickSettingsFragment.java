// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.top_fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.pm.PackageManager;

import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;

import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.FolderPagerAdapter;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.IconPackManager;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.UpdateListener;
import com.vanbo.homeux.dravite.newlayouttest.iconpacks.ThemeAdapter;
import com.vanbo.homeux.dravite.newlayouttest.settings.SettingsActivity;
import com.vanbo.homeux.dravite.newlayouttest.views.QuickSettingsButton;

import java.util.List;
import com.dravite.homeux.R;

public class QuickSettingsFragment extends Fragment
{
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
    }
    
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        return layoutInflater.inflate(R.layout.quick_settings_fragment, viewGroup, false);
    }
    
    @Override
    public void onViewCreated(final View view, final Bundle bundle) {
        super.onViewCreated(view, bundle);
        final LauncherActivity launcherActivity = (LauncherActivity)this.getActivity();
        final QuickSettingsButton settingsBt = (QuickSettingsButton)view.findViewById(R.id.bt_settings);
        final QuickSettingsButton iconPackBt = (QuickSettingsButton)view.findViewById(R.id.bt_icon_pack);
        final QuickSettingsButton wallpaperBt = (QuickSettingsButton)view.findViewById(R.id.bt_wallpaper);

        //settings
        settingsBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                LauncherActivity.updateAfterSettings = false;
                LauncherUtils.startActivityForResult(launcherActivity, view, new Intent(launcherActivity, (Class)SettingsActivity.class), 425);
            }
        });

        //icon pack
        iconPackBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(QuickSettingsFragment.this.getActivity(), R.style.DialogTheme);
                final List<IconPackManager.Theme> allThemes = IconPackManager.getAllThemes(QuickSettingsFragment.this.getActivity(), true);
                builder.setTitle(R.string.menu_action_icon_pack)
                        .setAdapter(new ThemeAdapter(QuickSettingsFragment.this.getActivity(), allThemes), new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialogInterface, final int n) {
                                new MyJobs(launcherActivity, allThemes, n).startJobs();
                            }
                        })
                        .setNegativeButton(R.string.app_name, new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialogInterface, final int n) {
                            }
                        }).show();
            }
        });

        //wallpaper
        wallpaperBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                LauncherUtils.startActivity(launcherActivity, view, new Intent("android.intent.action.SET_WALLPAPER"));
            }
        });
    }

    class MyJobs {

        LauncherActivity launcherActivity;
        List<IconPackManager.Theme> allThemes;
        int mPackIndex;

        MyJobs(LauncherActivity activity, List<IconPackManager.Theme> themes, int index){
            launcherActivity = activity;
            allThemes = themes;
            mPackIndex = index;
        }

        public void startJobs(){
            AsyncTask<Void, Integer, Void> jobs = new MyAsyncTask();
            jobs.execute(new Void[0]);
        }

        class MyAsyncTask extends AsyncTask<Void, Integer, Void>{
            ProgressDialog mProgressDialog;
            long time;

            /* synthetic */ void access000(final MyAsyncTask asyncTask, final Integer[] array) {
                asyncTask.publishProgress(array);
            }

            protected Void doInBackground(final Void... array) {
                //while (true) {
                    try {
                        launcherActivity.mCurrentIconPack.loadAllInstalled(new UpdateListener() {
                            @Override
                            public void update(final int n, final int n2) {
                                access000(MyAsyncTask.this, new Integer[] { (int)(n / n2 * 100.0f) });
                            }
                        });
                        LauncherActivity.mDrawerTree.fullReload();
                        return null;
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        //continue;
                    }
                //    break;
                //}
                return null;
            }

            protected void onPostExecute(final Void void1) {
                this.mProgressDialog.cancel();
                ((FolderPagerAdapter)launcherActivity.mPager.getAdapter()).notifyPagesChanged();
            }

            protected void onPreExecute() {
                while (true) {
                    try {
                        launcherActivity.mCurrentIconPack = new IconPackManager.IconPack(QuickSettingsFragment.this.getActivity(), allThemes.get(mPackIndex).packageName);
                        (this.mProgressDialog = new ProgressDialog(QuickSettingsFragment.this.getActivity(), R.style.DialogTheme)).setIndeterminate(false);
                        this.mProgressDialog.setProgressStyle(1);
                        this.mProgressDialog.setProgress(0);
                        this.mProgressDialog.setTitle(R.string.dialog_apply_icon_pack_title);
                        this.mProgressDialog.setMessage(QuickSettingsFragment.this.getActivity().getString(R.string.dialog_apply_icon_pack_message));
                        this.mProgressDialog.setCancelable(false);
                        this.mProgressDialog.show();
                        this.time = System.currentTimeMillis();
                        final SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(QuickSettingsFragment.this.getActivity()).edit();
                        edit.putString("iconPack", launcherActivity.mCurrentIconPack.mPackageName);
                        edit.apply();
                    }
                    catch (PackageManager.NameNotFoundException ex) {
                        ex.printStackTrace();
                        continue;
                    }
                    break;
                }
            }

            protected void onProgressUpdate(final Integer... array) {
                this.mProgressDialog.setProgress(array[0]);
            }
        }
    }
}
