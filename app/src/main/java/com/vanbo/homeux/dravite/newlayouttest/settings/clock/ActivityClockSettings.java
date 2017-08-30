// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.settings.clock;

import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import com.vanbo.homeux.dravite.newlayouttest.LauncherLog;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.Arrays;
import android.widget.SpinnerAdapter;
import com.vanbo.homeux.dravite.newlayouttest.general_adapters.FontAdapter;
import android.widget.Spinner;
import com.vanbo.homeux.dravite.newlayouttest.Const;
import android.content.Context;
import android.text.format.DateFormat;
import com.vanbo.homeux.dravite.newlayouttest.views.ToggleImageButton;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import com.vanbo.homeux.dravite.newlayouttest.views.TextDate;
import android.widget.TextClock;
import android.support.v7.app.AppCompatActivity;
import com.dravite.homeux.R;

public class ActivityClockSettings extends AppCompatActivity
{
    private TextClock mClockView;
    private TextDate mDateView;
    private SharedPreferences mPreferences;
    
    SharedPreferences.Editor editor() {
        return this.mPreferences.edit();
    }
    
    void initAMPMToggle() {
        final ToggleImageButton toggleImageButton = (ToggleImageButton)this.findViewById(R.id.btn_am_pm);
        toggleImageButton.setEnabled(!DateFormat.is24HourFormat((Context)this));
        if (toggleImageButton.isEnabled()) {
            toggleImageButton.setOnCheckedChangeListener((ToggleImageButton.OnCheckedChangeListener)new ToggleImageButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final ToggleImageButton toggleImageButton, final boolean b) {
                    ActivityClockSettings.this.editor().putBoolean("ampm", b).apply();
                    ActivityClockSettings.this.updateAMPM();
                }
            });
            toggleImageButton.setChecked(this.mPreferences.getBoolean("ampm", Const.Defaults.getBoolean("ampm")));
        }
    }
    
    void initAlignmentToggles() {
        final ToggleImageButton toggleImageButton = (ToggleImageButton)this.findViewById(R.id.btn_align_center);
        final ToggleImageButton toggleImageButton2 = (ToggleImageButton)this.findViewById(R.id.btn_align_left);
        final boolean boolean1 = this.mPreferences.getBoolean("centerclock", Const.Defaults.getBoolean("centerclock"));
        toggleImageButton.setSelected(boolean1);
        toggleImageButton2.setSelected(!boolean1);
        this.updateAlignment();
        toggleImageButton.setOnCheckedChangeListener((ToggleImageButton.OnCheckedChangeListener)new ToggleImageButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final ToggleImageButton toggleImageButton, final boolean b) {
                toggleImageButton2.setSelected(false);
                ActivityClockSettings.this.editor().putBoolean("centerclock", true).apply();
                ActivityClockSettings.this.updateAlignment();
            }
        });
        toggleImageButton2.setOnCheckedChangeListener((ToggleImageButton.OnCheckedChangeListener)new ToggleImageButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final ToggleImageButton toggleImageButton, final boolean b) {
                toggleImageButton.setSelected(false);
                ActivityClockSettings.this.editor().putBoolean("centerclock", false).apply();
                ActivityClockSettings.this.updateAlignment();
            }
        });
    }
    
    void initFontStyleToggles() {
        final ToggleImageButton toggleImageButton = (ToggleImageButton)this.findViewById(R.id.btn_toggle_bold);
        final ToggleImageButton toggleImageButton2 = (ToggleImageButton)this.findViewById(R.id.btn_toggle_italic);
        toggleImageButton.setOnCheckedChangeListener((ToggleImageButton.OnCheckedChangeListener)new ToggleImageButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final ToggleImageButton toggleImageButton, final boolean b) {
                ActivityClockSettings.this.editor().putBoolean("clock_bold", b).apply();
                ActivityClockSettings.this.updateBold();
            }
        });
        toggleImageButton2.setOnCheckedChangeListener((ToggleImageButton.OnCheckedChangeListener)new ToggleImageButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final ToggleImageButton toggleImageButton, final boolean b) {
                ActivityClockSettings.this.editor().putBoolean("clock_italic", b).apply();
                ActivityClockSettings.this.updateItalic();
            }
        });
        toggleImageButton.setChecked(this.mPreferences.getBoolean("clock_bold", Const.Defaults.getBoolean("clock_bold")));
        toggleImageButton2.setChecked(this.mPreferences.getBoolean("clock_italic", Const.Defaults.getBoolean("clock_italic")));
    }
    
    void initSpinners() {
        final Spinner spinner = (Spinner)this.findViewById(R.id.spinner);
        spinner.setAdapter((SpinnerAdapter)new FontAdapter((Context)this));
        spinner.setSelection(Arrays.asList(FontAdapter.fonts).indexOf(this.mPreferences.getString("clock_font", Const.Defaults.getString("clock_font"))));
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                ActivityClockSettings.this.editor().putString("clock_font", FontAdapter.fonts[n]).apply();
                ActivityClockSettings.this.updateFont();
            }
            
            public void onNothingSelected(final AdapterView<?> adapterView) {
            }
        });
        final Spinner spinner2 = (Spinner)this.findViewById(R.id.spinner2);
        final ArrayAdapter fromResource = ArrayAdapter.createFromResource((Context)this, R.array.sizes, android.R.layout.simple_spinner_item);
        fromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter((SpinnerAdapter)fromResource);
        spinner2.setSelection(this.mPreferences.getInt("clock_sizeINT", Const.Defaults.getInt("clock_sizeINT")));
        spinner2.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(final AdapterView<?> adapterView, final View view, final int n, final long n2) {
                LauncherLog.d(this.getClass().getName(), "Position: " + n);
                ActivityClockSettings.this.editor().putString("clock_size", ActivityClockSettings.this.getResources().getStringArray(R.array.sizes)[n]).putInt("clock_sizeINT", n).apply();
                ActivityClockSettings.this.updateSize();
            }
            
            public void onNothingSelected(final AdapterView<?> adapterView) {
            }
        });
    }
    
    void initVisibilitySwitch() {
        final Switch switch1 = (Switch)this.findViewById(R.id.clockSwitch);
        switch1.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                ActivityClockSettings.this.editor().putBoolean("show_clock", b).apply();
                ActivityClockSettings.this.updateVisibility();
            }
        });
        switch1.setChecked(this.mPreferences.getBoolean("show_clock", Const.Defaults.getBoolean("show_clock")));
    }
    
    @Override
    protected void onCreate(@Nullable final Bundle bundle) {
        super.onCreate(bundle);
        this.mPreferences = PreferenceManager.getDefaultSharedPreferences((Context)this);
        this.setContentView(R.layout.activity_clock_settings);
        this.getWindow().setStatusBarColor(15108398);
        this.mClockView = (TextClock)this.findViewById(R.id.textClock);
        (this.mDateView = (TextDate)this.findViewById(R.id.dateView)).updateDate();
        this.initSpinners();
        this.initAlignmentToggles();
        this.initFontStyleToggles();
        this.initAMPMToggle();
        this.initVisibilitySwitch();
        ((ImageButton)this.findViewById(R.id.backArrow)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ActivityClockSettings.this.onBackPressed();
            }
        });
    }
    
    void updateAMPM() {
        final boolean boolean1 = this.mPreferences.getBoolean("ampm", Const.Defaults.getBoolean("ampm"));
        if (!DateFormat.is24HourFormat((Context)this) && boolean1) {
            this.mClockView.setFormat12Hour((CharSequence)"h:mm a");
            return;
        }
        this.mClockView.setFormat12Hour((CharSequence)"h:mm");
    }
    
    void updateAlignment() {
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)this.mDateView.getLayoutParams();
        final RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams)this.mClockView.getLayoutParams();
        if (this.mPreferences.getBoolean("centerclock", Const.Defaults.getBoolean("centerclock"))) {
            LauncherLog.d(this.getClass().getName(), "Align in center");
            layoutParams.removeRule(20);
            layoutParams2.removeRule(20);
            layoutParams.removeRule(9);
            layoutParams2.removeRule(9);
            layoutParams.addRule(13, -1);
            layoutParams2.addRule(13, -1);
        }
        else {
            LauncherLog.d(this.getClass().getName(), "Align left");
            layoutParams.addRule(20, -1);
            layoutParams2.addRule(20, -1);
        }
        this.mDateView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        this.mClockView.setLayoutParams((ViewGroup.LayoutParams)layoutParams2);
    }
    
    void updateBold() {
        this.updateStyle();
    }
    
    void updateFont() {
        this.updateStyle();
    }
    
    void updateItalic() {
        this.updateStyle();
    }
    
    void updateSize() {
        final String string = this.mPreferences.getString("clock_size", Const.Defaults.getString("clock_size"));
        if (string.equals(this.getString(R.string.small))) {
            this.mClockView.setTextSize(2, 35.0f);
            return;
        }
        if (string.equals(this.getString(R.string.medium))) {
            this.mClockView.setTextSize(2, 50.0f);
            return;
        }
        this.mClockView.setTextSize(2, 65.0f);
    }
    
    void updateStyle() {
        final boolean boolean1 = this.mPreferences.getBoolean("clock_bold", Const.Defaults.getBoolean("clock_bold"));
        final Boolean value = this.mPreferences.getBoolean("clock_italic", Const.Defaults.getBoolean("clock_italic"));
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
        this.mClockView.setTypeface(Typeface.create(this.mPreferences.getString("clock_font", Const.Defaults.getString("clock_font")), n));
    }
    
    void updateVisibility() {
        final boolean b = false;
        final boolean boolean1 = this.mPreferences.getBoolean("show_clock", Const.Defaults.getBoolean("show_clock"));
        int n;
        if (boolean1) {
            n = View.VISIBLE;
        }
        else {
            n = View.INVISIBLE;
        }
        this.mClockView.setVisibility(n);
        this.mDateView.setVisibility(n);
        this.findViewById(R.id.spinner).setEnabled(boolean1);
        this.findViewById(R.id.spinner2).setEnabled(boolean1);
        this.findViewById(R.id.btn_align_left).setEnabled(boolean1);
        this.findViewById(R.id.btn_align_center).setEnabled(boolean1);
        this.findViewById(R.id.btn_toggle_bold).setEnabled(boolean1);
        this.findViewById(R.id.btn_toggle_italic).setEnabled(boolean1);
        final View viewById = this.findViewById(R.id.btn_am_pm);
        boolean enabled = b;
        if (!DateFormat.is24HourFormat((Context)this)) {
            enabled = b;
            if (boolean1) {
                enabled = true;
            }
        }
        viewById.setEnabled(enabled);
    }
}
