// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.welcome;

import java.util.Iterator;
import android.widget.Button;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.content.ComponentName;
import android.app.Activity;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import com.vanbo.homeux.dravite.newlayouttest.folder_editor.SelectFolderIconActivity;
import android.content.pm.LauncherActivityInfo;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog;
import com.vanbo.homeux.dravite.newlayouttest.general_adapters.AppSelectAdapter;
import android.view.View;
import android.content.Context;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.JsonHelper;
import android.widget.LinearLayout;
import android.widget.ImageButton;
import android.content.Intent;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.QuickAction;
import java.util.ArrayList;
//import android.view.View.OnClickListener;
import android.support.v7.app.AppCompatActivity;
import com.dravite.homeux.R;

public class WelcomeActivityQuickApps extends AppCompatActivity implements View.OnClickListener
{
    public static final int REQUEST_CHANGE_QA_ICON = 932;
    private int mSelectedButton;
    ArrayList<QuickAction> quickActions;
    
    public WelcomeActivityQuickApps() {
        this.mSelectedButton = -1;
        this.quickActions = new ArrayList<QuickAction>();
    }
    
    protected void onActivityResult(final int n, final int n2, final Intent intent) {
        if (n2 == -1) {
            switch (n) {
                case 932: {
                    final String stringExtra = intent.getStringExtra("iconRes");
                    ((ImageButton)((LinearLayout)this.findViewById(R.id.quickAppLayout)).getChildAt(this.mSelectedButton)).setImageResource(this.getResources().getIdentifier(stringExtra, "drawable", this.getApplicationContext().getPackageName()));
                    for (int i = 0; i < this.quickActions.size(); ++i) {
                        if (this.quickActions.get(i).qaIndex == this.mSelectedButton) {
                            this.quickActions.get(i).iconRes = stringExtra;
                        }
                    }
                    JsonHelper.saveQuickApps((Context)this, this.quickActions);
                    break;
                }
            }
        }
        super.onActivityResult(n, n2, intent);
    }
    
    public void onClick(final View view) {
        this.mSelectedButton = ((LinearLayout)this.findViewById(R.id.quickAppLayout)).indexOfChild(view);
        final AppSelectAdapter appSelectAdapter = new AppSelectAdapter((Context)this);
        new AlertDialog.Builder((Context)this, R.style.DialogTheme).setNegativeButton(R.string.app_name, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, final int n) {
            }
        }).setTitle("Select an App").setAdapter((ListAdapter)appSelectAdapter, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, int i) {
                final ComponentName componentName = appSelectAdapter.mInfos.get(i).getComponentName();
                final QuickAction quickAction = new QuickAction("", componentName.getPackageName(), componentName.getClassName(), WelcomeActivityQuickApps.this.mSelectedButton);
                boolean b = false;
                for (i = 0; i < WelcomeActivityQuickApps.this.quickActions.size(); ++i) {
                    if (WelcomeActivityQuickApps.this.quickActions.get(i).qaIndex == WelcomeActivityQuickApps.this.mSelectedButton) {
                        WelcomeActivityQuickApps.this.quickActions.set(i, quickAction);
                        b = true;
                    }
                }
                if (!b) {
                    WelcomeActivityQuickApps.this.quickActions.add(quickAction);
                }
                LauncherUtils.startActivityForResult(WelcomeActivityQuickApps.this, view, new Intent((Context)WelcomeActivityQuickApps.this, (Class)SelectFolderIconActivity.class), 932);
            }
        }).show();
    }
    
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.welcome_qa);
        this.getWindow().setNavigationBarColor(24576);
        this.getWindow().setStatusBarColor(24576);
        final LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.quickAppLayout);
        for (int i = 0; i < linearLayout.getChildCount(); ++i) {
            linearLayout.getChildAt(i).setOnClickListener(this);
        }
        final ArrayList<QuickAction> loadQuickApps = JsonHelper.loadQuickApps(this);
        this.quickActions.clear();
        for (final QuickAction quickAction : loadQuickApps) {
            this.quickActions.add(quickAction);
            ((ImageButton)linearLayout.getChildAt(quickAction.qaIndex)).setImageResource(this.getResources().getIdentifier(quickAction.iconRes, "drawable", this.getApplicationContext().getPackageName()));
        }
        for (int j = 0; j < linearLayout.getChildCount(); ++j) {
            final ImageButton imageButton = (ImageButton)linearLayout.getChildAt(j);
            if (imageButton.getDrawable() == null) {
                imageButton.setImageResource(R.drawable.ic_add_black_24dp);
            }
        }
        ((Button)this.findViewById(R.id.next)).setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                WelcomeActivityQuickApps.this.startActivityForResult(new Intent(WelcomeActivityQuickApps.this, (Class)WelcomeActivityFoldersInfo.class), 463);
                WelcomeActivityQuickApps.this.overridePendingTransition(2131034126, 2131034127);
            }
        });
    }
}
