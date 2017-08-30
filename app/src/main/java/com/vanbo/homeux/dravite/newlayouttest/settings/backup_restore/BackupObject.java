// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.settings.backup_restore;

import java.util.ArrayList;

public class BackupObject
{
    public ArrayList<String> backupComponents;
    public String backupDate;
    public String backupName;
    public String backupSize;
    public String madeWithVersion;
    
    public BackupObject(final String backupDate, final String madeWithVersion, final String backupName, final String backupSize, final ArrayList<String> backupComponents) {
        this.backupDate = backupDate;
        this.madeWithVersion = madeWithVersion;
        this.backupName = backupName;
        this.backupSize = backupSize;
        this.backupComponents = backupComponents;
    }
}
