// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.general_adapters;

import android.widget.ImageView;
import android.support.annotation.DrawableRes;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import java.util.List;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import com.dravite.homeux.R;

public class AllDrawableAdapter extends RecyclerView.Adapter<AllDrawableAdapter.DrawableViewHolder>
{
    private boolean hasLoaded;
    private Context mContext;
    private RecyclerView mList;
    private OnItemClickListener mOnItemClickListener;
    private List<Integer> mResList;

    public AllDrawableAdapter(final Context p0, final RecyclerView p1, final OnItemClickListener p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: iconst_0       
        //     1: istore          4
        //     3: aload_0        
        //     4: invokespecial   android/support/v7/widget/RecyclerView.Adapter.<init>:()V
        //     7: aload_0        
        //     8: new             Ljava/util/ArrayList;
        //    11: dup            
        //    12: invokespecial   java/util/ArrayList.<init>:()V
        //    15: putfield        com/dravite/newlayouttest/general_adapters/AllDrawableAdapter.mResList:Ljava/util/List;
        //    18: aload_0        
        //    19: iconst_0       
        //    20: putfield        com/dravite/newlayouttest/general_adapters/AllDrawableAdapter.hasLoaded:Z
        //    23: aload_0        
        //    24: aload_1        
        //    25: putfield        com/dravite/newlayouttest/general_adapters/AllDrawableAdapter.mContext:Landroid/content/Context;
        //    28: aload_0        
        //    29: aload_2        
        //    30: putfield        com/dravite/newlayouttest/general_adapters/AllDrawableAdapter.mList:Landroid/support/v7/widget/RecyclerView;
        //    33: ldc             Lcom/dravite/newlayouttest/R.drawable;.class
        //    35: invokevirtual   java/lang/Class.getFields:()[Ljava/lang/reflect/Field;
        //    38: astore_2       
        //    39: aload_2        
        //    40: arraylength    
        //    41: istore          5
        //    43: iload           4
        //    45: iload           5
        //    47: if_icmpge       110
        //    50: aload_2        
        //    51: iload           4
        //    53: aaload         
        //    54: astore_1       
        //    55: aload_1        
        //    56: invokevirtual   java/lang/reflect/Field.getName:()Ljava/lang/String;
        //    59: ldc             "ic_"
        //    61: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //    64: ifeq            93
        //    67: ldc             "call"
        //    69: ldc             "Add mRest list"
        //    71: invokestatic    android/util/Log.e:(Ljava/lang/String;Ljava/lang/String;)I
        //    74: pop            
        //    75: aload_0        
        //    76: getfield        com/dravite/newlayouttest/general_adapters/AllDrawableAdapter.mResList:Ljava/util/List;
        //    79: aload_1        
        //    80: aconst_null    
        //    81: invokevirtual   java/lang/reflect/Field.getInt:(Ljava/lang/Object;)I
        //    84: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    87: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    92: pop            
        //    93: iload           4
        //    95: iconst_1       
        //    96: iadd           
        //    97: istore          4
        //    99: goto            43
        //   102: astore_1       
        //   103: aload_1        
        //   104: invokestatic    com/dravite/newlayouttest/general_helpers/ExceptionLog.w:(Ljava/lang/Exception;)V
        //   107: goto            93
        //   110: aload_0        
        //   111: iconst_1       
        //   112: putfield        com/dravite/newlayouttest/general_adapters/AllDrawableAdapter.hasLoaded:Z
        //   115: aload_0        
        //   116: aload_3        
        //   117: putfield        com/dravite/newlayouttest/general_adapters/AllDrawableAdapter.mOnItemClickListener:Lcom/dravite/newlayouttest/general_adapters/AllDrawableAdapter.OnItemClickListener;
        //   120: return         
        //   121: astore_1       
        //   122: goto            103
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                
        //  -----  -----  -----  -----  ------------------------------------
        //  55     93     121    125    Ljava/lang/IllegalArgumentException;
        //  55     93     102    103    Ljava/lang/IllegalAccessException;
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.assembler.ir.StackMappingVisitor.push(StackMappingVisitor.java:290)
        //     at com.strobel.assembler.ir.StackMappingVisitor.InstructionAnalyzer.execute(StackMappingVisitor.java:833)
        //     at com.strobel.assembler.ir.StackMappingVisitor.InstructionAnalyzer.visit(StackMappingVisitor.java:398)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2030)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:692)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:529)
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
    
    @Override
    public int getItemCount() {
        if (this.hasLoaded) {
            return this.mResList.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(final DrawableViewHolder drawableViewHolder, final int n) {
        final ImageButton imageButton = (ImageButton)drawableViewHolder.itemView;
        drawableViewHolder.loadImage(this.mResList.get(n), n / 6);
        imageButton.setOnClickListener((View.OnClickListener)new View.OnClickListener() {
            public void onClick(final View view) {
                if (AllDrawableAdapter.this.mOnItemClickListener != null) {
                    AllDrawableAdapter.this.mOnItemClickListener.onItemClick(AllDrawableAdapter.this.mContext.getDrawable((int)AllDrawableAdapter.this.mResList.get(((RecyclerView.ViewHolder)drawableViewHolder).getAdapterPosition())), ((RecyclerView.ViewHolder)drawableViewHolder).getAdapterPosition(), AllDrawableAdapter.this.mResList.get(((RecyclerView.ViewHolder)drawableViewHolder).getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public DrawableViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
        return new DrawableViewHolder(View.inflate(this.mContext, R.layout.icon_item, (ViewGroup)null));
    }
    
    class DrawableViewHolder extends RecyclerView.ViewHolder
    {
        Drawable icon;
        
        public DrawableViewHolder(final View view) {
            super(view);
        }
        
        void loadImage(@DrawableRes final int imageResource, final int n) {
            ((ImageView)this.itemView).setImageResource(imageResource);
            ((ImageView)this.itemView).setAlpha(0.57f);
            ((ImageView)this.itemView).setClickable(true);
        }
    }
    
    public interface OnItemClickListener
    {
        void onItemClick(final Drawable p0, final int p1, final int p2);
    }
}
