// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.welcome;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.dravite.homeux.R;

public class WelcomeActivityWidgetsInfo extends AppCompatActivity
{
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.welcome_widgets_info);
        this.getWindow().setNavigationBarColor(13070788);
        this.getWindow().setStatusBarColor(13070788);
        ((Button)this.findViewById(R.id.next)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                WelcomeActivityWidgetsInfo.this.startActivity(new Intent((Context)WelcomeActivityWidgetsInfo.this, (Class)WelcomeActivityQuickApps.class));
                WelcomeActivityWidgetsInfo.this.overridePendingTransition(2131034126, 2131034127);
            }
        });
    }
}
