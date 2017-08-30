// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.settings;

import android.view.ViewGroup;
import com.vanbo.homeux.dravite.newlayouttest.settings.settings_fragments.SettingsListFragment;
import java.util.List;
import com.vanbo.homeux.dravite.newlayouttest.settings.items.BaseItem;
import java.util.ArrayList;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.support.v4.view.PagerAdapter;
import com.vanbo.homeux.dravite.newlayouttest.views.SlidingTabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import com.vanbo.homeux.dravite.newlayouttest.settings.settings_fragments.SettingsFragmentAdapter;
import android.support.v7.app.AppCompatActivity;
import com.dravite.homeux.R;

public abstract class SettingsBaseActivity extends AppCompatActivity
{
    public static int ACCENT_COLOR;
    public static int PRIMARY_COLOR;
    public SettingsFragmentAdapter mSettingsFragmentAdapter;
    
    static {
        SettingsBaseActivity.PRIMARY_COLOR = -11751600;
        SettingsBaseActivity.ACCENT_COLOR = -12627531;
    }
    
    public abstract void initiateFragments(final SettingsFragmentAdapter p0);
    
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.activity_settings);
        final ViewPager viewPager = (ViewPager)this.findViewById(R.id.settings_pages);
        final SlidingTabLayout slidingTabLayout = (SlidingTabLayout)this.findViewById(R.id.tabs);
        viewPager.setAdapter(this.mSettingsFragmentAdapter = new SettingsFragmentAdapter(this.getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(8);
        this.initiateFragments(this.mSettingsFragmentAdapter);
        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(final int n) {
            }
            
            @Override
            public void onPageScrolled(final int n, final float n2, final int n3) {
            }
            
            @Override
            public void onPageSelected(final int n) {
                for (int i = 0; i < ((ViewGroup)slidingTabLayout.getSlidingTabStrip()).getChildCount(); ++i) {
                    final TextView textView = (TextView)((ViewGroup)slidingTabLayout.getSlidingTabStrip()).getChildAt(i);
                    if (i == n) {
                        textView.setTextColor(-1);
                    }
                    else {
                        textView.setTextColor(1996488705);
                    }
                }
            }
        });
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setViewPager(viewPager);
        for (int i = 0; i < ((ViewGroup)slidingTabLayout.getSlidingTabStrip()).getChildCount(); ++i) {
            final TextView textView = (TextView)((ViewGroup)slidingTabLayout.getSlidingTabStrip()).getChildAt(i);
            if (i == 0) {
                textView.setTextColor(-1);
            }
            else {
                textView.setTextColor(1996488705);
            }
        }
        slidingTabLayout.setSelectedIndicatorColors(-1);
        final Toolbar supportActionBar = (Toolbar)this.findViewById(R.id.toolbar);
        this.setSupportActionBar(supportActionBar);
        ((TextView)supportActionBar.findViewById(R.id.title)).setTextColor(-1);
        slidingTabLayout.setBackgroundColor(((ColorDrawable)supportActionBar.getBackground()).getColor());
        viewPager.setCurrentItem(this.getIntent().getIntExtra("page", 0));
    }
    
    @Override
    protected void onSaveInstanceState(final Bundle bundle) {
        bundle.putInt("primary", SettingsBaseActivity.PRIMARY_COLOR);
        bundle.putInt("accent", SettingsBaseActivity.ACCENT_COLOR);
        super.onSaveInstanceState(bundle);
    }
    
    public void putPage(final String s, final AddItems addItems) {
        final ArrayList<BaseItem> list = new ArrayList<BaseItem>();
        addItems.create(list);
        this.mSettingsFragmentAdapter.addFragment(SettingsListFragment.create(s, list));
    }
    
    public interface AddItems
    {
        void create(final List<BaseItem> p0);
    }
}
