// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.top_fragments;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.content.ActivityNotFoundException;
import android.widget.Toast;
import android.content.ContentUris;
import android.provider.CalendarContract;
import android.app.Activity;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import android.content.Intent;
import android.view.View.OnClickListener;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.JsonHelper;
import android.view.ViewGroup.LayoutParams;
import android.text.format.DateFormat;
import android.content.Context;
import android.widget.RelativeLayout;
import com.vanbo.homeux.dravite.newlayouttest.Const;
import android.preference.PreferenceManager;
import com.vanbo.homeux.dravite.newlayouttest.views.TextDate;
import android.widget.TextClock;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.os.Bundle;
import com.vanbo.homeux.dravite.newlayouttest.views.QuickAppBar;
import android.support.v4.app.Fragment;
import com.dravite.homeux.R;

public class ClockFragment extends Fragment
{
    public QuickAppBar mQuickAppBar;
    
    @Override
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
    }
    
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater layoutInflater, final ViewGroup viewGroup, final Bundle bundle) {
        final View inflate = layoutInflater.inflate(R.layout.layout_fragment_clock, (ViewGroup)null);
        inflate.setTag((Object)"clock");
        return inflate;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (this.getView() != null) {
            final TextClock textClock = (TextClock)this.getView().findViewById(R.id.textClock);
            final TextDate textDate = (TextDate)this.getView().findViewById(R.id.dateView);
            if (!PreferenceManager.getDefaultSharedPreferences(this.getContext()).getBoolean("show_clock", Const.Defaults.getBoolean("show_clock"))) {
                textClock.setVisibility(View.INVISIBLE);
                textDate.setVisibility(View.INVISIBLE);
                return;
            }
            textDate.updateDate();
            final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)textDate.getLayoutParams();
            final RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams)textClock.getLayoutParams();
            final boolean boolean1 = PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).getBoolean("ampm", Const.Defaults.getBoolean("ampm"));
            if (!DateFormat.is24HourFormat(this.getContext()) && boolean1) {
                textClock.setFormat12Hour((CharSequence)"h:mm a");
            }
            else {
                textClock.setFormat12Hour((CharSequence)"h:mm");
            }
            if (PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).getBoolean("centerclock", Const.Defaults.getBoolean("centerclock"))) {
                layoutParams.addRule(13, -1);
                layoutParams2.addRule(13, -1);
            }
            else {
                layoutParams.addRule(20, -1);
                layoutParams2.addRule(20, -1);
            }
            textDate.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
            textClock.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
            textClock.setVisibility(View.VISIBLE);
            textDate.setVisibility(View.VISIBLE);
        }
    }
    
    @Override
    public void onViewCreated(final View view, final Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mQuickAppBar = (QuickAppBar)view.findViewById(R.id.quick_action_bar);
        JsonHelper.inflateQuickApps(this.getContext(), this.mQuickAppBar);
        this.mQuickAppBar.setDragSurfaceLayout(((LauncherActivity)this.getActivity()).mDragView);
        ((LauncherActivity)this.getActivity()).mDragView.setQuickActionBar(this.mQuickAppBar);
        final TextDate textDate = (TextDate)view.findViewById(R.id.dateView);
        final TextClock textClock = (TextClock)view.findViewById(R.id.textClock);
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)textDate.getLayoutParams();
        final RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams)textClock.getLayoutParams();
        if (PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).getBoolean("centerclock", Const.Defaults.getBoolean("centerclock"))) {
            layoutParams.addRule(13, -1);
            layoutParams2.addRule(13, -1);
        }
        else {
            layoutParams.addRule(20, -1);
            layoutParams2.addRule(20, -1);
        }
        textDate.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        textClock.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
        textClock.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                final Intent intent = new Intent("android.intent.action.SHOW_ALARMS");
                intent.setFlags(268435456);
                LauncherUtils.startActivity(ClockFragment.this.getActivity(), view, intent);
            }
        });
        this.updateClock();
        textDate.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                final long currentTimeMillis = System.currentTimeMillis();
                final Uri.Builder buildUpon = CalendarContract.CONTENT_URI.buildUpon();
                buildUpon.appendPath("time");
                ContentUris.appendId(buildUpon, currentTimeMillis);
                final Intent setData = new Intent("android.intent.action.VIEW").setData(buildUpon.build());
                try {
                    LauncherUtils.startActivity(ClockFragment.this.getActivity(), view, setData);
                }
                catch (ActivityNotFoundException ex) {
                    Toast.makeText((Context)ClockFragment.this.getActivity(), R.string.calendar_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    void updateClock() {
        final TextClock textClock = (TextClock)this.getView().findViewById(R.id.textClock);
        final SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        final boolean boolean1 = defaultSharedPreferences.getBoolean("clock_bold", Const.Defaults.getBoolean("clock_bold"));
        final Boolean value = defaultSharedPreferences.getBoolean("clock_italic", Const.Defaults.getBoolean("clock_italic"));
        final String string = defaultSharedPreferences.getString("clock_size", Const.Defaults.getString("clock_size"));
        final boolean boolean2 = defaultSharedPreferences.getBoolean("ampm", Const.Defaults.getBoolean("ampm"));
        int n;
        if (Boolean.valueOf(boolean1)) {
            if (value) {
                n = 3;
            }
            else {
                n = 1;
            }
        }
        else if (value) {
            n = 2;
        }
        else {
            n = 0;
        }
        if (string.equals(this.getString(R.string.small))) {
            textClock.setTextSize(2, 35.0f);
        }
        else if (string.equals(this.getString(R.string.medium))) {
            textClock.setTextSize(2, 50.0f);
        }
        else {
            textClock.setTextSize(2, 65.0f);
        }
        if (!DateFormat.is24HourFormat(this.getContext()) && boolean2) {
            textClock.setFormat12Hour((CharSequence)"h:mm a");
        }
        else {
            textClock.setFormat12Hour((CharSequence)"hh:mm");
        }
        textClock.setTypeface(Typeface.create(defaultSharedPreferences.getString("clock_font", Const.Defaults.getString("clock_font")), n));
    }
}
