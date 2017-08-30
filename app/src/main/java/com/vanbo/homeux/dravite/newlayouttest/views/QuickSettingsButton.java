// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views;

import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Rect;
import android.widget.Button;
import com.dravite.homeux.R;

public class QuickSettingsButton extends android.support.v7.widget.AppCompatButton
{
    Rect mTextBounds;
    
    public QuickSettingsButton(final Context context) {
        this(context, null);
    }
    
    public QuickSettingsButton(final Context context, final AttributeSet set) {
        this(context, set, 0);
    }
    
    public QuickSettingsButton(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.mTextBounds = new Rect();
        final Drawable drawable = this.getCompoundDrawables()[1];
        drawable.setTint(-1);
        this.setCompoundDrawablesWithIntrinsicBounds((Drawable)null, drawable, (Drawable)null, (Drawable)null);
        this.setTextColor(-1);
        this.setTextSize(2, 12.0f);
        this.setGravity(17);
        this.setBackground(context.getDrawable(R.drawable.ripple));
    }
    
    protected void onMeasure(int height, int compoundDrawablePadding) {
        super.onMeasure(height, compoundDrawablePadding);
        height = this.getCompoundDrawables()[1].getBounds().height();
        compoundDrawablePadding = this.getCompoundDrawablePadding();
        this.getPaint().getTextBounds(this.getText().toString(), 0, this.getText().length(), this.mTextBounds);
        this.setPadding(0, (int)(-((height + compoundDrawablePadding + this.mTextBounds.height() - this.getMeasuredHeight()) / 2) - this.getResources().getDimension(R.dimen.app_icon_text_padding_delta)), 0, 0);
    }
}
