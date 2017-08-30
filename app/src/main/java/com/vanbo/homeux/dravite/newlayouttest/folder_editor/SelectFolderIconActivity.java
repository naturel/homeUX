// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.folder_editor;

import android.graphics.drawable.Drawable;
import com.vanbo.homeux.dravite.newlayouttest.general_adapters.AllDrawableAdapter;
import com.vanbo.homeux.dravite.newlayouttest.views.viewcomponents.IconListDecoration;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.dravite.homeux.R;

public class SelectFolderIconActivity extends AppCompatActivity
{
    private void returnData(final int n) {
        final String resourceName = this.getResources().getResourceName(n);
        final Intent intent = new Intent();
        intent.putExtra("iconRes", resourceName);
        this.setResult(-1, intent);
        this.finish();
    }
    
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_edit_folder_icon);
        this.setSupportActionBar((Toolbar)this.findViewById(R.id.toolbar));
        final RecyclerView recyclerView = (RecyclerView)this.findViewById(R.id.icon_list);
        recyclerView.setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager((Context)this, 6));
        recyclerView.addItemDecoration((RecyclerView.ItemDecoration)new IconListDecoration(LauncherUtils.dpToPx(8.0f, (Context)this)));
        recyclerView.setAdapter((RecyclerView.Adapter)new AllDrawableAdapter((Context)this, recyclerView, (AllDrawableAdapter.OnItemClickListener)new AllDrawableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final Drawable drawable, final int n, final int n2) {
                SelectFolderIconActivity.this.returnData(n2);
            }
        }));
    }
}
