package com.lenovo.way.opengldemo.two;

import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.lenovo.way.opengldemo.R;

public class Main2Activity extends AppCompatActivity {

    private Display display;
    private RelativeLayout relativelayout;

    private ModelSurfaceView mModelSurfaceView1;
    private ModelSurfaceView mModelSurfaceView2;

    private DrawModelRenderer mDrawModelRenderer1;
    private DrawModelRenderer mDrawModelRenderer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        relativelayout = (RelativeLayout) findViewById(R.id.relativelayout);

        mDrawModelRenderer1 = new DrawModelRenderer(this);
        mDrawModelRenderer2 = new DrawModelRenderer(this);

        mModelSurfaceView1 = new ModelSurfaceView(this);
        mModelSurfaceView1.setZOrderOnTop(true);
        mModelSurfaceView1.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        mModelSurfaceView1.getHolder().setFormat(PixelFormat.TRANSPARENT);
        mModelSurfaceView1.setRenderer(mDrawModelRenderer1);
        RelativeLayout.LayoutParams lp1 =new RelativeLayout.LayoutParams(width/2, height);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        mModelSurfaceView1.setLayoutParams(lp1);
        relativelayout.addView(mModelSurfaceView1);

        mModelSurfaceView2 = new ModelSurfaceView(this);
        mModelSurfaceView2.setZOrderOnTop(true);
        mModelSurfaceView2.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        mModelSurfaceView2.getHolder().setFormat(PixelFormat.TRANSPARENT);
        mModelSurfaceView2.setRenderer(mDrawModelRenderer2);
        RelativeLayout.LayoutParams lp2 =new RelativeLayout.LayoutParams(width/2, height);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mModelSurfaceView2.setLayoutParams(lp2);
        relativelayout.addView(mModelSurfaceView2);

        mDrawModelRenderer1.start();
        mDrawModelRenderer2.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mDrawModelRenderer1.resume();
        mDrawModelRenderer2.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDrawModelRenderer1.pause();
        mDrawModelRenderer2.pause();
    }
}
