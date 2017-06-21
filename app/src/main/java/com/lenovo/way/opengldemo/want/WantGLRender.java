package com.lenovo.way.opengldemo.want;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.view.GestureDetector;
import android.view.KeyEvent;

import com.lenovo.way.opengldemo.utils.ColorImage;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.lenovo.way.opengldemo.utils.BufferUtil.bufferUtil;

/**
 * @author way
 * @data 2017/5/26
 * @description .
 */

public class WantGLRender implements GLSurfaceView.Renderer, SensorEventListener {

    // 基本参数
    private Context mContext;
    private int view_width = 640;
    private int view_height = 720;
    private float ratio = 0;

    // 光线参数
    private FloatBuffer lightAmbient;
    private FloatBuffer lightDiffuse;
    private FloatBuffer lightPosition;
    private float[] light1 = new float[] { 0.5f, 0.5f, 0.5f, 1.0f};
    private float[] light2 = new float[] { 1.0f, 1.0f, 1.0f, 1.0f};
    private float[] light3 = new float[] { 0.0f, 0.0f, 2.0f, 1.0f};

    private int[] texture;  // 材质
    private IntBuffer vertices;
    private IntBuffer normals;
    private IntBuffer texCoords;  // 顶点

    // 数据参数
    int one = 0x10000;
    float xrot = 0.0f;
    float yrot = 0.0f;
    float zrot = 0.0f;
    float xspeed, yspeed;
    float z = -6.0f;  // 最近是-2.5f , 最远是-9.5f

    // gyro参数
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private static final float NS2S = 1.0f / 1000000000.0f;
    private float timestamp;
    private float[] angle = {0,0,0};


    public WantGLRender(Context context){
        mContext = context;

        vertices = bufferUtil(verticesdata);
        texCoords = bufferUtil(texCoordsdata);
        normals = bufferUtil(normalsdata);
        lightAmbient = bufferUtil(light1);
        lightDiffuse = bufferUtil(light2);
        lightPosition = bufferUtil(light3);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glDisable(GL10.GL_DITHER);

        // 告诉系统对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        // 黑色背景
        gl.glClearColor(0, 0, 0, 0);

        gl.glEnable(GL10.GL_CULL_FACE);
        // 启用阴影平滑
        gl.glShadeModel(GL10.GL_SMOOTH);
        // 启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);

        // 设置光线,,1.0f为全光线，a=50%
        gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
        // 基于源象素alpha通道值的半透明混合函数
//        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ZERO);

        // 纹理相关
        GLTextureUtil(gl);

        // 深度测试相关
        gl.glClearDepthf(1.0f);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        gl.glEnable(GL10.GL_TEXTURE_2D);

        // 设置屏幕大小
        sizeChanged(gl, view_width, view_height);

        // 设置环境光
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, lightAmbient);

        // 设置漫射光
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, lightDiffuse);

        // 设置光源位置
        gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, lightPosition);

        // 开启一号光源
        gl.glEnable(GL10.GL_LIGHT1);

        // 开启混合
        gl.glEnable(GL10.GL_BLEND);
    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        float ratio = (float) width / height;
        // 设置OpenGL场景的大小
        gl.glViewport(0, 0, width, height);
        // 设置投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // 重置投影矩阵
        gl.glLoadIdentity();
        // 设置视口的大小
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
        // 选择模型观察矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // 重置模型观察矩阵
        gl.glLoadIdentity();
    }


    @Override
    public void onDrawFrame(GL10 gl) {
        // 清除屏幕和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // 重置当前的模型观察矩阵
        gl.glLoadIdentity();

        gl.glEnable(GL10.GL_DEPTH_TEST);

        gl.glTranslatef(0.0f, 0.0f, z);

        // 设置旋转
        gl.glRotatef(xrot, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(yrot, 1.0f, 0.0f, 0.0f);
        // 设置法线
        gl.glNormalPointer(GL10.GL_FIXED, 0, normals);

        gl.glVertexPointer(3, GL10.GL_FIXED, 0, vertices);
        // 设置顶点数组为纹理坐标缓存
        gl.glTexCoordPointer(2, GL10.GL_FIXED, 0, texCoords);

        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        // 绘制四边形

        BindElementsWithTexture(gl);

    }

    // 绘制四边形和Texture元素绑定
    public void BindElementsWithTexture(GL10 gl) {
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[0]);
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE,
                indices1);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[1]);
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 8, GL10.GL_UNSIGNED_BYTE,
                indices2);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[2]);
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 12, GL10.GL_UNSIGNED_BYTE,
                indices3);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[3]);
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 16, GL10.GL_UNSIGNED_BYTE,
                indices4);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[4]);
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 20, GL10.GL_UNSIGNED_BYTE,
                indices5);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[5]);
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 24, GL10.GL_UNSIGNED_BYTE,
                indices6);

        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
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

            // phone
            xrot = yy ;
            yrot = xx ;
            // Lumes
