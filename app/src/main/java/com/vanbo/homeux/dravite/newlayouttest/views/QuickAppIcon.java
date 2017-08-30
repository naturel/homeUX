// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views;

import android.app.Activity;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.graphics.Rect;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import android.graphics.drawable.LayerDrawable;
import android.widget.ImageView.ScaleType;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.dravite.homeux.R;

public class QuickAppIcon extends android.support.v7.widget.AppCompatImageButton
{
    Drawable mIcon;
    int mIconRes;
    
    public QuickAppIcon(final Context context) {
        this(context, null);
    }
    
    public QuickAppIcon(final Context context, final AttributeSet set) {
        this(context, set, 0);
    }
    
    public QuickAppIcon(final Context context, final AttributeSet set, final int n) {
        this(context, set, n, 0);
    }
    
    public QuickAppIcon(final Context context, final AttributeSet set, final int n, final int n2) {
        super(context, set, n);
        this.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        this.setBackground(context.getDrawable(R.drawable.ripple));
    }
    
    public int getIconRes() {
        return this.mIconRes;
    }
    
    public void setIcon(final Drawable drawable) {
        this.mIcon = drawable.getConstantState().newDrawable();
        drawable.setTint(-1);
        final Drawable drawable2 = drawable.getConstantState().newDrawable();
        drawable2.setTint(2013265920);
        final int n = (int)this.getResources().getDimension(R.dimen.app_icon_text_padding_delta);
        final Rect bounds = drawable.getBounds();
        bounds.bottom += n;
        bounds.top += n;
        bounds.right += n / 2;
        drawable2.setBounds(bounds);
        final LayerDrawable imageDrawable = new LayerDrawable(new Drawable[] { drawable2, drawable });
        imageDrawable.setLayerInset(0, n / 3, n / 2, -n / 3, -n / 2);
        imageDrawable.setBounds(0, 0, LauncherUtils.dpToPx(24.0f, this.getContext()), LauncherUtils.dpToPx(24.0f, this.getContext()));
        super.setImageDrawable((Drawable)imageDrawable);
    }
    
    public void setIconRes(final int mIconRes) {
        this.mIconRes = mIconRes;
        this.setIcon(this.getContext().getDrawable(mIconRes));
    }
    
    public void setTag(final Object tag) {
        super.setTag(tag);
        if (!(tag instanceof Intent)) {
            throw new IllegalArgumentException("A QuickApp can only have an Intent as tag.");
        }
        this.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                LauncherUtils.startActivity((Activity)QuickAppIcon.this.getContext(), (View)QuickAppIcon.this, LauncherUtils.makeLaunchIntent((Intent)view.getTag()));
            }
        });
    }
}
