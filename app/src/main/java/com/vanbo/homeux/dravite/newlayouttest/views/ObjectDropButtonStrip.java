// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views;

import android.view.View.MeasureSpec;
import android.graphics.Rect;
import java.util.List;
import android.view.LayoutInflater;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.FolderStructure;
import com.vanbo.homeux.dravite.newlayouttest.LauncherActivity;
import android.animation.TimeInterpolator;
import android.view.animation.DecelerateInterpolator;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.structures.ClickableAppWidgetHostView;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.graphics.Point;
import com.vanbo.homeux.dravite.newlayouttest.drawerobjects.DrawerObject;
import android.view.View;
import com.vanbo.homeux.dravite.newlayouttest.LauncherUtils;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.content.Context;
import android.view.ViewGroup;
import com.dravite.homeux.R;


public class ObjectDropButtonStrip extends ViewGroup
{
    private WidgetRemoveListener mListener;
    
    public ObjectDropButtonStrip(final Context context) {
        this(context, null);
    }
    
    public ObjectDropButtonStrip(final Context context, final AttributeSet set) {
        this(context, set, 0);
    }
    
    public ObjectDropButtonStrip(final Context context, final AttributeSet set, final int n) {
        this(context, set, n, 0);
    }
    
    public ObjectDropButtonStrip(final Context context, final AttributeSet set, final int n, final int n2) {
        super(context, set, n, n2);
    }
    
    public void addButton(final String text, final String tag, final Drawable drawable) {
        final TextView textView = new TextView(this.getContext());
        drawable.setTint(16777216);
        drawable.setAlpha(145);
        drawable.setBounds(0, 0, LauncherUtils.dpToPx(24.0f, this.getContext()), LauncherUtils.dpToPx(24.0f, this.getContext()));
        textView.setCompoundDrawablesRelative((Drawable)null, drawable, (Drawable)null, (Drawable)null);
        textView.setText((CharSequence)text);
        textView.setTag((Object)tag);
        textView.setPadding(LauncherUtils.dpToPx(4.0f, this.getContext()), LauncherUtils.dpToPx(16.0f, this.getContext()), LauncherUtils.dpToPx(4.0f, this.getContext()), LauncherUtils.dpToPx(4.0f, this.getContext()));
        textView.setGravity(17);
        textView.setTextSize(2, 12.0f);
        this.addView((View)textView);
    }
    
    public String doHover(final DrawerObject drawerObject, final int n, final int n2) {
        if (this.mListener == null) {
            this.exitHover();
            return "nothing";
        }
        if (!this.isContaining(n, n2)) {
            this.exitHover();
            return "nothing";
        }
        final Point position = this.getPosition();
        final View child = this.getChildAt(n - position.x, n2 - position.y);
        if (child == null || child.getTag() == null) {
            this.exitHover();
            return "nothing";
        }
        final WidgetRemoveListener mListener = this.mListener;
        final int x = position.x;
        final int y = position.y;
        int indexOfChild;
        if (child == null) {
            indexOfChild = -1;
        }
        else {
            indexOfChild = this.indexOfChild(child);
        }
        mListener.hovers(drawerObject, n - x, n2 - y, indexOfChild);
        return child.getTag().toString();
    }
    
