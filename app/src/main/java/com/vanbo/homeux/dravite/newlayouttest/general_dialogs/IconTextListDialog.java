// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_dialogs;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.app.AlertDialog;
import com.dravite.homeux.R;

public class IconTextListDialog
{
    private Context mContext;
    private AlertDialog.Builder mDialogBuilder;
    private IconTextAdapter mIconTextAdapter;
    private ItemModifier mItemModifier;
    
    public IconTextListDialog(final Context mContext, final int n) {
        this.mContext = mContext;
        (this.mDialogBuilder = new AlertDialog.Builder(mContext, n)).setNegativeButton(R.string.app_name, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, final int n) {
                dialogInterface.dismiss();
            }
        });
        this.mIconTextAdapter = new IconTextAdapter();
    }
    
    public IconTextListDialog(final Context context, final int n, final int title) {
        this(context, n);
        this.mDialogBuilder.setTitle(title);
    }
    
    public IconTextListDialog doOnSubmit(final OnDialogSubmitListener onDialogSubmitListener) {
        this.mDialogBuilder.setAdapter((ListAdapter)this.mIconTextAdapter, (DialogInterface.OnClickListener)new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialogInterface, final int n) {
                onDialogSubmitListener.onSubmit(n);
            }
        });
        return this;
    }
    
    public IconTextListDialog setItemModifier(final ItemModifier mItemModifier) {
        this.mItemModifier = mItemModifier;
        this.mIconTextAdapter.notifyDataSetChanged();
        return this;
    }
    
    public void show() {
        this.mDialogBuilder.show();
    }
    
    private class IconTextAdapter extends BaseAdapter
    {
        public int getCount() {
            return IconTextListDialog.this.mItemModifier.getCount();
        }
        
        public Object getItem(final int n) {
            return null;
        }
        
        public long getItemId(final int n) {
            return 0L;
        }
        
        public View getView(final int n, View inflate, final ViewGroup viewGroup) {
            if (inflate == null) {
                inflate = View.inflate(IconTextListDialog.this.mContext, android.R.layout.simple_list_item_1, (ViewGroup)null);
            }
            final TextView textView = (TextView)inflate.findViewById(android.R.id.text1);
            textView.setText((CharSequence)IconTextListDialog.this.mItemModifier.getText(n));
            final Drawable drawable = IconTextListDialog.this.mItemModifier.getIcon(n).getConstantState().newDrawable();
            if (drawable != null) {
                drawable.setBounds(0, 0, LauncherUtils.dpToPx(IconTextListDialog.this.mItemModifier.getIconSize(n)[0], IconTextListDialog.this.mContext), LauncherUtils.dpToPx(IconTextListDialog.this.mItemModifier.getIconSize(n)[1], IconTextListDialog.this.mContext));
                drawable.setTintList(IconTextListDialog.this.mItemModifier.getTint(n));
                textView.setCompoundDrawables(drawable, (Drawable)null, (Drawable)null, (Drawable)null);
                textView.setCompoundDrawablePadding(LauncherUtils.dpToPx(24.0f, IconTextListDialog.this.mContext));
            }
            return inflate;
        }
    }
    
    public abstract static class ItemModifier
    {
        public int getCount() {
            return 0;
        }
        
        public Drawable getIcon(final int n) {
            return null;
        }
        
        public int[] getIconSize(final int n) {
            return new int[2];
        }
        
        public String getText(final int n) {
            return "";
        }
        
        public ColorStateList getTint(final int n) {
            return null;
        }
    }
    
    public interface OnDialogSubmitListener
    {
        void onSubmit(final int p0);
    }
}
