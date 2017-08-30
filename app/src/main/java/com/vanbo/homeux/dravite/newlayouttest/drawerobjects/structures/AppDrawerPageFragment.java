// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures;

import java.util.Iterator;
import com.vanbo.homeux.dravite.newlayouttest.folder_editor.FolderEditorActivity;

import android.app.Service;
import android.content.Intent;
import com.vanbo.homeux.dravite.newlayouttest.folder_editor.FolderEditorAddActivity;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.Application;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.DrawerObject;
import android.content.ComponentName;
import java.util.ArrayList;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View.OnLongClickListener;
import android.os.Vibrator;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import android.support.v7.widget.CardView;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.vanbo.homeux.dravite.newlayouttest.views.CustomGridLayout;
import android.support.v4.app.Fragment;
import com.dravite.homeux.R;

public class AppDrawerPageFragment extends Fragment
{
    private static final int VIBRATE_DROP = 20;
    public int cTag;
    public CustomGridLayout mAppGrid;
    public int mFolderPos;
    boolean mIsPopulated;
    public int mPage;
    public ViewPager mPager;
    
    public AppDrawerPageFragment() {
        this.cTag = 0;
        this.mIsPopulated = false;
    }
    
    public static AppDrawerPageFragment newInstance(final int n, final int n2) {
        final AppDrawerPageFragment appDrawerPageFragment = new AppDrawerPageFragment();
        final Bundle arguments = new Bundle();
        arguments.putInt("page", n2);
        arguments.putInt("folder", n);
        appDrawerPageFragment.setArguments(arguments);
        return appDrawerPageFragment;
    }
    
    public int getRemovalTag() {
        return this.cTag;
    }
    
    @Override
    public void onCreate(final Bundle bundle) {
        if (bundle != null) {
            this.mIsPopulated = bundle.getBoolean("isPopulated");
        }
        this.mPage = this.getArguments().getInt("page", 0);
        this.mFolderPos = this.getArguments().getInt("folder", 0);
        super.onCreate(bundle);
    }
    
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        this.mPager = (ViewPager)viewGroup;
        return layoutInflater.inflate(R.layout.grid_crad, viewGroup, false);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        final View view = this.getView();
        if (view != null) {
            final CardView cardView = (CardView)view.findViewById(R.id.testMainCard);
            int cardBackgroundColor;
            if (!((LauncherActivity)this.getActivity()).mHolder.showCard) {
                cardBackgroundColor = 0;
            }
            else {
                cardBackgroundColor = -1;
            }
            cardView.setCardBackgroundColor(cardBackgroundColor);
            float cardElevation;
            if (!((LauncherActivity)this.getActivity()).mHolder.showCard) {
                cardElevation = 0.0f;
            }
            else {
                cardElevation = LauncherUtils.dpToPx(4.0f, this.getContext());
            }
            cardView.setCardElevation(cardElevation);
        }
    }
    
    @Override
    public void onSaveInstanceState(final Bundle bundle) {
        bundle.putBoolean("isPopulated", this.mIsPopulated);
        super.onSaveInstanceState(bundle);
    }
    
    @Override
    public void onViewCreated(final View view, @Nullable final Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.mAppGrid == null) {
            final LauncherActivity launcherActivity = (LauncherActivity)this.getActivity();
            (this.mAppGrid = (CustomGridLayout)view.findViewById(R.id.appGrid)).setAppWidgetContainer(launcherActivity.mAppWidgetContainer);
            this.mAppGrid.setDragSurface(launcherActivity.mDragView);
            this.mAppGrid.setPager(this.mPager);
            this.mAppGrid.setOnLongClickListener(new View.OnLongClickListener() {
                final /* synthetic */ Vibrator valvibrator = (Vibrator)(getActivity()).getSystemService(Service.VIBRATOR_SERVICE);
                
                public boolean onLongClick(final View view) {
                    if (AppDrawerPageFragment.this.mAppGrid.getGridType() == 0) {
                        this.valvibrator.vibrate(20L);
                        final AlertDialog.Builder builder = new AlertDialog.Builder(AppDrawerPageFragment.this.getActivity(), R.style.DialogTheme);
                        builder.setItems(new CharSequence[] { "Widget", "Shortcut", "Apps", "Remove Page" }, new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialogInterface, final int n) {
                                switch (n) {
                                    case 0: {
                                        AppDrawerPageFragment.this.mAppGrid.getAppWidgetContainer().selectWidget();
                                    }
                                    case 1: {
                                        AppDrawerPageFragment.this.mAppGrid.getAppWidgetContainer().selectShortcut();
                                    }
                                    case 2: {
                                        final FolderStructure.Folder folder = LauncherActivity.mFolderStructure.folders.get(((LauncherActivity)AppDrawerPageFragment.this.getActivity()).mPager.getCurrentItem());
                                        final ArrayList<ComponentName> list = new ArrayList<ComponentName>();
                                        final Iterator<FolderStructure.Page> iterator = folder.pages.iterator();
                                        while (iterator.hasNext()) {
                                            for (final DrawerObject drawerObject : iterator.next().items) {
                                                if (drawerObject instanceof Application) {
                                                    list.add(new ComponentName(((Application)drawerObject).packageName, ((Application)drawerObject).className));
                                                }
                                            }
                                        }
                                        final Intent intent = new Intent(AppDrawerPageFragment.this.getActivity(), (Class)FolderEditorAddActivity.class);
                                        intent.putExtra("showSave", true);
                                        FolderEditorActivity.AppListPasser.passAlreadyContainedList(list);
                                        FolderEditorActivity.AppListPasser.passAppList(new ArrayList<ComponentName>());
                                        AppDrawerPageFragment.this.getActivity().startActivityForResult(intent, 2105);
                                    }
                                    case 3: {
                                        new AlertDialog.Builder((Context)AppDrawerPageFragment.this.getActivity(), R.style.DialogTheme).setTitle("Remove Page").setMessage("Do you really want to remove this page and all its content?").setPositiveButton(R.string.app_name, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                                            public void onClick(final DialogInterface dialogInterface, final int n) {
                                                ((AppDrawerPagerAdapter)AppDrawerPageFragment.this.mPager.getAdapter()).removePage(AppDrawerPageFragment.this.mPage);
                                            }
                                        }).setNegativeButton(R.string.app_name, null).show();
                                    }
                                    default: {}
                                }
                            }
                        });
                        builder.setTitle("What to add...");
                        builder.show();
                    }
                    return false;
                }
            });
            if ((LauncherActivity.mFolderStructure.folders.get(this.mFolderPos)).isAllFolder) {
                this.mAppGrid.setGridType(1);
            }
            this.mAppGrid.setRowCount(launcherActivity.mHolder.gridHeight);
            this.mAppGrid.setColumnCount(launcherActivity.mHolder.gridWidth);
            this.mAppGrid.setSaveEnabled(true);
            this.mAppGrid.setSaveFromParentEnabled(true);
            this.mAppGrid.setPosition(this.mFolderPos, this.mPage);
            if (!this.mIsPopulated) {
                if (this.mPage < (LauncherActivity.mFolderStructure.folders.get(this.mFolderPos)).pages.size()) {
                    this.mAppGrid.refresh();
                }
                this.mIsPopulated = true;
            }
        }
    }
    
    public void setRemovalTag(final int cTag) {
        this.cTag = cTag;
    }
    
    @Override
    public void setUserVisibleHint(final boolean userVisibleHint) {
        super.setUserVisibleHint(userVisibleHint);
    }
}
