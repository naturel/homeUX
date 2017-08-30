// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.search;

import android.view.LayoutInflater;
import android.widget.TextView;
import com.vanbo.homeux.dravite.newlayouttest.views.RoundImageView;
import android.view.ViewGroup;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.content.Intent;
import android.widget.ImageView.ScaleType;
import android.content.res.ColorStateList;
import android.view.ViewGroup.LayoutParams;
import com.vanbo.homeux.dravite.newlayouttest.views.AppIconView;
import android.support.v7.widget.GridLayoutManager;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import android.widget.FrameLayout;
import android.view.View;
import android.view.View.OnClickListener;
import java.util.Collection;
import java.util.ArrayList;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.helpers.ContactUtil;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.Application;
import java.util.List;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.widget.ImageView;
import android.provider.ContactsContract;
import com.dravite.homeux.R;

public class SearchResultAdapter extends Adapter<ViewHolder>
{
    public static final int VIEW_APP = 0;
    public static final int VIEW_CONTACT = 2;
    public static final int VIEW_SEPARATOR = 1;
    public static final int VIEW_WEBSEARCH = 3;
    private final Context mContext;
    private String mQuery;
    private final List<Application> mQueryResult;
    private final List<ContactUtil.Contact> mQueryResultContacts;
    
    public SearchResultAdapter(final Context mContext) {
        this.mQueryResult = new ArrayList<Application>();
        this.mQueryResultContacts = new ArrayList<ContactUtil.Contact>();
        this.mQuery = "";
        this.mContext = mContext;
    }
    
    public void addToContactsQueryResult(final Collection<ContactUtil.Contact> collection) {
        this.mQueryResultContacts.addAll(collection);
        ((RecyclerView.Adapter)this).notifyItemRangeChanged(this.mQueryResult.size() + 1, this.mQueryResultContacts.size() + 1);
    }
    
    public void addToQueryResult(final Collection<Application> collection) {
        this.mQueryResult.addAll(collection);
        ((RecyclerView.Adapter)this).notifyItemRangeChanged(0, this.mQueryResult.size());
    }
    
    public void clearQuery() {
        this.mQueryResult.clear();
        this.mQueryResultContacts.clear();
        ((RecyclerView.Adapter)this).notifyDataSetChanged();
        this.mQuery = "";
    }
    
    public int getContactIndex(final int n) {
        if (this.mQueryResult.isEmpty()) {
            return n - 1;
        }
        return n - 1 - 1 - this.mQueryResult.size();
    }
    
    @Override
    public int getItemCount() {
        if (this.mQuery.equals("")) {
            return 0;
        }
        if (this.mQueryResult.isEmpty() && this.mQueryResultContacts.isEmpty()) {
            return 1;
        }
        if (this.mQueryResultContacts.isEmpty()) {
            return this.mQueryResult.size() + 1;
        }
        if (this.mQueryResult.isEmpty()) {
            return this.mQueryResultContacts.size() + 1;
        }
        return this.mQueryResult.size() + 2 + this.mQueryResultContacts.size();
    }
    
    @Override
    public int getItemViewType(final int n) {
        if (n == 0) {
            return 3;
        }
        if (n < this.mQueryResult.size() + 1) {
            return 0;
        }
        if (!this.mQueryResult.isEmpty() && n == this.mQueryResult.size() + 1) {
            return 1;
        }
        return 2;
    }
    
