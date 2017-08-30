// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.app_editor;

import android.widget.TextView;
import android.view.ViewGroup;
import android.view.View;
import android.view.View.OnClickListener;
import android.graphics.drawable.Drawable;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import java.util.List;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.IconPackManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import com.dravite.homeux.R;


public class AppEditorIconPackActivity extends AppCompatActivity
{
    @Override
    protected void onActivityResult(final int n, final int n2, final Intent intent) {
        if (n2 == -1 && n == 4086) {
            if (this.getParent() == null) {
                this.setResult(-1, intent);
            }
            this.finish();
        }
        super.onActivityResult(n, n2, intent);
    }
    
    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_app_editor_icon_pack);
        final Toolbar supportActionBar = (Toolbar)this.findViewById(R.id.toolbar);
        this.setSupportActionBar(supportActionBar);
        supportActionBar.setBackgroundColor(ContextCompat.getColor((Context)this, R.color.colorPrimary));
        this.getWindow().setStatusBarColor(ContextCompat.getColor((Context)this, R.color.colorPrimaryDark));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final RecyclerView recyclerView = (RecyclerView)this.findViewById(R.id.icon_pack_list);
        recyclerView.setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager((Context)this, 1));
        recyclerView.setAdapter((RecyclerView.Adapter)new IconPackAdapter((Context)this, IconPackManager.getAllThemes((Context)this, false)));
    }
    
    @Override
    protected void onSaveInstanceState(final Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }
    
    public static class IconPackAdapter extends RecyclerView.Adapter<IconPackAdapter.IconPackViewHolder>
    {
        private Context mContext;
        private List<IconPackManager.Theme> mIconPacks;
        
        public IconPackAdapter(final Context mContext, final List<IconPackManager.Theme> mIconPacks) {
            this.mContext = mContext;
            this.mIconPacks = mIconPacks;
        }
        
        @Override
        public int getItemCount() {
            return this.mIconPacks.size();
        }
        
        public void onBindViewHolder(final IconPackViewHolder iconPackViewHolder, int dpToPx) {
            final IconPackManager.Theme theme = this.mIconPacks.get(dpToPx);
            iconPackViewHolder.text.setText((CharSequence)theme.label);
            dpToPx = LauncherUtils.dpToPx(40.0f, iconPackViewHolder.itemView.getContext());
            theme.icon.setBounds(0, 0, dpToPx, dpToPx);
            iconPackViewHolder.text.setCompoundDrawablesRelative(theme.icon, (Drawable)null, (Drawable)null, (Drawable)null);
            iconPackViewHolder.text.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View view) {
                    final Intent intent = new Intent(IconPackAdapter.this.mContext, (Class)AppEditorIconSelectActivity.class);
                    intent.putExtra("iconPack", theme.packageName);
                    ((AppEditorIconPackActivity)IconPackAdapter.this.mContext).startActivityForResult(intent, 4086);
                }
            });
        }
        
        public IconPackViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int n) {
            return new IconPackViewHolder(View.inflate(this.mContext, R.layout.folder_drop_icon, (ViewGroup)null));
        }
        
        public static class IconPackViewHolder extends RecyclerView.ViewHolder
        {
            public TextView text;
            
            public IconPackViewHolder(final View view) {
                super(view);
                this.text = (TextView)this.itemView;
            }
        }
    }
}
