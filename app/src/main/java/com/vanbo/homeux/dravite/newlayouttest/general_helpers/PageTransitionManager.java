// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_helpers;

import com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer.DelayTransformer;
import com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer.ZoomOutTranformer;
import com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer.ZoomOutSlideTransformer;
import com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer.ZoomInTransformer;
import com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer.TabletTransformer;
import com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer.StackTransformer;
import com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer.ScaleInOutTransformer;
import com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer.RotateUpTransformer;
import com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer.RotateDownTransformer;
import com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer.ForegroundToBackgroundTransformer;
import com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer.FlipVerticalTransformer;
import com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer.FlipHorizontalTransformer;
import com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer.DepthPageTransformer;
import com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer.DefaultTransformer;
import com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer.CubeOutTransformer;
import com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer.BackgroundToForegroundTransformer;
import com.vanbo.homeux.dravite.newlayouttest.views.page_transitions.app_drawer.AccordionTransformer;
import android.content.Context;
import java.util.HashMap;
import android.support.v4.view.ViewPager;
import java.util.Map;
import com.dravite.homeux.R;

public class PageTransitionManager
{
    private static String[] mTransitionIds;
    public static String[] mTransitionNames;
    public static Map<String, ViewPager.PageTransformer> pageTransformerMap;
    
    static {
        PageTransitionManager.pageTransformerMap = new HashMap<String, ViewPager.PageTransformer>();
    }
    
    public static ViewPager.PageTransformer getTransformer(final int n) {
        return PageTransitionManager.pageTransformerMap.get(PageTransitionManager.mTransitionIds[n]);
    }
    
    public static void initialize(final Context context) {
        PageTransitionManager.mTransitionIds = context.getResources().getStringArray(R.array.page_transformer_ids);
        PageTransitionManager.mTransitionNames = context.getResources().getStringArray(R.array.page_transformer_names);
        PageTransitionManager.pageTransformerMap.put(PageTransitionManager.mTransitionIds[0], new AccordionTransformer());
        PageTransitionManager.pageTransformerMap.put(PageTransitionManager.mTransitionIds[1], new BackgroundToForegroundTransformer());
        PageTransitionManager.pageTransformerMap.put(PageTransitionManager.mTransitionIds[2], new CubeOutTransformer());
        PageTransitionManager.pageTransformerMap.put(PageTransitionManager.mTransitionIds[3], new DefaultTransformer());
        PageTransitionManager.pageTransformerMap.put(PageTransitionManager.mTransitionIds[4], new DepthPageTransformer());
        PageTransitionManager.pageTransformerMap.put(PageTransitionManager.mTransitionIds[5], new FlipHorizontalTransformer());
        PageTransitionManager.pageTransformerMap.put(PageTransitionManager.mTransitionIds[6], new FlipVerticalTransformer());
        PageTransitionManager.pageTransformerMap.put(PageTransitionManager.mTransitionIds[7], new ForegroundToBackgroundTransformer());
        PageTransitionManager.pageTransformerMap.put(PageTransitionManager.mTransitionIds[8], new RotateDownTransformer());
        PageTransitionManager.pageTransformerMap.put(PageTransitionManager.mTransitionIds[9], new RotateUpTransformer());
        PageTransitionManager.pageTransformerMap.put(PageTransitionManager.mTransitionIds[10], new ScaleInOutTransformer());
        PageTransitionManager.pageTransformerMap.put(PageTransitionManager.mTransitionIds[11], new StackTransformer());
        PageTransitionManager.pageTransformerMap.put(PageTransitionManager.mTransitionIds[12], new TabletTransformer());
        PageTransitionManager.pageTransformerMap.put(PageTransitionManager.mTransitionIds[13], new ZoomInTransformer());
        PageTransitionManager.pageTransformerMap.put(PageTransitionManager.mTransitionIds[14], new ZoomOutSlideTransformer());
        PageTransitionManager.pageTransformerMap.put(PageTransitionManager.mTransitionIds[15], new ZoomOutTranformer());
        PageTransitionManager.pageTransformerMap.put(PageTransitionManager.mTransitionIds[16], new DelayTransformer());
    }
}