    public boolean hasEmptyQueries() {
        return this.mQueryResult.isEmpty() && this.mQueryResultContacts.isEmpty() && !this.mQuery.equals("");
    }
    
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int n) {
        switch (this.getItemViewType(n)) {
            default: {}
            case 3: {
                ((SearchWebSearchViewHolder)viewHolder).clickSpace.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                    public void onClick(final View view) {
                        SearchResultAdapter.this.startWebSearch();
                    }
                });
                ((SearchWebSearchViewHolder)viewHolder).searchText.setText((CharSequence)("Websearch for \"" + this.mQuery + "\""));
            }
            case 0: {
                final Application application = this.mQueryResult.get(n - 1);
                final FrameLayout frameLayout = (FrameLayout)viewHolder.itemView;
                final GridLayoutManager.LayoutParams layoutParams = new GridLayoutManager.LayoutParams(-1, LauncherUtils.dpToPx(84.0f, this.mContext));
                frameLayout.removeAllViews();
                final AppIconView appIconView = (AppIconView)application.createDefaultView(this.mContext);
                appIconView.overrideData(56);
                appIconView.setIconSizeInDP(56);
                appIconView.setIcon(appIconView.getIcon());
                frameLayout.addView((View)appIconView);
                frameLayout.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
                frameLayout.setBackgroundColor(-1);
            }
            case 2: {
                final SearchContactViewHolder searchContactViewHolder = (SearchContactViewHolder)viewHolder;
                final ContactUtil.Contact contact = this.mQueryResultContacts.get(this.getContactIndex(n));
                if (contact.mThumbnailUri != null) {
                    searchContactViewHolder.image.setImageTintList((ColorStateList)null);
                    searchContactViewHolder.image.setImageURI(contact.mThumbnailUri);
                    searchContactViewHolder.image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
                else {
                    searchContactViewHolder.image.setImageTintList(ColorStateList.valueOf(-1));
                    searchContactViewHolder.image.setBackgroundColor(14575885);
                    searchContactViewHolder.image.setImageResource(R.drawable.ic_person_black_24dp);
                    searchContactViewHolder.image.setScaleType(ImageView.ScaleType.CENTER);
                }
                searchContactViewHolder.nameLabel.setText((CharSequence)contact.mName);
                searchContactViewHolder.itemView.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
                    public void onClick(final View view) {
                        final Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setData(Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, contact.mLookupKey));
                        SearchResultAdapter.this.mContext.startActivity(intent);
                    }
                });
            }
        }
    }
    
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int n) {
        switch (n) {
            default: {
                return new SearchResultViewHolder(this.mContext);
            }
            case 0: {
                return new SearchResultViewHolder(this.mContext);
            }
            case 1: {
                return new SearchSeparatorViewHolder(this.mContext, viewGroup);
            }
            case 2: {
                return new SearchContactViewHolder(this.mContext, viewGroup);
            }
            case 3: {
                return new SearchWebSearchViewHolder(this.mContext, viewGroup);
            }
        }
    }
    
    public void setQuery(final String mQuery) {
        this.mQuery = mQuery;
        ((RecyclerView.Adapter)this).notifyItemChanged(0);
    }
    
    public void startWebSearch() {
        this.mContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.google.com/#q=" + this.mQuery)));
    }
    
    public static class SearchContactViewHolder extends ViewHolder
    {
        RoundImageView image;
        TextView nameLabel;
        
        public SearchContactViewHolder(final Context context, final ViewGroup viewGroup) {
            super(LayoutInflater.from(context).inflate(R.layout.search_result_contact, viewGroup, false));
            this.nameLabel = (TextView)this.itemView.findViewById(R.id.contact_name);
            this.image = (RoundImageView)this.itemView.findViewById(R.id.contact_img);
        }
    }
    
    public static class SearchResultViewHolder extends ViewHolder
    {
        public SearchResultViewHolder(final Context context) {
            super((View)new FrameLayout(context));
        }
    }
    
    public static class SearchSeparatorViewHolder extends ViewHolder
    {
        public SearchSeparatorViewHolder(final Context context, final ViewGroup viewGroup) {
            super(LayoutInflater.from(context).inflate(R.layout.search_result_separator, viewGroup, false));
        }
    }
    
    public static class SearchWebSearchViewHolder extends ViewHolder
    {
        View clickSpace;
        TextView searchText;
        
        public SearchWebSearchViewHolder(final Context context, final ViewGroup viewGroup) {
            super(LayoutInflater.from(context).inflate(R.layout.search_result_websearch, viewGroup, false));
            this.clickSpace = this.itemView.findViewById(R.id.search_button);
            this.searchText = (TextView)this.itemView.findViewById(R.id.searchText);
        }
    }
}
