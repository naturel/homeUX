// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.helpers;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.widget.Toast;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.Widget;
import android.view.ViewGroup;
import java.util.Collection;
import com.vanbo.homeux.dravite.newlayouttest.LauncherLog;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.ClickableAppWidgetHostView;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.Shortcut;
import android.appwidget.AppWidgetProviderInfo;
import android.os.Parcelable;
import android.content.ComponentName;
import android.content.Intent;
import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import java.util.TreeMap;
import java.util.List;
import android.support.v7.app.AppCompatActivity;
import android.appwidget.AppWidgetManager;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.ClickableAppWidgetHost;

public class AppWidgetContainer
{
    private static final int APPWIDGET_HOST_ID = 130;
    private static final int REQUEST_BIND_APPWIDGET = 7611;
    private static final int REQUEST_CREATE_APPWIDGET = 665;
    private static final int REQUEST_CREATE_SHORTCUT = 765;
    private static final int REQUEST_PICK_APPWIDGET = 664;
    private static final int REQUEST_PICK_SHORTCUT = 764;
    private static final String TAG = "AppWidgetContainer";
    public ClickableAppWidgetHost mAppWidgetHost;
    public final AppWidgetManager mAppWidgetManager;
    private AppCompatActivity mParentActivity;
    private final List<WidgetAndListener> mWaitToBind;
    private final TreeMap<Integer, View> widgetViews;
    
    public AppWidgetContainer(final Context context) {
        this.widgetViews = new TreeMap<Integer, View>();
        this.mWaitToBind = new ArrayList<WidgetAndListener>();
        this.mAppWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        this.mAppWidgetHost = new ClickableAppWidgetHost(context.getApplicationContext(), 130);
        if (context instanceof AppCompatActivity) {
            this.mParentActivity = (AppCompatActivity)context;
            return;
        }
        throw new IllegalArgumentException("This AppWidgetContainer needs an AppCompatActivity as its parent activity.");
    }
    
    private void addEmptyData(final Intent intent) {
        intent.putParcelableArrayListExtra("customInfo", new ArrayList());
        intent.putParcelableArrayListExtra("customExtras", new ArrayList());
    }
    
    private void addWidget(final ComponentName componentName, final OnWidgetCreatedListener onWidgetCreatedListener) {
        final int allocateAppWidgetId = this.mAppWidgetHost.allocateAppWidgetId();
        if (this.mAppWidgetManager.bindAppWidgetIdIfAllowed(allocateAppWidgetId, componentName)) {
            onWidgetCreatedListener.onWidgetCreated(this.createWidget(allocateAppWidgetId), allocateAppWidgetId);
            return;
        }
        final Intent intent = new Intent("android.appwidget.action.APPWIDGET_BIND");
        intent.putExtra("appWidgetId", allocateAppWidgetId);
        intent.putExtra("appWidgetProvider", (Parcelable)componentName);
        this.mParentActivity.startActivityForResult(intent, 7611);
    }
    
    private void configureShortcut(final Intent intent) {
        this.mParentActivity.startActivityForResult(intent, 765);
    }
    
    private boolean configureWidget(Intent intent, final OnWidgetCreatedListener onWidgetCreatedListener) {
        final int int1 = intent.getExtras().getInt("appWidgetId", -1);
        final AppWidgetProviderInfo appWidgetInfo = this.mAppWidgetManager.getAppWidgetInfo(int1);
        if (appWidgetInfo != null && appWidgetInfo.configure != null) {
            intent = new Intent("android.appwidget.action.APPWIDGET_CONFIGURE");
            intent.setComponent(appWidgetInfo.configure);
            intent.putExtra("appWidgetId", int1);
            this.mParentActivity.startActivityForResult(intent, 665);
            return true;
        }
        onWidgetCreatedListener.onWidgetCreated(this.createWidget(intent), int1);
        return false;
    }
    
    private Shortcut createShortcut(final Intent intent) {
        return new Shortcut(intent, (Context)this.mParentActivity);
    }
    
