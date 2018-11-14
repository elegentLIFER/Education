package com.sxmh.wt.education.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import com.sxmh.wt.education.util.NUtil;

public class CardItemView extends AppCompatTextView {
    public static final int CARD_STATUS_NORMAL = 0;
    public static final int CARD_STATUS_DONE = 1;
    public static final int CARD_STATUS_WRONG = 2;
    public static final int CARD_STATUS_RIGHT = 3;

    private Paint paint;
    private int cardStatus;

    public CardItemView(Context context) {
        super(context);
        initWork();
    }

    public CardItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initWork();
    }

    public CardItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWork();
    }

    private void initWork() {
        initPaint();
        setGravity(Gravity.CENTER);
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF rectF = new RectF(1, 1, getWidth() - 1, getHeight() - 1);

        switch (cardStatus) {
            case CARD_STATUS_NORMAL:
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                paint.setColor(Color.LTGRAY);
                setTextColor(Color.WHITE);
                break;
            case CARD_STATUS_DONE:
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.DKGRAY);
                setTextColor(Color.DKGRAY);
                break;
            case CARD_STATUS_RIGHT:
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.GREEN);
                setTextColor(Color.DKGRAY);
                break;
            case CARD_STATUS_WRONG:
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.RED);
                setTextColor(Color.DKGRAY);
                break;
        }
        canvas.drawRoundRect(rectF, NUtil.dp2px(5), NUtil.dp2px(5), paint);
        super.onDraw(canvas);
    }

    public void setCardStatus(int status) {
        cardStatus = status;
        invalidate();
    }
}
