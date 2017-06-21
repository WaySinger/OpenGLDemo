package com.lenovo.way.opengldemo.two;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;

import com.lenovo.way.opengldemo.R;

public class GyroActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private TextView tv_x, tv_y, tv_z,dis_z;

    private static final float NS2S = 1.0f / 1000000000.0f;
    private float timestamp;

    private float[] angle = {0,0,0};

    private float mPreviousX;
    private float mPreviousY;
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private final float TRACKBALL_SCALE_FACTOR = 36.0f;
    private float distanceZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyro);

        tv_x = (TextView)findViewById(R.id.tv_x);
        tv_y = (TextView)findViewById(R.id.tv_y);
        tv_z = (TextView)findViewById(R.id.tv_z);
        dis_z = (TextView)findViewById(R.id.dis_z);

        //通过getSystemService获得SensorManager实例对象
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //通过SensorManager实例对象获得想要的传感器对象:参数决定获取哪个传感器
//        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

    }

    /**
     * 在获得焦点时注册传感器并让本类实现SensorEventListener接口
     */
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * 传感器事件值改变时的回调接口：执行此方法的频率与注册传感器时的频率有关
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
//        float x = event.values[0];
//        float y = event.values[1];
//        float z = event.values[2];
//
//        // TODO 利用获得的三个float传感器值做些操作
//        String xx = String.valueOf(x);
//        String yy = String.valueOf(y);
//        String zz = String.valueOf(z);
//
//        tv_x.setText("x: "+ xx);
//        tv_y.setText("y: "+ yy);
//        tv_z.setText("z: "+ zz);

        //从 x、y、z 轴的正向位置观看处于原始方位的设备，如果设备逆时针旋转，将会收到正值；否则，为负值
        if (timestamp != 0)
        {
            //  event.timesamp表示当前的时间，单位是纳秒（1百万分之一毫秒）
            // 得到两次检测到手机旋转的时间差（纳秒），并将其转化为秒
            final float dT = (event.timestamp - timestamp) * NS2S;
            // 将手机在各个轴上的旋转角度相加，即可得到当前位置相对于初始位置的旋转弧度
            angle[0] += event.values[0] * dT;
            angle[1] += event.values[1] * dT;
            angle[2] += event.values[2] * dT;

            // 将弧度转化为角度
            float anglex = (float) Math.toDegrees(angle[0]);
            float angley = (float) Math.toDegrees(angle[1]);
            float anglez = (float) Math.toDegrees(angle[2]);

            // 方块旋转角度
            // y轴
            float yy = anglex;
            // x轴
            float xx = angley;
            // z轴
            float zz = - anglez;

            tv_x.setText("x: "+ anglex + "    xx: "+ xx);
            tv_y.setText("y: "+ angley + "    yy: "+ yy);
            tv_z.setText("z: "+ anglez + "    zz: "+ zz);

        }
        //将当前时间赋值给timestamp
        timestamp = event.timestamp;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO 在传感器精度发生改变时做些操作，accuracy为当前传感器精度
    }

    /**
     * 在失去焦点时注销传感器
     */
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        dis_z.onKeyUp(keyCode,event);
        return false;
    }

    public boolean onTrackballEvent(MotionEvent e) {
        distanceZ += e.getY() * TRACKBALL_SCALE_FACTOR;
        return true;
    }

    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mPreviousX;
                float dy = y - mPreviousY;
                distanceZ -= dy * TOUCH_SCALE_FACTOR;
        }
        mPreviousX = x;
        mPreviousY = y;
        float zzzz = distanceZ / 100;
        dis_z.setText("zzzzzzzz: "+ zzzz);
        return true;
    }
}
