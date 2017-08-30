// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_dialogs;

import android.animation.Animator.AnimatorListener;
import android.view.animation.OvershootInterpolator;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.animation.ObjectAnimator;
import android.graphics.drawable.ColorDrawable;
import com.vanbo.homeux.dravite.newlayouttest.general_adapters.ColorPresetAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;
import android.view.KeyEvent;
import android.widget.TextView.OnEditorActionListener;
import android.graphics.Color;
import android.content.res.ColorStateList;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.vanbo.homeux.dravite.newlayouttest.general_helpers.ColorUtils;
import android.widget.SeekBar;
import android.graphics.drawable.Drawable;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.view.ViewGroup;
import com.vanbo.homeux.dravite.newlayouttest.general_dialogs.helpers.ColorWatcher;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import com.dravite.homeux.R;

public class ColorDialog
{
    private int mColor;
    private View mContent;
    private Context mContext;
    private Dialog mDialog;
    private String mTitle;
    
    public ColorDialog(final Context mContext, final String mTitle, final int mColor, final ColorWatcher colorWatcher) {
        this.mContext = mContext;
        this.mDialog = new Dialog(mContext, R.style.DialogTheme);
        this.mContent = View.inflate(mContext, R.layout.empty_editor, (ViewGroup)null);
        this.mTitle = mTitle;
        this.mColor = mColor;
        this.mDialog.setContentView(this.mContent);
        ((Button)this.mContent.findViewById(R.id.buttonOk)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                colorWatcher.onColorSubmitted(ColorDialog.this.mColor);
                ColorDialog.this.mDialog.dismiss();
            }
        });
        ((Button)this.mContent.findViewById(R.id.buttonCancel)).setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                ColorDialog.this.mDialog.dismiss();
            }
        });
        this.initColorDialog();
    }
    
    void initColorDialog() {
        ((TextView)this.mDialog.findViewById(R.id.folderName)).setText((CharSequence)this.mTitle);
        ((TextView)this.mDialog.findViewById(R.id.folderName)).setCompoundDrawablesWithIntrinsicBounds((Drawable)null, (Drawable)null, (Drawable)null, (Drawable)null);
        final View inflate = View.inflate(this.mContext, R.layout.color_chooser_layout, (ViewGroup)null);
        inflate.setTag((Object)"colorLayout");
        inflate.setAlpha(0.0f);
        ((ViewGroup)this.mContent.findViewById(R.id.content)).addView(inflate);
        final SeekBar seekBar = (SeekBar)inflate.findViewById(R.id.hue);
        final SeekBar seekBar2 = (SeekBar)inflate.findViewById(R.id.saturation);
        final SeekBar seekBar3 = (SeekBar)inflate.findViewById(R.id.value);
        final float[] colorToHSL = ColorUtils.colorToHSL(this.mColor);
        seekBar.setProgress((int)colorToHSL[0]);
        seekBar2.setProgress((int)(1000.0f * colorToHSL[1]));
        seekBar3.setProgress((int)(1000.0f * colorToHSL[2]));
        this.updateColor(inflate, this.mColor);
        final SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = (SeekBar.OnSeekBarChangeListener)new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(final SeekBar seekBar, int hsLtoColor, final boolean b) {
                if (b) {
                    hsLtoColor = ColorUtils.HSLtoColor(new float[] { seekBar.getProgress(), seekBar2.getProgress() / 1000.0f, seekBar3.getProgress() / 1000.0f });
                    ColorDialog.this.updateColor(inflate, hsLtoColor);
                }
            }
            
            public void onStartTrackingTouch(final SeekBar seekBar) {
            }
            
            public void onStopTrackingTouch(final SeekBar seekBar) {
            }
        };
        seekBar.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener)onSeekBarChangeListener);
        seekBar2.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener)onSeekBarChangeListener);
        seekBar3.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener)onSeekBarChangeListener);
        final EditText editText = (EditText)inflate.findViewById(R.id.hexValue);
        final ImageButton imageButton = (ImageButton)inflate.findViewById(R.id.submitButton);
        imageButton.setImageTintList(ColorStateList.valueOf(ColorUtils.getDarkerColor(this.mColor)));
        imageButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (editText.getText().toString().matches("^[#][A-F,0-9,a-f]{6}.")) {
                    final int color = Color.parseColor(editText.getText().toString());
                    final float[] colorToHSL = ColorUtils.colorToHSL(color);
                    seekBar.setProgress((int)colorToHSL[0]);
                    seekBar2.setProgress((int)(colorToHSL[1] * 1000.0f));
                    seekBar3.setProgress((int)(colorToHSL[2] * 1000.0f));
                    ColorDialog.this.updateColor(inflate, color);
                }
            }
        });
        editText.setOnEditorActionListener((TextView.OnEditorActionListener)new TextView.OnEditorActionListener() {
            public boolean onEditorAction(final TextView textView, final int n, final KeyEvent keyEvent) {
                boolean b = false;
                if (n == 6) {
                    imageButton.performClick();
                    ((InputMethodManager)ColorDialog.this.mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    b = true;
                }
                return b;
            }
        });
        final RecyclerView recyclerView = (RecyclerView)inflate.findViewById(R.id.presets);
        recyclerView.setLayoutManager((RecyclerView.LayoutManager)new LinearLayoutManager(this.mContext, 1, false));
        recyclerView.setAdapter((RecyclerView.Adapter)new ColorPresetAdapter(this.mContext, (ColorPresetAdapter.ColorListener)new ColorPresetAdapter.ColorListener() {
            @Override
            public void onSelected(final int n) {
                final int color = ((ColorDrawable)inflate.findViewById(R.id.colorView).getBackground()).getColor();
                final float[] colorToHSL = ColorUtils.colorToHSL(color);
                final float[] colorToHSL2 = ColorUtils.colorToHSL(n);
                final ObjectAnimator ofArgb = ObjectAnimator.ofArgb(ColorDialog.this.mDialog.findViewById(R.id.folder_darker_panel), "backgroundColor", new int[] { ColorUtils.getDarkerColor(color), ColorUtils.getDarkerColor(n) });
                ofArgb.setInterpolator((TimeInterpolator)new DecelerateInterpolator());
                ofArgb.setDuration(400L);
                ofArgb.start();
                final ObjectAnimator ofInt = ObjectAnimator.ofInt(seekBar, "progress", new int[] { (int)colorToHSL[0], (int)colorToHSL2[0] });
                final ObjectAnimator ofInt2 = ObjectAnimator.ofInt(seekBar2, "progress", new int[] { (int)(colorToHSL[1] * 1000.0f), (int)(colorToHSL2[1] * 1000.0f) });
                final ObjectAnimator ofInt3 = ObjectAnimator.ofInt(seekBar3, "progress", new int[] { (int)(colorToHSL[2] * 1000.0f), (int)(colorToHSL2[2] * 1000.0f) });
                final ObjectAnimator ofArgb2 = ObjectAnimator.ofArgb(inflate.findViewById(R.id.colorView), "backgroundColor", new int[] { color, n });
                if (ColorUtils.isBrightColor(ColorUtils.getDarkerColor(n))) {
                    ((TextView)ColorDialog.this.mDialog.findViewById(R.id.folderName)).setTextColor(1979711488);
                }
                else {
                    ((TextView)ColorDialog.this.mDialog.findViewById(R.id.folderName)).setTextColor(-1);
                }
                final AnimatorSet set = new AnimatorSet();
                set.playTogether(new Animator[] { ofInt, ofInt2, ofInt3 });
                set.setInterpolator((TimeInterpolator)new OvershootInterpolator());
                set.setDuration(500L);
                set.addListener((Animator.AnimatorListener)new Animator.AnimatorListener() {
                    public void onAnimationCancel(final Animator animator) {
                    }
                    
                    public void onAnimationEnd(final Animator animator) {
                    }
                    
                    public void onAnimationRepeat(final Animator animator) {
                    }
                    
                    public void onAnimationStart(final Animator animator) {
                        ColorDialog.this.updateColor(inflate, n);
                    }
                });
                set.start();
                ofArgb2.setInterpolator((TimeInterpolator)new DecelerateInterpolator());
                ofArgb2.setDuration(400L);
                ofArgb2.start();
                editText.setText((CharSequence)String.format("#%02X%02X%02X", Color.red(n), Color.green(n), Color.blue(n)));
            }
        }));
        inflate.post((Runnable)new Runnable() {
            @Override
            public void run() {
                inflate.animate().alpha(1.0f);
            }
        });
        ((Button)inflate.findViewById(R.id.switchTo)).setVisibility(View.GONE);
    }
    
    public void show() {
        this.mDialog.show();
    }
    
    void updateColor(final View view, final int n) {
        final View viewById = view.findViewById(R.id.colorView);
        final EditText editText = (EditText)view.findViewById(R.id.hexValue);
        this.mColor = n;
        this.mDialog.findViewById(R.id.folder_darker_panel).setBackgroundColor(ColorUtils.getDarkerColor(n));
        ((TextView)this.mDialog.findViewById(R.id.buttonOk)).setTextColor(ColorUtils.getDarkerColor(n));
        ((TextView)this.mDialog.findViewById(R.id.buttonCancel)).setTextColor(ColorUtils.getDarkerColor(n));
        ((ImageButton)this.mDialog.findViewById(R.id.submitButton)).setImageTintList(ColorStateList.valueOf(ColorUtils.getDarkerColor(n)));
        if (ColorUtils.isBrightColor(n)) {
            ((TextView)this.mDialog.findViewById(R.id.folderName)).setTextColor(1979711488);
            ((Button)this.mDialog.findViewById(R.id.switchTo)).setTextColor(1979711488);
        }
        else {
            ((TextView)this.mDialog.findViewById(R.id.folderName)).setTextColor(-1);
            ((Button)this.mDialog.findViewById(R.id.switchTo)).setTextColor(-1);
        }
        editText.setText((CharSequence)String.format("#%02X%02X%02X", Color.red(n), Color.green(n), Color.blue(n)));
        viewById.setBackgroundColor(n);
    }
}
