// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views;

import android.view.WindowInsets;
import android.util.AttributeSet;
import android.content.Context;
import android.widget.LinearLayout;

public class SearchResultLayout extends LinearLayout
{
    public SearchResultLayout(final Context context) {
        this(context, null);
    }
    
    public SearchResultLayout(final Context context, final AttributeSet set) {
        this(context, set, 0);
    }
    
    public SearchResultLayout(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
    }
    
    public WindowInsets onApplyWindowInsets(final WindowInsets windowInsets) {
        this.setPadding(this.getPaddingLeft(), this.getPaddingTop(), this.getPaddingRight(), windowInsets.getSystemWindowInsetBottom());
        return super.onApplyWindowInsets(windowInsets);
    }
    
    protected void onLayout(final boolean b, final int n, final int n2, final int n3, final int n4) {
        super.onLayout(b, n, n2, n3, n4);
    }
}
