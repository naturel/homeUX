// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_adapters;

import android.view.View;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;
import android.graphics.drawable.Drawable;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.FolderStructure;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import android.graphics.Rect;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import com.dravite.homeux.R;


public class FolderDropAdapter extends RecyclerView.Adapter<FolderDropAdapter.FolderDropViewHolder>
{
    private int hovered;
    private int mAllFolderIndex;
    private Context mContext;
    private Rect mParentRect;
    
    public FolderDropAdapter(final Context mContext) {
        this.mParentRect = new Rect();
        this.mAllFolderIndex = 0;
        this.mContext = mContext;
        this.mAllFolderIndex = LauncherActivity.mFolderStructure.getFolderIndexOfName("All");
    }
    
    public int getHovered() {
        if (this.hovered < this.mAllFolderIndex) {
            return this.hovered;
        }
        return this.hovered + 1;
    }
    
    @Override
    public int getItemCount() {
        return LauncherActivity.mFolderStructure.folders.size() - 1;
    }
    
    public void hover(final RecyclerView recyclerView, final float n, final float n2) {
        recyclerView.getGlobalVisibleRect(this.mParentRect);
        final int childAdapterPosition = recyclerView.getChildAdapterPosition(recyclerView.findChildViewUnder(n - this.mParentRect.left, n2 - this.mParentRect.top));
        if (childAdapterPosition != this.hovered) {
            this.hovered = childAdapterPosition;
            ((RecyclerView.Adapter)this).notifyDataSetChanged();
        }
    }
    
    public void onBindViewHolder(final FolderDropViewHolder folderDropViewHolder, final int n) {
        final List<FolderStructure.Folder> folders = LauncherActivity.mFolderStructure.folders;
        int n2;
        if (n < this.mAllFolderIndex) {
            n2 = n;
        }
        else {
            n2 = n + 1;
        }
        final FolderStructure.Folder folder = folders.get(n2);
        if (n == this.hovered) {
            folderDropViewHolder.itemTextView.setBackgroundColor(857839347);
        }
        else {
            folderDropViewHolder.itemTextView.setBackgroundColor(16777215);
        }
        folderDropViewHolder.itemTextView.setText((CharSequence)folder.folderName);
        final Drawable drawable = this.mContext.getDrawable(this.mContext.getResources().getIdentifier(folder.folderIconRes, "drawable", this.mContext.getPackageName()));
        if (drawable != null) {
            drawable.setTint(1627389952);
        }
        if (n == ((RecyclerView.ViewHolder)folderDropViewHolder).getAdapterPosition()) {
            folderDropViewHolder.itemTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, (Drawable)null, (Drawable)null, (Drawable)null);
        }
    }
    
    public FolderDropViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int n) {
        return new FolderDropViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.folder_drop_icon, viewGroup, false));
    }
    
    public void resetHovered() {
        this.hovered = -1;
    }
    
    public static class FolderDropViewHolder extends RecyclerView.ViewHolder
    {
        TextView itemTextView;
        
        public FolderDropViewHolder(final View view) {
            super(view);
            this.itemTextView = (TextView)view;
        }
    }
}
