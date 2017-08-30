// 
// Decompiled by Procyon v0.5.30
// 

package com.vanbo.homeux.dravite.newlayouttest.views.viewcomponents;

import android.graphics.ColorFilter;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class TextDrawable extends Drawable
{
    private final Paint bgPaint;
    private int mCornerRadius;
    private int mPadding;
    private Rect mRect;
    private Rect mTextBounds;
    private final Paint paint;
    private final String text;
    
    public TextDrawable(final String text, final int mCornerRadius, final int mPadding) {
        this.mRect = new Rect();
        this.mTextBounds = new Rect();
        this.text = text;
        this.mCornerRadius = mCornerRadius;
        (this.paint = new Paint()).setColor(-1);
        this.paint.setTextSize(33.0f);
        this.paint.setAntiAlias(true);
        this.paint.setFakeBoldText(true);
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setTextAlign(Paint.Align.LEFT);
        this.paint.setTypeface(Typeface.DEFAULT_BOLD);
        (this.bgPaint = new Paint()).setColor(12232092);
        this.bgPaint.setAntiAlias(true);
        this.bgPaint.setShadowLayer(6.0f, 0.5f, 1.0f, -16777216);
        this.bgPaint.setStyle(Paint.Style.FILL);
        this.mPadding = mPadding;
    }
    
    public void draw(final Canvas canvas) {
        this.mRect = this.getBounds();
        this.paint.getTextBounds(this.text, 0, this.text.length(), this.mTextBounds);
        canvas.drawRoundRect((float)this.mRect.left, (float)this.mRect.top, (float)(this.mRect.left + this.mTextBounds.width() + this.mPadding * 4), (float)(this.mRect.top + this.mTextBounds.height() + this.mPadding * 2), (float)this.mCornerRadius, (float)this.mCornerRadius, this.bgPaint);
        canvas.drawText(this.text, (float)(this.mRect.left + this.mPadding * 2), (float)(this.mRect.top + this.mTextBounds.height() + this.mPadding), this.paint);
    }
    
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
    
    public void setAlpha(final int alpha) {
        this.paint.setAlpha(alpha);
    }
    
    public void setColorFilter(final ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
    }
}
