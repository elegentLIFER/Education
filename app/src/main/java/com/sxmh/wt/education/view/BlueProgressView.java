package com.sxmh.wt.education.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sxmh.wt.education.R;

public class BlueProgressView extends View {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 20;
    private Paint paint;
    private int rank;

    public BlueProgressView(Context context) {
        super(context);
        initWork();
    }

    public BlueProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWork();
    }

    public BlueProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWork();
    }

    private void initWork() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getLastMeasure(widthMeasureSpec, DEFAULT_WIDTH), getLastMeasure(heightMeasureSpec, DEFAULT_HEIGHT));
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();

        paint.setColor(getContext().getResources().getColor(R.color.colorBGGray));
        RectF rectF = new RectF(0, 0, width, height);
        canvas.drawRoundRect(rectF, height / 2, height / 2, paint);

        paint.setColor(getContext().getResources().getColor(R.color.colorMainBlue));
        RectF rectFBlue = new RectF(0, 0, width / 100 * rank, height);
        canvas.drawRoundRect(rectFBlue, height / 2, height / 2, paint);
    }

    public void setRank(int rank) {
        this.rank = rank;
        invalidate();
    }
}
