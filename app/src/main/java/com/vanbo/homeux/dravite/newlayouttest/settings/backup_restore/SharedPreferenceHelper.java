// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.settings.backup_restore;

import java.io.File;
import android.content.Context;

public class SharedPreferenceHelper
{
    public static boolean loadSharedPreferencesFromFile(final Context p0, final File p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: aconst_null    
        //     1: astore_2       
        //     2: aconst_null    
        //     3: astore          4
        //     5: aconst_null    
        //     6: astore_3       
        //     7: new             Ljava/io/ObjectInputStream;
        //    10: dup            
        //    11: new             Ljava/io/FileInputStream;
        //    14: dup            
        //    15: aload_1        
        //    16: invokespecial   java/io/FileInputStream.<init>:(Ljava/io/File;)V
        //    19: invokespecial   java/io/ObjectInputStream.<init>:(Ljava/io/InputStream;)V
        //    22: astore_1       
        //    23: aload_0        
        //    24: invokestatic    android/preference/PreferenceManager.getDefaultSharedPreferences:(Landroid/content/Context;)Landroid/content/SharedPreferences;
        //    27: invokeinterface android/content/SharedPreferences.edit:()Landroid/content/SharedPreferences.Editor;
        //    32: astore_0       
        //    33: aload_0        
        //    34: invokeinterface android/content/SharedPreferences.Editor.clear:()Landroid/content/SharedPreferences.Editor;
        //    39: pop            
        //    40: aload_1        
        //    41: invokevirtual   java/io/ObjectInputStream.readObject:()Ljava/lang/Object;
        //    44: checkcast       Ljava/util/Map;
        //    47: invokeinterface java/util/Map.entrySet:()Ljava/util/Set;
        //    52: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //    57: astore_2       
        //    58: aload_2        
        //    59: invokeinterface java/util/Iterator.hasNext:()Z
        //    64: ifeq            257
        //    67: aload_2        
        //    68: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    73: checkcast       Ljava/util/Map.Entry;
        //    76: astore          4
        //    78: aload           4
        //    80: invokeinterface java/util/Map.Entry.getValue:()Ljava/lang/Object;
        //    85: astore_3       
        //    86: aload           4
        //    88: invokeinterface java/util/Map.Entry.getKey:()Ljava/lang/Object;
        //    93: checkcast       Ljava/lang/String;
        //    96: astore          4
        //    98: aload_3        
        //    99: instanceof      Ljava/lang/Boolean;
        //   102: ifeq            145
        //   105: aload_0        
        //   106: aload           4
        //   108: aload_3        
        //   109: checkcast       Ljava/lang/Boolean;
        //   112: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   115: invokeinterface android/content/SharedPreferences.Editor.putBoolean:(Ljava/lang/String;Z)Landroid/content/SharedPreferences.Editor;
        //   120: pop            
        //   121: goto            58
        //   124: astore_2       
        //   125: aload_1        
        //   126: astore_0       
        //   127: aload_2        
        //   128: astore_1       
        //   129: aload_0        
        //   130: astore_2       
        //   131: aload_1        
        //   132: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   135: aload_0        
        //   136: ifnull          143
        //   139: aload_0        
        //   140: invokevirtual   java/io/ObjectInputStream.close:()V
        //   143: iconst_0       
        //   144: ireturn        
        //   145: aload_3        
        //   146: instanceof      Ljava/lang/Float;
        //   149: ifeq            171
        //   152: aload_0        
        //   153: aload           4
        //   155: aload_3        
        //   156: checkcast       Ljava/lang/Float;
        //   159: invokevirtual   java/lang/Float.floatValue:()F
        //   162: invokeinterface android/content/SharedPreferences.Editor.putFloat:(Ljava/lang/String;F)Landroid/content/SharedPreferences.Editor;
        //   167: pop            
        //   168: goto            58
        //   171: aload_3        
        //   172: instanceof      Ljava/lang/Integer;
        //   175: ifeq            208
        //   178: aload_0        
        //   179: aload           4
        //   181: aload_3        
        //   182: checkcast       Ljava/lang/Integer;
        //   185: invokevirtual   java/lang/Integer.intValue:()I
        //   188: invokeinterface android/content/SharedPreferences.Editor.putInt:(Ljava/lang/String;I)Landroid/content/SharedPreferences.Editor;
        //   193: pop            
        //   194: goto            58
        //   197: astore_0       
        //   198: aload_1        
        //   199: ifnull          206
        //   202: aload_1        
        //   203: invokevirtual   java/io/ObjectInputStream.close:()V
        //   206: aload_0        
        //   207: athrow         
        //   208: aload_3        
        //   209: instanceof      Ljava/lang/Long;
        //   212: ifeq            234
        //   215: aload_0        
        //   216: aload           4
        //   218: aload_3        
        //   219: checkcast       Ljava/lang/Long;
        //   222: invokevirtual   java/lang/Long.longValue:()J
        //   225: invokeinterface android/content/SharedPreferences.Editor.putLong:(Ljava/lang/String;J)Landroid/content/SharedPreferences.Editor;
        //   230: pop            
        //   231: goto            58
        //   234: aload_3        
        //   235: instanceof      Ljava/lang/String;
        //   238: ifeq            58
        //   241: aload_0        
        //   242: aload           4
        //   244: aload_3        
        //   245: checkcast       Ljava/lang/String;
        //   248: invokeinterface android/content/SharedPreferences.Editor.putString:(Ljava/lang/String;Ljava/lang/String;)Landroid/content/SharedPreferences.Editor;
        //   253: pop            
        //   254: goto            58
        //   257: aload_0        
        //   258: invokeinterface android/content/SharedPreferences.Editor.apply:()V
        //   263: aload_1        
        //   264: ifnull          271
        //   267: aload_1        
        //   268: invokevirtual   java/io/ObjectInputStream.close:()V
        //   271: iconst_1       
        //   272: ireturn        
        //   273: astore_0       
        //   274: aload_0        
        //   275: invokevirtual   java/io/IOException.printStackTrace:()V
        //   278: iconst_1       
        //   279: ireturn        
        //   280: astore_0       
        //   281: aload_0        
        //   282: invokevirtual   java/io/IOException.printStackTrace:()V
        //   285: iconst_0       
        //   286: ireturn        
        //   287: astore_1       
        //   288: aload_1        
        //   289: invokevirtual   java/io/IOException.printStackTrace:()V
        //   292: goto            206
        //   295: astore_0       
        //   296: aload_2        
        //   297: astore_1       
        //   298: goto            198
        //   301: astore_1       
        //   302: aload_3        
        //   303: astore_0       
        //   304: goto            129
        //   307: astore_1       
        //   308: aload           4
        //   310: astore_0       
        //   311: goto            319
        //   314: astore_2       
        //   315: aload_1        
        //   316: astore_0       
        //   317: aload_2        
        //   318: astore_1       
        //   319: goto            129
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                              
        //  -----  -----  -----  -----  ----------------------------------
        //  7      23     301    307    Ljava/lang/ClassNotFoundException;
        //  7      23     307    314    Ljava/io/IOException;
        //  7      23     295    301    Any
        //  23     58     124    129    Ljava/lang/ClassNotFoundException;
        //  23     58     314    319    Ljava/io/IOException;
        //  23     58     197    198    Any
        //  58     121    124    129    Ljava/lang/ClassNotFoundException;
        //  58     121    314    319    Ljava/io/IOException;
        //  58     121    197    198    Any
        //  131    135    295    301    Any
        //  139    143    280    287    Ljava/io/IOException;
        //  145    168    124    129    Ljava/lang/ClassNotFoundException;
        //  145    168    314    319    Ljava/io/IOException;
        //  145    168    197    198    Any
        //  171    194    124    129    Ljava/lang/ClassNotFoundException;
        //  171    194    314    319    Ljava/io/IOException;
        //  171    194    197    198    Any
        //  202    206    287    295    Ljava/io/IOException;
        //  208    231    124    129    Ljava/lang/ClassNotFoundException;
        //  208    231    314    319    Ljava/io/IOException;
        //  208    231    197    198    Any
        //  234    254    124    129    Ljava/lang/ClassNotFoundException;
        //  234    254    314    319    Ljava/io/IOException;
        //  234    254    197    198    Any
        //  257    263    124    129    Ljava/lang/ClassNotFoundException;
        //  257    263    314    319    Ljava/io/IOException;
        //  257    263    197    198    Any
        //  267    271    273    280    Ljava/io/IOException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IndexOutOfBoundsException: Index: 157, Size: 157
        //     at java.util.ArrayList.rangeCheck(Unknown Source)
        //     at java.util.ArrayList.get(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertToAst(AstBuilder.java:3303)
        //     at com.strobel.decompiler.ast.AstBuilder.convertToAst(AstBuilder.java:3417)
        //     at com.strobel.decompiler.ast.AstBuilder.convertToAst(AstBuilder.java:3417)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:113)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:138)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static boolean saveSharedPreferencesToFile(final Context p0, final File p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: iconst_0       
        //     1: istore_2       
        //     2: aconst_null    
        //     3: astore          6
        //     5: aconst_null    
        //     6: astore          5
        //     8: aload           6
        //    10: astore          4
        //    12: aload_1        
        //    13: invokevirtual   java/io/File.getParentFile:()Ljava/io/File;
        //    16: invokevirtual   java/io/File.mkdirs:()Z
        //    19: istore_3       
        //    20: iload_3        
        //    21: istore_2       
        //    22: aload           6
        //    24: astore          4
        //    26: iload_3        
        //    27: aload_1        
        //    28: invokevirtual   java/io/File.createNewFile:()Z
        //    31: ior            
        //    32: istore_3       
        //    33: iload_3        
        //    34: istore_2       
        //    35: aload           6
        //    37: astore          4
        //    39: new             Ljava/io/ObjectOutputStream;
        //    42: dup            
        //    43: new             Ljava/io/FileOutputStream;
        //    46: dup            
        //    47: aload_1        
        //    48: invokespecial   java/io/FileOutputStream.<init>:(Ljava/io/File;)V
        //    51: invokespecial   java/io/ObjectOutputStream.<init>:(Ljava/io/OutputStream;)V
        //    54: astore_1       
        //    55: aload_1        
        //    56: aload_0        
        //    57: invokestatic    android/preference/PreferenceManager.getDefaultSharedPreferences:(Landroid/content/Context;)Landroid/content/SharedPreferences;
        //    60: invokeinterface android/content/SharedPreferences.getAll:()Ljava/util/Map;
        //    65: invokevirtual   java/io/ObjectOutputStream.writeObject:(Ljava/lang/Object;)V
        //    68: iconst_1       
        //    69: istore_3       
        //    70: aload_1        
        //    71: ifnull          82
        //    74: aload_1        
        //    75: invokevirtual   java/io/ObjectOutputStream.flush:()V
        //    78: aload_1        
        //    79: invokevirtual   java/io/ObjectOutputStream.close:()V
        //    82: iload_3        
        //    83: ireturn        
        //    84: astore_0       
        //    85: aload_0        
        //    86: invokevirtual   java/io/IOException.printStackTrace:()V
        //    89: iconst_1       
        //    90: ireturn        
        //    91: astore_1       
        //    92: aload           5
        //    94: astore_0       
        //    95: aload_0        
        //    96: astore          4
        //    98: aload_1        
        //    99: invokevirtual   java/io/IOException.printStackTrace:()V
        //   102: iload_2        
        //   103: istore_3       
        //   104: aload_0        
        //   105: ifnull          82
        //   108: aload_0        
        //   109: invokevirtual   java/io/ObjectOutputStream.flush:()V
        //   112: aload_0        
        //   113: invokevirtual   java/io/ObjectOutputStream.close:()V
        //   116: iload_2        
        //   117: ireturn        
        //   118: astore_0       
        //   119: aload_0        
        //   120: invokevirtual   java/io/IOException.printStackTrace:()V
        //   123: iload_2        
        //   124: ireturn        
        //   125: astore_0       
        //   126: aload           4
        //   128: ifnull          141
        //   131: aload           4
        //   133: invokevirtual   java/io/ObjectOutputStream.flush:()V
        //   136: aload           4
        //   138: invokevirtual   java/io/ObjectOutputStream.close:()V
        //   141: aload_0        
        //   142: athrow         
        //   143: astore_1       
        //   144: aload_1        
        //   145: invokevirtual   java/io/IOException.printStackTrace:()V
        //   148: goto            141
        //   151: astore_0       
        //   152: aload_1        
        //   153: astore          4
        //   155: goto            126
        //   158: astore          4
        //   160: aload_1        
        //   161: astore_0       
        //   162: aload           4
        //   164: astore_1       
        //   165: iload_3        
        //   166: istore_2       
        //   167: goto            95
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  12     20     91     95     Ljava/io/IOException;
        //  12     20     125    126    Any
        //  26     33     91     95     Ljava/io/IOException;
        //  26     33     125    126    Any
        //  39     55     91     95     Ljava/io/IOException;
        //  39     55     125    126    Any
        //  55     68     158    170    Ljava/io/IOException;
        //  55     68     151    158    Any
        //  74     82     84     91     Ljava/io/IOException;
        //  98     102    125    126    Any
        //  108    116    118    125    Ljava/io/IOException;
        //  131    141    143    151    Ljava/io/IOException;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0082:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2592)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:138)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
