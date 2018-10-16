package com.miracle.michael.naoh.common.other;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

public class ClickableMovementMethod extends LinkMovementMethod {
    private static ClickableMovementMethod sInstance;
    public static ClickableMovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new ClickableMovementMethod();
        }
        return sInstance;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            RoundedBackgroundSpan[] roundedBackgroundSpans = buffer.getSpans(off, off, RoundedBackgroundSpan.class);
            if (roundedBackgroundSpans.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    roundedBackgroundSpans[0].onClick(widget);
                } else if (action == MotionEvent.ACTION_DOWN) {
                    Selection.setSelection(buffer, buffer.getSpanStart(roundedBackgroundSpans[0]), buffer.getSpanEnd(roundedBackgroundSpans[0]));
                }
                return true;
            } else {
                Selection.removeSelection(buffer);
            }
        }
        return false;
    }
}

