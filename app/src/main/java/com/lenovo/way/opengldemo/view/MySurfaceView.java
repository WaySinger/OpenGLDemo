package com.lenovo.way.opengldemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

/**
 * @author way
 * @data 2017/8/23
 * @description .
 */

public class MySurfaceView extends SurfaceView implements Runnable ,Callback{

    private Thread surfaceViewThread;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private boolean runFlag = false ;
    public static int screen_width , screen_height ;
    protected Resources resources ;
    private Canvas canvas ;
    private Bitmap bmp_bg1 , bmp_bg2 ;
    public static int cn = 1 ;


    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MySurfaceView(Context context) {
        super(context);
        init();
    }

    private void init(){
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void run() {

    }
}
