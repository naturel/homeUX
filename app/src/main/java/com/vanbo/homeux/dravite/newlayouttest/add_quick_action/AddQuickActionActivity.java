// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.add_quick_action;

import android.graphics.drawable.Drawable;
import com.vanbo.homeux.dravite.newlayouttest.general_adapters.AllDrawableAdapter;
import com.vanbo.homeux.dravite.newlayouttest.views.viewcomponents.IconListDecoration;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.os.Parcelable;
import android.content.Intent;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.Application;
import android.support.v7.app.AppCompatActivity;
import com.dravite.homeux.R;

public class AddQuickActionActivity extends AppCompatActivity
{
    private Application mData;
    private int mQaIndex;
    
    private void returnData(final Application application, final int n, final int n2) {
        final Intent intent = new Intent();
        intent.putExtra("app", (Parcelable)application);
        intent.putExtra("index", n);
        intent.putExtra("icon", n2);
        this.setResult(-1, intent);
        this.finish();
    }
    
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_add_qa);
        this.mQaIndex = this.getIntent().getIntExtra("index", 0);
        this.mData = (Application)this.getIntent().getParcelableExtra("data");
        this.setSupportActionBar((Toolbar)this.findViewById(R.id.toolbar));
        final RecyclerView recyclerView = (RecyclerView)this.findViewById(R.id.icon_list);
        recyclerView.setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager((Context)this, 6));
        recyclerView.addItemDecoration((RecyclerView.ItemDecoration)new IconListDecoration(LauncherUtils.dpToPx(8.0f, (Context)this)));
        recyclerView.setAdapter((RecyclerView.Adapter)new AllDrawableAdapter((Context)this, recyclerView, (AllDrawableAdapter.OnItemClickListener)new AllDrawableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final Drawable drawable, final int n, final int n2) {
                AddQuickActionActivity.this.returnData(AddQuickActionActivity.this.mData, AddQuickActionActivity.this.mQaIndex, n2);
            }
        }));
    }
}
