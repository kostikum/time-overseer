package com.kostikum.timeoverseer.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.View;

import androidx.annotation.Nullable;

import com.kostikum.timeoverseer.R;
import com.kostikum.timeoverseer.db.entity.ProcessWithProject;

import java.util.ArrayList;
import java.util.List;

public class DatePieView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private List<Pair<Float, Integer>> degreesAndColorsList = new ArrayList<>();

    RectF rectf = new RectF(10, 10, 400, 400);

    public DatePieView(Context context) {
        super(context);
    }

    public DatePieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DatePieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int min = Math.min(w, h);
        if (min == w) {
            rectf = new RectF(10, 10, min - 10, min - 10);
        } else {
            rectf = new RectF(10, 10, min / 3, min / 3);
        }
    }

    public void setData(List<Pair<Long, Integer>> list) {

        float total = 0;
        for (Pair<Long, Integer> pair : list) {
            total += pair.first;
        }
        for (Pair<Long, Integer> pair : list) {
            degreesAndColorsList.add(new Pair<>(360 * pair.first / total, pair.second));
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float tempo = 0f;

        if (!degreesAndColorsList.isEmpty()) {
            for (Pair<Float, Integer> pair : degreesAndColorsList) {
                paint.setColor(getResources().getColor(pair.second));
                canvas.drawArc(rectf, tempo, pair.first, true, paint);
                tempo += pair.first;
            }
        }
    }
}
