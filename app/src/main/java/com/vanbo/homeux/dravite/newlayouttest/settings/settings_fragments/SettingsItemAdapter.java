// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.settings.settings_fragments;

import android.view.ViewGroup;
import android.graphics.drawable.Drawable;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import com.vanbo.homeux.dravite.newlayouttest.settings.items.CaptionSettingsItem;
import com.vanbo.homeux.dravite.newlayouttest.settings.items.GenericSettingsItem;
import android.widget.CompoundButton;
import android.preference.PreferenceManager;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.vanbo.homeux.dravite.newlayouttest.settings.items.SwitchSettingsItem;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import com.vanbo.homeux.dravite.newlayouttest.settings.items.BaseItem;
import android.support.v7.widget.RecyclerView;
import com.dravite.homeux.R;

public class SettingsItemAdapter extends RecyclerView.Adapter<BaseItem.ItemViewHolder>
{
    SettingsListFragment mFragment;
    private List<BaseItem> mItems;
    
    public SettingsItemAdapter(final SettingsListFragment mFragment) {
        this.mItems = new ArrayList<BaseItem>();
        this.mFragment = mFragment;
    }
    
    public void addItems(final List<BaseItem> list) {
        this.mItems.addAll(list);
    }
    
    @Override
    public int getItemCount() {
        return this.mItems.size();
    }
    
    @Override
    public int getItemViewType(final int n) {
        return this.mItems.get(n).getType();
    }
    
    public void onBindViewHolder(final BaseItem.ItemViewHolder itemViewHolder, final int n) {
        final BaseItem baseItem = this.mItems.get(n);
        switch (baseItem.getType()) {
            case 1: {
                itemViewHolder.setTexts(((SwitchSettingsItem)baseItem).getTitle(), ((SwitchSettingsItem)baseItem).getDescription());
                itemViewHolder.setIcon(((SwitchSettingsItem)baseItem).getDrawableRes());
                ((SwitchSettingsItem.SwitchItemHolder)itemViewHolder).mSwitch.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)null);
                ((SwitchSettingsItem.SwitchItemHolder)itemViewHolder).mSwitch.setChecked(PreferenceManager.getDefaultSharedPreferences(((SwitchSettingsItem.SwitchItemHolder)itemViewHolder).itemView.getContext()).getBoolean(baseItem.getTag(), ((SwitchSettingsItem)baseItem).isChecked()));
                ((SwitchSettingsItem.SwitchItemHolder)itemViewHolder).mSwitch.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener)new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                        PreferenceManager.getDefaultSharedPreferences(compoundButton.getContext()).edit().putBoolean(baseItem.getTag(), b).apply();
                    }
                });
                break;
            }
            case 0: {
                itemViewHolder.setTexts(((GenericSettingsItem)baseItem).getTitle(), ((GenericSettingsItem)baseItem).getDescription());
                itemViewHolder.setIcon(((GenericSettingsItem)baseItem).getDrawableRes());
                break;
            }
            case 2: {
                itemViewHolder.setTexts(((CaptionSettingsItem)baseItem).getTitle(), null);
                break;
            }
        }
        itemViewHolder.setEnabled(!baseItem.isPremium() && baseItem.getAction() != null && baseItem.isEnabled());
        if (baseItem.getAction() != null && itemViewHolder.isEnabled()) {
            itemViewHolder.setOnItemClickListener(baseItem.getAction(), baseItem, this);
        }
        itemViewHolder.itemView.getOverlay().clear();
        if (baseItem.isPremium()) {
            final Drawable drawable = this.mFragment.getContext().getDrawable(R.drawable.pro_badge);
            if (drawable != null) {
                drawable.setBounds(0, LauncherUtils.dpToPx(8.0f, this.mFragment.getContext()), LauncherUtils.dpToPx(56.0f, this.mFragment.getContext()), LauncherUtils.dpToPx(64.0f, this.mFragment.getContext()));
                itemViewHolder.itemView.getOverlay().add(drawable);
            }
        }
    }
    
    public BaseItem.ItemViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int n) {
        switch (n) {
            case 1: {
                return new SwitchSettingsItem.SwitchItemHolder(this.mFragment.getContext(), viewGroup);
            }
            case 2: {
                return new CaptionSettingsItem.CaptionItemHolder(this.mFragment.getContext(), viewGroup);
            }
            default: {
                return new GenericSettingsItem.GeneralViewHolder(this.mFragment.getContext(), viewGroup);
            }
        }
    }
}
