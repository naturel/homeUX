// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures;

import android.content.ComponentName;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.JsonHelper;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.FileManager;
import java.util.Comparator;
import java.util.Collections;
import android.os.Process;
import java.util.Collection;
import java.util.Iterator;
import android.graphics.BitmapFactory;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import java.util.ArrayList;
import android.content.pm.LauncherApps;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.Application;
import java.util.TreeMap;
import android.content.Context;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.helpers.AppLauncherActivityInfoComparator;
import android.content.pm.LauncherActivityInfo;
import java.util.List;
import com.dravite.homeux.R;


public class DrawerTree
{
    private static final String TAG = "DrawerTree";
    private List<LauncherActivityInfo> infoList;
    public final AppLauncherActivityInfoComparator mComparator;
    private final Context mContext;
    private final TreeMap<String, Application> mFullAppList;
    private final LauncherApps mLauncherApps;
    private final TreeMap<String, List<LoadedListener>> mPendingListeners;
    
    public DrawerTree(Context context) {
        this.mFullAppList = new TreeMap<String, Application>();
        this.mPendingListeners = new TreeMap<String, List<LoadedListener>>();
        this.mLauncherApps = (LauncherApps)context.getSystemService(Context.LAUNCHER_APPS_SERVICE);
        this.mComparator = new AppLauncherActivityInfoComparator(context);
        this.mContext = context;
    }
    
    private void doWithApplication(final String s, final LauncherActivityInfo launcherActivityInfo, final LoadedListener loadedListener) {
        if (this.mFullAppList.containsKey(s)) {
            loadedListener.onApplicationLoadingFinished(this.mFullAppList.get(s));
            return;
        }
        if (this.mPendingListeners.containsKey(s)) {
            this.mPendingListeners.get(s).add(loadedListener);
        }
        else {
            final ArrayList<LoadedListener> list = new ArrayList<LoadedListener>();
            list.add(loadedListener);
            this.mPendingListeners.put(s, list);
        }
        this.loadApp(launcherActivityInfo);
    }
    
    private void loadApp(final LauncherActivityInfo launcherActivityInfo) {
        LauncherActivity.mStaticParallelThreadPool.enqueue(new Runnable() {
            @Override
            public void run() {
                DrawerTree.this.loadAppSync(launcherActivityInfo);
            }
        });
    }
    
    private void loadAppSync(final LauncherActivityInfo launcherActivityInfo) {
        final Application application = new Application(launcherActivityInfo);
        final String string = application.loadLabel(mContext) + ":" + launcherActivityInfo.getComponentName().toString();
        application.icon = application.loadIcon(mContext, launcherActivityInfo);
        if (application.icon == null || application.icon.getHeight() < 0 || application.icon.getWidth() < 0) {
            application.icon = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
        }
        mFullAppList.put(string, application);
        if (mPendingListeners.get(string) != null) {
            final Iterator<LoadedListener> iterator = mPendingListeners.get(string).iterator();
            while (iterator.hasNext()) {
                ((LauncherActivity)this.mContext).runOnUiThread((Runnable)new Runnable() {
                    final /* synthetic */ LoadedListener vallistener = iterator.next();
                    
                    @Override
                    public void run() {
                        this.vallistener.onApplicationLoadingFinished(application);
                    }
                });
            }
            this.mPendingListeners.get(string).clear();
            this.mPendingListeners.remove(string);
        }
    }
    
    private void loadApps() {
        loadApps(infoList);
    }
    
    private void loadApps(final List<LauncherActivityInfo> list) {
        final Iterator<LauncherActivityInfo> iterator = list.iterator();
        while (iterator.hasNext()) {
            loadApp(iterator.next());
        }
    }
    
    public void add(final String s) {
        final List activityList = this.mLauncherApps.getActivityList(s, Process.myUserHandle());
        Collections.sort(this.infoList = (List<LauncherActivityInfo>)this.mLauncherApps.getActivityList((String)null, Process.myUserHandle()), this.mComparator);
        this.loadApps(activityList);
    }
    
    public void addAll(final String... array) {
        final ArrayList<LauncherActivityInfo> list = new ArrayList<LauncherActivityInfo>();
        for (int length = array.length, i = 0; i < length; ++i) {
            list.addAll(this.mLauncherApps.getActivityList(array[i], Process.myUserHandle()));
        }
        Collections.sort(this.infoList = (List<LauncherActivityInfo>)this.mLauncherApps.getActivityList((String)null, Process.myUserHandle()), this.mComparator);
        this.loadApps((List<LauncherActivityInfo>)list);
    }
    
    public void change(final String s) {
        final List activityList = this.mLauncherApps.getActivityList(s, Process.myUserHandle());
        this.infoList = (List<LauncherActivityInfo>)this.mLauncherApps.getActivityList((String)null, Process.myUserHandle());
        this.mComparator.refresh();
        Collections.sort(this.infoList, this.mComparator);
        this.loadApps(activityList);
    }
    
