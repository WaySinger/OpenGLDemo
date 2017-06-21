package com.lenovo.way.opengldemo.face;

import com.lenovo.way.opengldemo.utils.BufferUtil;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author way
 * @data 2017/5/9
 * @description .
 */

public class SmoothColoredSquare extends Square {

    // vertexs's color
    private float[] colors = {
            1f, 0f, 0f, 1f,  // vertex 0 red
            0f, 1f, 0f, 1f,  // vertex 1 green
            0f, 0f, 1f, 1f,  // vertex 2 blue
            1f, 0f, 1f, 1f,  // vertex 3 megenta
    };

    // 四个顶点
    private float vertices[] = {
            -1.0f, 1.0f, 0.0f,  // 0, Top Left
            -1.0f, -1.0f, 0.0f, // 1, Bottom Left
            1.0f, -1.0f, 0.0f,  // 2, Bottom Right
            1.0f, 1.0f, 0.0f,   // 3, Top Right
    };

    // The order we like to connect them.
    private short[] indices = { 0, 1, 2, 0, 2, 3 };

    private FloatBuffer vertexBuffer;   // 顶点坐标，为了提高性能，通常将这些数组存放到java.io 中定义的Buffer类中

    private ShortBuffer indexBuffer;

    private FloatBuffer colorBuffer;

    public SmoothColoredSquare() {
        // 初始化
        vertexBuffer = BufferUtil.bufferUtil(vertices);
        indexBuffer = BufferUtil.bufferUtil(indices);
        colorBuffer = BufferUtil.bufferUtil(colors);
    }

    @Override
    public void draw(GL10 gl) {

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        // Enable the color array buffer to be used during rendering.
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        // Point out the where the color buffer is.
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);


        super.draw(gl);

        // Disable the color buffer.
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

    }
}
