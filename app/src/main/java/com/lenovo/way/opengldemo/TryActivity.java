package com.lenovo.way.opengldemo;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author way
 * @data 2017/5/22
 * @description .
 */

public class TryActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //第一步：通过getSystemService获得SensorManager实例对象
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //第二步：通过SensorManager实例对象获得想要的传感器对象:参数决定获取哪个传感器
//        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

    }

    /**
     * 第三步：在获得焦点时注册传感器并让本类实现SensorEventListener接口
     */
    @Override
    protected void onResume() {
        super.onResume();
        /*
     *第一个参数：SensorEventListener接口的实例对象
     *第二个参数：需要注册的传感器实例
     *第三个参数：传感器获取传感器事件event值频率：
     * SensorManager.SENSOR_DELAY_FASTEST = 0：对应0微秒的更新间隔，最快，1微秒 = 1 % 1000000秒
     * SensorManager.SENSOR_DELAY_GAME = 1：对应20000微秒的更新间隔，游戏中常用
     * SensorManager.SENSOR_DELAY_UI = 2：对应60000微秒的更新间隔
     * SensorManager.SENSOR_DELAY_NORMAL = 3：对应200000微秒的更新间隔
     * 键入自定义的int值x时：对应x微秒的更新间隔
     *
     */
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    //第四步：必须重写的两个方法：onAccuracyChanged，onSensorChanged

    /**
     * 传感器事件值改变时的回调接口：执行此方法的频率与注册传感器时的频率有关
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        // 大部分传感器会返回三个轴方向x,y,x的event值，值的意义因传感器而异
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        // TODO 利用获得的三个float传感器值做些操作

    }

    /**
     * 传感器精度发生改变的回调接口
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO 在传感器精度发生改变时做些操作，accuracy为当前传感器精度
    }

    /**
     * 第五步：在失去焦点时注销传感器
     */
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
