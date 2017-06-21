package com.lenovo.way.opengldemo.two;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.widget.TextView;

import com.lenovo.way.opengldemo.face.Square;
import com.lenovo.way.opengldemo.mesh.Cube;
import com.lenovo.way.opengldemo.mesh.Group;
import com.lenovo.way.opengldemo.mesh.Mesh;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author way
 * @data 2017/5/17
 * @description .
 */

public class DrawModelRenderer implements GLSurfaceView.Renderer,SensorEventListener {

    private Context mContext;

    private int view_width = 640;
    private int view_height = 720;

    private float ratio = 0;
    private float rotate = -30f;


    private SensorManager mSensorManager;
    private Sensor mSensor;

    private static final float NS2S = 1.0f / 1000000000.0f;
    private float timestamp;

    private float[] angle = {0,0,0};

    // Draw square.
    // private Square square;

    // Draw Cube.
    private Mesh root;
    private Group group;
    private Cube cube;

    public DrawModelRenderer(Context context){
        mContext = context;

        // square = new Square();

        group = new Group();
        cube = new Cube(1, 1, 1);
        cube.x = 1;
        cube.y = 0;
        cube.z = 0;
        cube.rx = -5;
        cube.ry = 0;

        group.add(cube);
        root = group;

//        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
//        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        // Set the background color to black ( RGBA ).
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        // gl.glClearColor(0.375f, 0.75f, 0.75f, 0.5f);  // My Favorite Color.

        // Enable Smooth Shading, default not really needed.
        // 启用阴影平滑
        gl.glShadeModel(GL10.GL_SMOOTH);

        // Depth buffer setup.
        // 清楚深度缓存
        gl.glClearDepthf(1.0f);

        // Enables depth testing.
        // 启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);

        // The type of depth testing to do.
        // 所做深度测试的类型
        gl.glDepthFunc(GL10.GL_LEQUAL);

        // Really nice perspective calculations.
        // 告诉系统对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,
                GL10.GL_NICEST);

        sizeChanged(gl, view_width, view_height);

    }

    // onSurfaceChanged方法里基本上是一致的
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        if (height == 0)
            height = 1; // To prevent divide by zero
        float aspect = (float) width / height;

        // Set the viewport (display area) to cover the entire window
        gl.glViewport(0, 0, width, height);

        // Setup perspective projection, with aspect ratio matches viewport
        gl.glMatrixMode(GL10.GL_PROJECTION); // Select projection matrix
        gl.glLoadIdentity(); // Reset projection matrix
        // Use perspective projection
        GLU.gluPerspective(gl, 45, aspect, 0.1f, 100.f);

        gl.glMatrixMode(GL10.GL_MODELVIEW); // Select model-view matrix
        gl.glLoadIdentity(); // Reset

    }

    @Override
    public void onDrawFrame(GL10 gl) {

        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT |
                GL10.GL_DEPTH_BUFFER_BIT);

        // Replace the current matrix with the identity matrix
        // 先重置Matrix的代码
        gl.glLoadIdentity();

        // Translates 4 units into the screen.
        // 因为OpenGL ES从当前位置开始渲染，缺省坐标为(0,0,0)，和View port 的坐标一样，相当于把画面放在眼前，对应这种情况OpenGL不会渲染离view Port很近的画面，因此我们需要将画面向后退一点距离
        gl.glTranslatef(0, 0, -8);

        // 沿着Y轴旋转
        // gl.glRotatef(rotate, 0f, 1f, 0f);

        // Draw square.
//        gl.glPushMatrix();  // 在变换前，在栈中保存当前矩阵
//        // gl.glRotatef(angle, 0, 0, 1);  // 旋转，角度为正时表示逆时针方向
//        square.draw(gl);
//        gl.glPopMatrix();  // 在变换后，从栈中恢复所存矩阵

        root.draw(gl);

        // rotate--;

        System.gc();
    }

    public void sizeChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        ratio = (float) width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 25);
    }

    public void draw(GL10 gl){
        gl.glDisable(GL10.GL_DEPTH_TEST);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
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

            cube.rx = xx ;
            cube.ry = yy ;
            cube.rz = zz ;
        }
        //将当前时间赋值给timestamp
        timestamp = event.timestamp;

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void start(){
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    public void resume(){
        mSensorManager.registerListener(this, mSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void pause(){
        mSensorManager.unregisterListener(this);
    }
}
