package com.lenovo.way.opengldemo.want;

import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lenovo.way.opengldemo.R;
import com.lenovo.way.opengldemo.two.ModelSurfaceView;
import com.lenovo.way.opengldemo.utils.ColorImage;

public class WantActivity extends AppCompatActivity {

    private Display display;
    private RelativeLayout relativelayout;

    private ModelSurfaceView mModelSurfaceView1;
    private ModelSurfaceView mModelSurfaceView2;

    private WantGLRender mWantGLRender1 = new WantGLRender(this);
    private WantGLRender mWantGLRender2 = new WantGLRender(this);

    // 滑动参数
    private GestureDetector mGestureDetector;

    private float mPreviousX;
    private float mPreviousY;
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private final float TRACKBALL_SCALE_FACTOR = 36.0f;
    private float distanceZ = -6.0f;

    // back button
    private Button slamStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ColorImage.load(this.getResources());

        display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();

        relativelayout = (RelativeLayout) findViewById(R.id.relativelayout_want);
        slamStop = (Button) findViewById(R.id.slam_stop);

        slamStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WantActivity.this,"STOP SLAM",Toast.LENGTH_SHORT).show();
            }
        });


        mModelSurfaceView1 = new ModelSurfaceView(this);
//        mModelSurfaceView1.setZOrderOnTop(true);
//        mModelSurfaceView1.setEGLConfigChooser(8, 8, 8, 8, 16, 0); // 设置颜色缓存为RGBA,位数都为8 depth缓存位数为16 stencil缓存位数为0
        mModelSurfaceView1.getHolder().setFormat(PixelFormat.TRANSPARENT);
        mModelSurfaceView1.setRenderer(mWantGLRender1);
        RelativeLayout.LayoutParams lp1 =new RelativeLayout.LayoutParams(width/2, height);
        lp1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        mModelSurfaceView1.setLayoutParams(lp1);
        relativelayout.addView(mModelSurfaceView1);

        mModelSurfaceView2 = new ModelSurfaceView(this);
//        mModelSurfaceView2.setZOrderOnTop(true);
//        mModelSurfaceView2.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        mModelSurfaceView2.getHolder().setFormat(PixelFormat.TRANSPARENT);
        mModelSurfaceView2.setRenderer(mWantGLRender2);
        RelativeLayout.LayoutParams lp2 =new RelativeLayout.LayoutParams(width/2, height);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        mModelSurfaceView2.setLayoutParams(lp2);
        relativelayout.addView(mModelSurfaceView2);

//        Button btn = new Button(this);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(WantActivity.this,"123",Toast.LENGTH_SHORT).show();
//            }
//        });
//        btn.setText("123");
//        relativelayout.addView(btn);

        mWantGLRender1.start();
        mWantGLRender2.start();

    }



    @Override
    protected void onResume() {

        mWantGLRender1.resume();
        mWantGLRender2.resume();

        mGestureDetector = new GestureDetector(new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                final double FLING_MIN_DISTANCE = 0.5;
                final double FLING_MIN_VELOCITY = 0.5;
                if (e1.getY() - e2.getY() > FLING_MIN_DISTANCE && Math.abs(distanceY) > FLING_MIN_VELOCITY){
//                    Log.e("way","up ~");
                    if (distanceZ <= -2.6f){
                        distanceZ += 0.1 ;
                        mWantGLRender1.z = distanceZ;
                        mWantGLRender2.z = distanceZ;
                    }else {
                        // Toast.makeText(MainActivity.this,"放到最大啦！",Toast.LENGTH_SHORT).show();
                    }
                }
                if (e1.getY() - e2.getY() < FLING_MIN_DISTANCE && Math.abs(distanceY) > FLING_MIN_VELOCITY){
//                    Log.e("way","down !");
                    if (distanceZ >= -8.9f){
                        distanceZ -= 0.1 ;
                        mWantGLRender1.z = distanceZ;
                        mWantGLRender2.z = distanceZ;
                    }else {
                        // Toast.makeText(MainActivity.this,"缩到最小了……",Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return true;
            }
        });

        super.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = mGestureDetector.onTouchEvent(event);
        if (!result){
            if (event.getAction() == MotionEvent.ACTION_UP){
                // getVideoInfosfromPath(filePath);
            }
            result = super.onTouchEvent(event);
        }
        return result;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWantGLRender1.pause();
        mWantGLRender2.pause();
    }
}
