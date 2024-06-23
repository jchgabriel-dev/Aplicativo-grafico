package com.example.idnp_lab06;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import java.sql.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class GraficoCircular extends View {
    private float mWidth;
    private float mHeight;
    private Paint mDialPaint;
    private Paint mTextPaint;
    private float mRadius;
    private HashMap<String, Float> paises;

    public GraficoCircular(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mDialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDialPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(40f);
    }

    private void iniciarPaises(){
        paises = new HashMap<String, Float>();
        paises.put("Argentina", 20.7f);
        paises.put("Bolivia", 46.6f);
        paises.put("Brazil", 28.6f);
        paises.put("Canada", 14.5f);
        paises.put("Chile", 23.4f);
        paises.put("Columbia", 27.4f);
        paises.put("Ecuador", 32.9f);
        paises.put("Guyana", 28.3f);
        paises.put("Mexico", 29f);
        paises.put("Paraguay", 34.8f);
        paises.put("Peru", 32.9f);
        paises.put("U.S.A.", 16.7f);
        paises.put("Uruguay", 18f);
        paises.put("Venezuela", 27.5f);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        mRadius = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Random rnd = new Random();
        DecimalFormat df = new DecimalFormat("#");

        float mX = mWidth/2;
        float mY = mHeight/2;
        float mR = mRadius/1.3f;
        mDialPaint.setStrokeWidth(mRadius * 0.6f);

        RectF oval = new RectF(mX-mR, mY-mR, mX+mR, mY+mR);
        iniciarPaises();

        float sumaTotal = 0;
        for (Map.Entry<String, Float> set : paises.entrySet()) {
            sumaTotal += set.getValue();
        }

        float porcentaje;
        float startAngle = 0;
        float sweepAngle;
        float valorMedio;
        String text;
        for (Map.Entry<String, Float> set : paises.entrySet()) {
            mDialPaint.setARGB(255, rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(100));;
            porcentaje = set.getValue() * 100 / sumaTotal;
            sweepAngle = set.getValue() * 360 / sumaTotal;
            canvas.drawArc(oval, startAngle, sweepAngle,false, mDialPaint);
            valorMedio = (float) (2 * Math.PI * ((startAngle + startAngle + sweepAngle)/ 2)/ 360);
            startAngle += sweepAngle;
            text = df.format(porcentaje)+"%";
            canvas.drawText(text, (float)(mR*Math.cos(valorMedio))+mX, (float)(mR*Math.sin(valorMedio))+mY,mTextPaint);
        }
    }
}
