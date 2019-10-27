package com.kostikum.timeoverseer.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DatePieView extends View {
    public DatePieView(Context context) {
        super(context);
    }

    public DatePieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DatePieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float[] value_degree;
    private int[] COLORS = {Color.BLUE, Color.GREEN, Color.GRAY, Color.CYAN, Color.RED};
    RectF rectf = new RectF(10, 10, 200, 200);
    int temp = 0;

    public void setData(float[] values) {
        calculateData(values);
        invalidate();
    }

    private void calculateData(float[] data) {
        float total=0;
        for (int i=0;i<data.length;i++)
        {
            total+=data[i];
        }
        for(int i=0;i<data.length;i++)
        {
            data[i]=360*(data[i]/total);
        }
        value_degree = data;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < value_degree.length; i++) {//values2.length; i++) {
            if (i == 0) {
                paint.setColor(COLORS[i]);
                canvas.drawArc(rectf, 0, value_degree[i], true, paint);
            } else {
                temp += (int) value_degree[i - 1];
                paint.setColor(COLORS[i]);
                canvas.drawArc(rectf, temp, value_degree[i], true, paint);
            }
        }
    }


}
