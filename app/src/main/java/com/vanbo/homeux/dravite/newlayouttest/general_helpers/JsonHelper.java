// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_helpers;

import java.io.Serializable;
import android.content.Intent;
import com.vanbo.homeux.dravite.newlayouttest.views.QuickAppIcon;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import android.content.ComponentName;
import java.util.List;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Iterator;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.QuickAction;
import com.vanbo.homeux.dravite.newlayouttest.views.QuickAppBar;
import java.util.ArrayList;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.DrawerObject;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.Application;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.FolderStructure;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.DrawerTree;
import android.content.Context;
import com.dravite.homeux.R;

public class JsonHelper
{
    static FolderStructure createNew(final Context context, final DrawerTree drawerTree, final PreferenceHolder preferenceHolder) {
        final FolderStructure folderStructure = new FolderStructure();
        final FolderStructure.Folder folder = new FolderStructure.Folder();
        folder.folderName = "All";
        folder.isAllFolder = true;
        folder.accentColor = 1085881;
        folder.folderIconRes = "ic_all";
        folder.headerImage = LauncherUtils.drawableToBitmap(context.getDrawable(R.drawable.welcome_header_small));
        for (int n = (int)Math.ceil(drawerTree.getApplicationCount() / preferenceHolder.gridSize()), i = 0; i < n; ++i) {
            final FolderStructure.Page page = new FolderStructure.Page();
            for (int n2 = 0; n2 < preferenceHolder.gridSize() && preferenceHolder.gridSize() * i + n2 < drawerTree.getApplicationCount(); ++n2) {
                drawerTree.doWithApplication(preferenceHolder.gridSize() * i + n2, new DrawerTree.LoadedListener() {
                    @Override
                    public void onApplicationLoadingFinished(final Application application) {
                        page.add(application);
                    }
                });
            }
            folder.add(page);
        }
        folderStructure.add(folder);
        saveFolderStructure(context, folderStructure);
        return folderStructure;
    }
    
    private static ArrayList fillDefaultQuickActions() {
        return new ArrayList();
    }
    
    public static void inflateQuickApps(final Context context, final QuickAppBar quickAppBar) {
        for (final Object next : loadQuickApps(context)) {
            if (!(next instanceof QuickAction)) {
                throw new RuntimeException("Loaded QuickAction is of a wrong format.");
            }
            quickAppBar.add((QuickAction)next);
        }
    }
    
    public static FolderStructure loadFolderStructure(final Context context, final DrawerTree drawerTree, final PreferenceHolder preferenceHolder) {
        File file = new File(context.getApplicationInfo().dataDir + "/somedata.json");
        if (file.exists()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);
                FolderStructure folderStructure = objectMapper.readValue(file, FolderStructure.class);
                Iterator<FolderStructure.Folder> iterator = folderStructure.folders.iterator();
                while (true) {
                    final FolderStructure new1 = folderStructure;
                    if (!iterator.hasNext()) {
                        return new1;
                    }
                    final FolderStructure.Folder folder = iterator.next();
                    folder.loadImage(context);
                    if (folder.folderName.equals("All")) {
                        continue;
                    }
                    folderStructure.addFolderAssignments(folder);
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return createNew(context, drawerTree, preferenceHolder);
    }
    
    public static List<ComponentName> loadHiddenAppList(Context context) {
        final File file = new File(context.getApplicationInfo().dataDir + "/hiddenApps.json");
        List<ComponentName> cn = new ArrayList<ComponentName>();
        if (file.exists()) {
            try {
                final ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);
                for (final Object next : objectMapper.readValue(file, ArrayList.class)) {
                    cn.add(new ComponentName(((SavableComponent)next).pkg, ((SavableComponent)next).cls));
                }
            }
            catch (IOException ex) {
                ExceptionLog.w(ex);
            }
        }
        return cn;
    }
    
    public static ArrayList loadQuickApps(final Context context) {
        final File file = new File(context.getApplicationInfo().dataDir + "/quickApps.json");
        if (file.exists()) {
            try {
                final ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);
                return objectMapper.readValue(file, ArrayList.class);
            }
            catch (IOException ex) {
                ExceptionLog.w(ex);
            }
        }
        return fillDefaultQuickActions();
    }
    
    public static void saveFolderStructure(final Context context, final FolderStructure folderStructure) {
        //try {
            final Iterator<FolderStructure.Folder> iterator = folderStructure.folders.iterator();
            while (iterator.hasNext()) {
                LauncherActivity.mStaticParallelThreadPool.enqueue(new Runnable() {
                    final /* synthetic */ FolderStructure.Folder valfolder = (FolderStructure.Folder)iterator.next();
                    
                    @Override
                    public void run() {
                        this.valfolder.saveImage(context, false);
                    }
                });
            }
        //}
        //catch (IOException ex) {
        //    ex.printStackTrace();
        //    return;
        //}

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);
        try {
            objectMapper.writeValue(new File(context.getApplicationInfo().dataDir + "/somedata.json"), folderStructure);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void saveHiddenAppList(final Context context, final List<ComponentName> list) {
        ArrayList<SavableComponent> list2;
        //try {
            list2 = new ArrayList<SavableComponent>();
            for (final ComponentName componentName : list) {
                final SavableComponent savableComponent = new SavableComponent();
                savableComponent.pkg = componentName.getPackageName();
                savableComponent.cls = componentName.getClassName();
                list2.add(savableComponent);
            }
        //}
        //catch (IOException ex) {
        //    ExceptionLog.w(ex);
        //    return;
        //}
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);
        try {
            objectMapper.writeValue(new File(context.getApplicationInfo().dataDir + "/hiddenApps.json"), list2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void saveQuickApps(final Context context, final QuickAppBar quickAppBar) {
        final ArrayList<QuickAction> list = new ArrayList<QuickAction>();
        for (int i = 0; i < 5; ++i) {
            final QuickAppIcon quickAppIcon = (QuickAppIcon)quickAppBar.getChildAtPosition(i);
            if (quickAppIcon != null) {
                final ComponentName component = ((Intent)quickAppIcon.getTag()).getComponent();
                list.add(new QuickAction(context.getResources().getResourceName(quickAppIcon.getIconRes()), component.getPackageName(), component.getClassName(), i));
            }
        }
        saveQuickApps(context, list);
    }
    
    public static void saveQuickApps(final Context context, final ArrayList<QuickAction> list) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);
            objectMapper.writeValue(new File(context.getApplicationInfo().dataDir + "/quickApps.json"), list);
        }
        catch (IOException ex) {
            ExceptionLog.w(ex);
        }
    }
    
    public static class SavableComponent implements Serializable
    {
        public String cls;
        public String pkg;
    }
}
