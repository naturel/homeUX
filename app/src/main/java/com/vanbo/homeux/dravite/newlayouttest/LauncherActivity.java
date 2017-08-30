// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.os.UserHandle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.vanbo.homeux.dravite.newlayouttest.app_editor.AppEditorActivity;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.Application;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.DrawerObject;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.Shortcut;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.Widget;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.helpers.ContactUtil;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.AppDrawerPageFragment;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.AppDrawerPagerAdapter;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.ClickableAppWidgetHostView;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.DrawerTree;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.FolderDrawerPageFragment;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.FolderPagerAdapter;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.FolderStructure;
import com.vanbo.homeux.dravite.newlayouttest.folder_editor.FolderEditorActivity;
import com.vanbo.homeux.dravite.newlayouttest.general_adapters.AppBarPagerAdapter;
import com.vanbo.homeux.dravite.newlayouttest.general_adapters.FolderDropAdapter;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.ColorUtils;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.CustomWallpaperManager;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.ExceptionLog;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.FileManager;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.IconPackManager;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.JsonHelper;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.PageTransitionManager;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.ParallelExecutor;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.PreferenceHolder;
import com.vanbo.homeux.dravite.newlayouttest.iconpacks.LicensingObserver;
import com.vanbo.homeux.dravite.newlayouttest.search.SearchResultAdapter;
import com.vanbo.homeux.dravite.newlayouttest.settings.SettingsActivity;
import com.vanbo.homeux.dravite.newlayouttest.top_fragments.ClockFragment;
import com.vanbo.homeux.dravite.newlayouttest.top_fragments.FolderListFragment;
import com.vanbo.homeux.dravite.newlayouttest.views.AppIconView;
import com.vanbo.homeux.dravite.newlayouttest.views.CustomGridLayout;
import com.vanbo.homeux.dravite.newlayouttest.views.DragSurfaceLayout;
import com.vanbo.homeux.dravite.newlayouttest.views.FolderButton;
import com.vanbo.homeux.dravite.newlayouttest.views.ObjectDropButtonStrip;
import com.vanbo.homeux.dravite.newlayouttest.views.QuickAppBar;
import com.vanbo.homeux.dravite.newlayouttest.views.RevealImageView;
import com.vanbo.homeux.dravite.newlayouttest.views.VerticalViewPager;
import com.vanbo.homeux.dravite.newlayouttest.views.helpers.AppWidgetContainer;
import com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_panel.DefaultTopPanelTransformer;
import com.vanbo.homeux.dravite.newlayouttest.views.viewcomponents.ProgressFadeDrawable;
import com.vanbo.homeux.dravite.newlayouttest.views.viewcomponents.RectOutlineProvider;
import com.vanbo.homeux.dravite.newlayouttest.views.viewcomponents.RevealOutlineProvider;
import com.vanbo.homeux.dravite.newlayouttest.welcome.WelcomeActivity;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import android.provider.Settings;

import javax.xml.parsers.ParserConfigurationException;
import com.vanbo.homeux.dravite.newlayouttest.folder_editor.FolderEditorAddActivity;
import com.dravite.homeux.R;

//import android.view.View.OnClickListener;

public class LauncherActivity extends AppCompatActivity implements Observer
{
    public static final int ANIM_DURATION_DEFAULT = 170;
    public static final int MAX_OFFSCREEN_FOLDERS = 100;
    private static final int RESULT_CHECK_PREMIUM = 484;
    private static final String TAG = "LauncherActivity";
    public static final int THREAD_COUNT = 16;
    public static boolean isPremium;
    public static DrawerTree mDrawerTree;
    public static FolderStructure mFolderStructure;
    public static ParallelExecutor mStaticParallelThreadPool;
    public static boolean updateAfterSettings;
    boolean isInFolderView;
    public ViewGroup mAppBarLayout;
    public ViewPager mAppBarPager;
    public View.OnClickListener mAppClickListener;
    private CustomGridLayout mAppGrid;
    public AppWidgetContainer mAppWidgetContainer;
    public CoordinatorLayout mCoordinatorLayout;
    public int mCurrentAccent;
    private float mCurrentFabOutlineProgress;
    public IconPackManager.IconPack mCurrentIconPack;
    public DragSurfaceLayout mDragView;
    Rect mDropFabRect;
    private View.OnClickListener mFabClickListener;
    private View.OnLongClickListener mFabLongClickListener;
    private FloatingActionButton mFloatingActionButton;
    public FolderDropAdapter mFolderDropAdapter;
    private FloatingActionButton mFolderDropButton;
    private CardView mFolderDropCard;
    private RecyclerView mFolderDropList;
    private FolderListFragment mFolderListFragment;
    public PreferenceHolder mHolder;
    @Deprecated
    public View mIndicator;
    private boolean mIsInSearchMode;
    private Intent mLicenseIntent;
    private NotificationReceiver mNotificationReceiver;
    private ObjectDropButtonStrip mObjectDropButtonStrip;
    public VerticalViewPager mPager;
    public SharedPreferences mPreferences;
    private ProgressFadeDrawable mProgressFadeDrawable;
    private RevealOutlineProvider mRevealOutlineProvider;
    private ImageButton mSearchBackButton;
    private EditText mSearchInput;
    private View mSearchInputLayout;
    private SearchResultAdapter mSearchResultAdapter;
    private View mSearchResultLayout;
    public View.OnClickListener mShortcutClickListener = (View.OnClickListener)new View.OnClickListener() {
        public void onClick(final View view) {
            LauncherUtils.startActivity(LauncherActivity.this, view, (Intent)view.getTag());
        }
    };
    public int[] mStatusBarNotificationCounts;
    public ArrayList<String> mStatusBarNotifications;
    public CustomWallpaperManager mWallpaperManager;
    
    static {
        LauncherActivity.updateAfterSettings = false;
        LauncherActivity.isPremium = false;
        LauncherActivity.mStaticParallelThreadPool = new ParallelExecutor(16);
    }
    
