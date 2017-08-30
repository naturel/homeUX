// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.welcome;

import android.view.ViewGroup;
import com.vanbo.homeux.dravite.newlayouttest.views.FolderButton;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.Button;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.view.GestureDetector.OnGestureListener;
import android.support.v7.widget.GridLayoutManager;
import android.os.Bundle;
import java.util.Iterator;
import android.preference.PreferenceManager;
import android.app.Activity;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import java.lang.ref.WeakReference;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog;
import android.view.View;
import java.util.ArrayList;
import java.util.Collection;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.DrawerObject;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.Application;
import android.os.Process;
import android.content.pm.LauncherApps;
import android.content.ComponentName;
import com.vanbo.homeux.dravite.newlayouttest.folder_editor.FolderEditorActivity;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.FolderStructure;
import android.content.Intent;
import android.content.Context;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.JsonHelper;
import java.util.List;
import java.util.Collections;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.os.Vibrator;
import android.view.GestureDetector;
import android.widget.FrameLayout;
import android.support.v7.widget.helper.ItemTouchHelper;
//import android.view.View.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import com.dravite.homeux.R;

public class WelcomeActivityFolders extends AppCompatActivity implements View.OnClickListener
{
    ItemTouchHelper.Callback callback;
    private WelcomeFolderAdapter mAdapter;
    private int mClickedFolderIndex;
    private int mCurrentFolderIndex;
    private FrameLayout mDragLayer;
    ItemTouchHelper mItemHelper;
    private GestureDetector mTapGestureDetector;
    private Vibrator mVibrator;
    
    public WelcomeActivityFolders() {
        this.mCurrentFolderIndex = -1;
        this.callback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(final RecyclerView recyclerView, final ViewHolder viewHolder) {
                return ItemTouchHelper.Callback.makeFlag(2, 51);
            }
            
            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }
            
            @Override
            public boolean onMove(final RecyclerView recyclerView, final ViewHolder viewHolder, final ViewHolder viewHolder2) {
                Collections.swap(WelcomeActivityFolders.this.mAdapter.mFolderStructure.folders, viewHolder.getAdapterPosition(), viewHolder2.getAdapterPosition());
                JsonHelper.saveFolderStructure((Context)WelcomeActivityFolders.this, WelcomeActivityFolders.this.mAdapter.getFolderStructure());
                ((RecyclerView.Adapter)WelcomeActivityFolders.this.mAdapter).notifyItemMoved(viewHolder.getAdapterPosition(), viewHolder2.getAdapterPosition());
                return true;
            }
            
            @Override
            public void onMoved(final RecyclerView recyclerView, final ViewHolder viewHolder, final int n, final ViewHolder viewHolder2, final int n2, final int n3, final int n4) {
                super.onMoved(recyclerView, viewHolder, n, viewHolder2, n2, n3, n4);
            }
            
