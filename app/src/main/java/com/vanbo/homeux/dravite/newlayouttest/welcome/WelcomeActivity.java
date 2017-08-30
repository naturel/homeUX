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

public class WelcomeActivity extends AppCompatActivity
{
    public static final int REQUEST_FIRST_START = 463;
    
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_welcome);
        this.getWindow().setNavigationBarColor(16746133);
        this.getWindow().setStatusBarColor(16746133);
        ((Button)this.findViewById(R.id.skip)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                final Intent intent = new Intent((Context)WelcomeActivity.this, (Class)LauncherActivity.class);
                intent.addFlags(268435456);
                WelcomeActivity.this.startActivity(intent);
                WelcomeActivity.this.overridePendingTransition(2131034126, 2131034127);
                final SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences((Context)WelcomeActivity.this).edit();
                edit.putBoolean("firstStart", false);
                edit.apply();
            }
        });
        ((Button)this.findViewById(R.id.start)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                WelcomeActivity.this.startActivityForResult(new Intent((Context)WelcomeActivity.this, (Class)WelcomeActivityWidgetsInfo.class), 463);
                WelcomeActivity.this.overridePendingTransition(2131034126, 2131034127);
            }
        });
    }
}
