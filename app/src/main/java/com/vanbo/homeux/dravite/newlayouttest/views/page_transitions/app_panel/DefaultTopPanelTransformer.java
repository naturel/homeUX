// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_panel;

import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import android.view.ViewGroup;
import android.view.View;
import android.content.Context;
import android.support.v4.view.ViewPager;
import com.dravite.homeux.R;

public class DefaultTopPanelTransformer implements ViewPager.PageTransformer
{
    private Context mContext;
    
    public DefaultTopPanelTransformer(final Context mContext) {
        this.mContext = mContext;
    }
    
    @Override
    public void transformPage(final View view, final float n) {
        if (Math.abs(n) == 1.0f) {
            view.setVisibility(View.INVISIBLE);
            view.setTranslationY(0.0f);
        }
        else {
            view.setVisibility(View.VISIBLE);
            if (((ViewGroup)view).getChildCount() == 3) {
                view.setTranslationY(-1.0f);
                view.setTranslationX(-n * view.getWidth());
                view.setAlpha(1.0f - Math.abs(n));
                view.setScaleX(1.0f - Math.abs(n));
                view.setScaleY(1.0f - Math.abs(n));
                return;
            }
            view.setTranslationY(1.0f);
            if (view.findViewById(R.id.quick_settings_bg) != null) {
                for (int i = 0; i < ((ViewGroup)view.findViewById(R.id.quick_settings_bg)).getChildCount(); ++i) {
                    ((ViewGroup)view.findViewById(R.id.quick_settings_bg)).getChildAt(i).setTranslationX((2 - i) * n * LauncherUtils.dpToPx(100.0f, this.mContext));
                    ((ViewGroup)view.findViewById(R.id.quick_settings_bg)).getChildAt(i).setScaleX(1.0f - Math.abs(n));
                    ((ViewGroup)view.findViewById(R.id.quick_settings_bg)).getChildAt(i).setScaleY(1.0f - Math.abs(n));
                }
            }
            else if (view.findViewById(R.id.folder_list) != null) {
                for (int j = 0; j < ((ViewGroup)view.findViewById(R.id.folder_list)).getChildCount(); ++j) {
                    ((ViewGroup)view.findViewById(R.id.folder_list)).getChildAt(j).setTranslationX(j % 4 * n * LauncherUtils.dpToPx(100.0f, this.mContext));
                    ((ViewGroup)view.findViewById(R.id.folder_list)).getChildAt(j).setScaleX(1.0f - Math.abs(n));
                    ((ViewGroup)view.findViewById(R.id.folder_list)).getChildAt(j).setScaleY(1.0f - Math.abs(n));
                }
            }
        }
    }
}
