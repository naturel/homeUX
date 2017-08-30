// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.app_editor;

import android.os.Parcelable;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.ExceptionLog;
import java.util.Collection;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.UpdateListener;

import android.view.View;
import android.widget.TextView;
import android.widget.ProgressBar;
import com.squareup.picasso.Picasso;
import java.util.Iterator;
import android.support.v7.widget.SearchView;
import android.graphics.Bitmap;
import com.vanbo.homeux.dravite.newlayouttest.iconpacks.IconPackIconAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.content.Context;
import android.os.Bundle;
import java.util.TreeSet;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.IconPackManager;
import android.os.AsyncTask;
import java.util.HashMap;
import android.support.v7.app.AppCompatActivity;
import android.content.pm.PackageManager;
import com.dravite.homeux.R;


public class AppEditorIconSelectActivity extends AppCompatActivity
{
    public HashMap<String, Integer> mIconListWithInt;
    public HashMap<String, String> mIconListWithString;
    AsyncTask<Object, Object, Object> mIconLoader;
    IconPackManager.IconPack mMainIconPack;
    public TreeSet<Integer> mValueSetInt;
    public TreeSet<String> mValueSetString;
    
    public AppEditorIconSelectActivity() {
        this.mIconListWithInt = new HashMap<String, Integer>();
        this.mIconListWithString = new HashMap<String, String>();
        this.mValueSetInt = new TreeSet<Integer>();
        this.mValueSetString = new TreeSet<String>();
    }
    
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_app_editor_icon_select);
        final String stringExtra = this.getIntent().getStringExtra("iconPack");
        while (true) {
            try {
                this.mMainIconPack = new IconPackManager.IconPack((Context)this, stringExtra);
                final Toolbar supportActionBar = (Toolbar)this.findViewById(R.id.toolbar);
                supportActionBar.setBackgroundColor(ContextCompat.getColor((Context)this, R.color.colorPrimary));
                this.getWindow().setStatusBarColor(ContextCompat.getColor((Context)this, R.color.colorPrimaryDark));
                this.setSupportActionBar(supportActionBar);
                this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                final RecyclerView recyclerView = (RecyclerView)this.findViewById(R.id.icon_list);
                recyclerView.setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager((Context)this, 4));
                recyclerView.setAdapter((RecyclerView.Adapter)new IconPackIconAdapter((Context)this, this.mMainIconPack, this.mValueSetInt, this.mValueSetString, (IconPackIconAdapter.OnAppSelectedInterface)new IconPackIconAdapter.OnAppSelectedInterface() {
                    @Override
                    public void onAppSelected(final Bitmap bitmap) {
                        AppEditorIconSelectActivity.this.returnIcon(bitmap);
                    }
                }));
                final SearchView searchView = (SearchView)this.findViewById(R.id.searchView);
                searchView.setOnQueryTextListener((SearchView.OnQueryTextListener)new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextChange(final String s) {
                        return false;
                    }
                    
                    @Override
                    public boolean onQueryTextSubmit(final String s) {
                        final TreeSet<String> set = new TreeSet<String>();
                        final TreeSet<Integer> set2 = new TreeSet<Integer>();
                        for (final Integer n : AppEditorIconSelectActivity.this.mValueSetInt) {
                            if (n != 0 && AppEditorIconSelectActivity.this.mMainIconPack.mPackRes.getResourceEntryName((int)n).contains(searchView.getQuery())) {
                                set2.add(n);
                            }
                        }
                        for (final String s2 : AppEditorIconSelectActivity.this.mValueSetString) {
                            if (s2.contains(searchView.getQuery())) {
                                set.add(s2);
                            }
                        }
                        recyclerView.setAdapter((RecyclerView.Adapter)new IconPackIconAdapter((Context)AppEditorIconSelectActivity.this, AppEditorIconSelectActivity.this.mMainIconPack, set2, set, (IconPackIconAdapter.OnAppSelectedInterface)new IconPackIconAdapter.OnAppSelectedInterface() {
                            @Override
                            public void onAppSelected(final Bitmap bitmap) {
                                AppEditorIconSelectActivity.this.returnIcon(bitmap);
                            }
                        }));
                        return true;
                    }
                });
                searchView.setOnCloseListener((SearchView.OnCloseListener)new SearchView.OnCloseListener() {
                    @Override
                    public boolean onClose() {
                        recyclerView.setAdapter((RecyclerView.Adapter)new IconPackIconAdapter((Context)AppEditorIconSelectActivity.this, AppEditorIconSelectActivity.this.mMainIconPack, AppEditorIconSelectActivity.this.mValueSetInt, AppEditorIconSelectActivity.this.mValueSetString, (IconPackIconAdapter.OnAppSelectedInterface)new IconPackIconAdapter.OnAppSelectedInterface() {
                            @Override
                            public void onAppSelected(final Bitmap bitmap) {
                                Picasso.with((Context)AppEditorIconSelectActivity.this).shutdown();
                                AppEditorIconSelectActivity.this.returnIcon(bitmap);
                            }
                        }));
                        return true;
                    }
                });
                (this.mIconLoader = new AsyncTask<Object, Object, Object>() {
                    ProgressBar mProgressBar;
                    TextView mProgressText;
                    
                    protected Object doInBackground(final Object[] array) {
                        try {
                            AppEditorIconSelectActivity.this.mMainIconPack.loadAll(null);
                            AppEditorIconSelectActivity.this.mIconListWithInt = AppEditorIconSelectActivity.this.mMainIconPack.mIconMap;
                            AppEditorIconSelectActivity.this.mIconListWithString = AppEditorIconSelectActivity.this.mMainIconPack.mIconMapStrings;
                            AppEditorIconSelectActivity.this.mValueSetInt = new TreeSet<Integer>(AppEditorIconSelectActivity.this.mIconListWithInt.values());
                            AppEditorIconSelectActivity.this.mValueSetString = new TreeSet<String>(AppEditorIconSelectActivity.this.mIconListWithString.values());
                            return null;
                        }
                        catch (Exception ex) {
                            ExceptionLog.e(ex);
                            return null;
                        }
                    }
                    
                    protected void onPostExecute(final Object o) {
                        this.mProgressBar.setVisibility(View.GONE);
                        this.mProgressText.setVisibility(View.GONE);
                        recyclerView.setEnabled(true);
                        recyclerView.setAdapter((RecyclerView.Adapter)new IconPackIconAdapter((Context)AppEditorIconSelectActivity.this, AppEditorIconSelectActivity.this.mMainIconPack, AppEditorIconSelectActivity.this.mValueSetInt, AppEditorIconSelectActivity.this.mValueSetString, (IconPackIconAdapter.OnAppSelectedInterface)new IconPackIconAdapter.OnAppSelectedInterface() {
                            @Override
                            public void onAppSelected(final Bitmap bitmap) {
                                AppEditorIconSelectActivity.this.returnIcon(bitmap);
                            }
                        }));
                    }
                    
                    protected void onPreExecute() {
                        this.mProgressBar = (ProgressBar)AppEditorIconSelectActivity.this.findViewById(R.id.progress);
                        this.mProgressText = (TextView)AppEditorIconSelectActivity.this.findViewById(R.id.loadingText);
                        this.mProgressBar.setIndeterminateTintList(ColorStateList.valueOf(ContextCompat.getColor((Context)AppEditorIconSelectActivity.this, R.color.colorAccent)));
                        this.mProgressBar.setVisibility(View.VISIBLE);
                        this.mProgressText.setVisibility(View.VISIBLE);
                        recyclerView.setEnabled(false);
                    }
                }).execute(new Object[0]);
            }
            catch (PackageManager.NameNotFoundException ex) {
                ex.printStackTrace();
                continue;
            }
            break;
        }
    }
    
    @Override
    protected void onPause() {
        this.mIconLoader.cancel(true);
        super.onPause();
    }
    
    public void returnIcon(final Bitmap bitmap) {
        final Intent intent = new Intent();
        intent.putExtra("icon", (Parcelable)bitmap);
        if (this.getParent() == null) {
            this.setResult(-1, intent);
        }
        this.finish();
    }
}