    private View createWidget(final Intent intent) {
        final int int1 = intent.getExtras().getInt("appWidgetId", -1);
        final AppWidgetProviderInfo appWidgetInfo = this.mAppWidgetManager.getAppWidgetInfo(int1);
        final ClickableAppWidgetHostView clickableAppWidgetHostView = (ClickableAppWidgetHostView)this.mAppWidgetHost.createView((Context)this.mParentActivity, int1, appWidgetInfo);
        clickableAppWidgetHostView.setAppWidget(int1, appWidgetInfo);
        return (View)clickableAppWidgetHostView;
    }
    
    private void runWidgetListener(final int n, final OnWidgetCreatedListener onWidgetCreatedListener) {
        this.mParentActivity.runOnUiThread((Runnable)new Runnable() {
            @Override
            public void run() {
                final View widget = AppWidgetContainer.this.createWidget(n);
                AppWidgetContainer.this.widgetViews.put(n, widget);
                LauncherLog.d("AppWidgetContainer", "Added widget " + n);
                onWidgetCreatedListener.onWidgetCreated(widget, n);
            }
        });
    }
    
    public View createWidget(final int n) {
        if (this.mAppWidgetHost == null) {
            this.mAppWidgetHost = new ClickableAppWidgetHost(this.mParentActivity.getApplicationContext(), 130);
        }
        final AppWidgetProviderInfo appWidgetInfo = this.mAppWidgetManager.getAppWidgetInfo(n);
        final ClickableAppWidgetHostView clickableAppWidgetHostView = (ClickableAppWidgetHostView)this.mAppWidgetHost.createView((Context)this.mParentActivity, n, appWidgetInfo);
        clickableAppWidgetHostView.setAppWidget(n, appWidgetInfo);
        return (View)clickableAppWidgetHostView;
    }
    
