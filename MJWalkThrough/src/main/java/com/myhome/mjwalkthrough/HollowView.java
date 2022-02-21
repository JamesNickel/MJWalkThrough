package com.myhome.mjwalkthrough;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.core.content.ContextCompat;

public class HollowView extends View {

    private ShapeDrawable drawableLeft, drawableTop, drawableRight, drawableBottom;

    public HollowView(Context context) {
        super(context);
    }

    public void setParams(int width, int height, int left, int top){
        Context context = getContext();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int color = ContextCompat.getColor(context, R.color.bg_opposite_shader);
        //textSizeTitle = context.getResources().getDimensionPixelSize(R.dimen.text_large);
        //textSizeDesc = context.getResources().getDimensionPixelSize(R.dimen.text_main);
        setContentDescription("");

        drawableLeft = new ShapeDrawable(new RectShape());
        drawableLeft.getPaint().setColor(color);
        drawableLeft.getPaint().setStyle(Paint.Style.FILL);
        //drawableLeft.getPaint().setStrokeWidth(border);
        drawableLeft.setBounds(0, 0, left, displayMetrics.heightPixels);

        drawableTop = new ShapeDrawable(new RectShape());
        drawableTop.getPaint().setColor(color);
        drawableTop.getPaint().setStyle(Paint.Style.FILL);
        //drawableTop.getPaint().setStrokeWidth(border);
        drawableTop.setBounds(left, 0, left + width, top);

        drawableRight = new ShapeDrawable(new RectShape());
        drawableRight.getPaint().setColor(color);
        drawableRight.getPaint().setStyle(Paint.Style.FILL);
        //drawableRight.getPaint().setStrokeWidth(border);
        drawableRight.setBounds(left + width, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);

        drawableBottom = new ShapeDrawable(new RectShape());
        drawableBottom.getPaint().setColor(color);
        drawableBottom.getPaint().setStyle(Paint.Style.FILL);
        //drawableBottom.getPaint().setStrokeWidth(border);
        drawableBottom.setBounds(left, top + height, left + width, displayMetrics.heightPixels);

        //paintTitle = new Paint();
        //paintTitle.setStyle(Paint.Style.FILL);
        //paintTitle.setColor(ContextCompat.getColor(context, R.color.orange));
        //paintTitle.setTextSize(textSizeTitle);
        ////paintTitle.setTextAlign(Paint.Align.RIGHT);
        //paintDesc = new Paint();
        //paintDesc.setStyle(Paint.Style.FILL);
        //paintDesc.setColor(ContextCompat.getColor(context, R.color.text_origin_primary));
        //paintDesc.setTextSize(textSizeDesc);
        ////paintDesc.setTextAlign(Paint.Align.RIGHT);
    }

    protected void onDraw(Canvas canvas) {
        drawableLeft.draw(canvas);
        drawableTop.draw(canvas);
        drawableRight.draw(canvas);
        drawableBottom.draw(canvas);

        //canvas.drawPaint(paint);
        //canvas.drawText(captionTitle, displayMetrics.widthPixels - (captionTitle.length() * textSizeTitle), 200, paintTitle);
        //canvas.drawText(captionDesc, displayMetrics.widthPixels - (captionDesc.length() * textSizeDesc), 400, paintDesc);
    }
}
