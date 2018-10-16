package com.miracle.michael.naoh.common.other;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;
import android.view.View;

public abstract class RoundedBackgroundSpan extends ReplacementSpan {
    private static int CORNER_RADIUS = 10;
    private int backgroundColor;
    private int textColor;

    public RoundedBackgroundSpan(int backgroundColor, int textColor) {
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {

        return Math.round(paint.measureText(text, start, end));
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y,
                     int bottom, Paint paint) {
        RectF rectF = new RectF(x, top - 8, x + measureText(paint, text, start, end) - 10, y + 16);
        paint.setColor(backgroundColor);
        canvas.drawRoundRect(rectF, CORNER_RADIUS, CORNER_RADIUS, paint);
        paint.setColor(textColor);

        canvas.drawText(text, start, end, x, y, paint);
    }

    private float measureText(Paint paint, CharSequence text, int start, int end) {
        return paint.measureText(text, start, end);
    }

    public abstract void onClick(View view);
}

