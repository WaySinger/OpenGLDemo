package com.lenovo.way.opengldemo.openes;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

import com.lenovo.way.opengldemo.openes.EsCube;
import com.lenovo.way.opengldemo.openes.util.MatrixState;

/**
 * @author way
 * @data 2017/10/12
 * @description .
 */

public class EsCubeRender implements Renderer {

    private static final String TAG = "EsCubeRender";

    private Context context;

    public EsCubeRender(Context context) {
        this.context = context;
    }

    EsCube esCube;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.i(TAG, "onSurfaceCreated");
        // 设置屏幕背景色RGBA
        glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        // 打开深度检测
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        // 打开背面剪裁
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        esCube = new EsCube(context);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        // 设置投影矩阵
//        circle.projectionMatrix(width, height);
        // 调用此方法计算产生透视投影矩阵
        MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 20, 100);
        // 调用此方法产生摄像机9参数位置矩阵
        MatrixState.setCamera(-16f, 8f, 45, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // 清除深度缓冲与颜色缓冲
        glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        esCube.draw();

    }
}
