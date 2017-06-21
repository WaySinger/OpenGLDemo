package com.lenovo.way.opengldemo.face;

import com.lenovo.way.opengldemo.utils.BufferUtil;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author way
 * @data 2017/5/9
 * @description 绘制正方形.
 */

public class Square {

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

    public Square() {

        // 初始化
        vertexBuffer = BufferUtil.bufferUtil(vertices);
        indexBuffer = BufferUtil.bufferUtil(indices);
    }

    /**
     * This function draws our square on screen.
     * @param gl
     */
    public void draw(GL10 gl) {

        // Counter-clockwise winding.  // 设置逆时针方法为面的“前面”
        gl.glFrontFace(GL10.GL_CCW);
        // Enable face culling.  //  打开 忽略“后面”设置
        gl.glEnable(GL10.GL_CULL_FACE);
        // What faces to remove with the face culling.  // 明确指明“忽略”哪个面
        gl.glCullFace(GL10.GL_BACK);

        // 需要告诉OpenGL库打开 Vertex buffer以便传入顶点坐标Buffer.
        // 要注意:使用OpenGL ES的这些功能之后，要关闭这个功能以免影响后续操作.

        // Enabled the vertices buffer for writing and to be used during rendering.
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // Specifies the location and data format of an array of vertex coordinates to use when rendering.
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0,
                vertexBuffer);

        gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
                GL10.GL_UNSIGNED_SHORT, indexBuffer);

        // When you are done with the buffer don't forget to disable it.
        // Disable the vertices buffer.
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        // Disable face culling.
        gl.glDisable(GL10.GL_CULL_FACE);

        // Color
        gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);
    }

}
