package com.sxmh.wt.education.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.sxmh.wt.education.R;
import com.sxmh.wt.education.util.NUtil;

public class OptionView extends AppCompatCheckBox {
    private Paint paint;
    public static final int DEFAULT_W_H = 70;

    public OptionView(Context context) {
        super(context);
        initWork();
    }

    public OptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWork();
    }

    public OptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWork();
    }

    private void initWork() {
        initPaint();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.colorMainBlue));
    }

    private int getLastMeasure(int measureSpec, int defaultValue) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                return size;
            case MeasureSpec.AT_MOST:
                return defaultValue;
            case MeasureSpec.UNSPECIFIED:
                return defaultValue;
        }
        return defaultValue;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getLastMeasure(widthMeasureSpec, DEFAULT_W_H), getLastMeasure(widthMeasureSpec, DEFAULT_W_H));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        String text = getText().toString();
        Paint.FontMetrics fm = paint.getFontMetrics();
        float textW = paint.measureText(text);

        RectF rectF = new RectF(1, 1, width-1, width-1);

        paint.setColor(getResources().getColor(R.color.colorMainBlue));
        if (isChecked()) {
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawRoundRect(rectF, NUtil.dp2px(5), NUtil.dp2px(5), paint);
            paint.setColor(Color.WHITE);
        } else {
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRoundRect(rectF, 10, 10, paint);
        }

        paint.setTextSize(getResources().getDimension(R.dimen.base_text_size));
        canvas.drawText(text,
                (width - textW) / 2,
                width / 2 - fm.descent + (fm.descent - fm.ascent) / 2,
                paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        invalidate();
        return false;
//        return super.onTouchEvent(event);
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        invalidate();
    }
}
