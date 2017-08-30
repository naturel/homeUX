// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.welcome;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.dravite.homeux.R;

public class WelcomeActivityFoldersInfo extends AppCompatActivity
{
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.welcome_folders_info);
        this.getWindow().setNavigationBarColor(15108398);
        this.getWindow().setStatusBarColor(15108398);
        (this.findViewById(R.id.next)).setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                WelcomeActivityFoldersInfo.this.startActivityForResult(new Intent(WelcomeActivityFoldersInfo.this, (Class)WelcomeActivityFolders.class), 463);
                WelcomeActivityFoldersInfo.this.overridePendingTransition(2131034126, 2131034127);
            }
        });
    }
}
