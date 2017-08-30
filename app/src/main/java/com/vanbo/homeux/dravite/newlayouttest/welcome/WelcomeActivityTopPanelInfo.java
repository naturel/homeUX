// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.welcome;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.content.Context;
import android.content.Intent;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.dravite.homeux.R;

public class WelcomeActivityTopPanelInfo extends AppCompatActivity
{
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.welcome_top_panel_info);
        this.getWindow().setNavigationBarColor(8708190);
        this.getWindow().setStatusBarColor(8708190);
        ((Button)this.findViewById(R.id.next)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                final Intent intent = new Intent((Context)WelcomeActivityTopPanelInfo.this, (Class)LauncherActivity.class);
                intent.addFlags(268435456);
                WelcomeActivityTopPanelInfo.this.startActivity(intent);
                WelcomeActivityTopPanelInfo.this.overridePendingTransition(2131034126, 2131034127);
                final SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences((Context)WelcomeActivityTopPanelInfo.this).edit();
                edit.putBoolean("firstStart", false);
                edit.apply();
            }
        });
    }
}
