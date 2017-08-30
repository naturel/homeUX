// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_helpers.notifications;

import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import java.util.TreeMap;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat;
import java.util.Map;
import android.content.Context;
import com.dravite.homeux.R;

public class Notifications
{
    public static final int ID_APPS_INSTALLED = 1;
    Context mContext;
    Map<Integer, NotificationCompat.Builder> mNotificationBuilders;
    NotificationManagerCompat mNotificationManager;
    
    public Notifications(final Context mContext) {
        this.mNotificationBuilders = new TreeMap<Integer, NotificationCompat.Builder>();
        this.mContext = mContext;
        this.mNotificationManager = NotificationManagerCompat.from(mContext);
        this.mNotificationBuilders.put(1, new NotificationCompat.Builder(mContext).setSmallIcon(2130837617).setColor(ContextCompat.getColor(mContext, R.color.colorPrimary)).setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher)));
    }
    
    public void show(final int n, final Object o) {
        switch (n) {
            default: {}
            case 1: {
                final NotificationCompat.Builder builder = this.mNotificationBuilders.get(1);
                final StringBuilder append = new StringBuilder().append((int)o).append(" new app");
                String s;
                if ((int)o == 1) {
                    s = "";
                }
                else {
                    s = "s";
                }
                builder.setContentTitle(append.append(s).append(" detected").toString()).setContentText("Click to put them into folders.");
                this.mNotificationManager.notify(1, this.mNotificationBuilders.get(1).build());
            }
        }
    }
}