    public LauncherActivity() {
        this.mStatusBarNotifications = new ArrayList<String>();
        this.mStatusBarNotificationCounts = new int[0];
        this.mLicenseIntent = new Intent();
        this.mIsInSearchMode = false;
        this.mDropFabRect = new Rect();
        this.isInFolderView = false;
        this.mFabLongClickListener = (View.OnLongClickListener)new View.OnLongClickListener() {
            public boolean onLongClick(final View view) {
                if (LauncherActivity.this.mAppBarPager.getCurrentItem() != 2) {
                    if (LauncherActivity.this.mPreferences.getBoolean("switchConfig", Const.Defaults.getBoolean("switchConfig"))) {
                        LauncherActivity.this.clickFab(view);
                    }
                    else {
                        LauncherActivity.this.toggleSearchMode();
                    }
                }
                return true;
            }
        };
        this.mFabClickListener = (View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (LauncherActivity.this.mAppBarPager.getCurrentItem() == 2) {
                    final FolderStructure.Folder folder = new FolderStructure.Folder();
                    folder.headerImage = BitmapFactory.decodeResource(LauncherActivity.this.getResources(), R.drawable.welcome_header_small);
                    folder.isAllFolder = false;
                    folder.accentColor = -13615201;
                    folder.folderName = "";
                    folder.folderIconRes = "ic_folder";
                    folder.pages.add(new FolderStructure.Page());
                    folder.mFolderType = 0;
                    FolderEditorActivity.FolderPasser.passFolder = new WeakReference<FolderStructure.Folder>(folder);
                    final Intent intent = new Intent((Context)LauncherActivity.this, (Class)FolderEditorActivity.class);
                    intent.putExtra("requestCode", 4025);
                    LauncherUtils.startActivityForResult(LauncherActivity.this, view, intent, 4025);
                    return;
                }
                if (LauncherActivity.this.mPreferences.getBoolean("switchConfig", Const.Defaults.getBoolean("switchConfig"))) {
                    LauncherActivity.this.toggleSearchMode();
                    return;
                }
                LauncherActivity.this.clickFab(view);
            }
        };
        this.mAppClickListener = new View.OnClickListener() {
            public void onClick(final View view) {
                LauncherUtils.startActivity(LauncherActivity.this, view, LauncherUtils.makeLaunchIntent((Intent)view.getTag()));
            }
        };
    }
    
    private void checkPremium(final boolean b) {
        this.mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final long long1 = this.mPreferences.getLong("lastLicenseCheck", 0L);
        LauncherActivity.isPremium = this.mPreferences.getBoolean("isLicensed", false);
        if (System.currentTimeMillis() - long1 >= 86400000L || !LauncherActivity.isPremium || b) {
            LauncherLog.d("Licensing", "Checking license...");
            final SharedPreferences.Editor edit = this.mPreferences.edit();
            edit.putLong("lastLicenseCheck", System.currentTimeMillis());
            while (true) {
                try {
                    this.mLicenseIntent.setComponent(new ComponentName("com.home.ux.donate", "com.home.ux.donate.LicensingService"));
                    this.startService(this.mLicenseIntent);
                    edit.apply();
                    return;
                }
                catch (ActivityNotFoundException ex) {
                    edit.putBoolean("isLicensed", false);
                    LauncherActivity.isPremium = false;
                    ex.printStackTrace();
                    continue;
                }
                //break; vanbo [delete]
            }
        }
        LauncherLog.d("Licensing", "Loading old License.");
    }
    
    private void onAddAppsRequest() {
        new AsyncTask<Void, Void, Void>() {
            final /* synthetic */ int pos = LauncherActivity.this.mPager.getCurrentItem();
            
            protected Void doInBackground(Void... iterator) {
                iterator = (Void[])(Object)FolderEditorActivity.AppListPasser.receiveAppList().iterator();
                while (((Iterator)(Object)iterator).hasNext()) {
                    final ComponentName component = ((Iterator<ComponentName>)(Object)iterator).next();
                    final Intent intent = new Intent();
                    intent.setComponent(component);
                    LauncherActivity.this.runOnUiThread((Runnable)new Runnable() {
                        final /* synthetic */ Application application = new Application(((LauncherApps)LauncherActivity.this.getSystemService(Context.LAUNCHER_APPS_SERVICE)).resolveActivity(intent, Process.myUserHandle()));
                        
                        @Override
                        public void run() {
                            LauncherActivity.this.addAppToFolder(application, pos);
                        }
                    });
                    try {
                        Thread.sleep(20L);
                    }
                    catch (InterruptedException ex) {
                        LauncherLog.w("LauncherActivity", ex.toString());
                    }
                }
                return null;
            }
            
            protected void onPostExecute(final Void void1) {
                LauncherActivity.mFolderStructure.addFolderAssignments((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(pos));
                LauncherActivity.this.refreshAllFolder(LauncherActivity.this.mHolder.gridHeight, LauncherActivity.this.mHolder.gridWidth);
                JsonHelper.saveFolderStructure((Context)LauncherActivity.this, LauncherActivity.mFolderStructure);
            }
        }.execute(new Void[0]);
    }
    
    private void onAddFolderRequest(final Intent intent) {
        FolderStructure.Folder folder = FolderEditorActivity.FolderPasser.passFolder.get();
        FolderEditorActivity.FolderPasser.passFolder.clear();
        if (folder == null) {
            return;
        }

        ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("appList");
        if (parcelableArrayListExtra.size() != 0) {
            folder.pages.clear();
        }

        //remove all hidden apps from folder chosen list
        parcelableArrayListExtra.removeAll(JsonHelper.loadHiddenAppList(this));

        int pages = (int)Math.ceil((double)parcelableArrayListExtra.size() / mHolder.gridSize());
        for (int i = 0; i < pages; ++i) {
            final FolderStructure.Page page = new FolderStructure.Page();
            for (int n2 = 0; n2 < mHolder.gridSize() && mHolder.gridSize() * i + n2 < parcelableArrayListExtra.size(); ++n2) {
                LauncherApps launcherApps = (LauncherApps)getSystemService(Context.LAUNCHER_APPS_SERVICE);
                Intent intent2 = new Intent();
                intent2.setComponent((ComponentName)parcelableArrayListExtra.get(mHolder.gridSize() * i + n2));
                LauncherActivityInfo resolveActivity = launcherApps.resolveActivity(intent2, Process.myUserHandle());
                if (resolveActivity != null) {
                    mDrawerTree.doWithApplication(resolveActivity, new DrawerTree.LoadedListener() {
                        @Override
                        public void onApplicationLoadingFinished(final Application application) {
                            page.add(application);
                        }
                    });
                }
            }
            folder.add(page);
        }
        mFolderStructure.add(folder);
        JsonHelper.saveFolderStructure(this, mFolderStructure);

        refreshAllFolder(mHolder.gridHeight, mHolder.gridWidth);
        if (mPager.getAdapter() != null) {
            mPager.getAdapter().notifyDataSetChanged();
        }
        (((FolderListFragment)mAppBarPager.getAdapter().instantiateItem(mAppBarPager, 2)).mAdapter).notifyDataSetChanged();
    }
    
    private void onEditFolderRequest(final Intent intent) {
        if (intent == null) {
            return;
        }
        final String stringExtra = intent.getStringExtra("iconRes");
        final int intExtra = intent.getIntExtra("accent", -1);
        final int intExtra2 = intent.getIntExtra("folderIndex", this.mPager.getCurrentItem());
        final String stringExtra2 = intent.getStringExtra("folderName");
        ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(intExtra2)).folderIconRes = stringExtra;
        LauncherActivity.mFolderStructure.setFolderName((Context)this, intExtra2, stringExtra2);
        ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(intExtra2)).accentColor = intExtra;
        if (intExtra2 == this.mPager.getCurrentItem()) {
            ((ImageView)this.findViewById(R.id.revealLayout).findViewById(R.id.reveal_bg)).setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.welcome_header_small));
        }
        ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(intExtra2)).loadImage((Context)this);
        final ArrayList<ComponentName> parcelableArrayListExtra = intent.getParcelableArrayListExtra("appList");
        parcelableArrayListExtra.removeAll(JsonHelper.loadHiddenAppList((Context)this));
        for (final ComponentName next : parcelableArrayListExtra) {
            final LauncherApps launcherApps = (LauncherApps)this.getSystemService(Context.LAUNCHER_APPS_SERVICE);
            final Intent intent2 = new Intent();
            intent2.setComponent((ComponentName)next);
            final LauncherActivityInfo resolveActivity = launcherApps.resolveActivity(intent2, Process.myUserHandle());
            if (resolveActivity != null) {
                LauncherActivity.mDrawerTree.doWithApplication(resolveActivity, (DrawerTree.LoadedListener)new DrawerTree.LoadedListener() {
                    @Override
                    public void onApplicationLoadingFinished(final Application application) {
                        LauncherActivity.this.addAppToFolder(application, intExtra2);
                    }
                });
            }
        }
        if (((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(intExtra2)).pages.size() == 0) {
            ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(intExtra2)).pages.add(new FolderStructure.Page());
        }
        if (intExtra2 == this.mPager.getCurrentItem()) {
            Bitmap bitmap;
            if (((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(intExtra2)).headerImage != null && !((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(intExtra2)).headerImage.isRecycled()) {
                bitmap = ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(intExtra2)).headerImage;
            }
            else {
                bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.welcome_header_small);
            }
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(final Palette palette) {
                    LauncherActivity.this.mCurrentAccent = ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(intExtra2)).accentColor;
                }
            });
            this.revealColor(((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(intExtra2)).headerImage);
            this.mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(intExtra));
            this.mIndicator.setBackgroundTintList(ColorStateList.valueOf(intExtra));
        }
        ((RecyclerView.Adapter)((FolderListFragment)this.mAppBarPager.getAdapter().instantiateItem(this.mAppBarPager, 2)).mAdapter).notifyDataSetChanged();
        JsonHelper.saveFolderStructure((Context)this, LauncherActivity.mFolderStructure);
        this.refreshAllFolder(this.mHolder.gridHeight, this.mHolder.gridWidth);
        ((FolderPagerAdapter)this.mPager.getAdapter()).notifyPagesChanged();
    }
    
    private void onPremiumRequest(final Intent intent) {
        LauncherActivity.isPremium = (intent != null && intent.getBooleanExtra("isLicensed", Const.Defaults.getBoolean("isLicensed")));
        if (!this.mPreferences.getBoolean("hasShownMessage", Const.Defaults.getBoolean("hasShownMessage")) && LauncherActivity.isPremium) {
            new AlertDialog.Builder((Context)this, R.style.DialogTheme).setTitle("Licensing successful!").setMessage("You successfully activated HomeUX premium. Have fun!").setPositiveButton(R.string.app_name, null).show();//vanbo text sting error
        }
        final SharedPreferences.Editor edit = this.mPreferences.edit();
        edit.putBoolean("isLicensed", LauncherActivity.isPremium);
        edit.putBoolean("hasShownMessage", LauncherActivity.isPremium);
        edit.apply();
    }
    
    private void registerReceivers() {
        this.mNotificationReceiver = new NotificationReceiver();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.vanbo.homeux.dravite.homeux.NOTIFICATION_LISTENER");
        this.registerReceiver(this.mNotificationReceiver, intentFilter);
    }
    
    private void startAppWidgetContainer() {
        if (this.mAppWidgetContainer == null) {
            this.mAppWidgetContainer = new AppWidgetContainer((Context)this);
        }
        this.mAppWidgetContainer.onStartActivity();
    }
    
    public void addAppToFolder(final Application application, final int n) {
        application.mGridPosition.row = Integer.MIN_VALUE;
        application.mGridPosition.col = Integer.MIN_VALUE;
        final List<FolderStructure.Page> pages = ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(n)).pages;
        final Iterator<FolderStructure.Page> iterator = pages.iterator();
        while (iterator.hasNext()) {
            for (final DrawerObject drawerObject : iterator.next().items) {
                if (drawerObject instanceof Application && ((Application)drawerObject).packageName.equals(application.packageName) && ((Application)drawerObject).className.equals(application.className)) {
                    Snackbar.make((View)this.mCoordinatorLayout, "This app is already in this folder.", -1).show();
                    return;
                }
            }
        }
        int n2 = -1;
        FolderStructure.Page page;
        int n3;
        do {
            n3 = n2 + 1;
            page = pages.get(n3);
        } while (page.items.isFull(this.mHolder.gridSize()) && (n2 = n3) < pages.size() - 1);
        if (!page.items.isFull(this.mHolder.gridSize())) {
            final FolderDrawerPageFragment folderDrawerPageFragment = (FolderDrawerPageFragment)this.mPager.getAdapter().instantiateItem(this.mPager, n);
            final CustomGridLayout mAppGrid;
            if (folderDrawerPageFragment.mPager == null) {
                mAppGrid = null;
            }
            else {
                mAppGrid = ((AppDrawerPageFragment)folderDrawerPageFragment.mPager.getAdapter().instantiateItem(folderDrawerPageFragment.mPager, n3)).mAppGrid;
            }
            if (mAppGrid != null) {
                final Point firstFreeCell = mAppGrid.findFirstFreeCell(1, 1);
                application.mGridPosition.row = firstFreeCell.x;
                application.mGridPosition.col = firstFreeCell.y;
                mAppGrid.occupyCells(application.mGridPosition.row, application.mGridPosition.col, 1, 1);
                application.createView(mAppGrid, (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE), new DrawerObject.OnViewCreatedListener() {
                    @Override
                    public void onViewCreated(final View view) {
                        mAppGrid.addObject(view, application);
                    }
                });
            }
            //vanbo begin
            //((ArrayList<Application>)page.items).add(application);
            (page.items).add(application);
            //vanbo end
            return;
        }
        final FolderStructure.Page page2 = new FolderStructure.Page();
        page2.add(application);
        pages.add(page2);
        ((FolderDrawerPageFragment)this.mPager.getAdapter().instantiateItem(this.mPager, n)).mPager.getAdapter().notifyDataSetChanged();
    }
    
    public boolean checkFirstStart() {
        boolean b = false;
        this.findViewById(R.id.infoOverlay).setClickable(false);
        if (this.mHolder.isFirstStart) {
            Log.v(TAG, "app first time start, start welcome activity.");
            this.mAppWidgetContainer.mAppWidgetHost.deleteHost();
            LauncherUtils.startActivityForResult(this, this.mAppBarPager, new Intent(this, (Class)WelcomeActivity.class), 463);
            this.finish();
            b = true;
        }
        return b;
    }
    
    void clickFab(final View view) {
        if (this.mHolder.fabComponent != null) {
            try {
                final Intent intent = new Intent();
                intent.setComponent(this.mHolder.fabComponent);
                LauncherUtils.startActivity(this, view, LauncherUtils.makeLaunchIntent(intent));
                return;
            }
            catch (ActivityNotFoundException ex) {
                Snackbar.make((View)this.mCoordinatorLayout, "The assigned QuickApp is not installed.", -1).show();
                return;
            }
        }
        Snackbar.make((View)this.mCoordinatorLayout, "You have not assigned any QuickApp yet.", -1).show();
    }
    
    public void fetchNotifications() {
        if (!this.mPreferences.getBoolean("notifications", Const.Defaults.getBoolean("notifications"))) {
            this.mStatusBarNotifications.clear();
            this.refreshNotificationIcons();
            return;
        }
        this.sendBroadcast(new Intent("com.vanbo.homeux.dravite.homeux.NOTIFICATION_LISTENER_SERVICE"));
    }
    
    public void hideSearchMode() {
        this.mIsInSearchMode = false;
        this.mSearchInput.setText((CharSequence)"");
        this.mSearchResultAdapter.clearQuery();
        final Window window = this.getWindow();
        int statusBarColor;
        if (this.mPreferences.getBoolean("transpStatus", Const.Defaults.getBoolean("transpStatus"))) {
            statusBarColor = 0;
        }
        else {
            statusBarColor = View.MeasureSpec.EXACTLY;
        }
        window.setStatusBarColor(statusBarColor);
        this.getWindow().setNavigationBarColor(0);
        this.mFloatingActionButton.setVisibility(View.VISIBLE);
        this.mSearchResultLayout.animate().alpha(0.0f).setDuration(100L).withEndAction((Runnable)new Runnable() {
            @Override
            public void run() {
                LauncherActivity.this.mSearchResultLayout.setVisibility(View.GONE);
                ((InputMethodManager)LauncherActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(LauncherActivity.this.mSearchInput.getWindowToken(), 0);
            }
        });
        this.mSearchInputLayout.animate().alpha(0.0f).scaleX(0.9f).scaleY(0.9f).setDuration(100L).withEndAction((Runnable)new Runnable() {
            @Override
            public void run() {
                LauncherActivity.this.mSearchInputLayout.setVisibility(View.GONE);
            }
        });
        this.mPager.setVisibility(View.VISIBLE);
        this.mAppBarPager.setVisibility(View.VISIBLE);
        this.mAppBarLayout.setVisibility(View.VISIBLE);
        this.mAppBarLayout.animate().setDuration(150L).alpha(1.0f);
        this.mIndicator.animate().scaleY(1.0f).alpha(1.0f).setDuration(150L);
        final ValueAnimator ofFloat = ObjectAnimator.ofFloat(new float[] { this.mRevealOutlineProvider.getProgress(), this.mCurrentFabOutlineProgress });
        ofFloat.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                LauncherActivity.this.mRevealOutlineProvider.setProgress(LauncherActivity.this.mCurrentFabOutlineProgress * valueAnimator.getAnimatedFraction());
                LauncherActivity.this.mFloatingActionButton.invalidateOutline();
            }
        });
        ofFloat.setDuration(150L);
        ofFloat.start();
        this.mAppBarPager.animate().alpha(1.0f).setDuration(150L);
        this.mPager.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(150L);
    }
    
    public void initViews() {
        mCoordinatorLayout = (CoordinatorLayout)this.findViewById(R.id.coordinatorLayout);

        mAppBarLayout = (ViewGroup)this.findViewById(R.id.appBarLayout);
        mAppBarLayout.setOutlineProvider(new RectOutlineProvider());

        mObjectDropButtonStrip = (ObjectDropButtonStrip)this.findViewById(R.id.widgetArea);
        mFolderDropButton = (FloatingActionButton)this.findViewById(R.id.folder_drop_fab);
        mFolderDropCard = (CardView)this.findViewById(R.id.folder_drop_card);
        mFolderDropList = (RecyclerView)this.findViewById(R.id.folder_drop_list);
        mIndicator = this.findViewById(R.id.indicator);
        mDragView = (DragSurfaceLayout)this.findViewById(R.id.dragView);
        mPager = (VerticalViewPager)this.findViewById(R.id.homePager);

        mAppBarPager = (ViewPager)this.findViewById(R.id.appBarPager);
        mAppBarPager.setNestedScrollingEnabled(true);

        mFloatingActionButton = (FloatingActionButton)this.findViewById(R.id.floatingActionButton);
        mSearchInputLayout = this.findViewById(R.id.searchInputLayout);
        mSearchBackButton = (ImageButton)this.findViewById(R.id.searchBackButton);
        mSearchInput = (EditText)this.findViewById(R.id.searchInput);
        mSearchResultLayout = this.findViewById(R.id.searchResultLayout);
    }
    
    public void initializeLauncherData() {
        try {
            Log.v(TAG, "initializeLauncherData: Start icon pack loading...");
            final long currentTimeMillis = System.currentTimeMillis();
            (this.mCurrentIconPack = new IconPackManager.IconPack(this, this.mPreferences.getString("iconPack", ""))).loadAllInstalled(null);
            Log.v(TAG, "initializeLauncherData: Loading icons took " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
            mDrawerTree = new DrawerTree(this);
            mDrawerTree.fullReload();
            PageTransitionManager.initialize(this);
            ((LauncherApps)this.getSystemService(Context.LAUNCHER_APPS_SERVICE)).registerCallback(new LauncherApps.Callback() {
                public void onPackageAdded(final String s, final UserHandle userHandle) {
                    LauncherActivity.this.onAppAdded(s);
                }
                
                public void onPackageChanged(final String s, final UserHandle userHandle) {
                    LauncherActivity.this.onAppChanged(s);
                }
                
                public void onPackageRemoved(final String s, final UserHandle userHandle) {
                    LauncherActivity.this.onAppRemoved(s);
                }
                
                public void onPackagesAvailable(final String[] array, final UserHandle userHandle, final boolean b) {
                    LauncherActivity.this.onAppsAdded(array);
                }
                
                public void onPackagesUnavailable(final String[] array, final UserHandle userHandle, final boolean b) {
                    LauncherActivity.this.onAppsRemoved(array);
                }
            });
            if (mFolderStructure == null) {
                Log.v(TAG, "start construct folder structure.");
                mFolderStructure = JsonHelper.loadFolderStructure(this, mDrawerTree, mHolder);
            }
            mWallpaperManager = new CustomWallpaperManager(this);
        }
        catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        }
        catch (IOException o1) {
        	o1.printStackTrace();
        }
        catch (PackageManager.NameNotFoundException o2) {
        	o2.printStackTrace();
        }
        catch (SAXException o3) {
        	o3.printStackTrace();
        }
    }
    
    public boolean isInAllFolder() {
        return this.mPager != null && this.mPager.getCurrentItem() == LauncherActivity.mFolderStructure.folders.indexOf(LauncherActivity.mFolderStructure.getFolderWithName("All"));
    }
    
    public boolean isInFolderDropLocation(final float n, final float n2, final boolean b) {
        if (this.mPager.getCurrentItem() == LauncherActivity.mFolderStructure.getFolderIndexOfName("All")) {
            if (this.isInFolderView) {
                if (this.mFolderDropAdapter != null) {
                    this.mFolderDropAdapter.hover(this.mFolderDropList, n, n2);
                }
                if (!b) {
                    return true;
                }
            }
            else if (this.mFolderDropButton.getGlobalVisibleRect(this.mDropFabRect)) {
                return this.mDropFabRect.contains((int)n, (int)n2);
            }
        }
        return false;
    }
    
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case FolderEditorAddActivity.REQUEST_APP_LIST_MAIN: {
                if (resultCode == -1) {
                    this.onAddAppsRequest();
                    break;
                }
                break;
            }
            case FolderEditorActivity.REQUEST_ADD_FOLDER: {
                this.onAddFolderRequest(intent);
                break;
            }
            case FolderEditorActivity.REQUEST_EDIT_FOLDER: {
                this.onEditFolderRequest(intent);
                break;
            }
            case RESULT_CHECK_PREMIUM: {
                this.onPremiumRequest(intent);
                break;
            }
            case SettingsActivity.REQUEST_SETTINGS: {
                this.onSettingsRequest();
                break;
            }
            case QuickAppBar.REQUEST_ADD_QA: {
                Log.e("call", "add quick app");
                if (resultCode != -1) {
                    ((ClockFragment)this.mAppBarPager.getAdapter().instantiateItem(this.mAppBarPager, 1)).mQuickAppBar.endDrag();
                    break;
                }
                final Application application = intent.getParcelableExtra("app");
                final int intExtra = intent.getIntExtra("index", -1);
                final int intExtra2 = intent.getIntExtra("icon", -1);
                if (intExtra != -1) {
                    ((ClockFragment)this.mAppBarPager.getAdapter().instantiateItem(this.mAppBarPager, 1)).mQuickAppBar.addAnimated(intExtra2, application, intExtra);
                    break;
                }
                break;
            }
            case QuickAppBar.REQUEST_EDIT_QA: {
                if (resultCode != -1) {
                    break;
                }
                final int intExtra3 = intent.getIntExtra("index", -1);
                final int intExtra4 = intent.getIntExtra("icon", -1);
                if (intExtra3 != -1) {
                    ((ClockFragment)this.mAppBarPager.getAdapter().instantiateItem(this.mAppBarPager, 1)).mQuickAppBar.replaceIconDelayed(intExtra4, intExtra3, 150);
                    break;
                }
                break;
            }
            case AppEditorActivity.REQUEST_EDIT_APP: {
                if (resultCode == -1) {
                    LauncherActivity.mDrawerTree.change(AppEditorActivity.PassApp.softApp.get().packageName);
                    this.refreshAllFolder(this.mHolder.gridHeight, this.mHolder.gridWidth);
                    ((FolderPagerAdapter)this.mPager.getAdapter()).update();
                }
                AppEditorActivity.PassApp.softApp.clear();
                break;
            }
        }
        mAppWidgetContainer.onActivityResult(requestCode, resultCode, intent, new AppWidgetContainer.OnWidgetCreatedListener() {
            void addWidgetToNewPage(final Widget widget) {
                final FolderDrawerPageFragment folderDrawerPageFragment = (FolderDrawerPageFragment)LauncherActivity.this.mPager.getAdapter().instantiateItem(LauncherActivity.this.mPager, LauncherActivity.this.mPager.getCurrentItem());
                final FolderStructure.Page page = new FolderStructure.Page();
                page.add(widget);
                (LauncherActivity.mFolderStructure.folders.get(LauncherActivity.this.mPager.getCurrentItem())).pages.add(page);
                folderDrawerPageFragment.mPager.getAdapter().notifyDataSetChanged();
                folderDrawerPageFragment.mPager.setCurrentItem(folderDrawerPageFragment.mPager.getAdapter().getCount() - 1);
            }
            
            @Override
            public void onShortcutCreated(final Shortcut shortcut) {
                final AppDrawerPageFragment currentPagerCard = ((FolderDrawerPageFragment)LauncherActivity.this.mPager.getAdapter().instantiateItem(LauncherActivity.this.mPager, LauncherActivity.this.mPager.getCurrentItem())).getCurrentPagerCard();
                LauncherActivity.this.mAppGrid = currentPagerCard.mAppGrid;
                final Point firstFreeCell = LauncherActivity.this.mAppGrid.findFirstFreeCell(1, 1);
                shortcut.mGridPosition = new DrawerObject.GridPositioning(firstFreeCell.x, firstFreeCell.y, 1, 1);
                shortcut.createView(LauncherActivity.this.mAppGrid, LayoutInflater.from(LauncherActivity.this), new DrawerObject.OnViewCreatedListener() {
                    @Override
                    public void onViewCreated(final View view) {
                        LauncherActivity.this.mAppGrid.addObject(view, shortcut);
                    }
                });
                ((LauncherActivity.mFolderStructure.folders.get(currentPagerCard.mFolderPos)).pages.get(currentPagerCard.mPage).items).add(shortcut);
                JsonHelper.saveFolderStructure(LauncherActivity.this, LauncherActivity.mFolderStructure);
            }
            
            @Override
            public void onWidgetCreated(final View view, final int n) {

                final AppWidgetProviderInfo appWidgetInfo = LauncherActivity.this.mAppWidgetContainer.mAppWidgetManager.getAppWidgetInfo(n);
                final int minHeight = appWidgetInfo.minHeight;
                final int minWidth = appWidgetInfo.minWidth;
                final AppDrawerPageFragment currentPagerCard = ((FolderDrawerPageFragment)LauncherActivity.this.mPager.getAdapter().instantiateItem(LauncherActivity.this.mPager, LauncherActivity.this.mPager.getCurrentItem())).getCurrentPagerCard();
                LauncherActivity.this.mAppGrid = currentPagerCard.mAppGrid;
                final int rowHeight = LauncherActivity.this.mAppGrid.getRowHeight();
                final int columnWidth = LauncherActivity.this.mAppGrid.getColumnWidth();
                final int min = Math.min((minHeight + rowHeight) / LauncherActivity.this.mAppGrid.getRowHeight(), LauncherActivity.this.mAppGrid.getRowCount());
                final int min2 = Math.min((minWidth + columnWidth) / LauncherActivity.this.mAppGrid.getColumnWidth(), LauncherActivity.this.mAppGrid.getColumnCount());
                final Point firstFreeCell = LauncherActivity.this.mAppGrid.findFirstFreeCell(min, min2);
                final Widget widget = new Widget(new DrawerObject.GridPositioning(0, 0, min, min2), n, ((ClickableAppWidgetHostView)view).getAppWidgetInfo().provider);
                if (firstFreeCell.x == 0 && firstFreeCell.y == 0 && LauncherActivity.this.mAppGrid.isCellGridUsedFull(0, 0, min, min2)) {
                    new AlertDialog.Builder((Context)LauncherActivity.this, R.style.DialogTheme).setTitle("No suitable place").setMessage("There is no suitable place in this page where the widget could fit. Would you like to append it at the end of this folder on a new page?").setPositiveButton(R.string.app_name, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() { //vanbo string replace
                        public void onClick(final DialogInterface dialogInterface, final int n) {
                            addWidgetToNewPage(widget);
                            JsonHelper.saveFolderStructure((Context)LauncherActivity.this, LauncherActivity.mFolderStructure);
                        }
                    }).setNegativeButton(R.string.app_name, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {//vanbo button string
                        public void onClick(final DialogInterface dialogInterface, final int n) {
                            Snackbar.make((View)LauncherActivity.this.mCoordinatorLayout, "This widget could not be added.", -1).show();
                            LauncherActivity.this.mAppWidgetContainer.mAppWidgetHost.deleteAppWidgetId(n);
                        }
                    }).setOnCancelListener((DialogInterface.OnCancelListener)new DialogInterface.OnCancelListener() {
                        public void onCancel(final DialogInterface dialogInterface) {
                            Snackbar.make((View)LauncherActivity.this.mCoordinatorLayout, "This widget could not be added.", -1).show();
                            LauncherActivity.this.mAppWidgetContainer.mAppWidgetHost.deleteAppWidgetId(n);
                        }
                    }).show();
                    return;
                }
                widget.mGridPosition.row = firstFreeCell.x;
                widget.mGridPosition.col = firstFreeCell.y;
                (((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(currentPagerCard.mFolderPos)).pages.get(currentPagerCard.mPage).items).add(widget);
                JsonHelper.saveFolderStructure((Context)LauncherActivity.this, LauncherActivity.mFolderStructure);
                LauncherActivity.this.mAppGrid.addObject(view, widget);
            }
        });
    }
    
    public void onAppAdded(final String s) {
        if (s.equals("com.home.ux.donate")) {
            this.checkPremium(false);
        }
        while (true) {
            try {
                this.mCurrentIconPack.loadSelectedPackage(null, s);
                LauncherActivity.mDrawerTree.add(s);
                this.refreshAllFolder(this.mHolder.gridHeight, this.mHolder.gridWidth);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                continue;
            }
            break;
        }
    }
    
    public void onAppChanged(final String s) {
        LauncherLog.d("LauncherActivity", "App changed: " + s);
        if (s.equals("com.home.ux.donate")) {
            this.checkPremium(false);
        }
        while (true) {
            try {
                this.mCurrentIconPack.loadSelectedPackage(null, s);
                LauncherActivity.mDrawerTree.change(s);
                this.refreshAllFolder(this.mHolder.gridHeight, this.mHolder.gridWidth);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                continue;
            }
            break;
        }
    }
    
    public void onAppRemoved(final String s) {
        FileManager.deleteRecursive(new File(this.getCacheDir().getPath() + "/apps/" + s));
        LauncherActivity.mDrawerTree.remove(s);
        if (s.equals("com.home.ux.donate")) {
            this.checkPremium(true);
        }
        FileManager.deleteRecursive(new File(this.getCacheDir().getPath() + "/apps/" + s));
        this.refreshAllFolder(this.mHolder.gridHeight, this.mHolder.gridWidth);
        LauncherActivity.mFolderStructure.iterateThrough(false, (FolderStructure.IteratorHelper)new FolderStructure.IteratorHelper() {
            @Override
            public void getObject(final int n, final FolderStructure.Folder folder, final int n2, final FolderStructure.Page page, final int n3, final DrawerObject drawerObject) {
                if (drawerObject instanceof Application && ((Application)drawerObject).packageName.equals(s)) {
                    page.items.remove(drawerObject);
                    LauncherActivity.mFolderStructure.removeAppPackage(new ComponentName(((Application)drawerObject).packageName, ((Application)drawerObject).className));
                }
            }
        });
        JsonHelper.saveFolderStructure((Context)this, LauncherActivity.mFolderStructure);
        ((FolderPagerAdapter)this.mPager.getAdapter()).notifyPagesChanged();
    }
    
    public void onAppsAdded(final String... array) {
        final ArrayList<LauncherActivityInfo> list = new ArrayList<LauncherActivityInfo>();
        for (int length = array.length, i = 0; i < length; ++i) {
            list.addAll(((LauncherApps)this.getSystemService(Context.LAUNCHER_APPS_SERVICE)).getActivityList(array[i], Process.myUserHandle()));
        }
        while (true) {
            try {
                this.mCurrentIconPack.loadSelected(null, list);
                LauncherActivity.mDrawerTree.addAll(array);
                this.refreshAllFolder(this.mHolder.gridHeight, this.mHolder.gridWidth);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                continue;
            }
            break;
        }
    }
    
    public void onAppsRemoved(final String... array) {
        LauncherActivity.mDrawerTree.removeAll(array);
        this.refreshAllFolder(this.mHolder.gridHeight, this.mHolder.gridWidth);
    }
    
    @Override
    public void onBackPressed() {
        if (this.mIsInSearchMode) {
            this.hideSearchMode();
        }
        else {
            final View view = ((FolderDrawerPageFragment)this.mPager.getAdapter().instantiateItem(this.mPager, this.mPager.getCurrentItem())).getView();
            if (view != null) {
                final ViewPager viewPager = (ViewPager)view.findViewById(R.id.folder_pager);
                if (viewPager.getCurrentItem() == 0) {
                    this.mPager.setCurrentItem(LauncherActivity.mFolderStructure.getDefaultFolderIndex(this), true);
                }
                else {
                    viewPager.setCurrentItem(0, true);
                }
                this.mAppBarPager.setCurrentItem(1, true);
            }
        }
    }
    
    @Override
    protected void onCreate(final Bundle bundle) {
        LicensingObserver.getInstance().addObserver(this);
        this.startAppWidgetContainer();
        if (bundle != null) {
            bundle.clear();
        }
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        mHolder = new PreferenceHolder();
        updateHolder();
        Const.ICON_SIZE = this.mPreferences.getInt("iconsize", Const.Defaults.getInt("iconsize")) * 8 + 32;
        registerReceivers();
        fetchNotifications();
        checkPremium(true);
        checkFirstStart();
        initializeLauncherData();
        initViews();
        refreshAllFolder(mHolder.gridHeight, mHolder.gridWidth);
        mObjectDropButtonStrip.setAlpha(0.0f);
        mObjectDropButtonStrip.setScaleX(0.7f);
        mObjectDropButtonStrip.setScaleY(0.7f);
        mObjectDropButtonStrip.setWidgetRemoveListener((ObjectDropButtonStrip.WidgetRemoveListener)new ObjectDropButtonStrip.WidgetRemoveListener() {
            View mHoveredView;
            
            @Override
            public void doAction(final DrawerObject drawerObject, final View view, final String s, final int n, final int n2, final CustomGridLayout customGridLayout) {
                switch (s) {
                    case "appInfo": {
                        final Intent intent = new Intent();
                        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                        intent.setData(Uri.fromParts("package", ((Application)drawerObject).packageName, (String)null));
                        new Handler().postDelayed((Runnable)new Runnable() {
                            @Override
                            public void run() {
                                LauncherActivity.this.startActivity(intent);
                            }
                        }, 180L);
                        break;
                    }
                    case "editApp": {
                        if (drawerObject instanceof Application) {
                            this.editApp((Application)drawerObject, view);
                            break;
                        }
                        break;
                    }
                    case "uninstall": {
                        if (drawerObject == null || !LauncherUtils.isPackageInstalled(((Application)drawerObject).appIntent.getComponent().getPackageName(), (Context)LauncherActivity.this)) {
                            break;
                        }
                        if (LauncherUtils.canBeUninstalled(((Application)drawerObject).appIntent.getComponent().getPackageName(), (Context)LauncherActivity.this)) {
                            final Intent intent2 = new Intent("android.intent.action.DELETE");
                            intent2.setData(Uri.parse("package:" + ((Application)drawerObject).appIntent.getComponent().getPackageName()));
                            LauncherActivity.this.startActivity(intent2);
                            break;
                        }
                        Snackbar.make((View)LauncherActivity.this.mCoordinatorLayout, "This is a system app and cannot be uninstalled.", -1).show();
                        break;
                    }
                }
                if (s.contains("appAction") && drawerObject != null) {
                    final DrawerObject copy = drawerObject.copy();
                    final String[] split = s.split("\n");
                    if (split.length >= 3) {
                        final int index = LauncherActivity.mFolderStructure.folders.indexOf(LauncherActivity.mFolderStructure.getFolderWithName(split[2]));
                        if (LauncherActivity.this.mPreferences.getBoolean("hideall", Const.Defaults.getBoolean("hideall")) && LauncherActivity.this.isInAllFolder()) {
                            ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(n)).pages.get(n2).items.remove(drawerObject);
                            if (view != null && view.getParent() != null) {
                                customGridLayout.removeView(customGridLayout.findViewWithTag((Object)((Application)copy).appIntent));
                            }
                        }
                        copy.mGridPosition.col = Integer.MIN_VALUE;
                        copy.mGridPosition.row = Integer.MIN_VALUE;
                        LauncherActivity.this.addAppToFolder((Application)copy, index);
                        if (LauncherActivity.this.mPreferences.getBoolean("hideall", Const.Defaults.getBoolean("hideall"))) {
                            LauncherActivity.this.refreshAllFolder(LauncherActivity.this.mHolder.gridHeight, LauncherActivity.this.mHolder.gridWidth);
                            ((FolderPagerAdapter)LauncherActivity.this.mPager.getAdapter()).notifyPagesChanged();
                        }
                        JsonHelper.saveFolderStructure((Context)LauncherActivity.this, LauncherActivity.mFolderStructure);
                    }
                }
            }
            
            void editApp(final Application application, final View view) {
                final Intent intent = new Intent((Context)LauncherActivity.this, (Class)AppEditorActivity.class);
                AppEditorActivity.PassApp.softApp = new SoftReference<Application>(application);
                AppEditorActivity.PassApp.iconPack = new SoftReference<IconPackManager.IconPack>(LauncherActivity.this.mCurrentIconPack);
                LauncherUtils.startActivityForResult(LauncherActivity.this, view, intent, 4085);
            }
            
            @Override
            public void editFolder(final View view, final FolderStructure.Folder folder) {
                new Handler().postDelayed((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        FolderEditorActivity.FolderPasser.passFolder = new WeakReference<FolderStructure.Folder>(folder);
                        final ArrayList<ComponentName> list = new ArrayList<ComponentName>();
                        final Iterator<FolderStructure.Page> iterator = folder.pages.iterator();
                        while (iterator.hasNext()) {
                            for (final DrawerObject drawerObject : iterator.next().items) {
                                if (drawerObject instanceof Application) {
                                    list.add(new ComponentName(((Application)drawerObject).packageName, ((Application)drawerObject).className));
                                }
                            }
                        }
                        FolderEditorActivity.AppListPasser.passAlreadyContainedList(list);
                        final Intent intent = new Intent((Context)LauncherActivity.this, (Class)FolderEditorActivity.class);
                        intent.putExtra("requestCode", 4026);
                        intent.putExtra("folderIndex", LauncherActivity.mFolderStructure.folders.indexOf(folder));
                        LauncherUtils.startActivityForResult(LauncherActivity.this, view, intent, 4026);
                    }
                }, 200L);
            }
            
            @Override
            public void hovers(final DrawerObject drawerObject, final int n, final int n2, final int n3) {
                if (this.mHoveredView != LauncherActivity.this.mObjectDropButtonStrip.getChildAt(n3) || 857839347 != ((ColorDrawable)LauncherActivity.this.mObjectDropButtonStrip.getChildAt(n3).getBackground()).getColor()) {
                    if (this.mHoveredView != null) {
                        this.mHoveredView.setBackgroundColor(16777215);
                    }
                    (this.mHoveredView = LauncherActivity.this.mObjectDropButtonStrip.getChildAt(n3)).setBackgroundColor(857839347);
                }
            }
            
            @Override
            public void notHovering() {
                if (this.mHoveredView != null) {
                    this.mHoveredView.setBackgroundColor(16777215);
                }
            }
            
            @Override
            public void removeFolder(final View view, final FolderStructure.Folder folder) {
                view.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).withEndAction((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        LauncherActivity.this.mDragView.removeView(view);
                    }
                });
                LauncherLog.d("LauncherActivity", "Folder removed");
                new AlertDialog.Builder((Context)LauncherActivity.this, R.style.DialogTheme).setTitle("Delete Folder").setMessage("Are you sure to delete this Folder?").setPositiveButton(R.string.app_name, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialogInterface, int max) {
                        max = Math.max(0, Math.min(LauncherActivity.this.mPager.getCurrentItem(), LauncherActivity.mFolderStructure.folders.size() - 1 - 1));
                        folder.deleteImage((Context)LauncherActivity.this);
                        final int index = LauncherActivity.mFolderStructure.folders.indexOf(folder);
                        LauncherActivity.mFolderStructure.remove(folder);
                        JsonHelper.saveFolderStructure((Context)LauncherActivity.this, LauncherActivity.mFolderStructure);
                        ((FolderPagerAdapter)LauncherActivity.this.mPager.getAdapter()).notifyPagesChanged();
                        ((RecyclerView.Adapter)((FolderListFragment)LauncherActivity.this.mAppBarPager.getAdapter().instantiateItem(LauncherActivity.this.mAppBarPager, 2)).mAdapter).notifyItemRemoved(index);
                        ((FolderListFragment)LauncherActivity.this.mAppBarPager.getAdapter().instantiateItem(LauncherActivity.this.mAppBarPager, 2)).mAdapter.select(max);
                        LauncherActivity.this.mPager.setCurrentItem(max, false);
                        LauncherActivity.this.revealColor(((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(max)).headerImage);
                    }
                }).setNegativeButton(R.string.app_name, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {//vanbo replace
                    public void onClick(final DialogInterface dialogInterface, final int n) {
                        ((RecyclerView.Adapter)((FolderListFragment)LauncherActivity.this.mAppBarPager.getAdapter().instantiateItem(LauncherActivity.this.mAppBarPager, 2)).mAdapter).notifyDataSetChanged();
                        ((FolderPagerAdapter)LauncherActivity.this.mPager.getAdapter()).notifyPagesChanged();
                    }
                }).show();
            }
            
            @Override
            public void removeOther(final DrawerObject drawerObject, final View view, final int n, final int n2) {
                if (!(view instanceof FolderButton)) {
                    if (view != null && view.getParent() != null && !LauncherActivity.this.isInAllFolder()) {
                        ((ViewGroup)view.getParent()).removeView(view);
                        ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(n)).pages.get(n2).items.remove(drawerObject);
                        if (!LauncherActivity.this.isInAllFolder() && LauncherActivity.this.mPreferences.getBoolean("hideall", Const.Defaults.getBoolean("hideall"))) {
                            LauncherActivity.this.refreshAllFolder(LauncherActivity.this.mHolder.gridHeight, LauncherActivity.this.mHolder.gridWidth);
                        }
                        JsonHelper.saveFolderStructure((Context)LauncherActivity.this, LauncherActivity.mFolderStructure);
                        if (drawerObject instanceof Application) {
                            LauncherActivity.mFolderStructure.removeFolderAssignment(new ComponentName(((Application)drawerObject).packageName, ((Application)drawerObject).className), ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(n)).folderName);
                            LauncherActivity.this.refreshAllFolder(LauncherActivity.this.mHolder.gridHeight, LauncherActivity.this.mHolder.gridWidth);
                        }
                        Snackbar.make((View)LauncherActivity.this.mCoordinatorLayout, "App removed from this folder.", -1).setAction("Undo", (View.OnClickListener)new View.OnClickListener() {
                            public void onClick(final View view) {
                                final FolderDrawerPageFragment folderDrawerPageFragment = (FolderDrawerPageFragment)LauncherActivity.this.mPager.getAdapter().instantiateItem(LauncherActivity.this.mPager, n);
                                if (n2 >= ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(n)).pages.size()) {
                                    ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(n)).pages.add(new FolderStructure.Page());
                                    folderDrawerPageFragment.mPager.getAdapter().notifyDataSetChanged();
                                    folderDrawerPageFragment.mPager.setCurrentItem(n2, true);
                                }
                                ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(n)).pages.get(n2).add(drawerObject);
                                JsonHelper.saveFolderStructure((Context)LauncherActivity.this, LauncherActivity.mFolderStructure);
                                if (drawerObject instanceof Application) {
                                    LauncherActivity.mFolderStructure.addFolderAssignment(new ComponentName(((Application)drawerObject).packageName, ((Application)drawerObject).className), ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(n)).folderName);
                                    LauncherActivity.this.refreshAllFolder(LauncherActivity.this.mHolder.gridHeight, LauncherActivity.this.mHolder.gridWidth);
                                }
                                try {
                                    LauncherActivity.this.mAppGrid = folderDrawerPageFragment.getPagerCard(n2).mAppGrid;
                                    drawerObject.createView(LauncherActivity.this.mAppGrid, (LayoutInflater)LauncherActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE), (DrawerObject.OnViewCreatedListener)new DrawerObject.OnViewCreatedListener() {
                                        @Override
                                        public void onViewCreated(final View view) {
                                            LauncherActivity.this.mAppGrid.addObject(view, drawerObject);
                                        }
                                    });
                                }
                                catch (NullPointerException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }).show();
                    }
                    if (drawerObject != null && LauncherActivity.this.isInAllFolder() && LauncherUtils.isPackageInstalled(((Application)drawerObject).appIntent.getComponent().getPackageName(), (Context)LauncherActivity.this)) {
                        if (LauncherUtils.canBeUninstalled(((Application)drawerObject).appIntent.getComponent().getPackageName(), (Context)LauncherActivity.this)) {
                            final Intent intent = new Intent("android.intent.action.DELETE");
                            intent.setData(Uri.parse("package:" + ((Application)drawerObject).appIntent.getComponent().getPackageName()));
                            LauncherActivity.this.startActivity(intent);
                            return;
                        }
                        Snackbar.make((View)LauncherActivity.this.mCoordinatorLayout, "This is a system app and cannot be uninstalled.", -1).show();
                    }
                }
            }
            
            @Override
            public void removeWidget(final DrawerObject drawerObject, final ClickableAppWidgetHostView clickableAppWidgetHostView, final int n, final int n2) {
                LauncherActivity.this.mAppWidgetContainer.removeWidget(clickableAppWidgetHostView);
                LauncherActivity.this.mAppGrid = ((FolderDrawerPageFragment)LauncherActivity.this.mPager.getAdapter().instantiateItem(LauncherActivity.this.mPager, LauncherActivity.this.mPager.getCurrentItem())).getCurrentPagerCard().mAppGrid;
                LauncherActivity.this.mAppGrid.normalizeGrid();
                while (true) {
                    try {
                        ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(n)).pages.get(n2).items.remove(drawerObject);
                        JsonHelper.saveFolderStructure((Context)LauncherActivity.this, LauncherActivity.mFolderStructure);
                    }
                    catch (IndexOutOfBoundsException ex) {
                        continue;
                    }
                    break;
                }
            }
        });
        mPager.setAdapter(new FolderPagerAdapter(this, getSupportFragmentManager()));
        mPager.setOffscreenPageLimit(100);
        mPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(final View view, final float n) {
                if (Math.abs(n) == 1.0f) {
                    view.setVisibility(View.INVISIBLE);
                    return;
                }
                if (n < 0.0f) {
                    view.setVisibility(View.VISIBLE);
                    view.setScaleX((Math.max(-1.0f, 2.0f * n) + 1.0f) * 0.5f + 0.5f);
                    view.setScaleY((Math.max(-1.0f, 2.0f * n) + 1.0f) * 0.5f + 0.5f);
                    view.setAlpha(Math.max(-1.0f, 2.0f * n) + 1.0f);
                    view.setTranslationY(view.getHeight() * Math.abs(n));
                    return;
                }
                if (n > 0.0f) {
                    view.setVisibility(View.VISIBLE);
                    return;
                }
                view.setVisibility(View.VISIBLE);
            }
        });
        mAppBarPager.setAdapter(new AppBarPagerAdapter(this.getSupportFragmentManager()));
        mAppBarPager.setCurrentItem(1);
        mAppBarPager.setOffscreenPageLimit(2);
        mAppBarPager.setPageTransformer(true, (ViewPager.PageTransformer)new DefaultTopPanelTransformer((Context)this));
        mRevealOutlineProvider = new RevealOutlineProvider(LauncherUtils.dpToPx(28.0f, (Context)this), LauncherUtils.dpToPx(28.0f, (Context)this), 0.0f, LauncherUtils.dpToPx(28.0f, (Context)this));
        mFloatingActionButton.setOutlineProvider((ViewOutlineProvider)this.mRevealOutlineProvider);
        mFloatingActionButton.setClipToOutline(true);
        mRevealOutlineProvider.setProgress(1.0f);
        (this.mProgressFadeDrawable = new ProgressFadeDrawable(this.getDrawable(R.drawable.ic_search_black_24dp), this.getDrawable(R.drawable.ic_add_black_24dp))).setTint(-1);
        mFloatingActionButton.setImageDrawable((Drawable)this.mProgressFadeDrawable);
        mAppBarPager.addOnPageChangeListener((ViewPager.OnPageChangeListener)new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(final int n) {
            }
            
            @Override
            public void onPageScrolled(final int n, final float progress, final int n2) {
                if (n == 1 && progress > 0.0f) {
                    LauncherActivity.this.mRevealOutlineProvider.setProgress(0.8f + 0.2f * (1.0f - progress));
                    LauncherActivity.this.mFloatingActionButton.invalidateOutline();
                    LauncherActivity.this.mFloatingActionButton.setRotation(90.0f * progress);
                    LauncherActivity.this.mProgressFadeDrawable.setProgress(progress);
                }
                else if (n != 2) {
                    LauncherActivity.this.mRevealOutlineProvider.setProgress(1.0f);
                    LauncherActivity.this.mFloatingActionButton.invalidateOutline();
                    LauncherActivity.this.mFloatingActionButton.setRotation(0.0f);
                    LauncherActivity.this.mProgressFadeDrawable.setProgress(0.0f);
                }
            }
            
            @Override
            public void onPageSelected(final int n) {
            }
        });
        mDragView.setObjectDropButtonStrip(this.mObjectDropButtonStrip);
        mDragView.setDragDropListenerAppdrawer((new DragSurfaceLayout.DragDropListenerAppDrawer() {
            ViewPager mFocusPager;
            
            @Override
            public void onEndDrag() {
                LauncherActivity.this.mAppBarLayout.animate().translationY(0.0f).setDuration(170L);
                final int count = this.mFocusPager.getAdapter().getCount();
                LauncherActivity.this.mIndicator.animate().translationY(0.0f).setDuration(170L).scaleX(1.0f / count).translationX((float)(this.mFocusPager.getCurrentItem() * (LauncherActivity.this.mAppBarLayout.getMeasuredWidth() / count)));
                if (!LauncherActivity.this.mHolder.showCard) {
                    this.mFocusPager.setBackgroundColor(0);
                }
                LauncherActivity.this.mFloatingActionButton.animate().translationY(0.0f).scaleX(1.0f).scaleY(1.0f).rotation(0.0f).setDuration(170L);
                LauncherActivity.this.mObjectDropButtonStrip.animate().alpha(0.0f).scaleX(0.75f).scaleY(0.75f).setDuration(170L);
                LauncherActivity.this.switchBackFromFolderView();
                LauncherActivity.this.mFolderDropButton.animate().scaleX(0.0f).scaleY(0.0f).setDuration(170L).setInterpolator((TimeInterpolator)new AnticipateInterpolator()).withEndAction((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        LauncherActivity.this.mFolderDropButton.setVisibility(View.GONE);
                    }
                });
                this.mFocusPager.animate().scaleY(1.0f).scaleX(1.0f).setDuration(170L);
                this.mFocusPager = null;
                LauncherActivity.this.fetchNotifications();
                LauncherActivity.this.mDragView.hideNextPageIndicator();
                LauncherActivity.this.mDragView.hidePrevPageIndicator();
            }
            
            @Override
            public void onStartDrag(final View view, final DrawerObject drawerObject, final ViewPager mFocusPager) {
                LauncherActivity.this.mObjectDropButtonStrip.wipeButtons();
                this.mFocusPager = mFocusPager;
                LauncherActivity.this.mAppBarPager.setCurrentItem(1, true);
                if (drawerObject instanceof Application) {
                    final ObjectDropButtonStrip access100 = LauncherActivity.this.mObjectDropButtonStrip;
                    Drawable removeIcon;
                    if (LauncherActivity.this.isInAllFolder()) {
                        removeIcon = LauncherActivity.this.getDrawable(R.drawable.ic_action_uninstall);
                    }
                    else {
                        removeIcon = LauncherActivity.this.getDrawable(R.drawable.ic_remove_black_24dp);
                    }
                    access100.setRemoveIcon(removeIcon);
                    final ObjectDropButtonStrip access101 = LauncherActivity.this.mObjectDropButtonStrip;
                    String removeText;
                    if (LauncherActivity.this.isInAllFolder()) {
                        removeText = "Uninstall";
                    }
                    else {
                        removeText = "Remove";
                    }
                    access101.setRemoveText(removeText);
                    if (!LauncherActivity.this.isInAllFolder()) {
                        LauncherActivity.this.mObjectDropButtonStrip.addButton("Uninstall", "uninstall", LauncherActivity.this.getDrawable(R.drawable.ic_action_uninstall));
                    }
                    LauncherActivity.this.mObjectDropButtonStrip.addButton("Info", "appInfo", LauncherActivity.this.getDrawable(R.drawable.ic_action_info));
                    LauncherActivity.this.mObjectDropButtonStrip.addButton("Edit", "editApp", LauncherActivity.this.getDrawable(R.drawable.ic_action_edit));
                }
                if (!LauncherActivity.this.mHolder.showCard) {
                    this.mFocusPager.setBackgroundColor(503316480);
                }
                LauncherActivity.this.mObjectDropButtonStrip.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        LauncherActivity.this.mAppBarLayout.animate().setDuration(170L).translationY(-TypedValue.applyDimension(1, 100.0f, LauncherActivity.this.getResources().getDisplayMetrics()));
                        LauncherActivity.this.mIndicator.animate().scaleY(1.0f).setDuration(170L).translationY(-TypedValue.applyDimension(1, 100.0f, LauncherActivity.this.getResources().getDisplayMetrics()));
                        LauncherActivity.this.mFloatingActionButton.animate().setDuration(170L).translationY(-TypedValue.applyDimension(1, 100.0f, LauncherActivity.this.getResources().getDisplayMetrics())).scaleX(0.0f).scaleY(0.0f).rotation(90.0f);
                        LauncherActivity.this.mObjectDropButtonStrip.animate().setDuration(170L).alpha(1.0f).scaleX(1.0f).scaleY(1.0f);
                    }
                });
                this.mFocusPager.animate().scaleY(0.94f).scaleX(0.94f).setDuration(170L);
                if (LauncherActivity.this.mPager.getCurrentItem() == LauncherActivity.mFolderStructure.getFolderIndexOfName("All")) {
                    LauncherActivity.this.mFolderDropButton.animate().scaleY(1.0f).scaleX(1.0f).setInterpolator((TimeInterpolator)new OvershootInterpolator()).setDuration(170L).withStartAction((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            LauncherActivity.this.mFolderDropButton.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        }));
        mDragView.setDragDropListenerQuickApp((DragSurfaceLayout.DragDropListenerQuickApp)new DragSurfaceLayout.DragDropListenerQuickApp() {
            @Override
            public void onEndDrag() {
                LauncherActivity.this.mPager.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(170L);
                LauncherActivity.this.mObjectDropButtonStrip.animate().alpha(0.0f).scaleX(0.75f).scaleY(0.75f).translationY(0.0f).setDuration(170L);
                LauncherActivity.this.mFloatingActionButton.animate().scaleX(1.0f).scaleY(1.0f).rotation(0.0f).setDuration(170L);
                LauncherActivity.this.mDragView.hideNextPageIndicator();
                LauncherActivity.this.mDragView.hidePrevPageIndicator();
            }
            
            @Override
            public void onStartDrag(final View view) {
                LauncherActivity.this.mObjectDropButtonStrip.wipeButtons();
                LauncherActivity.this.mObjectDropButtonStrip.addButton("Edit", "editQa", LauncherActivity.this.getDrawable(R.drawable.ic_action_edit));
                LauncherActivity.this.mPager.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(170L);
                LauncherActivity.this.mObjectDropButtonStrip.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).translationY((float)LauncherUtils.dpToPx(100.0f, (Context)LauncherActivity.this)).setDuration(170L);
                LauncherActivity.this.mFloatingActionButton.animate().setDuration(170L).scaleX(0.0f).scaleY(0.0f).rotation(90.0f);
            }
        });
        mDragView.setDragDropListenerFolder((DragSurfaceLayout.DragDropListenerFolder)new DragSurfaceLayout.DragDropListenerFolder() {
            @Override
            public void onEndDrag() {
                ((FolderPagerAdapter)LauncherActivity.this.mPager.getAdapter()).switchFromNullState();
                LauncherActivity.this.mPager.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(170L);
                LauncherActivity.this.mObjectDropButtonStrip.animate().alpha(0.0f).scaleX(0.75f).scaleY(0.75f).translationY(0.0f).setDuration(170L);
                LauncherActivity.this.mFloatingActionButton.animate().scaleX(1.0f).scaleY(1.0f).rotation(0.0f).setDuration(170L);
                LauncherActivity.this.mDragView.hideNextPageIndicator();
                LauncherActivity.this.mDragView.hidePrevPageIndicator();
            }
            
            @Override
            public void onStartDrag(final View view, final FolderStructure.Folder folder) {
                if (folder == null) {
                    return;
                }
                if (folder.folderName.equals("All")) {
                    LauncherActivity.this.mObjectDropButtonStrip.wipeAllButtons();
                }
                else {
                    LauncherActivity.this.mObjectDropButtonStrip.wipeButtons();
                }
                LauncherActivity.this.mObjectDropButtonStrip.addButton("Edit", "editFolder", LauncherActivity.this.getDrawable(R.drawable.ic_action_edit));
                LauncherActivity.this.mPager.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(170L).withEndAction((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        ((FolderPagerAdapter)LauncherActivity.this.mPager.getAdapter()).switchToNullState();
                    }
                });
                LauncherActivity.this.mObjectDropButtonStrip.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).translationY((float)LauncherUtils.dpToPx(100.0f, (Context)LauncherActivity.this)).setDuration(170L);
                LauncherActivity.this.mFloatingActionButton.animate().setDuration(170L).scaleX(0.0f).scaleY(0.0f).rotation(90.0f);
            }
        });
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            ViewPager focusPager;
            final /* synthetic */ RevealImageView valrevImgView = (RevealImageView)LauncherActivity.this.findViewById(R.id.revealImgView);
            
            public int calcColorValue(int blue, int blue2, final float n) {
                final int red = Color.red(blue);
                final int red2 = Color.red(blue2);
                final int green = Color.green(blue);
                final int green2 = Color.green(blue2);
                blue = Color.blue(blue);
                blue2 = Color.blue(blue2);
                return Color.argb(255, (int)(red2 * n + red * (1.0f - n)), (int)(green2 * n + green * (1.0f - n)), (int)(blue2 * n + blue * (1.0f - n)));
            }
            
            @Override
            public void onPageScrollStateChanged(final int n) {
            }
            
            @Override
            public void onPageScrolled(final int n, final float progress, int tint) {
                LauncherActivity.this.mIndicator.setScaleY(1.0f - -(float)(10.0 * Math.log(1.0f - progress) * (1.0f - progress) * (1.0f - progress) * (1.0f - progress) * (1.0f - progress)));
                tint = ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(Math.max(0, n))).accentColor;
                final int accentColor = ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(Math.min(LauncherActivity.mFolderStructure.folders.size() - 1, n + 1))).accentColor;
                final int calcColorValue = this.calcColorValue(tint, accentColor, progress);
                if (ColorUtils.isBrightColor(tint)) {
                    tint = -16777216;
                }
                else {
                    tint = -1;
                }
                int n2;
                if (ColorUtils.isBrightColor(accentColor)) {
                    n2 = -16777216;
                }
                else {
                    n2 = -1;
                }
                tint = this.calcColorValue(tint, n2, progress);
                LauncherActivity.this.mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(calcColorValue));
                LauncherActivity.this.mProgressFadeDrawable.setTint(tint);
                LauncherActivity.this.mIndicator.setBackgroundTintList(ColorStateList.valueOf(calcColorValue));
                if (LauncherActivity.this.mHolder.useDirectReveal) {
                    int[] revealXCenter;
                    if (LauncherActivity.this.mAppBarPager.getCurrentItem() != 2 || LauncherActivity.this.mFolderListFragment == null) {
                        revealXCenter = new int[] { this.valrevImgView.getMeasuredWidth() / 2, this.valrevImgView.getMeasuredHeight() / 2 };
                    }
                    else {
                        revealXCenter = LauncherActivity.this.mFolderListFragment.getRevealXCenter(Math.min(LauncherActivity.mFolderStructure.folders.size() - 1, n + 1), new int[] { this.valrevImgView.getMeasuredWidth() / 2, this.valrevImgView.getMeasuredHeight() / 2 });
                    }
                    this.valrevImgView.setRevealCenter(revealXCenter[0], revealXCenter[1]);
                    this.valrevImgView.setBackground((Drawable)new BitmapDrawable(LauncherActivity.this.getResources(), ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(n)).headerImage));
                    this.valrevImgView.setForeground((Drawable)new BitmapDrawable(LauncherActivity.this.getResources(), ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(Math.min(LauncherActivity.mFolderStructure.folders.size() - 1, n + 1))).headerImage));
                    this.valrevImgView.setProgress(progress);
                }
            }
            
            @Override
            public void onPageSelected(final int n) {
                int count = 1;
                final View view = ((FolderDrawerPageFragment)LauncherActivity.this.mPager.getAdapter().instantiateItem(LauncherActivity.this.mPager, LauncherActivity.this.mPager.getCurrentItem())).getView();
                if (view != null) {
                    focusPager = (ViewPager)view.findViewById(R.id.folder_pager);
                    LauncherActivity.this.mDragView.setPager(this.focusPager);
                    if (!LauncherActivity.this.mPreferences.getBoolean("disablewallpaperscroll", Const.Defaults.getBoolean("disablewallpaperscroll"))) {
                        final ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[] { LauncherActivity.this.mWallpaperManager.getWallpaperOffsets().x, this.focusPager.getCurrentItem() / this.focusPager.getAdapter().getCount() });
                        ofFloat.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new ValueAnimator.AnimatorUpdateListener() {
                            public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                                LauncherActivity.this.mWallpaperManager.setWallpaperOffsets(LauncherActivity.this.mPager.getWindowToken(), (float)valueAnimator.getAnimatedValue(), 0.0f);
                            }
                        });
                        ofFloat.start();
                    }
                    ((GradientDrawable)LauncherActivity.this.mIndicator.getBackground()).setColor(((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(n)).accentColor);
                    if (this.focusPager.getAdapter().getCount() > 0) {
                        count = this.focusPager.getAdapter().getCount();
                    }
                    LauncherActivity.this.mIndicator.animate().scaleX(1.0f / count).translationX((float)(this.focusPager.getCurrentItem() * (LauncherActivity.this.mAppBarLayout.getMeasuredWidth() / count))).scaleY(1.0f);
                }
                if (!LauncherActivity.this.mHolder.useDirectReveal) {
                    LauncherActivity.this.revealColor(((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(n)).headerImage);
                }
                LauncherActivity.this.mCurrentAccent = ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(n)).accentColor;
                if (LauncherActivity.this.mFolderListFragment == null) {
                    LauncherActivity.this.mFolderListFragment = (FolderListFragment)LauncherActivity.this.mAppBarPager.getAdapter().instantiateItem(LauncherActivity.this.mAppBarPager, 2);
                }
                if (LauncherActivity.this.mFolderListFragment.mAdapter != null) {
                    LauncherActivity.this.mFolderListFragment.mAdapter.select(n);
                }
            }
        });
        mCoordinatorLayout.requestApplyInsets();
        mFloatingActionButton.setOnClickListener(mFabClickListener);
        mFloatingActionButton.setOnLongClickListener(mFabLongClickListener);
        final int defaultFolderIndex = LauncherActivity.mFolderStructure.getDefaultFolderIndex(this);
        final PagerAdapter adapter = mPager.getAdapter();
        //final VerticalViewPager mPager = mPager;
        int n;
        if ((n = defaultFolderIndex) == -1) {
            n = 0;
        }
        final View view = ((FolderDrawerPageFragment)adapter.instantiateItem(mPager, n)).getView();
        if (view != null) {
            final ViewPager pager = (ViewPager)view.findViewById(R.id.folder_pager);
            this.mDragView.setPager(pager);
            if (!this.mPreferences.getBoolean("disablewallpaperscroll", Const.Defaults.getBoolean("disablewallpaperscroll"))) {
                final ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[] { this.mWallpaperManager.getWallpaperOffsets().x, pager.getCurrentItem() / pager.getAdapter().getCount() });
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                        LauncherActivity.this.mWallpaperManager.setWallpaperOffsets(LauncherActivity.this.mPager.getWindowToken(), (float)valueAnimator.getAnimatedValue(), 0.0f);
                    }
                });
                ofFloat.start();
            }
            int color = (LauncherActivity.mFolderStructure.folders.get(LauncherActivity.mFolderStructure.getDefaultFolderIndex(this))).accentColor;
            ((GradientDrawable)this.mIndicator.getBackground()).setColor(color);
        }
        final FolderStructure.Folder folderWithName = LauncherActivity.mFolderStructure.getFolderWithName(this.mPreferences.getString("defaultFolder", Const.Defaults.getString("defaultFolder")));
        Log.v(TAG, "default folder index=" + defaultFolderIndex + " name=" + this.mPreferences.getString("defaultFolder", Const.Defaults.getString("defaultFolder")));
        final ViewPropertyAnimator animate = this.mIndicator.animate();
        Serializable folderWithName2;
        if (folderWithName == null) {
            folderWithName2 = LauncherActivity.mFolderStructure.getFolderWithName("All");
        }
        else {
            folderWithName2 = folderWithName;
        }
        animate.scaleX(1.0f / ((FolderStructure.Folder)folderWithName2).pages.size()).translationX(0.0f).scaleY(1.0f);
        if (folderWithName == null) {
            this.mPreferences.edit().putString("defaultFolder", Const.Defaults.getString("defaultFolder")).apply();
        }
        this.revealColor((LauncherActivity.mFolderStructure.folders.get(LauncherActivity.mFolderStructure.getDefaultFolderIndex(this))).headerImage);
        this.mCurrentAccent = (LauncherActivity.mFolderStructure.folders.get(LauncherActivity.mFolderStructure.getDefaultFolderIndex(this))).accentColor;
        this.mPager.setCurrentItem(LauncherActivity.mFolderStructure.getDefaultFolderIndex(this));
        this.fetchNotifications();
        final RecyclerView recyclerView = (RecyclerView)this.findViewById(R.id.searchResultList);
        this.mSearchResultAdapter = new SearchResultAdapter(this);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(final int n) {
                int n2 = 4;
                switch (LauncherActivity.this.mSearchResultAdapter.getItemViewType(n)) {
                    default: {
                        n2 = 1;
                        return n2;
                    }
                    case 1:
                    case 3: {
                        return n2;
                    }
                    case 0: {
                        return 1;
                    }
                    case 2: {
                        return 2;
                    }
                }
            }
        });
        recyclerView.setAdapter(this.mSearchResultAdapter);
        this.mSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(final TextView textView, final int n, final KeyEvent keyEvent) {
                if (LauncherActivity.this.mSearchResultAdapter.hasEmptyQueries() && n == 6) {
                    LauncherActivity.this.mSearchResultAdapter.startWebSearch();
                    return true;
                }
                return false;
            }
        });
        this.mSearchInput.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(final Editable editable) {
            }
            
            public void beforeTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
            }
            
            public void onTextChanged(final CharSequence charSequence, final int n, final int n2, final int n3) {
                final List<Application> applicationsContaining = LauncherActivity.mDrawerTree.getApplicationsContaining(charSequence.toString());
                LauncherActivity.this.mSearchResultAdapter.clearQuery();
                if (!applicationsContaining.isEmpty()) {
                    LauncherActivity.this.mSearchResultAdapter.addToQueryResult(applicationsContaining);
                }
                LauncherActivity.this.mSearchResultAdapter.setQuery(charSequence.toString());
                LauncherActivity.this.mSearchResultAdapter.addToContactsQueryResult(ContactUtil.getContactList((Context)LauncherActivity.this, charSequence.toString()));
            }
        });
    }
    
    @Override
    protected void onDestroy() {
        this.unregisterReceiver(this.mNotificationReceiver);
        this.mAppWidgetContainer.onStopActivity();
        super.onDestroy();
    }
    
    public boolean onKeyUp(final int n, final KeyEvent keyEvent) {
        if (n == 82) {
            if (this.mAppBarPager != null) {
                this.mAppBarPager.setCurrentItem(0);
            }
            return true;
        }
        return super.onKeyUp(n, keyEvent);
    }
    
    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        if (this.hasWindowFocus()) {
            if (this.mIsInSearchMode) {
                this.hideSearchMode();
            }
            final ViewPager mPager = ((FolderDrawerPageFragment)this.mPager.getAdapter().instantiateItem(this.mPager, this.mPager.getCurrentItem())).mPager;
            mPager.getAdapter().notifyDataSetChanged();
            mPager.setCurrentItem(0, true);
            final int defaultFolderIndex = LauncherActivity.mFolderStructure.getDefaultFolderIndex((Context)this);
            this.mPager.setCurrentItem(defaultFolderIndex, true);
            new Handler().postDelayed((Runnable)new Runnable() {
                @Override
                public void run() {
                    ((FolderDrawerPageFragment)LauncherActivity.this.mPager.getAdapter().instantiateItem(LauncherActivity.this.mPager, defaultFolderIndex)).mPager.setCurrentItem(0, true);
                    LauncherActivity.this.mAppBarPager.setCurrentItem(1, true);
                }
            }, 100L);
        }
    }
    
    protected void onRestoreInstanceState(final Bundle bundle) {
        bundle.clear();
        super.onRestoreInstanceState(bundle);
    }
    
    @Override
    protected void onResume() {
        final boolean b = true;
        super.onResume();
        updateHolder();
        checkFirstStart();
        if (this.mPager != null && this.mPager.getAdapter() != null) {
            this.mPager.getAdapter().notifyDataSetChanged();
        }
        this.mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!(LauncherActivity.isPremium = this.mPreferences.getBoolean("isLicensed", Const.Defaults.getBoolean("isLicensed")))) {
            this.checkPremium(true);
        }
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Const.ICON_SIZE = defaultSharedPreferences.getInt("iconsize", Const.Defaults.getInt("iconsize")) * 8 + 32;
        this.mProgressFadeDrawable.setDrawableStart(this.getDrawable(this.getResources().getIdentifier(PreferenceManager.getDefaultSharedPreferences((Context)this).getString("qaIcon", Const.Defaults.getString("qaIcon")), "drawable", this.getPackageName())));
        final ProgressFadeDrawable mProgressFadeDrawable = this.mProgressFadeDrawable;
        int tint;
        if (ColorUtils.isBrightColor(this.mCurrentAccent)) {
            tint = 16777216;
        }
        else {
            tint = -1;
        }
        mProgressFadeDrawable.setTint(tint);
        final boolean b2 = Settings.Secure.getString(this.getContentResolver(), "enabled_notification_listeners") != null && Settings.Secure.getString(this.getContentResolver(), "enabled_notification_listeners").contains(this.getPackageName()) && b;
        if (defaultSharedPreferences.getBoolean("notifications", Const.Defaults.getBoolean("notifications")) && !b2) {
            new AlertDialog.Builder((Context)this, R.style.DialogTheme).setTitle("\u901a\u77e5\u6743\u9650").setMessage("\u8bf7\u68c0\u67e5 HomeUX \u684c\u9762\u7684\u901a\u77e5\u6743\u9650\u662f\u5426\u5df2\u7ecf\u542f\u7528.(\u5982\u679c\u4f60\u5173\u95ed\u8fd9\u4e2a\u5bf9\u8bdd\u6846, \u901a\u8bdd\u5fbd\u7ae0\u5c06\u4e0d\u4f1a\u663e\u793a\u5728\u56fe\u6807\u4e0a)").setPositiveButton("\u6253\u5f00", (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialogInterface, final int n) {
                    LauncherActivity.this.startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                }
            }).setNegativeButton(R.string.app_name, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialogInterface, final int n) {
                    final SharedPreferences.Editor edit = defaultSharedPreferences.edit();
                    edit.putBoolean("notifications", false);
                    edit.apply();
                }
            }).show();
        }
        this.fetchNotifications();
        final FrameLayout frameLayout = (FrameLayout)this.findViewById(R.id.revealLayout);
        final float float1 = defaultSharedPreferences.getFloat("panelTransparency", Const.Defaults.getFloat("panelTransparency"));
        frameLayout.setAlpha(float1);
        this.findViewById(R.id.revealImgView).setAlpha(float1);
        if (this.mIsInSearchMode) {
            this.getWindow().setStatusBarColor(12232092);
        }
        else if (float1 == 0.0f) {
            this.mAppBarLayout.setElevation((float)LauncherUtils.dpToPx(1.0f, (Context)this));
            this.getWindow().setStatusBarColor(0);
        }
        else {
            this.mAppBarLayout.setElevation((float)LauncherUtils.dpToPx(4.0f, (Context)this));
            final Window window = this.getWindow();
            int statusBarColor;
            if (defaultSharedPreferences.getBoolean("transpStatus", Const.Defaults.getBoolean("transpStatus"))) {
                statusBarColor = 0;
            }
            else {
                statusBarColor = View.MeasureSpec.EXACTLY;
            }
            window.setStatusBarColor(statusBarColor);
        }
        if (this.mHolder.useDirectReveal) {
            frameLayout.setVisibility(View.GONE);
            this.findViewById(R.id.revealImgView).setVisibility(View.VISIBLE);
        }
        else {
            frameLayout.setVisibility(View.VISIBLE);
            this.findViewById(R.id.revealImgView).setVisibility(View.GONE);
        }
        if (!defaultSharedPreferences.getBoolean("disablewallpaperscroll", Const.Defaults.getBoolean("disablewallpaperscroll")) && this.mPager != null && this.mWallpaperManager != null) {
            final View view = ((FolderDrawerPageFragment)this.mPager.getAdapter().instantiateItem(this.mPager, this.mPager.getCurrentItem())).getView();
            if (view != null) {
                final ViewPager viewPager = (ViewPager)view.findViewById(R.id.folder_pager);
                this.mWallpaperManager.setWallpaperOffsets(this.mPager.getWindowToken(), viewPager.getCurrentItem() / viewPager.getAdapter().getCount(), 0.0f);
            }
        }
    }
    
    public void onSettingsRequest() {
        new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(final Void... array) {
                LauncherActivity.this.updateHolder();
                if (LauncherActivity.updateAfterSettings) {
                    LauncherActivity.mFolderStructure = JsonHelper.loadFolderStructure((Context)LauncherActivity.this, LauncherActivity.mDrawerTree, LauncherActivity.this.mHolder);
                    LauncherActivity.mDrawerTree.fullReload();
                }
                System.gc();
                return null;
            }
            
            protected void onPostExecute(final Void void1) {
                LauncherActivity.this.revealColor(((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(Math.min(LauncherActivity.this.mPager.getCurrentItem(), LauncherActivity.this.mPager.getAdapter().getCount() - 1))).headerImage);
                LauncherActivity.this.mAppBarPager.setAdapter(new AppBarPagerAdapter(LauncherActivity.this.getSupportFragmentManager()));
                LauncherActivity.this.refreshAllFolder(LauncherActivity.this.mHolder.gridHeight, LauncherActivity.this.mHolder.gridWidth);
                if (LauncherActivity.this.mPager != null && LauncherActivity.this.mPager.getAdapter() != null) {
                    ((FolderPagerAdapter)LauncherActivity.this.mPager.getAdapter()).update();
                }
                LauncherActivity.this.mFolderListFragment = null;
            }
            
            protected void onPreExecute() {
            }
        }.execute(new Void[0]);
    }
    
    @Override
    protected void onStart() {
        this.checkPremium(false);
        super.onStart();
    }
    
    public void refreshAllFolder(@Deprecated int i, @Deprecated int n) {
        ArrayList<LauncherActivityInfo> list = new ArrayList<LauncherActivityInfo>(mDrawerTree.getAppsWithoutHidden());
        if (this.mPreferences.getBoolean("hideall", Const.Defaults.getBoolean("hideall"))) {
            Log.v(TAG, "method:refreshAllFolder hide all applications.");
            int n2;
            for (int j = 0; j < list.size(); j = n2 + 1) {
                final LinkedList<String> list2 = LauncherActivity.mFolderStructure.mApplicationToFolderMapper.get(list.get(j).getComponentName());
                n2 = j;
                if (list2 != null) {
                    n2 = j;
                    if (!list2.isEmpty()) {
                        list.remove(j);
                        n2 = j - 1;
                    }
                }
            }
        }
        int n3 = i * n;
        FolderStructure.Folder folderWithName = mFolderStructure.getFolderWithName("All");
        int index = mFolderStructure.folders.indexOf(folderWithName);
        folderWithName.pages.clear();
        FolderStructure.Page page;
        Application application;
        for (int n4 = (int)Math.ceil((double)list.size() / n3), j = 0; j < n4; ++j) {
            page = new FolderStructure.Page();
            for (int n5 = 0; n5 < n3 && j * n3 + n5 < list.size(); ++n5) {
                application = new Application(list.get(j * n3 + n5));
                application.mGridPosition.row = n5 / n;
                application.mGridPosition.col = n5 % n;
                page.add(application);
            }
            folderWithName.add(page);
        }
        mFolderStructure.folders.set(index, folderWithName);
        JsonHelper.saveFolderStructure(this, mFolderStructure);
        if (mPager == null || mPager.getAdapter() == null) {
            return;
        }
        try {
            FolderDrawerPageFragment fdpf = (FolderDrawerPageFragment)mPager.getAdapter().instantiateItem(mPager, LauncherActivity.mFolderStructure.getFolderIndexOfName("All"));
            AppDrawerPagerAdapter adapter  = (AppDrawerPagerAdapter)fdpf.mPager.getAdapter();
            adapter.update();
        }
        catch (Exception ex) {
            ExceptionLog.w(ex);
        }
    }
    
    void refreshNotificationIcons() {
        if (this.mPager != null) {
            for (int i = Math.max(0, this.mPager.getCurrentItem() - 1); i < Math.min(this.mPager.getAdapter().getCount(), this.mPager.getCurrentItem() + 2); ++i) {
                final ViewPager mPager = ((FolderDrawerPageFragment)this.mPager.getAdapter().instantiateItem(this.mPager, i)).mPager;
                if (mPager != null) {
                    for (int j = Math.max(0, mPager.getCurrentItem() - 1); j < Math.min(mPager.getAdapter().getCount(), mPager.getCurrentItem() + 2); ++j) {
                        final AppDrawerPageFragment appDrawerPageFragment = (AppDrawerPageFragment)mPager.getAdapter().instantiateItem(mPager, j);
                        if (appDrawerPageFragment.mAppGrid != null) {
                            for (int k = 0; k < appDrawerPageFragment.mAppGrid.getChildCount(); ++k) {
                                if (appDrawerPageFragment.mAppGrid.getChildAt(k) instanceof AppIconView) {
                                    final CustomGridLayout.GridLayoutParams gridLayoutParams = (CustomGridLayout.GridLayoutParams)appDrawerPageFragment.mAppGrid.getChildAt(k).getLayoutParams();
                                    if (gridLayoutParams.viewData instanceof Application && this.mStatusBarNotifications.contains(((Application)gridLayoutParams.viewData).info.getComponentName().getPackageName())) {
                                        final int n = this.mStatusBarNotificationCounts[this.mStatusBarNotifications.indexOf(((Application)gridLayoutParams.viewData).info.getComponentName().getPackageName())];
                                        int counterOverlay;
                                        if ((counterOverlay = n) == 0) {
                                            counterOverlay = n + 1;
                                        }
                                        ((AppIconView)appDrawerPageFragment.mAppGrid.getChildAt(k)).setCounterOverlay(counterOverlay);
                                    }
                                    else {
                                        ((AppIconView)appDrawerPageFragment.mAppGrid.getChildAt(k)).removeOverlay();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void revealColor(final Bitmap bitmap) {
        final FrameLayout frameLayout = (FrameLayout)this.findViewById(R.id.revealLayout);
        final ImageView imageView = (ImageView)frameLayout.findViewById(R.id.reveal_bg);
        final ImageView imageView2 = new ImageView((Context)this);
        imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView2.setImageDrawable((Drawable)new BitmapDrawable(this.getResources(), bitmap));
        imageView2.setAlpha(0.0f);
        frameLayout.addView((View)imageView2);
        imageView2.post((Runnable)new Runnable() {
            @Override
            public void run() {
                Label_0217: {
                    if (LauncherActivity.this.mFolderListFragment != null) {
                        break Label_0217;
                    }
                    int[] revealXCenter = { imageView2.getRight() + imageView2.getLeft(), imageView2.getTop() + imageView2.getBottom() };
                Label_0085_Outer:
                    while (true) {
                        Label_0285: {
                            if (LauncherActivity.this.mAppBarPager.getCurrentItem() == 2) {
                                break Label_0285;
                            }
                            int n = (imageView2.getRight() + imageView2.getLeft()) / 2;
                        Label_0117_Outer:
                            while (true) {
                                Label_0293: {
                                    if (LauncherActivity.this.mAppBarPager.getCurrentItem() == 2) {
                                        break Label_0293;
                                    }
                                    int n2 = (imageView2.getTop() + imageView2.getBottom()) / 2;
                                    try {
                                        while (true) {
                                            final ImageView valvColorView = imageView2;
                                            float n3;
                                            if (LauncherActivity.this.mAppBarPager.getCurrentItem() == 2) {
                                                n3 = LauncherUtils.dpToPx(20.0f, (Context)LauncherActivity.this);
                                            }
                                            else {
                                                n3 = 0.0f;
                                            }
                                            final Animator circularReveal = ViewAnimationUtils.createCircularReveal((View)valvColorView, n, n2, n3, (float)imageView2.getWidth());
                                            circularReveal.addListener((Animator.AnimatorListener)new Animator.AnimatorListener() {
                                                public void onAnimationCancel(final Animator animator) {
                                                }
                                                
                                                public void onAnimationEnd(final Animator animator) {
                                                    imageView.setImageDrawable((Drawable)new BitmapDrawable(LauncherActivity.this.getResources(), bitmap));
                                                    frameLayout.removeView((View)imageView2);
                                                }
                                                
                                                public void onAnimationRepeat(final Animator animator) {
                                                }
                                                
                                                public void onAnimationStart(final Animator animator) {
                                                    imageView2.setAlpha(1.0f);
                                                }
                                            });
                                            final AnimatorSet set = new AnimatorSet();
                                            set.playTogether(new Animator[] { circularReveal });
                                            set.setDuration(800L);
                                            set.start();
                                            return;
                                            //vanbo something is wrong begin
                                            //revealXCenter = LauncherActivity.this.mFolderListFragment.getRevealXCenter(LauncherActivity.this.mPager.getCurrentItem(), new int[] { (imageView2.getRight() + imageView2.getLeft()) / 2, (imageView2.getTop() + imageView2.getBottom()) / 2 });
                                            //continue Label_0085_Outer;
                                            //n = revealXCenter[0];
                                            //continue Label_0117_Outer;
                                            //n2 = revealXCenter[1];
                                            //continue;
                                            //vanbo something is wrong end
                                        }
                                    }
                                    catch (Exception ex) {
                                        imageView.setImageDrawable((Drawable)new BitmapDrawable(LauncherActivity.this.getResources(), bitmap));
                                        frameLayout.removeView((View)imageView2);
                                        ex.printStackTrace();
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        });
    }
    
    @Override
    public void setContentView(final int contentView) {
        super.setContentView(contentView);
        this.getWindow().getDecorView().setSystemUiVisibility(1280);
    }
    
    public void switchBackFromFolderView() {
        if (!this.isInFolderView) {
            return;
        }
        this.isInFolderView = false;
        this.mFolderDropButton.animate().scaleY(1.0f).scaleX(1.0f).setInterpolator((TimeInterpolator)new OvershootInterpolator()).setDuration(170L);
        this.mPager.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f);
        this.mFolderDropCard.animate().scaleX(0.5f).scaleY(0.5f).translationY((float)LauncherUtils.dpToPx(100.0f, (Context)this)).alpha(0.0f).withEndAction((Runnable)new Runnable() {
            @Override
            public void run() {
                LauncherActivity.this.mFolderDropCard.setVisibility(View.GONE);
            }
        });
    }
    
    public void switchToFolderView() {
        if (this.isInFolderView) {
            return;
        }
        this.isInFolderView = true;
        this.mFolderDropButton.animate().scaleY(0.0f).scaleX(0.0f).setInterpolator((TimeInterpolator)new AnticipateInterpolator()).setDuration(170L);
        this.mPager.animate().scaleX(0.75f).scaleY(0.75f).alpha(0.0f);
        this.mFolderDropCard.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).translationY(0.0f).withStartAction((Runnable)new Runnable() {
            @Override
            public void run() {
                LauncherActivity.this.mFolderDropCard.setVisibility(View.VISIBLE);
                LauncherActivity.this.mFolderDropList.setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager((Context)LauncherActivity.this, 2));
                LauncherActivity.this.mFolderDropAdapter = new FolderDropAdapter((Context)LauncherActivity.this);
                LauncherActivity.this.mFolderDropList.setAdapter((RecyclerView.Adapter)LauncherActivity.this.mFolderDropAdapter);
            }
        });
    }
    
    public void toggleSearchMode() {
        this.mIsInSearchMode = true;
        this.getWindow().setNavigationBarColor(16777216);
        this.getWindow().setStatusBarColor(12232092);
        ((ImageButton)this.findViewById(R.id.searchClearButton)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                LauncherActivity.this.mSearchInput.setText((CharSequence)"");
            }
        });
        this.mSearchBackButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                LauncherActivity.this.hideSearchMode();
            }
        });
        this.mSearchResultLayout.setVisibility(View.VISIBLE);
        this.mSearchResultLayout.setAlpha(0.0f);
        this.mSearchResultLayout.animate().alpha(1.0f).setStartDelay(50L).setDuration(150L);
        this.mSearchInputLayout.setVisibility(View.VISIBLE);
        this.mSearchInputLayout.setAlpha(0.0f);
        this.mSearchInputLayout.setScaleX(0.9f);
        this.mSearchInputLayout.setScaleY(0.9f);
        this.mSearchInputLayout.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setStartDelay(50L).setDuration(150L).withEndAction((Runnable)new Runnable() {
            @Override
            public void run() {
                if (LauncherActivity.this.mSearchInput.requestFocus()) {
                    ((InputMethodManager)LauncherActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput((View)LauncherActivity.this.mSearchInput, 1);
                }
            }
        });
        this.mIndicator.animate().scaleY(0.0f).alpha(0.0f).setDuration(150L);
        this.mAppBarLayout.animate().setDuration(150L).alpha(0.0f).withEndAction((Runnable)new Runnable() {
            @Override
            public void run() {
                LauncherActivity.this.mAppBarLayout.setVisibility(View.GONE);
                LauncherActivity.this.mFloatingActionButton.setVisibility(View.GONE);
            }
        });
        this.mCurrentFabOutlineProgress = this.mRevealOutlineProvider.getProgress();
        final ValueAnimator ofFloat = ObjectAnimator.ofFloat(new float[] { this.mCurrentFabOutlineProgress, 0.0f });
        ofFloat.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                LauncherActivity.this.mRevealOutlineProvider.setProgress(LauncherActivity.this.mCurrentFabOutlineProgress * (1.0f - valueAnimator.getAnimatedFraction()));
            }
        });
        ofFloat.setDuration(150L);
        ofFloat.start();
        this.mAppBarPager.animate().alpha(0.0f).setDuration(150L);
        this.mPager.animate().scaleX(0.7f).scaleY(0.7f).alpha(0.0f).setDuration(150L).withEndAction((Runnable)new Runnable() {
            @Override
            public void run() {
                LauncherActivity.this.mPager.setVisibility(View.GONE);
                LauncherActivity.this.mAppBarPager.setVisibility(View.GONE);
                LauncherActivity.this.mRevealOutlineProvider.setProgress(0.0f);
                LauncherActivity.this.mFloatingActionButton.invalidateOutline();
            }
        });
    }
    
    @Override
    public void update(final Observable observable, final Object o) {
        LauncherLog.d("LauncherActivity", "Received update on Licensing");
        if (o != null && o instanceof Intent) {
            LauncherActivity.isPremium = ((Intent)o).getBooleanExtra("isPremium", false);
            if (!this.mPreferences.getBoolean("hasShownMessage", false) && LauncherActivity.isPremium && !this.isFinishing()) {
                new AlertDialog.Builder((Context)this, R.style.DialogTheme).setTitle("Licensing successful!").setMessage("You successfully activated HomeUX premium. Have fun!").setPositiveButton(R.string.app_name, null).show();
            }
            final SharedPreferences.Editor edit = this.mPreferences.edit();
            edit.putBoolean("isLicensed", LauncherActivity.isPremium);
            edit.putBoolean("hasShownMessage", LauncherActivity.isPremium);
            edit.apply();
            this.stopService(this.mLicenseIntent);
        }
    }
    
    public void updateHolder() {
        this.mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.mHolder.showCard = this.mPreferences.getBoolean("hidecards", Const.Defaults.getBoolean("hidecards"));
        this.mHolder.pagerTransition = this.mPreferences.getInt("transformerINT", Const.Defaults.getInt("transformerINT"));
        this.mHolder.gridHeight = this.mPreferences.getInt("appheight", this.getResources().getInteger(R.integer.app_grid_height));
        this.mHolder.gridWidth = this.mPreferences.getInt("appwidth", this.getResources().getInteger(R.integer.app_grid_width));
        this.mHolder.isFirstStart = this.mPreferences.getBoolean("firstStart", Const.Defaults.getBoolean("firstStart"));
        this.mHolder.useDirectReveal = this.mPreferences.getBoolean("directReveal", Const.Defaults.getBoolean("directReveal"));
        final String string = this.mPreferences.getString("qa_fab_pkg", Const.Defaults.getString("qa_fab_pkg"));
        final String string2 = this.mPreferences.getString("qa_fab_cls", Const.Defaults.getString("qa_fab_cls"));
        if (!string.equals("") || !string2.equals("")) {
            this.mHolder.fabComponent = new ComponentName(string, string2);
        }
        else {
            this.mHolder.fabComponent = null;
        }
        this.mPreferences.edit().putBoolean("hidecards", false).apply();
    }
    
    public class NotificationReceiver extends BroadcastReceiver
    {
        public void onReceive(final Context context, final Intent intent) {
            final String[] stringArrayExtra = intent.getStringArrayExtra("notifications");
            LauncherActivity.this.mStatusBarNotifications.clear();
            if (stringArrayExtra == null) {
                LauncherActivity.this.refreshNotificationIcons();
                return;
            }
            LauncherActivity.this.mStatusBarNotificationCounts = intent.getIntArrayExtra("numbers");
            Collections.addAll(LauncherActivity.this.mStatusBarNotifications, stringArrayExtra);
            LauncherActivity.this.refreshNotificationIcons();
        }
    }
}