            @Override
            public void onSwiped(final ViewHolder viewHolder, final int n) {
            }
        };
        this.mItemHelper = new ItemTouchHelper(this.callback);
    }
    
    boolean isDefaultFolder(final int n) {
        return this.mAdapter.mFolderStructure.getDefaultFolderIndex((Context)this) == n;
    }
    
    protected void onActivityResult(final int n, final int n2, final Intent intent) {
        if (n2 == -1) {
            switch (n) {
                case 4025: {
                    final FolderStructure.Folder folder = FolderEditorActivity.FolderPasser.passFolder.get();
                    final ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("appList");
                    if (parcelableArrayListExtra.size() != 0) {
                        folder.pages.clear();
                    }
                    for (int n3 = (int)Math.ceil(parcelableArrayListExtra.size() / 16.0f), i = 0; i < n3; ++i) {
                        final FolderStructure.Page page = new FolderStructure.Page();
                        for (int n4 = 0; n4 < 16 && i * 16 + n4 < parcelableArrayListExtra.size(); ++n4) {
                            final Intent intent2 = new Intent();
                            intent2.setComponent((ComponentName)parcelableArrayListExtra.get(i * 16 + n4));
                            page.add(new Application(((LauncherApps)this.getSystemService(Context.LAUNCHER_APPS_SERVICE)).resolveActivity(intent2, Process.myUserHandle())));
                        }
                        folder.add(page);
                    }
                    this.mAdapter.addFolder(folder);
                    FolderEditorActivity.FolderPasser.passFolder.clear();
                    JsonHelper.saveFolderStructure((Context)this, this.mAdapter.getFolderStructure());
                    break;
                }
                case 4026: {
                    if (intent != null) {
                        final String stringExtra = intent.getStringExtra("iconRes");
                        final int intExtra = intent.getIntExtra("accent", -1);
                        final int intExtra2 = intent.getIntExtra("folderIndex", this.mClickedFolderIndex);
                        final String stringExtra2 = intent.getStringExtra("folderName");
                        ((FolderStructure.Folder)this.mAdapter.mFolderStructure.folders.get(intExtra2)).folderIconRes = stringExtra;
                        ((FolderStructure.Folder)this.mAdapter.mFolderStructure.folders.get(intExtra2)).folderName = stringExtra2;
                        ((FolderStructure.Folder)this.mAdapter.mFolderStructure.folders.get(intExtra2)).accentColor = intExtra;
                        final ArrayList parcelableArrayListExtra2 = intent.getParcelableArrayListExtra("appList");
                        parcelableArrayListExtra2.removeAll(JsonHelper.loadHiddenAppList((Context)this));
                        if (((FolderStructure.Folder)this.mAdapter.mFolderStructure.folders.get(intExtra2)).pages.size() == 1 && ((FolderStructure.Folder)this.mAdapter.mFolderStructure.folders.get(intExtra2)).pages.get(0).items.size() == 0) {
                            ((FolderStructure.Folder)this.mAdapter.mFolderStructure.folders.get(intExtra2)).pages.clear();
                        }
                        for (int n5 = (int)Math.ceil(parcelableArrayListExtra2.size() / 16.0f), j = 0; j < n5; ++j) {
                            final FolderStructure.Page page2 = new FolderStructure.Page();
                            for (int n6 = 0; n6 < 16 && j * 16 + n6 < parcelableArrayListExtra2.size(); ++n6) {
                                final Intent intent3 = new Intent();
                                intent3.setComponent((ComponentName)parcelableArrayListExtra2.get(j * 16 + n6));
                                page2.add(new Application(((LauncherApps)this.getSystemService(Context.LAUNCHER_APPS_SERVICE)).resolveActivity(intent3, Process.myUserHandle())));
                            }
                            ((FolderStructure.Folder)this.mAdapter.mFolderStructure.folders.get(intExtra2)).add(page2);
                        }
                        if (((FolderStructure.Folder)this.mAdapter.mFolderStructure.folders.get(intExtra2)).pages.size() == 0) {
                            ((FolderStructure.Folder)this.mAdapter.mFolderStructure.folders.get(intExtra2)).pages.add(new FolderStructure.Page());
                        }
                        ((RecyclerView.Adapter)this.mAdapter).notifyItemChanged(intExtra2);
                        JsonHelper.saveFolderStructure((Context)this, this.mAdapter.mFolderStructure);
                        break;
                    }
                    break;
                }
            }
        }
        super.onActivityResult(n, n2, intent);
    }
    
    public void onClick(final View view) {
        final AlertDialog.Builder setNegativeButton = new AlertDialog.Builder((Context)this, R.style.DialogTheme).setNegativeButton(R.string.app_name, null);
        String[] array;
        if (((FolderStructure.Folder)this.mAdapter.mFolderStructure.folders.get(this.mClickedFolderIndex)).folderName.equals("\u5168\u90e8")) {
            array = new String[] { "\u7f16\u8f91", "\u8bbe\u4e3a\u9ed8\u8ba4" };
        }
        else {
            array = new String[] { "\u7f16\u8f91", "\u8bbe\u4e3a\u9ed8\u8ba4", "\u5220\u9664" };
        }
        setNegativeButton.setItems(array, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, int defaultFolderIndex) {
                switch (defaultFolderIndex) {
                    default: {}
                    case 0: {
                        FolderEditorActivity.FolderPasser.passFolder = new WeakReference<FolderStructure.Folder>(WelcomeActivityFolders.this.mAdapter.mFolderStructure.folders.get(WelcomeActivityFolders.this.mClickedFolderIndex));
                        final ArrayList<ComponentName> list = new ArrayList<ComponentName>();
                        final Iterator<FolderStructure.Page> iterator = ((FolderStructure.Folder)WelcomeActivityFolders.this.mAdapter.mFolderStructure.folders.get(WelcomeActivityFolders.this.mClickedFolderIndex)).pages.iterator();
                        while (iterator.hasNext()) {
                            for (final DrawerObject drawerObject : iterator.next().items) {
                                if (drawerObject instanceof Application) {
                                    list.add(new ComponentName(((Application)drawerObject).packageName, ((Application)drawerObject).className));
                                }
                            }
                        }
                        FolderEditorActivity.AppListPasser.passAlreadyContainedList(list);
                        final Intent intent = new Intent((Context)WelcomeActivityFolders.this, (Class)FolderEditorActivity.class);
                        intent.putExtra("requestCode", 4026);
                        intent.putExtra("folderIndex", WelcomeActivityFolders.this.mAdapter.mFolderStructure.folders.indexOf(WelcomeActivityFolders.this.mAdapter.mFolderStructure.folders.get(WelcomeActivityFolders.this.mClickedFolderIndex)));
                        LauncherUtils.startActivityForResult(WelcomeActivityFolders.this, view, intent, 4026);
                    }
                    case 1: {
                        defaultFolderIndex = WelcomeActivityFolders.this.mAdapter.mFolderStructure.getDefaultFolderIndex((Context)WelcomeActivityFolders.this);
                        PreferenceManager.getDefaultSharedPreferences((Context)WelcomeActivityFolders.this).edit().putString("defaultFolder", ((FolderStructure.Folder)WelcomeActivityFolders.this.mAdapter.mFolderStructure.folders.get(WelcomeActivityFolders.this.mClickedFolderIndex)).folderName).apply();
                        ((RecyclerView.Adapter)WelcomeActivityFolders.this.mAdapter).notifyItemChanged(defaultFolderIndex);
                        ((RecyclerView.Adapter)WelcomeActivityFolders.this.mAdapter).notifyItemChanged(WelcomeActivityFolders.this.mAdapter.mFolderStructure.getDefaultFolderIndex((Context)WelcomeActivityFolders.this));
                    }
                    case 2: {
                        WelcomeActivityFolders.this.mAdapter.mFolderStructure.remove(WelcomeActivityFolders.this.mClickedFolderIndex);
                        ((RecyclerView.Adapter)WelcomeActivityFolders.this.mAdapter).notifyItemRemoved(WelcomeActivityFolders.this.mClickedFolderIndex);
                        ((RecyclerView.Adapter)WelcomeActivityFolders.this.mAdapter).notifyItemChanged(WelcomeActivityFolders.this.mAdapter.mFolderStructure.getDefaultFolderIndex((Context)WelcomeActivityFolders.this));
                        JsonHelper.saveFolderStructure((Context)WelcomeActivityFolders.this, WelcomeActivityFolders.this.mAdapter.mFolderStructure);
                        PreferenceManager.getDefaultSharedPreferences((Context)WelcomeActivityFolders.this).edit().putString("defaultFolder", "All").apply();
                    }
                }
            }
        }).show();
    }
    
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.welcome_folders);
        this.getWindow().setNavigationBarColor(15108398);
        this.getWindow().setStatusBarColor(15108398);
        this.mVibrator = (Vibrator)this.getSystemService(Context.VIBRATOR_SERVICE);
        final RecyclerView recyclerView = (RecyclerView)this.findViewById(R.id.folderList);
        recyclerView.setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager((Context)this, 4));
        this.mItemHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter((RecyclerView.Adapter)(this.mAdapter = new WelcomeFolderAdapter((Context)this)));
        this.mTapGestureDetector = new GestureDetector((Context)this, (GestureDetector.OnGestureListener)new SingleTapGestureDetector());
        this.mDragLayer = (FrameLayout)this.findViewById(R.id.dragLayer);
        ((FloatingActionButton)this.findViewById(R.id.fab_add)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                final FolderStructure.Folder folder = new FolderStructure.Folder();
                folder.headerImage = BitmapFactory.decodeResource(WelcomeActivityFolders.this.getResources(), R.drawable.welcome_header_small);
                folder.isAllFolder = false;
                folder.accentColor = -13615201;
                folder.folderName = "";
                folder.folderIconRes = "ic_folder";
                folder.pages.add(new FolderStructure.Page());
                folder.mFolderType = 0;
                FolderEditorActivity.FolderPasser.passFolder = new WeakReference<FolderStructure.Folder>(folder);
                final Intent intent = new Intent((Context)WelcomeActivityFolders.this, (Class)FolderEditorActivity.class);
                intent.putExtra("requestCode", 4025);
                LauncherUtils.startActivityForResult(WelcomeActivityFolders.this, view, intent, 4025);
            }
        });
        ((Button)this.findViewById(R.id.finish)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                WelcomeActivityFolders.this.startActivity(new Intent((Context)WelcomeActivityFolders.this, (Class)WelcomeActivityTopPanelInfo.class));
                WelcomeActivityFolders.this.overridePendingTransition(2131034126, 2131034127);
            }
        });
    }
    
    private class SingleTapGestureDetector extends GestureDetector.SimpleOnGestureListener
    {
        public boolean onSingleTapConfirmed(final MotionEvent motionEvent) {
            return true;
        }
    }
    
    public class WelcomeFolderAdapter extends RecyclerView.Adapter<WelcomeFolderAdapter.WelcomeFolderHolder>
    {
        private Context mContext;
        private FolderStructure mFolderStructure;
        RecyclerView parentView;
        
        public WelcomeFolderAdapter(final Context mContext) {
            this.mContext = mContext;
            this.mFolderStructure = LauncherActivity.mFolderStructure;
            if (this.mFolderStructure == null) {
                this.mFolderStructure = new FolderStructure();
                final FolderStructure.Folder folder = new FolderStructure.Folder();
                folder.folderName = "All";
                folder.isAllFolder = true;
                folder.accentColor = -1085881;
                folder.folderIconRes = "ic_all";
                folder.headerImage = LauncherUtils.drawableToBitmap(mContext.getDrawable(R.drawable.welcome_header_small));
                this.mFolderStructure.add(folder);
            }
        }
        
        public void addFolder(final FolderStructure.Folder folder) {
            this.mFolderStructure.add(folder);
            ((RecyclerView.Adapter)this).notifyItemInserted(this.mFolderStructure.folders.indexOf(folder));
        }
        
        public FolderStructure getFolderStructure() {
            return this.mFolderStructure;
        }
        
        @Override
        public int getItemCount() {
            return this.mFolderStructure.folders.size();
        }
        
        @Override
        public void onAttachedToRecyclerView(final RecyclerView parentView) {
            super.onAttachedToRecyclerView(this.parentView = parentView);
        }
        
        public void onBindViewHolder(final WelcomeFolderHolder welcomeFolderHolder, final int n) {
            final FolderButton folderButton = (FolderButton)welcomeFolderHolder.itemView;
            folderButton.assignFolder(this.mFolderStructure.folders.get(n));
            if (WelcomeActivityFolders.this.isDefaultFolder(n)) {
                folderButton.select(-22746);
            }
            else {
                folderButton.select(-12409355);
            }
            folderButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    WelcomeActivityFolders.this.mClickedFolderIndex = n;
                    WelcomeActivityFolders.this.onClick(view);
                }
            });
            if (n == WelcomeActivityFolders.this.mCurrentFolderIndex) {
                folderButton.setVisibility(View.INVISIBLE);
                return;
            }
            folderButton.setVisibility(View.VISIBLE);
        }
        
        public WelcomeFolderHolder onCreateViewHolder(final ViewGroup viewGroup, final int n) {
            return new WelcomeFolderHolder((View)new FolderButton(this.mContext));
        }
        
        public class WelcomeFolderHolder extends ViewHolder
        {
            public WelcomeFolderHolder(final View view) {
                super(view);
            }
        }
    }
}
