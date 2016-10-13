package com.zl.countdownview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * Created by zl on 16/10/12.
 */

public class CountDownView extends View {

    private Paint mPaint;
    private int mWidth;
    private float progress = 360;
    private Paint mBgPaint;
    private Paint mTextPaint;
    private int mBaseLine;
    //    private String s = "跳过";
    private String mText;
    private OnCountDownListener listener;

    public CountDownView(Context context) {
        this(context, null);
    }

    public CountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CountDownView);
        int bgColor = ta.getColor(R.styleable.CountDownView_background_color, Color.GRAY);
        int progressColor = ta.getColor(R.styleable.CountDownView_progress_color, Color.GREEN);
        float textSize = ta.getDimension(R.styleable.CountDownView_text_size, dip2px(context, 10));
        mText = ta.getString(R.styleable.CountDownView_text);
        int textColor = ta.getColor(R.styleable.CountDownView_text_color, Color.WHITE);

        mPaint = new Paint();
        mPaint.setColor(progressColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);

        mBgPaint = new Paint();
        mBgPaint.setColor(bgColor);
        mBgPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setStrokeWidth(4);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(mText, 0, mText.length(), bounds);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mBaseLine = (int) ((mWidth - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF rectF = new RectF(6, 6, mWidth - 6, mWidth - 6);
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2 - 6, mBgPaint);
        canvas.drawArc(rectF, -90, progress, false, mPaint);
        canvas.drawText(mText, mWidth / 2, mWidth / 2 + mBaseLine, mTextPaint);
        super.onDraw(canvas);
    }

    public void start(final int totalTime) {
        if (listener != null){
            listener.start();
        }
        CountDownTimer countDownTimer = new CountDownTimer(totalTime, totalTime / 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                progress = 360 - ((totalTime - millisUntilFinished) / (totalTime + 0.0f)) * 360;
                Log.d("", "progress:" + progress);
                invalidate();
            }

            @Override
            public void onFinish() {
                progress = 0;
                if (listener != null){
                    listener.finish();
                }
                invalidate();
            }

        }.start();
    }

    public void setOnCountDownListener(OnCountDownListener listener){
        this.listener = listener;
    }

    public interface OnCountDownListener{
        void start();
        void finish();
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