//            xrot = - xx ;
//            yrot = - yy ;
            zrot = zz ;
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


    public void sizeChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        ratio = (float) width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 25);
    }


    // Texture纹理初始化
    public void GLTextureUtil(GL10 gl) {

        IntBuffer textureBuffer = IntBuffer.allocate(6);
        gl.glGenTextures(6, textureBuffer);
        texture = textureBuffer.array();

        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[0]);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, ColorImage.mBitmap1, 0);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                GL10.GL_NEAREST);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                GL10.GL_NEAREST);

        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[1]);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, ColorImage.mBitmap2, 0);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                GL10.GL_NEAREST);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                GL10.GL_NEAREST);

        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[2]);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, ColorImage.mBitmap3, 0);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                GL10.GL_NEAREST);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                GL10.GL_NEAREST);

        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[3]);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, ColorImage.mBitmap4, 0);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                GL10.GL_NEAREST);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                GL10.GL_NEAREST);

        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[4]);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, ColorImage.mBitmap5, 0);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                GL10.GL_NEAREST);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                GL10.GL_NEAREST);

        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture[5]);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, ColorImage.mBitmap6, 0);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                GL10.GL_NEAREST);
        gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                GL10.GL_NEAREST);
    }


    int[] verticesdata = new int[] {
            -one,-one,one,
            one,-one,one,
            one,one,one,
            -one,one,one,

            -one,-one,-one,
            -one,one,-one,
            one,one,-one,
            one,-one,-one,

            -one,one,-one,
            -one,one,one,
            one,one,one,
            one,one,-one,

            -one,-one,-one,
            one,-one,-one,
            one,-one,one,
            -one,-one,one,

            one,-one,-one,
            one,one,-one,
            one,one,one,
            one,-one,one,

            -one,-one,-one,
            -one,-one,one,
            -one,one,one,
            -one,one,-one,
    };

    int[] normalsdata = new int[] {
            0,0,one,
            0,0,one,
            0,0,one,
            0,0,one,

            0,0,one,
            0,0,one,
            0,0,one,
            0,0,one,

            0,one,0,
            0,one,0,
            0,one,0,
            0,one,0,

            0,-one,0,
            0,-one,0,
            0,-one,0,
            0,-one,0,

            one,0,0,
            one,0,0,
            one,0,0,
            one,0,0,

            -one,0,0,
            -one,0,0,
            -one,0,0,
            -one,0,0,  };

    int[] texCoordsdata = new int[] {
            one,0,0,0,0,one,one,one,
            0,0,0,one,one,one,one,0,
            one,one,one,0,0,0,0,one,
            0,one,one,one,one,0,0,0,
            0,0,0,one,one,one,one,0,
            one,0,0,0,0,one,one,one, };
    ByteBuffer indices1 = ByteBuffer.wrap(new byte[] {
            0,1,3,2,
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
    });
    ByteBuffer indices2 = ByteBuffer.wrap(new byte[] {
            0,0,0,0,
            4,5,7,6,
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
            0,0,0,0, });
    ByteBuffer indices3 = ByteBuffer.wrap(new byte[] {
            0,0,0,0,
            0,0,0,0,
            8,9,11,10,
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,  });
    ByteBuffer indices4 = ByteBuffer.wrap(new byte[] {
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
            12,13,15,14,
            0,0,0,0,
            0,0,0,0,  });
    ByteBuffer indices5 = ByteBuffer.wrap(new byte[] {
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
            16,17,19,18,
            0,0,0,0, });
    ByteBuffer indices6 = ByteBuffer.wrap(new byte[] {
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
            0,0,0,0,
            20,21,23,22, });
}
