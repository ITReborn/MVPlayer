package com.itheima.mvplayer32.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.itheima.mvplayer32.model.LyricBean;
import com.itheima.mvplayer32.utils.LyricParser;

import java.util.ArrayList;
import java.util.List;

public class LyricView extends View {
    public static final String TAG = "LyricView";
    private Paint mLyricPaint;

    private float mCenterX;
    private float mCenterY;

    private Rect mLyricRect;
    private static final String SINGLE_LINE_TEST = "Hello Uncle Leon Fan";
    private static final String[] MULTIPLE_LINE_TEST = {"啦","啦啦", "啦啦啦"};

    private float mHighLightTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics());
    private float mNormalTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
    private float mLineHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());

    private int mHighLightPosition = 0;

    private List<LyricBean> mLyrics;
    private float mOffset;

    public LyricView(Context context) {
        this(context, null);
    }

    public LyricView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mLyrics = new ArrayList<LyricBean>();

        mLyricPaint = new Paint();
        mLyricPaint.setAntiAlias(true);
        mLyricPaint.setColor(Color.GREEN);
        mLyricPaint.setTextSize(mHighLightTextSize);
        mLyricRect = new Rect();
    }

    /**
     * layout - > setFrame -> sizeChange -> onSizeChange
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //在onSizeChanged里面初始化一些跟宽高相关的成员变量
        mCenterX = w * 1.0f / 2;
        mCenterY = h * 1.0f / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        drawSingleLine(canvas);
//        drawMultipleLines(canvas);
        for (int i = 0; i < mLyrics.size(); i++) {
            //配置画笔颜色和大小
            if (i == mHighLightPosition) {
                mLyricPaint.setColor(Color.GREEN);
                mLyricPaint.setTextSize(mHighLightTextSize);
            } else {
                mLyricPaint.setColor(Color.WHITE);
                mLyricPaint.setTextSize(mNormalTextSize);
            }
            LyricBean bean = mLyrics.get(i);
            mLyricPaint.getTextBounds(bean.getLyric(), 0, bean.getLyric().length(), mLyricRect);
            float x = mCenterX - mLyricRect.width() / 2;//绘制文本的起始位置
            float y = mCenterY + mLyricRect.height() / 2 + ( i - mHighLightPosition) * mLineHeight - mOffset; //绘制文本baseline
            canvas.drawText(bean.getLyric(), x, y, mLyricPaint);
        }


    }

    private void drawMultipleLines(Canvas canvas) {
        for (int i = 0; i < MULTIPLE_LINE_TEST.length; i++) {
            //配置画笔颜色和大小
            if (i == mHighLightPosition) {
                mLyricPaint.setColor(Color.GREEN);
                mLyricPaint.setTextSize(mHighLightTextSize);
            } else {
                mLyricPaint.setColor(Color.WHITE);
                mLyricPaint.setTextSize(mNormalTextSize);
            }

            mLyricPaint.getTextBounds(MULTIPLE_LINE_TEST[i], 0, MULTIPLE_LINE_TEST[i].length(), mLyricRect);
            float x = mCenterX - mLyricRect.width() / 2;//绘制文本的起始位置
            float y = mCenterY + mLyricRect.height() / 2 + ( i - mHighLightPosition) * mLineHeight; //绘制文本baseline
            canvas.drawText(MULTIPLE_LINE_TEST[i], x, y, mLyricPaint);
        }
    }

    private void drawSingleLine(Canvas canvas) {
        mLyricPaint.getTextBounds(SINGLE_LINE_TEST, 0, SINGLE_LINE_TEST.length(), mLyricRect);
        float x = mCenterX - mLyricRect.width() / 2;//绘制文本的起始位置
        float y = mCenterY + mLyricRect.height() / 2;//绘制文本baseline
        canvas.drawText(SINGLE_LINE_TEST, x, y, mLyricPaint);
    }

    public void setLyricFileName(final String lyricFileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mLyrics = LyricParser.parseLyric(lyricFileName);
                postInvalidate();
            }
        }).start();
    }

    /**
     * 根据当前歌曲的播放进度，找出对应的高亮歌词的位置
     * @param progress
     * @param duration
     */
    public void roll(int progress, int duration) {
        //找出progress对应的高亮歌词的位置
        for (int i = 0; i < mLyrics.size(); i++) {
            int start = mLyrics.get(i).getTimestamp();
            int end = 0;
            //如果遍历到最后一行歌词
            if (i == mLyrics.size() - 1) {
                end = duration;
            } else {
                end = mLyrics.get(i + 1).getTimestamp();
            }
            if (progress > start && progress <= end) {
                mHighLightPosition = i;
                //找出偏移量
                int lineDuration = end - start;//一行歌词的时间
                //根据当前的进度，求出该行歌词已经唱了多长时间
                int passed = progress - start;
                mOffset = passed * 1.0f / lineDuration * mLineHeight;
                invalidate();
                break;
            }
        }
    }
}