    public void onActivityResult(int requestCode, final int resultCode, final Intent intent, final OnWidgetCreatedListener onWidgetCreatedListener) {
        if (requestCode == REQUEST_BIND_APPWIDGET) {
            if (resultCode == Activity.RESULT_OK) {
                int widgetId = intent.getIntExtra("appWidgetId", -1);
                final ArrayList<WidgetAndListener> list = new ArrayList<WidgetAndListener>(mWaitToBind);
                if (list.size() >= 1) {
                    (list.get(0)).listener.onWidgetCreated(this.createWidget(widgetId), widgetId);
                    this.mWaitToBind.clear();
                    WidgetAndListener widgetAndListener;
                    for (int i = 1; i < list.size(); ++i) {
                        widgetAndListener = list.get(i);
                        this.addWidget(widgetAndListener.provider, widgetAndListener.listener);
                        LauncherLog.d("AppWidgetContainer", "onActivityResult: result: " + this.mWaitToBind.size());
                    }
                }
            }
            else {
                LauncherLog.w("AppWidgetContainer", "onActivityResult: couldn't add widgets");
            }
        }
        else if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PICK_APPWIDGET: {
                    this.configureWidget(intent, onWidgetCreatedListener);
                }
                case REQUEST_CREATE_APPWIDGET: {
                    onWidgetCreatedListener.onWidgetCreated(this.createWidget(intent), intent.getExtras().getInt("appWidgetId", -1));
                }
                case REQUEST_PICK_SHORTCUT: {
                    this.configureShortcut(intent);
                }
                case REQUEST_CREATE_SHORTCUT: {
                    onWidgetCreatedListener.onShortcutCreated(this.createShortcut(intent));
                }
                default: {}
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED && intent != null) {
            int widgetId = intent.getIntExtra("appWidgetId", -1);
            if (widgetId != -1) {
                this.mAppWidgetHost.deleteAppWidgetId(widgetId);
            }
        }
    }
    
    public void onStartActivity() {
        this.mAppWidgetHost.startListening();
    }
    
    public void onStopActivity() {
        this.mAppWidgetHost.stopListening();
    }
    
    public void removeWidget(final ClickableAppWidgetHostView clickableAppWidgetHostView) {
        this.mAppWidgetHost.deleteAppWidgetId(clickableAppWidgetHostView.getAppWidgetId());
        if (clickableAppWidgetHostView.getParent() != null) {
            ((ViewGroup)clickableAppWidgetHostView.getParent()).removeView((View)clickableAppWidgetHostView);
        }
    }
    
    public void restoreWidget(final Widget widget, final OnWidgetCreatedListener onWidgetCreatedListener) {
        if (this.widgetViews.containsKey(widget.widgetId)) {
            onWidgetCreatedListener.onWidgetCreated(this.widgetViews.get(widget.widgetId), widget.widgetId);
            return;
        }
        LauncherActivity.mStaticParallelThreadPool.enqueue(new Runnable() {
            @Override
            public void run() {
                if (AppWidgetContainer.this.mAppWidgetManager.getAppWidgetInfo(widget.widgetId) != null) {
                    AppWidgetContainer.this.runWidgetListener(widget.widgetId, onWidgetCreatedListener);
                }
                else {
                    if (widget.provider() == null) {
                        widget.widgetId = -1;
                        AppWidgetContainer.this.runWidgetListener(-1, onWidgetCreatedListener);
                        return;
                    }
                    final int allocateAppWidgetId = AppWidgetContainer.this.mAppWidgetHost.allocateAppWidgetId();
                    int n;
                    if (widget.provider() == null || AppWidgetContainer.this.mAppWidgetManager.bindAppWidgetIdIfAllowed(allocateAppWidgetId, widget.provider())) {
                        n = 1;
                    }
                    else {
                        n = 0;
                    }
                    if (n != 0) {
                        widget.widgetId = allocateAppWidgetId;
                        AppWidgetContainer.this.runWidgetListener(allocateAppWidgetId, onWidgetCreatedListener);
                        return;
                    }
                    AppWidgetContainer.this.mWaitToBind.add(new WidgetAndListener(widget.provider(), onWidgetCreatedListener));
                    if (AppWidgetContainer.this.mWaitToBind.size() == 1) {
                        final Intent intent = new Intent("android.appwidget.action.APPWIDGET_BIND");
                        intent.putExtra("appWidgetId", allocateAppWidgetId);
                        intent.putExtra("appWidgetProvider", (Parcelable)widget.provider());
                        AppWidgetContainer.this.mParentActivity.startActivityForResult(intent, 7611);
                    }
                }
            }
        });
    }
    
    public void selectShortcut() {
        final Intent intent = new Intent("android.intent.action.PICK_ACTIVITY");
        intent.putExtra("android.intent.extra.INTENT", (Parcelable)new Intent("android.intent.action.CREATE_SHORTCUT"));
        this.mParentActivity.startActivityForResult(intent, 764);
    }
    
    public void selectWidget() {
        final int allocateAppWidgetId = this.mAppWidgetHost.allocateAppWidgetId();
        final Intent intent = new Intent("android.appwidget.action.APPWIDGET_PICK");
        intent.putExtra("appWidgetId", allocateAppWidgetId);
        this.addEmptyData(intent);
        try {
            this.mParentActivity.startActivityForResult(intent, 664);
        }
        catch (ActivityNotFoundException ex) {
            Toast.makeText((Context)this.mParentActivity, (CharSequence)"No widget selection list found.", Toast.LENGTH_SHORT).show();
        }
    }
    
    public interface OnWidgetCreatedListener
    {
        void onShortcutCreated(final Shortcut p0);
        
        void onWidgetCreated(final View p0, final int p1);
    }
    
    static class WidgetAndListener
    {
        final OnWidgetCreatedListener listener;
        final ComponentName provider;
        
        WidgetAndListener(final ComponentName provider, final OnWidgetCreatedListener listener) {
            this.provider = provider;
            this.listener = listener;
        }
    }
}
