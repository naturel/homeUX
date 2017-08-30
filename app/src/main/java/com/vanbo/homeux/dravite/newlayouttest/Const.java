// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest;

import java.util.Collections;
import java.util.TreeMap;
import java.util.SortedMap;
import com.dravite.homeux.R;

public class Const
{
    public static final float APP_GRID_ZOOM_OUT_SCALE = 0.94f;
    public static int ICON_SIZE;
    
    static {
        Const.ICON_SIZE = 32;
    }
    
    public static class Defaults
    {
        public static final String TAG_AM_PM = "ampm";
        public static final String TAG_APP_HEIGHT = "appheight";
        public static final String TAG_APP_WIDTH = "appwidth";
        public static final String TAG_CENTER_CLOCK = "centerclock";
        public static final String TAG_CLOCK_BOLD = "clock_bold";
        public static final String TAG_CLOCK_FONT = "clock_font";
        public static final String TAG_CLOCK_ITALIC = "clock_italic";
        public static final String TAG_CLOCK_SHOW = "show_clock";
        public static final String TAG_CLOCK_SIZE = "clock_size";
        public static final String TAG_CLOCK_SIZE_INT = "clock_sizeINT";
        public static final String TAG_DEFAULT_FOLDER = "defaultFolder";
        public static final String TAG_DIRECT_REVEAL = "directReveal";
        public static final String TAG_DISABLE_WALLPAPER_SCROLL = "disablewallpaperscroll";
        public static final String TAG_FIRST_START = "firstStart";
        public static final String TAG_HAS_SHOWN_MESSAGE = "hasShownMessage";
        public static final String TAG_HIDE_ALL = "hideall";
        public static final String TAG_HIDE_CARDS = "hidecards";
        public static final String TAG_ICON_PACK = "iconPack";
        public static final String TAG_ICON_SIZE = "iconsize";
        public static final String TAG_LICENSED = "isLicensed";
        public static final String TAG_NOTIFICATIONS = "notifications";
        public static final String TAG_PANEL_TRANS = "panelTransparency";
        public static final String TAG_QA_FAB = "qa_fab";
        public static final String TAG_QA_FAB_CLS = "qa_fab_cls";
        public static final String TAG_QA_FAB_PKG = "qa_fab_pkg";
        public static final String TAG_QA_ICON = "qaIcon";
        public static final String TAG_SHOW_LABELS = "showLabels";
        public static final String TAG_SWITCH_CONFIG = "switchConfig";
        public static final String TAG_TRANSFORMER = "transformer";
        public static final String TAG_TRANSFORMER_INT = "transformerINT";
        public static final String TAG_TRANSP_STATUS = "transpStatus";
        private static final SortedMap<String, Object> mDefaults;
        
        static {
            //vanbo begin
            final TreeMap<String, Object> treeMap = new TreeMap<String, Object>();
            treeMap.put(TAG_ICON_SIZE, 3);
            treeMap.put(TAG_SHOW_LABELS, true);
            treeMap.put(TAG_TRANSFORMER_INT, 3);
            treeMap.put(TAG_DEFAULT_FOLDER, "All");
            treeMap.put(TAG_QA_FAB, 0);
            treeMap.put(TAG_QA_ICON, R.drawable.ic_search_black_24dp);
            treeMap.put(TAG_SWITCH_CONFIG, true);
            treeMap.put(TAG_CENTER_CLOCK, true);
            treeMap.put(TAG_HIDE_CARDS, false);
            treeMap.put(TAG_CLOCK_SIZE, R.string.app_name);
            treeMap.put(TAG_CLOCK_SIZE_INT, 1);
            treeMap.put(TAG_CLOCK_FONT, 0);
            treeMap.put(TAG_PANEL_TRANS, 1.0f);
            treeMap.put(TAG_FIRST_START, true);
            treeMap.put(TAG_QA_FAB_CLS, 0);
            treeMap.put(TAG_QA_FAB_PKG, 0);
            treeMap.put(TAG_NOTIFICATIONS, false);
            treeMap.put(TAG_ICON_PACK, 0);
            treeMap.put(TAG_HIDE_ALL, false);
            treeMap.put(TAG_LICENSED, false);
            treeMap.put(TAG_HAS_SHOWN_MESSAGE, false);
            treeMap.put(TAG_TRANSFORMER, 0);
            treeMap.put(TAG_TRANSP_STATUS, false);
            treeMap.put(TAG_AM_PM, false);
            treeMap.put(TAG_CLOCK_BOLD, false);
            treeMap.put(TAG_CLOCK_ITALIC, false);
            treeMap.put(TAG_DISABLE_WALLPAPER_SCROLL, true);
            treeMap.put(TAG_DIRECT_REVEAL, false);
            treeMap.put(TAG_CLOCK_SHOW, true);
            mDefaults = Collections.unmodifiableSortedMap((SortedMap<String, ?>)treeMap);
        }
        
        public static Object get(final String s) {
            return Defaults.mDefaults.get(s);
        }
        
        public static boolean getBoolean(final String s) {
            return (boolean)get(s);
        }
        
        public static float getFloat(final String s) {
            return (float)get(s);
        }
        
        public static int getInt(final String s) {
            return (int)get(s);
        }
        
        public static String getString(final String s) {
            return String.valueOf(get(s));
        }
    }
}
