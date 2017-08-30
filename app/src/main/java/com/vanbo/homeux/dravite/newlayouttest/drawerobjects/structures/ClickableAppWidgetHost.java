// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures;

import android.os.TransactionTooLargeException;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.appwidget.AppWidgetHost;

public class ClickableAppWidgetHost extends AppWidgetHost
{
    public ClickableAppWidgetHost(final Context context, final int n) {
        super(context, n);
    }
    
    protected AppWidgetHostView onCreateView(final Context context, final int n, final AppWidgetProviderInfo appWidgetProviderInfo) {
        return new ClickableAppWidgetHostView(context);
    }
    
    public void startListening() {
        try {
            super.startListening();
        }
        catch (Exception ex) {
            if (!(ex.getCause() instanceof TransactionTooLargeException)) {
                throw new RuntimeException(ex);
            }
        }
    }
    
    public void stopListening() {
        super.stopListening();
        this.clearViews();
    }
}
