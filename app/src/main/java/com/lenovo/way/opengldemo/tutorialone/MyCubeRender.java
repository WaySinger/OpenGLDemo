package com.lenovo.way.opengldemo.tutorialone;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


/**
 * @author way
 * @data 2017/5/11
 * @description .
 */

public class MyCubeRender implements GLSurfaceView.Renderer {

    //每一个面画两个三角形，立方体有6个面
    private float[] vertices = {
            0f, 1f, 1f,
            0f, 0f, 1f,
            1f, 0f, 1f,
            0f, 1f, 1f,
            1f, 0f, 1f,
            1f, 1f, 1f,  // front

            1f, 1f, 1f,
            1f, 0f, 1f,
            1f, 0f, 0f,
            1f, 1f, 1f,
            1f, 0f, 0f,
            1f, 1f, 0f,  // right

            0f, 1f, 0f,
            0f, 0f, 0f,
            0f, 1f, 1f,
            0f, 0f, 0f,
            0f, 0f, 1f,
            0f, 1f, 1f,  // left

            1f, 1f, 0f,
            1f, 0f, 0f,
            0f, 0f, 0f,
            1f, 1f, 0f,
            0f, 0f, 0f,
            0f, 1f, 0f,  // behind

            0f, 1f, 0f,
            0f, 1f, 1f,
            1f, 1f, 0f,
            0f, 1f, 1f,
            1f, 1f, 1f,
            1f, 1f, 0f,  // up

            0f, 0f, 1f,
            0f, 0f, 0f,
            1f, 0f, 0f,
            0f, 0f, 1f,
            1f, 0f, 0f,
            1f, 0f, 1f,  // down
    };
    //立方体的顶点颜色
    private float[] colors = {
            1f, 0f, 0f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 0f, 1f,

            1f, 0f, 1f, 1f,
            1f, 0f, 1f, 1f,
            1f, 0f, 1f, 1f,
            1f, 0f, 1f, 1f,
            1f, 0f, 1f, 1f,
            1f, 0f, 1f, 1f,

            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,

            0f, 0f, 1f, 1f,
            0f, 0f, 1f, 1f,
            0f, 0f, 1f, 1f,
            0f, 0f, 1f, 1f,
            0f, 0f, 1f, 1f,
            0f, 0f, 1f, 1f,

            0.5f, 0f, 1f, 1f,
            0.5f, 0f, 1f, 1f,
            0.5f, 0f, 1f, 1f,
            0.5f, 0f, 1f, 1f,
            0.5f, 0f, 1f, 1f,
            0.5f, 0f, 1f, 1f,

            1f, 0f, 0.5f, 1f,
            1f, 0f, 0.5f, 1f,
            1f, 0f, 0.5f, 1f,
            1f, 0f, 0.5f, 1f,
            1f, 0f, 0.5f, 1f,
            1f, 0f, 0.5f, 1f,
    };

    FloatBuffer vertBuffer; //顶点缓冲
    FloatBuffer colorBuffer; //颜色缓冲
    float rx = -70f; //旋转角度

    public MyCubeRender() {
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertBuffer = vbb.asFloatBuffer();
        vertBuffer.put(vertices);
        vertBuffer.position(0);

        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // 所做深度测试的类型
        gl.glDepthFunc(GL10.GL_DITHER);
        //黑色背景
        gl.glClearColor(0f, 0f, 0f, 0.5f);
        //启用阴影平滑
        gl.glShadeModel(GL10.GL_SMOOTH);
        //清除深度缓存
        gl.glClearDepthf(1.0f);

        gl.glEnable(GL10.GL_TEXTURE_2D);
        //告诉系统对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
    }

    public void draw(GL10 gl) {
        gl.glFrontFace(GL10.GL_CCW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);
        //开启顶点和纹理缓冲
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertBuffer);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -7);
        gl.glRotatef(15f, 1f, 0f, 0f); // x
        gl.glRotatef(-15f, 0f, 1f, 0f); // y
        gl.glRotatef(0f, 0f, 0f, 1f); // z

        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertices.length);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
        rx--; //旋转角度减1
    }

    public void onDrawFrame(GL10 gl) {

        // 清除深度和颜色缓存
        gl.glClearColor(0f, 0f, 0f, 0.5f);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);  //设置矩阵模式
        draw(gl);
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
}
