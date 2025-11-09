package com.example.rab4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DrawingView extends View {

    private Paint bgPaint;
    private Paint starPaint;
    private Paint planetPaint;
    private Paint rocketPaint;
    private Paint strokePaint;
    private Paint windowPaint;
    private Paint flamePaint;
    private Paint earthPaint;
    private Paint landPaint;

    public DrawingView(Context context) {
        super(context);
        init();
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private float dp(float v) {
        return v * getResources().getDisplayMetrics().density;
    }

    private void init() {
        setLayerType(LAYER_TYPE_HARDWARE, null);

        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.rgb(18, 35, 112)); // тёмно-синий фон

        starPaint = new Paint();
        starPaint.setStyle(Paint.Style.FILL);
        starPaint.setColor(Color.WHITE);

        planetPaint = new Paint();
        planetPaint.setStyle(Paint.Style.FILL);

        rocketPaint = new Paint();
        rocketPaint.setStyle(Paint.Style.FILL);
        rocketPaint.setColor(Color.LTGRAY);

        strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(dp(2));
        strokePaint.setColor(Color.BLACK);

        windowPaint = new Paint();
        windowPaint.setStyle(Paint.Style.FILL);
        windowPaint.setColor(Color.BLACK);

        flamePaint = new Paint();
        flamePaint.setStyle(Paint.Style.FILL);
        flamePaint.setColor(Color.rgb(255, 120, 10)); // оранжево-красный

        earthPaint = new Paint();
        earthPaint.setStyle(Paint.Style.FILL);
        earthPaint.setColor(Color.rgb(60, 140, 220)); // синий для Земли

        landPaint = new Paint();
        landPaint.setStyle(Paint.Style.FILL);
        landPaint.setColor(Color.rgb(55, 120, 40)); // зелёные континенты
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        int w = getWidth();
        int h = getHeight();
        if (w == 0 || h == 0) return;

        // фон
        canvas.drawRect(0, 0, w, h, bgPaint);

        // звёзды
        drawStars(canvas, w, h);

        // Луна
        planetPaint.setColor(Color.LTGRAY);
        float moonRadius = w * 0.18f;
        float moonCx = -moonRadius * 0.25f;
        float moonCy = moonRadius * 0.25f;
        canvas.drawCircle(moonCx, moonCy, moonRadius, planetPaint);
        canvas.drawCircle(moonCx, moonCy, moonRadius, strokePaint);


        // Планеты
        // Красная планета
        final float redCx = w * 0.78f;
        final float redCy = h * 0.12f;
        final float redR = w * 0.10f;
        planetPaint.setColor(Color.rgb(180, 40, 40));
        canvas.drawCircle(redCx, redCy, redR, planetPaint);
        canvas.drawCircle(redCx, redCy, redR, strokePaint);

        //Ломаные линии
        Paint yellowPaint = new Paint();
        yellowPaint.setStyle(Paint.Style.STROKE);
        yellowPaint.setStrokeWidth(dp(2));
        yellowPaint.setColor(Color.YELLOW);

        //3шт
        for (int line = 0; line < 3; line++) {
            Path zig = new Path();
            float startX = redCx - redR * 0.9f;
            float endX = redCx + redR * 0.9f;
            float baseY = redCy + (line - 1) * redR * 0.18f;

            // 6 точек (ломаная)
            int segments = 6;
            for (int i = 0; i <= segments; i++) {
                float t = i / (float) segments;
                float x = startX + t * (endX - startX);
                // У колеблется по синусоиде внутри радиуса, но ограничено так, чтобы линия лежала на планете
                float y = baseY + (float) Math.sin(t * Math.PI * 4 + line) * redR * 0.18f;
                if (i == 0) zig.moveTo(x, y);
                else zig.lineTo(x, y);
            }
            canvas.drawPath(zig, yellowPaint);
        }


        // фиолетовая планета (с кратерами)
        final float p2Cx = w * 0.72f;
        final float p2Cy = h * 0.42f;
        final float p2R = w * 0.06f;
        planetPaint.setColor(Color.rgb(120, 80, 140));
        canvas.drawCircle(p2Cx, p2Cy, p2R, planetPaint);
        canvas.drawCircle(p2Cx, p2Cy, p2R, strokePaint);
        // кратеры
        float iR = p2R * 0.12f;
        float oR = p2R * 0.18f;
        float a1x = p2Cx - p2R * 0.20f;
        float a1y = p2Cy - p2R * 0.10f;
        planetPaint.setColor(Color.rgb(90, 60, 100));
        canvas.drawCircle(a1x, a1y, iR, planetPaint);
        planetPaint.setColor(Color.rgb(150, 120, 170));
        canvas.drawCircle(a1x, a1y, oR, planetPaint);

        float a2x = p2Cx + p2R * 0.18f;
        float a2y = p2Cy + p2R * 0.12f;
        planetPaint.setColor(Color.rgb(85, 55, 95));
        canvas.drawCircle(a2x, a2y, iR * 0.95f, planetPaint);
        planetPaint.setColor(Color.rgb(160, 130, 175));
        canvas.drawCircle(a2x, a2y, oR * 0.95f, planetPaint);


        // голубая планета
        final float p3Cx = w * 0.55f;
        final float p3Cy = h * 0.08f;
        final float p3R = w * 0.05f;
        planetPaint.setColor(Color.rgb(120, 200, 220));
        canvas.drawCircle(p3Cx, p3Cy, p3R, planetPaint);
        canvas.drawCircle(p3Cx, p3Cy, p3R, strokePaint);


        // вернуть strokePaint в чёрный
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStrokeWidth(dp(2));

        // Земля
        float earthRadius = Math.min(w, h) * 0.55f;
        float earthCx = w * 0.5f;
        float earthCy = h - earthRadius * 0.25f; // центр немного выше низа

        canvas.drawCircle(earthCx, earthCy, earthRadius, earthPaint);
        canvas.drawCircle(earthCx, earthCy, earthRadius, strokePaint);

        // Континент — многоугольник
        // Вспомогательный код: создаём Path по массивам углов (в градусах) и относительных радиусов точек.
        {
            float[] angles1 = {20, 32, 45, 53, 65, 72, 88, 97, 102, 118, 125, 138, 155, 170, 212, 226, 240};
            float[] rfrac1 = {0.55f, 0.68f, 0.80f, 0.65f, 0.85f, 0.70f, 0.62f, 0.78f, 0.60f, 0.50f, 0.62f, 0.34f, 0.27f, 0.55f, 0.43f, 0.56f, 0.65f};

            Path land1 = new Path();
            for (int i = 0; i < angles1.length; i++) {
                double rad = Math.toRadians(angles1[i]);
                float px = earthCx + (float) (Math.cos(rad) * earthRadius * rfrac1[i]);
                float py = earthCy - (float) (Math.sin(rad) * earthRadius * rfrac1[i]);
                if (i == 0) land1.moveTo(px, py);
                else land1.lineTo(px, py);
            }
            land1.close();
            canvas.drawPath(land1, landPaint);
            canvas.drawPath(land1, strokePaint);
        }


        // Ракета (тело, конус, крылья, иллюминатор, пламя)
        float rocketCenterX = w * 0.35f;
        float rocketCenterY = h * 0.35f;
        float rocketWidth = w * 0.12f;
        float rocketHeight = h * 0.28f;

        // тело — округлый прямоугольник
        RectF body = new RectF(
                rocketCenterX - rocketWidth / 2f, rocketCenterY - rocketHeight / 2f,
                rocketCenterX + rocketWidth / 2f, rocketCenterY + rocketHeight / 2f
        );
        rocketPaint.setColor(Color.LTGRAY);
        canvas.drawRoundRect(body, dp(16), dp(16), rocketPaint);
        canvas.drawRoundRect(body, dp(16), dp(16), strokePaint);

        // носовой конус — простой треугольник
        Path cone = new Path();
        cone.moveTo(body.centerX(), body.top - rocketHeight * 0.15f); // вершина
        cone.lineTo(body.left + dp(4), body.top + dp(2));
        cone.lineTo(body.right - dp(4), body.top + dp(2));
        cone.close();
        canvas.drawPath(cone, rocketPaint);
        canvas.drawPath(cone, strokePaint);

        // верхние крылья
        Path finLeft = new Path();
        finLeft.moveTo(body.left, body.centerY());
        finLeft.lineTo(body.left - rocketWidth * 0.7f, body.centerY() + rocketHeight * 0.18f);
        finLeft.lineTo(body.left + dp(4), body.centerY() + dp(6));
        finLeft.close();
        canvas.drawPath(finLeft, rocketPaint);
        canvas.drawPath(finLeft, strokePaint);

        Path finRight = new Path();
        finRight.moveTo(body.right, body.centerY());
        finRight.lineTo(body.right + rocketWidth * 0.7f, body.centerY() + rocketHeight * 0.18f);
        finRight.lineTo(body.right - dp(4), body.centerY() + dp(6));
        finRight.close();
        canvas.drawPath(finRight, rocketPaint);
        canvas.drawPath(finRight, strokePaint);

        // хвостовые «выступы» снизу
        Path tailLeft = new Path();
        tailLeft.moveTo(body.left + dp(6), body.bottom - dp(6));
        tailLeft.lineTo(body.left - rocketWidth * 0.25f, body.bottom + rocketHeight * 0.20f);
        tailLeft.lineTo(body.left + rocketWidth * 0.05f, body.bottom - dp(6));
        tailLeft.close();
        canvas.drawPath(tailLeft, rocketPaint);
        canvas.drawPath(tailLeft, strokePaint);

        Path tailRight = new Path();
        tailRight.moveTo(body.right - dp(6), body.bottom - dp(6));
        tailRight.lineTo(body.right + rocketWidth * 0.25f, body.bottom + rocketHeight * 0.20f);
        tailRight.lineTo(body.right - rocketWidth * 0.05f, body.bottom - dp(6));
        tailRight.close();
        canvas.drawPath(tailRight, rocketPaint);
        canvas.drawPath(tailRight, strokePaint);

        // иллюминаторы 3шт
        float windowR = rocketWidth * 0.28f;
        canvas.drawCircle(body.centerX(), body.top + rocketHeight * 0.28f, windowR, windowPaint);
        canvas.drawCircle(body.centerX(), body.top + rocketHeight * 0.28f, windowR, strokePaint);

        canvas.drawCircle(body.centerX(), body.top + rocketHeight * 0.28f - dp(35), windowR, windowPaint);
        canvas.drawCircle(body.centerX(), body.top + rocketHeight * 0.28f - dp(35), windowR, strokePaint);

        canvas.drawCircle(body.centerX(), body.top + rocketHeight * 0.28f + dp(35), windowR, windowPaint);
        canvas.drawCircle(body.centerX(), body.top + rocketHeight * 0.28f + dp(35), windowR, strokePaint);

        // Внешний слой (оранжево-красный)
        Path flameOuter = new Path();
        flameOuter.moveTo(body.centerX(), body.bottom + rocketHeight * 0.40f); // нижняя вершина
        flameOuter.lineTo(body.centerX() - rocketWidth * 0.25f, body.bottom + rocketHeight * 0.25f);
        flameOuter.lineTo(body.centerX() - rocketWidth * 0.10f, body.bottom);
        flameOuter.lineTo(body.centerX() + rocketWidth * 0.10f, body.bottom);
        flameOuter.lineTo(body.centerX() + rocketWidth * 0.25f, body.bottom + rocketHeight * 0.25f);
        flameOuter.close();

        flamePaint.setColor(Color.rgb(255, 120, 10));
        canvas.drawPath(flameOuter, flamePaint);
        canvas.drawPath(flameOuter, strokePaint);

        // Внутренний слой (жёлтый)
        Path flameInner = new Path();
        flameInner.moveTo(body.centerX(), body.bottom + rocketHeight * 0.28f);
        flameInner.lineTo(body.centerX() - rocketWidth * 0.15f, body.bottom + rocketHeight * 0.12f);
        flameInner.lineTo(body.centerX(), body.bottom - dp(4));
        flameInner.lineTo(body.centerX() + rocketWidth * 0.15f, body.bottom + rocketHeight * 0.12f);
        flameInner.close();

        flamePaint.setColor(Color.YELLOW);
        canvas.drawPath(flameInner, flamePaint);
        canvas.drawPath(flameInner, strokePaint);
    }

    private void drawStars(Canvas canvas, int w, int h) {
        // Набор координат в относительных долях
        float[][] coords = new float[][]{
                {0.10f, 0.08f}, {0.18f, 0.25f}, {0.30f, 0.15f}, {0.45f, 0.12f},
                {0.60f, 0.20f}, {0.72f, 0.05f}, {0.82f, 0.30f}, {0.90f, 0.18f},
                {0.40f, 0.45f}, {0.22f, 0.40f}, {0.12f, 0.60f}, {0.55f, 0.55f},
                {0.80f, 0.55f}, {0.30f, 0.70f}, {0.15f, 0.30f}
        };
        for (float[] c : coords) {
            float cx = c[0] * w;
            float cy = c[1] * h;
            float r = dp((int) (1 + (c[0] * 3))); // разного размера
            canvas.drawCircle(cx, cy, r, starPaint);
        }
    }
}