    public void changeLabel(final String s, final String s2, final String s3) {
        this.mFullAppList.put(s3 + ":" + s2, this.mFullAppList.remove(s + ":" + s2));
        this.mComparator.refresh();
        Collections.sort(this.infoList, this.mComparator);
    }
    
    public void doWithApplicatioByLabel(final String s, final LauncherActivityInfo launcherActivityInfo, final LoadedListener loadedListener) {
        this.doWithApplication(s + ":" + launcherActivityInfo.getComponentName().toString(), launcherActivityInfo, loadedListener);
    }
    
    public void doWithApplication(final int n, final LoadedListener loadedListener) {
        this.doWithApplication(this.infoList.get(n), loadedListener);
    }
    
    public void doWithApplication(final LauncherActivityInfo launcherActivityInfo, final LoadedListener loadedListener) {
        String s = launcherActivityInfo.getLabel().toString();
        if (FileManager.fileExists(this.mContext, "/apps/" + launcherActivityInfo.getComponentName().getPackageName(), launcherActivityInfo.getComponentName().getClassName())) {
            s = FileManager.readTextFile(this.mContext, "/apps/" + launcherActivityInfo.getComponentName().getPackageName(), launcherActivityInfo.getComponentName().getClassName());
        }
        this.doWithApplication(s + ":" + launcherActivityInfo.getComponentName().toString(), launcherActivityInfo, loadedListener);
    }
    
    public void fullReload() {
        mFullAppList.clear();
        mPendingListeners.clear();
        if (infoList != null) {
            infoList.clear();
        }
        infoList = mLauncherApps.getActivityList(null, Process.myUserHandle());
        mComparator.refresh();
        Collections.sort(infoList, mComparator);
        loadApps();
    }
    
    public int getApplicationCount() {
        return this.infoList.size();
    }
    
    public List<Application> getApplicationsContaining(final String s) {
        ArrayList<Application> list;
        if (s.equals("")) {
            list = new ArrayList<Application>();
        }
        else {
            final ArrayList<Application> list2 = new ArrayList<Application>();
            final Iterator<Application> iterator = this.mFullAppList.values().iterator();
            while (true) {
                list = list2;
                if (!iterator.hasNext()) {
                    break;
                }
                final Application application = iterator.next();
                if (!application.loadLabel(this.mContext).toLowerCase().contains(s.toLowerCase())) {
                    continue;
                }
                list2.add(application);
            }
        }
        return list;
    }
    
    public List<Application> getApplicationsStartingWith(final String s) {
        ArrayList<Application> list;
        if (s.equals("")) {
            list = new ArrayList<Application>();
        }
        else {
            final ArrayList<Application> list2 = new ArrayList<Application>();
            final Iterator<Application> iterator = this.mFullAppList.values().iterator();
            while (true) {
                list = list2;
                if (!iterator.hasNext()) {
                    break;
                }
                final Application application = iterator.next();
                if (!application.loadLabel(this.mContext).toLowerCase().startsWith(s.toLowerCase())) {
                    continue;
                }
                list2.add(application);
            }
        }
        return list;
    }
    
    public List<LauncherActivityInfo> getAppsWithoutHidden() {
        ArrayList<LauncherActivityInfo> list = new ArrayList<LauncherActivityInfo>(infoList);
        List<ComponentName> loadHiddenAppList = JsonHelper.loadHiddenAppList(mContext);
        int n;
        for (int i = 0; i < list.size(); i = n + 1) {
            n = i;
            if (loadHiddenAppList.contains(list.get(i).getComponentName())) {
                list.remove(i);
                n = i - 1;
            }
        }
        return list;
    }
    
    public void remove(final String s) {
        final ArrayList<String> list = new ArrayList<String>();
        for (final String s2 : this.mFullAppList.keySet()) {
            if (s2.split(":")[1].split("/")[0].endsWith(s)) {
                list.add(s2);
            }
        }
        final Iterator<String> iterator2 = list.iterator();
        while (iterator2.hasNext()) {
            this.mFullAppList.remove(iterator2.next());
        }
        this.infoList.clear();
        Collections.sort(this.infoList = (List<LauncherActivityInfo>)this.mLauncherApps.getActivityList((String)null, Process.myUserHandle()), this.mComparator);
    }
    
    public void removeAll(final String... array) {
        for (int length = array.length, i = 0; i < length; ++i) {
            this.remove(array[i]);
        }
    }
    
    public void removeAppsFromOtherFolder(final List<LauncherActivityInfo> list, final FolderStructure folderStructure) {
    }
    
    public interface LoadedListener
    {
        void onApplicationLoadingFinished(final Application p0);
    }
}
