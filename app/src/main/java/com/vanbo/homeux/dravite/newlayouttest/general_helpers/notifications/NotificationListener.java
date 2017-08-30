// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_helpers.notifications;

import com.vanbo.homeux.dravite.newlayouttest.LauncherLog;
import android.content.Context;
import android.content.Intent;
import android.service.notification.StatusBarNotification;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.service.notification.NotificationListenerService;

public class NotificationListener extends NotificationListenerService
{
    private NotificationServiceReceiver mServiceReceiver;
    
    public void onCreate() {
        super.onCreate();
        this.mServiceReceiver = new NotificationServiceReceiver();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.vanbo.homeux.dravite.homeux.NOTIFICATION_LISTENER_SERVICE");
        this.registerReceiver((BroadcastReceiver)this.mServiceReceiver, intentFilter);
    }
    
    public void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver((BroadcastReceiver)this.mServiceReceiver);
    }
    
    public void onNotificationPosted(final StatusBarNotification statusBarNotification) {
        final Intent intent = new Intent("com.vanbo.homeux.dravite.homeux.NOTIFICATION_LISTENER");
        final String[] array = new String[this.getActiveNotifications().length];
        final int[] array2 = new int[this.getActiveNotifications().length];
        for (int i = 0; i < array.length; ++i) {
            array[i] = this.getActiveNotifications()[i].getPackageName();
            array2[i] = this.getActiveNotifications()[i].getNotification().number;
        }
        intent.putExtra("notifications", array);
        intent.putExtra("numbers", array2);
        this.sendBroadcast(intent);
    }
    
    public void onNotificationRemoved(final StatusBarNotification statusBarNotification) {
        final Intent intent = new Intent("com.vanbo.homeux.dravite.homeux.NOTIFICATION_LISTENER");
        final String[] array = new String[this.getActiveNotifications().length];
        final int[] array2 = new int[this.getActiveNotifications().length];
        for (int i = 0; i < array.length; ++i) {
            array[i] = this.getActiveNotifications()[i].getPackageName();
            array2[i] = this.getActiveNotifications()[i].getNotification().number;
        }
        intent.putExtra("notifications", array);
        intent.putExtra("numbers", array2);
        this.sendBroadcast(intent);
    }
    
    public class NotificationServiceReceiver extends BroadcastReceiver
    {
        public void onReceive(final Context context, final Intent intent) {
            final Intent intent2 = new Intent("com.vanbo.homeux.dravite.homeux.NOTIFICATION_LISTENER");
            try {
                if (NotificationListener.this.getActiveNotifications() == null) {
                    return;
                }
                final String[] array = new String[NotificationListener.this.getActiveNotifications().length];
                final int[] array2 = new int[NotificationListener.this.getActiveNotifications().length];
                for (int i = 0; i < NotificationListener.this.getActiveNotifications().length; ++i) {
                    array[i] = NotificationListener.this.getActiveNotifications()[i].getPackageName();
                    array2[i] = NotificationListener.this.getActiveNotifications()[i].getNotification().number;
                }
                intent2.putExtra("notifications", array);
                intent2.putExtra("numbers", array2);
                NotificationListener.this.sendBroadcast(intent2);
            }
            catch (RuntimeException ex) {
                LauncherLog.w("Notifications", ex.getMessage());
            }
        }
    }
}