    public boolean doRemove(final DrawerObject drawerObject, final View view, final String s, final CustomGridLayout customGridLayout, final ViewPager viewPager, final int n, final int n2, final boolean b, @Nullable final Runnable runnable, final boolean b2) {
        if (view instanceof ClickableAppWidgetHostView) {
            if (s.equals("remove")) {
                if (b) {
                    if (b2) {
                        view.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).y((float)LauncherUtils.dpToPx(120.0f, this.getContext())).x(0.0f).setDuration(150L).setInterpolator((TimeInterpolator)new DecelerateInterpolator()).withEndAction((Runnable)new Runnable() {
                            @Override
                            public void run() {
                                if (runnable != null) {
                                    runnable.run();
                                }
                                ObjectDropButtonStrip.this.mListener.removeWidget(drawerObject, (ClickableAppWidgetHostView)view, n, n2);
                            }
                        });
                    }
                    else {
                        view.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(150L).withEndAction((Runnable)new Runnable() {
                            @Override
                            public void run() {
                                if (runnable != null) {
                                    runnable.run();
                                }
                                ObjectDropButtonStrip.this.mListener.removeWidget(drawerObject, (ClickableAppWidgetHostView)view, n, n2);
                            }
                        });
                    }
                }
                else {
                    this.mListener.removeWidget(drawerObject, (ClickableAppWidgetHostView)view, n, n2);
                }
                return true;
            }
            if (s.equals("nothing")) {
                return false;
            }
            this.postDelayed((Runnable)new Runnable() {
                @Override
                public void run() {
                    ObjectDropButtonStrip.this.mListener.doAction(drawerObject, view, s, n, n2, customGridLayout);
                }
            }, 50L);
            return s.contains("appAction") && ((LauncherActivity)this.getContext()).isInAllFolder();
        }
        else {
            if (s.equals("remove")) {
                if (view != null) {
                    if (b) {
                        if (b2) {
                            view.animate().scaleX(0.0f).scaleY(0.0f).y((float)LauncherUtils.dpToPx(120.0f, this.getContext())).x(0.0f).alpha(0.0f).setDuration(150L).setInterpolator((TimeInterpolator)new DecelerateInterpolator()).withEndAction((Runnable)new Runnable() {
                                @Override
                                public void run() {
                                    if (runnable != null) {
                                        runnable.run();
                                    }
                                    if (drawerObject != null) {
                                        final int currentItem = ((LauncherActivity)ObjectDropButtonStrip.this.getContext()).mPager.getCurrentItem();
                                        final LauncherActivity launcherActivity = (LauncherActivity)ObjectDropButtonStrip.this.getContext();
                                        final List<FolderStructure.Folder> folders = LauncherActivity.mFolderStructure.folders;
                                        final LauncherActivity launcherActivity2 = (LauncherActivity)ObjectDropButtonStrip.this.getContext();
                                        if (currentItem == folders.indexOf(LauncherActivity.mFolderStructure.getFolderWithName("All"))) {
                                            ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(((LauncherActivity)ObjectDropButtonStrip.this.getContext()).mPager.getCurrentItem())).pages.get(viewPager.getCurrentItem()).add(customGridLayout.mDragData);
                                            drawerObject.createView(customGridLayout, (LayoutInflater)ObjectDropButtonStrip.this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE), (DrawerObject.OnViewCreatedListener)new DrawerObject.OnViewCreatedListener() {
                                                @Override
                                                public void onViewCreated(final View view) {
                                                    customGridLayout.addObject(view, drawerObject);
                                                }
                                            });
                                        }
                                    }
                                    ObjectDropButtonStrip.this.mListener.removeOther(drawerObject, view, n, n2);
                                }
                            });
                        }
                        else {
                            view.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(150L).withEndAction((Runnable)new Runnable() {
                                @Override
                                public void run() {
                                    if (runnable != null) {
                                        runnable.run();
                                    }
                                    if (drawerObject != null) {
                                        final int currentItem = ((LauncherActivity)ObjectDropButtonStrip.this.getContext()).mPager.getCurrentItem();
                                        final LauncherActivity launcherActivity = (LauncherActivity)ObjectDropButtonStrip.this.getContext();
                                        final List<FolderStructure.Folder> folders = LauncherActivity.mFolderStructure.folders;
                                        final LauncherActivity launcherActivity2 = (LauncherActivity)ObjectDropButtonStrip.this.getContext();
                                        if (currentItem == folders.indexOf(LauncherActivity.mFolderStructure.getFolderWithName("All"))) {
                                            ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(((LauncherActivity)ObjectDropButtonStrip.this.getContext()).mPager.getCurrentItem())).pages.get(viewPager.getCurrentItem()).add(customGridLayout.mDragData);
                                            drawerObject.createView(customGridLayout, (LayoutInflater)ObjectDropButtonStrip.this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE), (DrawerObject.OnViewCreatedListener)new DrawerObject.OnViewCreatedListener() {
                                                @Override
                                                public void onViewCreated(final View view) {
                                                    customGridLayout.addObject(view, drawerObject);
                                                }
                                            });
                                        }
                                    }
                                    ObjectDropButtonStrip.this.mListener.removeOther(drawerObject, view, n, n2);
                                }
                            });
                        }
                    }
                    else {
                        if (drawerObject != null) {
                            final int currentItem = ((LauncherActivity)this.getContext()).mPager.getCurrentItem();
                            final LauncherActivity launcherActivity = (LauncherActivity)this.getContext();
                            final List<FolderStructure.Folder> folders = LauncherActivity.mFolderStructure.folders;
                            final LauncherActivity launcherActivity2 = (LauncherActivity)this.getContext();
                            if (currentItem == folders.indexOf(LauncherActivity.mFolderStructure.getFolderWithName("All"))) {
                                ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(((LauncherActivity)this.getContext()).mPager.getCurrentItem())).pages.get(viewPager.getCurrentItem()).add(customGridLayout.mDragData);
                                drawerObject.createView(customGridLayout, (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE), (DrawerObject.OnViewCreatedListener)new DrawerObject.OnViewCreatedListener() {
                                    @Override
                                    public void onViewCreated(final View view) {
                                        customGridLayout.addObject(view, drawerObject);
                                    }
                                });
                            }
                        }
                        this.mListener.removeOther(drawerObject, view, n, n2);
                    }
                }
                else {
                    this.mListener.removeOther(drawerObject, null, n, n2);
                }
                return true;
            }
            if (s.equals("nothing")) {
                return false;
            }
            if (s.equals("justRemove") && view != null) {
                final int currentItem2 = ((LauncherActivity)this.getContext()).mPager.getCurrentItem();
                final LauncherActivity launcherActivity3 = (LauncherActivity)this.getContext();
                final List<FolderStructure.Folder> folders2 = LauncherActivity.mFolderStructure.folders;
                final LauncherActivity launcherActivity4 = (LauncherActivity)this.getContext();
                if (currentItem2 == folders2.indexOf(LauncherActivity.mFolderStructure.getFolderWithName("All"))) {
                    final LauncherActivity launcherActivity5 = (LauncherActivity)this.getContext();
                    ((FolderStructure.Folder)LauncherActivity.mFolderStructure.folders.get(((LauncherActivity)this.getContext()).mPager.getCurrentItem())).pages.get(viewPager.getCurrentItem()).add(customGridLayout.mDragData);
                    drawerObject.createView(customGridLayout, (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE), (DrawerObject.OnViewCreatedListener)new DrawerObject.OnViewCreatedListener() {
                        @Override
                        public void onViewCreated(final View view) {
                            customGridLayout.addObject(view, drawerObject);
                        }
                    });
                }
            }
            this.postDelayed((Runnable)new Runnable() {
                @Override
                public void run() {
                    ObjectDropButtonStrip.this.mListener.doAction(drawerObject, view, s, n, n2, customGridLayout);
                }
            }, 50L);
            return s.contains("appAction") && ((LauncherActivity)this.getContext()).isInAllFolder();
        }
    }
    
    public boolean doRemoveFolder(final View view, final FolderStructure.Folder folder, final String s) {
        if (s.equals("remove")) {
            this.mListener.removeFolder(view, folder);
            return true;
        }
        if (s.equals("editFolder")) {
            this.mListener.editFolder(view, folder);
            return true;
        }
        return false;
    }
    
    public void exitHover() {
        this.mListener.notHovering();
    }
    
    public View getChildAt(final int n, final int n2) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            final View child = this.getChildAt(i);
            final Rect rect = new Rect();
            child.getHitRect(rect);
            if (rect.contains(n, n2)) {
                return child;
            }
        }
        return null;
    }
    
    public Point getPosition() {
        final int[] array = new int[2];
        this.getLocationInWindow(array);
        return new Point(array[0], array[1]);
    }
    
    public boolean isContaining(final int n, final int n2) {
        final Rect rect = new Rect();
        this.getHitRect(rect);
        return rect.contains(n, n2);
    }
    
    protected void onLayout(final boolean b, final int n, final int n2, final int n3, final int n4) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            final View child = this.getChildAt(i);
            final int n5 = (n3 - n) / this.getChildCount();
            final int n6 = i * n5;
            if (n5 != child.getMeasuredWidth()) {
                child.measure(View.MeasureSpec.makeMeasureSpec(n5, MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(n4 - n2, MeasureSpec.EXACTLY));
            }
            child.layout(n6, 0, n6 + n5, n4 - n2);
        }
    }
    
    protected void onMeasure(final int n, final int n2) {
        super.onMeasure(n, n2);
        this.setPivotY(0.0f);
        this.setPivotX(this.getMeasuredWidth() / 2.0f);
    }
    
    public void setRemoveIcon(final Drawable drawable) {
        final TextView textView = (TextView)this.findViewWithTag((Object)"remove");
        drawable.setTint(16777216);
        drawable.setAlpha(145);
        drawable.setBounds(0, 0, LauncherUtils.dpToPx(24.0f, this.getContext()), LauncherUtils.dpToPx(24.0f, this.getContext()));
        textView.setCompoundDrawablesRelative((Drawable)null, drawable, (Drawable)null, (Drawable)null);
    }
    
    public void setRemoveText(final String text) {
        ((TextView)this.findViewWithTag((Object)"remove")).setText((CharSequence)text);
    }
    
    public void setWidgetRemoveListener(final WidgetRemoveListener mListener) {
        this.mListener = mListener;
    }
    
    public void wipeAllButtons() {
        this.removeAllViews();
    }
    
    public void wipeButtons() {
        this.removeAllViews();
        this.addButton("Remove", "remove", this.getContext().getDrawable(R.drawable.ic_remove_black_24dp));
    }
    
    public interface WidgetRemoveListener
    {
        void doAction(final DrawerObject p0, final View p1, final String p2, final int p3, final int p4, final CustomGridLayout p5);
        
        void editFolder(final View p0, final FolderStructure.Folder p1);
        
        void hovers(final DrawerObject p0, final int p1, final int p2, final int p3);
        
        void notHovering();
        
        void removeFolder(final View p0, final FolderStructure.Folder p1);
        
        void removeOther(final DrawerObject p0, final View p1, final int p2, final int p3);
        
        void removeWidget(final DrawerObject p0, final ClickableAppWidgetHostView p1, final int p2, final int p3);
    }
}
