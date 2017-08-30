// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.folder_editor;

import android.view.MenuItem;
import android.content.ComponentName;
import java.util.ArrayList;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CheckBox;
import android.widget.Button;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.dravite.homeux.R;


public class FolderEditorAddActivity extends AppCompatActivity
{
    public static final int REQUEST_APP_LIST_MAIN = 2105;
    private boolean showSave;
    
    public FolderEditorAddActivity() {
        this.showSave = false;
    }
    
    void backPressed() {
        if (this.showSave) {
            this.setResult(0, new Intent());
            super.onBackPressed();
            return;
        }
        final RecyclerView recyclerView = (RecyclerView)this.findViewById(R.id.appGrid);
        FolderEditorActivity.AppListPasser.passAppList(((FolderAddAppsListAdapter)recyclerView.getAdapter()).mSelectedAppsList);
        FolderEditorActivity.AppListPasser.passAlreadyContainedList(((FolderAddAppsListAdapter)recyclerView.getAdapter()).mContainsList);
        final Intent intent = new Intent();
        if (this.getParent() == null) {
            this.setResult(-1, intent);
        }
        super.onBackPressed();
    }
    
    @Override
    public void onBackPressed() {
        this.backPressed();
    }
    
    @Override
    protected void onCreate(final Bundle bundle) {
        int visibility = View.VISIBLE;
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_folder_editor_add);
        final Toolbar supportActionBar = (Toolbar)this.findViewById(R.id.toolbar);
        this.setSupportActionBar(supportActionBar);
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        final Intent intent = this.getIntent();
        final ArrayList<ComponentName> receiveAppList = FolderEditorActivity.AppListPasser.receiveAppList();
        final ArrayList<ComponentName> receiveContainedList = FolderEditorActivity.AppListPasser.receiveContainedList();
        supportActionBar.setBackgroundColor(14575885);
        this.getWindow().setStatusBarColor(15108398);
        final RecyclerView recyclerView = (RecyclerView)this.findViewById(R.id.appGrid);
        recyclerView.setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager((Context)this, 4));
        recyclerView.setAdapter((RecyclerView.Adapter)new FolderAddAppsListAdapter((Context)this, receiveAppList, receiveContainedList));
        this.showSave = intent.getBooleanExtra("showSave", false);
        final Button button = (Button)this.findViewById(R.id.save);
        if (!this.showSave) {
            visibility = View.GONE;
        }
        button.setVisibility(visibility);
        ((CheckBox)this.findViewById(R.id.hideAppsInAnotherFolder)).setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                ((FolderAddAppsListAdapter)recyclerView.getAdapter()).setShowInOthers(!b);
            }
        });
        final Switch switch1 = (Switch)this.findViewById(R.id.pswitch);
        switch1.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                ((FolderAddAppsListAdapter)recyclerView.getAdapter()).setShowInOthers(!b);
            }
        });
        this.findViewById(R.id.hide_option).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                switch1.setChecked(!switch1.isChecked());
            }
        });
        button.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                FolderEditorActivity.AppListPasser.passAppList(((FolderAddAppsListAdapter)recyclerView.getAdapter()).mSelectedAppsList);
                FolderEditorActivity.AppListPasser.passAlreadyContainedList(((FolderAddAppsListAdapter)recyclerView.getAdapter()).mContainsList);
                final Intent intent = new Intent();
                if (FolderEditorAddActivity.this.getParent() == null) {
                    FolderEditorAddActivity.this.setResult(-1, intent);
                }
                FolderEditorAddActivity.this.finish();
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
}
