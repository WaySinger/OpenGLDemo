package com.lenovo.way.opengldemo.mesh;

import com.lenovo.way.opengldemo.utils.BufferUtil;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author way
 * @data 2017/5/11
 * @description .
 */

public class CubeTwo {

    // vertex buffer.
    FloatBuffer verticesBuffer;

    float rotate =-70f;

    public float x = 0;
    public float y = 0;
    public float z = 0;

    // Rotate params.
    // rx,ry,rz 定义旋转变换的参数
    public float rx = 0;
    public float ry = -70f;
    public float rz = 0;

    // Smooth Colors
    private FloatBuffer colorBuffer = null;

    //每一个面画两个三角形，立方体有6个面
    private float[] vertices={
//            -1.0f,1.0f,1f, // top left
//            -1.0f,-1.0f,1f, // bottom left
//            1.0f,-1.0f,1f,  //top right
//            -1.0f,1.0f,1f, //bottom left
//            1.0f,-1.0f,1f, //bottom right
//            1.0f,1.0f,1f,   //top right //前面
//
//            1.0f,1.0f,1f,
//            1.0f,-1.0f,1f,
//            1.0f,-1.0f,-1f,
//            1.0f,1.0f,1f,
//            1.0f,-1.0f,-1.0f,
//            1.0f,1.0f,-1f,      //右面
//
//            -1.0f,1.0f,-1.0f,
//            -1.0f,-1.0f,-1.0f,
//            -1.0f,1.0f,1.0f,
//            -1.0f,-1.0f,-1.0f,
//            -1.0f,-1.0f,1.0f,
//            -1.0f,1.0f,1.0f,  //左面
//
//            1.0f,1.0f,-1.0f,
//            1.0f,-1.0f,-1.0f,
//            -1.0f,-1.0f,-1.0f,
//            1.0f,1.0f,-1.0f,
//            -1.0f,-1.0f,-1.0f,
//            -1.0f,1.0f,-1.0f,   //后面
//
//            -1.0f,1.0f,-1.0f,   // top left
//            -1.0f,1.0f,1.0f,    //bottom left
//            1.0f,1.0f,-1.0f,    //top right
//            -1.0f,1.0f,1.0f,    //bottom left
//            1.0f,1.0f,1.0f,     //top right
//            1.0f,1.0f,-1.0f,    // -top right上面
//
//            -1.0f,-1.0f,1.0f,
//            -1.0f,-1.0f,-1.0f,
//            1.0f,-1.0f,-1.0f,
//            -1.0f,-1.0f,1.0f,
//            1.0f,-1.0f,-1.0f,
//            1.0f,-1.0f,1.0f,    //下面
            0f,1f,1f,
            0f,0f,1f,
            1f,0f,1f,
            0f,1f,1f,
            1f,0f,1f,
            1f,1f,1f,  // front

            1f,1f,1f,
            1f,0f,1f,
            1f,0f,0f,
            1f,1f,1f,
            1f,0f,0f,
            1f,1f,0f,  // right

            0f,1f,0f,
            0f,0f,0f,
            0f,1f,1f,
            0f,0f,0f,
            0f,0f,1f,
            0f,1f,1f,  // left

            1f,1f,0f,
            1f,0f,0f,
            0f,0f,0f,
            1f,1f,0f,
            0f,0f,0f,
            0f,1f,0f,  // behind

            0f,1f,0f,
            0f,1f,1f,
            1f,1f,0f,
            0f,1f,1f,
            1f,1f,1f,
            1f,1f,0f,  // up

            0f,0f,1f,
            0f,0f,0f,
            1f,0f,0f,
            0f,0f,1f,
            1f,0f,0f,
            1f,0f,1f,  // down
    };

    //立方体的顶点颜色
    private float[] colors={
            1f,0f,0f,1f,
            1f,0f,0f,1f,
            1f,0f,0f,1f,
            1f,0f,0f,1f,
            1f,0f,0f,1f,
            1f,0f,0f,1f,

            1f,0f,1f,1f,
            1f,0f,1f,1f,
            1f,0f,1f,1f,
            1f,0f,1f,1f,
            1f,0f,1f,1f,
            1f,0f,1f,1f,

            0f,1f,0f,1f,
            0f,1f,0f,1f,
            0f,1f,0f,1f,
            0f,1f,0f,1f,
            0f,1f,0f,1f,
            0f,1f,0f,1f,

            0f,0f,1f,1f,
            0f,0f,1f,1f,
            0f,0f,1f,1f,
            0f,0f,1f,1f,
            0f,0f,1f,1f,
            0f,0f,1f,1f,

            0.5f,0f,1f,1f,
            0.5f,0f,1f,1f,
            0.5f,0f,1f,1f,
            0.5f,0f,1f,1f,
            0.5f,0f,1f,1f,
            0.5f,0f,1f,1f,

            1f,0f,0.5f,1f,
            1f,0f,0.5f,1f,
            1f,0f,0.5f,1f,
            1f,0f,0.5f,1f,
            1f,0f,0.5f,1f,
            1f,0f,0.5f,1f,
    };

    public CubeTwo(){
        verticesBuffer = BufferUtil.bufferUtil(vertices); // 允许子类重新定义顶点坐标
        colorBuffer = BufferUtil.bufferUtil(colors);  // 允许子类重新定义颜色
        // rx = 10 ;
        // ry = 15 ;
    }

    public void draw(GL10 gl){

        // Counter-clockwise winding.
        gl.glFrontFace(GL10.GL_CCW);
        // Enable face culling.
        gl.glEnable(GL10.GL_CULL_FACE);
        // What faces to remove with the face culling.
        gl.glCullFace(GL10.GL_BACK);

        //开启顶点和纹理缓冲
        // Enabled the vertices buffer for writing and to be used during rendering.
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // Specifies the location and data format of an array of vertex coordinates to use when rendering.
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);

        // Smooth color
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
//        if (colorBuffer != null) {
//            // Enable the color array buffer to be used during rendering.
//            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
//            gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
//        }

        gl.glLoadIdentity();

        gl.glTranslatef(0, 0, -5);
        gl.glRotatef(45f, 1, 0, 0);
        gl.glRotatef(ry, 0, 1, 0);
        // gl.glRotatef(rz, 0, 0, 1);

        // Point out the where the color buffer is.
        // gl.glDrawElements(GL10.GL_TRIANGLES, numOfIndices, GL10.GL_UNSIGNED_SHORT, indicesBuffer);

        // DrawArrays.
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0,vertices.length);

        // Disable the vertices buffer.
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        // Disable face culling.
        gl.glDisable(GL10.GL_CULL_FACE);

        ry--;  // 旋转角度减1
    }
}
